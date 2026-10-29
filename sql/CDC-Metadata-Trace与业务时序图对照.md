# CDC Metadata Trace 与业务时序图对照

> 背景：`cdms-gl-gbl-sor-cpmg-party` 接入 OpenTelemetry 后，Grafana Tempo 中出现一条 trace：
> 根 span 为 `cdms-cdc-listener-service: MetadataListenerSchedulingService.scheduledMetadataWork`（trace 总时长 4.07s，36 个 span），
> 包含 CDC Listener → Party `PUT /api/v1/parties/{partyIdentifier}/metadata` → MongoDB 的跨服务调用。
> 本文判断这条链路是否属于此前梳理的 CUS ↔ CDMS 完整业务时序图。

## 结论

**不是。** 这条 trace 是 CDC Listener 中**另一条流程（metadata 同步）**，与业务时序图只有一小部分重叠。

## 1. 逐项对照

| 业务时序图（CDC 部分，步骤 ⑭–㉑） | 本条 trace |
|---|---|
| ⑭⑮ Change Stream 事件存为 PENDING，写入 `erds_cdms_event`，由 Scheduler 处理 | 根 span 为 `MetadataListenerSchedulingService.scheduledMetadataWork`，是 metadata 专用定时任务。trace 中有 `find erds_cdms_event`，是唯一明确的交集 |
| ⑯ **HTTP 调用 Event Store** `searchHistory` | ❌ 未出现 `cdms-event-store-service` 的 span |
| ⑰–⑲ Event Store 查询 Mongo events | ❌ 无 |
| ⑳ 判断来源是否为 CUS→CDMS | 无法从 trace 看出 |
| ㉑ 写入 `ERDSToCUSNotSync` | ❌ 无 |
| — | ✅ 读取 `metadata_queue` / `metadata_queue_config`，**调用 Party `PUT /metadata`**，再更新 `metadata_queue`。这些步骤**时序图中均未出现** |

## 2. 为什么判断 CDC 的两个 POST 不是调用 Event Store

Event Store 已接入 OTel（Tempo 节点图中可见该服务）。若 CDC 的两个 `POST`（81ms / 29ms）是 searchHistory，其下方应挂有 `cdms-event-store-service` 的 server span，但实际下方为空。

确认方法：点开 POST span，查看 `url.full` / `server.address` 属性。根据节点图推测，目标大概率是未接入 OTel 的主机 `hkl25084737.hk.hsbc`。

## 3. 与业务流程可能的关系

两条流程结构相同，都是“先落库，再由定时任务处理”，只是处理目标不同：

```
业务时序图：Change Stream → erds_cdms_event(PENDING) → Scheduler → Event Store searchHistory → 写 ERDSToCUSNotSync
本条 trace：     ?        → metadata_queue           → Scheduler → Party PUT /metadata    → 更新 metadata_queue
```

一种可能（**未经代码证实**）：时序图第 ⑧ 步 Syncback PATCH 了 Party，数据变化后有组件向 `metadata_queue` 写入一条记录，再由本 Scheduler 回写 Party 的 metadata。若属实，它就是时序图中**尚未画出的一条下游分支**。

确认方法：在代码中查找**谁在写入 `metadata_queue`**。

## 4. 在 Tempo 中查找时序图上的 CDC 链路（⑯–⑲）

```
{ resource.service.name = "cdms-cdc-listener-service" }
  >> { resource.service.name = "cdms-event-store-service" && span.db.system = "mongodb" }
```

- **能查到**：即为时序图中 CDC → Event Store → Mongo events 这一段。
- **查不到**：要么该流程近期未被触发，要么 CDC 调用 Event Store 时 context 断开，需进一步排查。

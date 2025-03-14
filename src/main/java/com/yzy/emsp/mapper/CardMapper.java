package com.yzy.emsp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzy.emsp.domain.entity.Card;
import com.yzy.emsp.domain.query.CardQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CardMapper extends BaseMapper<Card> {


    IPage<Card> selectCardPage(Page<Card> pageParam,@Param("cardQuery")  CardQuery cardQuery);
}

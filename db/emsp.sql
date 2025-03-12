/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1_30001
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 127.0.0.1:30001
 Source Schema         : emsp

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 12/03/2025 15:06:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ev_account
-- ----------------------------
DROP TABLE IF EXISTS `ev_account`;
CREATE TABLE `ev_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contract_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT 'Account Status (INACTIVE=0;ACTIVATED=1)',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ev_account
-- ----------------------------
INSERT INTO `ev_account` VALUES (1, 'aaaa', 'asas', 'DE123ABC4567890', 'a@ema.com', 'INACTIVE', '2025-03-19 00:07:26', '2025-03-10 23:45:53');
INSERT INTO `ev_account` VALUES (4, 'kong', '$2a$10$1Fvjd1Fke1OqkB3xz5sfj.NWEIkQ3jEbyuq641eijK7PwmvTBdTS2', 'GBXMC58JBC2M2QH', 'a183124902@gmail.com', 'INACTIVE', '2025-03-10 23:02:15', '2025-03-10 23:02:15');
INSERT INTO `ev_account` VALUES (5, '1231231', '$2a$10$TbZOhsiEYF7Dl6d/4DerAus3uB0JnsBnJvxdjzSJfwPx4KK35k8nW', 'GBANNJJ3BP6THNT', '123@qq.com', 'INACTIVE', '2025-03-10 23:34:56', '2025-03-10 23:36:48');
INSERT INTO `ev_account` VALUES (6, 'st123ring', '$2a$10$5ySicWybhuAW9C8.zr.ftONeuNZiRw09zMYIt.iz5GIR1BJED5StC', 'SEMQZA69P6E5TV4', '123@gg.com', 'ACTIVATED', '2025-03-11 00:28:22', '2025-03-11 00:28:58');

-- ----------------------------
-- Table structure for ev_card
-- ----------------------------
DROP TABLE IF EXISTS `ev_card`;
CREATE TABLE `ev_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ' Visible Number',
  `uid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'uid',
  `user_id` int(11) NULL DEFAULT NULL COMMENT 'Bound User ID',
  `card_type` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Card Type (RFID=0;NFC=1;QR Code=2)',
  `balance` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Prepaid Card Balance ',
  `status` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Card Status (CREATED=0;ASSIGNED=1;ACTIVATED=2;DEACTIVATED=3)',
  `issue_date` datetime NULL DEFAULT NULL COMMENT 'Card Issuance Time',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'Record Creation Time\n',
  `update_time` datetime NULL DEFAULT NULL COMMENT 'Record Update Time\n\n\n\n\n\n\n',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_card_card_num`(`card_number`) USING BTREE,
  UNIQUE INDEX `uniq_card_uid`(`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ev_card
-- ----------------------------
INSERT INTO `ev_card` VALUES (1, 'EV-202503-000002', '138D184725E6A184', 5, 'RFID', '100', 'CREATED', '2023-07-20 07:00:00', '2025-03-11 00:32:05', '2025-03-11 21:34:00');

SET FOREIGN_KEY_CHECKS = 1;

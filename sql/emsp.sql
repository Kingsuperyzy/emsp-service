

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contract_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(11) NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_ruxg86y66hmn0a129n4j75akk`(`account_no`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `balance` decimal(10, 2) NOT NULL,
  `card_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `card_type` int(11) NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `issue_date` datetime NULL DEFAULT NULL,
  `status` int(11) NOT NULL,
  `uid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `user_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_2gifx4rwq94tfkasgtoaxpdah`(`card_no`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

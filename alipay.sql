/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云sfy
 Source Server Type    : MySQL
 Source Server Version : 50625
 Source Host           : 47.99.244.14:3306
 Source Schema         : alipay

 Target Server Type    : MySQL
 Target Server Version : 50625
 File Encoding         : 65001

 Date: 29/03/2019 15:10:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jy_order
-- ----------------------------
DROP TABLE IF EXISTS `jy_order`;
CREATE TABLE `jy_order`  (
  `out_trade_no` varchar(35) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商家订单号（唯一）',
  `payment` decimal(20, 2) DEFAULT NULL COMMENT '实付金额。精确到2位小数;单位:元。',
  `act_rec_money` decimal(20, 2) DEFAULT NULL COMMENT '实收金额（实付金额扣除服务费后）单位：元',
  `order_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '状态：1、未付款，2、已付款，3、已完成，4、已退款 5，已关闭（订单超时）',
  `goods_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '状态:  1、未出货,   2、已出货',
  `create_time` datetime(0) DEFAULT NULL COMMENT '订单创建时间',
  `finish_time` datetime(0) DEFAULT NULL COMMENT '订单完成时间',
  `close_time` datetime(0) DEFAULT NULL COMMENT '订单关闭时间（取消购买/退款）',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `seller_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商家ID',
  PRIMARY KEY (`out_trade_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jy_order
-- ----------------------------
INSERT INTO `jy_order` VALUES ('1111501169912750080', 16665.00, 16665.00, '2', '1', '2019-03-29 13:31:45', '2019-03-29 13:32:14', NULL, '2088102177530622', '2088102177366276');
INSERT INTO `jy_order` VALUES ('1111501485408296960', 888.00, NULL, '5', '1', '2019-03-29 13:33:00', NULL, '2019-03-29 13:35:01', NULL, '2088102177366276');
INSERT INTO `jy_order` VALUES ('11b910488f594af9a693df002517c770', NULL, 10.00, '2', NULL, NULL, '2019-03-27 09:50:05', NULL, '2088102177530622', NULL);
INSERT INTO `jy_order` VALUES ('1ed1031c7ebf4c4a9e36aba634ddd010', 6.66, 6.66, '2', '1', '2019-03-27 10:29:00', '2019-03-27 10:29:31', NULL, '2088102177530622', '');
INSERT INTO `jy_order` VALUES ('2a957447494c4c16afa3bf46134773ab', 8.88, NULL, '5', '1', '2019-03-28 11:50:45', NULL, '2019-03-28 11:52:45', NULL, '');
INSERT INTO `jy_order` VALUES ('407d5c04e81044329a98a53a9a6b8b01', 60.00, 60.00, '2', '1', '2019-03-29 12:00:10', '2019-03-29 12:00:24', NULL, '2088102177530622', '2088102177366276');
INSERT INTO `jy_order` VALUES ('4898d14bde634d46bf9dc7b3ef9d0052', 10.32, 10.32, '2', '1', '2019-03-27 10:40:41', '2019-03-27 10:40:55', NULL, '2088102177530622', '');
INSERT INTO `jy_order` VALUES ('5ef6fe5c5af7483e8a96e35f5fcc5d79', 10.00, NULL, '1', '1', '2019-03-27 09:42:45', NULL, NULL, NULL, '');
INSERT INTO `jy_order` VALUES ('6218ca12f88047cbbb656a1f65137aaf', 11.38, 11.38, '2', '1', '2019-03-27 17:00:08', '2019-03-27 17:00:20', NULL, '2088102177530622', '');
INSERT INTO `jy_order` VALUES ('65ee196c91e24a72aa976e2dc5abe4c7', 8.88, NULL, '1', '1', '2019-03-27 10:12:53', NULL, NULL, NULL, '');
INSERT INTO `jy_order` VALUES ('7bf150c39f5c4e04875b27d6aff9fe05', 10.00, NULL, '1', '1', '2019-03-27 09:09:04', NULL, NULL, NULL, '');
INSERT INTO `jy_order` VALUES ('96a5cd05159040f88107c484199f3ca9', 8.61, 8.61, '2', '1', '2019-03-27 11:11:29', '2019-03-27 11:12:16', NULL, '2088102177530622', '');
INSERT INTO `jy_order` VALUES ('99dfb5d132324124ac8c9dd7e435daf6', 8.88, NULL, '1', '1', '2019-03-27 10:23:45', NULL, NULL, NULL, '');
INSERT INTO `jy_order` VALUES ('a2f55ed4ae6c47d4964b99e51e1c1ea6', 110.00, 110.00, '4', '1', '2019-03-28 15:38:34', '2019-03-28 15:38:48', '2019-03-29 11:48:49', '2088102177530622', '2088102177366276');
INSERT INTO `jy_order` VALUES ('a4beb39dff4e4cadb4f2ea04a30e5552', 1998.00, 1998.00, '4', '1', '2019-03-29 09:42:17', '2019-03-29 09:44:00', '2019-03-29 11:48:25', '2088102177530622', '2088102177366276');
INSERT INTO `jy_order` VALUES ('ae883049aab04dcfbaee0abe41604f92', 9.95, NULL, '5', '1', '2019-03-27 11:16:17', NULL, '2019-03-27 11:18:20', NULL, '');
INSERT INTO `jy_order` VALUES ('c91e3a8132264de5b544f3db3c85fc3c', 176.00, 176.00, '4', '1', '2019-03-28 15:31:37', '2019-03-28 15:32:15', '2019-03-28 15:33:01', '2088102177530622', '2088102177366276');
INSERT INTO `jy_order` VALUES ('de58f5740f7c48ed9e82f4fcaf4b04ee', 50.00, 50.00, '2', '1', '2019-03-28 14:54:44', '2019-03-28 14:55:08', NULL, '2088102177530622', '2088102177603913');
INSERT INTO `jy_order` VALUES ('e64409be5a2a430e99c30b320cc6b391', 49995.00, NULL, '5', '1', '2019-03-29 11:54:09', NULL, '2019-03-29 11:56:49', NULL, '2088102177366276');
INSERT INTO `jy_order` VALUES ('e94158cf4ec44416a2f7b41dee622adb', 8.88, NULL, '1', '1', '2019-03-27 10:03:39', NULL, NULL, NULL, '');
INSERT INTO `jy_order` VALUES ('edcf8ee01f9d4051a9719e17f9303d5d', 22222.00, 22222.00, '2', '1', '2019-03-29 11:52:30', '2019-03-29 11:53:30', NULL, '2088102177530622', '2088102177366276');
INSERT INTO `jy_order` VALUES ('f36aa16779714338840b5244a5f50baf', 166.80, NULL, '5', '1', '2019-03-28 08:36:43', NULL, '2019-03-28 08:38:45', NULL, '');
INSERT INTO `jy_order` VALUES ('fbb4ac7257e848b4a12e4a79bc1bea58', 390.72, 390.72, '2', '1', '2019-03-28 15:00:10', '2019-03-28 15:00:39', NULL, '2088102177530622', '2088102177603913');

-- ----------------------------
-- Table structure for jy_order_item
-- ----------------------------
DROP TABLE IF EXISTS `jy_order_item`;
CREATE TABLE `jy_order_item`  (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(35) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单id',
  `goods_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `price` decimal(20, 2) DEFAULT NULL COMMENT '商品单价',
  `num` int(10) DEFAULT NULL COMMENT '商品购买数量',
  `total_fee` decimal(20, 2) DEFAULT NULL COMMENT '商品总金额',
  `seller_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE,
  INDEX `order_id`(`out_trade_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jy_order_item
-- ----------------------------
INSERT INTO `jy_order_item` VALUES (3, '7bf150c39f5c4e04875b27d6aff9fe05', NULL, '????', 5.00, 2, 10.00, '');
INSERT INTO `jy_order_item` VALUES (4, '5ef6fe5c5af7483e8a96e35f5fcc5d79', NULL, '??', 5.00, 2, 10.00, '');
INSERT INTO `jy_order_item` VALUES (5, '11b910488f594af9a693df002517c770', NULL, '??', 5.00, 2, 10.00, '');
INSERT INTO `jy_order_item` VALUES (6, 'e94158cf4ec44416a2f7b41dee622adb', NULL, '????', 4.44, 2, 8.88, '');
INSERT INTO `jy_order_item` VALUES (7, '65ee196c91e24a72aa976e2dc5abe4c7', NULL, '可乐', 4.44, 2, 8.88, '');
INSERT INTO `jy_order_item` VALUES (8, '99dfb5d132324124ac8c9dd7e435daf6', NULL, '可乐', 4.44, 2, 8.88, '');
INSERT INTO `jy_order_item` VALUES (9, '1ed1031c7ebf4c4a9e36aba634ddd010', NULL, '核桃豆浆', 3.33, 2, 6.66, '');
INSERT INTO `jy_order_item` VALUES (10, '4898d14bde634d46bf9dc7b3ef9d0052', NULL, '测试', 3.44, 3, 10.32, '');
INSERT INTO `jy_order_item` VALUES (11, '96a5cd05159040f88107c484199f3ca9', NULL, '咖啡', 2.87, 3, 8.61, '');
INSERT INTO `jy_order_item` VALUES (12, 'ae883049aab04dcfbaee0abe41604f92', NULL, '可乐1', 1.99, 5, 9.95, '');
INSERT INTO `jy_order_item` VALUES (13, '6218ca12f88047cbbb656a1f65137aaf', NULL, 'yuntest1', 5.69, 2, 11.38, '');
INSERT INTO `jy_order_item` VALUES (14, 'f36aa16779714338840b5244a5f50baf', NULL, '肉包子', 55.60, 3, 166.80, '');
INSERT INTO `jy_order_item` VALUES (15, '2a957447494c4c16afa3bf46134773ab', NULL, '可乐', 4.44, 2, 8.88, '');
INSERT INTO `jy_order_item` VALUES (16, 'de58f5740f7c48ed9e82f4fcaf4b04ee', NULL, '咖啡', 10.00, 5, 50.00, '2088102177603913');
INSERT INTO `jy_order_item` VALUES (17, 'fbb4ac7257e848b4a12e4a79bc1bea58', NULL, '可乐1', 4.44, 88, 390.72, '2088102177603913');
INSERT INTO `jy_order_item` VALUES (18, 'c91e3a8132264de5b544f3db3c85fc3c', NULL, '新测试', 88.00, 2, 176.00, '2088102177366276');
INSERT INTO `jy_order_item` VALUES (21, 'a2f55ed4ae6c47d4964b99e51e1c1ea6', NULL, '测试2', 55.00, 2, 110.00, '2088102177366276');
INSERT INTO `jy_order_item` VALUES (22, 'a4beb39dff4e4cadb4f2ea04a30e5552', NULL, 'ceshi', 999.00, 2, 1998.00, '2088102177366276');
INSERT INTO `jy_order_item` VALUES (23, 'edcf8ee01f9d4051a9719e17f9303d5d', NULL, '大雪碧', 11111.00, 2, 22222.00, '2088102177366276');
INSERT INTO `jy_order_item` VALUES (24, 'e64409be5a2a430e99c30b320cc6b391', NULL, '酷儿', 9999.00, 5, 49995.00, '2088102177366276');
INSERT INTO `jy_order_item` VALUES (25, '407d5c04e81044329a98a53a9a6b8b01', NULL, '鲜奶', 6.00, 10, 60.00, '2088102177366276');
INSERT INTO `jy_order_item` VALUES (26, '1111501169912750080', NULL, '清风', 5555.00, 3, 16665.00, '2088102177366276');
INSERT INTO `jy_order_item` VALUES (27, '1111501485408296960', NULL, '键盘', 888.00, 1, 888.00, '2088102177366276');

-- ----------------------------
-- Table structure for jy_pay_log
-- ----------------------------
DROP TABLE IF EXISTS `jy_pay_log`;
CREATE TABLE `jy_pay_log`  (
  `out_trade_no` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商家订单号',
  `trade_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付订单号',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `pay_time` datetime(0) DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` decimal(20, 2) DEFAULT NULL COMMENT '支付金额（元）',
  `trade_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易状态 0：未支付 1：已支付 2：已退款',
  `pay_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付类型 0：支付宝 1：微信',
  PRIMARY KEY (`out_trade_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jy_pay_log
-- ----------------------------
INSERT INTO `jy_pay_log` VALUES ('1111501169912750080', '2019032922001430621000030872', '2019-03-29 13:31:45', '2019-03-29 13:32:14', 16665.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('1111501485408296960', NULL, '2019-03-29 13:33:00', NULL, 888.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('11b910488f594af9a693df002517c770', NULL, NULL, '2019-03-27 09:50:05', NULL, '1', NULL);
INSERT INTO `jy_pay_log` VALUES ('1ed1031c7ebf4c4a9e36aba634ddd010', NULL, '2019-03-27 10:29:00', '2019-03-27 10:29:31', 7.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('21a51083554f4628a8eee52dde72aea3', NULL, '2019-03-28 15:34:30', NULL, 176.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('2a957447494c4c16afa3bf46134773ab', NULL, '2019-03-28 11:50:45', NULL, 8.88, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('407d5c04e81044329a98a53a9a6b8b01', '2019032922001430621000030871', '2019-03-29 12:00:10', '2019-03-29 12:00:24', 60.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('4898d14bde634d46bf9dc7b3ef9d0052', NULL, '2019-03-27 10:40:42', '2019-03-27 10:40:55', 10.32, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('5ef6fe5c5af7483e8a96e35f5fcc5d79', NULL, '2019-03-27 09:42:45', NULL, 10.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('6218ca12f88047cbbb656a1f65137aaf', '2019032722001430621000027778', '2019-03-27 17:00:08', '2019-03-27 17:00:20', 11.38, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('65ee196c91e24a72aa976e2dc5abe4c7', NULL, '2019-03-27 10:12:53', NULL, 9.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('7bf150c39f5c4e04875b27d6aff9fe05', NULL, '2019-03-27 09:09:04', NULL, 10.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('96a5cd05159040f88107c484199f3ca9', NULL, '2019-03-27 11:11:29', '2019-03-27 11:12:16', 8.61, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('99dfb5d132324124ac8c9dd7e435daf6', NULL, '2019-03-27 10:23:45', NULL, 9.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('a2f55ed4ae6c47d4964b99e51e1c1ea6', '2019032822001430621000027782', '2019-03-28 15:38:34', '2019-03-28 15:38:48', 110.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('a4beb39dff4e4cadb4f2ea04a30e5552', '2019032922001430621000029634', '2019-03-29 09:42:17', '2019-03-29 09:44:00', 1998.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('ae883049aab04dcfbaee0abe41604f92', NULL, '2019-03-27 11:16:18', NULL, 9.95, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('c91e3a8132264de5b544f3db3c85fc3c', '2019032822001430621000027781', '2019-03-28 15:31:37', '2019-03-28 15:32:15', 176.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('de58f5740f7c48ed9e82f4fcaf4b04ee', '2019032822001430621000030868', '2019-03-28 14:54:44', '2019-03-28 14:55:08', 50.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('e64409be5a2a430e99c30b320cc6b391', NULL, '2019-03-29 11:54:09', NULL, 49995.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('e94158cf4ec44416a2f7b41dee622adb', NULL, '2019-03-27 10:03:40', NULL, 9.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('edcf8ee01f9d4051a9719e17f9303d5d', '2019032922001430621000030870', '2019-03-29 11:52:30', '2019-03-29 11:53:30', 22222.00, '1', '0');
INSERT INTO `jy_pay_log` VALUES ('f36aa16779714338840b5244a5f50baf', NULL, '2019-03-28 08:36:43', NULL, 166.80, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('f406b1f4de9143daa545464695ae9f16', NULL, '2019-03-28 15:33:57', NULL, 176.00, '0', '0');
INSERT INTO `jy_pay_log` VALUES ('fbb4ac7257e848b4a12e4a79bc1bea58', '2019032822001430621000027779', '2019-03-28 15:00:10', '2019-03-28 15:00:39', 390.72, '1', '0');

-- ----------------------------
-- Table structure for jy_seller
-- ----------------------------
DROP TABLE IF EXISTS `jy_seller`;
CREATE TABLE `jy_seller`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '商家ID',
  `store_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门店编号',
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商家名称',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '状态 0：未授权 1：已授权',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建日期',
  `linkman_name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人姓名',
  `linkman_mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人电话',
  `linkman_email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人EMAIL',
  `license_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '营业执照号',
  `address` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商家地址',
  `app_auth_token` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权令牌',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jy_seller
-- ----------------------------
INSERT INTO `jy_seller` VALUES (1, '5538ab655b614016ae2e3e1ddf34f82a', '003', '烧麦', '1', '2019-03-28 11:29:20', '小烧麦', '8987', '44455@126.com', '9898aa', '食堂', '201903BBe3779570e7fe435b88d43d1c1381eC91');
INSERT INTO `jy_seller` VALUES (2, '733a219d60014ba397bc056c0f1acea1', '005', '纯品', '1', '2019-03-28 11:50:02', '大大泡泡糖', '1222', '6666@qq.com', '66669999', '上苍', '201903BBc1a23b2b7f354942b2b18d43dc67cE91');
INSERT INTO `jy_seller` VALUES (3, '789fe2c2be5142f1b5d5b02103502b21', '004', '百年孤独', '1', '2019-03-28 11:43:36', '布鲁斯', '238293', '343434@qq.com', '56565aaazz', '月球', '201903BBe1835321151e4fc591a58e4df578cX91');
INSERT INTO `jy_seller` VALUES (4, 'ec357e64-f2ad-4b69-830f-c079a8b32f7f', '002', '伊利', '0', '2019-03-28 11:21:22', '大奶牛', '898989', '221203@163.com', '56454213213', '内蒙古', NULL);
INSERT INTO `jy_seller` VALUES (5, 'fcc108db84404e1fbdbce78e7e2da79a', '007', '咖啡', '1', '2019-03-28 13:30:49', '拉丝', '56632136', '23546@163.com', 'sdjsd21212', '路边摊', '201903BB4551f655da2b4f05b8a6650ae22d6X91');
INSERT INTO `jy_seller` VALUES (6, NULL, '008', '啦啦啦', '0', '2019-03-28 13:33:49', '小妖', '1885695244', '965@163.com', '6546sfd3s', '八卦炉', NULL);
INSERT INTO `jy_seller` VALUES (8, '2088102177603913', '009', '十足', '1', '2019-03-28 13:40:26', '孙大圣', '445645641', '56513@163.com', '646sdsd3', '码头', '201903BB506739cee2b34d6ebd0c2994d51d0F91');
INSERT INTO `jy_seller` VALUES (10, '2088102177366276', '10', 'aaa', '1', '2019-03-28 15:09:04', 'bbb', '555', '666', '222', '666', '201903BB05c842f6b819421db71e78daef03cX27');
INSERT INTO `jy_seller` VALUES (11, '2088102177366276', '6', '猫的天空之城', '1', '2019-03-28 15:30:49', '呜呜', '23218312', '1321323@google.com', '23232555', '喵星球', '201903BBa0d70f5e354b45269b2864277a7d5D27');

SET FOREIGN_KEY_CHECKS = 1;

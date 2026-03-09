/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : upis_demo

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2024-11-07 17:15:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `p_article`
-- ----------------------------
DROP TABLE IF EXISTS `p_article`;
CREATE TABLE `p_article` (
  `id` varchar(255) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `created_dt` datetime(6) DEFAULT NULL,
  `is_public` bit(1) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `url_file` varchar(100) DEFAULT NULL,
  `url_video` varchar(100) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `url_video_cf` varchar(100) DEFAULT NULL,
  `url_video_s3` varchar(100) DEFAULT NULL,
  `summary` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhme9dqyeas0ith4dob0fska0w` (`group_id`),
  CONSTRAINT `FKhme9dqyeas0ith4dob0fska0w` FOREIGN KEY (`group_id`) REFERENCES `p_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_article
-- ----------------------------
INSERT INTO `p_article` VALUES ('01aac192-0936-4736-b1a0-e6911668ff85', '234234', '2024-09-12 13:10:29.000000', '', '0', '23423424', null, null, null, null, null, '1');
INSERT INTO `p_article` VALUES ('08f01f6e-17e1-400e-8ee3-9b4e059a12b2', '\r\nÔng Trump: \'Tôi có màn tranh luận tốt nhất từ trước đến nay\'\r\nÔng Trump nói cuộc đối đầu với bà Harris là màn tranh luận tốt nhất từ trước đến nay của ông, đồng thời cáo buộc người điều phối \"thiên vị\".', '2024-09-11 10:44:27.000000', '', '0', 'Bài 1', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('3332a389-9bc5-4a11-86d8-3c30406d2e4b', 'Theo Giám đốc Trung tâm Khí tượng Thủy văn Quốc gia, với mức lũ như hiện nay, khu vực nội thành, trong đê ít bị ảnh hưởng, vùng ngập chủ yếu là trũng thấp ngoài đê và bãi giữa sông Hồng.', '2024-09-11 10:44:34.000000', '', '0', 'Bài 2', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('55bc75d9-4bd8-429d-9a7d-d785418a4225', 'Cầu Long Biên, Chương Dương, Trung Hà, Vĩnh Phú, Đuống, Phong Châu bị mưa lũ uy hiếp, phải cấm xe để đảm bảo an toàn. ', '2024-09-11 10:44:26.000000', '', '0', 'Bài 3', null, null, null, null, null, '3');
INSERT INTO `p_article` VALUES ('59128ee1-e9ab-4fd0-a557-e753e75eb386', '\r\nChị Văn Thị Dung vừa xúc xẻng cát cuối cùng đổ vào bao, chồng vội buộc túm miệng, ghé vai vác chạy hòa vào hàng trăm người hộ đê dưới chân cầu Bến Tượng, 19h', '2024-09-11 10:44:28.000000', '', '0', 'Bài 4', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('5d73c78e-6352-42f5-9a12-b17c71d441a3', '234234', '2024-09-12 13:09:50.000000', '', '0', '23423424', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('66f6db52-460a-404c-bb4a-7e051c4aedb0', '234234', '2024-09-12 17:43:14.000000', '', '0', '23423424', null, null, null, null, null, null);
INSERT INTO `p_article` VALUES ('a5409bd1-4739-4e99-89f6-5f17557d2e31', '234234', '2024-09-12 13:22:55.000000', '', '0', '23423424', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('b3cf81c5-1038-4157-86be-a3e8f8f685f9', 'Quỹ Hy vọng Báo VnExpress phát động chiến dịch \"Chung tay cùng đồng bào vượt bão\" nhằm hỗ trợ người dân bị ảnh hưởng bởi lũ lụt, sạt lở.', '2024-09-11 10:44:28.000000', '', '0', 'Bài 5', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('b698aed1-ebed-464a-ab59-772c449b1ccc', 'Quỹ Hy vọng Báo VnExpress phát động chiến dịch \"Chung tay cùng đồng bào vượt bão\" nhằm hỗ trợ người dân bị ảnh hưởng bởi lũ lụt, sạt lở.', '2024-09-11 10:44:34.000000', '', '0', 'Bài 6', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('bef48986-bb1d-4e03-b0df-46b13ef913f4', '234234', '2024-09-12 22:59:29.000000', '', '0', '23423424', null, null, null, null, null, null);
INSERT INTO `p_article` VALUES ('c418b61b-a44f-465b-bfd1-839f9bba144d', '234234', '2024-09-12 13:09:59.000000', '', '0', '23423424', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('d1b187f2-7658-4201-9ba0-a8248b18f455', 'Quỹ Hy vọng Báo VnExpress phát động chiến dịch \"Chung tay cùng đồng bào vượt bão\" nhằm hỗ trợ người dân bị ảnh hưởng bởi lũ lụt, sạt lở.', '2024-09-11 10:44:23.000000', '', '0', 'Bài 7', null, null, null, null, null, '2');
INSERT INTO `p_article` VALUES ('d426ad11-e3f1-4ffe-aa88-bf2a1d3cf204', '<p>Bài học số 1</p>', '2024-09-12 23:03:16.000000', '', '0', 'Bài 100', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `p_bookmark`
-- ----------------------------
DROP TABLE IF EXISTS `p_bookmark`;
CREATE TABLE `p_bookmark` (
  `zone_id` varchar(255) NOT NULL,
  `cmu_id` varchar(10) DEFAULT NULL,
  `dit_id` varchar(10) DEFAULT NULL,
  `prv_id` varchar(10) DEFAULT NULL,
  `zone_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_bookmark
-- ----------------------------

-- ----------------------------
-- Table structure for `p_group`
-- ----------------------------
DROP TABLE IF EXISTS `p_group`;
CREATE TABLE `p_group` (
  `group_id` varchar(255) NOT NULL,
  `created_dt` datetime(6) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `user_create` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_group
-- ----------------------------

-- ----------------------------
-- Table structure for `p_my_article`
-- ----------------------------
DROP TABLE IF EXISTS `p_my_article`;
CREATE TABLE `p_my_article` (
  `id` varchar(255) NOT NULL,
  `article_id` varchar(10) DEFAULT NULL,
  `created_dt` datetime(6) DEFAULT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_my_article
-- ----------------------------

-- ----------------------------
-- Table structure for `p_order`
-- ----------------------------
DROP TABLE IF EXISTS `p_order`;
CREATE TABLE `p_order` (
  `order_id` varchar(255) NOT NULL,
  `bill_code` varchar(50) DEFAULT NULL,
  `created_dt` datetime(6) DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `payed_money` float DEFAULT NULL,
  `total` float DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_order
-- ----------------------------

-- ----------------------------
-- Table structure for `p_order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `p_order_detail`;
CREATE TABLE `p_order_detail` (
  `id` varchar(255) NOT NULL,
  `article_id` varchar(10) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiqi6i9m372hdceqihj66bgl6j` (`order_id`),
  CONSTRAINT `FKiqi6i9m372hdceqihj66bgl6j` FOREIGN KEY (`order_id`) REFERENCES `p_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_order_detail
-- ----------------------------

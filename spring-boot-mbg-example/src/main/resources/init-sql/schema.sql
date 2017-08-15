SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `height` int(11) DEFAULT '160' COMMENT '身高',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `age` int(5) unsigned NOT NULL COMMENT '年龄',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `uuid` varchar(50) DEFAULT NULL COMMENT '唯一标识码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
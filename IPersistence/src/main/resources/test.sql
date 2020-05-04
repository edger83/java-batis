SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `testId` int(11) NOT NULL AUTO_INCREMENT,
  `testName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`testId`)
) ENGINE=InnoDB AUTO_INCREMENT=100086 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `test`
-- ----------------------------
BEGIN;
INSERT INTO `test` VALUES ('18', 'time:1585237352249'), ('19', 'zxcvbnm'), ('20', 'zxcvbnm'), ('21', 'zxcvbnm'), ('22', '123456789'), ('23', '123456789'), ('24', '123456789'), ('25', '123456789'), ('26', '123456789'), ('27', '123456789'), ('28', '213'), ('47', '1584605749336'), ('48', '1584605760511'), ('53', 'ddddd'), ('55', 'sddd'), ('56', 'sddd'), ('59', '-2302-32'), ('70', 'sssss'), ('71', 'sssss'), ('72', 'sssss'), ('73', 'sssss'), ('74', 'sssss'), ('75', 'sssss'), ('76', 'sssss'), ('77', 'sssss'), ('78', 'sssss'), ('79', 'sssss'), ('80', 'sssss'), ('81', 'sssss'), ('82', 'sssss'), ('83', 'sssss'), ('84', 'sssss'), ('85', 'name-8'), ('86', 'name-7'), ('87', 'name-6'), ('88', 'name-2'), ('89', 'name-4'), ('90', 'name-3'), ('91', 'name-5'), ('92', 'name-9'), ('93', 'name-1'), ('94', 'name-11'), ('95', 'name-10'), ('96', 'name-0'), ('97', 'name-13'), ('98', 'name-14'), ('99', 'name-12'), ('100', 'name-15');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

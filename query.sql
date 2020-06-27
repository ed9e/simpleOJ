/*
 Navicat MySQL Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : query

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 08/12/2019 21:28:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` int(11) NOT NULL,
  `title` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `level` int(4) NULL DEFAULT NULL,
  `runtime_limit` int(4) NULL DEFAULT NULL,
  `memory_limit` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES (1, '两数之和', '给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。\n\n你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。\n\n示例:\n给定 nums = [2, 7, 11, 15], target = 9\n\n因为 nums[0] + nums[1] = 2 + 7 = 9\n所以返回 [0, 1]', 1, 4096, 65535);
INSERT INTO `problem` VALUES (2, ' 删除排序数组中的重复项', '给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，\n返回移除后数组的新长度。\n\n不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。\n\n示例:\n\n给定数组 nums = [1,1,2], 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。 \n\n你不需要考虑数组中超出新长度后面的元素。', 1, 4096, 65535);
INSERT INTO `problem` VALUES (3, '有序数组的平方', '给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。\n输入的第一个数字为数组的元素个数\n示例 1：\n\n输入：5 -4 -1 0 3 10\n输出：0 1 9 16 100\n\n示例 2：\n\n输入：5 -7 -3 2 3 11\n输出：4 9 9 49 121', 1, 4096, 65535);

-- ----------------------------
-- Table structure for submission
-- ----------------------------
DROP TABLE IF EXISTS `submission`;
CREATE TABLE `submission`  (
  `submission_id` int(11) NOT NULL,
  `userid` int(4) NOT NULL,
  `problem_id` int(11) NOT NULL,
  `language` int(4) NOT NULL,
  `code` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `result` int(4) NULL DEFAULT NULL,
  `info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `runtime` int(11) NULL DEFAULT NULL,
  `memory` int(11) NULL DEFAULT NULL,
  `input` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `output` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `expected` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testcase
-- ----------------------------
DROP TABLE IF EXISTS `testcase`;
CREATE TABLE `testcase`  (
  `id` int(11) NOT NULL,
  `case_id` int(11) NULL DEFAULT NULL,
  `input` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `output` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testcase
-- ----------------------------
INSERT INTO `testcase` VALUES (1, 1, '4 \r\n2 7 11 15\r\n 9', '0 1');
INSERT INTO `testcase` VALUES (1, 2, '4 \r\n2 7 11 15\r\n 13', '0 2');
INSERT INTO `testcase` VALUES (2, 1, '0', '0');
INSERT INTO `testcase` VALUES (2, 2, '0', '0');
INSERT INTO `testcase` VALUES (3, 1, '5 -4 -1 0 3 10', '0 1 9 16 100');
INSERT INTO `testcase` VALUES (3, 2, '5 -7 -3 2 3 11', '4 9 9 49 121');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userid` int(4) NOT NULL,
  PRIMARY KEY (`userid`, `username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', 'admin', 1);
INSERT INTO `user` VALUES ('test', '123456', 2);
INSERT INTO `user` VALUES ('ed9e', 'a123456', 3);

SET FOREIGN_KEY_CHECKS = 1;

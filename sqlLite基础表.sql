/*
Navicat SQLite Data Transfer

Source Server         : sqLite
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-04-24 15:22:33
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for SYS_MENU
-- ----------------------------
DROP TABLE IF EXISTS "main"."SYS_MENU";
CREATE TABLE "SYS_MENU" (
"ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"CONTEXT"  TEXT,
"URL_CONTEXT"  TEXT,
"MODULE"  TEXT,
"DEL"  INTEGER,
"PID"  INTEGER,
"GREAD"  INTEGER,
"HASCHILDS"  INTEGER
);

-- ----------------------------
-- Records of SYS_MENU
-- ----------------------------
INSERT INTO "main"."SYS_MENU" VALUES (1, '二维码功能', null, 'M', 0, null, 1, 1);
INSERT INTO "main"."SYS_MENU" VALUES (2, '批量生成二维码', null, 'M', 0, 1, 1, 0);

-- ----------------------------
-- Table structure for UTILS_LOGGING
-- ----------------------------
DROP TABLE IF EXISTS "main"."UTILS_LOGGING";
CREATE TABLE "UTILS_LOGGING" (
"ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"IP"  TEXT,
"USER"  TEXT,
"OPERATION"  TEXT,
"GRADE"  INTEGER,
"CONTENT"  TEXT,
"REMARK"  TEXT,
"CREATETIME"  TEXT
);

-- ----------------------------
-- Records of UTILS_LOGGING
-- ----------------------------

-- ----------------------------
-- Indexes structure for table UTILS_LOGGING
-- ----------------------------
CREATE INDEX "main"."grade"
ON "UTILS_LOGGING" ("GRADE" ASC);
CREATE INDEX "main"."timeSeq"
ON "UTILS_LOGGING" ("CREATETIME" ASC);

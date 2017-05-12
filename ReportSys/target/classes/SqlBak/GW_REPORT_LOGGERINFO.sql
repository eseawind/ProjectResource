/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.1.0

Source Server         : ProdUAT
Source Server Version : 110200
Source Host           : 10.1.245.54:1521
Source Schema         : PRODUAT

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2017-05-12 10:01:01
*/


-- ----------------------------
-- Table structure for GW_REPORT_LOGGERINFO
-- ----------------------------
DROP TABLE GW_REPORT_LOGGERINFO;
CREATE TABLE GW_REPORT_LOGGERINFO(
"ID" VARCHAR2(50 BYTE) NOT NULL ,
"LEVEL_CODE" VARCHAR2(255 BYTE) NULL ,
"MODULE_CODE" VARCHAR2(255 BYTE) NULL ,
"DOUSER" VARCHAR2(255 BYTE) NULL ,
"CREATE_TIME" VARCHAR2(23 BYTE) NULL ,
"NOTE_TEXT2" VARCHAR2(4000 BYTE) NULL ,
"NOTE_TEXT" VARCHAR2(4000 BYTE) NULL 
)
;

-- ----------------------------
-- Indexes structure for table GW_REPORT_LOGGERINFO
-- ----------------------------

-- ----------------------------
-- Checks structure for table GW_REPORT_LOGGERINFO
-- ----------------------------
ALTER TABLE GW_REPORT_LOGGERINFO ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table GW_REPORT_LOGGERINFO
-- ----------------------------
ALTER TABLE GW_REPORT_LOGGERINFO ADD PRIMARY KEY ("ID");

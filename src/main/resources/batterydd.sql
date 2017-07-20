/*
 Navicat MySQL Data Transfer

 Source Server         : dd
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 10.13.54.246:3306
 Source Schema         : batterydd

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 20/07/2017 09:25:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CELLPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ID_NUMBER` varchar(18) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ROLE` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ADMIN_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `ADMIN_ID_NUMBER_uindex`(`ID_NUMBER`) USING BTREE,
  UNIQUE INDEX `ADMIN_EMAIL_uindex`(`EMAIL`) USING BTREE,
  UNIQUE INDEX `ADMIN_CELLPHONE_uindex`(`CELLPHONE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AREA_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `AREA_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CITY_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`AREA_CODE`) USING BTREE,
  UNIQUE INDEX `AREA_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `AREA_AREA_CODE_uindex`(`AREA_CODE`) USING BTREE,
  INDEX `area_city_CITY_CODE_fk`(`CITY_CODE`) USING BTREE,
  CONSTRAINT `area_city_CITY_CODE_fk` FOREIGN KEY (`CITY_CODE`) REFERENCES `city` (`CITY_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3132 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for batteery
-- ----------------------------
DROP TABLE IF EXISTS `batteery`;
CREATE TABLE `batteery`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MANUFACTURER_ID` int(11) NOT NULL,
  `WARRENTY_PERIOD` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `BATTEERY_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `BATTEERY_MANUFACTURER_ID_uindex`(`MANUFACTURER_ID`) USING BTREE,
  CONSTRAINT `batteery_manufacturer_ID_fk` FOREIGN KEY (`MANUFACTURER_ID`) REFERENCES `manufacturer` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for battery_price
-- ----------------------------
DROP TABLE IF EXISTS `battery_price`;
CREATE TABLE `battery_price`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BATTERY_ID` int(11) NOT NULL,
  `CITY_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `PRICE` decimal(5, 2) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `BATTERY_PRICE_ID_uindex`(`ID`) USING BTREE,
  INDEX `battery_price_batteery_ID_fk`(`BATTERY_ID`) USING BTREE,
  INDEX `battery_price_city_CITY_CODE_fk`(`CITY_CODE`) USING BTREE,
  CONSTRAINT `battery_price_batteery_ID_fk` FOREIGN KEY (`BATTERY_ID`) REFERENCES `batteery` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `battery_price_city_CITY_CODE_fk` FOREIGN KEY (`CITY_CODE`) REFERENCES `city` (`CITY_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for battery_stock
-- ----------------------------
DROP TABLE IF EXISTS `battery_stock`;
CREATE TABLE `battery_stock`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BATTERY_ID` int(11) NOT NULL,
  `CITY_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `INVERTORY` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `battery_stock_BATTERY_ID_uindex`(`BATTERY_ID`) USING BTREE,
  UNIQUE INDEX `battery_stock_ID_uindex`(`ID`) USING BTREE,
  INDEX `battery_stock_city_CITY_CODE_fk`(`CITY_CODE`) USING BTREE,
  CONSTRAINT `battery_stock_batteery_ID_fk` FOREIGN KEY (`BATTERY_ID`) REFERENCES `batteery` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `battery_stock_city_CITY_CODE_fk` FOREIGN KEY (`CITY_CODE`) REFERENCES `city` (`CITY_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CITY_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CITY_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `PROVINCE_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`CITY_CODE`) USING BTREE,
  UNIQUE INDEX `CITY_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `CITY_CITY_CODE_uindex`(`CITY_CODE`) USING BTREE,
  INDEX `city_province_PROVINCE_CODE_fk`(`PROVINCE_CODE`) USING BTREE,
  CONSTRAINT `city_province_PROVINCE_CODE_fk` FOREIGN KEY (`PROVINCE_CODE`) REFERENCES `province` (`PROVINCE_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 345 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PASSWORD` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SEX` varchar(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CELLPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TELEPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `POSTCODE` varchar(7) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CUSTOMER_ID_uindex`(`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for manufacturer
-- ----------------------------
DROP TABLE IF EXISTS `manufacturer`;
CREATE TABLE `manufacturer`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CONTACT_ID` int(11) NOT NULL,
  `CONTACT_NUMBER` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `MANUFACTURER_ID_uindex`(`ID`) USING BTREE,
  INDEX `manufacturer_staff_ID_fk`(`CONTACT_ID`) USING BTREE,
  CONSTRAINT `manufacturer_staff_ID_fk` FOREIGN KEY (`CONTACT_ID`) REFERENCES `staff` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for motocycle_type
-- ----------------------------
DROP TABLE IF EXISTS `motocycle_type`;
CREATE TABLE `motocycle_type`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `BATTERY_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `MOTOCYCLE_TYPE_ID_uindex`(`ID`) USING BTREE,
  INDEX `motocycle_type_batteery_ID_fk`(`BATTERY_ID`) USING BTREE,
  CONSTRAINT `motocycle_type_batteery_ID_fk` FOREIGN KEY (`BATTERY_ID`) REFERENCES `batteery` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BATTERY_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CUSTOMER_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CUSTOMER_CELLPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CUSTOMER_TELEPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MOTORCYCLE_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MOTORCYCLE_NUMBER` varchar(7) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `CITY_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATE_TIME` datetime(0) NOT NULL,
  `FINISH_TIME` datetime(0) NOT NULL,
  `STATUS` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ORDER_ID_uindex`(`ID`) USING BTREE,
  INDEX `order_city_CITY_CODE_fk`(`CITY_CODE`) USING BTREE,
  CONSTRAINT `order_city_CITY_CODE_fk` FOREIGN KEY (`CITY_CODE`) REFERENCES `city` (`CITY_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_allocation
-- ----------------------------
DROP TABLE IF EXISTS `order_allocation`;
CREATE TABLE `order_allocation`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` int(11) NOT NULL,
  `TECHNICIAN_ID` int(11) NOT NULL,
  `ACCEPT_TIME` datetime(0) DEFAULT NULL,
  `TECHNICIAN_LONGITUDE` decimal(6, 3) DEFAULT NULL,
  `TECHNICIAN_LATITUDE` decimal(6, 2) DEFAULT NULL,
  `CUSTOMER_LONGITUDE` decimal(6, 3) DEFAULT NULL,
  `CUSTOMER_LATITUDE` decimal(6, 2) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ORDER_ALLOCATION_ID_uindex`(`ID`) USING BTREE,
  INDEX `order_allocation_technician_ID_fk`(`TECHNICIAN_ID`) USING BTREE,
  INDEX `order_allocation_order_ID_fk`(`ORDER_ID`) USING BTREE,
  CONSTRAINT `order_allocation_order_ID_fk` FOREIGN KEY (`ORDER_ID`) REFERENCES `order` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_allocation_technician_ID_fk` FOREIGN KEY (`TECHNICIAN_ID`) REFERENCES `technician` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `order_evaluation`;
CREATE TABLE `order_evaluation`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` int(11) NOT NULL,
  `TECHNICIAN_ID` int(11) NOT NULL,
  `TIME` datetime(0) NOT NULL,
  `SERVICE_ATTITUDE` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `RESPONSE_SPEED` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `PROFICIENCY` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ORDER_EVALUATION_ID_uindex`(`ID`) USING BTREE,
  INDEX `order_evaluation_order_ID_fk`(`ORDER_ID`) USING BTREE,
  INDEX `order_evaluation_technician_ID_fk`(`TECHNICIAN_ID`) USING BTREE,
  CONSTRAINT `order_evaluation_order_ID_fk` FOREIGN KEY (`ORDER_ID`) REFERENCES `order` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_evaluation_technician_ID_fk` FOREIGN KEY (`TECHNICIAN_ID`) REFERENCES `technician` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `STAFF_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ORGANIZATION_ID_uindex`(`ID`) USING BTREE,
  INDEX `organization_staff_ID_fk`(`STAFF_ID`) USING BTREE,
  CONSTRAINT `organization_staff_ID_fk` FOREIGN KEY (`STAFF_ID`) REFERENCES `staff` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROVINCE_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `PROVINCE_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`PROVINCE_CODE`) USING BTREE,
  UNIQUE INDEX `PROVINCE_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `PROVINCE_PROVINCE_CODE_uindex`(`PROVINCE_CODE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for receiving_confirmation
-- ----------------------------
DROP TABLE IF EXISTS `receiving_confirmation`;
CREATE TABLE `receiving_confirmation`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` int(11) NOT NULL,
  `TECHNICIAN_ID` int(11) NOT NULL,
  `LICENSE_PLATE_NUMBER_IMG` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `QUALITY_ASSURANCE_IMG` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `BATTERY_IMG` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TIME` datetime(0) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `RECEIVING_CONFIRMATION_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `RECEIVING_CONFIRMATION_ORDER_ID_uindex`(`ORDER_ID`) USING BTREE,
  UNIQUE INDEX `RECEIVING_CONFIRMATION_LICENSE_PLATE_NUMBER_IMG_uindex`(`LICENSE_PLATE_NUMBER_IMG`) USING BTREE,
  UNIQUE INDEX `RECEIVING_CONFIRMATION_QUALITY_ASSURANCE_IMG_uindex`(`QUALITY_ASSURANCE_IMG`) USING BTREE,
  UNIQUE INDEX `RECEIVING_CONFIRMATION_BATTERY_IMG_uindex`(`BATTERY_IMG`) USING BTREE,
  INDEX `receiving_confirmation_technician_ID_fk`(`TECHNICIAN_ID`) USING BTREE,
  CONSTRAINT `receiving_confirmation_order_ID_fk` FOREIGN KEY (`ORDER_ID`) REFERENCES `order` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `receiving_confirmation_technician_ID_fk` FOREIGN KEY (`TECHNICIAN_ID`) REFERENCES `technician` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SEX` varchar(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CELLPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TELEPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ID_NUMBER` varchar(18) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ROLE` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ORGANIZATION_ID` int(11) NOT NULL,
  `PASSWORD` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `STAFF_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `STAFF_CELLPHONE_uindex`(`CELLPHONE`) USING BTREE,
  UNIQUE INDEX `STAFF_EMAIL_uindex`(`EMAIL`) USING BTREE,
  UNIQUE INDEX `STAFF_ID_NUMBER_uindex`(`ID_NUMBER`) USING BTREE,
  UNIQUE INDEX `STAFF_TELEPHONE_uindex`(`TELEPHONE`) USING BTREE,
  INDEX `staff_organization_ID_fk`(`ORGANIZATION_ID`) USING BTREE,
  CONSTRAINT `staff_organization_ID_fk` FOREIGN KEY (`ORGANIZATION_ID`) REFERENCES `organization` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for technician
-- ----------------------------
DROP TABLE IF EXISTS `technician`;
CREATE TABLE `technician`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SEX` varchar(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CELLPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TELEPHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ID_NUMBER` varchar(18) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `LICENSE_PLATE_NUMBER` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ORGANIZATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `TECHNICIAN_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `TECHNICIAN_CELLPHONE_uindex`(`CELLPHONE`) USING BTREE,
  UNIQUE INDEX `TECHNICIAN_EMAIL_uindex`(`EMAIL`) USING BTREE,
  UNIQUE INDEX `TECHNICIAN_ID_NUMBER_uindex`(`ID_NUMBER`) USING BTREE,
  UNIQUE INDEX `TECHNICIAN_LICENSE_PLATE_NUMBER_uindex`(`LICENSE_PLATE_NUMBER`) USING BTREE,
  INDEX `technician_organization_ID_fk`(`ORGANIZATION_ID`) USING BTREE,
  CONSTRAINT `technician_organization_ID_fk` FOREIGN KEY (`ORGANIZATION_ID`) REFERENCES `organization` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for technician_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `technician_evaluation`;
CREATE TABLE `technician_evaluation`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TECHNICIAN_ID` int(11) NOT NULL,
  `TIME` datetime(0) NOT NULL,
  `AVERAGE` decimal(3, 2) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `TECHNICIAN_EVALUATION_ID_uindex`(`ID`) USING BTREE,
  UNIQUE INDEX `TECHNICIAN_EVALUATION_TECHNICIAN_ID_uindex`(`TECHNICIAN_ID`) USING BTREE,
  CONSTRAINT `technician_evaluation_technician_ID_fk` FOREIGN KEY (`TECHNICIAN_ID`) REFERENCES `technician` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

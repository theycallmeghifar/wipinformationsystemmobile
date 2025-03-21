-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: wipinformationsystem
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.27-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `box`
--

DROP TABLE IF EXISTS `box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `box` (
  `boxCode` varchar(50) NOT NULL,
  `boxColor` varchar(20) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `usageStatus` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`boxCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `box`
--

LOCK TABLES `box` WRITE;
/*!40000 ALTER TABLE `box` DISABLE KEYS */;
INSERT INTO `box` VALUES ('BOX-00001','Merah',180,1,1,'wip','2025-02-10 08:03:17','wip','2025-03-19 11:38:56'),('BOX-00002','Kuning',180,1,1,'wip','2025-02-10 09:54:13','wip','2025-03-19 11:38:56'),('BOX-00003','Hijau',180,1,1,'wip','2025-02-10 09:54:46','wip','2025-03-19 11:38:56'),('BOX-00004','Biru',180,0,1,'wip','2025-02-10 09:55:06','wip','2025-03-19 11:38:56'),('BOX-00005','Merah',180,0,1,'wip','2025-02-10 09:55:16','wip','2025-03-19 11:38:56'),('BOX-00006','Kuning',120,0,1,'wip','2025-03-18 14:53:01','wip','2025-03-19 11:38:56'),('BOX-00007','Biru',180,0,1,'wip','2025-03-19 12:10:12',NULL,NULL),('BOX-00008','Merah',180,0,1,'wip','2025-03-19 12:10:12',NULL,NULL);
/*!40000 ALTER TABLE `box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `itemCode` varchar(50) NOT NULL,
  `itemName` varchar(20) DEFAULT NULL,
  `cavity` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`itemCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('CDLPA-0LBVALVE-002','LABEL VALVE 1S7','Cavity A',1,'wip','2025-02-10 08:38:05','wip','2025-03-19 10:59:14'),('CDLPA-0LBVALVE-003','LABEL VALVE 5D9','Cavity A',1,'wip','2025-02-10 09:51:07','wip','2025-03-19 09:27:56'),('CDLPA-0LBVALVE-004','LABEL VALVE 5MX','Cavity C',1,'wip','2025-02-10 09:51:28','wip','2025-03-19 09:27:56'),('CDLPA-0LBVALVE-005','LABEL VALVE GN5','Cavity D',1,'wip','2025-02-10 09:51:46','wip','2025-03-19 09:27:58'),('CDLPA-0LBVALVE-006	','LABEL VALVE K56	','Cavity E',1,'wip','2025-02-10 09:52:03','wip','2025-02-10 14:58:59'),('CDLPA-0LBVALVE-006','LABEL VALVE K56','Cavity E',1,'wip','2025-03-19 11:02:53',NULL,NULL),('CDLPA-0LBVALVE-007','LABEL VALVE KBB','Cavity F',1,'wip','2025-02-10 13:57:44','wip','2025-03-19 09:27:59'),('CDLPA-0LBVALVE-008','LABEL VALVE KGH','Cavity G',1,'wip','2025-02-10 13:58:00','wip','2025-03-19 09:27:59'),('CDLPA-0LBVALVE-009','LABEL VALVE KVY','Cavity H',1,'wip','2025-02-10 13:58:14','wip','2025-03-19 09:27:59'),('CDLPA-0LBVALVE-010','LABEL VALVE KZY','Cavity I',1,'wip','2025-02-10 13:58:38','wip','2025-03-19 09:28:00'),('CDLPA-0LBVALVE-011','LABEL VALVE SAT','Cavity J',1,'wip','2025-02-10 13:58:57','wip','2025-03-19 09:28:00'),('CDLPA-0LBVALVE-012','LABEL VALVE RDA','Cavity B',1,'wip','2025-03-19 08:49:56','wip','2025-03-19 09:28:00'),('CDLPA-0LBVALVE-013','LABEL VALVE MAG','Cavity Z',1,'wip','2025-03-19 09:20:56','wip','2025-03-19 09:31:58'),('CDLPA-0LBVALVE-014','LABEL VALVE RER','Cavity U',1,'wip','2025-03-19 10:12:35','wip','2025-03-19 11:00:31'),('CDLPA-0LBVALVE-015','LABEL VALVE PAR','Cavity B',1,'wip','2025-03-19 11:02:53','wip','2025-03-19 11:04:00'),('CDLPA-0LBVALVE-016','LABEL VALVE HAA','Cavity A',1,'wip','2025-03-19 11:05:04','wip','2025-03-19 11:14:49'),('CDLPA-0LBVALVE-017','LABEL VALVE DKA','Cavity C',1,'wip','2025-03-19 11:14:49',NULL,NULL);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `locationId` int(11) NOT NULL AUTO_INCREMENT,
  `area` varchar(45) DEFAULT NULL,
  `line` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`locationId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Finishing','Finishing',1,NULL,'2025-02-10 10:35:39','','2025-03-19 14:17:07'),(2,'Machining','Line 1',1,'wip','2025-02-10 10:35:39','','2025-03-19 14:17:07'),(3,'Machining','Line 2',1,'wip','2025-02-10 10:36:16','','2025-03-19 14:17:07'),(4,'Machining','Line 3',0,'wip','2025-02-10 11:23:44','','2025-03-19 14:17:07'),(5,'Machining','Line 4',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(6,'Machining','Line 5',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(7,'WIP','Jalur A',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(8,'WIP','Jalur C',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(9,'WIP','Jalur D',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(10,'WIP','Jalur E',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(11,'WIP','Jalur F',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(12,'WIP','Jalur G',1,'wip','2025-02-10 11:24:57','','2025-03-19 14:17:07'),(13,'WIP','Jalur H',1,'wip','2025-03-19 10:40:07','wip','2025-03-19 14:29:32'),(14,'WIP','Jalur B',1,'wip','2025-03-19 15:03:54','wip','2025-03-19 15:05:34'),(15,'Machining','Line ',1,'wip','2025-03-19 15:05:47',NULL,NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `itemCode` varchar(50) DEFAULT NULL,
  `cavity` varchar(45) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `locationId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,'CDLPA-0LBVALVE-002',NULL,100,2,0,'wip',NULL,NULL,NULL),(12,NULL,'Cavity A',100,2,2,'mc','2025-03-17 08:57:16','wip','2025-03-17 13:56:22'),(13,NULL,'Cavity B',100,2,1,'mc','2025-03-17 08:58:57','wip','2025-03-17 15:10:05'),(14,'CDLPA-0LBVALVE-002','Cavity A',10,4,0,'mc','2025-03-20 10:50:24',NULL,NULL),(18,'CDLPA-0LBVALVE-002','Cavity A',5,3,0,'mc','2025-03-20 14:50:57',NULL,NULL),(19,'CDLPA-0LBVALVE-002','Cavity A',5,2,0,'mc','2025-03-20 15:03:30',NULL,NULL);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'wip','wipAdmin',1,1),(2,'mc','mcAdmin',2,1),(3,'fn','fnAdmin',3,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wip_box`
--

DROP TABLE IF EXISTS `wip_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wip_box` (
  `wipBoxId` int(11) NOT NULL AUTO_INCREMENT,
  `boxCode` varchar(50) DEFAULT NULL,
  `locationId` int(11) DEFAULT NULL,
  `wipLineNumber` int(11) DEFAULT NULL,
  `stack` int(11) DEFAULT NULL,
  `productionDate` date DEFAULT NULL,
  `cavity` varchar(50) DEFAULT NULL,
  `orderStatus` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`wipBoxId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wip_box`
--

LOCK TABLES `wip_box` WRITE;
/*!40000 ALTER TABLE `wip_box` DISABLE KEYS */;
INSERT INTO `wip_box` VALUES (9,'BOX-00001',7,1,1,'2025-03-13','Cavity A',1,1,'fn','2025-03-13 14:53:23',NULL,NULL),(10,'BOX-00002',7,1,NULL,'2025-03-13','Cavity B',1,1,'fn','2025-03-13 15:02:16',NULL,NULL),(11,'BOX-00003',7,1,2,'2025-03-10','Cavity C',0,1,'fn','2025-03-17 09:06:59','wip','2025-03-21 13:07:38'),(12,'BOX-00004',1,0,0,'2025-03-17','Cavity C',0,1,'fn','2025-03-17 09:08:35','wip','2025-03-21 10:51:22');
/*!40000 ALTER TABLE `wip_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wip_box_detail`
--

DROP TABLE IF EXISTS `wip_box_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wip_box_detail` (
  `wipBoxDetailId` int(11) NOT NULL AUTO_INCREMENT,
  `wipBoxId` int(11) DEFAULT NULL,
  `itemCode` varchar(50) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`wipBoxDetailId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wip_box_detail`
--

LOCK TABLES `wip_box_detail` WRITE;
/*!40000 ALTER TABLE `wip_box_detail` DISABLE KEYS */;
INSERT INTO `wip_box_detail` VALUES (10,9,'CDLPA-0LBVALVE-002',200,'fn','2025-03-13 14:53:23',NULL,NULL),(11,10,'CDLPA-0LBVALVE-002',200,'fn','2025-03-13 15:02:16',NULL,NULL),(12,11,'CDLPA-0LBVALVE-004',200,'fn','2025-03-17 09:08:35',NULL,NULL);
/*!40000 ALTER TABLE `wip_box_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-21 13:16:44

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
INSERT INTO `box` VALUES ('BOX-00001','Merah',180,1,1,'wip','2025-02-10 08:03:17','wip','2025-02-10 09:53:54'),('BOX-00002','Kuning',180,0,1,'wip','2025-02-10 09:54:13',NULL,NULL),('BOX-00003','Hijau',180,0,1,'wip','2025-02-10 09:54:46',NULL,NULL),('BOX-00004','Biru',180,0,1,'wip','2025-02-10 09:55:06',NULL,NULL),('BOX-00005','Merah',180,0,1,'wip','2025-02-10 09:55:16',NULL,NULL);
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
INSERT INTO `item` VALUES ('CDLPA-0LBVALVE-002','LABEL VALVE 1S7','A',1,'wip','2025-02-10 08:38:05','wip','2025-02-10 14:58:40'),('CDLPA-0LBVALVE-003','LABEL VALVE 5D9','B',1,'wip','2025-02-10 09:51:07','wip','2025-02-10 14:58:45'),('CDLPA-0LBVALVE-004','LABEL VALVE 5MX','C',1,'wip','2025-02-10 09:51:28','wip','2025-02-10 14:58:53'),('CDLPA-0LBVALVE-005','LABEL VALVE GN5	','D',1,'wip','2025-02-10 09:51:46','wip','2025-02-10 14:58:56'),('CDLPA-0LBVALVE-006	','LABEL VALVE K56	','E',1,'wip','2025-02-10 09:52:03','wip','2025-02-10 14:58:59'),('CDLPA-0LBVALVE-007','LABEL VALVE KBB','F',1,'wip','2025-02-10 13:57:44',NULL,NULL),('CDLPA-0LBVALVE-008','LABEL VALVE KGH','G',1,'wip','2025-02-10 13:58:00',NULL,NULL),('CDLPA-0LBVALVE-009','LABEL VALVE KVY','H',1,'wip','2025-02-10 13:58:14',NULL,NULL),('CDLPA-0LBVALVE-010','LABEL VALVE KZY','I',1,'wip','2025-02-10 13:58:38',NULL,NULL),('CDLPA-0LBVALVE-011','LABEL VALVE SAT','J',1,'wip','2025-02-10 13:58:57',NULL,NULL);
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
  `location` varchar(45) DEFAULT NULL,
  `line` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`locationId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Finishing',NULL,1,NULL,'2025-02-10 10:35:39',NULL,NULL),(2,'Machining','Line 1',1,'wip','2025-02-10 10:35:39','wip','2025-02-10 11:19:01'),(3,'Machining','Line 2',1,'wip','2025-02-10 10:36:16','','2025-02-10 11:18:55'),(4,'Machining','Line 3',0,'wip','2025-02-10 11:23:44','wip','2025-02-10 11:25:37'),(5,'Machining','Line 4',1,'wip','2025-02-10 11:24:57',NULL,NULL),(6,'Machining','Line 5',1,'wip','2025-02-10 11:24:57',NULL,NULL),(7,'WIP','Jalur A',1,'wip','2025-02-10 11:24:57',NULL,NULL),(8,'WIP','Jalur C',1,'wip','2025-02-10 11:24:57',NULL,NULL),(9,'WIP','Jalur D',1,'wip','2025-02-10 11:24:57',NULL,NULL),(10,'WIP','Jalur E',1,'wip','2025-02-10 11:24:57',NULL,NULL),(11,'WIP','Jalur F',1,'wip','2025-02-10 11:24:57',NULL,NULL),(12,'WIP','Jalur G',1,'wip','2025-02-10 11:24:57',NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,'CDLPA-0LBVALVE-002','A',100,2,2,'mc','2025-03-12 09:54:36',NULL,NULL),(2,'CDLPA-0LBVALVE-002','A',100,3,1,'mc','2025-03-12 10:07:12',NULL,NULL),(3,'CDLPA-0LBVALVE-003','A',100,2,1,'mc','2025-03-12 10:08:25',NULL,NULL),(4,'CDLPA-0LBVALVE-003','B',100,3,1,'mc','2025-03-12 10:20:25',NULL,NULL);
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
  `stack` int(11) DEFAULT NULL,
  `productionDate` date DEFAULT NULL,
  `cavity` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createdBy` varchar(45) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`wipBoxId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wip_box`
--

LOCK TABLES `wip_box` WRITE;
/*!40000 ALTER TABLE `wip_box` DISABLE KEYS */;
INSERT INTO `wip_box` VALUES (4,'BOX-00001',4,1,'2025-03-12','A',1,NULL,NULL,NULL,NULL),(5,'BOX-00002',4,2,'2025-03-12','B',1,NULL,NULL,NULL,NULL),(6,'BOX-00003',4,3,'2025-03-12','C',1,NULL,NULL,NULL,NULL),(7,'BOX-00004',4,4,'2025-03-12','A',1,NULL,NULL,NULL,NULL),(8,'BOX-00005',4,5,'2025-03-12','C',1,NULL,NULL,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wip_box_detail`
--

LOCK TABLES `wip_box_detail` WRITE;
/*!40000 ALTER TABLE `wip_box_detail` DISABLE KEYS */;
INSERT INTO `wip_box_detail` VALUES (5,4,'CDLPA-0LBVALVE-002',100,NULL,NULL,NULL,NULL),(6,5,'CDLPA-0LBVALVE-003',120,NULL,NULL,NULL,NULL),(7,6,'CDLPA-0LBVALVE-004',100,NULL,NULL,NULL,NULL),(8,7,'CDLPA-0LBVALVE-002',120,NULL,NULL,NULL,NULL),(9,8,'CDLPA-0LBVALVE-004',100,NULL,NULL,NULL,NULL);
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

-- Dump completed on 2025-03-12 15:06:44

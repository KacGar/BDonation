-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: blooddonation
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `pobranie_krwi`
--

DROP TABLE IF EXISTS `pobranie_krwi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pobranie_krwi` (
  `idpobranie_krwi` int NOT NULL AUTO_INCREMENT,
  `id_pacjenta` int NOT NULL,
  `data_pobrania` date NOT NULL,
  `czas_pobrania` time DEFAULT NULL,
  `ilosc_krwi` int NOT NULL,
  `grupa_krwi` varchar(8) NOT NULL,
  `zarejestrowany_w_banku` tinyint DEFAULT NULL,
  PRIMARY KEY (`idpobranie_krwi`),
  UNIQUE KEY `idpobranie_krwi_UNIQUE` (`idpobranie_krwi`),
  KEY `idpacjenta_idx` (`id_pacjenta`),
  CONSTRAINT `idpacjenta` FOREIGN KEY (`id_pacjenta`) REFERENCES `pacjent` (`idpacjent`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pobranie_krwi`
--

LOCK TABLES `pobranie_krwi` WRITE;
/*!40000 ALTER TABLE `pobranie_krwi` DISABLE KEYS */;
INSERT INTO `pobranie_krwi` VALUES (1,23,'2023-05-29','23:35:46',450,'AB-',1),(2,24,'2023-05-18','10:32:45',440,'A-',1),(3,25,'2023-05-18','11:18:32',450,'B',1),(4,26,'2023-05-18','11:41:11',440,'AB',1),(5,27,'2023-05-19','12:26:56',450,'0RH-',1),(6,28,'2023-05-19','12:52:05',450,'A',1),(7,36,'2023-05-26','03:49:57',420,'0+',1),(8,37,'2023-05-29','23:39:23',430,'B+',1),(9,38,'2023-05-30','01:40:47',450,'0+',1);
/*!40000 ALTER TABLE `pobranie_krwi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-08 13:56:17

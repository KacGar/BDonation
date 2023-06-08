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
-- Table structure for table `pacjent`
--

DROP TABLE IF EXISTS `pacjent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pacjent` (
  `idpacjent` int NOT NULL AUTO_INCREMENT,
  `social_num` varchar(20) DEFAULT NULL,
  `imie_pacjenta` varchar(45) NOT NULL,
  `nazwisko_pacjenta` varchar(45) NOT NULL,
  `wiek` int DEFAULT NULL,
  `plec` varchar(1) DEFAULT NULL,
  `waga` int DEFAULT NULL,
  `numer_tel` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `adres` int DEFAULT NULL,
  PRIMARY KEY (`idpacjent`),
  UNIQUE KEY `idpacjent_UNIQUE` (`idpacjent`),
  UNIQUE KEY `numer_tel_UNIQUE` (`numer_tel`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `adres_idx` (`adres`),
  CONSTRAINT `adres` FOREIGN KEY (`adres`) REFERENCES `adres_pacjenta` (`idadres`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacjent`
--

LOCK TABLES `pacjent` WRITE;
/*!40000 ALTER TABLE `pacjent` DISABLE KEYS */;
INSERT INTO `pacjent` VALUES (23,'92120510236','JAN','KOWALSKI',35,'M',80,'123456789','myemail@host.com',1),(24,'99060375412','ANNA','KOWALSKA',23,'K',75,'369514872','email2@hostname.com',1),(25,'91042756400','JACEK','BĄK',27,'M',92,'147357846','email@host.com',2),(26,'95081135144','MICHAŁ','NOWAK',30,'M',94,'234567891','email3@hostname.pl',3),(27,'82090787755','KATARZYNA','CUK',35,'K',74,'345678912','email4@hostname.pl',4),(28,'84111188155','ANDRZEJ','BĄK',37,'M',95,'456789123','email5@hostname.pl',5),(29,'88012687432','ANGELIKA','KOZIOŁ',25,'K',70,'567891234','email6@hostname.pl',6),(30,'00120585236','ANDRZEJ','KĄT',28,'M',66,'678912345','email7@hostname.pl',7),(31,'01062832188','JAN','KROP',41,'M',71,'789123456','email8@hostname.pl',8),(32,'02022874411','MAŁGORZATA','GUZDA',42,'K',80,'891234567','email9@hostname.pl',9),(33,'04052296311','IGA','BOBEK',45,'K',70,'912345678','email10@hostname.pl',10),(34,'95080811122','AGNIESZKA','BUZ',18,'K',69,'147258369','email11@hostname.pl',11),(35,'69122215942','ZYGMUNT','BOŻYDAR',34,'M',88,'963157359','email12@hostname.pl',12),(36,'80121255599','AGNIESZKA','ELBOWSKA',30,'K',80,'601258369','melbowska@o2.pl',15),(37,'13052974188','ZUZANNA','CZOCHA',20,'K',69,'601235852','annaczocha@o2.pl',16),(38,'82092548963','ZUZANNA','CALKA',34,'F',73,'794852669','zuza.calka@onet.eu',17);
/*!40000 ALTER TABLE `pacjent` ENABLE KEYS */;
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

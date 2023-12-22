Enter password: 
-- MySQL dump 10.13  Distrib 8.2.0, for Linux (x86_64)
--
-- Host: localhost    Database: tg-santa
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
mysqldump: Error: 'Access denied; you need (at least one of) the PROCESS privilege(s) for this operation' when trying to dump tablespaces

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOG` VALUES ('1','AlexCher','db/changelog/db.changelog-master.xml','2023-11-30 15:52:30',1,'EXECUTED','8:31eda9c97bcec21697dc6c77919bb80f','createTable tableName=test','',NULL,'4.20.0',NULL,NULL,'1359549989'),('2','AlexCher','db/changelog/db.changelog-master.xml','2023-11-30 15:52:30',2,'EXECUTED','8:8c1bf47f664a71952eb479a19a517207','insert tableName=test','',NULL,'4.20.0',NULL,NULL,'1359549989');
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ads_table`
--

DROP TABLE IF EXISTS `ads_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ads_table` (
  `id` bigint NOT NULL,
  `ad` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ads_table`
--

LOCK TABLES `ads_table` WRITE;
/*!40000 ALTER TABLE `ads_table` DISABLE KEYS */;
INSERT INTO `ads_table` VALUES (1,'╨Я╨╡╤А╨▓╨░╤П ╨╖╨░╨┐╨╗╨░╨╜╨╕╤А╨╛╨▓╨░╨╜╨╜╨░╤П ╤В╨╡╤Б╤В╨╛╨▓╨░╤П ╤А╨░╤Б╤Б╤Л╨╗╨║╨╛ ╨┐╨╛ ╨▓╤А╨╡╨╝╨╡╨╜╨╕ ╨е╨╛ ╨е╨╛ ╨е╨╛'),(2,'╨б╨┐╨░╤Б╨╕╨▒╨╛ ╨▓╤Б╨╡╨╝ ╨╖╨░ ╤Г╤З╨░╤Б╤В╨╕╨╡ ╨▓ ╨▒╨╡╤В╨░-╤В╨╡╤Б╤В╨░╤Е ╨┐╤А╨╕╨╗╨╛╨╢╨╡╨╜╨╕╤П\n╨Ю╨│╤А╨╛╨╝╨╜╨╛╨╡ ╤Б╨┐╨░╤Б╨╕╨▒╨╛ ╨╖╨░ ╨╛╨▒╤А╨░╤В╨╜╤Г╤О ╤Б╨▓╤П╨╖╤М, ╤Н╤В╨╛ ╨┐╨╛╨╝╨╛╨│╨╗╨╛ ╤Б╨┤╨╡╨╗╨░╤В╤М ╨┐╤А╨╕╨╗╨╛╨╢╨╡╨╜╨╕╨╡ ╨╗╤Г╤З╤И╨╡\n╨б╨╡╨│╨╛╨┤╨╜╤П ╨▓ 14:00 ╨▓╤Б╨╡╨╝ ╤Б╨╛╤В╤А╤Г╨┤╨╜╨╕╨║╨░╨╝ ╨║╨╗╤Г╨▒╨░ ╨▒╤Г╨┤╨╡╤В ╨╛╤В╨┐╤А╨░╨▓╨╗╨╡╨╜╨╛ ╨┐╤А╨╡╨┤╨╗╨╛╨╢╨╡╨╜╨╕╨╡ ╨┐╤А╨╕╤Б╨╛╨╡╨┤╨╕╨╜╨╕╤В╤М╤Б╤П ╨║ ╨╕╨│╤А╨╡.\n\n╨Т╨б╨Х╨Ь ╨Ъ╨Ю╨Ь╨г ╨Я╨а╨Ш╨и╨Ы╨Ю ╨н╨в╨Ю ╨б╨Ю╨Ю╨С╨й╨Х╨Э╨Ш╨Х ╨Э╨Х╨Ю╨С╨е╨Ю╨Ф╨Ш╨Ь╨Ю!!!\n╤Г╨┤╨░╨╗╨╕╤В╤М ╨╕╤Б╤В╨╛╤А╨╕╤О ╤З╨░╤В╨░, ╨┤╨╗╤П ╤Н╤В╨╛╨│╨╛:\n╨╜╨░╨╢╨╝╨╕╤В╨╡ ╤В╤А╨╕ ╤В╨╛╤З╨║╨╕ ╨▓ ╨┐╤А╨░╨▓╨╛╨╝ ╨▓╨╡╤А╤Е╨╜╨╡╨╝ ╤Г╨│╨╗╤Г\n╨▓╤Л╨▒╨╡╤А╨╕╤В╨╡ ╨┐╤Г╨╜╨║╤В Delete and block\n╤Г╨▒╨╡╤А╨╕╤В╨╡ ╨│╨░╨╗╨╛╤З╨║╤Г ╤Б Block bot\n╨╜╨░╨╢╨╝╨╕╤В╨╡ Delete chat\n\n╨Х╤Й╨╡ ╤А╨░╨╖ ╨▒╨╛╨╗╤М╤И╨╛╨╡ ╤Б╨┐╨░╤Б╨╕╨▒╨╛ ╨╕ ╤Б ╨Э╨░╤Б╤В╤Г╨┐╨░╤О╤Й╨╕╨╝ ╨Э╨╛╨▓╤Л╨╝ ╨У╨╛╨┤╨╛╨╝!');
/*!40000 ALTER TABLE `ads_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'Initial Name');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_data_table`
--

DROP TABLE IF EXISTS `users_data_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_data_table` (
  `chat_id` bigint NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `registered_at` datetime(6) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `present` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_data_table`
--

LOCK TABLES `users_data_table` WRITE;
/*!40000 ALTER TABLE `users_data_table` DISABLE KEYS */;
INSERT INTO `users_data_table` VALUES (420528090,'╨Р╨╗╨╡╨║╤Б╨░╨╜╨┤╤А╨░',NULL,'2023-12-11 14:30:45.738000','lammeow','╨Ы╨╡╨╛╨╜╨╡╨╜╨║╨╛ ╨Р╨╗╨╡╨║╤Б╨░╨╜╨┤╤А╨░',NULL,'╨з╤В╨╛ ╤Г╨│╨╛╨┤╨╜╨╛, ╨║╤А╨╛╨╝╨╡ ╨╝╨░╨╜╨┤╨░╤А╨╕╨╜╨╛╨▓ ╨╕ ╨░╨╗╨║╨╛╨│╨╛╨╗╤П ЁЯдк'),(429774279,'╨Ы╨░╤А╨╕╤Б╨░','╨Я╨╡╤В╤А╨╛╨▓╨░','2023-12-12 15:34:44.349000','klarisa_fitness','╨Ы╨░╤А╨╕╤Б╨░ ╨Я╨╡╤В╤А╨╛╨▓╨░',NULL,'╨С╨╕╨╛╤В╨╕╨╜ 5000 ╨▓╨╕╤В╨░╨╝╨╕╨╜╤Л ╨▓ ╨║╨░╨┐╤Б╤Г╨╗╨░╤Е, ╨║╨╛╨╝╨┐╨╗╨╡╨║╤Б ╤Д╨╛╤А╤В╨╡ ╨┤╨╗╤П ╨▓╨╛╨╗╨╛╤Б / ╨Т╨╕╤В╨░╨╝╨╕╨╜ H https://ozon.ru/t/5w47VAN'),(452798187,'Viktoria','Ablaeva','2023-12-11 14:06:23.567000','AblaevaV',NULL,'AWAITING_FIO',NULL),(453562732,'╨Р╨╜╨╜╨░','╨и╨╕╨╗╨╛╨▓╨░','2023-12-11 14:48:15.415000','Annkosichky','╨и╨╕╨╗╨╛╨▓╨░ ╨Р╨╜╨╜╨░',NULL,'╨Я╨╛╨┤╨░╤А╨║╨╕ ╨┤╨╛ 500тВ╜\n\n╨Т╨░╨╖╨░ ╨┤╨╗╤П ╤Ж╨▓╨╡╤В╨╛╨▓ ╤Б╤В╨╡╨║╨╗╤П╨╜╨╜╨░╤П Nina Glass \"╨е╨░╨╜╨╜╨░\", ╤Ж╨▓╨╡╤В: ╨┐╤А╨╛╨╖╤А╨░╤З╨╜╤Л╨╣, ╨▓╤Л╤Б╨╛╤В╨░ 18,5 ╤Б╨╝ https://ozon.ru/t/lYYXLok\n╨Р╤А╨╛╨╝╨░╤В╨╕╤З╨╡╤Б╨║╨░╤П ╤Б╨▓╨╡╤З╨░ ╨╕╨╖ ╤Б╨╛╨╡╨▓╨╛╨│╨╛ ╨▓╨╛╤Б╨║╨░ IQTRAVELS - ╨е╨▓╨╛╨╣╨╜╤Л╨╣ ╤Б╨╛╨╜ https://ozon.ru/t/600nzEl\n╨Я╨╛╨┤╨╜╨╛╤Б, 21 ╤Б╨╝ ╤Е 11 ╤Б╨╝, 1 ╤И╤В https://ozon.ru/t/600nee3\n╨Я╨╛╨┤╨╜╨╛╤Б ╨┤╨╡╤А╨╡╨▓╤П╨╜╨╜╤Л╨╣ ╨┤╨╡╨║╨╛╤А╨░╤В╨╕╨▓╨╜╤Л╨╣ ╨┤╨╗╤П ╨╕╨╜╤В╨╡╤А╤М╨╡╤А╨░ ╤Б ╨┐╨╛╤В╨╡╤А╤В╨╛╤Б╤В╤П╨╝╨╕ Grifeldecor ╨┐╤А╤П╨╝╨╛╤Г╨│╨╛╨╗╤М╨╜╤Л╨╣ ╨▒╨╡╨╗╤Л╨╣ ╤А╨░╨╖╨╜╨╛╤Б ╤Б ╨┐╨░╨╖╨░╨╝╨╕ ╨╕ ╤А╤Г╤З╨║╨░╨╝╨╕ ╨┤╨╗╤П ╨╖╨░╨▓╤В╤А╨░╨║╨░ https://ozon.ru/t/G33Zw7j\n╨Я╨╛╨┤╨╜╨╛╤Б ╨┤╨╡╤А╨╡╨▓╤П╨╜╨╜╤Л╨╣ ╨║╤А╤Г╨│╨╗╤Л╨╣, ╨┤╨╗╤П ╨╖╨░╨▓╤В╤А╨░╨║╨░. 190╤Е20╨╝╨╝. https://ozon.ru/t/WPPJDpJ\n╨Э╨╛╤Б╨║╨╕ ╨в╨╡╨┐╨╗╨╛ ╤Б╨╛ ╨╝╨╜╨╛╨╣, 1 ╨┐╨░╤А╨░ https://ozon.ru/t/dkkllow'),(500276979,'VolodтАЩya',NULL,'2023-12-11 14:01:07.133000','zambrozium',NULL,NULL,NULL),(529558031,'╨Р╨╜╨╜╨░','╨Ъ╨╛╨╖╤Л╤А╨╡╨▓╨░','2023-12-11 14:58:26.315000',NULL,NULL,'AWAITING_FIO',NULL),(592510671,'╨Ь╨░╤А╨╕╤П','╨б╨╛╤В╨╜╨╕╨║╨╛╨▓╨░','2023-12-11 14:29:26.274000','fit_marii','╨б╨╛╤В╨╜╨╕╨║╨╛╨▓╨░ ╨Ь╨░╤А╨╕╤П',NULL,'╨С╨░╨╜╨░╨╜ AMG'),(623187168,'Alechka',NULL,'2023-12-11 21:04:34.725000','ohh_i_deva','╨а╤П╨▒╨╛╨▓╨░ ╨Р╨╗╨╡╨▓╤В╨╕╨╜╨░ ╨Т╨╗╨░╨┤╨╕╨╝╨╕╤А╨╛╨▓╨╜╨░\n╨Я╨╛╨┤╨░╤А╨╛╤З╨╜╨░╤П ╨║╨░╤А╤В╨░ ┬л╨а╨╕╨▓ ╨У╨╛╤И┬╗',NULL,'╨Я╨╛╨┤╨░╤А╨╛╤З╨╜╨░╤П ╨║╨░╤А╤В╨░ ┬л╨а╨╕╨▓ ╨У╨╛╤И┬╗'),(654069777,'╨Р╨╜╨┤╤А╨╡╨╣','╨Х╤Д╨╕╨╝╨╛╨▓','2023-12-11 15:16:11.217000',NULL,'╨Х╤Д╨╕╨╝╨╛╨▓ ╨Р╨╜╨┤╤А╨╡╨╣ ╨Т╨╗╨░╨┤╨╕╨╝╨╕╤А╨╛╨▓╨╕╤З',NULL,'╨Ъ╤А╤Г╨╢╨║╨░ ╤Б ╨╛╤А╨╕╨│╨╕╨╜╨░╨╗╤М╨╜╤Л╨╝ ╨┤╨╕╨╖╨░╨╣╨╜╨╛╨╝'),(656889772,'╨╖╨╗╨░╤В╨╛╨▓╨╗╨░ss╨║╨░',NULL,'2023-12-11 14:07:53.960000','zlatovlassssska','╨Ъ╤А╤Л╨╗╨╛╨▓╨░ ╨Ч╨╗╨░╤В╨░',NULL,'╨Х╤Б╨╗╨╕ ╤В╤Л ╤Б╨╛╨▓╤Б╨╡╨╝ ╨╜╨╡ ╨╖╨╜╨░╨╡╤И╤М, ╤З╤В╨╛ ╨┐╨╛╨┤╨░╤А╨╕╤В╤М, ╤В╨╛ ╨▓╨╛╤В ╨╜╨╡╤Б╨║╨╛╨╗╤М╨║╨╛ ╨▓╨░╤А╨╕╨░╨╜╤В╨╛╨▓\n╨Х╤Б╨╗╨╕ ╤Е╨╛╤З╨╡╤И╤М ╨┐╨╛╨┤╨░╤А╨╕╤В╤М ╤З╤В╨╛-╤В╨╛ ╤Б╨▓╨╛╤С тАФ ╨▓╨┐╨╡╤А╨╡╨┤! :) \n\n1. https://www.wildberries.ru/catalog/29034911/detail.aspx?targetUrl=MI&size=65139984\n2. https://www.wildberries.ru/catalog/173233595/detail.aspx?targetUrl=MI&size=287341763\n3. https://www.wildberries.ru/catalog/13856867/detail.aspx?targetUrl=GP&size=41649691\n4. https://www.wildberries.ru/catalog/179781765/detail.aspx?targetUrl=GP&size=297281332\n5. https://www.wildberries.ru/catalog/161287385/detail.aspx?targetUrl=MI&size=267987304'),(794787578,'M','P','2023-12-11 14:15:37.365000','fannuuqx','╨Я╨╛╤Б╤В╨╜╤Л╤Е ╨Ь╨░╤А╨╕╨╜╨░ ╨Т╨╗╨░╨┤╨╕╨╝╨╕╤А╨╛╨▓╨╜╨░',NULL,'╨Ь4 2023 CSL'),(872065473,'Olga',NULL,'2023-12-11 14:02:50.314000','olyakalashnisk',NULL,'AWAITING_FIO',NULL),(883100281,'ValeriaЁЯШЗ',NULL,'2023-12-11 15:51:47.217000','narcissistkaaa','╨д╨╕╨╗╨╕╨┐╨┐╨╛╨▓╨░ ╨Т╨░╨╗╨╡╤А╨╕╤П',NULL,'https://www.wildberries.ru/catalog/36339633/detail.aspx?targetUrl=MI&size=75682573'),(938951934,'Kristina','Rozenberg','2023-12-11 16:11:44.811000','kristalash','╨Ь╤П╨║╨╕╤И╨╡╨▓╨░ ╨Ъ╤А╨╕╤Б╤В╨╕╨╜╨░',NULL,'https://www.wildberries.ru/catalog/112471302/detail.aspx'),(957450146,'╨Р╨╗╨╡╨║╤Б╨░╨╜╨┤╤А','╨з╨╡╤А╨║╨░╤Б╨╛╨▓','2023-12-08 17:39:24.686000','aleksandr_cherkasov','╨Я╤А╨╛╤А╨┐',NULL,'╨Я╤А╨╜╨╡╨║╨║'),(960586701,'╨Ъ╤Б╨╡╨╜╨╕╤П','╨Т╨░╤А╨╜╨░╨▓╤Б╨║╨░╤П','2023-12-11 14:14:16.628000','Kseniya_varna','╨Т╨░╤А╨╜╨░╨▓╤Б╨║╨░╤П ╨Ъ╤Б╨╡╨╜╨╕╤П',NULL,'YVES ROCHER / ╨Ш╨▓ ╨а╨╛╤И╨╡ / ╨Ь╨╛╨╗╨╛╤З╨║╨╛ ╨┤╨╗╤П ╨в╨╡╨╗╨░ LES PLAISIRS NATURE - ╨Я╨Ы╨Х╨Ч╨Ш╨а ╨Э╨Р╨в╨о╨а \"╨Ы╨░╨▓╨░╨╜╨┤╨░ & ╨Х╨╢╨╡╨▓╨╕╨║╨░\", ╨д╨╗╨░╨║╨╛╨╜ 390 ╨╝╨╗ https://ozon.ru/t/byJBPBW'),(966868851,'Michael',NULL,'2023-12-12 07:05:21.403000','Muxapuh007','╨б╨░╨┐╨╗╨╕╨╜ ╨Ь╨╕╤Е╨░╨╕╨╗',NULL,'╨Э╨╛╨▓╨╛╨┐╨░╤Б╤Б╨╕╤В ЁЯе▓'),(989446743,'╨Р╨╜╨░╤Б╤В╨░╤Б╨╕╤П',NULL,'2023-12-11 14:12:57.596000','lirikacosmosa',NULL,NULL,NULL),(991900223,'Andrey Bublik',NULL,'2023-12-11 14:09:31.792000','AndreyBublik','╨С╤Г╨▒╨╗╨╕╨║ ╨Р╨╜╨┤╤А╨╡╨╣','AWAITING_PRESENT',NULL),(1033382585,'Danya','Bolshakov','2023-12-11 14:06:23.040000','doonya_7',NULL,NULL,NULL),(1090931452,'╨Х╨╗╨╡╨╜╨░','╨Ь╤П╨║╨╛╤В╨╜╨╕╨║╨╛╨▓╨░','2023-12-11 14:07:28.432000',NULL,'╨Ь╤П╨║╨╛╤В╨╜╨╕╨║╨╛╨▓╨░ ╨Х╨╗╨╡╨╜╨░ ╨Т╨╕╨║╤В╨╛╤А╨╛╨▓╨╜╨░',NULL,'╨д╤А╤Г╨║╤В╤Л'),(1167494821,'╨Х╤Д╨╕╨╝╨╛╨▓╨░','╨Ъ╤Б╨╡╨╜╨╕╤П','2023-12-11 17:59:28.910000','Ksu_Efimova',NULL,NULL,NULL),(1205527960,'╨Э╨░╤Б╤В╤П',NULL,'2023-12-11 15:48:53.411000','anetteeu','╨п╤Ж╨║╨╛╨▓╨░ ╨Р╨╜╨░╤Б╤В╨░╤Б╨╕╤П',NULL,'╨С╤Г╨┤╤Г ╨▓╤Б╨╡╨╝╤Г ╤А╨░╨┤╨░ ЁЯЩВ'),(1221560263,'Valeria','Valeria','2023-12-11 14:13:56.589000',NULL,'╨Ь╨╕╤Е╨░╨╣╨╗╨╛╨▓╨░ ╨Т╨░╨╗╨╡╤А╨╕╤П',NULL,'╤Д╨╕╤В╨╜╨╡╤Б ╤А╨╡╨╖╨╕╨╜╨║╨╕, ╨╗╨╕╨▒╨╛ ╤Б╨╗╨░╨┤╨║╨╕╨╣ ╨╜╨░╨▒╨╛╤А(╨╝╨╛╨╗╨╛╤З╨╜╤Л╨╣ ╤И╨╛╨║╨╛╨╗╨░╨┤),╨║╨╛╨▓╤А╨╕╨║ ╨┤╨╗╤П ╨╣╨╛╨│╨╕, ╨╜╨░╨▒╨╛╤А ╤А╨╡╨╖╨╕╨╜╨╛╨║ ╨┤╨╗╤П ╨▓╨╛╨╗╨╛╤Б, ╨╜╨░ ╨▓╤Л╨▒╨╛╤А)'),(1237374601,'Su',NULL,'2023-12-12 10:39:32.853000','cyanide_kalium','╨з╤Г╨┐╤А╨╕╨╜╨░ ╨Ъ╤Б╨╡╨╜╨╕╤П',NULL,'https://www.wildberries.ru/catalog/28877279/detail.aspx?targetUrl=MS&size=64886783 Vivienne Sabo ╨Ь╨░╤Б╨╗╨╛ ╨┤╨╗╤П ╨│╤Г╨▒ Dessert ╤В╨╛╨╜ 05 ╤Г╨▓╨╗╨░╨╢╨╜╤П╤О╤Й╨╡╨╡ ╤Б ╨░╨┐╨┐╨╗╨╕╨║╨░╤В╨╛╤А╨╛╨╝'),(1240803595,'╨Р╨╗╨╡╨║╤Б╨░╨╜╨┤╤А','╨б╤В╤А╨╛╨│╨╕╨╣','2023-12-11 14:17:31.106000','StrogiiAlexandr',NULL,NULL,NULL),(1310154574,'╨Ь╨░╤А╨╕╤П',NULL,'2023-12-11 14:27:56.135000',NULL,'╨Э╨╕╨║╨╛╨╗╨░╨╡╨▓╨░ ╨Ь╨░╤А╨╕╤П',NULL,'╨в╤Г╤И╤М ╨┤╨╗╤П ╤А╨╡╤Б╨╜╨╕╤Ж ╤З╨╡╤А╨╜╨░╤П Femme Fatale, ╤В╨╛╨╜ 01 Vivienne Sabo\nhttps://wildberries.ru/catalog/19094386/detail.aspx'),(1536048324,'╨Ь╨░╤А╨╕╤П','╨Ъ╤А╤Г╨│╨╗╨╛╨▓╨░','2023-12-11 14:23:28.803000','maruchcha','╨Ь╨╛╤Б╨║╨░╨╗╨╡╨╜╨║╨╛ ╨Ь╨░╤А╨╕╤П',NULL,'╨е╨╛╤З╤Г ╤Б╤О╤А╨┐╤А╨╕╨╖ ЁЯдн'),(1559357731,'╨Ь╨░╤А╨╕╤П','╨з╨╡╤И╨╕╨╜╨░','2023-12-11 14:10:33.296000',NULL,'╨з╨╡╤И╨╕╨╜╨░ ╨Ь╨░╤А╨╕╤П ╨Ь╨╕╤Е╨░╨╣╨╗╨╛╨▓╨╜╨░',NULL,'https://www.wildberries.ru/catalog/153437592/detail.aspx?targetUrl=EX&size=256513722'),(1609860845,'RM',NULL,'2023-12-12 17:08:52.955000','SPB_Roman_Moskalenko',NULL,NULL,NULL),(1796952982,'Svetlana','Kolpakova','2023-12-11 14:17:28.968000',NULL,'╨Ъ╨╛╨╗╨┐╨░╨║╨╛╨▓╨░ ╨б',NULL,'╨б╨▒╨╛╤А╤Л ╤В╤А╨░╨▓ ╤Г╤Б╨┐╨╛╨║╨╛╨╕╤В╨╡╨╗╤М╨╜╤Л╨╣ ╨╕ ╨▓╨╕╤В╨░╨╝╨╕╨╜╨╜╤Л╨╣.╨Ь╨╛╨╢╨╜╨╛ ╤Б╨░╨╣╤В╨░ \"╤А╤Г╤Б╤Б╨║╨╕╨╡ ╨║╨╛╤А╨╜╨╕\")))'),(2070894946,'╨Х╨╗╨╡╨╜╨░',NULL,'2023-12-11 14:14:29.926000',NULL,'╨С╨╛╤З╨║╨░╤А╨╡╨▓╨░ ╨Х╨╗╨╡╨╜╨░',NULL,'https://ozon.ru/t/gYYaRR8'),(2072649440,'╨Ф╨░╨╜╨╕╨╕╨╗','╨Ъ╨╛╤А╤З╨░╨│╨╕╨╜','2023-12-11 16:28:41.495000','MAKORCH',NULL,NULL,NULL),(2143160889,'Imant',NULL,'2023-12-11 14:06:59.129000','Imantfakel','╨С╨░╨╗╤М╤З╨╕╤В╨╕╤Б ╨Ш╨╝╨░╨╜╤В',NULL,'╨Ь╨░╤Б╨╗╨╛ ╨┤╨╗╤П ╨▒╨╛╤А╨╛╨┤╤Л ╤Б ╨┐╤А╨╕╨▓╨║╤Г╤Б╨╛╨╝ ╨╜╨╛╨▓╨╛╨│╨╛ ╨│╨╛╨┤╨░, ╤С╨╗╨║╨╕, ╨▒╤Г╤Е╨╗╨░, ╤Б╨░╨┤╨╛╨╝╨╕╨╕');
/*!40000 ALTER TABLE `users_data_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-14 11:47:57

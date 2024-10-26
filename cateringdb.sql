-- MySQL dump 10.13  Distrib 8.1.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cateringdb
-- ------------------------------------------------------
-- Server version	8.1.0

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



CREATE DATABASE cateringdb CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE cateringdb;



--
-- Table structure for table `availability`
--

DROP TABLE IF EXISTS `availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `availability` (
  `idUser` int NOT NULL,
  `idTurn` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `availability`
--

LOCK TABLES `availability` WRITE;
/*!40000 ALTER TABLE `availability` DISABLE KEYS */;
INSERT INTO `availability` (`idUser`, `idTurn`) VALUES (2,1),(4,1);
/*!40000 ALTER TABLE `availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cooktask`
--

DROP TABLE IF EXISTS `cooktask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cooktask` (
  `idCook` int NOT NULL,
  `idTask` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cooktask`
--

LOCK TABLES `cooktask` WRITE;
/*!40000 ALTER TABLE `cooktask` DISABLE KEYS */;
/*!40000 ALTER TABLE `cooktask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `expected_participants` int DEFAULT NULL,
  `organizer_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `organizer_id`) VALUES (1,'Convegno Agile Community','2020-09-25','2020-09-25',100,2),(2,'Compleanno di Manuela','2020-08-13','2020-08-13',25,2),(3,'Fiera del Sedano Rapa','2020-10-02','2020-10-04',400,1);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kitchentasks`
--

DROP TABLE IF EXISTS `kitchentasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kitchentasks` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `idTurn` int DEFAULT NULL,
  `idProcedure` int DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `duration` varchar(30) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kitchentasks`
--

LOCK TABLES `kitchentasks` WRITE;
/*!40000 ALTER TABLE `kitchentasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `kitchentasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menufeatures`
--

DROP TABLE IF EXISTS `menufeatures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menufeatures` (
  `menu_id` int NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `value` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menufeatures`
--

LOCK TABLES `menufeatures` WRITE;
/*!40000 ALTER TABLE `menufeatures` DISABLE KEYS */;
INSERT INTO `menufeatures` (`menu_id`, `name`, `value`) VALUES (80,'Richiede cuoco',0),(80,'Buffet',0),(80,'Richiede cucina',0),(80,'Finger food',0),(80,'Piatti caldi',0),(82,'Richiede cuoco',0),(82,'Buffet',0),(82,'Richiede cucina',0),(82,'Finger food',0),(82,'Piatti caldi',0),(86,'Richiede cuoco',0),(86,'Buffet',0),(86,'Richiede cucina',0),(86,'Finger food',0),(86,'Piatti caldi',0);
/*!40000 ALTER TABLE `menufeatures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menuitems`
--

DROP TABLE IF EXISTS `menuitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menuitems` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_id` int NOT NULL,
  `section_id` int DEFAULT NULL,
  `description` tinytext,
  `recipe_id` int NOT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuitems`
--

LOCK TABLES `menuitems` WRITE;
/*!40000 ALTER TABLE `menuitems` DISABLE KEYS */;
INSERT INTO `menuitems` (`id`, `menu_id`, `section_id`, `description`, `recipe_id`, `position`) VALUES (96,80,0,'Croissant vuoti',9,0),(97,80,0,'Croissant alla marmellata',9,1),(98,80,0,'Pane al cioccolato mignon',10,2),(99,80,0,'Panini al latte con prosciutto crudo',12,4),(100,80,0,'Panini al latte con prosciutto cotto',12,5),(101,80,0,'Panini al latte con formaggio spalmabile alle erbe',12,6),(102,80,0,'Girelle all\'uvetta mignon',11,3),(103,82,0,'Biscotti',13,1),(104,82,0,'Lingue di gatto',14,2),(105,82,0,'Bigné alla crema',15,3),(106,82,0,'Bigné al caffè',15,4),(107,82,0,'Pizzette',16,5),(108,82,0,'Croissant al prosciutto crudo mignon',9,6),(109,82,0,'Tramezzini tonno e carciofini mignon',17,7),(112,86,41,'Vitello tonnato',1,0),(113,86,41,'Carpaccio di spada',2,1),(114,86,41,'Alici marinate',3,2),(115,86,42,'Penne alla messinese',5,0),(116,86,42,'Risotto alla zucca',20,1),(117,86,43,'Salmone al forno',8,0),(118,86,44,'Sorbetto al limone',18,0),(119,86,44,'Torta Saint Honoré',19,1);
/*!40000 ALTER TABLE `menuitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` tinytext,
  `owner_id` int DEFAULT NULL,
  `published` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus`
--

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` (`id`, `title`, `owner_id`, `published`) VALUES (80,'Coffee break mattutino',2,1),(82,'Coffee break pomeridiano',2,1),(86,'Cena di compleanno pesce',3,1);
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menusections`
--

DROP TABLE IF EXISTS `menusections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menusections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_id` int NOT NULL,
  `name` tinytext,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menusections`
--

LOCK TABLES `menusections` WRITE;
/*!40000 ALTER TABLE `menusections` DISABLE KEYS */;
INSERT INTO `menusections` (`id`, `menu_id`, `name`, `position`) VALUES (41,86,'Antipasti',0),(42,86,'Primi',1),(43,86,'Secondi',2),(44,86,'Dessert',3),(45,87,'Antipasti',0);
/*!40000 ALTER TABLE `menusections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` tinytext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` (`id`, `name`) VALUES (1,'Vitello tonnato'),(2,'Carpaccio di spada'),(3,'Alici marinate'),(4,'Insalata di riso'),(5,'Penne al sugo di baccalà'),(6,'Pappa al pomodoro'),(7,'Hamburger con bacon e cipolla caramellata'),(8,'Salmone al forno'),(9,'Croissant'),(10,'Pane al cioccolato'),(11,'Girelle all\'uvetta'),(12,'Panini al latte'),(13,'Biscotti di pasta frolla'),(14,'Lingue di gatto'),(15,'Bigné farciti'),(16,'Pizzette'),(17,'Tramezzini'),(18,'Sorbetto al limone'),(19,'Torta Saint Honoré'),(20,'Risotto alla zucca');
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` char(1) NOT NULL,
  `role` varchar(128) NOT NULL DEFAULT 'servizio',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `role`) VALUES ('c','cuoco'),('h','chef'),('o','organizzatore'),('s','servizio');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `proposed_menu_id` int NOT NULL DEFAULT '0',
  `approved_menu_id` int DEFAULT '0',
  `service_date` date DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `expected_participants` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` (`id`, `event_id`, `name`, `proposed_menu_id`, `approved_menu_id`, `service_date`, `time_start`, `time_end`, `expected_participants`) VALUES (1,2,'Cena',86,80,'2020-08-13','20:00:00','23:30:00',25),(2,1,'Coffee break mattino',86,80,'2020-09-25','10:30:00','11:30:00',100),(3,1,'Colazione di lavoro',86,80,'2020-09-25','13:00:00','14:00:00',80),(4,1,'Coffee break pomeriggio',86,80,'2020-09-25','16:00:00','16:30:00',100),(5,1,'Cena sociale',86,80,'2020-09-25','20:00:00','22:30:00',40),(6,3,'Pranzo giorno 1',86,80,'2020-10-02','12:00:00','15:00:00',200),(7,3,'Pranzo giorno 2',86,80,'2020-10-03','12:00:00','15:00:00',300),(8,3,'Pranzo giorno 3',86,80,'2020-10-04','12:00:00','15:00:00',400);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todolists`
--

DROP TABLE IF EXISTS `todolists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `todolists` (
  `idService` int NOT NULL,
  `idTask` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todolists`
--

LOCK TABLES `todolists` WRITE;
/*!40000 ALTER TABLE `todolists` DISABLE KEYS */;
/*!40000 ALTER TABLE `todolists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turns`
--

DROP TABLE IF EXISTS `turns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turns` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turns`
--

LOCK TABLES `turns` WRITE;
/*!40000 ALTER TABLE `turns` DISABLE KEYS */;
INSERT INTO `turns` (`id`) VALUES (1),(2),(3),(4);
/*!40000 ALTER TABLE `turns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userroles` (
  `user_id` int NOT NULL,
  `role_id` char(1) NOT NULL DEFAULT 's'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` (`user_id`, `role_id`) VALUES (1,'o'),(2,'o'),(2,'h'),(3,'h'),(4,'h'),(4,'c'),(5,'c'),(6,'c'),(7,'c'),(8,'s'),(9,'s'),(10,'s'),(7,'s');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `username`) VALUES (1,'Carlin'),(2,'Lidia'),(3,'Tony'),(4,'Marinella'),(5,'Guido'),(6,'Antonietta'),(7,'Paola'),(8,'Silvia'),(9,'Marco'),(10,'Piergiorgio');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-15 15:15:12

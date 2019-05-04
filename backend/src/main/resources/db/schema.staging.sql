-- MySQL dump 10.16  Distrib 10.1.38-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mdl_staging
-- ------------------------------------------------------
-- Server version	10.1.38-MariaDB-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET FOREIGN_KEY_CHECKS = 0;
--
-- Current Database: `mdl_staging`
--

DROP DATABASE IF EXISTS `mdl_staging`;

CREATE DATABASE  IF NOT EXISTS `mdl_staging` DEFAULT CHARACTER SET utf8mb4;

USE `mdl_staging`;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `abstract` longtext NOT NULL,
  `created_at` date DEFAULT NULL,
  `journal` varchar(255) NOT NULL,
  `journal_number` varchar(255) DEFAULT NULL,
  `journal_volume` varchar(255) DEFAULT NULL,
  `nb_citations` int(11) DEFAULT NULL,
  `nb_views` int(11) DEFAULT NULL,
  `pages` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `publication_month` varchar(255) DEFAULT NULL,
  `publication_year` int(11) NOT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `creator_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `test` (`category_id`),
  KEY `abc` (`creator_user_id`),
  CONSTRAINT `abc` FOREIGN KEY (`creator_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `test` FOREIGN KEY (`category_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `article_authors`
--

DROP TABLE IF EXISTS `article_authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_authors` (
  `author_id` bigint(20) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  PRIMARY KEY (`author_id`,`article_id`),
  KEY `FK2n7f44d637jpr8i8ak6h6ieec` (`article_id`),
  CONSTRAINT `FK2n7f44d637jpr8i8ak6h6ieec` FOREIGN KEY (`article_id`) REFERENCES `author` (`id`),
  CONSTRAINT `FK33ebmlepmluhtmdcxcqvtr103` FOREIGN KEY (`author_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `article_keywords`
--

DROP TABLE IF EXISTS `article_keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_keywords` (
  `article_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`article_id`,`tag_id`),
  KEY `FKidmgmyolurscrsos0ese1mu8p` (`tag_id`),
  CONSTRAINT `FKidmgmyolurscrsos0ese1mu8p` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`),
  CONSTRAINT `FKimdomuah3ta4hmeb77e68m0wl` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `article_references`
--

DROP TABLE IF EXISTS `article_references`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_references` (
  `article_id` bigint(20) NOT NULL,
  `reference_id` bigint(20) NOT NULL,
  PRIMARY KEY (`article_id`,`reference_id`),
  KEY `FK8eawewn44yvivq0y13naa7r3r` (`reference_id`),
  CONSTRAINT `FK8eawewn44yvivq0y13naa7r3r` FOREIGN KEY (`reference_id`) REFERENCES `article` (`id`),
  CONSTRAINT `FKq84xvm8kwu6x3bs310xpokl5p` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookmark` (
  `id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `sota_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcow5ux3yhmj8uwu36so5928gp` (`article_id`),
  KEY `FK3ogdxsxa4tx6vndyvpk1fk1am` (`user_id`),
  KEY `FK74s6injeo6trqcm8g2rpri5bi` (`sota_id`),
  CONSTRAINT `FK3ogdxsxa4tx6vndyvpk1fk1am` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK74s6injeo6trqcm8g2rpri5bi` FOREIGN KEY (`sota_id`) REFERENCES `state_of_the_art` (`id`),
  CONSTRAINT `FKcow5ux3yhmj8uwu36so5928gp` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `follower_tags`
--

DROP TABLE IF EXISTS `follower_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follower_tags` (
  `follower_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`follower_id`,`tag_id`),
  KEY `FKjoex3arxl68mm3hgoq7v6d0ea` (`tag_id`),
  CONSTRAINT `FKb2nvfi2atb8hhhcx3cmqh86cx` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKjoex3arxl68mm3hgoq7v6d0ea` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
-- INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1),(1),(1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `research_group`
--

DROP TABLE IF EXISTS `research_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `research_group` (
  `id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `nombre` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `state_of_the_art`
--

DROP TABLE IF EXISTS `state_of_the_art`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state_of_the_art` (
  `id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK31ulxnjxdsp53eal91qqtri2b` (`category_id`),
  KEY `FKplwr874kk4rxi3cnqn6peia4f` (`user_id`),
  CONSTRAINT `FK31ulxnjxdsp53eal91qqtri2b` FOREIGN KEY (`category_id`) REFERENCES `tag` (`id`),
  CONSTRAINT `FKplwr874kk4rxi3cnqn6peia4f` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `state_of_the_art_articles`
--

DROP TABLE IF EXISTS `state_of_the_art_articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state_of_the_art_articles` (
  `sota_id` bigint(20) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  KEY `FK84ikkes9utrx6t55tjgc2jiyh` (`article_id`),
  KEY `FK25rgvv5368m2ql32r9mot5wd1` (`sota_id`),
  CONSTRAINT `FK25rgvv5368m2ql32r9mot5wd1` FOREIGN KEY (`sota_id`) REFERENCES `state_of_the_art` (`id`),
  CONSTRAINT `FK84ikkes9utrx6t55tjgc2jiyh` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `state_of_the_art_tags`
--

DROP TABLE IF EXISTS `state_of_the_art_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state_of_the_art_tags` (
  `sota_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  KEY `FKsyyaxo1u3ssb8kivuq9kh72s6` (`tag_id`),
  KEY `FK4xubpmgs27oenkfxgi2n7hti4` (`sota_id`),
  CONSTRAINT `FK4xubpmgs27oenkfxgi2n7hti4` FOREIGN KEY (`sota_id`) REFERENCES `state_of_the_art` (`id`),
  CONSTRAINT `FKsyyaxo1u3ssb8kivuq9kh72s6` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `university`
--

DROP TABLE IF EXISTS `university`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `university` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `website_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `university_current`
--

DROP TABLE IF EXISTS `university_current`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `university_current` (
  `university_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`university_id`,`user_id`),
  KEY `FKhc7ol0yavh5d7iwki8975qcmy` (`user_id`),
  CONSTRAINT `FKhc7ol0yavh5d7iwki8975qcmy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKhq70genstsfbk2o3oypg7gyke` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL,
  `domain` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `current_university_id` bigint(20) DEFAULT NULL,
  `profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1mcjtpxmwom9h9bf2q0k412e0` (`profile_id`),
  KEY `FK1e2ty7blsp9ipx3bcenkm1d4k` (`current_university_id`),
  CONSTRAINT `FK1e2ty7blsp9ipx3bcenkm1d4k` FOREIGN KEY (`current_university_id`) REFERENCES `university` (`id`),
  CONSTRAINT `FK5hv52mjjufrwrx302p37tq262` FOREIGN KEY (`profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `user_follower`
--

DROP TABLE IF EXISTS `user_follower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_follower` (
  `user_id` bigint(20) NOT NULL,
  `following_id` bigint(20) NOT NULL,
  KEY `FK23901xecck2pgewtesgjp6p73` (`following_id`),
  KEY `FK7lyufbui36w4jul3ml7p4k5ar` (`user_id`),
  CONSTRAINT `FK23901xecck2pgewtesgjp6p73` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK7lyufbui36w4jul3ml7p4k5ar` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group` (
  `group_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKsbeb7hers5b9rnvcclmnvot5n` (`group_id`),
  CONSTRAINT `FK1c1dsw3q36679vaiqwvtv36a6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsbeb7hers5b9rnvcclmnvot5n` FOREIGN KEY (`group_id`) REFERENCES `research_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `facebook_url` varchar(255) DEFAULT NULL,
  `linkedin_url` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `twitter_url` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6kwj5lk78pnhwor4pgosvb51r` (`user_id`),
  CONSTRAINT `FK6kwj5lk78pnhwor4pgosvb51r` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;



SET FOREIGN_KEY_CHECKS = 1;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */

-- Dump completed on 2019-05-04 11:39:48

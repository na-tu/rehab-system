-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: rehab_system
-- ------------------------------------------------------
-- Server version	8.0.42

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

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `disease_name` varchar(100) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `notes` text,
  `gender` varchar(10) DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,'変更済太郎','脳梗塞','2025-06-01',NULL,'庭作業中に転倒。救急搬送された。\r\n希望：身の回りの事は自分で出来るようになりたい.','男性',52),(2,'佐藤　光','先天性心疾患','2025-06-01','2025-07-31','小学校に通学している。\r\n関節や筋力の問題はないが、心臓負荷量の配慮が必要。','男性',10),(5,'鈴木　未知','リウマチ','2025-06-01','2025-06-25','ADLはおおむね自立している。日によって関節の増減がありサポートが必要。','女性',30),(6,'大場　かな','右大腿骨骨折','2025-06-17',NULL,'転倒し骨折。ADLは一部介助。ガッツがある。','女性',44),(7,'後藤　一','肺気腫','2025-06-17',NULL,'ニコンちん依存症。入院中もタバコやめられない。HOT必要だが本人自覚なし。','その他',55),(10,'山本　みき','変形性膝関節証','2025-05-29','2025-06-30','右膝の痛みが顕著で、人工関節置換術の手術済み。早期よりリハビリを開始し在宅復帰を希望している。','女性',48),(11,'木口　花','上腕骨骨折','2025-06-29','2025-07-08','転倒にて骨折。ギプス着用しながらのADL動作訓練が必要。\r\n','女性',35),(16,'比嘉　一','腰椎圧迫骨折','2025-07-02',NULL,'尻もちをついて発症。体幹コルセット着用あり。本人は在宅復帰を希望している。','男性',80),(17,'A','脳梗塞','2025-07-08',NULL,'44','',50),(19,'テスト花子','リウマチ','2024-04-10',NULL,NULL,'女性',72),(21,'テスト花子','リウマチ','2024-04-10',NULL,NULL,'女性',72),(23,'テスト花子','リウマチ','2024-04-10',NULL,NULL,'女性',72),(25,'テスト花子','リウマチ','2024-04-10',NULL,NULL,'女性',72);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rehab_records`
--

DROP TABLE IF EXISTS `rehab_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rehab_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `patient_id` bigint DEFAULT NULL,
  `date` date DEFAULT NULL,
  `content` text,
  `barthel_index` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `rehab_records_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rehab_records`
--

LOCK TABLES `rehab_records` WRITE;
/*!40000 ALTER TABLE `rehab_records` DISABLE KEYS */;
INSERT INTO `rehab_records` VALUES (5,7,'2025-06-17','有酸素運動と本人への病状説明・リハビリ指導。',70),(6,7,'2025-06-18','有酸素運動と本人への病状説明・リハビリ指導。',75),(11,5,'2025-06-22','ADL状況の確認。指関節の痛みが強いためスプリング検討。',77),(23,1,'2025-07-02','入浴動作は環境設定すれば見守りで可能。',70),(28,1,'2025-06-22','更衣訓練。一部介助。',44),(32,1,'2025-06-09','昨日と同様な状況。',20),(33,6,'2025-06-17','ベッド上でROM実施。痛み顕著で力がはいっていない。',30),(35,2,'2025-06-02','平行棒での歩行訓練を実施。膝折れ目立つ。',70),(36,2,'2025-06-09','運動時の喘鳴がみられるためリハ中止。',70),(37,2,'2025-06-16','有酸素運動と本人への病状説明・リハビリ指導。',70),(38,2,'2025-07-31','有酸素運動でも息切れ様は緩和。体力向上みられる。',80),(39,5,'2025-06-25','スプリング装具の装着であれば痛み軽減あり。ADLも自己で可能。',80),(40,6,'2025-06-24','痛みが強くリハ中止。',30),(41,7,'2025-06-19','昨日と同様な状況。',75),(42,10,'2025-05-29','ベッド上でROM実施。痛みが強いため軽負荷で実施。',40),(43,10,'2025-06-07','平行棒での歩行訓練を実施。膝折れ目立つ。',44),(44,10,'2025-06-30','痛みも目立ってなく歩行安定。ADLもほぼ自立。',80),(45,11,'2025-06-30','痛みが強くリハ中止。',50),(46,11,'2025-07-01','動作時に痛みは少しあるがゆっくりであれば更衣動作もスムーズ。',70),(47,16,'2025-07-03','動作確認。痛みあるがゆっくりであればADL動作可能。時間は要する。見守り必要。',75),(48,16,'2025-07-07','平行棒での歩行訓練を実施。痛み生じているが、フラツキはない。',75),(49,17,'2025-07-08','運動時の喘鳴がみられるためリハ中止。',29);
/*!40000 ALTER TABLE `rehab_records` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-13 14:49:17

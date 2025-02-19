CREATE TABLE `gmfcircoscrizione` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  `flag_raggruppamento` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

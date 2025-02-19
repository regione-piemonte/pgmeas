CREATE TABLE `gmfcommento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(255) DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  `testo` varchar(255) DEFAULT NULL,
  `idfase` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

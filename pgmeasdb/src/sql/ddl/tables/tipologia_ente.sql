CREATE TABLE `tipologia_ente` (
  `id_tipologia_ente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `desc_tipologia_ente` varchar(255) NOT NULL,
  PRIMARY KEY (`id_tipologia_ente`),
  UNIQUE KEY `uk_tipologia_ente` (`id_tipologia_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

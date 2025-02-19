CREATE TABLE `tipoperaz_faseproced` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `tipologia_operazione_id` int NOT NULL,
  `cod_fase` int DEFAULT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `tenant` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `direzione` (
  `id_direzione` bigint unsigned NOT NULL AUTO_INCREMENT,
  `flag_titolare` tinyint NOT NULL DEFAULT '0',
  `descrizione` varchar(255) NOT NULL,
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `id_ente` bigint unsigned NOT NULL,
  PRIMARY KEY (`id_direzione`),
  KEY `fk_direzione_ente` (`id_ente`),
  CONSTRAINT `fk_direzione_ente` FOREIGN KEY (`id_ente`) REFERENCES `ente` (`id_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

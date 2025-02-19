CREATE TABLE `direzione_gerarchia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `direzione_id_padre` bigint unsigned DEFAULT NULL,
  `direzione_id_figlio` bigint unsigned DEFAULT NULL,
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `username_creazione` varchar(255) NOT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_gerarchia_direzione` (`direzione_id_padre`,`direzione_id_figlio`,`data_inizio_validita`,`data_fine_validita`),
  KEY `fk_id_direzione_figlio` (`direzione_id_figlio`),
  CONSTRAINT `fk_id_direzione_figlio` FOREIGN KEY (`direzione_id_figlio`) REFERENCES `direzione` (`id_direzione`),
  CONSTRAINT `fk_id_direzione_padre` FOREIGN KEY (`direzione_id_padre`) REFERENCES `direzione` (`id_direzione`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ente` (
  `id_ente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(255) NOT NULL,
  `abilitato` tinyint NOT NULL DEFAULT '0',
  `id_immagine` bigint unsigned DEFAULT NULL,
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `id_tipologia_ente` bigint unsigned NOT NULL,
  `codice_fiscale` varchar(16) DEFAULT NULL,
  `flag_contabilita_esterna` TINYINT NOT NULL DEFAULT '0',
  `path_immagine` VARCHAR(255) NULL
  PRIMARY KEY (`id_ente`),
  KEY `fk_ente_tipologia` (`id_tipologia_ente`),
  CONSTRAINT `fk_ente_tipologia` FOREIGN KEY (`id_tipologia_ente`) REFERENCES `tipologia_ente` (`id_tipologia_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Creata nuova tabella funzionalita
CREATE TABLE `funzionalita` (
  `id_funzionalita` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice_funzionalita` varchar(10) NOT NULL,
  `desc_funzionalita` varchar(255) NOT NULL,
  `order` int unsigned NOT NULL,
  PRIMARY KEY (`id_funzionalita`),
  UNIQUE KEY `id_UNIQUE` (`id_funzionalita`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- Creata nuova tabella ruolo_funzionalita
CREATE TABLE `ruolo_funzionalita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ruolo_id` bigint unsigned NOT NULL,
  `funzionalita_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk1_ruolo` FOREIGN KEY (`ruolo_id`) REFERENCES `ruolo` (`id_ruolo`),
  CONSTRAINT `fk1_funzionalita` FOREIGN KEY (`funzionalita_id`) REFERENCES `funzionalita` (`id_funzionalita`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- Creata nuova tabella direzione_gerarchia
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

ALTER TABLE `progetto` ADD INDEX `idx_ente_direzione` (`tenant` ASC, `direzione_id` ASC);
ALTER TABLE `progetto` ADD INDEX `idx_ente_programma` (`tenant` ASC, `gmfprogrammi_programma_id` ASC);
ALTER TABLE `progetto` ADD INDEX `idx_ente_fondo` (`tenant` ASC, `gmffondo_fondo_id` ASC);




CREATE TABLE `raggruppamento_circoscrizione` (
  `id`                       bigint unsigned NOT NULL AUTO_INCREMENT,
  `circoscrizione_padre_id`  int             NOT NULL,
  `circoscrizione_figlio_id` int             NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk01_circ_padre_id_idx` (`circoscrizione_padre_id`),
  KEY `fk02_circ_figlio_id_idx` (`circoscrizione_figlio_id`),
  CONSTRAINT `fk01_circ_padre_id`  FOREIGN KEY (`circoscrizione_padre_id`)  REFERENCES `gmfcircoscrizione` (`id`),
  CONSTRAINT `fk02_circ_figlio_id` FOREIGN KEY (`circoscrizione_figlio_id`) REFERENCES `gmfcircoscrizione` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

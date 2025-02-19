CREATE TABLE `progetto_circoscrizione` (
  `id_progetto_circoscrizione` bigint NOT NULL AUTO_INCREMENT,
  `progetto_id` bigint unsigned NOT NULL,
  `circoscrizione_id` bigint NOT NULL,
  `ordine` double DEFAULT NULL,
  PRIMARY KEY (`id_progetto_circoscrizione`),
  KEY `fk_progetto_circoscrizione_idx` (`progetto_id`),
  CONSTRAINT `fk_progetto_circoscrizione` FOREIGN KEY (`progetto_id`) REFERENCES `progetto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

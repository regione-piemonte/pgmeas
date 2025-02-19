DROP TABLE IF EXISTS progetto_raggruppamento_tipo;

CREATE TABLE `progetto_raggruppamento_tipo` (
  `id`                          bigint unsigned NOT NULL AUTO_INCREMENT,
  `progetto_raggruppamento_id`  bigint unsigned NOT NULL,
  `tipo_raggruppamento_id`      bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `progetto_raggruppamento_tipo_FK1` (`progetto_raggruppamento_id`),
  KEY `progetto_raggruppamento_tipo_FK2` (`tipo_raggruppamento_id`),
  CONSTRAINT `progetto_raggruppamento_tipo_FK1`  FOREIGN KEY (`progetto_raggruppamento_id`) REFERENCES `progetto_raggruppamento` (`id`),
  CONSTRAINT `progetto_raggruppamento_tipo_FK2` FOREIGN KEY (`tipo_raggruppamento_id`)      REFERENCES `tipo_raggruppamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS progetto_gerarchia;

-- Create table progetto_gerarchia
CREATE TABLE `progetto_gerarchia` (
  `id`                          bigint unsigned NOT NULL AUTO_INCREMENT,
  `progetto_raggruppamento_id`  bigint unsigned NOT NULL,
  `progetto_id_master`          bigint unsigned NOT NULL,
  `progetto_id`                 bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `progetto_gerarchia_FK1` (`progetto_id_master`),
  KEY `progetto_gerarchia_FK2` (`progetto_id`),
  KEY `progetto_gerarchia_FK3` (`progetto_raggruppamento_id`),
  CONSTRAINT `progetto_gerarchia_FK1` FOREIGN KEY (`progetto_id_master`)         REFERENCES `progetto` (`id`),
  CONSTRAINT `progetto_gerarchia_FK2` FOREIGN KEY (`progetto_id`)                REFERENCES `progetto` (`id`),
  CONSTRAINT `progetto_gerarchia_FK3` FOREIGN KEY (`progetto_raggruppamento_id`) REFERENCES `progetto_raggruppamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
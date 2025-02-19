CREATE TABLE `gmfsottofasi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `milestone` varchar(255) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `stato` varchar(255) DEFAULT NULL,
  `idfaseprocedurale` int DEFAULT NULL,
  `datainizio` date DEFAULT NULL COMMENT 'Data inizio attività/task',
  `datafine` date DEFAULT NULL,
  `tipo_attivita_id` int unsigned DEFAULT NULL COMMENT 'Identificativo tabella tipo_attivita',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_tipo_attivita` FOREIGN KEY (`tipo_attivita_id`) REFERENCES `tipo_attivita` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `gmfsottofasi` ADD INDEX `fk_tipo_attivita_idx` (`tipo_attivita_id` ASC) VISIBLE;
CREATE TABLE `progetto_tag` (
  `id_progetto_tag` bigint NOT NULL AUTO_INCREMENT,
  `progetto_id` bigint unsigned NOT NULL,
  `tag_id` int DEFAULT NULL,
  `ordine` int NOT NULL,
  PRIMARY KEY (`id_progetto_tag`),
  KEY `fk_progetto_tag_idx` (`progetto_id`),
  CONSTRAINT `fk_progetto_tag` FOREIGN KEY (`progetto_id`) REFERENCES `progetto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

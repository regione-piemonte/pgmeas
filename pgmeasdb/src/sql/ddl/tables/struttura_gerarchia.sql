CREATE TABLE `struttura_gerarchia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `elemento_gerarchia_id_padre` bigint DEFAULT NULL,
  `elemento_gerarchia_id_figlio` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_gerarchia1` (`elemento_gerarchia_id_padre`,`elemento_gerarchia_id_figlio`),
  KEY `fk_id_Elemento_Gerarchia_figlio_idx` (`elemento_gerarchia_id_figlio`),
  CONSTRAINT `fk_id_Elemento_Gerarchia_figlio` FOREIGN KEY (`elemento_gerarchia_id_figlio`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_id_Elemento_Gerarchia_padre` FOREIGN KEY (`elemento_gerarchia_id_padre`) REFERENCES `elemento_gerarchia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

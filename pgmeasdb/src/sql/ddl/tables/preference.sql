CREATE TABLE `preference` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `ente_id` bigint unsigned DEFAULT NULL,
  `direzione_id` bigint unsigned DEFAULT NULL,
  `cod_funz` varchar(45) NOT NULL,
  `nome_filtro` varchar(255) NOT NULL,
  `preference` json DEFAULT NULL,
  `flag_predefinito` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk1_user_idx` (`user_id`),
  KEY `fk2_ente_idx` (`ente_id`),
  CONSTRAINT `fk1_user` FOREIGN KEY (`user_id`) REFERENCES `utente` (`id_utente`),
  CONSTRAINT `fk2_ente` FOREIGN KEY (`ente_id`) REFERENCES `ente` (`id_ente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

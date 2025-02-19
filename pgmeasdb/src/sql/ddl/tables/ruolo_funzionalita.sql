CREATE TABLE `ruolo_funzionalita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ruolo_id` bigint unsigned NOT NULL,
  `funzionalita_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_ruolo` (`ruolo_id`),
  KEY `fk1_funzionalita` (`funzionalita_id`),
  CONSTRAINT `fk1_funzionalita` FOREIGN KEY (`funzionalita_id`) REFERENCES `funzionalita` (`id_funzionalita`),
  CONSTRAINT `fk1_ruolo` FOREIGN KEY (`ruolo_id`) REFERENCES `ruolo` (`id_ruolo`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

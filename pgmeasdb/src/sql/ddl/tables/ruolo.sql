CREATE TABLE `ruolo` (
  `id_ruolo` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice` varchar(20) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  PRIMARY KEY (`id_ruolo`),
  UNIQUE KEY `uk_ruolo` (`codice`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

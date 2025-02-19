CREATE TABLE `funzione` (
  `id_funzione` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice` varchar(50) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  PRIMARY KEY (`id_funzione`),
  UNIQUE KEY `uk_funzione` (`codice`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

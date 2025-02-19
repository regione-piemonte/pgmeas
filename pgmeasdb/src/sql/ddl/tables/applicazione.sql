CREATE TABLE `applicazione` (
  `id_applicazione` bigint unsigned NOT NULL AUTO_INCREMENT,
  `api_key` int NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `data_eliminazione` datetime DEFAULT NULL,
  PRIMARY KEY (`id_applicazione`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

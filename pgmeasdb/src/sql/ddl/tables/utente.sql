CREATE TABLE `utente` (
  `id_utente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `cognome` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `lingua` varchar(2) NOT NULL DEFAULT 'IT',
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `data_ultimo_accesso` datetime DEFAULT NULL,
  PRIMARY KEY (`id_utente`),
  UNIQUE KEY `key01` (`username`,`data_inizio_validita`,`data_fine_validita`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

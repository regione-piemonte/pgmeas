CREATE TABLE `funzionalita` (
  `id_funzionalita` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice_funzionalita` varchar(10) NOT NULL,
  `desc_funzionalita` varchar(255) NOT NULL,
  `order` int unsigned NOT NULL,
  PRIMARY KEY (`id_funzionalita`),
  UNIQUE KEY `id_UNIQUE` (`id_funzionalita`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

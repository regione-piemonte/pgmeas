CREATE TABLE `ruolo_funzione` (
  `id_ruolo` bigint unsigned NOT NULL,
  `id_funzione` bigint unsigned NOT NULL,
  PRIMARY KEY (`id_ruolo`,`id_funzione`),
  KEY `fk_ra_funzione` (`id_funzione`),
  CONSTRAINT `fk_ra_funzione` FOREIGN KEY (`id_funzione`) REFERENCES `funzione` (`id_funzione`),
  CONSTRAINT `fk_ra_ruolo` FOREIGN KEY (`id_ruolo`) REFERENCES `ruolo` (`id_ruolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

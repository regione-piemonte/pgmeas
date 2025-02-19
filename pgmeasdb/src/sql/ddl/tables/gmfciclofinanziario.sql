CREATE TABLE `gmfciclofinanziario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `step` varchar(255) DEFAULT NULL,
  `valore` double DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `stato` varchar(255) DEFAULT NULL,
  `idprogetto` int DEFAULT NULL,
  `idfase` int DEFAULT NULL,
  `datafine` date DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL COMMENT 'Note sullo step finanziario',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

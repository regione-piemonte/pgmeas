CREATE TABLE `gmffaseprocedurale` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `idprogetto` int DEFAULT NULL,
  `datainizioprev` date DEFAULT NULL,
  `datafineprev` date DEFAULT NULL,
  `datainizioeff` date DEFAULT NULL,
  `datafineeff` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

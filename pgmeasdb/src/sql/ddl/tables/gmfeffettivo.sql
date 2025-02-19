CREATE TABLE `gmfeffettivo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itrimeff` double DEFAULT NULL,
  `iitrimeff` double DEFAULT NULL,
  `iiitrimeff` double DEFAULT NULL,
  `ivtrimeff` double DEFAULT NULL,
  `idanno` int DEFAULT NULL,
  `idfase` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

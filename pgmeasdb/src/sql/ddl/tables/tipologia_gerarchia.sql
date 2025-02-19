CREATE TABLE `tipologia_gerarchia` (
  `cod_tipologia_gerarchia` VARCHAR(5) NOT NULL,
  `des_tipologia` VARCHAR(50) NOT NULL,
  `livello_tipologia` INT NOT NULL,
  `type_fund` INT NOT NULL,
  `des_gerarchia` VARCHAR(1024) NOT NULL,
  `livello_tipologia` INT NOT NULL,
  `type_fund` INT NOT NULL,
  `des_gerarchia` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`cod_tipologia_gerarchia`),
  UNIQUE KEY `id_tipologia_UNIQUE` (`cod_tipologia_gerarchia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

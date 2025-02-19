CREATE TABLE `elemento_gerarchia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ente_id` int NOT NULL,
  `cod_tipologia_gerarchia` varchar(5) NOT NULL,
  `des_elemento` varchar(255) NOT NULL,
  `attivo` tinyint NOT NULL DEFAULT '1',
  `cod_livello_elemento` varchar(45) NOT NULL,
  `order` int DEFAULT NULL,
  `id_pk_old` bigint DEFAULT NULL,
  `nome_tabella_old` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tipologia_gerarchia_idx` (`cod_tipologia_gerarchia`),
  CONSTRAINT `fk_tipologia_gerarchia` FOREIGN KEY (`cod_tipologia_gerarchia`) REFERENCES `tipologia_gerarchia` (`cod_tipologia_gerarchia`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

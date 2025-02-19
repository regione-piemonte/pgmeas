DROP TABLE IF EXISTS tipo_raggruppamento;

CREATE TABLE `tipo_raggruppamento` (
  `id`                          bigint unsigned NOT NULL AUTO_INCREMENT,
  `desc_tipo_raggruppamento`    varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

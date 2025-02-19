DROP TABLE IF EXISTS tipo_attivita;
CREATE TABLE `tipo_attivita` (
  `id`      int             UNSIGNED NOT NULL AUTO_INCREMENT,
  `value`   varchar(255)    DEFAULT NULL,
  `order`   double          DEFAULT NULL,
  `active`  tinyint         DEFAULT NULL,
--  `tenant`  int             DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

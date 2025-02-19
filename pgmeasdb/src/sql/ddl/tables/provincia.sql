CREATE TABLE `provincia` (
  `id`              int             UNSIGNED NOT NULL AUTO_INCREMENT,
  `des_provincia`   varchar(255)    DEFAULT NULL,
  `regione_id`      INT UNSIGNED    NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_regione_idx` (`regione_id` ASC) VISIBLE,
  CONSTRAINT `fk_regione`
    FOREIGN KEY (`regione_id`)
    REFERENCES `regione` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comune` (
  `id`              int             UNSIGNED NOT NULL AUTO_INCREMENT,
  `des_comune`      varchar(255)    DEFAULT NULL,
  `regione_id`      INT UNSIGNED    NULL,
  `provincia_id`    INT UNSIGNED    NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_provincia_idx` (`provincia_id` ASC) VISIBLE,
  CONSTRAINT `fk_provincia`
    FOREIGN KEY (`provincia_id`)
    REFERENCES `provincia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

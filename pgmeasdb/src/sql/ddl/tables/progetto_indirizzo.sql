CREATE TABLE `progetto_indirizzo` (
  `id`              int     unsigned    NOT NULL AUTO_INCREMENT,
  `progetto_id`     bigint  unsigned    NOT NULL,
  `regione_id`      int     unsigned    NOT NULL,
  `provincia_id`    int     unsigned    DEFAULT NULL,
  `comune_id`       int     unsigned    DEFAULT NULL,
  `indirizzo`       varchar(1000)       DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  CONSTRAINT `fk_progetto_indirizzo_comune`     FOREIGN KEY (`comune_id`)       REFERENCES `comune`     (`id`),
  CONSTRAINT `fk_progetto_indirizzo_provincia`  FOREIGN KEY (`provincia_id`)    REFERENCES `provincia`  (`id`),
  CONSTRAINT `fk_progetto_indirizzo_regione`    FOREIGN KEY (`regione_id`)      REFERENCES `regione`    (`id`),
  CONSTRAINT `fk_progetto_indirizzo_progetto`   FOREIGN KEY (`progetto_id`)     REFERENCES `progetto`   (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

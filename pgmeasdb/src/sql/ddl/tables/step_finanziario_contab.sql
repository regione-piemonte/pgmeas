CREATE TABLE `step_finanziario_contab` (
  `id`                        BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `tipo_step_finanziario_id`  INT    unsigned NOT NULL,
  `step_finanziario_cup_id`   BIGINT unsigned NOT NULL COMMENT 'Identificativo id della tabella step_finanziario_cup',
  `anno_impegno`              INT    unsigned NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno e Determina di Liquidazione',
  `numero_documento`          INT    unsigned NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno, Determina di Liquidazione e Mandato di Pagamento',
  `determina_tipo_sigla`      VARCHAR(50)     NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno e Determina di Liquidazione',
  `determina_tipo_des`        VARCHAR(255)    NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno e Determina di Liquidazione',
  `determina_settore`         VARCHAR(50)     NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno e Determina di Liquidazione',
  `determina_numero`          INT    unsigned NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno e Determina di Liquidazione',
  `determina_anno`            INT    unsigned NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno e Determina di Liquidazione',
  `data_documento`            DATE            NULL  COMMENT 'Per Determina di Prenotazione, Determina d''Impegno, Determina di Liquidazione e Mandato di Pagamento',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_tipo_step_finanziario_contab` FOREIGN KEY (`tipo_step_finanziario_id`) REFERENCES `tipo_step_finanziario_contab` (`id`),
  CONSTRAINT `fk_step_finanziario_cup`         FOREIGN KEY (`step_finanziario_cup_id`)  REFERENCES `step_finanziario_cup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

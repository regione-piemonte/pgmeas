CREATE TABLE `step_finanziario_cup` (
  `id`                        BIGINT unsigned  NOT NULL AUTO_INCREMENT,
  `cup`                       VARCHAR(50)      NOT NULL,
  `status`                    VARCHAR(3)       NULL,
  `title`                     LONGTEXT         NULL,
  `detail`                    LONGTEXT         NULL,
  `datetimecall`              DATETIME         DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

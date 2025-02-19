DROP TABLE IF EXISTS progetto_raggruppamento;

CREATE TABLE `progetto_raggruppamento` (
  `id`                  bigint unsigned NOT NULL AUTO_INCREMENT,
--  `id_raggruppamento`   bigint unsigned NOT NULL,
  `tenant`              bigint unsigned NOT NULL,
  `cup`                 varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`) /*!80000 INVISIBLE */,
  UNIQUE KEY `id_key_logica` (`id`,`tenant`,`cup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

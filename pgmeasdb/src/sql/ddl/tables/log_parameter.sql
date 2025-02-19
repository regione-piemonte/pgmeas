CREATE TABLE `log_parameter` (
  `id_log_parameter` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'identificativo log parameter',
  `param_key` varchar(255) NOT NULL COMMENT 'chiave parametro',
  `param_value` varchar(16000) DEFAULT NULL COMMENT 'valore parametro',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'data creazione',
  `updated_date` datetime DEFAULT NULL COMMENT 'data aggiornamento',
  `log_action_id` bigint unsigned DEFAULT NULL COMMENT 'identificativo log',
  PRIMARY KEY (`id_log_parameter`),
  KEY `log_parameter_FK` (`log_action_id`),
  CONSTRAINT `log_parameter_FK` FOREIGN KEY (`log_action_id`) REFERENCES `log_action` (`id_log_action`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

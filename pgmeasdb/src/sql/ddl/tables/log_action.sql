CREATE TABLE `log_action` (
  `id_log_action` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'identificativo log',
  `ip_source` varchar(50) DEFAULT NULL,
  `ip_destination` varchar(50) DEFAULT NULL,
  `api_key` bigint unsigned DEFAULT NULL COMMENT 'codice api key',
  `username` varchar(100) NOT NULL,
  `activity_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `flag_send_gdpr` char(1) NOT NULL,
  `http_method` varchar(10) DEFAULT NULL COMMENT 'http method',
  `http_function` varchar(255) DEFAULT NULL COMMENT 'http function',
  `http_status` bigint unsigned DEFAULT NULL COMMENT 'http status',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'data creazione',
  `updated_date` datetime DEFAULT NULL COMMENT 'data aggiornamento',
  PRIMARY KEY (`id_log_action`),
  KEY `key_username` (`username`),
  KEY `key_created_date` (`created_date`),
  KEY `key_username_created_date` (`username`,`created_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

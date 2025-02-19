/*****************************
 * Definizione delle tabelle *
 *****************************/
CREATE TABLE `applicazione` (
  `id_applicazione` bigint unsigned NOT NULL AUTO_INCREMENT,
  `api_key` int NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `data_eliminazione` datetime DEFAULT NULL,
  PRIMARY KEY (`id_applicazione`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `fase_ciclo_finanziario` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `funzionalita` (
  `id_funzionalita` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice_funzionalita` varchar(10) NOT NULL,
  `desc_funzionalita` varchar(255) NOT NULL,
  `order` int unsigned NOT NULL,
  PRIMARY KEY (`id_funzionalita`),
  UNIQUE KEY `id_UNIQUE` (`id_funzionalita`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `funzione` (
  `id_funzione` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice` varchar(50) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  PRIMARY KEY (`id_funzione`),
  UNIQUE KEY `uk_funzione` (`codice`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfasse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfazioni` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfcantiere` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfciclofinanziario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `step` varchar(255) DEFAULT NULL,
  `valore` double DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `stato` varchar(255) DEFAULT NULL,
  `idprogetto` int DEFAULT NULL,
  `idfase` int DEFAULT NULL,
  `datafine` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=443 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfcircoscrizione` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5756 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfclausole` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfcommento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(255) DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  `testo` varchar(255) DEFAULT NULL,
  `idfase` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfcontributoprevisto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfeffettivo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itrimeff` double DEFAULT NULL,
  `iitrimeff` double DEFAULT NULL,
  `iiitrimeff` double DEFAULT NULL,
  `ivtrimeff` double DEFAULT NULL,
  `idanno` int DEFAULT NULL,
  `idfase` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmffaseprocedurale` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `idprogetto` int DEFAULT NULL,
  `datainizioprev` date DEFAULT NULL,
  `datafineprev` date DEFAULT NULL,
  `datainizioeff` date DEFAULT NULL,
  `datafineeff` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1862 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmffondo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` int DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  `type_fund` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfinserimentostrumenti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfobiettivospecifico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfprogrammi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=278 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfrisorse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfsottofasi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `milestone` varchar(255) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `stato` varchar(255) DEFAULT NULL,
  `idfaseprocedurale` int DEFAULT NULL,
  `datafine` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=399 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmfstrutturadiriferimento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmftag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=650 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmftipologia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gmftipologiaprocedura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `order` double DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `tenant` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `progetto_gerarchia` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `progetto_id` int NOT NULL,
  `ente_id` int NOT NULL,
  `cod_tipologia_gerarchia` varchar(5) NOT NULL,
  `elemento_gerarchia_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ruolo` (
  `id_ruolo` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice` varchar(20) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  PRIMARY KEY (`id_ruolo`),
  UNIQUE KEY `uk_ruolo` (`codice`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tipologia_ente` (
  `id_tipologia_ente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `desc_tipologia_ente` varchar(255) NOT NULL,
  PRIMARY KEY (`id_tipologia_ente`),
  UNIQUE KEY `uk_tipologia_ente` (`id_tipologia_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tipologia_gerarchia` (
  `cod_tipologia_gerarchia` varchar(5) NOT NULL,
  `des_tipologia` varchar(50) NOT NULL,
  PRIMARY KEY (`cod_tipologia_gerarchia`),
  UNIQUE KEY `id_tipologia_UNIQUE` (`cod_tipologia_gerarchia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tipoperaz_faseproced` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `tipologia_operazione_id` int NOT NULL,
  `cod_fase` int DEFAULT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `tenant` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `utente` (
  `id_utente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `cognome` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `lingua` varchar(2) NOT NULL DEFAULT 'IT',
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `data_ultimo_accesso` datetime DEFAULT NULL,
  PRIMARY KEY (`id_utente`),
  UNIQUE KEY `key01` (`username`,`data_inizio_validita`,`data_fine_validita`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `elemento_gerarchia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ente_id` int NOT NULL,
  `cod_tipologia_gerarchia` varchar(5) NOT NULL,
  `des_elemento` varchar(255) NOT NULL,
  `attivo` tinyint NOT NULL DEFAULT '1',
  `cod_livello_elemento` varchar(45) NOT NULL,
  `order` int DEFAULT NULL,
  `id_pk_old` bigint DEFAULT NULL,
  `nome_tabella_old` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tipologia_gerarchia_idx` (`cod_tipologia_gerarchia`),
  CONSTRAINT `fk_tipologia_gerarchia` FOREIGN KEY (`cod_tipologia_gerarchia`) REFERENCES `tipologia_gerarchia` (`cod_tipologia_gerarchia`)
) ENGINE=InnoDB AUTO_INCREMENT=3261 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ente` (
  `id_ente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(255) NOT NULL,
  `abilitato` tinyint NOT NULL DEFAULT '0',
  `id_immagine` bigint unsigned DEFAULT NULL,
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `id_tipologia_ente` bigint unsigned NOT NULL,
  `codice_fiscale` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id_ente`),
  KEY `fk_ente_tipologia` (`id_tipologia_ente`),
  CONSTRAINT `fk_ente_tipologia` FOREIGN KEY (`id_tipologia_ente`) REFERENCES `tipologia_ente` (`id_tipologia_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `preference` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `ente_id` bigint unsigned DEFAULT NULL,
  `direzione_id` bigint unsigned DEFAULT NULL,
  `cod_funz` varchar(45) NOT NULL,
  `nome_filtro` varchar(255) NOT NULL,
  `preference` json DEFAULT NULL,
  `flag_predefinito` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk1_user_idx` (`user_id`),
  KEY `fk2_ente_idx` (`ente_id`),
  CONSTRAINT `fk1_user` FOREIGN KEY (`user_id`) REFERENCES `utente` (`id_utente`),
  CONSTRAINT `fk2_ente` FOREIGN KEY (`ente_id`) REFERENCES `ente` (`id_ente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ruolo_funzionalita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ruolo_id` bigint unsigned NOT NULL,
  `funzionalita_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_ruolo` (`ruolo_id`),
  KEY `fk1_funzionalita` (`funzionalita_id`),
  CONSTRAINT `fk1_funzionalita` FOREIGN KEY (`funzionalita_id`) REFERENCES `funzionalita` (`id_funzionalita`),
  CONSTRAINT `fk1_ruolo` FOREIGN KEY (`ruolo_id`) REFERENCES `ruolo` (`id_ruolo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ruolo_funzione` (
  `id_ruolo` bigint unsigned NOT NULL,
  `id_funzione` bigint unsigned NOT NULL,
  PRIMARY KEY (`id_ruolo`,`id_funzione`),
  KEY `fk_ra_funzione` (`id_funzione`),
  CONSTRAINT `fk_ra_funzione` FOREIGN KEY (`id_funzione`) REFERENCES `funzione` (`id_funzione`),
  CONSTRAINT `fk_ra_ruolo` FOREIGN KEY (`id_ruolo`) REFERENCES `ruolo` (`id_ruolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `struttura_gerarchia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `elemento_gerarchia_id_padre` bigint DEFAULT NULL,
  `elemento_gerarchia_id_figlio` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_gerarchia1` (`elemento_gerarchia_id_padre`,`elemento_gerarchia_id_figlio`),
  KEY `fk_id_Elemento_Gerarchia_figlio_idx` (`elemento_gerarchia_id_figlio`),
  CONSTRAINT `fk_id_Elemento_Gerarchia_figlio` FOREIGN KEY (`elemento_gerarchia_id_figlio`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_id_Elemento_Gerarchia_padre` FOREIGN KEY (`elemento_gerarchia_id_padre`) REFERENCES `elemento_gerarchia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3198 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `direzione` (
  `id_direzione` bigint unsigned NOT NULL AUTO_INCREMENT,
  `flag_titolare` tinyint NOT NULL DEFAULT '0',
  `descrizione` varchar(255) NOT NULL,
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  `id_ente` bigint unsigned NOT NULL,
  PRIMARY KEY (`id_direzione`),
  KEY `fk_direzione_ente` (`id_ente`),
  CONSTRAINT `fk_direzione_ente` FOREIGN KEY (`id_ente`) REFERENCES `ente` (`id_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `direzione_gerarchia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `direzione_id_padre` bigint unsigned DEFAULT NULL,
  `direzione_id_figlio` bigint unsigned DEFAULT NULL,
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `username_creazione` varchar(255) NOT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_gerarchia_direzione` (`direzione_id_padre`,`direzione_id_figlio`,`data_inizio_validita`,`data_fine_validita`),
  KEY `fk_id_direzione_figlio` (`direzione_id_figlio`),
  CONSTRAINT `fk_id_direzione_figlio` FOREIGN KEY (`direzione_id_figlio`) REFERENCES `direzione` (`id_direzione`),
  CONSTRAINT `fk_id_direzione_padre` FOREIGN KEY (`direzione_id_padre`) REFERENCES `direzione` (`id_direzione`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `direzione_utente_ruolo` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `id_applicazione` bigint unsigned NOT NULL,
  `id_utente` bigint unsigned NOT NULL,
  `id_direzione` bigint unsigned NOT NULL,
  `id_ruolo` bigint unsigned NOT NULL,
  `flag_direzione_default` tinyint NOT NULL DEFAULT '0',
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username_creazione` varchar(255) NOT NULL,
  `data_aggiornamento` datetime DEFAULT NULL,
  `username_aggiornamento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `key_unique` (`id_applicazione`,`id_utente`,`id_direzione`,`id_ruolo`,`data_inizio_validita`),
  KEY `fk_audr_direzione` (`id_direzione`),
  KEY `fk_audr_ruolo` (`id_ruolo`),
  KEY `fk_audr_utente` (`id_utente`),
  CONSTRAINT `fk_audr_applicazione` FOREIGN KEY (`id_applicazione`) REFERENCES `applicazione` (`id_applicazione`),
  CONSTRAINT `fk_audr_direzione` FOREIGN KEY (`id_direzione`) REFERENCES `direzione` (`id_direzione`),
  CONSTRAINT `fk_audr_ruolo` FOREIGN KEY (`id_ruolo`) REFERENCES `ruolo` (`id_ruolo`),
  CONSTRAINT `fk_audr_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `progetto` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `titolopropostaprogettuale` varchar(255) DEFAULT NULL,
  `oggettodelfinanziamento` varchar(255) DEFAULT NULL,
  `descrizionedelprogetto` text,
  `titolaritdelprogetto` varchar(255) DEFAULT NULL,
  `eventualialtrisoggetticoxca` varchar(255) DEFAULT NULL,
  `strutturadiriferimento` varchar(255) DEFAULT NULL,
  `fabbisognofinanziario` double DEFAULT NULL COMMENT 'Totale Accertato',
  `tipologiaproceduraattivahzy` text,
  `parerinecessari` varchar(255) DEFAULT NULL,
  `altro` varchar(255) DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `createdon` datetime DEFAULT NULL,
  `updatedby` varchar(255) DEFAULT NULL,
  `updatedon` datetime DEFAULT NULL,
  `codice` varchar(255) DEFAULT NULL,
  `gmfasse_asse_id` int DEFAULT NULL,
  `gmffondo_fondo_id` int DEFAULT NULL,
  `gmfbttvspcfc_bttvspcific_id` int DEFAULT NULL,
  `gmfazioni_azioni_id` int DEFAULT NULL,
  `gmfprogrammi_programma_id` int DEFAULT NULL,
  `altrerisorse` double DEFAULT NULL,
  `risorseprivate` double DEFAULT NULL,
  `costototale` double DEFAULT NULL,
  `evntlfntdifinnzimentriginri` double DEFAULT NULL,
  `gmfmisura_misura_id` int DEFAULT NULL,
  `missione_id` bigint DEFAULT NULL,
  `componente_id` bigint DEFAULT NULL,
  `investimento_id` bigint DEFAULT NULL,
  `sub_investimento_id` bigint DEFAULT NULL,
  `gmftiplgia_tiplgiaperazinid` int DEFAULT NULL,
  `gmfmissioni_missione_id` int DEFAULT NULL,
  `obiettivodipolicy` varchar(255) DEFAULT NULL,
  `titolaritadelprogetto` varchar(255) DEFAULT NULL,
  `altrisoggetticoinvolti` varchar(255) DEFAULT NULL,
  `gmftplgprcdr_tiplgiprcdr_id` int DEFAULT NULL,
  `gmfstrttrdrf_strttrdrfrmn_d` int DEFAULT NULL,
  `gmfnsrmntstr_nsrmntstrmnt_d` int DEFAULT NULL,
  `direzione_id` bigint unsigned DEFAULT NULL,
  `tenant` bigint unsigned DEFAULT NULL,
  `intervento` varchar(255) DEFAULT NULL,
  `risorsestanziate` double DEFAULT NULL,
  `tiplgiaprcedradiaffidamento` varchar(255) DEFAULT NULL,
  `freemisura` varchar(255) DEFAULT NULL,
  `appaltatori` varchar(255) DEFAULT NULL,
  `subappaltatori` varchar(255) DEFAULT NULL,
  `direttorelavori` varchar(255) DEFAULT NULL,
  `gmfclausole_clausole_id` int DEFAULT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  `comune` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `regione` varchar(255) DEFAULT NULL,
  `gmfrisorse_risorse_id` int DEFAULT NULL,
  `gmfcntrbtprvs_cntrbtprvst_d` int DEFAULT NULL,
  `gmfcantiere_cantiere_id` int DEFAULT NULL,
  `piva` varchar(255) DEFAULT NULL,
  `data_inizio_validita` datetime NOT NULL,
  `data_fine_validita` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gmfasse_idx` (`gmfasse_asse_id`),
  KEY `fk_gmfcantiere_idx` (`gmfcantiere_cantiere_id`),
  KEY `fk_gmfcontributoprevisto_idx` (`gmfcntrbtprvs_cntrbtprvst_d`),
  KEY `fk_gmfclausole_idx` (`gmfclausole_clausole_id`),
  KEY `fk_gmffondo_idx` (`gmffondo_fondo_id`),
  KEY `fk_gmfinserimentostrumenti_idx` (`gmfnsrmntstr_nsrmntstrmnt_d`),
  KEY `fk_obiettivospecifico_idx` (`gmfbttvspcfc_bttvspcific_id`),
  KEY `fk_gmfprogrammi_idx` (`gmfprogrammi_programma_id`),
  KEY `fk_gmfrisorse_idx` (`gmfrisorse_risorse_id`),
  KEY `fk_gmfstrutturadiriferimento_idx` (`gmfstrttrdrf_strttrdrfrmn_d`),
  KEY `fk_gmftipologiaprocedura_idx` (`gmftplgprcdr_tiplgiprcdr_id`),
  KEY `fk_gmfmissione_idx` (`missione_id`),
  KEY `fk_gmfcomponente_idx` (`componente_id`),
  KEY `fk_gmfinvestimento_idx` (`investimento_id`),
  KEY `fk_gmfsubinvestimento_idx` (`sub_investimento_id`),
  KEY `fk_tenant_idx` (`tenant`),
  KEY `fk_direzione_idx` (`direzione_id`),
  KEY `idx_ente_direzione` (`tenant`,`direzione_id`),
  KEY `idx_ente_programma` (`tenant`,`gmfprogrammi_programma_id`),
  KEY `idx_ente_fondo` (`tenant`,`gmffondo_fondo_id`),
  CONSTRAINT `fk_direzione` FOREIGN KEY (`direzione_id`) REFERENCES `direzione` (`id_direzione`),
  CONSTRAINT `fk_gmfasse` FOREIGN KEY (`gmfasse_asse_id`) REFERENCES `gmfasse` (`id`),
  CONSTRAINT `fk_gmfcantiere` FOREIGN KEY (`gmfcantiere_cantiere_id`) REFERENCES `gmfcantiere` (`id`),
  CONSTRAINT `fk_gmfclausole` FOREIGN KEY (`gmfclausole_clausole_id`) REFERENCES `gmfclausole` (`id`),
  CONSTRAINT `fk_gmfcomponente` FOREIGN KEY (`componente_id`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_gmfcontributoprevisto` FOREIGN KEY (`gmfcntrbtprvs_cntrbtprvst_d`) REFERENCES `gmfcontributoprevisto` (`id`),
  CONSTRAINT `fk_gmffondo` FOREIGN KEY (`gmffondo_fondo_id`) REFERENCES `gmffondo` (`id`),
  CONSTRAINT `fk_gmfinserimentostrumenti` FOREIGN KEY (`gmfnsrmntstr_nsrmntstrmnt_d`) REFERENCES `gmfinserimentostrumenti` (`id`),
  CONSTRAINT `fk_gmfinvestimento` FOREIGN KEY (`investimento_id`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_gmfmissione` FOREIGN KEY (`missione_id`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_gmfobiettivospecifico` FOREIGN KEY (`gmfbttvspcfc_bttvspcific_id`) REFERENCES `gmfobiettivospecifico` (`id`),
  CONSTRAINT `fk_gmfprogrammi` FOREIGN KEY (`gmfprogrammi_programma_id`) REFERENCES `gmfprogrammi` (`id`),
  CONSTRAINT `fk_gmfrisorse` FOREIGN KEY (`gmfrisorse_risorse_id`) REFERENCES `gmfrisorse` (`id`),
  CONSTRAINT `fk_gmfstrutturadiriferimento` FOREIGN KEY (`gmfstrttrdrf_strttrdrfrmn_d`) REFERENCES `gmfstrutturadiriferimento` (`id`),
  CONSTRAINT `fk_gmfsubinvestimento` FOREIGN KEY (`sub_investimento_id`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_gmftipologiaprocedura` FOREIGN KEY (`gmftplgprcdr_tiplgiprcdr_id`) REFERENCES `gmftipologiaprocedura` (`id`),
  CONSTRAINT `fk_tenant` FOREIGN KEY (`tenant`) REFERENCES `ente` (`id_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=1628 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `progetto_circoscrizione` (
  `id_progetto_circoscrizione` bigint NOT NULL AUTO_INCREMENT,
  `progetto_id` bigint unsigned NOT NULL,
  `circoscrizione_id` bigint NOT NULL,
  `ordine` double DEFAULT NULL,
  PRIMARY KEY (`id_progetto_circoscrizione`),
  KEY `fk_progetto_circoscrizione_idx` (`progetto_id`),
  CONSTRAINT `fk_progetto_circoscrizione` FOREIGN KEY (`progetto_id`) REFERENCES `progetto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65536 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `progetto_tag` (
  `id_progetto_tag` bigint NOT NULL AUTO_INCREMENT,
  `progetto_id` bigint unsigned NOT NULL,
  `tag_id` int DEFAULT NULL,
  `ordine` int NOT NULL,
  PRIMARY KEY (`id_progetto_tag`),
  KEY `fk_progetto_tag_idx` (`progetto_id`),
  CONSTRAINT `fk_progetto_tag` FOREIGN KEY (`progetto_id`) REFERENCES `progetto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

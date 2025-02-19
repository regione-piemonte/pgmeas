-- progetto_gerarchia definition
CREATE TABLE `progetto_gerarchia` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `progetto_id` int NOT NULL,
  `ente_id` int NOT NULL,
  `cod_tipologia_gerarchia` varchar(5) NOT NULL,
  `elemento_gerarchia_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- tipologia_gerarchia definition
CREATE TABLE `tipologia_gerarchia` (
  `cod_tipologia_gerarchia` varchar(5) NOT NULL,
  `des_tipologia` varchar(50) NOT NULL,
  PRIMARY KEY (`cod_tipologia_gerarchia`),
  UNIQUE KEY `id_tipologia_UNIQUE` (`cod_tipologia_gerarchia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- elemento_gerarchia definition
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- struttura_gerarchia definition
CREATE TABLE `struttura_gerarchia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `elemento_gerarchia_id_padre` bigint DEFAULT NULL,
  `elemento_gerarchia_id_figlio` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_gerarchia1` (`elemento_gerarchia_id_padre`,`elemento_gerarchia_id_figlio`),
  KEY `fk_id_Elemento_Gerarchia_figlio_idx` (`elemento_gerarchia_id_figlio`),
  CONSTRAINT `fk_id_Elemento_Gerarchia_figlio` FOREIGN KEY (`elemento_gerarchia_id_figlio`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_id_Elemento_Gerarchia_padre` FOREIGN KEY (`elemento_gerarchia_id_padre`) REFERENCES `elemento_gerarchia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- log_action definition
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
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- log_parameter definition
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
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- applicazione definition
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- funzione definition
CREATE TABLE `funzione` (
  `id_funzione` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice` varchar(50) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  PRIMARY KEY (`id_funzione`),
  UNIQUE KEY `uk_funzione` (`codice`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ruolo definition
CREATE TABLE `ruolo` (
  `id_ruolo` bigint unsigned NOT NULL AUTO_INCREMENT,
  `codice` varchar(20) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  PRIMARY KEY (`id_ruolo`),
  UNIQUE KEY `uk_ruolo` (`codice`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ruolo_funzione definition
CREATE TABLE `ruolo_funzione` (
  `id_ruolo` bigint unsigned NOT NULL,
  `id_funzione` bigint unsigned NOT NULL,
  PRIMARY KEY (`id_ruolo`,`id_funzione`),
  KEY `fk_ra_funzione` (`id_funzione`),
  CONSTRAINT `fk_ra_funzione` FOREIGN KEY (`id_funzione`) REFERENCES `funzione` (`id_funzione`),
  CONSTRAINT `fk_ra_ruolo` FOREIGN KEY (`id_ruolo`) REFERENCES `ruolo` (`id_ruolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- utente definition
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- tipologia_ente definition
CREATE TABLE `tipologia_ente` (
  `id_tipologia_ente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `desc_tipologia_ente` varchar(255) NOT NULL,
  PRIMARY KEY (`id_tipologia_ente`),
  UNIQUE KEY `uk_tipologia_ente` (`id_tipologia_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- ente definition
CREATE TABLE `ente` (
  `id_ente` bigint unsigned NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(255) NOT NULL,
  `abilitato` tinyint NOT NULL DEFAULT '0',
  `id_immagine` bigint unsigned,
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




-- direzione definition
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
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- api_direzione_utente_ruolo definition
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
  CONSTRAINT `fk_audr_applicazione` FOREIGN KEY (`id_applicazione`) REFERENCES `applicazione` (`id_applicazione`),
  CONSTRAINT `fk_audr_direzione` FOREIGN KEY (`id_direzione`) REFERENCES `direzione` (`id_direzione`),
  CONSTRAINT `fk_audr_ruolo` FOREIGN KEY (`id_ruolo`) REFERENCES `ruolo` (`id_ruolo`),
  CONSTRAINT `fk_audr_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- progetto definition
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
  -- `gmftplgprcdr_tplgprcdrffd_d` int DEFAULT NULL,
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
--  KEY `fk_gmfmisura_idx` (`gmfmisura_misura_id`),
  KEY `fk_obiettivospecifico_idx` (`gmfbttvspcfc_bttvspcific_id`),
  KEY `fk_gmfprogrammi_idx` (`gmfprogrammi_programma_id`),
  KEY `fk_gmfrisorse_idx` (`gmfrisorse_risorse_id`),
  KEY `fk_gmfstrutturadiriferimento_idx` (`gmfstrttrdrf_strttrdrfrmn_d`),
--  KEY `fk_gmftipprocaffidam_idx` (`gmftplgprcdr_tplgprcdrffd_d`),
  KEY `fk_gmftipologiaprocedura_idx` (`gmftplgprcdr_tiplgiprcdr_id`),
  KEY `fk_gmfmissione_idx` (`missione_id`),
  KEY `fk_gmfcomponente_idx` (`componente_id`),
  KEY `fk_gmfinvestimento_idx` (`investimento_id`),
  KEY `fk_gmfsubinvestimento_idx` (`sub_investimento_id`),
  KEY `fk_tenant_idx` (`tenant`),
  KEY `fk_direzione_idx` (`direzione_id`),
  CONSTRAINT `fk_gmfasse` FOREIGN KEY (`gmfasse_asse_id`) REFERENCES `gmfasse` (`id`),
  CONSTRAINT `fk_gmfcantiere` FOREIGN KEY (`gmfcantiere_cantiere_id`) REFERENCES `gmfcantiere` (`id`),
  CONSTRAINT `fk_gmfclausole` FOREIGN KEY (`gmfclausole_clausole_id`) REFERENCES `gmfclausole` (`id`),
  CONSTRAINT `fk_gmfcomponente` FOREIGN KEY (`componente_id`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_gmfcontributoprevisto` FOREIGN KEY (`gmfcntrbtprvs_cntrbtprvst_d`) REFERENCES `gmfcontributoprevisto` (`id`),
  CONSTRAINT `fk_gmffondo` FOREIGN KEY (`gmffondo_fondo_id`) REFERENCES `gmffondo` (`id`),
  CONSTRAINT `fk_gmfinserimentostrumenti` FOREIGN KEY (`gmfnsrmntstr_nsrmntstrmnt_d`) REFERENCES `gmfinserimentostrumenti` (`id`),
  CONSTRAINT `fk_gmfinvestimento` FOREIGN KEY (`investimento_id`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_gmfmissione` FOREIGN KEY (`missione_id`) REFERENCES `elemento_gerarchia` (`id`),
--  CONSTRAINT `fk_gmfmisura` FOREIGN KEY (`gmfmisura_misura_id`) REFERENCES `gmfmisura` (`id`),
  CONSTRAINT `fk_gmfobiettivospecifico` FOREIGN KEY (`gmfbttvspcfc_bttvspcific_id`) REFERENCES `gmfobiettivospecifico` (`id`),
  CONSTRAINT `fk_gmfprogrammi` FOREIGN KEY (`gmfprogrammi_programma_id`) REFERENCES `gmfprogrammi` (`id`),
  CONSTRAINT `fk_gmfrisorse` FOREIGN KEY (`gmfrisorse_risorse_id`) REFERENCES `gmfrisorse` (`id`),
  CONSTRAINT `fk_gmfstrutturadiriferimento` FOREIGN KEY (`gmfstrttrdrf_strttrdrfrmn_d`) REFERENCES `gmfstrutturadiriferimento` (`id`),
  CONSTRAINT `fk_gmfsubinvestimento` FOREIGN KEY (`sub_investimento_id`) REFERENCES `elemento_gerarchia` (`id`),
  CONSTRAINT `fk_gmftipologiaprocedura` FOREIGN KEY (`gmftplgprcdr_tiplgiprcdr_id`) REFERENCES `gmftipologiaprocedura` (`id`),
  -- CONSTRAINT `fk_gmftipprocaffidam` FOREIGN KEY (`gmftplgprcdr_tplgprcdrffd_d`) REFERENCES `gmftiplgiaprcedraaffidament` (`id`),
  CONSTRAINT `fk_direzione` FOREIGN KEY (`direzione_id`) REFERENCES `direzione` (`id_direzione`),
  CONSTRAINT `fk_tenant` FOREIGN KEY (`tenant`) REFERENCES `ente` (`id_ente`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- vw_componenti_progetto_old source
CREATE OR REPLACE
ALGORITHM = UNDEFINED VIEW `vw_componenti_progetto_old` AS
select
    `qckapp_g_main`.`id` AS `id_progetto_old`,
    `qckapp_g_main`.`tenant` AS `id_ente_old`,
    (case
        when (`qckapp_g_main`.`gmfcmpnenti1_componente1_id` is not null) then 'gmfcomponente1'
        when (`qckapp_g_main`.`gmfcmpnente2_componente2_id` is not null) then 'gmfcomponente2'
        when (`qckapp_g_main`.`gmfcmpnente3_componente3_id` is not null) then 'gmfcomponente3'
        when (`qckapp_g_main`.`gmfcmpnente4_componente4_id` is not null) then 'gmfcomponente4'
        when (`qckapp_g_main`.`gmfcmpnente5_componente5_id` is not null) then 'gmfcomponente5'
        when (`qckapp_g_main`.`gmfcmpnente6_componente6_id` is not null) then 'gmfcomponente6'
    end) AS `tabella_in_fk`,
    (case
        when (`qckapp_g_main`.`gmfcmpnenti1_componente1_id` is not null) then `qckapp_g_main`.`gmfcmpnenti1_componente1_id`
        when (`qckapp_g_main`.`gmfcmpnente2_componente2_id` is not null) then `qckapp_g_main`.`gmfcmpnente2_componente2_id`
        when (`qckapp_g_main`.`gmfcmpnente3_componente3_id` is not null) then `qckapp_g_main`.`gmfcmpnente3_componente3_id`
        when (`qckapp_g_main`.`gmfcmpnente4_componente4_id` is not null) then `qckapp_g_main`.`gmfcmpnente4_componente4_id`
        when (`qckapp_g_main`.`gmfcmpnente5_componente5_id` is not null) then `qckapp_g_main`.`gmfcmpnente5_componente5_id`
        when (`qckapp_g_main`.`gmfcmpnente6_componente6_id` is not null) then `qckapp_g_main`.`gmfcmpnente6_componente6_id`
    end) AS `id_componente_old`
from
    `qckapp_g_main`;


-- vw_investimenti_progetto_old source
CREATE OR REPLACE
ALGORITHM = UNDEFINED VIEW `vw_investimenti_progetto_old` AS
select
    `qckapp_g_main`.`id` AS `id_progetto_old`,
    `qckapp_g_main`.`tenant` AS `id_ente_old`,
    (case
        when (`qckapp_g_main`.`gmfinvstimnt1_invstimnt1_id` is not null) then 'gmfinvestimento1'
        when (`qckapp_g_main`.`gmfinvstmnt12_nvstimnt12_id` is not null) then 'gmfinvestimento12'
        when (`qckapp_g_main`.`gmfinstmnt13_invstimnt13_id` is not null) then 'gmfinvestimento13'
        when (`qckapp_g_main`.`gmfinvstmnt21_nvstimnt21_id` is not null) then 'gmfinvestimento21'
        when (`qckapp_g_main`.`gmfinvstmnt22_nvstimnt22_id` is not null) then 'gmfinvestimento22'
        when (`qckapp_g_main`.`gmfinvstmnt23_nvstimnt23_id` is not null) then 'gmfinvestimento23'
        when (`qckapp_g_main`.`gmfinvstmnt24_nvstimnt24_id` is not null) then 'gmfinvestimento24'
        when (`qckapp_g_main`.`gmfinvstmnt31_nvstimnt31_id` is not null) then 'gmfinvestimento31'
        when (`qckapp_g_main`.`gmfinvstmnt32_nvstimnt32_id` is not null) then 'gmfinvestimento32'
        when (`qckapp_g_main`.`gmfinvstmnt41_nvstimnt41_id` is not null) then 'gmfinvestimento41'
        when (`qckapp_g_main`.`gmfinvstmnt42_nvstimnt42_id` is not null) then 'gmfinvestimento42'
        when (`qckapp_g_main`.`gmfinvstmnt51_nvstimnt51_id` is not null) then 'gmfinvestimento51'
        when (`qckapp_g_main`.`gmfinvstmnt52_nvstimnt52_id` is not null) then 'gmfinvestimento52'
        when (`qckapp_g_main`.`gmfinvstmnt53_nvstimnt53_id` is not null) then 'gmfinvestimento53'
        when (`qckapp_g_main`.`gmfinvstmnt61_nvstimnt61_id` is not null) then 'gmfinvestimento61'
        when (`qckapp_g_main`.`gmfinvstmnt62_nvstimnt62_id` is not null) then 'gmfinvestimento61'
    end) AS `tabella_in_fk`,
    (case
        when (`qckapp_g_main`.`gmfinvstimnt1_invstimnt1_id` is not null) then `qckapp_g_main`.`gmfinvstimnt1_invstimnt1_id`
        when (`qckapp_g_main`.`gmfinvstmnt12_nvstimnt12_id` is not null) then `qckapp_g_main`.`gmfinvstmnt12_nvstimnt12_id`
        when (`qckapp_g_main`.`gmfinstmnt13_invstimnt13_id` is not null) then `qckapp_g_main`.`gmfinstmnt13_invstimnt13_id`
        when (`qckapp_g_main`.`gmfinvstmnt21_nvstimnt21_id` is not null) then `qckapp_g_main`.`gmfinvstmnt21_nvstimnt21_id`
        when (`qckapp_g_main`.`gmfinvstmnt22_nvstimnt22_id` is not null) then `qckapp_g_main`.`gmfinvstmnt22_nvstimnt22_id`
        when (`qckapp_g_main`.`gmfinvstmnt23_nvstimnt23_id` is not null) then `qckapp_g_main`.`gmfinvstmnt23_nvstimnt23_id`
        when (`qckapp_g_main`.`gmfinvstmnt24_nvstimnt24_id` is not null) then `qckapp_g_main`.`gmfinvstmnt24_nvstimnt24_id`
        when (`qckapp_g_main`.`gmfinvstmnt31_nvstimnt31_id` is not null) then `qckapp_g_main`.`gmfinvstmnt31_nvstimnt31_id`
        when (`qckapp_g_main`.`gmfinvstmnt32_nvstimnt32_id` is not null) then `qckapp_g_main`.`gmfinvstmnt32_nvstimnt32_id`
        when (`qckapp_g_main`.`gmfinvstmnt41_nvstimnt41_id` is not null) then `qckapp_g_main`.`gmfinvstmnt41_nvstimnt41_id`
        when (`qckapp_g_main`.`gmfinvstmnt42_nvstimnt42_id` is not null) then `qckapp_g_main`.`gmfinvstmnt42_nvstimnt42_id`
        when (`qckapp_g_main`.`gmfinvstmnt51_nvstimnt51_id` is not null) then `qckapp_g_main`.`gmfinvstmnt51_nvstimnt51_id`
        when (`qckapp_g_main`.`gmfinvstmnt52_nvstimnt52_id` is not null) then `qckapp_g_main`.`gmfinvstmnt52_nvstimnt52_id`
        when (`qckapp_g_main`.`gmfinvstmnt53_nvstimnt53_id` is not null) then `qckapp_g_main`.`gmfinvstmnt53_nvstimnt53_id`
        when (`qckapp_g_main`.`gmfinvstmnt61_nvstimnt61_id` is not null) then `qckapp_g_main`.`gmfinvstmnt61_nvstimnt61_id`
        when (`qckapp_g_main`.`gmfinvstmnt62_nvstimnt62_id` is not null) then `qckapp_g_main`.`gmfinvstmnt62_nvstimnt62_id`
    end) AS `id_investimento_old`
from
    `qckapp_g_main`;


-- vw_subinvestimenti_progetto_old source
CREATE OR REPLACE
ALGORITHM = UNDEFINED VIEW `vw_subinvestimenti_progetto_old` AS
select
    `qckapp_g_main`.`id` AS `id_progetto_old`,
    `qckapp_g_main`.`tenant` AS `id_ente_old`,
    (case
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt13_d` is not null) then 'gmfsubinvestimento13'
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt14_d` is not null) then 'gmfsubinvestimento14'
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt16_d` is not null) then 'gmfsubinvestimento16'
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt17_d` is not null) then 'gmfsubinvestimento17'
        when (`qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt21_d` is not null) then 'gmfsubinvestimento21'
        when (`qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt22_d` is not null) then 'gmfsubinvestimento22'
        when (`qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt23_d` is not null) then 'gmfsubinvestimento23'
        when (`qckapp_g_main`.`gmfsub311_sub311_id` is not null) then 'gmfsub311'
        when (`qckapp_g_main`.`gmfsub312_sub312_id` is not null) then 'gmfsub312'
        when (`qckapp_g_main`.`gmfsub313_sub313_id` is not null) then 'gmfsub313'
        when (`qckapp_g_main`.`gmfsub322_sub322_id` is not null) then 'gmfsub322'
        when (`qckapp_g_main`.`gmfsub321_sub321_id` is not null) then 'gmfsub321'
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt121_d` is not null) then 'gmfsubinvestimento121'
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt123_d` is not null) then 'gmfsubinvestimento123'
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt124_d` is not null) then 'gmfsubinvestimento124'
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt131_d` is not null) then 'gmfsubinvestimento131'
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt133_d` is not null) then 'gmfsubinvestimento133'
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt225_d` is not null) then 'gmfsubinvestimento225'
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt242_d` is not null) then 'gmfsubinvestimento242'
        when (`qckapp_g_main`.`gmfsub521_sub521_id` is not null) then 'gmfsub521'
        when (`qckapp_g_main`.`gmfsub522_sub522_id` is not null) then 'gmfsub522'
        when (`qckapp_g_main`.`gmfsub531_sub531_id` is not null) then 'gmfsub531'
        when (`qckapp_g_main`.`gmfsub53es_sub53es_id` is not null) then 'gmfsub53es'
        when (`qckapp_g_main`.`gmfsub611_sub611_id` is not null) then 'gmfsub611'
        when (`qckapp_g_main`.`gmfsub621_sub621_id` is not null) then 'gmfsub621'
        when (`qckapp_g_main`.`gmfsub622_sub622_id` is not null) then 'gmfsub622'
        when (`qckapp_g_main`.`gmfsub623_sub623_id` is not null) then 'gmfsub623'
        when (`qckapp_g_main`.`gmfsub1342_sub1342_id` is not null) then 'gmfsub1342'
        when (`qckapp_g_main`.`gmfsub1343_sub1343_id` is not null) then 'gmfsub1343'
        when (`qckapp_g_main`.`gmfsub125_sub125_id` is not null) then 'gmfsub125'
        when (`qckapp_g_main`.`gmfsub221_sub221_id` is not null) then 'gmfsub221'
        when (`qckapp_g_main`.`gmfsub224_sub224_id` is not null) then 'gmfsub224'
    end) AS `tabella_in_fk`,
    (case
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt13_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt13_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt14_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt14_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt16_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt16_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt17_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt1_sbnvstmnt17_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt21_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt21_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt22_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt22_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt23_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt2_sbnvstmnt23_d`
        when (`qckapp_g_main`.`gmfsub311_sub311_id` is not null) then `qckapp_g_main`.`gmfsub311_sub311_id`
        when (`qckapp_g_main`.`gmfsub312_sub312_id` is not null) then `qckapp_g_main`.`gmfsub312_sub312_id`
        when (`qckapp_g_main`.`gmfsub313_sub313_id` is not null) then `qckapp_g_main`.`gmfsub313_sub313_id`
        when (`qckapp_g_main`.`gmfsub322_sub322_id` is not null) then `qckapp_g_main`.`gmfsub322_sub322_id`
        when (`qckapp_g_main`.`gmfsub321_sub321_id` is not null) then `qckapp_g_main`.`gmfsub321_sub321_id`
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt121_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt121_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt123_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt123_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt124_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt124_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt131_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt131_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt133_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt133_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt225_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt225_d`
        when (`qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt242_d` is not null) then `qckapp_g_main`.`gmfsbnvstmnt_sbnvstmnt242_d`
        when (`qckapp_g_main`.`gmfsub521_sub521_id` is not null) then `qckapp_g_main`.`gmfsub521_sub521_id`
        when (`qckapp_g_main`.`gmfsub522_sub522_id` is not null) then `qckapp_g_main`.`gmfsub522_sub522_id`
        when (`qckapp_g_main`.`gmfsub531_sub531_id` is not null) then `qckapp_g_main`.`gmfsub531_sub531_id`
        when (`qckapp_g_main`.`gmfsub53es_sub53es_id` is not null) then `qckapp_g_main`.`gmfsub53es_sub53es_id`
        when (`qckapp_g_main`.`gmfsub611_sub611_id` is not null) then `qckapp_g_main`.`gmfsub611_sub611_id`
        when (`qckapp_g_main`.`gmfsub621_sub621_id` is not null) then `qckapp_g_main`.`gmfsub621_sub621_id`
        when (`qckapp_g_main`.`gmfsub622_sub622_id` is not null) then `qckapp_g_main`.`gmfsub622_sub622_id`
        when (`qckapp_g_main`.`gmfsub623_sub623_id` is not null) then `qckapp_g_main`.`gmfsub623_sub623_id`
        when (`qckapp_g_main`.`gmfsub1342_sub1342_id` is not null) then `qckapp_g_main`.`gmfsub1342_sub1342_id`
        when (`qckapp_g_main`.`gmfsub1343_sub1343_id` is not null) then `qckapp_g_main`.`gmfsub1343_sub1343_id`
        when (`qckapp_g_main`.`gmfsub125_sub125_id` is not null) then `qckapp_g_main`.`gmfsub125_sub125_id`
        when (`qckapp_g_main`.`gmfsub221_sub221_id` is not null) then `qckapp_g_main`.`gmfsub221_sub221_id`
        when (`qckapp_g_main`.`gmfsub224_sub224_id` is not null) then `qckapp_g_main`.`gmfsub224_sub224_id`
    end) AS `id_subinvestimento_old`
from
    `qckapp_g_main`;


CREATE TABLE `progetto_circoscrizione` (
  `id_progetto_circoscrizione` bigint NOT NULL AUTO_INCREMENT,
  `progetto_id` bigint unsigned NOT NULL,
  `circoscrizione_id` bigint NOT NULL,
  `ordine` double DEFAULT NULL,
  PRIMARY KEY (`id_progetto_circoscrizione`),
  KEY `fk_progetto_circoscrizione_idx` (`progetto_id`),
  CONSTRAINT `fk_progetto_circoscrizione` FOREIGN KEY (`progetto_id`) REFERENCES `progetto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4437 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `progetto_tag` (
  `id_progetto_tag` bigint NOT NULL AUTO_INCREMENT,
  `progetto_id` bigint unsigned NOT NULL,
  `tag_id` int DEFAULT NULL,
  `ordine` int NOT NULL,
  PRIMARY KEY (`id_progetto_tag`),
  KEY `fk_progetto_tag_idx` (`progetto_id`),
  CONSTRAINT `fk_progetto_tag` FOREIGN KEY (`progetto_id`) REFERENCES `progetto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `fase_ciclo_finanziario` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `descrizione` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `tipoperaz_faseproced` (
  `id`                      INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tipologia_operazione_id` INT NOT NULL,
  `cod_fase`                INT NULL,
  `descrizione`             VARCHAR(100) NULL,
  `tenant`                  INT NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `preference` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `ente_id` bigint unsigned,
  `direzione_id` bigint unsigned,
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


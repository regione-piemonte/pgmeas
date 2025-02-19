DELETE FROM progetto;
ALTER TABLE progetto AUTO_INCREMENT = 1;

INSERT INTO progetto(
`id`,
`titolopropostaprogettuale`,
`oggettodelfinanziamento`,
`descrizionedelprogetto`,
`titolaritdelprogetto`,
`eventualialtrisoggetticoxca`,
`strutturadiriferimento`,
`fabbisognofinanziario`,
`tipologiaproceduraattivahzy`,
`parerinecessari`,
`altro`,
`createdby`,
`createdon`,
`updatedby`,
`updatedon`,
`codice`,
`gmfasse_asse_id`,
`gmffondo_fondo_id`,
`gmfbttvspcfc_bttvspcific_id`,
`gmfazioni_azioni_id`,
`gmfprogrammi_programma_id`,
`altrerisorse`,
`risorseprivate`,
`costototale`,
`evntlfntdifinnzimentriginri`,
`gmfmisura_misura_id`,
`gmftiplgia_tiplgiaperazinid`,
`tenant`,
`missione_id`,
`componente_id`,
`investimento_id`,
`sub_investimento_id`,
`obiettivodipolicy`,
`titolaritadelprogetto`,
`altrisoggetticoinvolti`,
`gmftplgprcdr_tiplgiprcdr_id`,
`gmfstrttrdrf_strttrdrfrmn_d`,
`gmfnsrmntstr_nsrmntstrmnt_d`,
`direzione_id`,
`intervento`,
`risorsestanziate`,
`tiplgiaprcedradiaffidamento`,
`freemisura`,
`appaltatori`,
`subappaltatori`,
`direttorelavori`,
`gmfclausole_clausole_id`,
`indirizzo`,
`comune`,
`provincia`,
`regione`,
`gmfrisorse_risorse_id`,
`gmfcntrbtprvs_cntrbtprvst_d`,
`gmfcantiere_cantiere_id`,
`piva`,
`data_inizio_validita`
)
SELECT
`id`, 
`titolopropostaprogettuale`,
`oggettodelfinanziamento`,
`descrizionedelprogetto`,
`titolaritdelprogetto`,
`eventualialtrisoggetticoxca`,
`strutturadiriferimento`,
`fabbisognofinanziario`,
`tipologiaproceduraattivahzy`,
`parerinecessari`,
`altro`,
`createdby`,
`createdon`,
`updatedby`,
`updatedon`,
`codice`,
`gmfasse_asse_id`,
`gmffondo_fondo_id`,
`gmfbttvspcfc_bttvspcific_id`,
`gmfazioni_azioni_id`,
`gmfprogrammi_programma_id`,
`altrerisorse`,
`risorseprivate`,
`costototale`,
`evntlfntdifinnzimentriginri`,
`gmfmisura_misura_id`,
`gmftiplgia_tiplgiaperazinid`,
`tenant`,
(select a.id 
    from elemento_gerarchia a
    where a.ente_id                 = qckapp_g_main.tenant 
    and   a.cod_tipologia_gerarchia = 'MIS' 
    and   a.id_pk_old               = qckapp_g_main.gmfmissioni_missione_id),
(select a.id 
    from elemento_gerarchia a, vw_componenti_progetto_old comp
    where a.ente_id                 = qckapp_g_main.tenant 
    and   comp.id_progetto_old      = qckapp_g_main.id
    and   a.cod_tipologia_gerarchia = 'COM'
    and   a.ente_id                 = comp.id_ente_old
    and   a.id_pk_old               = comp.id_componente_old
    and   a.nome_tabella_old        = comp.tabella_in_fk),    
(select a.id 
    from elemento_gerarchia a, vw_investimenti_progetto_old inv
    where a.ente_id                 = qckapp_g_main.tenant 
    and   inv.id_progetto_old       = qckapp_g_main.id
    and   a.cod_tipologia_gerarchia = 'INV'
    and   a.ente_id                 = inv.id_ente_old
    and   a.id_pk_old               = inv.id_investimento_old
    and   a.nome_tabella_old        = inv.tabella_in_fk),
(select a.id 
    from elemento_gerarchia a, vw_subinvestimenti_progetto_old inv
    where a.ente_id                 = qckapp_g_main.tenant 
    and   inv.id_progetto_old       = qckapp_g_main.id
    and   a.cod_tipologia_gerarchia = 'SUB'
    and   a.ente_id                 = inv.id_ente_old
    and   a.id_pk_old               = inv.id_subinvestimento_old
    and   a.nome_tabella_old        = inv.tabella_in_fk),
`obiettivodipolicy`,
`titolaritadelprogetto`,
`altrisoggetticoinvolti`,
`gmftplgprcdr_tiplgiprcdr_id`,
`gmfstrttrdrf_strttrdrfrmn_d`,
`gmfnsrmntstr_nsrmntstrmnt_d`,
`gmftplgprcdr_tplgprcdrffd_d`,
`intervento`,
`risorsestanziate`,
`tiplgiaprcedradiaffidamento`,
`freemisura`,
`appaltatori`,
`subappaltatori`,
`direttorelavori`,
`gmfclausole_clausole_id`,
`indirizzo`,
`comune`,
`provincia`,
`regione`,
`gmfrisorse_risorse_id`,
`gmfcntrbtprvs_cntrbtprvst_d`,
`gmfcantiere_cantiere_id`,
`piva`,
`createdon`   
FROM qckapp_g_main
;

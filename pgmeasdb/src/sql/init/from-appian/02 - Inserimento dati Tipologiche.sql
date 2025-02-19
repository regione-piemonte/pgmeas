DELETE FROM tipologia_gerarchia;
INSERT INTO tipologia_gerarchia (cod_tipologia_gerarchia, des_tipologia) VALUES ('COM','Componente'), ('INV','Investimento'), ('MIS','Missione'), ('SUB','Sub Investimento');

DELETE FROM applicazione;
ALTER TABLE applicazione AUTO_INCREMENT = 1;
INSERT INTO applicazione (api_key,descrizione,data_creazione,username_creazione,data_aggiornamento,username_aggiornamento,data_eliminazione) VALUES
	 (742361,'CSI - GMF','2022-12-01 00:00:00','ScriptSQL',NULL,NULL,NULL);


DELETE FROM funzione;
ALTER TABLE funzione AUTO_INCREMENT = 1;
INSERT INTO funzione (codice,descrizione) VALUES
	 ('EDIT','Read e Write'),
	 ('READ','Only Read');     ;


DELETE FROM ruolo;
ALTER TABLE ruolo AUTO_INCREMENT = 1;
INSERT INTO ruolo (codice,descrizione) VALUES
	 ('SUPER_ADMIN','Amministratore'),
	 ('ADMIN','Amministratore Ente'),
	 ('USER','Utente Operativo');

DELETE FROM ruolo_funzione;
INSERT INTO `ruolo_funzione` (`id_ruolo`, `id_funzione`) VALUES ('1', '1');
INSERT INTO `ruolo_funzione` (`id_ruolo`, `id_funzione`) VALUES ('2', '1');
INSERT INTO `ruolo_funzione` (`id_ruolo`, `id_funzione`) VALUES ('3', '1');

DELETE FROM tipologia_ente;
ALTER TABLE tipologia_ente AUTO_INCREMENT = 1;
INSERT INTO tipologia_ente (desc_tipologia_ente) VALUES
	 ('Altro'),
	 ('Area Metropolitana'),
	 ('Circoscrizione'),
	 ('Comune'),
	 ('Provincia'),
	 ('Regione');


DELETE FROM fase_ciclo_finanziario;
ALTER TABLE fase_ciclo_finanziario AUTO_INCREMENT = 1;
INSERT INTO fase_ciclo_finanziario (descrizione) VALUES
	 ('Determina d''impegno'),
	 ('Fatture Ricevute'),
	 ('Richiesta Fondi'),
	 ('Trasferimento Fondi'),
	 ('Determina di Incasso'),
	 ('Determina di Liquidazione'),
	 ('Mandato di Pagamento'),
	 ('Quietanzato'),
	 ('Rendicontato'),
	 ('Ribasso di gara'),
	 ('Impegno Giuridicamente Vincolante'),
	 ('Mandare a Roma'),
	 ('Chiedere Rimborso'),
	 ('Validazione');
     
     
-- Caricamento tabella Associativa Operazione-Fase Procedurale
DELETE FROM tipoperaz_faseproced;
ALTER TABLE tipoperaz_faseproced AUTO_INCREMENT = 1;

INSERT INTO `tipoperaz_faseproced` (`tipologia_operazione_id`, `cod_fase`, `descrizione`, `tenant`)
select id, 1, 'Stipula Contratto',             tenant from gmftipologia where value = 'Acquisto di beni' union
select id, 2, 'Esecuzione Fornitura',          tenant from gmftipologia where value = 'Acquisto di beni' union
select id, 3, 'Chiusura rendicontazione',      tenant from gmftipologia where value = 'Acquisto di beni' union

select id, 1, 'Stipula Contratto',             tenant from gmftipologia where value = 'Acquisto o realizzazione di servizi' union
select id, 2, 'Esecuzione Fornitura',          tenant from gmftipologia where value = 'Acquisto o realizzazione di servizi' union
select id, 3, 'Chiusura rendicontazione',      tenant from gmftipologia where value = 'Acquisto o realizzazione di servizi' union

select id, 1,  'Fattibilità Tecnica economica',                            tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 2,  'Progettazione definitiva',                                 tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 3,  'Progettazione esecutiva',                                  tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 4,  'Verifica e Validazione progetto (art. 26 D.Lgs. 50/2016)', tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 5,  'Determina a Contrarre - Gara Lavori',                      tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 6,  'Pubblicazione bando di gara lavori',                       tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 7,  'Stipula Contratto d''Appalto Lavori',                      tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 8,  'Esecuzione lavori',                                        tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 9,  'Collaudo Opera',                                           tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union
select id, 10, 'Chiusura rendicontazione',                                 tenant from gmftipologia where value = 'Realizzazione di lavori pubblici (opere ed impiantistica)' union

select id, 1, 'Attribuzione finanziamento',       tenant from gmftipologia where value = 'Concessione di contributi ad altri soggetti (diversi da unita'' produttive)' union
select id, 2, 'Esecuzione investimenti/attività', tenant from gmftipologia where value = 'Concessione di contributi ad altri soggetti (diversi da unita'' produttive)' union
select id, 3, 'Chiusura rendicontazione',         tenant from gmftipologia where value = 'Concessione di contributi ad altri soggetti (diversi da unita'' produttive)' union

select id, 1, 'Attribuzione finanziamento', tenant from gmftipologia where value = 'Concessione di incentivi ad unita'' produttive' union
select id, 2, 'Esecuzione investimenti',    tenant from gmftipologia where value = 'Concessione di incentivi ad unita'' produttive' union
select id, 3, 'Chiusura rendicontazione',   tenant from gmftipologia where value = 'Concessione di incentivi ad unita'' produttive' union

select id, 1, 'Attribuzione finanziamento', tenant from gmftipologia where value = 'Sottoscrizione iniziale o aumento di capitale sociale (compresi spin off), fondi di rischio o di garanzia' union
select id, 2, 'Esecuzione investimenti',    tenant from gmftipologia where value = 'Sottoscrizione iniziale o aumento di capitale sociale (compresi spin off), fondi di rischio o di garanzia' union
select id, 3, 'Chiusura rendicontazione',   tenant from gmftipologia where value = 'Sottoscrizione iniziale o aumento di capitale sociale (compresi spin off), fondi di rischio o di garanzia'
;


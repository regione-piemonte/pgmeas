/********************
 * Inserimento dati *
 ********************/

INSERT INTO applicazione (api_key, descrizione, data_creazione, username_creazione, data_aggiornamento, username_aggiornamento)
SELECT tmp.api_key, tmp.descrizione, now(), 'ScriptSQL', now(), 'ScriptSQL'
FROM (VALUES
    ROW(123456, 'GMF')
) AS tmp(api_key, descrizione)
WHERE NOT EXISTS (
    SELECT 1
    FROM applicazione curr
    WHERE curr.api_key = tmp.api_key
);

INSERT INTO fase_ciclo_finanziario(descrizione)
SELECT tmp.descrizione
FROM (VALUES
    ROW(1, 'Determina d''impegno'),
    ROW(2, 'Fatture Ricevute'),
    ROW(3, 'Richiesta Fondi'),
    ROW(4, 'Trasferimento Fondi'),
    ROW(5, 'Determina di Incasso'),
    ROW(6, 'Determina di Liquidazione'),
    ROW(7, 'Mandato di Pagamento'),
    ROW(8, 'Quietanzato'),
    ROW(9, 'Rendicontato'),
    ROW(10, 'Ribasso di gara'),
    ROW(11, 'Impegno Giuridicamente Vincolante'),
    ROW(12, 'Mandare a Roma'),
    ROW(13, 'Chiedere Rimborso'),
    ROW(14, 'Validazione')
) AS tmp(`order`, descrizione)
WHERE NOT EXISTS (
    SELECT 1
    FROM fase_ciclo_finanziario curr
    WHERE curr.descrizione = tmp.descrizione
)
ORDER BY tmp.order;

INSERT INTO funzionalita (codice_funzionalita, desc_funzionalita, `order`)
SELECT tmp.codice_funzionalita, tmp.desc_funzionalita, tmp.order
FROM (VALUES
    ROW('DASHB', 'DASHBOARD', 1),
    ROW('PRJ', 'PROGETTI', 2),
    ROW('PROF', 'PROFILAZIONE', 3),
    ROW('ANAG', 'ANAGRAFICHE', 4)
) AS tmp(codice_funzionalita, desc_funzionalita, `order`)
WHERE NOT EXISTS (
    SELECT 1
    FROM funzionalita curr
    WHERE curr.codice_funzionalita = tmp.codice_funzionalita
)
ORDER BY tmp.order;

INSERT INTO funzione (codice, descrizione)
SELECT tmp.codice, tmp.desc_funzionalita, tmp.order
FROM (VALUES
    ROW(1, 'EDIT', 'Read e Write'),
    ROW(2, 'READ', 'Only Read')
) AS tmp(`order`, codice, descrizione)
WHERE NOT EXISTS (
    SELECT 1
    FROM funzione curr
    WHERE curr.codice = tmp.codice
)
ORDER BY tmp.order;

INSERT INTO tipologia_ente (desc_tipologia_ente)
SELECT tmp.codice, tmp.desc_funzionalita, tmp.order
FROM (VALUES
    ROW(1, 'Altro'),
    ROW(2, 'Area Metropolitana'),
    ROW(3, 'Circoscrizione'),
    ROW(4, 'Comune'),
    ROW(5, 'Provincia'),
    ROW(6, 'Regione')
) AS tmp(`order`, desc_tipologia_ente)
WHERE NOT EXISTS (
    SELECT 1
    FROM tipologia_ente curr
    WHERE curr.desc_tipologia_ente = tmp.desc_tipologia_ente
)
ORDER BY tmp.order;

INSERT INTO ente (descrizione, abilitato, data_inizio_validita, data_creazione, username_creazione, data_aggiornamento, username_aggiornamento, id_tipologia_ente)
SELECT tmp.descrizione, 1, now(), now(), 'ScriptSQL', now(), 'ScriptSQL', tipologia_ente.id_tipologia_ente
FROM (VALUES
    ROW(1, 'TEST', 'Altro')
) AS tmp(`order`, descrizione, desc_tipologia_ente)
JOIN tipologia_ente ON tipologia_ente.desc_tipologia_ente = tmp.desc_tipologia_ente
WHERE NOT EXISTS (
    SELECT 1
    FROM ente curr
    WHERE curr.descrizione = tmp.descrizione
)
ORDER BY tmp.order;

INSERT INTO gmfasse (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Asse 1 - Agenda Digitale Metropolitana', 1, 'TEST'),
    ROW('Asse 2 - Sostenibilità dei servizi pubblici e della mobilità urbana', 2, 'TEST'),
    ROW('Asse 3 - Servizi per l''inclusione sociale', 3, 'TEST'),
    ROW('Asse 4 - Infrastrutture per l''inclusione sociale', 4, 'TEST'),
    ROW('Asse 5 - Assistenza tecnica', 5, 'TEST'),
    ROW('Asse 6 - Ripresa verde, digitale e resiliente (REACT-EU FESR)', 6, 'TEST'),
    ROW('Asse 7 - Ripresa sociale, economica e occupazionale (REACT-EU FSE)', 7, 'TEST'),
    ROW('Asse 8 - Assistenza Tecnica (REACT-EU FESR)', 8, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfasse curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfobiettivospecifico (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('6.1 Transizione verde e digitale delle città metropolitane', 1, 'TEST'),
    ROW('6.2 Resilienza delle città metropolitane', 2, 'TEST'),
    ROW('7.1 Rafforzamento sociale nelle città metropolitane', 3, 'TEST'),
    ROW('8.1 Assistenza tecnica e capacità', 4, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfobiettivospecifico curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfazioni (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('6.1.1 Servizi digitali', 1, 'TEST'),
    ROW('6.1.2 Mobilità sostenibile', 2, 'TEST'),
    ROW('6.1.3. Energia ed efficienza energetica', 3, 'TEST'),
    ROW('6.1.4 Qualità dell’ambiente e adattamento ai cambiamenti climatici', 4, 'TEST'),
    ROW('6.2.1 Rafforzamento sociale e occupazionale nelle città metropolitane', 5, 'TEST'),
    ROW('7.1.1 Incremento di servizi e iniziative di sostegno per i segmenti più fragili delle comunità', 6, 'TEST'),
    ROW('8.1.1 Assistenza tecnica e capacità amministrativa REACT -EU', 7, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfazioni curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfcantiere (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Si', 1),
    ROW('No', 2)
) AS tmp(`value`, `order`)
CROSS JOIN ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfcantiere curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfcircoscrizione (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Circoscrizione 1', 1, 'TEST'),
    ROW('Circoscrizione 2', 2, 'TEST'),
    ROW('Circoscrizione 3', 3, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfcircoscrizione curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfclausole (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Si', 1),
    ROW('No', 2)
) AS tmp(`value`, `order`)
CROSS JOIN ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfclausole curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfcontributoprevisto (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Si', 1),
    ROW('No', 2)
) AS tmp(`value`, `order`)
CROSS JOIN ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfcontributoprevisto curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmffondo (`value`, `order`, active, tenant, type_fund)
SELECT tmp.value, tmp.order, 1, ente.id_ente, tmp.type_fund
FROM (VALUES
    ROW('REACT', 1, 1, 'TEST'),
    ROW('PNRR', 2, 2, 'TEST'),
    ROW('Fondo di esempio', 3, 2, 'TEST'),
    ROW('Fondo Nazionale Complementare', 4, 2, 'TEST'),
    ROW('Fondo Europeo di Sviluppo Regionale (FESR)', 5, 2, 'TEST'),
    ROW('Fondo Sociale Europeo (FSE)', 6, 2, 'TEST'),
    ROW('Ripresa per la Coesione e i Territori d''Europa (REACT-EU)', 7, 2, 'TEST'),
    ROW('Recovery and Resilience Facility (RRF)', 8, 2, 'TEST'),
    ROW('Fondo Sviluppo e Coesione (FSC)', 9, 2, 'TEST'),
    ROW('PNRR & Fondo Nazionale Complementare', 10, 2, 'TEST'),
    ROW('Altri Fondi', 11, 2, 'TEST')
) AS tmp(`value`, `order`, type_fund, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmffondo curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfinserimentostrumenti (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Piano strategico città metropolitana', 1, 'TEST'),
    ROW('Piano energetico/Paes/Paesc', 2, 'TEST'),
    ROW('Pums', 3, 'TEST'),
    ROW('PUC', 4, 'TEST'),
    ROW('Altro', 5, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfinserimentostrumenti curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfprogrammi (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('PON metro', 1, 'TEST'),
    ROW('Parco del Valentino', 2, 'TEST'),
    ROW('PIU', 3, 'TEST'),
    ROW('PON metro plus', 4, 'TEST'),
    ROW('PINQuA Vallette', 5, 'TEST'),
    ROW('PINQuA Porta Palazzo', 6, 'TEST'),
    ROW('PINQuA Racconigi', 7, 'TEST'),
    ROW('TRM', 8, 'TEST'),
    ROW('Rigenerazione Urbana', 9, 'TEST'),
    ROW('SICURO, VERDE, SOCIALE', 10, 'TEST'),
    ROW('NUOVE SCUOLE ', 11, 'TEST'),
    ROW('MENSE E TEMPO PIENO PNRR', 12, 'TEST'),
    ROW('SERVIZI SOCIO-ASSISTENZIALI, DISABILITA'' E MARGINALITA'' PNRR', 13, 'TEST'),
    ROW('Mobility as a service', 14, 'TEST'),
    ROW('Ecoefficienza nei teatri,cinema e musei', 15, 'TEST'),
    ROW('Rinnovo flotte bus', 16, 'TEST'),
    ROW('Sport e inclusione sociale', 17, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfprogrammi curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfrisorse (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Si', 1),
    ROW('No', 2)
) AS tmp(`value`, `order`)
CROSS JOIN ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfrisorse curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmfstrutturadiriferimento (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Ente Test', 1, 'TEST'),
    ROW('Direzione 1', 2, 'TEST'),
    ROW('Direzione 2', 3, 'TEST'),
    ROW('Direzione 3', 4, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmfstrutturadiriferimento curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmftag (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('digitalizzazione', 1, 'TEST'),
    ROW('digitale', 2, 'TEST'),
    ROW('innovazione', 3, 'TEST'),
    ROW('Rifiuti', 4, 'TEST'),
    ROW('edilizia', 5, 'TEST'),
    ROW('tecnologia', 6, 'TEST'),
    ROW('traffico', 7, 'TEST'),
    ROW('veicoli', 8, 'TEST'),
    ROW('ciclabile', 9, 'TEST'),
    ROW('ciclabilità', 10, 'TEST'),
    ROW('scolastica', 11, 'TEST'),
    ROW('sostenibilità', 12, 'TEST'),
    ROW('Verde', 13, 'TEST'),
    ROW('mobilità', 14, 'TEST'),
    ROW('energia', 15, 'TEST'),
    ROW('palestre', 16, 'TEST'),
    ROW('sport', 17, 'TEST'),
    ROW('clima', 18, 'TEST'),
    ROW('cambiamenti climatici', 19, 'TEST'),
    ROW('parchi', 20, 'TEST'),
    ROW('ambiente', 21, 'TEST'),
    ROW('aria', 22, 'TEST'),
    ROW('inclusione', 23, 'TEST'),
    ROW('sociale', 24, 'TEST'),
    ROW('formazione', 25, 'TEST'),
    ROW('personale', 26, 'TEST'),
    ROW('cultura', 27, 'TEST'),
    ROW('turismo', 28, 'TEST'),
    ROW('parco', 29, 'TEST'),
    ROW('infrastrutture', 30, 'TEST'),
    ROW('biblioteche', 31, 'TEST'),
    ROW('resilienza', 32, 'TEST')
) AS tmp(`value`, `order`, ente)
JOIN ente ON ente.descrizione = tmp.ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmftag curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmftipologia (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Acquisto di beni', 1),
    ROW('Acquisto o realizzazione di servizi', 2),
    ROW('Realizzazione di lavori pubblici (opere ed impiantistica)', 3),
    ROW('Concessione di contributi ad altri soggetti (diversi da unita'' produttive)', 4),
    ROW('Concessione di incentivi ad unita'' produttive', 5),
    ROW('Sottoscrizione iniziale o aumento di capitale sociale (compresi spin off), fondi di rischio o di garanzia', 6)
) AS tmp(`value`, `order`)
CROSS JOIN ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmftipologia curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO gmftipologiaprocedura (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, 1, ente.id_ente
FROM (VALUES
    ROW('Bando', 1),
    ROW('Avviso', 2),
    ROW('Affidamento in house', 3)
) AS tmp(`value`, `order`)
CROSS JOIN ente
WHERE NOT EXISTS (
    SELECT 1
    FROM gmftipologiaprocedura curr
    WHERE curr.tenant = ente.id_ente
    AND curr.order = tmp.order
)
ORDER BY tmp.order;

INSERT INTO tipologia_gerarchia (cod_tipologia_gerarchia, des_tipologia)
SELECT tmp.codice, tmp.descrizione
FROM (VALUES
    ROW(2, 'COM', 'Componente'),
    ROW(3, 'INV', 'Investimento'),
    ROW(1, 'MIS', 'Missione'),
    ROW(4, 'SUB', 'Sub Investimento')
) AS tmp(`order`, codice, descrizione)
WHERE NOT EXISTS (
    SELECT 1
    FROM tipologia_gerarchia curr
    WHERE curr.cod_tipologia_gerarchia = tmp.codice
);

INSERT INTO ruolo_funzionalita (ruolo_id, funzionalita_id)
SELECT ruolo.id_ruolo, funzionalita.id_funzionalita
FROM (VALUES
    ROW('ADMIN','DASHB'),
    ROW('ADMIN','PRJ'),
    ROW('ADMIN','PROF'),
    ROW('SUPER_ADMIN','DASHB'),
    ROW('SUPER_ADMIN','PRJ'),
    ROW('SUPER_ADMIN','PROF'),
    ROW('USER','DASHB'),
    ROW('USER','PRJ'),
    ROW('USER_READ_ONLY','DASHB'),
    ROW('USER_READ_ONLY','PRJ')
) AS tmp(codice_ruolo, codice_funzionalita)
JOIN ruolo ON ruolo.codice = tmp.codice_ruolo
JOIN funzionalita ON funzionalita.codice_funzionalita = tmp.codice_funzionalita
WHERE NOT EXISTS (
    SELECT 1
    FROM ruolo_funzionalita curr
    WHERE curr.ruolo_id = ruolo.id_ruolo
    AND curr.funzionalita_id = funzionalita.id_funzionalita
);

INSERT INTO ruolo_funzione (id_ruolo, id_funzione)
SELECT ruolo.id_ruolo, funzione.id_funzione
FROM (VALUES
    ROW('SUPER_ADMIN','EDIT'),
    ROW('ADMIN','EDIT'),
    ROW('USER','EDIT'),
    ROW('USER_READ_ONLY','READ')
) AS tmp(codice_ruolo, codice_funzione)
JOIN ruolo ON ruolo.codice = tmp.codice_ruolo
JOIN funzione ON funzione.codice = tmp.codice_funzione
WHERE NOT EXISTS (
    SELECT 1
    FROM ruolo_funzione curr
    WHERE curr.id_ruolo = ruolo.id_ruolo
    AND curr.id_funzione = funzione.id_funzione
);

INSERT INTO direzione (flag_titolare, descrizione, data_inizio_validita, data_creazione, username_creazione, data_aggiornamento, username_aggiornamento, id_ente)
SELECT tmp.flag_titolare, tmp.descrizione, now(), now(), 'ScriptSQL', now(), 'ScriptSQL', ente.id_ente
FROM (VALUES
    ROW(1, 1, 'Ente TEST', 'TEST'),
    ROW(2, 0, 'Direzione A', 'TEST'),
    ROW(3, 0, 'Direzione B', 'TEST'),
    ROW(4, 0, 'Direzione C', 'TEST'),
    ROW(5, 0, 'Direzione A-1', 'TEST'),
    ROW(6, 0, 'Direzione A-2', 'TEST'),
    ROW(7, 0, 'Direzione A-3', 'TEST'),
    ROW(8, 0, 'Direzione A-1-1', 'TEST')
) AS tmp(`order`, flag_titolare, descrizione, descrizione_ente)
JOIN ente ON ente.descrizione = tmp.descrizione_ente
WHERE NOT EXISTS (
    SELECT 1
    FROM direzione curr
    WHERE curr.descrizione = tmp.descrizione
    AND curr.id_ente = ente.id_ente
)
ORDER BY tmp.order;

INSERT INTO direzione_gerarchia (direzione_id_padre, direzione_id_figlio, data_inizio_validita, username_creazione, username_aggiornamento)
SELECT direzione_padre.id_direzione, direzione_figlio.id_direzione, now(), 'ScriptSQL', 'ScriptSQL'
FROM (VALUES
    ROW('Ente TEST', 'Direzione A', 'TEST'),
    ROW('Ente TEST', 'Direzione B', 'TEST'),
    ROW('Ente TEST', 'Direzione C', 'TEST'),
    ROW('Direzione A', 'Direzione A-1', 'TEST'),
    ROW('Direzione A', 'Direzione A-2', 'TEST'),
    ROW('Direzione A', 'Direzione A-3', 'TEST'),
    ROW('Direzione A-1', 'Direzione A-1-1', 'TEST')
) AS tmp(padre, figlio, descrizione_ente)
JOIN ente ON ente.descrizione = tmp.descrizione_ente
JOIN direzione direzione_padre ON direzione_padre.id_ente = ente.id_ente AND direzione_padre.descrizione = tmp.padre
JOIN direzione direzione_figlio ON direzione_figlio.id_ente = ente.id_ente AND direzione_figlio.descrizione = tmp.figlio
WHERE NOT EXISTS (
    SELECT 1
    FROM direzione_gerarchia curr
    WHERE curr.direzione_id_padre = direzione_padre.id_direzione
    AND curr.direzione_id_figlio = direzione_figlio.id_direzione
);

INSERT INTO tipoperaz_faseproced(tipologia_operazione_id, cod_fase, descrizione, tenant)
SELECT gmftipologia.id, tmp.cod_fase, tmp.descrizione, ente.id_ente
FROM (VALUES
    ROW('1',1,'Stipula Contratto','TEST'),
    ROW('1',2,'Esecuzione Fornitura','TEST'),
    ROW('1',3,'Chiusura rendicontazione','TEST'),
    ROW('2',1,'Stipula Contratto','TEST'),
    ROW('2',2,'Esecuzione Fornitura','TEST'),
    ROW('2',3,'Chiusura rendicontazione','TEST'),
    ROW('3',1,'Fattibilità Tecnica economica','TEST'),
    ROW('3',2,'Progettazione definitiva','TEST'),
    ROW('3',3,'Progettazione esecutiva','TEST'),
    ROW('3',4,'Verifica e Validazione progetto (art. 26 D.Lgs. 50/2016)','TEST'),
    ROW('3',5,'Determina a Contrarre - Gara Lavori','TEST'),
    ROW('3',6,'Pubblicazione bando di gara lavori','TEST'),
    ROW('3',7,'Stipula Contratto d''Appalto Lavori','TEST'),
    ROW('3',8,'Esecuzione lavori','TEST'),
    ROW('3',9,'Collaudo Opera','TEST'),
    ROW('3',10,'Chiusura rendicontazione','TEST'),
    ROW('4',1,'Attribuzione finanziamento','TEST'),
    ROW('4',2,'Esecuzione investimenti/attività','TEST'),
    ROW('4',3,'Chiusura rendicontazione','TEST'),
    ROW('5',1,'Attribuzione finanziamento','TEST'),
    ROW('5',2,'Esecuzione investimenti','TEST'),
    ROW('5',3,'Chiusura rendicontazione','TEST'),
    ROW('6',1,'Attribuzione finanziamento','TEST'),
    ROW('6',2,'Esecuzione investimenti','TEST'),
    ROW('6',3,'Chiusura rendicontazione','TEST')
) AS tmp(tipologia, cod_fase, descrizione, descrizione_ente)
JOIN ente ON ente.descrizione = tmp.descrizione_ente
JOIN gmftipologia ON gmftipologia.order = tmp.tipologia AND gmftipologia.tenant = ente.id_ente
WHERE NOT EXISTS (
    SELECT 1
    FROM tipoperaz_faseproced curr
    WHERE curr.tipologia_operazione_id = gmftipologia.id
    AND curr.cod_fase = tmp.cod_fase
    AND curr.tenant = ente.id_ente
)
ORDER BY tmp.tipologia, tmp.cod_fase;

INSERT INTO elemento_gerarchia (ente_id, cod_tipologia_gerarchia, des_elemento, attivo, cod_livello_elemento, `order`)
SELECT ente.id_ente, tmp.cod_tipologia_gerarchia, tmp.des_elemento, tmp.attivo, tmp.cod_livello_elemento, tmp.order
FROM (VALUES
    ROW('MIS','1: Digitalizzazione, innovazione, competitività, cultura e turismo',1,'1',1,'TEST'),
    ROW('MIS','2: Rivoluzione verde e transizione ecologica',1,'2',2,'TEST'),
    ROW('MIS','3: Infrastrutture per una mobilità sostenibile',1,'3',3,'TEST'),
    ROW('MIS','4: Istruzione e ricerca',1,'4',4,'TEST'),
    ROW('MIS','5: Coesione e inclusione',1,'5',5,'TEST'),
    ROW('MIS','6: Salute',1,'6',6,'TEST'),
    ROW('COM','1: Digitalizzazione, innovazione e sicurezza nella PA',1,'1-1',1,'TEST'),
    ROW('COM','2: Digitalizzazione, innovazione e competitività nel sistema produttivo',1,'1-2',2,'TEST'),
    ROW('COM','3: Turismo e cultura 4.0',1,'1-3',3,'TEST'),
    ROW('COM','1: Economia circolare e agricoltura sostenibile',1,'2-1',1,'TEST'),
    ROW('COM','2: Energia rinnovabile, idrogeno, rete e mobilità sostenibile',1,'2-2',2,'TEST'),
    ROW('COM','3: Efficienza energetica e riqualificazione degli edifici',1,'2-3',3,'TEST'),
    ROW('COM','4: Tutela del territorio e della risorsa idrica',1,'2-4',4,'TEST'),
    ROW('COM','1: Investimenti sulla rete ferroviaria',1,'3-1',1,'TEST'),
    ROW('COM','2: Intermodalità e logistica integrata',1,'3-2',2,'TEST'),
    ROW('COM','1: Potenziamento dell’offerta dei servizi di istruzione: dagli asili nido alle università',1,'4-1',1,'TEST'),
    ROW('COM','2: Dalla ricerca all’impresa',1,'4-2',2,'TEST'),
    ROW('COM','1: Politiche per il lavoro',1,'5-1',1,'TEST'),
    ROW('COM','2: Infrastrutture sociali, famiglie, comunità e terzo settore',1,'5-2',2,'TEST'),
    ROW('COM','3: Interventi speciali per la coesione territoriale',1,'5-3',3,'TEST'),
    ROW('COM','1: Reti di prossimità, strutture e telemedicina per l’assistenza sanitaria territoriale',1,'6-1',1,'TEST'),
    ROW('COM','2: Innovazione, ricerca e digitalizzazione del servizio sanitario nazionale',1,'6-2',2,'TEST'),
    ROW('INV','Servizi digitali e cittadinanza digitale (Piano complementare)',1,'1-1-',5,'TEST'),
    ROW('INV','Servizi digitali e competenze digitali',1,'1-1-',6,'TEST'),
    ROW('INV','“Polis” - Case dei servizi di cittadinanza digitale',1,'1-1-',7,'TEST'),
    ROW('INV','1.1: Infrastrutture digitali',1,'1-1-1.1',1,'TEST'),
    ROW('INV','1.2: Abilitazione e facilitazione migrazione al Cloud',1,'1-1-1.2',2,'TEST'),
    ROW('INV','1.3: Dati e interoperabilità',1,'1-1-1.3',3,'TEST'),
    ROW('INV','1.4: Servizi digitali e cittadinanza digitale (PNRR)',1,'1-1-1.4',4,'TEST'),
    ROW('INV','1.5: Cybersecurity ',1,'1-1-1.5',8,'TEST'),
    ROW('INV','1.6: Digitalizzazione delle grandi amministrazioni centrali',1,'1-1-1.6',9,'TEST'),
    ROW('INV','1.7: Competenze digitali di base',1,'1-1-1.7',10,'TEST'),
    ROW('INV','2.1: Portale unico del reclutamento',1,'1-1-2.1',11,'TEST'),
    ROW('INV','2.2: Task Force digitalizzazione, monitoraggio e performance',1,'1-1-2.2',12,'TEST'),
    ROW('INV','2.3: Competenze: Competenze e capacità amministrativa ',1,'1-1-2.3',13,'TEST'),
    ROW('INV','3.1: Investimento in capitale umano per rafforzare l’Ufficio del Processo e superare le disparità tra tribunali',1,'1-1-3.1',14,'TEST'),
    ROW('INV','3.2: Rafforzamento dell''Ufficio del processo per la Giustizia amministrativa',1,'1-1-3.2',15,'TEST'),
    ROW('INV','Transizione 4.0 (Piano complementare)',1,'1-2-',9,'TEST'),
    ROW('INV','Digitalizzazione PMI e Fondo di Garanzia',1,'1-2-',5,'TEST'),
    ROW('INV','Tecnologie satellitari ed economia spaziale (Piano complementare)',1,'1-2-',4,'TEST'),
    ROW('INV','1: Transizione 4.0 (PNRR)',1,'1-2-1',8,'TEST'),
    ROW('INV','2. Innovazione e tecnologia della Microelettronica',1,'1-2-2',1,'TEST'),
    ROW('INV','3. Reti ultraveloci (banda ultra-larga e 5G)',1,'1-2-3',2,'TEST'),
    ROW('INV','4. Tecnologie satellitari ed economia spaziale* (PNRR)',1,'1-2-4',3,'TEST'),
    ROW('INV','5: Politiche industriali di filiera e internazionalizzazione',1,'1-2-5',6,'TEST'),
    ROW('INV','6.1 Investimento Sistema della Proprietà Industriale',1,'1-2-6.1',7,'TEST'),
    ROW('INV','Piano di investimenti strategici sui siti del patrimonio culturale, edifici e aree naturali',1,'1-3-',6,'TEST'),
    ROW('INV','1.1:Strategia digitale e piattaforme per il patrimonio culturale',1,'1-3-1.1',3,'TEST'),
    ROW('INV','1.2: Rimozione delle barriere fisiche e cognitive in musei, biblioteche e archivi per consentire un più ampio accesso e partecipazione alla cultura',1,'1-3-1.2',4,'TEST'),
    ROW('INV','1.3: Migliorare l''efficienza energetica di cinema,  teatri e  musei',1,'1-3-1.3',5,'TEST'),
    ROW('INV','2.1: Attrattività dei borghi',1,'1-3-2.1',7,'TEST'),
    ROW('INV','2.2: Tutela e valorizzazione dell''architettura e del paesaggio rurale',1,'1-3-2.2',8,'TEST'),
    ROW('INV','2.3: Programmi per valorizzare l''identità di luoghi: parchi e giardini storici ',1,'1-3-2.3',9,'TEST'),
    ROW('INV','2.4: Sicurezza sismica nei luoghi di culto, restauro del patrimonio culturale del Fondo Edifici di Culto (FEC) e siti di ricovero per le opere d’arte (Recovery Art)',1,'1-3-2.4',10,'TEST'),
    ROW('INV','3.2: Sviluppo industria cinematografica (Progetto Cinecittà)',1,'1-3-3.2',1,'TEST'),
    ROW('INV','3.3: Capacity building per gli operatori della cultura per gestire la transizione digitale e verde',1,'1-3-3.3',2,'TEST'),
    ROW('INV','4.1 Hub del Turismo Digitale',1,'1-3-4.1',11,'TEST'),
    ROW('INV','4.2 Fondi integrati per la competitività delle imprese turistiche',1,'1-3-4.2',12,'TEST'),
    ROW('INV','4.3 Caput Mundi. Next Generation EU per grandi eventi turistici',1,'1-3-4.3',13,'TEST'),
    ROW('INV','Contratti di filiera e distrettuali per i settori agroalimentare, pesca e acquacoltura, silvicoltura, floricoltura e vivaismo',1,'2-1-',4,'TEST'),
    ROW('INV','1.1 Realizzazione nuovi impianti di gestione rifiuti e ammodernamento di impianti esistenti',1,'2-1-1.1',5,'TEST'),
    ROW('INV','1.2 Progetti “faro” di economia circolare',1,'2-1-1.2',6,'TEST'),
    ROW('INV','2.1 Sviluppo logistica per i settori agroalimentare, pesca e acquacoltura, silvicoltura, floricoltura e vivaismo',1,'2-1-2.1',1,'TEST'),
    ROW('INV','2.2 Parco Agrisolare',1,'2-1-2.2',2,'TEST'),
    ROW('INV','2.3 Innovazione e meccanizzazione nel settore agricolo ed alimentare',1,'2-1-2.3',3,'TEST'),
    ROW('INV','3.1 Isole verdi',1,'2-1-3.1',7,'TEST'),
    ROW('INV','3.2 Green communities',1,'2-1-3.2',8,'TEST'),
    ROW('INV','3.3 Cultura e consapevolezza su temi e sfide ambientali',1,'2-1-3.3',9,'TEST'),
    ROW('INV','Energia rinnovabile',1,'2-2-',6,'TEST'),
    ROW('INV','Rinnovo flotte, bus, treni e navi verdi - Navi (Piano complementare)',1,'2-2-',22,'TEST'),
    ROW('INV','Rinnovo flotte, bus, treni e navi verdi - Bus (Piano complementare)',1,'2-2-',21,'TEST'),
    ROW('INV','1.1 Sviluppo agro-voltaico',1,'2-2-1.1',1,'TEST'),
    ROW('INV','1.2 Promozione rinnovabili per le comunità energetiche e l''auto-consumo',1,'2-2-1.2',2,'TEST'),
    ROW('INV','1.3 Promozione impianti innovativi (incluso off-shore)',1,'2-2-1.3',3,'TEST'),
    ROW('INV','1.4 Sviluppo bio-metano',1,'2-2-1.4',4,'TEST'),
    ROW('INV','2.1 Rafforzamento smart grid',1,'2-2-2.1',15,'TEST'),
    ROW('INV','2.2 Interventi su resilienza climatica reti',1,'2-2-2.2',16,'TEST'),
    ROW('INV','3.1 Produzione in aree industriali dismesse ',1,'2-2-3.1',10,'TEST'),
    ROW('INV','3.2 Utilizzo in settori hard-to-abate',1,'2-2-3.2',11,'TEST'),
    ROW('INV','3.3: Sperimentazione dell''idrogeno per il trasporto stradale',1,'2-2-3.3',12,'TEST'),
    ROW('INV','3.4: Sperimentazione dell''idrogeno per il trasporto ferroviario',1,'2-2-3.4',13,'TEST'),
    ROW('INV','3.5 Ricerca e sviluppo sull''idrogeno',1,'2-2-3.5',14,'TEST'),
    ROW('INV','4.1: Rafforzamento mobilità ciclistica',1,'2-2-4.1',17,'TEST'),
    ROW('INV','4.2: Sviluppo trasporto rapido di massa',1,'2-2-4.2',18,'TEST'),
    ROW('INV','4.3 Sviluppo infrastrutture di ricarica elettrica',1,'2-2-4.3',19,'TEST'),
    ROW('INV','4.4: Rinnovo flotte bus e treni verdi (PNRR)',1,'2-2-4.4',20,'TEST'),
    ROW('INV','5.1: Rinnovabili e batterie',1,'2-2-5.1',5,'TEST'),
    ROW('INV','5.2 Idrogeno',1,'2-2-5.2',7,'TEST'),
    ROW('INV','5.3: Bus elettrici (filiera industriale)',1,'2-2-5.3',8,'TEST'),
    ROW('INV','5.4: Supporto a start-up e venture capital attivi nella transizione ecologica',1,'2-2-5.4',9,'TEST'),
    ROW('INV','Sicuro, verde e sociale: riqualificazione edilizia residenziale pubblica',1,'2-3-',3,'TEST'),
    ROW('INV','Efficientamento energetico edifici pubblici',1,'2-3-',4,'TEST'),
    ROW('INV','Ecobonus e Sismabonus fino al 110% per l''efficienza energetica e la sicurezza degli edifici  (Piano complementare)',1,'2-3-',6,'TEST'),
    ROW('INV','Rinnovo flotte, bus, treni e navi verdi - Bus (Piano complementare)',1,'2-3-',21,'TEST'),
    ROW('INV','1.1 Piano di sostituzione di edifici scolastici e di riqualificazione energetica',1,'2-3-1.1',1,'TEST'),
    ROW('INV','1.2 Efficientamento degli edifici giudiziari',1,'2-3-1.2',2,'TEST'),
    ROW('INV','2.1 Ecobonus e Sismabonus fino al 110% per l''efficienza energetica e la sicurezza degli edifici (PNRR)',1,'2-3-2.1',5,'TEST'),
    ROW('INV','3.1 Sviluppo di sistemi di teleriscaldamento',1,'2-3-3.1',7,'TEST'),
    ROW('INV','1.1 Realizzazione di un sistema avanzato ed integrato di monitoraggio e previsione',1,'2-4-1.1',7,'TEST'),
    ROW('INV','2.1  Misure per la gestione del rischio di alluvione e per la riduzione del rischio idrogeologico ',1,'2-4-2.1',1,'TEST'),
    ROW('INV','2.2 Interventi per la resilienza, la valorizzazione del territorio e l''efficienza energetica dei Comuni',1,'2-4-2.2',2,'TEST'),
    ROW('INV','3.1 Tutela e valorizzazione del verde urbano ed extraurbano',1,'2-4-3.1',8,'TEST'),
    ROW('INV','3.2 Digitalizzazione dei parchi nazionali',1,'2-4-3.2',9,'TEST'),
    ROW('INV','3.3 Rinaturazione dell’area del Po',1,'2-4-3.3',10,'TEST'),
    ROW('INV','3.4 Bonifica dei siti orfani',1,'2-4-3.4',11,'TEST'),
    ROW('INV','3.5 Ripristino e tutela dei fondali e degli habitat marini',1,'2-4-3.5',12,'TEST'),
    ROW('INV','4.1: Investimenti in infrastrutture idriche primarie per la sicurezza dell''approvvigionamento idrico',1,'2-4-4.1',3,'TEST'),
    ROW('INV','4.2: Riduzione delle perdite nelle reti di distribuzione dell''acqua, compresa la digitalizzazione e il monitoraggio delle reti',1,'2-4-4.2',4,'TEST'),
    ROW('INV','4.3: Investimenti nella resilienza dell''agro-sistema irriguo per un migliore gestione delle risorse idriche',1,'2-4-4.3',5,'TEST'),
    ROW('INV','4.4 Investimenti in fognatura e depurazione',1,'2-4-4.4',6,'TEST'),
    ROW('INV','Rafforzamento delle linee regionali - linee regionali gestite da Regioni e Municipalità',1,'3-1-',9,'TEST'),
    ROW('INV','Rinnovo del materiale rotabile',1,'3-1-',10,'TEST'),
    ROW('INV','Strade sicure - Implementazione di un sistema di monitoraggio dinamico per il controllo da remoto di ponti, viadotti e tunne (ANAS)',1,'3-1-',11,'TEST'),
    ROW('INV','Strade sicure - Implementazione di un sistema di monitoraggio dinamico per il controllo da remoto di ponti, viadotti e tunnel (A24-A25) ',1,'3-1-',12,'TEST'),
    ROW('INV','1.1 Collegamenti ferroviari ad Alta Velocità verso il Sud',1,'3-1-1.1',1,'TEST'),
    ROW('INV','1.2: Linee ad alta velocità nel Nord che collegano',1,'3-1-1.2',2,'TEST'),
    ROW('INV','1.3:  Connessioni diagonali',1,'3-1-1.3',3,'TEST'),
    ROW('INV','1.4 Sviluppo del sistema europeo di gestione del trasporto ferroviario (ERTMS)',1,'3-1-1.4',4,'TEST'),
    ROW('INV','1.5 Rafforzamento dei nodi ferroviari metropolitani e dei collegamenti nazionali chiave',1,'3-1-1.5',5,'TEST'),
    ROW('INV','1.6 Potenziamento delle linee regionali',1,'3-1-1.6',6,'TEST'),
    ROW('INV','1.7 Potenziamento, elettrificazione e aumento della resilienza delle ferrovie nel Sud',1,'3-1-1.7',7,'TEST'),
    ROW('INV','1.8 Miglioramento delle stazioni ferroviarie nel Sud',1,'3-1-1.8',8,'TEST'),
    ROW('INV','Ultimo/Penultimo Miglio Ferroviario/Stradale',1,'3-2-',8,'TEST'),
    ROW('INV','Sviluppo dell’accessibilità marittima e della resilienza delle infrastrutture portuali ai cambiamenti climatici',1,'3-2-',7,'TEST'),
    ROW('INV','Elettrificazione delle banchine (Cold ironing)',1,'3-2-',6,'TEST'),
    ROW('INV','Efficientamento energetico',1,'3-2-',5,'TEST'),
    ROW('INV','Aumento selettivo della capacità portuale',1,'3-2-',4,'TEST'),
    ROW('INV','1.1 Interventi per la sostenibilità ambientale dei porti (Green Ports)',1,'3-2-1.1',3,'TEST'),
    ROW('INV','2.1 Digitalizzazione della catena logistica',1,'3-2-2.1',1,'TEST'),
    ROW('INV','2.2: Innovazione digitale dei sistemi aeroportuali ',1,'3-2-2.2',2,'TEST'),
    ROW('INV','1.1 Piano asili nido e scuole dell''infanzia e servizi di educazione e cura per la prima infanzia',1,'4-1-1.1',6,'TEST'),
    ROW('INV','1.2 Piano per l''estensione del tempo pieno e mense',1,'4-1-1.2',7,'TEST'),
    ROW('INV','1.3 Potenziamento infrastrutture per lo sport a scuola',1,'4-1-1.3',8,'TEST'),
    ROW('INV','1.4 Intervento straordinario finalizzato alla riduzione dei divari territoriali nei cicli I e II della scuola secondaria di secondo grado',1,'4-1-1.4',9,'TEST'),
    ROW('INV','1.5 Sviluppo del sistema di formazione professionale terziaria (ITS)',1,'4-1-1.5',10,'TEST'),
    ROW('INV','1.6 Orientamento attivo nella transizione scuola - università',1,'4-1-1.6',11,'TEST'),
    ROW('INV','1.7 Borse di studio per l''accesso all''università',1,'4-1-1.7',12,'TEST'),
    ROW('INV','2.1: Didattica digitale integrata e formazione sulla transizione digitale del personale scolastico',1,'4-1-2.1',5,'TEST'),
    ROW('INV','3.1 Nuove competenze e nuovi linguaggi',1,'4-1-3.1',1,'TEST'),
    ROW('INV','3.2 Scuola 4.0: scuole innovative,  nuove aule didattiche e laboratori ',1,'4-1-3.2',2,'TEST'),
    ROW('INV','3.3 Piano di messa in sicurezza e riqualificazione dell''edilizia scolastica',1,'4-1-3.3',3,'TEST'),
    ROW('INV','3.4 Didattica e competenze universitarie avanzate',1,'4-1-3.4',4,'TEST'),
    ROW('INV','4.1 Estensione del numero di dottorati di ricerca e dottorati innovativi per la Pubblica Amministrazione e il patrimonio culturale',1,'4-1-4.1',13,'TEST'),
    ROW('INV','Dottorati e ricercatori green e innovazione (REACT EU)',1,'4-2-',10,'TEST'),
    ROW('INV','Accordi per l''Innovazione (Piano complementare) ',1,'4-2-',9,'TEST'),
    ROW('INV','1.1 Fondo per il Programma Nazionale della Ricerca (PNR) e Progetti di Ricerca di Rilevante Interesse Nazionale (PRIN)',1,'4-2-1.1',4,'TEST'),
    ROW('INV','1.2 Finanziamento di progetti presentati da giovani ricercatori',1,'4-2-1.2',5,'TEST'),
    ROW('INV','1.3 Partenariati estesi a Università, centri di ricerca, imprese e finanziamento progetti di ricerca',1,'4-2-1.3',6,'TEST'),
    ROW('INV','1.4 Potenziamento strutture di ricerca e creazione di "campioni nazionali" di R&S su alcune Key enabling technologies',1,'4-2-1.4',7,'TEST'),
    ROW('INV','1.5 Creazione e rafforzamento di "ecosistemi dell''innovazione per la sostenibilità", costruendo "leader territoriali di R&S" ',1,'4-2-1.5',8,'TEST'),
    ROW('INV','2.1 IPCEI',1,'4-2-2.1',11,'TEST'),
    ROW('INV','2.2 Partenariati - Horizon Europe',1,'4-2-2.2',12,'TEST'),
    ROW('INV','2.3 Potenziamento ed estensione tematica e territoriale dei centri di trasferimento tecnologico per segmenti di industria',1,'4-2-2.3',13,'TEST'),
    ROW('INV','3.1 Fondo per la realizzazione di un sistema integrato di infrastrutture di ricerca e innovazione',1,'4-2-3.1',1,'TEST'),
    ROW('INV','3.2 Finanziamento di start-up',1,'4-2-3.2',2,'TEST'),
    ROW('INV','3.3 Introduzione di dottorati innovativi che rispondono ai fabbisogni di innovazione delle imprese e promuovono l''assunzione dei ricercatori da parte delle imprese',1,'4-2-3.3',3,'TEST'),
    ROW('INV','Fiscalità di vantaggio per il lavoro al sud e nuove assunzioni di giovani e donne',1,'5-1-',5,'TEST'),
    ROW('INV','1.1 Potenziamento dei Centri per l’Impiego',1,'5-1-1.1',1,'TEST'),
    ROW('INV','1.2 Creazione di impresa femminili',1,'5-1-1.2',2,'TEST'),
    ROW('INV','1.3 Sistema di certificazione della parità di genere',1,'5-1-1.3',3,'TEST'),
    ROW('INV','1.4 Sistema duale',1,'5-1-1.4',4,'TEST'),
    ROW('INV','2.1 Servizio civile universale',1,'5-1-2.1',6,'TEST'),
    ROW('INV','Costruzione e Miglioramento padiglioni e spazi strutture penitenziarie per adulti e minori',1,'5-2-',1,'TEST'),
    ROW('INV','Transizione verde e digitale città metro',1,'5-2-',8,'TEST'),
    ROW('INV','Piani urbani integrati (Piano complementare)',1,'5-2-',7,'TEST'),
    ROW('INV','1.1: Sostegno alle persone vulnerabili e prevenzione',1,'5-2-1.1',9,'TEST'),
    ROW('INV','1.2: Percorsi di autonomia per persone con disabilità',1,'5-2-1.2',10,'TEST'),
    ROW('INV','1.3:  Housing Temporaneo e Stazioni di posta',1,'5-2-1.3',11,'TEST'),
    ROW('INV','2.1: Investimenti in progetti di rigenerazione urbana, volti a ridurre situazioni di emarginazione e degrado sociale',1,'5-2-2.1',2,'TEST'),
    ROW('INV','2.2: Piani Urbani Integrati (general project) (PNRR)',1,'5-2-2.2',5,'TEST'),
    ROW('INV','2.2 b) Piani urbani integrati - Fondo dei Fondi della BEI',1,'5-2-2.2',4,'TEST'),
    ROW('INV','2.2 a) Piani urbani integrati- superamento degli insediamenti abusiviper combattere lo sfruttamento dei lavoratori in agricoltura',1,'5-2-2.2',3,'TEST'),
    ROW('INV','2.3: Programma innovativo della qualità dell’abitare',1,'5-2-2.3',6,'TEST'),
    ROW('INV','3.1 Sport e inclusione sociale',1,'5-2-3.1',12,'TEST'),
    ROW('INV','Ecosistemi per l’innovazione al Sud in contesti urbani marginalizzati ',1,'5-3-',1,'TEST'),
    ROW('INV','Interventi per le aree del terremoto del 2009 e 2016',1,'5-3-',2,'TEST'),
    ROW('INV','Strategia Nazionale Aree Interne - Miglioramento dell''accessibilità e della sicurezza delle strade',1,'5-3-',5,'TEST'),
    ROW('INV','1. Strategia nazionale per le aree interne',1,'5-3-1',4,'TEST'),
    ROW('INV','2. Valorizzazione dei beni confiscati alle mafie',1,'5-3-2',6,'TEST'),
    ROW('INV','3. Interventi socio-educativi strutturati per combattere la povertà educativa nel Mezzogiorno a sostegno del Terzo Settore',1,'5-3-3',3,'TEST'),
    ROW('INV','4.  Interventi per le Zone Economiche Speciali (ZES)',1,'5-3-4',7,'TEST'),
    ROW('INV','Salute, ambiente, biodiversità e clima',1,'6-1-',4,'TEST'),
    ROW('INV','Personale sanitario e vaccini',1,'6-1-',5,'TEST'),
    ROW('INV','1.1 Case della Comunità e presa in carico della persona',1,'6-1-1.1',1,'TEST'),
    ROW('INV','1.2. Casa come primo luogo di cura e telemedicina',1,'6-1-1.2',2,'TEST'),
    ROW('INV','1.3. Rafforzamento dell''assistenza sanitaria intermedia e delle sue strutture (Ospedali di Comunità).',1,'6-1-1.3',3,'TEST'),
    ROW('INV','Verso un nuovo ospedale sicuro e sostenibile',1,'6-2-',4,'TEST'),
    ROW('INV','Ecosistema innovativo della salute',1,'6-2-',7,'TEST'),
    ROW('INV','Iniziative di ricerca per tecnologie e percorsi innovativi in ??ambito sanitario e assistenziale',1,'6-2-',8,'TEST'),
    ROW('INV','1.1 Ammodernamento del parco tecnologico e digitale ospedaliero',1,'6-2-1.1',1,'TEST'),
    ROW('INV','1.2. Verso un ospedale sicuro e sostenibile',1,'6-2-1.2',2,'TEST'),
    ROW('INV','1.3. Rafforzamento dell''infrastruttura tecnologica e degli strumenti per la raccolta, l’elaborazione, l’analisi dei dati e la simulazione',1,'6-2-1.3',3,'TEST'),
    ROW('INV','2.1. Valorizzazione e potenziamento della ricerca biomedica del SSN',1,'6-2-2.1',5,'TEST'),
    ROW('INV','2.2 Sviluppo delle competenze tecniche-professionali, digitali e manageriali del personale del sistema sanitario',1,'6-2-2.2',6,'TEST'),
    ROW('SUB','1.3.2: Single Digital Gateway',1,'1-1-1.3',2,'TEST'),
    ROW('SUB','1.3.1: Piattaforma nazionale digitale dei dati',1,'1-1-1.3',1,'TEST'),
    ROW('SUB','1.4.1: Citizen experience - Miglioramento della qualità e dell''usabilità dei servizi pubblici digitali ',1,'1-1-1.4',1,'TEST'),
    ROW('SUB','1.4.2: Citizen inclusion - Miglioramento dell''accessibilità dei servizi pubblici digitali',1,'1-1-1.4',2,'TEST'),
    ROW('SUB','1.4.3: Servizi digitali e cittadinanza digitale - piattaforme e applicativi',1,'1-1-1.4',3,'TEST'),
    ROW('SUB','1.4.4: Estensione dell''utilizzo delle piattaforme nazionali di Identità Digitale (SPID, CIE) e dell''anagrafe nazionale digitale (ANPR) ',1,'1-1-1.4',4,'TEST'),
    ROW('SUB','1.4.5: Piattaforma Notifiche Digitali',1,'1-1-1.4',5,'TEST'),
    ROW('SUB','1.4.6: Mobility as a service for Italy',1,'1-1-1.4',6,'TEST'),
    ROW('SUB','1.6.1: Digitalizzazione del Ministero dell''Interno',1,'1-1-1.6',1,'TEST'),
    ROW('SUB','1.6.2: Digitalizzazione del Ministero della Giustizia',1,'1-1-1.6',2,'TEST'),
    ROW('SUB','1.6.3: Digitalizzazione dell''Istituto Nazionale della Previdenza Sociale (INPS) e dell''Istituto Nazionale per l''Assicurazione contro gli Infortuni sul Lavoro (INAIL)',1,'1-1-1.6',3,'TEST'),
    ROW('SUB','1.6.4: Digitalizzazione del Ministero della Difesa',1,'1-1-1.6',4,'TEST'),
    ROW('SUB','1.6.5: Digitalizzazione Consiglio di Stato',1,'1-1-1.6',5,'TEST'),
    ROW('SUB','1.6.6: Digitalizzazione Guardia di Finanza',1,'1-1-1.6',6,'TEST'),
    ROW('SUB','1.7.1: Servizio Civile Digitale',1,'1-1-1.7',1,'TEST'),
    ROW('SUB','1.7.2: Rete di servizi di facilitazione digitale',1,'1-1-1.7',2,'TEST'),
    ROW('SUB','2.1.1: Creazione di una piattaforma unica di reclutamento',1,'1-1-2.1',1,'TEST'),
    ROW('SUB','2.1.2: Procedure per l''assunzione di profili tecnici',1,'1-1-2.1',2,'TEST'),
    ROW('SUB','2.2.3:  Digitalizzazione delle procedure (SUAP & SUE)',1,'1-1-2.2',3,'TEST'),
    ROW('SUB','2.2.4: Monitoraggio e comunicazione delle azioni di semplificazione',1,'1-1-2.2',4,'TEST'),
    ROW('SUB','2.2.5: Amministrazione pubblica orientata ai risultati ',1,'1-1-2.2',5,'TEST'),
    ROW('SUB','2.2.1: Assistenza tecnica a livello centrale e locale ',1,'1-1-2.2',1,'TEST'),
    ROW('SUB','2.2.2: Semplificazione e standardizzazione delle procedure',1,'1-1-2.2',2,'TEST'),
    ROW('SUB','2.3.1: Investimenti in istruzione e formazione',1,'1-1-2.3',1,'TEST'),
    ROW('SUB','2.3.2: Sviluppo delle capacità nella pianificazione, organizzazione e formazione strategica della forza lavoro',1,'1-1-2.3',2,'TEST'),
    ROW('SUB','1.1.1 Credito d''imposta per i beni strumentali 4.0',1,'1-2-1',1,'TEST'),
    ROW('SUB','1.1.2 Credito d''imposta (immateriali non 4.0)',1,'1-2-1',2,'TEST'),
    ROW('SUB','1.1.3 Crediti d''imposta per beni immateriali tradizionali',1,'1-2-1',3,'TEST'),
    ROW('SUB','1.1.4 Credito d''imposta per R&D&I',1,'1-2-1',4,'TEST'),
    ROW('SUB','1.1.5 Credito d’imposta formazione',1,'1-2-1',5,'TEST'),
    ROW('SUB','3.1 Piano Italia a 1 Gbps',1,'1-2-3',1,'TEST'),
    ROW('SUB','3.2 Italia 5G - Corridoi 5G, Strade extraurbane (+ 5G Aree bianche)',1,'1-2-3',2,'TEST'),
    ROW('SUB','3.3 Scuola Connessa',1,'1-2-3',3,'TEST'),
    ROW('SUB','3.4 Sanità Connessa',1,'1-2-3',4,'TEST'),
    ROW('SUB','3.5 Collegamento isole minori',1,'1-2-3',5,'TEST'),
    ROW('SUB','4.1 SatCom',1,'1-2-4',1,'TEST'),
    ROW('SUB','4.2 Osservazione della Terra',1,'1-2-4',2,'TEST'),
    ROW('SUB','4.3 Space Factory',1,'1-2-4',3,'TEST'),
    ROW('SUB','4.4 In-Orbit Economy',1,'1-2-4',4,'TEST'),
    ROW('SUB','5.1 Rifinanziamento e ridefinizione del Fondo 394/81 gestito da SIMEST',1,'1-2-5',1,'TEST'),
    ROW('SUB','5.2 Competitività e resilienza delle filiere produttive (CdS)',1,'1-2-5',2,'TEST'),
    ROW('SUB','1.1.1: Piano nazionale di digitalizzazione per i beni culturali',1,'1-3-1.1',1,'TEST'),
    ROW('SUB','1.1.2: Sistema di certificazione dell''identità digitale per i beni culturali',1,'1-3-1.1',2,'TEST'),
    ROW('SUB','1.1.3: Servizi di infrastruttura cloud',1,'1-3-1.1',3,'TEST'),
    ROW('SUB','1.1.4: Infrastruttura digitale per il patrimonio culturale',1,'1-3-1.1',4,'TEST'),
    ROW('SUB','1.1.5: Digitalizzazione ',1,'1-3-1.1',5,'TEST'),
    ROW('SUB','1.1.6: Formazione e miglioramento delle competenze digitali',1,'1-3-1.1',6,'TEST'),
    ROW('SUB','1.1.7: Supporto operativo',1,'1-3-1.1',7,'TEST'),
    ROW('SUB','1.1.8: Polo di conservazione digitale',1,'1-3-1.1',8,'TEST'),
    ROW('SUB','1.1.9: Portale dei procedimenti e dei servizi ai cittadini',1,'1-3-1.1',9,'TEST'),
    ROW('SUB','1.1.10: Piattaforma di accesso integrata della Digital Library',1,'1-3-1.1',10,'TEST'),
    ROW('SUB','1.1.11: Piattaforma di co-creazione e crowdsourcing ',1,'1-3-1.1',11,'TEST'),
    ROW('SUB','1.1.12: Piattaforma di servizi digitali per sviluppatori e imprese culturali ',1,'1-3-1.1',12,'TEST'),
    ROW('SUB','3.3.1 Interventi per migliorare l''ecosistema in cui operano i settori culturali e creativi, incoraggiando la cooperazione tra operatori cultuari e organizzazioni e facilitando upskill e reskill',1,'1-3-3.3',1,'TEST'),
    ROW('SUB','3.3.2 Sostegno ai settori culturali e creativi per l''innovazione e la transizione digitale ',1,'1-3-3.3',2,'TEST'),
    ROW('SUB','3.3.3 Promuovere la riduzione dell''impronta ecologica degli eventi culturali',1,'1-3-3.3',3,'TEST'),
    ROW('SUB','3.3.4 Promuovere l''innovazione e l''eco-progettazione inclusiva',1,'1-3-3.3',4,'TEST'),
    ROW('SUB','4.2.1 Miglioramento delle infrastrutture di ricettività attraverso lo strumento del Tax credit',1,'1-3-4.2',1,'TEST'),
    ROW('SUB','4.2.2 Digitalizzazione Agenzie e Tour Operator',1,'1-3-4.2',2,'TEST'),
    ROW('SUB','4.2.3 Sviluppo e resilienza delle imprese del settore turistico (Fondo dei Fondi BEI) ',1,'1-3-4.2',3,'TEST'),
    ROW('SUB','4.2.4 Sostegno alla nascita e al consolidamento delle pmi turismo (Sezione speciale “turismo” del Fondo di Garanzia per le PMI)',1,'1-3-4.2',4,'TEST'),
    ROW('SUB','4.2.5 Fondo rotativo imprese (FRI )  per il sostegno alle imprese e gli investimenti di sviluppo',1,'1-3-4.2',5,'TEST'),
    ROW('SUB','4.2.6 Valorizzazione, competitività e tutela del patrimonio ricettivo attraverso la partecipazione del Min. Turismo nel Fondo Nazionale Turismo',1,'1-3-4.2',6,'TEST'),
    ROW('SUB','4.3.1 Roman Cultural Heritage for EU-Next Generation',1,'1-3-4.3',1,'TEST'),
    ROW('SUB','4.3.2 I percorsi Giubilari 2025',1,'1-3-4.3',2,'TEST'),
    ROW('SUB','4.3.3 La città condivisa',1,'1-3-4.3',3,'TEST'),
    ROW('SUB','4.3.4 Mitingodiverde',1,'1-3-4.3',4,'TEST'),
    ROW('SUB','4.3.5 Roma 4.0',1,'1-3-4.3',5,'TEST'),
    ROW('SUB','4.3.6 Amanotesa',1,'1-3-4.3',6,'TEST'),
    ROW('SUB','4.1: Rafforzamento mobilità ciclistica (Ciclovie turistiche)',1,'2-2-4.1',1,'TEST'),
    ROW('SUB','4.1: Rafforzamento mobilità ciclistica (Ciclovie urbane)',1,'2-2-4.1',2,'TEST'),
    ROW('SUB','4.4.1: Rinnovo flotte Bus',1,'2-2-4.4',1,'TEST'),
    ROW('SUB','4.4.2: Rinnovo flotte treni',1,'2-2-4.4',2,'TEST'),
    ROW('SUB','4.4.3: Vigili del Fuoco',1,'2-2-4.4',3,'TEST'),
    ROW('SUB','5.1.1 Tecnologia PV',1,'2-2-5.1',1,'TEST'),
    ROW('SUB','5.1.2 Industria eolica',1,'2-2-5.1',2,'TEST'),
    ROW('SUB','5.1.3 Settore Batterie',1,'2-2-5.1',3,'TEST'),
    ROW('SUB','2.1.b Misure per la gestione del rischio di alluvione e per la riduzione del rischio idrogeologico (FSC)',1,'2-4-2.1',2,'TEST'),
    ROW('SUB','2.1a Misure per la gestione del rischio di alluvione e per la riduzione del rischio idrogeologico (PNRR)',1,'2-4-2.1',1,'TEST'),
    ROW('SUB','1.1 Collegamenti ferroviari ad Alta Velocità con il Mezzogiorno per passeggeri e merci (Napoli - Bari)',1,'3-1-1.1',1,'TEST'),
    ROW('SUB','1.1 Collegamenti ferroviari ad Alta Velocità con il Mezzogiorno per passeggeri e merci (Palermo-Catania)',1,'3-1-1.1',2,'TEST'),
    ROW('SUB','1.1 Collegamenti ferroviari ad Alta Velocità con il Mezzogiorno per passeggeri e merci (Salerno-Reggio Calabria) ',1,'3-1-1.1',3,'TEST'),
    ROW('SUB','1.2.1 Linee di collegamento ad Alta Velocità con l’Europa nel Nord (Brescia-Verona-Vicenza - Padova)',1,'3-1-1.2',1,'TEST'),
    ROW('SUB','1.2.2 Linee di collegamento ad Alta Velocità con l’Europa nel Nord (Liguria-Alpi)',1,'3-1-1.2',2,'TEST'),
    ROW('SUB','1.2.3 Linee di collegamento ad Alta Velocità con l’Europa nel Nord (Verona-Brennero - opere di adduzione)',1,'3-1-1.2',3,'TEST'),
    ROW('SUB','1.3 Collegamenti diagonali (Taranto-Metaponto-Potenza-Battipaglia)',1,'3-1-1.3',3,'TEST'),
    ROW('SUB','1.3 Collegamenti diagonali (Roma-Pescara)',1,'3-1-1.3',2,'TEST'),
    ROW('SUB','1.3 Collegamenti diagonali (Orte-Falconara)',1,'3-1-1.3',1,'TEST'),
    ROW('SUB','2.1.1: LogIN Center',1,'3-2-2.1',1,'TEST'),
    ROW('SUB','2.1.2: Rete di porti e interporti',1,'3-2-2.1',2,'TEST'),
    ROW('SUB','2.1.3: LogIN Business',1,'3-2-2.1',3,'TEST'),
    ROW('SUB','2.2.1: Digitalizzazione della manutenzione e gestione dei dati aeronautici',1,'3-2-2.2',1,'TEST'),
    ROW('SUB','2.2.2: Ottimizzazione delle procedure di avvicinamento APT',1,'3-2-2.2',2,'TEST'),
    ROW('SUB','1.1.1: Sostegno alle persone vulnerabili e prevenzione dell''istituzionalizzazione - Intervento 1) Azioni volte a sostenere le capacità genitoriali e prevenire la vulnerabilità delle famiglie e dei bambini',1,'5-2-1.1',1,'TEST'),
    ROW('SUB','1.1.2: Sostegno alle persone vulnerabili e prevenzione dell''istituzionalizzazione  - Intervento 2) Azioni per una vita autonoma e deistituzionalizzazione per gli anziani',1,'5-2-1.1',2,'TEST'),
    ROW('SUB','1.1.3: Sostegno alle persone vulnerabili e prevenzione dell''istituzionalizzazione  - Intervento 3) Rafforzare i servizi sociali domiciliari per garantire una dimissione assistita precoce e prevenire il ricovero in ospedale',1,'5-2-1.1',3,'TEST'),
    ROW('SUB','1.1.4: Sostegno alle persone vulnerabili e prevenzione dell''istituzionalizzazione  - Intervento 4) Rafforzare i servizi sociali e prevenire il burn out tra gli assistenti sociali',1,'5-2-1.1',4,'TEST'),
    ROW('SUB','2.3 Social housing - Piano innovativo per la qualità abitativa (PinQuA) -  Interventi ad alto impatto strategico sul territorio nazionale',1,'5-2-2.3',1,'TEST'),
    ROW('SUB','2.3 Social housing - Piano innovativo per la qualità abitativa (PinQuA) - Riqualificazione e incremento dell''edilizia sociale, ristrutturazione e rigenerazione della società urbana, miglioramento dell''accessibilità e sicurezza urbana',1,'5-2-2.3',2,'TEST'),
    ROW('SUB','1.1 NSIA (Strategia nazionale per le aree interne): Potenziamento dei servizi e delle infrastrutture sociali della comunità',1,'5-3-1',1,'TEST'),
    ROW('SUB','1.2 NSIA (Strategia nazionale per le aree interne): Strutture sanitarie di prossimità territoriale',1,'5-3-1',2,'TEST'),
    ROW('SUB','Investimenti infrastrutturali per Zone Economiche Speciali - Soggetto attuatore RFI',1,'5-3-4',1,'TEST'),
    ROW('SUB','Investimenti infrastrutturali per Zone Economiche Speciali - Soggetto attuatore Anas',1,'5-3-4',2,'TEST'),
    ROW('SUB','Investimenti infrastrutturali per Zone Economiche Speciali - Soggetto attuatore AdSP',1,'5-3-4',3,'TEST'),
    ROW('SUB','Investimenti infrastrutturali per Zone Economiche Speciali - Soggetto attuatore Regioni',1,'5-3-4',4,'TEST'),
    ROW('SUB','1.2.1  Casa come primo luogo di cura  (Adi)',1,'6-1-1.1',1,'TEST'),
    ROW('SUB','1.2.2  Implementazione delle Centrali operative territoriali (COT)',1,'6-1-1.1',2,'TEST'),
    ROW('SUB','1.2.3  Telemedicina per un migliore supporto ai pazienti cronici',1,'6-1-1.1',3,'TEST'),
    ROW('SUB','1.1.1 Ammodernamento del parco tecnologico e digitale ospedaliero (Digitalizzazione)',1,'6-2-1.1',1,'TEST'),
    ROW('SUB','1.1.2 Ammodernamento del parco tecnologico e digitale ospedaliero (grandi apprecchiature)',1,'6-2-1.1',2,'TEST'),
    ROW('SUB','1.3.1 Rafforzamento dell''infrastruttura tecnologica e degli strumenti per la raccolta, l’elaborazione, l’analisi dei dati e la simulazione (FSE)',1,'6-2-1.3',1,'TEST'),
    ROW('SUB','1.3.2 Rafforzamento dell''infrastruttura tecnologica e degli strumenti per la raccolta, l’elaborazione, l’analisi dei dati e la simulazione (Potenziamento, modello predittivo, SDK ….)',1,'6-2-1.3',2,'TEST'),
    ROW('SUB','2.2 (a) Sviluppo delle competenze tecniche-professionali, digitali e manageriali del personale del sistema sanitario. Sub-misura: borse aggiuntive in formazione di medicina generale',1,'6-6-2.2',1,'TEST'),
    ROW('SUB','2.2 (b) Sviluppo delle competenze tecniche-professionali, digitali e manageriali del personale del sistema sanitario: Sub-misura: corso di formazione in infezioni ospedaliere',1,'6-6-2.2',2,'TEST'),
    ROW('SUB','2.2 (c) Sviluppo delle competenze tecniche-professionali, digitali e manageriali del personale del sistema sanitario: Sub-misura: corso di formazione manageriale',1,'6-6-2.2',3,'TEST'),
    ROW('SUB','2.2 (d) Sviluppo delle competenze tecniche-professionali, digitali e manageriali del personale del sistema sanitario. Sub-misure: contratti di formazione medico-specialistica.',1,'6-6-2.2',4,'TEST')
) AS tmp(cod_tipologia_gerarchia, des_elemento, attivo, cod_livello_elemento, `order`, descrizione_ente)
JOIN ente ON ente.descrizione = tmp.descrizione_ente
WHERE NOT EXISTS (
    SELECT 1
    FROM elemento_gerarchia curr
    WHERE curr.cod_tipologia_gerarchia = tmp.cod_tipologia_gerarchia
    AND curr.des_elemento = tmp.des_elemento
    AND curr.ente_id = ente.id_ente
);

INSERT INTO struttura_gerarchia(elemento_gerarchia_id_padre, elemento_gerarchia_id_figlio)
SELECT padre.id, figlio.id
FROM (VALUES
    ROW('MIS',1,'1','COM',1,'1-1','TEST'),
    ROW('MIS',1,'1','COM',2,'1-2','TEST'),
    ROW('MIS',1,'1','COM',3,'1-3','TEST'),
    ROW('MIS',2,'2','COM',1,'2-1','TEST'),
    ROW('MIS',2,'2','COM',2,'2-2','TEST'),
    ROW('MIS',2,'2','COM',3,'2-3','TEST'),
    ROW('MIS',2,'2','COM',4,'2-4','TEST'),
    ROW('MIS',3,'3','COM',1,'3-1','TEST'),
    ROW('MIS',3,'3','COM',2,'3-2','TEST'),
    ROW('MIS',4,'4','COM',1,'4-1','TEST'),
    ROW('MIS',4,'4','COM',2,'4-2','TEST'),
    ROW('MIS',5,'5','COM',1,'5-1','TEST'),
    ROW('MIS',5,'5','COM',2,'5-2','TEST'),
    ROW('MIS',5,'5','COM',3,'5-3','TEST'),
    ROW('MIS',6,'6','COM',1,'6-1','TEST'),
    ROW('MIS',6,'6','COM',2,'6-2','TEST'),
    ROW('COM',1,'1-1','INV',1,'1-1-1.1','TEST'),
    ROW('COM',1,'1-1','INV',2,'1-1-1.2','TEST'),
    ROW('COM',1,'1-1','INV',3,'1-1-1.3','TEST'),
    ROW('COM',1,'1-1','INV',4,'1-1-1.4','TEST'),
    ROW('COM',1,'1-1','INV',5,'1-1-','TEST'),
    ROW('COM',1,'1-1','INV',6,'1-1-','TEST'),
    ROW('COM',1,'1-1','INV',7,'1-1-','TEST'),
    ROW('COM',1,'1-1','INV',8,'1-1-1.5','TEST'),
    ROW('COM',1,'1-1','INV',9,'1-1-1.6','TEST'),
    ROW('COM',1,'1-1','INV',10,'1-1-1.7','TEST'),
    ROW('COM',1,'1-1','INV',11,'1-1-2.1','TEST'),
    ROW('COM',1,'1-1','INV',12,'1-1-2.2','TEST'),
    ROW('COM',1,'1-1','INV',13,'1-1-2.3','TEST'),
    ROW('COM',1,'1-1','INV',14,'1-1-3.1','TEST'),
    ROW('COM',1,'1-1','INV',15,'1-1-3.2','TEST'),
    ROW('COM',2,'1-2','INV',1,'1-2-2','TEST'),
    ROW('COM',2,'1-2','INV',2,'1-2-3','TEST'),
    ROW('COM',2,'1-2','INV',3,'1-2-4','TEST'),
    ROW('COM',2,'1-2','INV',4,'1-2-','TEST'),
    ROW('COM',2,'1-2','INV',5,'1-2-','TEST'),
    ROW('COM',2,'1-2','INV',6,'1-2-5','TEST'),
    ROW('COM',2,'1-2','INV',7,'1-2-6.1','TEST'),
    ROW('COM',2,'1-2','INV',8,'1-2-1','TEST'),
    ROW('COM',2,'1-2','INV',9,'1-2-','TEST'),
    ROW('COM',3,'1-3','INV',1,'1-3-3.2','TEST'),
    ROW('COM',3,'1-3','INV',2,'1-3-3.3','TEST'),
    ROW('COM',3,'1-3','INV',3,'1-3-1.1','TEST'),
    ROW('COM',3,'1-3','INV',4,'1-3-1.2','TEST'),
    ROW('COM',3,'1-3','INV',5,'1-3-1.3','TEST'),
    ROW('COM',3,'1-3','INV',6,'1-3-','TEST'),
    ROW('COM',3,'1-3','INV',7,'1-3-2.1','TEST'),
    ROW('COM',3,'1-3','INV',8,'1-3-2.2','TEST'),
    ROW('COM',3,'1-3','INV',9,'1-3-2.3','TEST'),
    ROW('COM',3,'1-3','INV',10,'1-3-2.4','TEST'),
    ROW('COM',3,'1-3','INV',11,'1-3-4.1','TEST'),
    ROW('COM',3,'1-3','INV',12,'1-3-4.2','TEST'),
    ROW('COM',3,'1-3','INV',13,'1-3-4.3','TEST'),
    ROW('COM',1,'2-1','INV',1,'2-1-2.1','TEST'),
    ROW('COM',1,'2-1','INV',2,'2-1-2.2','TEST'),
    ROW('COM',1,'2-1','INV',3,'2-1-2.3','TEST'),
    ROW('COM',1,'2-1','INV',4,'2-1-','TEST'),
    ROW('COM',1,'2-1','INV',5,'2-1-1.1','TEST'),
    ROW('COM',1,'2-1','INV',6,'2-1-1.2','TEST'),
    ROW('COM',1,'2-1','INV',7,'2-1-3.1','TEST'),
    ROW('COM',1,'2-1','INV',8,'2-1-3.2','TEST'),
    ROW('COM',1,'2-1','INV',9,'2-1-3.3','TEST'),
    ROW('COM',2,'2-2','INV',1,'2-2-1.1','TEST'),
    ROW('COM',2,'2-2','INV',2,'2-2-1.2','TEST'),
    ROW('COM',2,'2-2','INV',3,'2-2-1.3','TEST'),
    ROW('COM',2,'2-2','INV',4,'2-2-1.4','TEST'),
    ROW('COM',2,'2-2','INV',5,'2-2-5.1','TEST'),
    ROW('COM',2,'2-2','INV',6,'2-2-','TEST'),
    ROW('COM',2,'2-2','INV',7,'2-2-5.2','TEST'),
    ROW('COM',2,'2-2','INV',8,'2-2-5.3','TEST'),
    ROW('COM',2,'2-2','INV',9,'2-2-5.4','TEST'),
    ROW('COM',2,'2-2','INV',10,'2-2-3.1','TEST'),
    ROW('COM',2,'2-2','INV',11,'2-2-3.2','TEST'),
    ROW('COM',2,'2-2','INV',12,'2-2-3.3','TEST'),
    ROW('COM',2,'2-2','INV',13,'2-2-3.4','TEST'),
    ROW('COM',2,'2-2','INV',14,'2-2-3.5','TEST'),
    ROW('COM',2,'2-2','INV',15,'2-2-2.1','TEST'),
    ROW('COM',2,'2-2','INV',16,'2-2-2.2','TEST'),
    ROW('COM',2,'2-2','INV',17,'2-2-4.1','TEST'),
    ROW('COM',2,'2-2','INV',18,'2-2-4.2','TEST'),
    ROW('COM',2,'2-2','INV',19,'2-2-4.3','TEST'),
    ROW('COM',2,'2-2','INV',20,'2-2-4.4','TEST'),
    ROW('COM',2,'2-2','INV',21,'2-2-','TEST'),
    ROW('COM',2,'2-2','INV',22,'2-2-','TEST'),
    ROW('COM',3,'2-3','INV',1,'2-3-1.1','TEST'),
    ROW('COM',3,'2-3','INV',2,'2-3-1.2','TEST'),
    ROW('COM',3,'2-3','INV',3,'2-3-','TEST'),
    ROW('COM',3,'2-3','INV',4,'2-3-','TEST'),
    ROW('COM',3,'2-3','INV',5,'2-3-2.1','TEST'),
    ROW('COM',3,'2-3','INV',6,'2-3-','TEST'),
    ROW('COM',3,'2-3','INV',7,'2-3-3.1','TEST'),
    ROW('COM',3,'2-3','INV',21,'2-3-','TEST'),
    ROW('COM',4,'2-4','INV',1,'2-4-2.1','TEST'),
    ROW('COM',4,'2-4','INV',2,'2-4-2.2','TEST'),
    ROW('COM',4,'2-4','INV',3,'2-4-4.1','TEST'),
    ROW('COM',4,'2-4','INV',4,'2-4-4.2','TEST'),
    ROW('COM',4,'2-4','INV',5,'2-4-4.3','TEST'),
    ROW('COM',4,'2-4','INV',6,'2-4-4.4','TEST'),
    ROW('COM',4,'2-4','INV',7,'2-4-1.1','TEST'),
    ROW('COM',4,'2-4','INV',8,'2-4-3.1','TEST'),
    ROW('COM',4,'2-4','INV',9,'2-4-3.2','TEST'),
    ROW('COM',4,'2-4','INV',10,'2-4-3.3','TEST'),
    ROW('COM',4,'2-4','INV',11,'2-4-3.4','TEST'),
    ROW('COM',4,'2-4','INV',12,'2-4-3.5','TEST'),
    ROW('COM',1,'3-1','INV',1,'3-1-1.1','TEST'),
    ROW('COM',1,'3-1','INV',2,'3-1-1.2','TEST'),
    ROW('COM',1,'3-1','INV',3,'3-1-1.3','TEST'),
    ROW('COM',1,'3-1','INV',4,'3-1-1.4','TEST'),
    ROW('COM',1,'3-1','INV',5,'3-1-1.5','TEST'),
    ROW('COM',1,'3-1','INV',6,'3-1-1.6','TEST'),
    ROW('COM',1,'3-1','INV',7,'3-1-1.7','TEST'),
    ROW('COM',1,'3-1','INV',8,'3-1-1.8','TEST'),
    ROW('COM',1,'3-1','INV',9,'3-1-','TEST'),
    ROW('COM',1,'3-1','INV',10,'3-1-','TEST'),
    ROW('COM',1,'3-1','INV',11,'3-1-','TEST'),
    ROW('COM',1,'3-1','INV',12,'3-1-','TEST'),
    ROW('COM',2,'3-2','INV',1,'3-2-2.1','TEST'),
    ROW('COM',2,'3-2','INV',2,'3-2-2.2','TEST'),
    ROW('COM',2,'3-2','INV',3,'3-2-1.1','TEST'),
    ROW('COM',2,'3-2','INV',4,'3-2-','TEST'),
    ROW('COM',2,'3-2','INV',5,'3-2-','TEST'),
    ROW('COM',2,'3-2','INV',6,'3-2-','TEST'),
    ROW('COM',2,'3-2','INV',7,'3-2-','TEST'),
    ROW('COM',2,'3-2','INV',8,'3-2-','TEST'),
    ROW('COM',1,'4-1','INV',1,'4-1-3.1','TEST'),
    ROW('COM',1,'4-1','INV',2,'4-1-3.2','TEST'),
    ROW('COM',1,'4-1','INV',3,'4-1-3.3','TEST'),
    ROW('COM',1,'4-1','INV',4,'4-1-3.4','TEST'),
    ROW('COM',1,'4-1','INV',5,'4-1-2.1','TEST'),
    ROW('COM',1,'4-1','INV',6,'4-1-1.1','TEST'),
    ROW('COM',1,'4-1','INV',7,'4-1-1.2','TEST'),
    ROW('COM',1,'4-1','INV',8,'4-1-1.3','TEST'),
    ROW('COM',1,'4-1','INV',9,'4-1-1.4','TEST'),
    ROW('COM',1,'4-1','INV',10,'4-1-1.5','TEST'),
    ROW('COM',1,'4-1','INV',11,'4-1-1.6','TEST'),
    ROW('COM',1,'4-1','INV',12,'4-1-1.7','TEST'),
    ROW('COM',1,'4-1','INV',13,'4-1-4.1','TEST'),
    ROW('COM',2,'4-2','INV',1,'4-2-3.1','TEST'),
    ROW('COM',2,'4-2','INV',2,'4-2-3.2','TEST'),
    ROW('COM',2,'4-2','INV',3,'4-2-3.3','TEST'),
    ROW('COM',2,'4-2','INV',4,'4-2-1.1','TEST'),
    ROW('COM',2,'4-2','INV',5,'4-2-1.2','TEST'),
    ROW('COM',2,'4-2','INV',6,'4-2-1.3','TEST'),
    ROW('COM',2,'4-2','INV',7,'4-2-1.4','TEST'),
    ROW('COM',2,'4-2','INV',8,'4-2-1.5','TEST'),
    ROW('COM',2,'4-2','INV',9,'4-2-','TEST'),
    ROW('COM',2,'4-2','INV',10,'4-2-','TEST'),
    ROW('COM',2,'4-2','INV',11,'4-2-2.1','TEST'),
    ROW('COM',2,'4-2','INV',12,'4-2-2.2','TEST'),
    ROW('COM',2,'4-2','INV',13,'4-2-2.3','TEST'),
    ROW('COM',1,'5-1','INV',1,'5-1-1.1','TEST'),
    ROW('COM',1,'5-1','INV',2,'5-1-1.2','TEST'),
    ROW('COM',1,'5-1','INV',3,'5-1-1.3','TEST'),
    ROW('COM',1,'5-1','INV',4,'5-1-1.4','TEST'),
    ROW('COM',1,'5-1','INV',5,'5-1-','TEST'),
    ROW('COM',1,'5-1','INV',6,'5-1-2.1','TEST'),
    ROW('COM',2,'5-2','INV',1,'5-2-','TEST'),
    ROW('COM',2,'5-2','INV',2,'5-2-2.1','TEST'),
    ROW('COM',2,'5-2','INV',3,'5-2-2.2','TEST'),
    ROW('COM',2,'5-2','INV',4,'5-2-2.2','TEST'),
    ROW('COM',2,'5-2','INV',5,'5-2-2.2','TEST'),
    ROW('COM',2,'5-2','INV',6,'5-2-2.3','TEST'),
    ROW('COM',2,'5-2','INV',7,'5-2-','TEST'),
    ROW('COM',2,'5-2','INV',8,'5-2-','TEST'),
    ROW('COM',2,'5-2','INV',9,'5-2-1.1','TEST'),
    ROW('COM',2,'5-2','INV',10,'5-2-1.2','TEST'),
    ROW('COM',2,'5-2','INV',11,'5-2-1.3','TEST'),
    ROW('COM',2,'5-2','INV',12,'5-2-3.1','TEST'),
    ROW('COM',3,'5-3','INV',1,'5-3-','TEST'),
    ROW('COM',3,'5-3','INV',2,'5-3-','TEST'),
    ROW('COM',3,'5-3','INV',3,'5-3-3','TEST'),
    ROW('COM',3,'5-3','INV',4,'5-3-1','TEST'),
    ROW('COM',3,'5-3','INV',5,'5-3-','TEST'),
    ROW('COM',3,'5-3','INV',6,'5-3-2','TEST'),
    ROW('COM',3,'5-3','INV',7,'5-3-4','TEST'),
    ROW('COM',1,'6-1','INV',1,'6-1-1.1','TEST'),
    ROW('COM',1,'6-1','INV',2,'6-1-1.2','TEST'),
    ROW('COM',1,'6-1','INV',3,'6-1-1.3','TEST'),
    ROW('COM',1,'6-1','INV',4,'6-1-','TEST'),
    ROW('COM',1,'6-1','INV',5,'6-1-','TEST'),
    ROW('COM',2,'6-2','INV',1,'6-2-1.1','TEST'),
    ROW('COM',2,'6-2','INV',2,'6-2-1.2','TEST'),
    ROW('COM',2,'6-2','INV',3,'6-2-1.3','TEST'),
    ROW('COM',2,'6-2','INV',4,'6-2-','TEST'),
    ROW('COM',2,'6-2','INV',5,'6-2-2.1','TEST'),
    ROW('COM',2,'6-2','INV',6,'6-2-2.2','TEST'),
    ROW('COM',2,'6-2','INV',7,'6-2-','TEST'),
    ROW('COM',2,'6-2','INV',8,'6-2-','TEST'),
    ROW('INV',3,'1-1-1.3','SUB',1,'1-1-1.3','TEST'),
    ROW('INV',3,'1-1-1.3','SUB',2,'1-1-1.3','TEST'),
    ROW('INV',4,'1-1-1.4','SUB',1,'1-1-1.4','TEST'),
    ROW('INV',4,'1-1-1.4','SUB',2,'1-1-1.4','TEST'),
    ROW('INV',4,'1-1-1.4','SUB',3,'1-1-1.4','TEST'),
    ROW('INV',4,'1-1-1.4','SUB',4,'1-1-1.4','TEST'),
    ROW('INV',4,'1-1-1.4','SUB',5,'1-1-1.4','TEST'),
    ROW('INV',4,'1-1-1.4','SUB',6,'1-1-1.4','TEST'),
    ROW('INV',9,'1-1-1.6','SUB',1,'1-1-1.6','TEST'),
    ROW('INV',9,'1-1-1.6','SUB',2,'1-1-1.6','TEST'),
    ROW('INV',9,'1-1-1.6','SUB',3,'1-1-1.6','TEST'),
    ROW('INV',9,'1-1-1.6','SUB',4,'1-1-1.6','TEST'),
    ROW('INV',9,'1-1-1.6','SUB',5,'1-1-1.6','TEST'),
    ROW('INV',9,'1-1-1.6','SUB',6,'1-1-1.6','TEST'),
    ROW('INV',10,'1-1-1.7','SUB',1,'1-1-1.7','TEST'),
    ROW('INV',10,'1-1-1.7','SUB',2,'1-1-1.7','TEST'),
    ROW('INV',11,'1-1-2.1','SUB',1,'1-1-2.1','TEST'),
    ROW('INV',11,'1-1-2.1','SUB',2,'1-1-2.1','TEST'),
    ROW('INV',12,'1-1-2.2','SUB',1,'1-1-2.2','TEST'),
    ROW('INV',12,'1-1-2.2','SUB',2,'1-1-2.2','TEST'),
    ROW('INV',12,'1-1-2.2','SUB',3,'1-1-2.2','TEST'),
    ROW('INV',12,'1-1-2.2','SUB',4,'1-1-2.2','TEST'),
    ROW('INV',12,'1-1-2.2','SUB',5,'1-1-2.2','TEST'),
    ROW('INV',13,'1-1-2.3','SUB',1,'1-1-2.3','TEST'),
    ROW('INV',13,'1-1-2.3','SUB',2,'1-1-2.3','TEST'),
    ROW('INV',2,'1-2-3','SUB',1,'1-2-3','TEST'),
    ROW('INV',2,'1-2-3','SUB',2,'1-2-3','TEST'),
    ROW('INV',2,'1-2-3','SUB',3,'1-2-3','TEST'),
    ROW('INV',2,'1-2-3','SUB',4,'1-2-3','TEST'),
    ROW('INV',2,'1-2-3','SUB',5,'1-2-3','TEST'),
    ROW('INV',3,'1-2-4','SUB',1,'1-2-4','TEST'),
    ROW('INV',3,'1-2-4','SUB',2,'1-2-4','TEST'),
    ROW('INV',3,'1-2-4','SUB',3,'1-2-4','TEST'),
    ROW('INV',3,'1-2-4','SUB',4,'1-2-4','TEST'),
    ROW('INV',6,'1-2-5','SUB',1,'1-2-5','TEST'),
    ROW('INV',6,'1-2-5','SUB',2,'1-2-5','TEST'),
    ROW('INV',8,'1-2-1','SUB',1,'1-2-1','TEST'),
    ROW('INV',8,'1-2-1','SUB',2,'1-2-1','TEST'),
    ROW('INV',8,'1-2-1','SUB',3,'1-2-1','TEST'),
    ROW('INV',8,'1-2-1','SUB',4,'1-2-1','TEST'),
    ROW('INV',8,'1-2-1','SUB',5,'1-2-1','TEST'),
    ROW('INV',2,'1-3-3.3','SUB',1,'1-3-3.3','TEST'),
    ROW('INV',2,'1-3-3.3','SUB',2,'1-3-3.3','TEST'),
    ROW('INV',2,'1-3-3.3','SUB',3,'1-3-3.3','TEST'),
    ROW('INV',2,'1-3-3.3','SUB',4,'1-3-3.3','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',1,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',2,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',3,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',4,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',5,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',6,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',7,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',8,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',9,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',10,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',11,'1-3-1.1','TEST'),
    ROW('INV',3,'1-3-1.1','SUB',12,'1-3-1.1','TEST'),
    ROW('INV',12,'1-3-4.2','SUB',1,'1-3-4.2','TEST'),
    ROW('INV',12,'1-3-4.2','SUB',2,'1-3-4.2','TEST'),
    ROW('INV',12,'1-3-4.2','SUB',3,'1-3-4.2','TEST'),
    ROW('INV',12,'1-3-4.2','SUB',4,'1-3-4.2','TEST'),
    ROW('INV',12,'1-3-4.2','SUB',5,'1-3-4.2','TEST'),
    ROW('INV',12,'1-3-4.2','SUB',6,'1-3-4.2','TEST'),
    ROW('INV',13,'1-3-4.3','SUB',1,'1-3-4.3','TEST'),
    ROW('INV',13,'1-3-4.3','SUB',2,'1-3-4.3','TEST'),
    ROW('INV',13,'1-3-4.3','SUB',3,'1-3-4.3','TEST'),
    ROW('INV',13,'1-3-4.3','SUB',4,'1-3-4.3','TEST'),
    ROW('INV',13,'1-3-4.3','SUB',5,'1-3-4.3','TEST'),
    ROW('INV',13,'1-3-4.3','SUB',6,'1-3-4.3','TEST'),
    ROW('INV',5,'2-2-5.1','SUB',1,'2-2-5.1','TEST'),
    ROW('INV',5,'2-2-5.1','SUB',2,'2-2-5.1','TEST'),
    ROW('INV',5,'2-2-5.1','SUB',3,'2-2-5.1','TEST'),
    ROW('INV',17,'2-2-4.1','SUB',1,'2-2-4.1','TEST'),
    ROW('INV',17,'2-2-4.1','SUB',2,'2-2-4.1','TEST'),
    ROW('INV',20,'2-2-4.4','SUB',1,'2-2-4.4','TEST'),
    ROW('INV',20,'2-2-4.4','SUB',2,'2-2-4.4','TEST'),
    ROW('INV',20,'2-2-4.4','SUB',3,'2-2-4.4','TEST'),
    ROW('INV',1,'2-4-2.1','SUB',1,'2-4-2.1','TEST'),
    ROW('INV',1,'2-4-2.1','SUB',2,'2-4-2.1','TEST'),
    ROW('INV',1,'3-1-1.1','SUB',1,'3-1-1.1','TEST'),
    ROW('INV',1,'3-1-1.1','SUB',2,'3-1-1.1','TEST'),
    ROW('INV',1,'3-1-1.1','SUB',3,'3-1-1.1','TEST'),
    ROW('INV',2,'3-1-1.2','SUB',1,'3-1-1.2','TEST'),
    ROW('INV',2,'3-1-1.2','SUB',2,'3-1-1.2','TEST'),
    ROW('INV',2,'3-1-1.2','SUB',3,'3-1-1.2','TEST'),
    ROW('INV',3,'3-1-1.3','SUB',1,'3-1-1.3','TEST'),
    ROW('INV',3,'3-1-1.3','SUB',2,'3-1-1.3','TEST'),
    ROW('INV',3,'3-1-1.3','SUB',3,'3-1-1.3','TEST'),
    ROW('INV',1,'3-2-2.1','SUB',1,'3-2-2.1','TEST'),
    ROW('INV',1,'3-2-2.1','SUB',2,'3-2-2.1','TEST'),
    ROW('INV',1,'3-2-2.1','SUB',3,'3-2-2.1','TEST'),
    ROW('INV',2,'3-2-2.2','SUB',1,'3-2-2.2','TEST'),
    ROW('INV',2,'3-2-2.2','SUB',2,'3-2-2.2','TEST'),
    ROW('INV',6,'5-2-2.3','SUB',1,'5-2-2.3','TEST'),
    ROW('INV',6,'5-2-2.3','SUB',2,'5-2-2.3','TEST'),
    ROW('INV',9,'5-2-1.1','SUB',1,'5-2-1.1','TEST'),
    ROW('INV',9,'5-2-1.1','SUB',2,'5-2-1.1','TEST'),
    ROW('INV',9,'5-2-1.1','SUB',3,'5-2-1.1','TEST'),
    ROW('INV',9,'5-2-1.1','SUB',4,'5-2-1.1','TEST'),
    ROW('INV',4,'5-3-1','SUB',1,'5-3-1','TEST'),
    ROW('INV',4,'5-3-1','SUB',2,'5-3-1','TEST'),
    ROW('INV',7,'5-3-4','SUB',1,'5-3-4','TEST'),
    ROW('INV',7,'5-3-4','SUB',2,'5-3-4','TEST'),
    ROW('INV',7,'5-3-4','SUB',3,'5-3-4','TEST'),
    ROW('INV',7,'5-3-4','SUB',4,'5-3-4','TEST'),
    ROW('INV',1,'6-1-1.1','SUB',1,'6-1-1.1','TEST'),
    ROW('INV',1,'6-1-1.1','SUB',2,'6-1-1.1','TEST'),
    ROW('INV',1,'6-1-1.1','SUB',3,'6-1-1.1','TEST'),
    ROW('INV',1,'6-2-1.1','SUB',1,'6-2-1.1','TEST'),
    ROW('INV',1,'6-2-1.1','SUB',2,'6-2-1.1','TEST'),
    ROW('INV',3,'6-2-1.3','SUB',1,'6-2-1.3','TEST'),
    ROW('INV',3,'6-2-1.3','SUB',2,'6-2-1.3','TEST')
) AS tmp(padre_tipologia, padre_order, padre_livello, figlio_tipologia, figlio_order, figlio_livello, descrizione_ente)
JOIN ente ON ente.descrizione = tmp.descrizione_ente
JOIN elemento_gerarchia padre ON padre.ente_id = ente.id_ente AND padre.cod_tipologia_gerarchia = tmp.padre_tipologia AND padre.order = tmp.padre_order AND padre.cod_livello_elemento = tmp.padre_livello
JOIN elemento_gerarchia figlio ON figlio.ente_id = ente.id_ente AND figlio.cod_tipologia_gerarchia = tmp.figlio_tipologia AND figlio.order = tmp.figlio_order AND figlio.cod_livello_elemento = tmp.figlio_livello
WHERE NOT EXISTS (
    SELECT 1
    FROM struttura_gerarchia curr
    WHERE curr.elemento_gerarchia_id_padre = padre.id
    AND curr.elemento_gerarchia_id_figlio = figlio.id
);

INSERT INTO utente(username, cognome, nome, email, lingua, data_inizio_validita, data_creazione, username_creazione, data_aggiornamento, username_aggiornamento)
SELECT tmp.username, tmp.cognome, tmp.nome, tmp.email, tmp.lingua, now(), now(), 'ScriptSQL', now(), 'ScriptSQL'
FROM (VALUES
    ROW('AAAAAA00A00A000A', 'Test', 'Utente', 'utente.test@example.com', 'IT')
) AS tmp(username, cognome, nome, email, lingua)
WHERE NOT EXISTS (
    SELECT 1
    FROM utente curr
    WHERE curr.username = tmp.username
);

INSERT INTO direzione_utente_ruolo(id_applicazione, id_utente, id_direzione, id_ruolo, flag_direzione_default, data_inizio_validita, data_creazione, username_creazione, data_aggiornamento, username_aggiornamento)
SELECT applicazione.id_applicazione, utente.id_utente, direzione.id_direzione, ruolo.id_ruolo, tmp.flag_direzione_default, now(), now(), 'ScriptSQL', now(), 'ScriptSQL'
FROM (VALUES
    ROW('GMF', 'AAAAAA00A00A000A', 'Ente TEST', 'SUPER_ADMIN', 1, 'TEST')
) AS tmp(applicazione, utente, direzione, ruolo, flag_direzione_default, descrizione_ente)
JOIN ente ON ente.descrizione = tmp.descrizione_ente
JOIN applicazione ON applicazione.descrizione = tmp.applicazione
JOIN utente ON utente.username = tmp.utente
JOIN direzione ON direzione.id_ente = ente.id_ente AND direzione.descrizione = tmp.direzione
JOIN ruolo ON ruolo.codice = tmp.ruolo
WHERE NOT EXISTS (
    SELECT 1
    FROM direzione_utente_ruolo curr
    WHERE curr.id_applicazione = applicazione.id_applicazione
    AND curr.id_utente = utente.id_utente
    AND curr.id_direzione = direzione.id_direzione
    AND curr.id_ruolo = ruolo.id_ruolo
);

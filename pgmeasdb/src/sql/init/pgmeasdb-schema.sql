CREATE TABLE pgmeas.pgmeas_d_intervento_categoria (
    int_categoria_id SERIAL  NOT NULL,
    int_categoria_cod TEXT  NOT NULL,
    int_categoria_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_categoria_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_categoria_1 ON pgmeas.pgmeas_d_intervento_categoria (int_categoria_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_categoria IS 'Tabella Edisan: EDISAN_D_CATEGORIA  Valori:  99    	99 - ALTRO A1    	A1 - PREVENZIONE COLLETTIVA SERVIZI DI IGIENE PUBBLICA A2    	A2 - PREVENZIONE COLLETTIVA I.Z.S. B1    	B1 - SERVIZI TERRITORIALI DISTRETTI B2    	B2 - SERVIZI TERRITORIALI POLIAMBULATORI B3    	B3 - SERVIZI TERRITORIALI ALTRE STRUTTURE B4    	B4 - SERVIZI TERRITORIALI TECNOLOGIE C1    	C1 - RESIDENZE SANITARIE ASSISTENZIALI RSA PER ANZIANI C2    	C2 - RESIDENZE SANITARIE ASSISTENZIALI RSA PER DISABILI D1    	D1 - OSPEDALI OPERE D2    	D2 - OSPEDALI TECNOLOGIE E1    	E1 - SERVIZI GENERALI OSPEDALIERI MESSA A NORMA E2    	E2 - SERVIZI GENERALI OSPEDALIERI SISTEMA INFORMATIVO E3    	E3 - SERVIZI GENERALI OSPEDALIERI UMANIZZAZIONE E CONF. E4    	E4 - SERVIZI GENERALI OSPEDALIERI ALTRO F     	F - PROGETTO DI RILIEVO NAZIONALE';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_finalita"                        */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_finalita (
    int_finalita_id SERIAL  NOT NULL,
    int_finalita_cod TEXT  NOT NULL,
    int_finalita_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_finalita_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_finalita_1 ON pgmeas.pgmeas_d_intervento_finalita (int_finalita_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_finalita IS 'Tabella fonte: EDISAN_D_FINALITA_INTERVENTO  Valori:  00001	MIGLIORAMENTO ED INCREMENTO DEL SERVIZIO 00002	CONSERVAZIONE DEL PATRIMONIO 00003	ADEGUAMENTO NORMATIVO 00004	COMPLETAMENTO D''OPERA 00005	VALORIZZAZIONE BENI VINCOLATI 00006	QUALITA'' URBANA 00007	QUALITA'' AMBIENTALE 99999	ALTRO 00008	ADEGUAMENTO TECNOLOGICO 00009	RISPARMIO ENERGETICO';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_tipo"                            */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_tipo (
    int_tipo_id SERIAL  NOT NULL,
    int_tipo_cod TEXT  NOT NULL,
    int_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_tipo_1 ON pgmeas.pgmeas_d_intervento_tipo (int_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_tipo IS 'Tabella Edisan: EDISAN_D_TIPO_INTERVENTO  Valori:  00001	NUOVA COSTRUZIONE 00003	RECUPERO 00004	RISTRUTTURAZIONE 00005	RESTAURO 00006	MANUTENZIONE ORDINARIA 00007	MANUTENZIONE STRAORDINARIA 00008	COMPLETAMENTO 00009	AMPLIAMENTO 00010	ACQUISTO DI IMMOBILI 00002	DEMOLIZIONE 00011	ACQUISTO DI ATTREZZATURE 99999	ALTRO';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_tipo_det"                        */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_tipo_det (
    int_tipo_det_id SERIAL  NOT NULL,
    int_tipo_det_cod TEXT  NOT NULL,
    int_tipo_det_desc TEXT,
    int_tipo_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_tipo_det_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_tipo_det_1 ON pgmeas.pgmeas_d_intervento_tipo_det (int_tipo_det_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_tipo_det IS 'Tabella Edisan: EDISAN_D_CLASSE_ATTREZZ  Valori per int_tipo_desc=''ACQUISTO DI ATTREZZATURE'':  999	ALTRO ADG	SISTEMA PER ANGIOGRAFIA DIGITALE/EMODINAMICA AIC	ANALIZZATORE AUTOMATICO PER IMMUNO CHIMICA ALI	ACCELERATORE LINEARE AME	ANALIZZATORE MULTIPARAMETRICO SELETTIVO ANS	ANESTESIA, APPARECCHIO PER BRR	SISTEMA PER BRACHITERAPIA RADIANTE CEC	SISTEMA PER LA CIRCOLAZIONE EXTRACORPOREA CIL	CICLOTRONE CIP	CAMERA IPERBARICA ECT	ECOTOMOGRAFO EMD	EMODIALISI, APPARECCHIO PER GCC	GAMMA CAMERA COMPUTERIZZATA GCD	CONTAGLOBULI AUTOMATICO DIFFERENZIALE GRD	GRUPPO RADIOLOGICO GTT	SISTEMA TAC-GAMMA CAMERA INTEGRATO LIT	LITROTITORE EXTRACORPOREO MAD	MAMMOGRAFO DIGITALE (DR) MCH	SISTEMA LASER PER CORREZIONE VISTA MEL	MICROSCOPIO ELETTRONICO A TRASMISSIONE MON	MONITOR PRD	PORTATILE PER RADIOSCOPIA RDG	SISTEMA INTEGRATO PER RADIOCHIRURGIA STEREOTATTICA (GAMMA KNIFE) RDX	DIAGNOSTICA RADIOLOGICA DIGITALE (DR) RTP	ACCELERATORE LINEARE PER RADIOTERAPIA INTRAOPERATORIA SBS	SISTEMA ROBOTIZZATO PER CHIRURGIA ENDOSCOPICA SSP	SISTEMA TAC-PET INTEGRATO TAC	TOMOGRAFO ASSIALE COMPUTERIZZATO TAU	TERAPIA ONCOLOGICA AD ULTRASUONI (HIFU) TCZ	TC-SIMULATORE PER RADIOTERAPIA TEP	TOMOGRAFO AD EMISSIONE DI POSITRONI TER	SISTEMA PER TOMOTERAPIA TOD	SISTEMA PER TERAPIA AD ONDE D''URTO TOP	TAVOLO OPERATORIO TRM	TOMOGRAFO A RISONANZA MAGNETICA TTE	TAVOLO TELECOMANDATO PER APPARECCHIO RADIOLOGICO VPO	VENTILATORE POLMONARE';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_stato"                           */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_stato (
    int_stato_id SERIAL  NOT NULL,
    int_stato_cod TEXT  NOT NULL,
    int_stato_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_stato_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_stato_1 ON pgmeas.pgmeas_d_intervento_stato (int_stato_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_stato IS 'Tabella Edisan: EDISAN_D_QUALIFICA_INTERVENTO  Valori:  00002	PROPOSTO 00003	IN CORSO D''OPERA 00004	FINANZIATO 99999	ANNULLATO 00000	VALIDATO ARESS 88888	NON VALIDATO ARESS   Tabella EDISAN_STATO_INT  Valori:  A	AUTORIZZATO N	NON AUTORIZZATO P	PARZIALMENTE AUTORIZZATO';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_finanziamento_tipo"                         */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_finanziamento_tipo (
    fin_tipo_id SERIAL  NOT NULL,
    fin_tipo_cod TEXT  NOT NULL,
    fin_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (fin_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_finanziamento_tipo_1 ON pgmeas.pgmeas_d_finanziamento_tipo (fin_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_finanziamento_tipo IS 'Tabella Edisan: EDISAN_D_MODALITA_FINANZIAMENT  Valori:  00005	FONDI STATALI     S(tipofin) 00006	FONDI REGIONALI   R(tipofin) 99999	ALTRO';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_finanziamento_tipo_det"                     */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_finanziamento_tipo_det (
    fin_tipo_det_id SERIAL  NOT NULL,
    fin_tipo_det_cod TEXT  NOT NULL,
    fin_tipo_det_desc TEXT,
    fin_tipo_id INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    fin_tipo_det_percentuale_stato INTEGER,
    fin_tipo_det_percentuale_regione INTEGER,
    fin_tipo_det_percentuale_altro INTEGER,
    PRIMARY KEY (fin_tipo_det_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_finanziamento_tipo_det_1 ON pgmeas.pgmeas_d_finanziamento_tipo_det (fin_tipo_det_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_finanziamento_tipo_det IS 'Valori se fin_tipo_desc<>''ALTRO''--> fonte: EDISAN_D_CLASSIFICAZIONE_FIN    00001	ART.20 L.N.67/88 I FASE PROGRAMMA INVESTIMENTI		S 00002	ART.20 L.N.67/88 II FASE PROGRAMMA INVESTIMENTI		S 00003	ART.20 L.N.67/88 III FASE PROGRAMMA INVESTIMENTI		S 00004	ART.71 L.N.448/88 RIQUALIFICAZIONE CENTRI URBANI		S 00005	RADIOTERAPIA		S 00006	INTRA MOENIA		S 00007	HOSPICE		S 00008	L.135/90 AIDS		S 00009	L.R. N. 24/2000		R 00010	L.R. N. 25/2000		R 00011	L.R. N. 2/2003		R 00012	CONTO CAPITALE REGIONALE		R 00016	L.N. 285 del 09/10/2000		S 00013	L.R. 55 del 01/09/1997		R 00014	L.N. 450 del 1997		S 00015	L.R. 40 del 03/07/1996		R 00017	QUOTA 5% REGIONALE		R 00018	ACCORDO 2008 (art. 20 legge 67/88)		S   Valori se fin_tipo_desc=''ALTRO''--> fonte EDISAN_D_MODALITA_FINALTRO:  00002	DONAZIONE 00003	MUTUO 00004	FONDI DELL''AZIENDA 00007	SPONSORIZZAZIONI 00008	SOCIETA'' PARTECIPATE O DI SCOPO 00009	FINANZA DI PROGETTO 00010	CONCESSIONE DI COSTRUZIONE E GESTIONE 00011	CAPITALE PRIVATO 00012	ALIENAZIONE 00999	ALTRI FINANZIAMENTI 00012	ALIENAZIONE 00999	ALTRI FINANZIAMENTI';

COMMENT ON COLUMN pgmeas.pgmeas_d_finanziamento_tipo_det.fin_tipo_det_percentuale_stato IS 'se = 0 --> non utilizzabile, se NULL = % variabile';
COMMENT ON COLUMN pgmeas.pgmeas_d_finanziamento_tipo_det.fin_tipo_det_percentuale_regione IS 'se = 0 --> non utilizzabile, se NULL = % variabile';
COMMENT ON COLUMN pgmeas.pgmeas_d_finanziamento_tipo_det.fin_tipo_det_percentuale_altro IS 'se = 0 --> non utilizzabile, se NULL = % variabile';


/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_organo"                                     */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_organo (
    organo_id SERIAL  NOT NULL,
    organo_cod TEXT  NOT NULL,
    organo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (organo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_organo_1 ON pgmeas.pgmeas_d_organo (organo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_organo IS 'Valori:  C Consiglio G Giunta R Regione';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_provvedimento_livello"                      */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_provvedimento_livello (
    prov_liv_id SERIAL  NOT NULL,
    prov_liv_cod TEXT  NOT NULL,
    prov_liv_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (prov_liv_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_provvedimento_livello_1 ON pgmeas.pgmeas_d_provvedimento_livello (prov_liv_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_provvedimento_livello IS 'Label interfaccia edisan: Provvedimento tipologia  Tabella Edisan: EDISAN_D_LIVELLI  Valori:  0	ASSEGNAZIONE FINANZIAMENTO STATALE / REGIONALE 1	ACCORDO DI PROGRAMMA 2	PROVVEDIMENTO DI ASSEGNAZIONE REGIONALE 3	PROVVEDIMENTO OPERATIVO';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_provvedimento_tipo"                         */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_provvedimento_tipo (
    prov_tipo_id SERIAL  NOT NULL,
    prov_tipo_cod TEXT  NOT NULL,
    prov_tipo_desc TEXT,
    organo_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (prov_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_provvedimento_tipo_1 ON pgmeas.pgmeas_d_provvedimento_tipo (prov_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_provvedimento_tipo IS 'Tabella Edisan: EDISAN_D_PROVVEDIMENTI  Valori:  ACCORDO DI PROGRAMMA	C	AP ATTO DI LIQUIDAZIONE	R	AL ATTO DIRIGENZIALE	C	AD COMUNICAZIONE CEE	C	CE DECISIONE CEE	C	EE DECRETO ASSESSORILE	G	DA DECRETO DEL CAPO PROVVISORIO DELLO STATO	G	CP DECRETO DEL PRESIDENTE DEL CONSIGLIO DEI MINISTRI	G	CM DECRETO DEL PRESIDENTE DELLA REPUBBLICA	G	DT DECRETO LEGGE	G	DR DECRETO LEGGE LUOGOTENENZIALE	G	LL DECRETO LEGISLATIVO	G	LG DECRETO LEGISLATIVO DEL CAPO PROVVISORIO DELLO STATO	G	PP DECRETO LEGISLATIVO LUOGOTENEZIALE	G	GT DECRETO LEGISLATIVO PRESIDENZIALE	G	GP DECRETO LUOGOTENENZIALE	G	LT DECRETO MINISTERIALE	C	DM DECRETO PRESIDENTE GIUNTA	G	DP DELIBERA UFFICIO PRESIDENZA CONSIGLIO REGIONALE	G	DU DELIBERAZIONE C.I.P.E	G	PE DELIBERAZIONE CONSIGLIO REGIONALE	C	DC DELIBERAZIONE GIUNTA REGIONALE	G	DG DISEGNO DI LEGGE	G	DL DISEGNO LEGGE FINANZIARIA	G	DF LEGGE	G	LE LEGGE COSTITUZIONALE	G	LC LEGGE DI APPROVAZIONE BILANCIO	C	LA LEGGE DI BILANCIO	G	LB LEGGE DI VARIAZIONE DI BILANCIO	G	LV LEGGE FINANZIARIA	G	LF LEGGE PROVINCIALE	G	LP LEGGE REGIONALE	G	LR ORDINANZA	G	OR REGIO DECRETO	G	RD REGIO DECRETO LEGGE	G	RL REGIO DECRETO LEGISLATIVO	G	RG REGOLAMENTO CEE	G	RE TRATTATO	G	TR';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_obiettivo"                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_obiettivo (
    int_obiettivo_id SERIAL  NOT NULL,
    int_obiettivo_cod TEXT  NOT NULL,
    int_obiettivo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_obiettivo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_obiettivo_1 ON pgmeas.pgmeas_d_intervento_obiettivo (int_obiettivo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_obiettivo IS 'Valori da excel:  CONSERVAZIONE SICUREZZA ANTINCENDIO SICUREZZA ANTISISMICA EFFICIENTAMENTO ENERGETICO ADEGUAMENTO RIORDINO POTENZIAMENTO';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_stato_progettuale"               */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_stato_progettuale (
    int_stato_prog_id SERIAL  NOT NULL,
    int_stato_prog_cod TEXT  NOT NULL,
    int_stato_prog_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_stato_prog_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_stato_progettuale_1 ON pgmeas.pgmeas_d_intervento_stato_progettuale (int_stato_prog_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_stato_progettuale IS 'Valori da excel:  studio fattibilità progetto fattibilità tecnico economico progetto esecutivo  più   Tabella Edisan: EDISAN_D_STATO_PROGETTO  Valori:  C	CAPITOLATO PRESTAZIONALE FORNITURA D	PROGETTO DEFINITIVO E	PROGETTO ESECUTIVO P	PROGETTO PRELIMINARE S	SCHEDA PREFATTIBILITA''';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_contratto_tipo"                  */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_contratto_tipo (
    int_contratto_tipo_id SERIAL  NOT NULL,
    int_contratto_tipo_cod TEXT  NOT NULL,
    int_contratto_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_contratto_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_contratto_tipo_1 ON pgmeas.pgmeas_d_intervento_contratto_tipo (int_contratto_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_contratto_tipo IS 'Valori da excel:  lavoro fornitura';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_forma_realizzativa"              */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_forma_realizzativa (
    int_forma_realizzativa_id SERIAL  NOT NULL,
    int_forma_realizzativa_cod TEXT  NOT NULL,
    int_forma_realizzativa_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_forma_realizzativa_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_forma_realizzativa_1 ON pgmeas.pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_intervento_forma_realizzativa IS 'Valori da excel:  Partenariato Pubblco Privato Contratto di prestazione energetica Concessione di lavori Appalto integrato Appalto Donazione';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_fase"                                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_fase (
    fase_id SERIAL  NOT NULL,
    fase_cod TEXT  NOT NULL,
    fase_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (fase_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_fase_1 ON pgmeas.pgmeas_d_fase (fase_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_fase IS 'Valori:  programmazione gestione monitoraggio';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_classif_elem"                               */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_classif_elem (
    classif_id SERIAL  NOT NULL,
    classif_cod TEXT  NOT NULL,
    classif_desc TEXT  NOT NULL,
    classif_desc_estesa TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (classif_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_classif_elem_1 ON pgmeas.pgmeas_d_classif_elem (classif_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_classif_elem IS 'elementi del quadro economico';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_piano_triennale"                            */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_piano_triennale (
    piano_id SERIAL  NOT NULL,
    piano_cod TEXT  NOT NULL,
    paino_desc TEXT,
    anno_da INTEGER  NOT NULL,
    anno_a INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (piano_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_piano_triennale_1 ON pgmeas.pgmeas_d_piano_triennale (piano_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_piano_triennale IS 'es.  piano_cod= ''PIANO 2024-2026'' anno_da=2024 anno_a=2026';


/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_quadrante"                                  */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_quadrante (
    quadrante_id SERIAL  NOT NULL,
    quadrante_cod TEXT  NOT NULL,
    quadrante_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (quadrante_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_quadrante_1 ON pgmeas.pgmeas_d_quadrante (quadrante_cod) where data_cancellazione is null;

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_allegato_tipo"                              */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_allegato_tipo (
    allegato_tipo_id SERIAL  NOT NULL,
    allegato_tipo_cod TEXT  NOT NULL,
    allegato_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (allegato_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_allegato_tipo_1 ON pgmeas.pgmeas_d_allegato_tipo (allegato_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_allegato_tipo IS 'Tipo di allegato:  Provvedimento aziendale di approvazione del progetto0 Relazione tecnica  modulo a modulo a-p ...';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.allegato_tipo_cod IS 'codice';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.allegato_tipo_desc IS 'descrizione';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.validita_inizio IS 'inizio validità record';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.validita_fine IS 'fine validità record';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.data_creazione IS 'data creazione record';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.data_modifica IS 'data modifica record';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.data_cancellazione IS 'data cancellazione record';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.utente_creazione IS 'utente creazione';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.utente_modifica IS 'utente modifica';

COMMENT ON COLUMN pgmeas.pgmeas_d_allegato_tipo.utente_cancellazione IS 'utente cancellazione';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_c_parametro_tipo"                             */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_c_parametro_tipo (
    parametro_tipo_id SERIAL  NOT NULL,
    parametro_tipo_cod TEXT  NOT NULL,
    parametro_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (parametro_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_c_parametro_tipo_1 ON pgmeas.pgmeas_c_parametro_tipo (parametro_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_c_parametro_tipo IS 'tipo di parametri di configurazione Es.  ERRORE MESSAGGIO_PUSH MESSAGGIO_EMAIL MESSAGGIO_SMS PARAMETRO';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.parametro_tipo_cod IS 'codice';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.parametro_tipo_desc IS 'descrizione';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.validita_inizio IS 'inizio validità record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.validita_fine IS 'fine validità record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.data_creazione IS 'data creazione record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.data_modifica IS 'data modifica record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.data_cancellazione IS 'data cancellazione record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.utente_creazione IS 'utente creazione';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.utente_modifica IS 'utente modifica';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro_tipo.utente_cancellazione IS 'utente cancellazione';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.csi_log_audit"                                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.csi_log_audit (
    audit_id SERIAL  NOT NULL,
    data_ora TIMESTAMP  NOT NULL,
    id_app TEXT  NOT NULL,
    ip_address TEXT  NOT NULL,
    utente TEXT  NOT NULL,
    operazione TEXT  NOT NULL,
    ogg_oper TEXT,
    key_oper TEXT,
    uuid TEXT,
    request_payload TEXT,
    response_payload TEXT,
    esito_chiamata INTEGER,
    PRIMARY KEY (audit_id)
);



COMMENT ON TABLE pgmeas.csi_log_audit IS 'lod di audit';

COMMENT ON COLUMN pgmeas.csi_log_audit.data_ora IS 'Data e ora dell''evento';

COMMENT ON COLUMN pgmeas.csi_log_audit.id_app IS 'Codice identificativo dell''''applicazione utilizzata dall''utente; da comporre con i valori presenti in Anagrafica Prodotti: <codice prodotto>_<codice linea cliente>_<codice ambiente>_<codice Unità di Installazione>';

COMMENT ON COLUMN pgmeas.csi_log_audit.ip_address IS 'Ip del client utente (se possibile)';

COMMENT ON COLUMN pgmeas.csi_log_audit.utente IS 'Identificativo univoco dell''utente che ha effettuato l''''operazione (es. login / codice fiscale / matricola / ecc.)';

COMMENT ON COLUMN pgmeas.csi_log_audit.operazione IS 'Nome API (uri o summary) e verbo';

COMMENT ON COLUMN pgmeas.csi_log_audit.ogg_oper IS 'Query param quando non sono da cifrare oppure se da cifrare solo nome campo senza il valore';

COMMENT ON COLUMN pgmeas.csi_log_audit.key_oper IS 'Nome operazione';

COMMENT ON COLUMN pgmeas.csi_log_audit.uuid IS 'X REQUEST ID';

COMMENT ON COLUMN pgmeas.csi_log_audit.request_payload IS 'Request payload cifrato. Non deve essere scritto quando si caricano o scaricano gli allegati ';

COMMENT ON COLUMN pgmeas.csi_log_audit.response_payload IS 'Response payload cifrato. Non deve essere scritto quando si caricano o scaricano gli allegati';

COMMENT ON COLUMN pgmeas.csi_log_audit.esito_chiamata IS 'http status';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.version_control"                                     */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.version_control (
    change_number SERIAL  NOT NULL,
    release_version TEXT  NOT NULL,
    description TEXT  NOT NULL,
    release_type TEXT  NOT NULL,
    script_name TEXT  NOT NULL,
    checksum UUID,
    installed_by TEXT  NOT NULL,
    installed_on TIMESTAMP DEFAULT now()  NOT NULL,
    execution_time INTEGER  NOT NULL,
    success BOOLEAN  NOT NULL,
    PRIMARY KEY (change_number)
);



COMMENT ON TABLE pgmeas.version_control IS 'versionamento database';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_ente_tipo"                                  */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_ente_tipo (
    ente_tipo_id SERIAL  NOT NULL,
    ente_tipo_cod TEXT  NOT NULL,
    ente_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (ente_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_ente_tipo_1 ON pgmeas.pgmeas_d_ente_tipo (ente_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_ente_tipo IS 'Valori:  ASL	Azienda Sanitaria Locale AO	Azienda Ospedaliera AOU	Azienda Ospedaliera Universitaria';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_finanziamento_importo_tipo"                 */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_finanziamento_importo_tipo (
    fin_imp_tipo_id SERIAL  NOT NULL,
    fin_imp_tipo_cod TEXT  NOT NULL,
    fin_imp_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (fin_imp_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_finanziamento_importo_tipo_1 ON pgmeas.pgmeas_d_finanziamento_importo_tipo (fin_imp_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_finanziamento_importo_tipo IS 'Valori:  importo a carico dello stato importo a carico della regione importo a carico dell''azienda   forse occorre una tabella di relazione tra d_finanziamento_importo_tipo e d_finanziamento_tipo_det che dica quali tipi di importi possono essere usati su quali tipi di dinanziamento det';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_struttura_tipo"                             */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_struttura_tipo (
    struttura_tipo_id SERIAL  NOT NULL,
    struttura_tipo_cod TEXT  NOT NULL,
    struttura_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (struttura_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_struttura_tipo_1 ON pgmeas.pgmeas_d_struttura_tipo (struttura_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_struttura_tipo IS 'Tipi di struttura (ARPE)';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.struttura_tipo_cod IS 'codice';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.struttura_tipo_desc IS 'descrizione';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.validita_inizio IS 'inizio validità record';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.validita_fine IS 'fine validità record';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.data_creazione IS 'data creazione record';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.data_modifica IS 'data modifica record';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.data_cancellazione IS 'data cancellazione record';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.utente_creazione IS 'utente creazione';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.utente_modifica IS 'utente modifica';

COMMENT ON COLUMN pgmeas.pgmeas_d_struttura_tipo.utente_cancellazione IS 'utente cancellazione';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_intervento_appalto_tipo"                    */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_intervento_appalto_tipo (
    int_appalto_tipo_id SERIAL  NOT NULL,
    int_appalto_tipo_cod TEXT  NOT NULL,
    int_appalto_tipo_desc TEXT,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (int_appalto_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_intervento_appalto_tipo_1 ON pgmeas.pgmeas_d_intervento_appalto_tipo (int_appalto_tipo_cod) where data_cancellazione is null;

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_classif_ts_tipo"                            */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_classif_ts_tipo (
    classif_ts_tipo_id SERIAL  NOT NULL,
    classif_ts_tipo_cod TEXT  NOT NULL,
    classif_ts_tipo_desc TEXT  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (classif_ts_tipo_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_classif_ts_tipo_1 ON pgmeas.pgmeas_d_classif_ts_tipo (classif_ts_tipo_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_classif_ts_tipo IS 'indica la tipologia di testata di un albero di voci:   Quadro economico Intervento edilizio';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_ente"                                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_ente (
    ente_id SERIAL  NOT NULL,
    ente_cod_esteso TEXT  NOT NULL,
    ente_regione_cod TEXT,
    ente_regione_desc TEXT,
    ente_cod TEXT,
    ente_desc TEXT,
    ente_indirizzo TEXT,
    ente_cap TEXT,
    ente_comune TEXT,
    ente_provincia_sigla TEXT,
    ente_telefono TEXT,
    ente_fax TEXT,
    ente_email TEXT,
    ente_sito_web TEXT,
    ente_partita_iva TEXT,
    ente_tipo_id INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (ente_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_ente_1 ON pgmeas.pgmeas_d_ente (ente_cod_esteso) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_ente IS 'Ente proprietario del dato. Le tabelle R e T sono partizionate per ente_id';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_provvedimento"                              */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_provvedimento (
    prov_id SERIAL  NOT NULL,
    prov_id_padre INTEGER,
    prov_titolo TEXT  NOT NULL,
    prov_data TIMESTAMP  NOT NULL,
    prov_importo NUMERIC  NOT NULL,
    prov_ente_provenienza TEXT,
    prov_oggetto TEXT  NOT NULL,
    prov_numero NUMERIC,
    prov_numero2 NUMERIC,
    prov_note TEXT,
    prov_liv_id INTEGER  NOT NULL,
    fin_tipo_det_id INTEGER  NOT NULL,
    prov_tipo_id INTEGER  NOT NULL,
    prov_id_sostituito INTEGER,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (prov_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_provvedimento_1 ON pgmeas.pgmeas_t_provvedimento (prov_liv_id);

CREATE INDEX IDX_pgmeas_t_provvedimento_2 ON pgmeas.pgmeas_t_provvedimento (prov_tipo_id);

CREATE INDEX IDX_pgmeas_t_provvedimento_3 ON pgmeas.pgmeas_t_provvedimento (prov_tipo_id);

CREATE INDEX IDX_pgmeas_t_provvedimento_4 ON pgmeas.pgmeas_t_provvedimento (prov_id_sostituito);

CREATE INDEX IDX_pgmeas_t_provvedimento_5 ON pgmeas.pgmeas_t_provvedimento (prov_id_padre);

CREATE INDEX IDX_pgmeas_t_provvedimento_6 ON pgmeas.pgmeas_t_provvedimento (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_provvedimento IS 'Provvedimento di assegnazione  la somma degli importi dei Provvedimenti Figli non potrà superare l’importo del relativo Provvedimento Padre 1 intervento N finanziamenti 1 provvedimento  N finanziamenti    Lo stato non è gestito perchè: è attivo quando non ha data fine valorizzata nel passato. e chiuso quando ha data fine valorizzata nel passato.  un provvedimento può sostituirne un altro in base al campo prov_id_sostituito';

COMMENT ON COLUMN pgmeas.pgmeas_t_provvedimento.prov_titolo IS 'titolo';

COMMENT ON COLUMN pgmeas.pgmeas_t_provvedimento.prov_oggetto IS 'titolo';

COMMENT ON COLUMN pgmeas.pgmeas_t_provvedimento.prov_id_sostituito IS 'id del provvedimento che l''attuale ha sostituito';

COMMENT ON COLUMN pgmeas.pgmeas_t_provvedimento.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_classif_ts"                                 */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_classif_ts (
    classif_ts_id SERIAL  NOT NULL,
    classif_ts_cod TEXT  NOT NULL,
    classif_ts_desc TEXT  NOT NULL,
    classif_ts_tipo_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (classif_ts_id)
);



CREATE UNIQUE INDEX IDX_pgmeas_d_classif_ts_1 ON pgmeas.pgmeas_d_classif_ts (classif_ts_cod) where data_cancellazione is null;

COMMENT ON TABLE pgmeas.pgmeas_d_classif_ts IS 'indica la testata di un albero di voci del quadro economico,può variare nel tempo';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_d_classif_tree"                               */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_d_classif_tree (
    classif_tree_id SERIAL  NOT NULL,
    classif_ts_id INTEGER,
    classif_id INTEGER  NOT NULL,
    classif_id_padre INTEGER,
    classif_tree_livello INTEGER,
    classif_tree_ordine INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (classif_tree_id)
);



CREATE INDEX IDX_pgmeas_d_classif_tree_1 ON pgmeas.pgmeas_d_classif_tree (classif_ts_id);

CREATE INDEX IDX_pgmeas_d_classif_tree_2 ON pgmeas.pgmeas_d_classif_tree (classif_id);

CREATE INDEX IDX_pgmeas_d_classif_tree_3 ON pgmeas.pgmeas_d_classif_tree (classif_id_padre);

COMMENT ON TABLE pgmeas.pgmeas_d_classif_tree IS 'Albero con voci del quadro economico';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_c_parametro"                                  */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_c_parametro (
    parametro_id SERIAL  NOT NULL,
    parametro_cod TEXT  NOT NULL,
    parametro_desc TEXT,
    parametro_valore TEXT,
    parametro_tipo_id INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    PRIMARY KEY (parametro_id)
);



COMMENT ON TABLE pgmeas.pgmeas_c_parametro IS 'parametri di configurazione';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.parametro_cod IS 'codice';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.parametro_desc IS 'descrizione';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.validita_inizio IS 'inizio validità record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.validita_fine IS 'fine validità record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.data_creazione IS 'data creazione record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.data_modifica IS 'data modifica record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.data_cancellazione IS 'data cancellazione record';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.utente_creazione IS 'utente creazione';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.utente_modifica IS 'utente modifica';

COMMENT ON COLUMN pgmeas.pgmeas_c_parametro.utente_cancellazione IS 'utente cancellazione';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_finanziamento_importo_tipo_det"             */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_finanziamento_importo_tipo_det (
    r_imp_tipo_det_id SERIAL  NOT NULL,
    fin_tipo_det_id INTEGER  NOT NULL,
    fin_imp_tipo_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_imp_tipo_det_id, ente_id)
)  PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_finanziamento_importo_tipo_det_1 ON pgmeas.pgmeas_r_finanziamento_importo_tipo_det (fin_tipo_det_id);

CREATE INDEX IDX_pgmeas_r_finanziamento_importo_tipo_det_2 ON pgmeas.pgmeas_r_finanziamento_importo_tipo_det (fin_imp_tipo_id);

CREATE INDEX IDX_pgmeas_r_finanziamento_importo_tipo_det_3 ON pgmeas.pgmeas_r_finanziamento_importo_tipo_det (ente_id);

COMMENT ON COLUMN pgmeas.pgmeas_r_finanziamento_importo_tipo_det.validita_inizio IS 'now()';

COMMENT ON COLUMN pgmeas.pgmeas_r_finanziamento_importo_tipo_det.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intstr_interventoedilizio_ts"               */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_ts (
    intstr_ie_ts_id SERIAL  NOT NULL,
    intstr_id INTEGER,
    classif_ts_id INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (intstr_ie_ts_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intstr_interventoedilizio_ts_1 ON pgmeas.pgmeas_t_intstr_interventoedilizio_ts (intstr_id);

CREATE INDEX IDX_pgmeas_t_intstr_interventoedilizio_ts_2 ON pgmeas.pgmeas_t_intstr_interventoedilizio_ts (ente_id);

CREATE INDEX IDX_pgmeas_t_intstr_interventoedilizio_ts_3 ON pgmeas.pgmeas_t_intstr_interventoedilizio_ts (classif_ts_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_ts IS 'testata del modello quadro economico utilizzato dall''intervento';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_interventoedilizio_ts.classif_ts_id IS 'quale testata(modello) è utilizzata';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_interventoedilizio_ts.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intstr_interventoedilizio_tree"             */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_tree (
    intstr_ie_tree_id SERIAL  NOT NULL,
    intstr_ie_ts_id INTEGER  NOT NULL,
    classif_tree_id INTEGER  NOT NULL,
    intstr_ie_tree_visibile BOOLEAN DEFAULT true  NOT NULL,
    intstr_ie_tree_importo NUMERIC,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (intstr_ie_tree_id, ente_id)
)  PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intstr_interventoedilizio_tree_1 ON pgmeas.pgmeas_t_intstr_interventoedilizio_tree (intstr_ie_ts_id);

CREATE INDEX IDX_pgmeas_t_intstr_interventoedilizio_tree_2 ON pgmeas.pgmeas_t_intstr_interventoedilizio_tree (classif_tree_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_tree IS 'voce del quadro economico associata all''intervento e con importo';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_interventoedilizio_tree.intstr_ie_tree_visibile IS 'indica se la voce è utilizzata';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_interventoedilizio_tree.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intervento"                                 */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intervento (
    int_id SERIAL  NOT NULL,
    int_cod TEXT  NOT NULL,
    int_titolo TEXT  NOT NULL,
    int_anno TEXT  NOT NULL,
    int_cup TEXT,
    int_importo NUMERIC,
    int_codic_nsis TEXT,
    int_dg_aziendale_approvazione TEXT,
    int_dg_aziendale_approvazione_data TIMESTAMP,
    int_dg_regionale_approvazione TEXT,
    int_dg_regionale_approvazione_data TIMESTAMP,
    int_direttore_generale_cognome TEXT,
    int_direttore_generale_nome TEXT,
    int_commissario_cognome TEXT,
    int_commissario_nome TEXT,
    int_referente_pratica_cognome TEXT,
    int_referente_pratica_nome TEXT,
    int_referente_pratica_telefono TEXT,
    int_referente_pratica_email TEXT,
    int_forma_realizzativa_id INTEGER,
    int_str_tipo_id INTEGER,
    quadrante_id INTEGER,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (int_id, ente_id)
)  PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intervento_1 ON pgmeas.pgmeas_t_intervento (int_forma_realizzativa_id);

CREATE INDEX IDX_pgmeas_t_intervento_2 ON pgmeas.pgmeas_t_intervento (int_str_tipo_id);

CREATE INDEX IDX_pgmeas_t_intervento_3 ON pgmeas.pgmeas_t_intervento (quadrante_id);

CREATE INDEX IDX_pgmeas_t_intervento_4 ON pgmeas.pgmeas_t_intervento (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intervento IS 'Interventi';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento.int_cod IS 'codice intervento= codice ente+anno (ANNO_RIF)+pad6(progressivo(NUMERO_PROGRESSIVO))';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento.int_titolo IS 'titolo intervento';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento.int_anno IS 'anno intervento';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_struttura"                                  */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_struttura (
    str_id SERIAL  NOT NULL,
    str_cod TEXT  NOT NULL,
    str_denominazione TEXT  NOT NULL,
    str_hsp11_cod TEXT,
    str_fim_cod TEXT,
    str_bis_cod TEXT,
    str_indirizzo TEXT,
    str_coordinata_x NUMERIC,
    str_coordinata_y NUMERIC,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    str_id_padre INTEGER,
    struttura_tipo_id INTEGER,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (str_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_struttura_1 ON pgmeas.pgmeas_t_struttura (ente_id);

COMMENT ON COLUMN pgmeas.pgmeas_t_struttura.str_bis_cod IS 'solo ospedali';

COMMENT ON COLUMN pgmeas.pgmeas_t_struttura.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_finanziamento"                              */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_finanziamento (
    fin_id SERIAL  NOT NULL,
    fin_cod TEXT,
    fin_importo NUMERIC,
    fin_note TEXT,
    fin_tipo_det_id INTEGER  NOT NULL,
    fin_principale BOOLEAN DEFAULT false  NOT NULL,
    prov_id INTEGER,
    int_id INTEGER  NOT NULL,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (fin_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_finanziamento_1 ON pgmeas.pgmeas_t_finanziamento (ente_id);

CREATE INDEX IDX_pgmeas_t_finanziamento_2 ON pgmeas.pgmeas_t_finanziamento (int_id);

CREATE INDEX IDX_pgmeas_t_finanziamento_3 ON pgmeas.pgmeas_t_finanziamento (prov_id);

CREATE INDEX IDX_pgmeas_t_finanziamento_4 ON pgmeas.pgmeas_t_finanziamento (fin_tipo_det_id);

COMMENT ON TABLE pgmeas.pgmeas_t_finanziamento IS 'Funzionalità di assegnazione di finanziamento solo utente RP Un intervento può avere N finanziamenti  Lo stato non è gestito perchè: è attivo quando non ha data fine valorizzata nel passato. è chiuso quando ha data fine valorizzata nel passato.';

COMMENT ON COLUMN pgmeas.pgmeas_t_finanziamento.fin_importo IS 'importo totale';

COMMENT ON COLUMN pgmeas.pgmeas_t_finanziamento.fin_principale IS 'indica se il finanziamento è il principale per un intervento';

COMMENT ON COLUMN pgmeas.pgmeas_t_finanziamento.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intervento_previsione_spesa"                */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intervento_previsione_spesa (
    int_prev_spesa_id SERIAL  NOT NULL,
    int_prev_spesa_anno TEXT,
    int_prev_spesa_importo NUMERIC,
    int_id INTEGER  NOT NULL,
    piano_id INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (int_prev_spesa_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intervento_previsione_spesa_1 ON pgmeas.pgmeas_t_intervento_previsione_spesa (int_id);

CREATE INDEX IDX_pgmeas_t_intervento_previsione_spesa_2 ON pgmeas.pgmeas_t_intervento_previsione_spesa (piano_id);

CREATE INDEX IDX_pgmeas_t_intervento_previsione_spesa_3 ON pgmeas.pgmeas_t_intervento_previsione_spesa (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intervento_previsione_spesa IS 'previsione spesa dell''intervento';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_previsione_spesa.int_prev_spesa_anno IS 'anno erogazione';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_previsione_spesa.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intervento_struttura"                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intervento_struttura (
    intstr_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    str_id INTEGER  NOT NULL,
    intstr_importo NUMERIC,
    intstr_responsabile_cognome TEXT,
    intstr_responsabile_nome TEXT,
    intstr_responsabile_procedimento_cognome TEXT,
    intstr_responsabile_procedimento_nome TEXT,
    intstr_parere_vincolante_ppp BOOLEAN,
    intstr_parere_vincolante_ppp_n_protocollo TEXT,
    intstr_parere_cabina_di_regia BOOLEAN,
    intstr_parere_cabina_di_regia_n_protocollo TEXT,
    intstr_apertura_cantiere_data_prevista DATE,
    intstr_apertura_cantiere_data_effettiva DATE,
    intstr_priorita_anno TEXT,
    intstr_priorita INTEGER  NOT NULL,
    intstr_sottopriorita TEXT  NOT NULL,
    intstr_collaudo_data_prevista DATE,
    intstr_collaudo_data_effettiva DATE,
    intstr_pfte_previsione_durata_gg INTEGER,
    intstr_gara_previsione_durata_gg INTEGER,
    intstr_collaudo_previsione_durata_gg INTEGER,
    intstr_progettoesecutivo_previsione_durata_gg INTEGER,
    intstr_lavori_previsione_durata_gg INTEGER,
    intstr_attivazione_previsione_durata_gg INTEGER,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (intstr_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intervento_struttura_1 ON pgmeas.pgmeas_t_intervento_struttura (str_id);

CREATE INDEX IDX_pgmeas_t_intervento_struttura_2 ON pgmeas.pgmeas_t_intervento_struttura (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intervento_struttura IS 'Associazione intervento/struttura.';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_priorita_anno IS 'le priorità seguenti sono per anno';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_priorita IS '1,2,3...';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_sottopriorita IS 'a,b,c';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_pfte_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_gara_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_collaudo_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_progettoesecutivo_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_lavori_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.intstr_attivazione_previsione_durata_gg IS 'in giorni - è la previsione di attivazione in N giorni dopo l''ultimazione dei lavori';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_struttura.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intervento_gara_appalto"                    */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intervento_gara_appalto (
    gara_appalto_id SERIAL  NOT NULL,
    gara_appalto_cig_cod TEXT  NOT NULL,
    gara_appalto_data TIMESTAMP,
    intstr_id INTEGER  NOT NULL,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (gara_appalto_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intervento_gara_appalto_1 ON pgmeas.pgmeas_t_intervento_gara_appalto (intstr_id);

CREATE INDEX IDX_pgmeas_t_intervento_gara_appalto_2 ON pgmeas.pgmeas_t_intervento_gara_appalto (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intervento_gara_appalto IS 'Codici gara associati a un intervento';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_gara_appalto.gara_appalto_cig_cod IS 'codice gara d''appalto';

COMMENT ON COLUMN pgmeas.pgmeas_t_intervento_gara_appalto.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_allegato"                                   */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_allegato (
    allegato_id SERIAL  NOT NULL,
    allegato_oggetto TEXT,
    allegato_protocollo_numero TEXT,
    allegato_protocollo_data TIMESTAMP,
    file_name_user TEXT  NOT NULL,
    file_name_system TEXT  NOT NULL,
    file_type TEXT,
    file_path TEXT  NOT NULL,
    allegato_tipo_id INTEGER,
    int_id INTEGER,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (allegato_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_allegato_1 ON pgmeas.pgmeas_t_allegato (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_allegato IS 'Files allegati';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.allegato_protocollo_numero IS 'numero protocollo';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.data_creazione IS 'data creazione record';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.data_modifica IS 'data modifica record';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.data_cancellazione IS 'data cancellazione record';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.utente_creazione IS 'utente creazione';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.utente_modifica IS 'utente modifica';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.utente_cancellazione IS 'utente cancellazione';

COMMENT ON COLUMN pgmeas.pgmeas_t_allegato.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta"       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta (
    liq_ric_id SERIAL  NOT NULL,
    liq_ric_numero INTEGER,
    liq_ric_protocollo TEXT,
    liq_ric_portocollo_data TIMESTAMP,
    liq_ric_importo NUMERIC,
    fin_id INTEGER  NOT NULL,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (liq_ric_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_finanziamento_liquidazione_richiesta_1 ON pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta (fin_id);

CREATE INDEX IDX_pgmeas_t_finanziamento_liquidazione_richiesta_2 ON pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta IS 'Richieste di liquidazione';

COMMENT ON COLUMN pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_finanziamento_liquidazione"                 */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_finanziamento_liquidazione (
    liq_id SERIAL  NOT NULL,
    liq_numero INTEGER,
    liq_data_da TIMESTAMP,
    liq_data_a TIMESTAMP,
    liq_importo NUMERIC,
    fin_id INTEGER  NOT NULL,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (liq_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_finanziamento_liquidazione_1 ON pgmeas.pgmeas_t_finanziamento_liquidazione (fin_id);

CREATE INDEX IDX_pgmeas_t_finanziamento_liquidazione_2 ON pgmeas.pgmeas_t_finanziamento_liquidazione (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_t_finanziamento_liquidazione IS 'Liquidazioni';

COMMENT ON COLUMN pgmeas.pgmeas_t_finanziamento_liquidazione.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_liquidazione"                               */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_liquidazione (
    r_liq_ric_id SERIAL  NOT NULL,
    liq_ric_id INTEGER  NOT NULL,
    liq_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_liq_ric_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_liquidazione_1 ON pgmeas.pgmeas_r_liquidazione (liq_ric_id);

CREATE INDEX IDX_pgmeas_r_liquidazione_2 ON pgmeas.pgmeas_r_liquidazione (liq_id);

CREATE INDEX IDX_pgmeas_r_liquidazione_3 ON pgmeas.pgmeas_r_liquidazione (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_liquidazione IS 'Rapporto tra importo richiesto (liquidazione richiesta) e importo liquidato (liquidazione)';

COMMENT ON COLUMN pgmeas.pgmeas_r_liquidazione.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_categoria"                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_categoria (
    r_int_categoria_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    int_categoria_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_categoria_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_categoria_1 ON pgmeas.pgmeas_r_intervento_categoria (int_categoria_id);

CREATE INDEX IDX_pgmeas_r_intervento_categoria_2 ON pgmeas.pgmeas_r_intervento_categoria (int_id);

CREATE INDEX IDX_pgmeas_r_intervento_categoria_3 ON pgmeas.pgmeas_r_intervento_categoria (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_categoria IS 'Rapporto tra intervento e categoria intervento 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_categoria.validita_inizio IS 'now()';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_categoria.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_finalita"                        */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_finalita (
    r_int_finalita_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    int_finalita_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_finalita_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_finalita_1 ON pgmeas.pgmeas_r_intervento_finalita (int_finalita_id);

CREATE INDEX IDX_pgmeas_r_intervento_finalita_2 ON pgmeas.pgmeas_r_intervento_finalita (int_id);

CREATE INDEX IDX_pgmeas_r_intervento_finalita_3 ON pgmeas.pgmeas_r_intervento_finalita (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_finalita IS 'Rapporto tra intervento e finalità intervento 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_finalita.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_tipo"                            */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_tipo (
    r_int_tipo_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    note TEXT,
    int_tipo_id INTEGER,
    int_tipo_det_id INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_tipo_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_tipo_1 ON pgmeas.pgmeas_r_intervento_tipo (int_id);

CREATE INDEX IDX_pgmeas_r_intervento_tipo_2 ON pgmeas.pgmeas_r_intervento_tipo (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_tipo IS 'Rapporto tra intervento e tipo intervento 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_tipo.note IS 'valorizzato solo se selezionato int_tipo_cod = ''ALTRO''';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_tipo.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_contratto_tipo"                  */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_contratto_tipo (
    r_int_contratto_tipo_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    int_contratto_tipo_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_contratto_tipo_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_contratto_tipo_1 ON pgmeas.pgmeas_r_intervento_contratto_tipo (int_contratto_tipo_id);

CREATE INDEX IDX_pgmeas_r_intervento_contratto_tipo_2 ON pgmeas.pgmeas_r_intervento_contratto_tipo (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_contratto_tipo IS 'Rapporto tra intervento e tipo contratto 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_contratto_tipo.validita_inizio IS 'now()';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_contratto_tipo.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_obiettivo"                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_obiettivo (
    r_int_obiettivo_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    int_obiettivo_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_obiettivo_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_obiettivo_1 ON pgmeas.pgmeas_r_intervento_obiettivo (int_obiettivo_id);

CREATE INDEX IDX_pgmeas_r_intervento_obiettivo_2 ON pgmeas.pgmeas_r_intervento_obiettivo (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_obiettivo IS 'Rapporto tra intervento e obiettivo intervento 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_obiettivo.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_stato"                           */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_stato (
    r_int_stato_id SERIAL  NOT NULL,
    int_id INTEGER,
    int_stato_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_stato_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_stato_1 ON pgmeas.pgmeas_r_intervento_stato (int_stato_id);

CREATE INDEX IDX_pgmeas_r_intervento_stato_2 ON pgmeas.pgmeas_r_intervento_stato (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_stato IS 'Rapporto tra intervento e stato intervento 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_stato.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_stato_progettuale"               */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_stato_progettuale (
    r_int_stato_prog_id SERIAL  NOT NULL,
    int_id INTEGER,
    int_stato_prog_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_stato_prog_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_stato_progettuale_1 ON pgmeas.pgmeas_r_intervento_stato_progettuale (int_stato_prog_id);

CREATE INDEX IDX_pgmeas_r_intervento_stato_progettuale_2 ON pgmeas.pgmeas_r_intervento_stato_progettuale (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_stato_progettuale IS 'Rapporto tra intervento e stato progettuale intervento 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_stato_progettuale.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_finanziamento_importo"                      */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_finanziamento_importo (
    fin_imp_id SERIAL  NOT NULL,
    fin_id INTEGER,
    int_str_id INTEGER  NOT NULL,
    fin_imp_tipo_id INTEGER  NOT NULL,
    fin_importo NUMERIC  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (fin_imp_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_finanziamento_importo_1 ON pgmeas.pgmeas_r_finanziamento_importo (fin_imp_tipo_id);

CREATE INDEX IDX_pgmeas_r_finanziamento_importo_2 ON pgmeas.pgmeas_r_finanziamento_importo (fin_id);

CREATE INDEX IDX_pgmeas_r_finanziamento_importo_3 ON pgmeas.pgmeas_r_finanziamento_importo (ente_id);

CREATE INDEX IDX_pgmeas_r_finanziamento_importo_4 ON pgmeas.pgmeas_r_finanziamento_importo (int_str_id);

COMMENT ON TABLE pgmeas.pgmeas_r_finanziamento_importo IS 'Piano finanziario:  alle coppie  finanziamento/finanziamento tipo det intervento/struttura  si possono associare diversi tipi di importo in linea con le percentuali specificate in d_finanziamento_tipo_det';

COMMENT ON COLUMN pgmeas.pgmeas_r_finanziamento_importo.int_str_id IS 'coppia intervento struttura';

COMMENT ON COLUMN pgmeas.pgmeas_r_finanziamento_importo.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa"        */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa (
    int_fin_prev_spesa_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    fin_id INTEGER  NOT NULL,
    int_fin_prev_spesa_anno INTEGER  NOT NULL,
    int_fin_prev_spesa_importo NUMERIC  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (int_fin_prev_spesa_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_finanziamento_prev_spesa_1 ON pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa (fin_id);

CREATE INDEX IDX_pgmeas_r_intervento_finanziamento_prev_spesa_2 ON pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa (int_id);

CREATE INDEX IDX_pgmeas_r_intervento_finanziamento_prev_spesa_3 ON pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa IS 'Previsione spesa dell''intervento per finanziamento/anno';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intstr_quadroecon_ts"                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intstr_quadroecon_ts (
    intstr_qe_ts_id SERIAL  NOT NULL,
    intstr_id INTEGER,
    classif_ts_id INTEGER,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (intstr_qe_ts_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intstr_quadroecon_ts_1 ON pgmeas.pgmeas_t_intstr_quadroecon_ts (intstr_id);

CREATE INDEX IDX_pgmeas_t_intstr_quadroecon_ts_2 ON pgmeas.pgmeas_t_intstr_quadroecon_ts (ente_id);

CREATE INDEX IDX_pgmeas_t_intstr_quadroecon_ts_3 ON pgmeas.pgmeas_t_intstr_quadroecon_ts (classif_ts_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intstr_quadroecon_ts IS 'testata del modello quadro economico utilizzato dall''intervento';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_quadroecon_ts.classif_ts_id IS 'quale testata(modello) è utilizzata';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_quadroecon_ts.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_r_intervento_appalto_tipo"                    */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_r_intervento_appalto_tipo (
    r_int_appalto_tipo_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    int_appalto_tipo_id INTEGER  NOT NULL,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (r_int_appalto_tipo_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_r_intervento_appalto_tipo_1 ON pgmeas.pgmeas_r_intervento_appalto_tipo (int_appalto_tipo_id);

CREATE INDEX IDX_pgmeas_r_intervento_appalto_tipo_2 ON pgmeas.pgmeas_r_intervento_appalto_tipo (int_id);

CREATE INDEX IDX_pgmeas_r_intervento_appalto_tipo_3 ON pgmeas.pgmeas_r_intervento_appalto_tipo (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_r_intervento_appalto_tipo IS 'Rapporto tra intervento e appalto tipo 1-N';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_appalto_tipo.validita_inizio IS 'now()';

COMMENT ON COLUMN pgmeas.pgmeas_r_intervento_appalto_tipo.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_s_intervento_struttura"                       */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_s_intervento_struttura (
    s_intstr_id SERIAL  NOT NULL,
    intstr_id SERIAL  NOT NULL,
    int_id INTEGER  NOT NULL,
    str_id INTEGER  NOT NULL,
    intstr_importo NUMERIC,
    intstr_responsabile_cognome TEXT,
    intstr_responsabile_nome TEXT,
    intstr_responsabile_procedimento_cognome TEXT,
    intstr_responsabile_procedimento_nome TEXT,
    intstr_parere_vincolante_ppp BOOLEAN,
    intstr_parere_vincolante_ppp_n_protocollo TEXT,
    intstr_parere_cabina_di_regia BOOLEAN,
    intstr_parere_cabina_di_regia_n_protocollo TEXT,
    intstr_apertura_cantiere_data_prevista DATE,
    intstr_apertura_cantiere_data_effettiva DATE,
    intstr_priorita_anno TEXT,
    intstr_priorita INTEGER  NOT NULL,
    intstr_sottopriorita TEXT  NOT NULL,
    intstr_collaudo_data_prevista DATE,
    intstr_collaudo_data_effettiva DATE,
    intstr_pfte_previsione_durata_gg INTEGER,
    intstr_gara_previsione_durata_gg INTEGER,
    intstr_collaudo_previsione_durata_gg INTEGER,
    intstr_progettoesecutivo_previsione_durata_gg INTEGER,
    intstr_lavori_previsione_durata_gg INTEGER,
    intstr_attivazione_previsione_durata_gg INTEGER,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    validita_inizio TIMESTAMP  NOT NULL,
    validita_fine TIMESTAMP  NOT NULL,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (s_intstr_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_s_intervento_struttura_1 ON pgmeas.pgmeas_s_intervento_struttura (str_id);

CREATE INDEX IDX_pgmeas_s_intervento_struttura_2 ON pgmeas.pgmeas_s_intervento_struttura (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_s_intervento_struttura IS 'Associazione intervento/struttura.';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_priorita_anno IS 'le priorità seguenti sono per anno';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_priorita IS '1,2,3...';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_sottopriorita IS 'a,b,c';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_pfte_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_gara_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_collaudo_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_progettoesecutivo_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_lavori_previsione_durata_gg IS 'in giorni';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.intstr_attivazione_previsione_durata_gg IS 'in giorni - è la previsione di attivazione in N giorni dopo l''ultimazione dei lavori';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento_struttura.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_s_intervento"                                 */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_s_intervento (
    s_int_id SERIAL  NOT NULL,
    int_id SERIAL  NOT NULL,
    int_cod TEXT  NOT NULL,
    int_titolo TEXT  NOT NULL,
    int_anno TEXT  NOT NULL,
    int_cup TEXT,
    int_importo NUMERIC,
    int_codic_nsis TEXT,
    int_dg_aziendale_approvazione TEXT,
    int_dg_aziendale_approvazione_data TIMESTAMP,
    int_dg_regionale_approvazione TEXT,
    int_dg_regionale_approvazione_data TIMESTAMP,
    int_direttore_generale_cognome TEXT,
    int_direttore_generale_nome TEXT,
    int_commissario_cognome TEXT,
    int_commissario_nome TEXT,
    int_referente_pratica_cognome TEXT,
    int_referente_pratica_nome TEXT,
    int_referente_pratica_telefono TEXT,
    int_referente_pratica_email TEXT,
    int_forma_realizzativa_id INTEGER,
    int_str_tipo_id INTEGER,
    quadrante_id INTEGER,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    validita_inizio TIMESTAMP  NOT NULL,
    validita_fine TIMESTAMP  NOT NULL,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (s_int_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_s_intervento_1 ON pgmeas.pgmeas_s_intervento (int_forma_realizzativa_id);

CREATE INDEX IDX_pgmeas_s_intervento_2 ON pgmeas.pgmeas_s_intervento (int_str_tipo_id);

CREATE INDEX IDX_pgmeas_s_intervento_3 ON pgmeas.pgmeas_s_intervento (quadrante_id);

CREATE INDEX IDX_pgmeas_s_intervento_4 ON pgmeas.pgmeas_s_intervento (ente_id);

COMMENT ON TABLE pgmeas.pgmeas_s_intervento IS 'Interventi';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento.int_cod IS 'codice intervento= codice ente+anno (ANNO_RIF)+pad6(progressivo(NUMERO_PROGRESSIVO))';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento.int_titolo IS 'titolo intervento';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento.int_anno IS 'anno intervento';

COMMENT ON COLUMN pgmeas.pgmeas_s_intervento.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add table "pgmeas.pgmeas_t_intstr_quadroecon_tree"                     */
/* ---------------------------------------------------------------------- */



CREATE TABLE pgmeas.pgmeas_t_intstr_quadroecon_tree (
    intstr_qe_tree_id SERIAL  NOT NULL,
    intstr_qe_ts_id INTEGER  NOT NULL,
    classif_tree_id INTEGER  NOT NULL,
    intstr_qe_tree_visibile BOOLEAN DEFAULT true  NOT NULL,
    intstr_qe_tree_importo NUMERIC,
    validita_inizio TIMESTAMP DEFAULT now()  NOT NULL,
    validita_fine TIMESTAMP,
    data_creazione TIMESTAMP DEFAULT now()  NOT NULL,
    data_modifica TIMESTAMP DEFAULT now()  NOT NULL,
    data_cancellazione TIMESTAMP,
    utente_creazione TEXT  NOT NULL,
    utente_modifica TEXT  NOT NULL,
    utente_cancellazione TEXT,
    ente_id INTEGER  NOT NULL,
    PRIMARY KEY (intstr_qe_tree_id, ente_id)
) PARTITION BY LIST (ente_id);



CREATE INDEX IDX_pgmeas_t_intstr_quadroecon_tree_1 ON pgmeas.pgmeas_t_intstr_quadroecon_tree (intstr_qe_ts_id);

CREATE INDEX IDX_pgmeas_t_intstr_quadroecon_tree_2 ON pgmeas.pgmeas_t_intstr_quadroecon_tree (classif_tree_id);

COMMENT ON TABLE pgmeas.pgmeas_t_intstr_quadroecon_tree IS 'voce del quadro economico associata all''intervento e con importo';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_quadroecon_tree.intstr_qe_tree_visibile IS 'indica se la voce è utilizzata';

COMMENT ON COLUMN pgmeas.pgmeas_t_intstr_quadroecon_tree.ente_id IS 'proprietario_dati';

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE pgmeas.pgmeas_t_intervento ADD CONSTRAINT pgmeas_d_intervento_forma_realizzativa_pgmeas_t_intervento 
    FOREIGN KEY (int_forma_realizzativa_id) REFERENCES pgmeas.pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_id);

ALTER TABLE pgmeas.pgmeas_t_intervento ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_intervento 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intervento ADD CONSTRAINT pgmeas_d_intervento_struttura_tipo_pgmeas_t_intervento 
    FOREIGN KEY (int_str_tipo_id) REFERENCES pgmeas.pgmeas_d_intervento_struttura_tipo (int_str_tipo_id);

ALTER TABLE pgmeas.pgmeas_t_intervento ADD CONSTRAINT pgmeas_d_quadrante_pgmeas_t_intervento 
    FOREIGN KEY (quadrante_id) REFERENCES pgmeas.pgmeas_d_quadrante (quadrante_id);

ALTER TABLE pgmeas.pgmeas_t_struttura ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_struttura 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

/*ALTER TABLE pgmeas.pgmeas_t_struttura ADD CONSTRAINT pgmeas_t_struttura_pgmeas_t_struttura 
    FOREIGN KEY (str_id_padre) REFERENCES pgmeas.pgmeas_t_struttura (str_id);*/

ALTER TABLE pgmeas.pgmeas_t_struttura ADD CONSTRAINT pgmeas_d_struttura_tipo_pgmeas_t_struttura 
    FOREIGN KEY (struttura_tipo_id) REFERENCES pgmeas.pgmeas_d_struttura_tipo (struttura_tipo_id);

ALTER TABLE pgmeas.pgmeas_d_ente ADD CONSTRAINT pgmeas_d_ente_tipo_pgmeas_d_ente 
    FOREIGN KEY (ente_tipo_id) REFERENCES pgmeas.pgmeas_d_ente_tipo (ente_tipo_id);

ALTER TABLE pgmeas.pgmeas_d_intervento_tipo_det ADD CONSTRAINT pgmeas_d_intervento_tipo_pgmeas_d_intervento_tipo_det 
    FOREIGN KEY (int_tipo_id) REFERENCES pgmeas.pgmeas_d_intervento_tipo (int_tipo_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento ADD CONSTRAINT pgmeas_d_finanziamento_tipo_det_pgmeas_t_finanziamento 
    FOREIGN KEY (fin_tipo_det_id) REFERENCES pgmeas.pgmeas_d_finanziamento_tipo_det (fin_tipo_det_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento ADD CONSTRAINT pgmeas_t_intervento_pgmeas_t_finanziamento 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_finanziamento 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento ADD CONSTRAINT pgmeas_t_provvedimento_pgmeas_t_finanziamento 
    FOREIGN KEY (prov_id, ente_id) REFERENCES pgmeas.pgmeas_t_provvedimento (prov_id,ente_id);

ALTER TABLE pgmeas.pgmeas_d_finanziamento_tipo_det ADD CONSTRAINT pgmeas_d_finanziamento_tipo_pgmeas_d_finanziamento_tipo_det 
    FOREIGN KEY (fin_tipo_id) REFERENCES pgmeas.pgmeas_d_finanziamento_tipo (fin_tipo_id);

ALTER TABLE pgmeas.pgmeas_t_provvedimento ADD CONSTRAINT pgmeas_d_finanziamento_tipo_det_pgmeas_t_provvedimento 
    FOREIGN KEY (fin_tipo_det_id) REFERENCES pgmeas.pgmeas_d_finanziamento_tipo_det (fin_tipo_det_id);

ALTER TABLE pgmeas.pgmeas_t_provvedimento ADD CONSTRAINT pgmeas_d_provvedimento_livello_pgmeas_t_provvedimento 
    FOREIGN KEY (prov_liv_id) REFERENCES pgmeas.pgmeas_d_provvedimento_livello (prov_liv_id);

ALTER TABLE pgmeas.pgmeas_t_provvedimento ADD CONSTRAINT pgmeas_d_provvedimento_tipo_pgmeas_t_provvedimento 
    FOREIGN KEY (prov_tipo_id) REFERENCES pgmeas.pgmeas_d_provvedimento_tipo (prov_tipo_id);

ALTER TABLE pgmeas.pgmeas_t_provvedimento ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_provvedimento 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_provvedimento ADD CONSTRAINT pgmeas_t_provvedimento_pgmeas_t_provvedimento22 
    FOREIGN KEY (prov_id_sostituito, ente_id) REFERENCES pgmeas.pgmeas_t_provvedimento (prov_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_provvedimento ADD CONSTRAINT pgmeas_t_provvedimento_pgmeas_t_provvedimento 
    FOREIGN KEY (prov_id_padre, ente_id) REFERENCES pgmeas.pgmeas_t_provvedimento (prov_id,ente_id);

ALTER TABLE pgmeas.pgmeas_d_provvedimento_tipo ADD CONSTRAINT pgmeas_d_organo_pgmeas_d_provvedimento_tipo 
    FOREIGN KEY (organo_id) REFERENCES pgmeas.pgmeas_d_organo (organo_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_previsione_spesa ADD CONSTRAINT pgmeas_t_intervento_pgmeas_t_intervento_previsione_spesa 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_previsione_spesa ADD CONSTRAINT pgmeas_d_piano_triennale_pgmeas_t_intervento_previsione_spesa 
    FOREIGN KEY (piano_id) REFERENCES pgmeas.pgmeas_d_piano_triennale (piano_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_previsione_spesa ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_intervento_previsione_spesa 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_d_classif_ts ADD CONSTRAINT pgmeas_d_classif_ts_tipo_pgmeas_d_classif_ts 
    FOREIGN KEY (classif_ts_tipo_id) REFERENCES pgmeas.pgmeas_d_classif_ts_tipo (classif_ts_tipo_id);

ALTER TABLE pgmeas.pgmeas_d_classif_tree ADD
    FOREIGN KEY (classif_id) REFERENCES pgmeas.pgmeas_d_classif_elem (classif_id);

ALTER TABLE pgmeas.pgmeas_d_classif_tree ADD CONSTRAINT pgmeas_d_classif_elem_pgmeas_d_classif_tree 
    FOREIGN KEY (classif_id_padre) REFERENCES pgmeas.pgmeas_d_classif_elem (classif_id);

ALTER TABLE pgmeas.pgmeas_d_classif_tree ADD CONSTRAINT pgmeas_d_classif_ts_pgmeas_d_classif_tree 
    FOREIGN KEY (classif_ts_id) REFERENCES pgmeas.pgmeas_d_classif_ts (classif_ts_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_struttura ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_intervento_struttura 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_struttura ADD CONSTRAINT pgmeas_t_struttura_pgmeas_t_intervento_struttura 
    FOREIGN KEY (str_id, ente_id) REFERENCES pgmeas.pgmeas_t_struttura (str_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_struttura ADD CONSTRAINT pgmeas_t_intervento_pgmeas_t_intervento_struttura 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_gara_appalto ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_intervento_gara_appalto 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intervento_gara_appalto ADD CONSTRAINT pgmeas_t_intervento_struttura_pgmeas_t_intervento_gara_appalto 
    FOREIGN KEY (intstr_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento_struttura (intstr_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_allegato ADD CONSTRAINT pgmeas_d_allegato_tipo_pgmeas_t_allegato 
    FOREIGN KEY (allegato_tipo_id) REFERENCES pgmeas.pgmeas_d_allegato_tipo (allegato_tipo_id);

ALTER TABLE pgmeas.pgmeas_t_allegato ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_allegato 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_allegato ADD CONSTRAINT pgmeas_t_intervento_pgmeas_t_allegato 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta ADD CONSTRAINT t_finanziamento_liquidazione_richiesta 
    FOREIGN KEY (fin_id, ente_id) REFERENCES pgmeas.pgmeas_t_finanziamento (fin_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_finanziamento_liquidazione_richiesta 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento_liquidazione ADD CONSTRAINT pgmeas_t_finanziamento_pgmeas_t_finanziamento_liquidazione 
    FOREIGN KEY (fin_id, ente_id) REFERENCES pgmeas.pgmeas_t_finanziamento (fin_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_finanziamento_liquidazione ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_finanziamento_liquidazione 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_liquidazione ADD CONSTRAINT pgmeas_t_fin_r_liquidazione 
    FOREIGN KEY (liq_ric_id, ente_id) REFERENCES pgmeas.pgmeas_t_finanziamento_liquidazione_richiesta (liq_ric_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_liquidazione ADD CONSTRAINT pgmeas_t_finanziamento_liquidazione_pgmeas_r_liquidazione 
    FOREIGN KEY (liq_id, ente_id) REFERENCES pgmeas.pgmeas_t_finanziamento_liquidazione (liq_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_liquidazione ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_liquidazione 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_quadroecon_tree ADD CONSTRAINT pgmeas_d_classif_tree_pgmeas_t_intstr_quadroecon_tree 
    FOREIGN KEY (classif_tree_id) REFERENCES pgmeas.pgmeas_d_classif_tree (classif_tree_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_quadroecon_tree ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_intstr_quadroecon_tree 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_quadroecon_tree ADD CONSTRAINT t_intervento_qe_ts_r_intervento_qe_elem 
    FOREIGN KEY (intstr_qe_ts_id, ente_id) REFERENCES pgmeas.pgmeas_t_intstr_quadroecon_ts (intstr_qe_ts_id,ente_id);

ALTER TABLE pgmeas.pgmeas_c_parametro ADD CONSTRAINT pgmeas_c_parametro_tipo_pgmeas_c_parametro 
    FOREIGN KEY (parametro_tipo_id) REFERENCES pgmeas.pgmeas_c_parametro_tipo (parametro_tipo_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_categoria ADD CONSTRAINT pgmeas_d_intervento_categoria_pgmeas_r_intervento_categoria 
    FOREIGN KEY (int_categoria_id) REFERENCES pgmeas.pgmeas_d_intervento_categoria (int_categoria_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_categoria ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_categoria 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_categoria ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_categoria 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_finalita ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_finalita 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_finalita ADD CONSTRAINT pgmeas_d_intervento_finalita_pgmeas_r_intervento_finalita 
    FOREIGN KEY (int_finalita_id) REFERENCES pgmeas.pgmeas_d_intervento_finalita (int_finalita_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_finalita ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_finalita 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_tipo ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_tipo 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_tipo ADD CONSTRAINT pgmeas_d_intervento_tipo_det_pgmeas_r_intervento_tipo 
    FOREIGN KEY (int_tipo_det_id) REFERENCES pgmeas.pgmeas_d_intervento_tipo_det (int_tipo_det_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_tipo ADD CONSTRAINT pgmeas_d_intervento_tipo_pgmeas_r_intervento_tipo 
    FOREIGN KEY (int_tipo_id) REFERENCES pgmeas.pgmeas_d_intervento_tipo (int_tipo_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_tipo ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_tipo 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_contratto_tipo ADD CONSTRAINT d_intervento_contratto_tipo_r_intervento_contratto_tipo 
    FOREIGN KEY (int_contratto_tipo_id) REFERENCES pgmeas.pgmeas_d_intervento_contratto_tipo (int_contratto_tipo_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_contratto_tipo ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_contratto_tipo 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_contratto_tipo ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_contratto_tipo 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_obiettivo ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_obiettivo 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_obiettivo ADD CONSTRAINT pgmeas_d_intervento_obiettivo_pgmeas_r_intervento_obiettivo 
    FOREIGN KEY (int_obiettivo_id) REFERENCES pgmeas.pgmeas_d_intervento_obiettivo (int_obiettivo_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_obiettivo ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_obiettivo 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_stato ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_stato 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_stato ADD CONSTRAINT pgmeas_d_intervento_stato_pgmeas_r_intervento_stato 
    FOREIGN KEY (int_stato_id) REFERENCES pgmeas.pgmeas_d_intervento_stato (int_stato_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_stato ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_stato 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_stato_progettuale ADD CONSTRAINT d_intervento_stato_progettuale_r_intervento_stato_progettuale 
    FOREIGN KEY (int_stato_prog_id) REFERENCES pgmeas.pgmeas_d_intervento_stato_progettuale (int_stato_prog_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_stato_progettuale ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_stato_progettuale 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_stato_progettuale ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_stato_progettuale 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_finanziamento_importo ADD CONSTRAINT d_finanziamento_importo_tipo_r_finanziamento_importo 
    FOREIGN KEY (fin_imp_tipo_id) REFERENCES pgmeas.pgmeas_d_finanziamento_importo_tipo (fin_imp_tipo_id);

ALTER TABLE pgmeas.pgmeas_r_finanziamento_importo ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_finanziamento_importo 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_finanziamento_importo ADD CONSTRAINT pgmeas_t_intervento_struttura_pgmeas_r_finanziamento_importo 
    FOREIGN KEY (int_str_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento_struttura (intstr_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_finanziamento_importo ADD CONSTRAINT pgmeas_t_finanziamento_pgmeas_r_finanziamento_importo 
    FOREIGN KEY (fin_id, ente_id) REFERENCES pgmeas.pgmeas_t_finanziamento (fin_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa ADD CONSTRAINT t_finanziamento_r_intervento_finanaziamento_prev_spesa 
    FOREIGN KEY (fin_id, ente_id) REFERENCES pgmeas.pgmeas_t_finanziamento (fin_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa ADD CONSTRAINT t_intervento_r_intervento_finanaziamento_prev_spesa 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_finanziamento_prev_spesa ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_finanziamento_prev_spesa 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_quadroecon_ts ADD CONSTRAINT pgmeas_d_classif_ts_pgmeas_t_intstr_quadroecon_ts 
    FOREIGN KEY (classif_ts_id) REFERENCES pgmeas.pgmeas_d_classif_ts (classif_ts_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_quadroecon_ts ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_intstr_quadroecon_ts 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_quadroecon_ts ADD CONSTRAINT pgmeas_t_intervento_struttura_pgmeas_t_intstr_quadroecon_ts 
    FOREIGN KEY (intstr_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento_struttura (intstr_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_finanziamento_importo_tipo_det ADD CONSTRAINT d_finanziamento_tipo_det_r_finanziamento_importo_tipo_det 
    FOREIGN KEY (fin_tipo_det_id) REFERENCES pgmeas.pgmeas_d_finanziamento_tipo_det (fin_tipo_det_id);

ALTER TABLE pgmeas.pgmeas_r_finanziamento_importo_tipo_det ADD CONSTRAINT d_finanziamento_importo_tipo_r_finanziamento_importo_tipo_det 
    FOREIGN KEY (fin_imp_tipo_id) REFERENCES pgmeas.pgmeas_d_finanziamento_importo_tipo (fin_imp_tipo_id);

ALTER TABLE pgmeas.pgmeas_r_finanziamento_importo_tipo_det ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_finanziamento_importo_tipo_det 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_appalto_tipo ADD CONSTRAINT d_intervento_appalto_tipo_r_intervento_appalto_tipo 
    FOREIGN KEY (int_appalto_tipo_id) REFERENCES pgmeas.pgmeas_d_intervento_appalto_tipo (int_appalto_tipo_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_appalto_tipo ADD CONSTRAINT pgmeas_t_intervento_pgmeas_r_intervento_appalto_tipo 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_r_intervento_appalto_tipo ADD CONSTRAINT pgmeas_d_ente_pgmeas_r_intervento_appalto_tipo 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_s_intervento_struttura ADD CONSTRAINT pgmeas_t_intervento_struttura_pgmeas_s_intervento_struttura 
    FOREIGN KEY (intstr_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento_struttura (intstr_id,ente_id);

ALTER TABLE pgmeas.pgmeas_s_intervento_struttura ADD CONSTRAINT pgmeas_d_ente_pgmeas_s_intervento_struttura 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_s_intervento ADD CONSTRAINT pgmeas_t_intervento_pgmeas_s_intervento 
    FOREIGN KEY (int_id, ente_id) REFERENCES pgmeas.pgmeas_t_intervento (int_id,ente_id);

ALTER TABLE pgmeas.pgmeas_s_intervento ADD CONSTRAINT pgmeas_d_ente_pgmeas_s_intervento 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_ts ADD CONSTRAINT pgmeas_d_classif_ts_pgmeas_t_intstr_interventoedilizio_ts 
    FOREIGN KEY (classif_ts_id) REFERENCES pgmeas.pgmeas_d_classif_ts (classif_ts_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_ts ADD CONSTRAINT pgmeas_d_ente_pgmeas_t_intstr_interventoedilizio_ts 
    FOREIGN KEY (ente_id) REFERENCES pgmeas.pgmeas_d_ente (ente_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_tree ADD CONSTRAINT t_intervento_qe_ts_r_intervento_qe_elem2 
    FOREIGN KEY (intstr_ie_ts_id, ente_id) REFERENCES pgmeas.pgmeas_t_intstr_interventoedilizio_ts (intstr_ie_ts_id,ente_id);

ALTER TABLE pgmeas.pgmeas_t_intstr_interventoedilizio_tree ADD CONSTRAINT pgmeas_d_classif_tree_pgmeas_t_intstr_interventoedilizio_tree 
    FOREIGN KEY (classif_tree_id) REFERENCES pgmeas.pgmeas_d_classif_tree (classif_tree_id);



CREATE OR REPLACE FUNCTION pgmeas.fnc_after_insert_on_pgmeas_d_ente()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare 
curtb record;
tbus text; 
begin

set search_path=pgmeas;

for curtb in 
SELECT b.tablename, b.tablename||'_' tbus 
FROM pg_class a, pg_tables b
WHERE  
b.tablename =a.relname  and 
substring(b.tablename ,1,8) in ('pgmeas_r','pgmeas_t','pgmeas_s') 
AND relnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'pgmeas')
and relpartbound is null  loop 


 execute format(
        'CREATE TABLE if not exists %s PARTITION OF %2$s FOR VALUES in (%3$s)',
        concat(curtb.tbus, new.ente_id),
        curtb.tablename,
        new.ente_id);

end loop;
       
    return null;
end
$function$
;

create trigger trg_after_insert_on_pgmeas_d_ente after
insert
    on
    pgmeas.pgmeas_d_ente for each row execute function pgmeas.fnc_after_insert_on_pgmeas_d_ente();




CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BYTEA NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

--12/06/2024
alter table pgmeas_r_finanziamento_importo_tipo_det add column fin_percentuale_importo numeric 


--modifiche dev 05/08/2024


alter table pgmeas_t_intervento drop column int_dg_aziendale_approvazione;
alter table pgmeas_t_intervento drop column int_dg_aziendale_approvazione_data;
alter table pgmeas_t_intervento drop column int_dg_regionale_approvazione;
alter table pgmeas_t_intervento drop column int_dg_regionale_approvazione_data;


alter table pgmeas_t_intervento add column int_rup_cognome text;
alter table pgmeas_t_intervento add column int_rup_nome text;
alter table pgmeas_t_intervento add column int_rup_cf text;
alter table pgmeas_t_intervento add column int_priorita_anno integer;
alter table pgmeas_t_intervento add column int_priorita integer;
alter table pgmeas_t_intervento add column int_sottopriorita text;
alter table pgmeas_t_intervento add column int_finanziabile boolean;


update pgmeas_t_intervento set int_rup_cognome=subquery.intstr_responsabile_procedimento_cognome from (
select int_id, intstr_responsabile_procedimento_cognome from pgmeas_t_intervento_struttura
) as subquery where subquery.int_id=pgmeas_t_intervento.int_id;

update pgmeas_t_intervento set int_rup_nome=subquery.intstr_responsabile_procedimento_nome from (
select int_id, intstr_responsabile_procedimento_nome from pgmeas_t_intervento_struttura
) as subquery where subquery.int_id=pgmeas_t_intervento.int_id;

update pgmeas_t_intervento set int_priorita_anno=subquery.intstr_priorita_anno::integer from (
select int_id, intstr_priorita_anno from pgmeas_t_intervento_struttura
) as subquery where subquery.int_id=pgmeas_t_intervento.int_id;

update pgmeas_t_intervento set int_priorita=subquery.intstr_priorita from (
select int_id, intstr_priorita from pgmeas_t_intervento_struttura
) as subquery where subquery.int_id=pgmeas_t_intervento.int_id;

update pgmeas_t_intervento set int_sottopriorita=subquery.intstr_sottopriorita from (
select int_id, intstr_sottopriorita from pgmeas_t_intervento_struttura
) as subquery where subquery.int_id=pgmeas_t_intervento.int_id;




alter table pgmeas_t_intervento_struttura add column intstr_responsabile_struttura_complessa_cognome text;
alter table pgmeas_t_intervento_struttura add column intstr_responsabile_struttura_complessa_nome text;
alter table pgmeas_t_intervento_struttura add column intstr_responsabile_struttura_complessa_cf text;
alter table pgmeas_t_intervento_struttura add column intstr_responsabile_struttura_semplice_cognome text;
alter table pgmeas_t_intervento_struttura add column intstr_responsabile_struttura_semplice_nome text;
alter table pgmeas_t_intervento_struttura add column intstr_responsabile_struttura_semplice_cf text;


update pgmeas_t_intervento_struttura set intstr_responsabile_struttura_complessa_cognome=intstr_responsabile_cognome;
update pgmeas_t_intervento_struttura set intstr_responsabile_struttura_complessa_nome=intstr_responsabile_nome;

alter table pgmeas_t_intervento_struttura drop column intstr_responsabile_cognome;
alter table pgmeas_t_intervento_struttura drop column intstr_responsabile_nome;

ALTER TABLE pgmeas_t_intervento_struttura  RENAME COLUMN intstr_parere_vincolante_ppp TO intstr_parere_ppp;
alter table pgmeas_t_intervento_struttura drop column intstr_parere_vincolante_ppp_n_protocollo;
alter table pgmeas_t_intervento_struttura add column intstr_parere_hta boolean;

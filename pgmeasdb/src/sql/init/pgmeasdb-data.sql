insert into pgmeas.pgmeas_d_ente_tipo (ente_tipo_cod,ente_tipo_desc,utente_creazione,utente_modifica) values
	 ('ASL','Azienda Sanitaria Locale','ADMIN','ADMIN'),
	 ('AO','Azienda Ospedaliera','ADMIN','ADMIN'),
	 ('AOU','Azienda Ospedaliera Universitaria','ADMIN','ADMIN');



insert into pgmeas.pgmeas_d_ente (ente_cod_esteso,ente_regione_cod,ente_regione_desc,ente_cod,ente_desc,ente_indirizzo,ente_cap,ente_comune,ente_provincia_sigla,ente_telefono,ente_fax,ente_email,ente_sito_web,ente_partita_iva,ente_tipo_id,utente_creazione,utente_modifica) 
values
	 ('010203','010','PIEMONTE','203','ASL TO3 (Distretti Pinerolo, Val Susa-Val Sangone, Area Metropolitana Nord-Centro-Sud)','VIA MARTIRI XXX APRILE 30','10093','COLLEGNO','TO','01140171','0114017277','aslto3@cert.aslto3.piemonte.it','www.aslto3.piemonte.it','02704350012',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010204','010','PIEMONTE','204','ASL TO4 (Distretti di Ciriè, Chivasso-San Mauro, Settimo Torinese, Ivrea, Cuorgnè)','Via Po 11','10034','CHIVASSO','TO','0119176666','0119176322','direzione.generale@asl7.to.it','www.asl.ivrea.to.it','09736160012',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010205','010','PIEMONTE','205','ASL TO5 (Chieri, Carmagnola, Moncalieri, Nichelino)','VIA SAN DOMENICO 21','10023','CHIERI','TO','01194291','01194293268','protocollo@cert.aslto5.piemonte.it','www.asl8.piemonte.it','06827170017',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010206','010','PIEMONTE','206','ASL VC (Vercelli)','C.SO MARIO ABBIATE 21','13100','VERCELLI','VC','01615931','0161210284','affari.generali@asl11.piemonte.it','www.asl11.piemonte.it','01811110020',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010207','010','PIEMONTE','207','ASL BI (Biella)','VIA DEI PONDERANESI 2','13875','PONDERANO','BI','01535031','0153503545','ufficio.protocollo@cert.aslbi.piemonte.it','www2.aslbi.piemonte.it','01810260024',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010208','010','PIEMONTE','208','ASL NO (Novara)','VIALE ROMA 7','28100','NOVARA','NO','0321374111','0321374519','protocollogenerale@pec.asl.novara.it','www.asl13.novara.it','01522670031',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010209','010','PIEMONTE','209','ASL VCO (Verbano Cusio Ossola)','VIA MAZZINI 117','28887','OMEGNA','VB','0323868111','0323643020','protocollo@pec.aslvco.it','www.aslvco.it','00634880033',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010210','010','PIEMONTE','210','ASL CN1 (Cuneo, Borgo S.Dalmazzo-Dronero, Mondovì, Ceva, Savigliano-Fossano, Saluzzo)','VIA CARLO BOGGIO 12','12100','CUNEO','CN','0171450111','0171450207','protocollo@aslcn1.legalmailPA.it','www.asl17.it','01128930045',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010211','010','PIEMONTE','211','ASL CN2 (Alba e Bra)','VIA VIDA 10','12051','ALBA','CN','0173316111','0173316480','aslcn2@legalmail.it','www.asl18.it','02419170044',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010212','010','PIEMONTE','212','ASL AT (Asti Centro, Asti Nord, Asti Sud)','VIA CONTE VERDE 125','14100','ASTI','AT','0141484000','0141484095','protocollo@asl.at.it','portale.asl.at.it','01120620057',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010213','010','PIEMONTE','213','ASL AL (Alessandria)','VIA VENEZIA 6','15121','ALESSANDRIA','AL','0142434111','0142434361','direzione@aslal.it','www.aslal.it','02190140067',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
	 ('010301','010','PIEMONTE','301','ASL Città di TORINO','VIA SAN SECONDO  29','10128','TORINO','TO','0115661566','0115661566','protocollo@pec.aslcittaditorino.it','www.aslcittaditorino.it','11632570013',(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='ASL'),'ADMIN','ADMIN'),
('010906','010','PIEMONTE','906','AZIENDA OSP. S.CROCE E CARLE', null,null,null,null,null,null,null,null,null,(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='AO'),'ADMIN','ADMIN'),
('010907','010','PIEMONTE','907','AZIENDA OSP. S.ANTONIO BIAGIO/ARRIGO', null,null,null,null,null,null,null,null,null,(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='AO'),'ADMIN','ADMIN'),
('010908','010','PIEMONTE','908','AZIENDA OSP. ORDINE MAURIZIANO DI TORINO', null,null,null,null,null,null,null,null,null,(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='AO'),'ADMIN','ADMIN'),
('010904','010','PIEMONTE','904','AZIENDA OSP. S.LUIGI', null,null,null,null,null,null,null,null,null,(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='AOU'),'ADMIN','ADMIN'),
('010905','010','PIEMONTE','905','AZIENDA OSP. MAGGIORE DELLA CARITA''', null,null,null,null,null,null,null,null,null,(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='AOU'),'ADMIN','ADMIN'),
('010909','010','PIEMONTE','909','AZIENDA OSP. CITTA DELLA SALUTE E DELLA SCIENZA DI TORINO', null,null,null,null,null,null,null,null,null,(select ente_tipo_id from pgmeas.pgmeas_d_ente_tipo where ente_tipo_cod='AOU'),'ADMIN','ADMIN');

insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo A, Richiesta di ammissione al finanziamento','MOD_A', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo A-A, Richiesta di ammissione al finanziamento per attrezzature','MOD_A_A', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo A-C, Richiesta di nulla-osta per opere complementari dell''intervento','MOD_A_C', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo A-P, Richiesta di nulla-osta per varianti in corso d''opera','MOD_A_P', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo B-A, Richiesta di liquidazione per le attrezzature','MOD_B_A', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo B-R, Richiesta di liquidazione finanziamenti regionali','MOD_B_R', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo B-S, Richiesta di liquidazione finanziamenti statali','MOD_B_S', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo E, Richiesta di utilizzo economie per opere, arredi e/o attrezzature supplementari','MOD_E', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo R-A, Relazione acclarante i rapporti finanziari tra Regione Piemonte ed Ente','MOD_R_A', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Modulo R-E, Relazione richiesta utilizzo delle economie','MOD_R_E', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Provvedimento aziendale di approvazione del progetto','PROV_AZ_APP', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Relazione tecnica','REL_TEC', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Scheda C-R, Monitoraggio dell''attuazione degli interventi finanziati dalla Regione','SCH_C_R', 'ADMIN','ADMIN';
insert into pgmeas_d_allegato_tipo (allegato_tipo_desc, allegato_tipo_cod, utente_creazione, utente_modifica) select 'Scheda C-S, Monitoraggio dell''attuazione degli interventi finanziati dallo Stato','SCH_C_S', 'ADMIN','ADMIN';



--insert into pgmeas_d_appalto_tipo (appalto_tipo_cod,  appalto_tipo_desc,utente_creazione, utente_modifica) select 'FOR','Forniture', 'ADMIN','ADMIN';
--insert into pgmeas_d_appalto_tipo (appalto_tipo_cod,  appalto_tipo_desc,utente_creazione, utente_modifica) select 'LAV','Lavori', 'ADMIN','ADMIN';
--insert into pgmeas_d_appalto_tipo (appalto_tipo_cod,  appalto_tipo_desc,utente_creazione, utente_modifica) select 'SER','Servizi', 'ADMIN','ADMIN';
	

insert into pgmeas_d_fase (fase_cod,  fase_desc,utente_creazione, utente_modifica) select 'P','Programmazione', 'ADMIN','ADMIN';
insert into pgmeas_d_fase (fase_cod,  fase_desc,utente_creazione, utente_modifica) select 'G','Gestione', 'ADMIN','ADMIN';
insert into pgmeas_d_fase (fase_cod,  fase_desc,utente_creazione, utente_modifica) select 'M','Monitoraggio', 'ADMIN','ADMIN';



insert into pgmeas_d_finanziamento_tipo (fin_tipo_cod, fin_tipo_desc,utente_creazione, utente_modifica) select 'A','ALTROI FONDI', 'ADMIN','ADMIN';
insert into pgmeas_d_finanziamento_tipo (fin_tipo_cod, fin_tipo_desc,utente_creazione, utente_modifica) select 'R','FONDI REGIONALI', 'ADMIN','ADMIN';
insert into pgmeas_d_finanziamento_tipo (fin_tipo_cod, fin_tipo_desc,utente_creazione, utente_modifica) select 'S','FONDI STATALI', 'ADMIN','ADMIN';
insert into pgmeas_d_finanziamento_tipo (fin_tipo_cod, fin_tipo_desc,utente_creazione, utente_modifica) select 'I','FONDI INAIL', 'ADMIN','ADMIN';

INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00001','ART. 20 L. n. 67/1988 I^ Fase (C.D.P.)',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00002','ART. 20 L. n. 67/1988 II^ Fase DCR 440 L 450-97',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00003','ART. 20 L. n. 67/1988 II^ Fase ACCORDO 2000 DCR 10-2005 ',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00004','ART. 20 L. n. 67/1988 II^ Fase ACCORDO 2000 DCR 10-2005 (35%) ',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00005','ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2008',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00006','ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2018',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00007','ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2022',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00008','ART. 20 L. n. 67/1988 ACCORDO 2023',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00009','ART. 20 L. n. 67/1988 ACCORDO 2024',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00010','ART. 20 L. n. 67/1988 NUOVI INTERVENTI IN PROGRAMMAZIONE',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00011','ART. 20 L. n. 67/1988 DGR 2-3900/2016',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00012','D.L. 18/2020 – INTERVENTI URGENTI COVID-19',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00013','INTRAMOENIA D. LGS. 254-2000',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',95,5,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00014','OPERE OLIMPICHE – L. n. 285-2000 ',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00015','L. n. 135/1990 AIDS',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00016','L. n. 145 ART. 1 C. 95 INTERVENTI ANTINCENDIO/ANTISISMICA',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00017','L. n. 160 ART. 1 C.14 EFFICIENTAMENTO ENERGETICO',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00018','L. n. 178 ART.1 C. 445 OSSIGENO MEDICALE',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00019','HOSPICE',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00020','RADIOTERAPIA',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00021','ART. 71 L. n. 448/1988',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='S'), 'ADMIN','ADMIN',null,null,0;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00022','Altri Fondi Regionali',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00023','Radiofarmaci',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00024','Conto Capitale Regionale',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00025','Fondi Regionali GSA',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00026','Fondi Regionali derivanti da sanzioni amministrative',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00027','L.R. N. 24/2000',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00028','L.R. N. 25/2000',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00029','L.R. N. 2/2003',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00030','L.R. 55 del 01/09/1997',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00031','L.R. 40 del 03/07/1996',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00032','Anticipazioni Alienazioni',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='R'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00033','Alienazioni',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00034','Altro ',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00035','PPP',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00036','Donazioni',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00037','Fondi aziendali',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00038','MUTUO',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00039','SPONSORIZZAZIONI',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00040','SOCIETA'' PARTECIPATE O DI SCOPO',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00041','FINANZA DI PROGETTO',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00042','CONCESSIONE DI COSTRUZIONE E GESTIONE',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00043','CAPITALE PRIVATO',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='A'), 'ADMIN','ADMIN',0,0,100;
INSERT INTO pgmeas_d_finanziamento_tipo_det ( fin_tipo_det_cod, fin_tipo_det_desc, fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro) SELECT '00044','INAIL NUOVI INTERVENTI',(select fin_tipo_id from pgmeas_d_finanziamento_tipo where fin_tipo_cod='I'), 'ADMIN','ADMIN',0,0,100;
 

insert into pgmeas_d_finanziamento_importo_tipo (fin_imp_tipo_cod,fin_imp_tipo_desc,utente_creazione, utente_modifica) select 'IMP_STATO','IMPORTO A CARICO DELLO STATO', 'ADMIN','ADMIN';
insert into pgmeas_d_finanziamento_importo_tipo (fin_imp_tipo_cod,fin_imp_tipo_desc,utente_creazione, utente_modifica) select 'IMP_REGIONE','IMPORTO A CARICO DELLA REGIONE', 'ADMIN','ADMIN';
insert into pgmeas_d_finanziamento_importo_tipo (fin_imp_tipo_cod,fin_imp_tipo_desc,utente_creazione, utente_modifica) select 'IMP_AZ','IMPORTO A CARICO DELL''AZIENDA', 'ADMIN','ADMIN';
insert into pgmeas_d_finanziamento_importo_tipo (fin_imp_tipo_cod,fin_imp_tipo_desc,utente_creazione, utente_modifica) select 'IMP_INAIL','IMPORTO A CARICO DI INAIL', 'ADMIN','ADMIN';


insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'ALTRO','9999', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'PREVENZIONE COLLETTIVA SERVIZI DI IGIENE PUBBLICA','A1A1', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'PREVENZIONE COLLETTIVA I.Z.S.','A2A2', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI TERRITORIALI DISTRETTI','B1B1', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI TERRITORIALI POLIAMBULATORI','B2B2', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI TERRITORIALI ALTRE STRUTTURE','B3B3', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI TERRITORIALI TECNOLOGIE','B4B4', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'RESIDENZE SANITARIE ASSISTENZIALI RSA PER ANZIANI','C1C1', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'RESIDENZE SANITARIE ASSISTENZIALI RSA PER DISABILI','C2C2', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'OSPEDALI OPERE','D1D1', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'OSPEDALI TECNOLOGIE','D2D2', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI GENERALI OSPEDALIERI MESSA A NORMA','E1E1', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI GENERALI OSPEDALIERI SISTEMA INFORMATIVO','E2E2', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI GENERALI OSPEDALIERI UMANIZZAZIONE E CONF.','E3E3', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'SERVIZI GENERALI OSPEDALIERI ALTRO','E4E4', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_categoria (int_categoria_cod,int_categoria_desc,utente_creazione, utente_modifica) select 'PROGETTO DI RILIEVO NAZIONALE','FF', 'ADMIN','ADMIN';


insert into pgmeas_d_intervento_contratto_tipo (int_contratto_tipo_cod,int_contratto_tipo_desc,utente_creazione, utente_modifica) select 'APP_INT','Appalto integrato', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_contratto_tipo (int_contratto_tipo_cod,int_contratto_tipo_desc,utente_creazione, utente_modifica) select 'APP_LAV','Appalto lavori', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_contratto_tipo (int_contratto_tipo_cod,int_contratto_tipo_desc,utente_creazione, utente_modifica) select 'CONC_PPP','Concessione/PPP', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_contratto_tipo (int_contratto_tipo_cod,int_contratto_tipo_desc,utente_creazione, utente_modifica) select 'DON','Donazione', 'ADMIN','ADMIN';


insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'MIG_INC','MIGLIORAMENTO ED INCREMENTO DEL SERVIZIO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'CON_PAT','CONSERVAZIONE DEL PATRIMONIO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'ADE_NOR','ADEGUAMENTO NORMATIVO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'COM_OPE','COMPLETAMENTO D''OPERA', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'VAL_BEN','VALORIZZAZIONE BENI VINCOLATI', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'QUA_URB','QUALITA'' URBANA', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'QUA_AMB','QUALITA'' AMBIENTALE', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'ALTRO','ALTRO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'ADE_TEC','ADEGUAMENTO TECNOLOGICO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_finalita (int_finalita_cod,int_finalita_desc,utente_creazione, utente_modifica) select 'RIS_ENE','RISPARMIO ENERGETICO', 'ADMIN','ADMIN';


insert into pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_cod,int_forma_realizzativa_desc,utente_creazione, utente_modifica) select 'APP','Appalto', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_cod,int_forma_realizzativa_desc,utente_creazione, utente_modifica) select 'APP_INT','Appalto integrato', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_cod,int_forma_realizzativa_desc,utente_creazione, utente_modifica) select 'CONC_LAV','Concessione di lavori', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_cod,int_forma_realizzativa_desc,utente_creazione, utente_modifica) select 'CONTR_PREST_EN','Contratto di prestazione energetica', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_cod,int_forma_realizzativa_desc,utente_creazione, utente_modifica) select 'DON','Donazione', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_forma_realizzativa (int_forma_realizzativa_cod,int_forma_realizzativa_desc,utente_creazione, utente_modifica) select 'PPP','Partenariato Pubblico Privato PPP', 'ADMIN','ADMIN';


insert into pgmeas_d_intervento_obiettivo (int_obiettivo_cod,int_obiettivo_desc,utente_creazione, utente_modifica) select 'AD','ADEGUAMENTO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_obiettivo (int_obiettivo_cod,int_obiettivo_desc,utente_creazione, utente_modifica) select 'CONS','CONSERVAZIONE', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_obiettivo (int_obiettivo_cod,int_obiettivo_desc,utente_creazione, utente_modifica) select 'EFF_EN','EFFICIENTAMENTO ENERGETICO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_obiettivo (int_obiettivo_cod,int_obiettivo_desc,utente_creazione, utente_modifica) select 'POT','POTENZIAMENTO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_obiettivo (int_obiettivo_cod,int_obiettivo_desc,utente_creazione, utente_modifica) select 'RIOR','RIORDINO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_obiettivo (int_obiettivo_cod,int_obiettivo_desc,utente_creazione, utente_modifica) select 'SIC_ANTINCENDIO','SICUREZZA ANTINCENDIO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_obiettivo (int_obiettivo_cod,int_obiettivo_desc,utente_creazione, utente_modifica) select 'SIC_ANTISISMICA','SICUREZZA ANTISISMICA', 'ADMIN','ADMIN';


insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'ANN','ANNULLATO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'APPR','APPROVATO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'COLL','COLLAUDATO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'FIN','FINANZIATO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'IN_CORSO_OPERA','IN CORSO D''OPERA', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'NON_FIN','NON FINANZIATO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'PROP','PROPOSTO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'REV_MINISTERO','REVOCATO DA MINISTERO DELLA SALUTE', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato (int_stato_cod,int_stato_desc,utente_creazione, utente_modifica) select 'REV_REGIONE','REVOCATO DA REGIONE PIEMONTE', 'ADMIN','ADMIN';


insert into pgmeas_d_intervento_stato_progettuale (int_stato_prog_cod,int_stato_prog_desc,utente_creazione, utente_modifica) select 'CAP_PREST_FOR','Capitolato Prestazionale Forniture', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato_progettuale (int_stato_prog_cod,int_stato_prog_desc,utente_creazione, utente_modifica) select 'DIP','DIP, Documento Indirizzo Progettazione', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato_progettuale (int_stato_prog_cod,int_stato_prog_desc,utente_creazione, utente_modifica) select 'DOCFAP','DOCFAP, Documento Fattibilità Progetto', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato_progettuale (int_stato_prog_cod,int_stato_prog_desc,utente_creazione, utente_modifica) select 'PFTE','PFTE, Proposta Finanziaria Tecnico Economica', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_stato_progettuale (int_stato_prog_cod,int_stato_prog_desc,utente_creazione, utente_modifica) select 'PE','Progetto Esecutivo', 'ADMIN','ADMIN';



insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'NUO_COS','NUOVA COSTRUZIONE', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'RECUP','RECUPERO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'RISTR','RISTRUTTURAZIONE', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'RESTA','RESTAURO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'MAN_ORD','MANUTENZIONE ORDINARIA', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'MAN_STRA','MANUTENZIONE STRAORDINARIA', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'COMPL','COMPLETAMENTO', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'AMPL','AMPLIAMENTO/SOPRAELEVAZIONI', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'ACQ_IMM','ACQUISTO DI IMMOBILI', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'DEMO','DEMOLIZIONE', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'ACQ_ATTR','ACQUISTO DI ATTREZZATURE', 'ADMIN','ADMIN';
insert into pgmeas_d_intervento_tipo (int_tipo_cod,int_tipo_desc,utente_creazione, utente_modifica) select 'ALTRO','ALTRO', 'ADMIN','ADMIN';



INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'SOS_STR','SOSTITUZIONE STRUTTURA ESISTENTE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='NUO_COS';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'AGG_STR','AGGIUNTA STRUTTURA ESISTENTE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='NUO_COS';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ALTRO_NC','ALTRO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='NUO_COS';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'NUO_STR','NUOVA STRUTTURA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='NUO_COS';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ANG_EMO','SISTEMA PER ANGIOGRAFIA DIGITALE/EMODINAMICA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ANA_IMM','ANALIZZATORE AUTOMATICO PER IMMUNO CHIMICA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ACC_LIN','ACCELERATORE LINEARE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ANA_MUL','ANALIZZATORE MULTIPARAMETRICO SELETTIVO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ANESTE','ANESTESIA, APPARECCHIO PER',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'BRA_RAD','SISTEMA PER BRACHITERAPIA RADIANTE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'CIR_EXTRA','SISTEMA PER LA CIRCOLAZIONE EXTRACORPOREA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'CICLO','CICLOTRONE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'IPERBA','CAMERA IPERBARICA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ECOTOMO','ECOTOMOGRAFO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'EMODIA','EMODIALISI, APPARECCHIO PER',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'GAMMA_CAM','GAMMA CAMERA COMPUTERIZZATA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'CONTA_GLOBULI','CONTAGLOBULI AUTOMATICO DIFFERENZIALE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'RADIO','GRUPPO RADIOLOGICO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TAC_GAMMA','SISTEMA TAC-GAMMA CAMERA INTEGRATO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'LITO_EXTRA','LITROTITORE EXTRACORPOREO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'MAMMO_DIG','MAMMOGRAFO DIGITALE (DR)',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'LASER_VISTA','SISTEMA LASER PER CORREZIONE VISTA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'MICRO_ELE','MICROSCOPIO ELETTRONICO A TRASMISSIONE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'MONITOR','MONITOR',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'PC_RADIO','PORTATILE PER RADIOSCOPIA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'GAMMA_KNIFE',' SISTEMA INTEGRATO PER RADIOCHIRURGIA STEREOTATTICA (GAMMA KNIFE)',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'RX_DR','DIAGNOSTICA RADIOLOGICA DIGITALE (DR)',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ACC_LIN_RI','ACCELERATORE LINEARE PER RADIOTERAPIA INTRAOPERATORIA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ROBOT_END','SISTEMA ROBOTIZZATO PER CHIRURGIA ENDOSCOPICA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TAC_PET','SISTEMA TAC-PET INTEGRATO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TAC','TOMOGRAFO ASSIALE COMPUTERIZZATO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'HIFU','TERAPIA ONCOLOGICA AD ULTRASUONI (HIFU)',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'SIMU_RADIO','SIMULATORE PER RADIOTERAPIA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TAC_POSI','TOMOGRAFO AD EMISSIONE DI POSITRONI',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TOMO_TERA','SISTEMA PER TOMOTERAPIA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'URTO',' SISTEMA PER TERAPIA AD ONDE D''URTO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TAV_OPE','TAVOLO OPERATORIO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TRMN','TOMOGRAFO A RISONANZA MAGNETICA',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'TAV_TLC','TAVOLO TELECOMANDATO PER APPARECCHIO RADIOLOGICO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'VEN_POL','VENTILATORE POLMONARE',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';
INSERT INTO pgmeas_d_intervento_tipo_det(int_tipo_det_cod,int_tipo_det_desc,int_tipo_id,utente_creazione,utente_modifica)   select 'ALTRO_AT',' ALTRO',int_tipo_id,'ADMIN','ADMIN' from pgmeas_d_intervento_tipo where int_tipo_cod='ACQ_ATTR';

insert into pgmeas_d_organo (organo_cod,organo_desc,utente_creazione, utente_modifica) select 'C ','Consiglio', 'ADMIN','ADMIN';
insert into pgmeas_d_organo (organo_cod,organo_desc,utente_creazione, utente_modifica) select 'G ','Giunta', 'ADMIN','ADMIN';
insert into pgmeas_d_organo (organo_cod,organo_desc,utente_creazione, utente_modifica) select 'R','Regione', 'ADMIN','ADMIN';





INSERT INTO 
  pgmeas.pgmeas_d_piano_triennale
(
  piano_cod,
  paino_desc,
  anno_da,
  anno_a,
  utente_creazione,
  utente_modifica
)
select
'PT-'||lpad(numero_progressivo::text,3,'0'),'<'||anno::text||'-'||annofine::text||'>',
 tb.anno,tb.annofine, 'ADMIN', 'ADMIN'  from (
SELECT anno,  anno+2 annofine,
       ROW_NUMBER() OVER () AS numero_progressivo
FROM generate_series(1994, 2024) AS anni(anno)) as tb;

insert into pgmeas_d_provvedimento_livello (prov_liv_cod,prov_liv_desc,utente_creazione, utente_modifica) select 'ASS_FIN','ASSEGNAZIONE FINANZIAMENTO STATALE / REGIONALE', 'ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_livello (prov_liv_cod,prov_liv_desc,utente_creazione, utente_modifica) select 'ACC_PRO','ACCORDO DI PROGRAMMA', 'ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_livello (prov_liv_cod,prov_liv_desc,utente_creazione, utente_modifica) select 'PRO_ASR_R','PROVVEDIMENTO DI ASSEGNAZIONE REGIONALE', 'ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_livello (prov_liv_cod,prov_liv_desc,utente_creazione, utente_modifica) select 'PRO_OPE','PROVVEDIMENTO OPERATIVO', 'ADMIN','ADMIN';




insert into pgmeas_d_intervento_appalto_tipo (int_appalto_tipo_cod,int_appalto_tipo_desc,utente_creazione,utente_modifica) values
	 ('FOR','Forniture','ADMIN','ADMIN'),
	 ('LAV','Lavori','ADMIN','ADMIN'),
	 ('SER','Servizi','ADMIN','ADMIN');


INSERT INTO 
  pgmeas.pgmeas_t_intervento
(
  int_cod,
  int_titolo,
  int_anno,
  int_cup,
  int_importo,
  int_codic_nsis,
  int_dg_aziendale_approvazione,
  int_dg_aziendale_approvazione_data,
  int_dg_regionale_approvazione,
  int_dg_regionale_approvazione_data,
  int_direttore_generale_cognome,
  int_direttore_generale_nome,
  int_commissario_cognome,
  int_commissario_nome,
  int_referente_pratica_cognome,
  int_referente_pratica_nome,
  int_referente_pratica_telefono,
  int_referente_pratica_email,
  int_forma_realizzativa_id,
  --int_str_tipo_id,
  --quadrante_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select
  '010301_2022_000001' int_cod ,
  'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile',
  2022 int_anno,
  '2022-2EA2022' int_cup,
  10000000 int_importo,
  '2022301NSIS'  int_codic_nsis,
  'DGA_2022_01' int_dg_aziendale_approvazione,
  to_timestamp ('01/03/2022','dd/mm/yyyy') int_dg_aziendale_approvazione_data,
  'DGR_2022_03' int_dg_regionale_approvazione,
  to_timestamp ('20/03/2022','dd/mm/yyyy') int_dg_regionale_approvazione_data,
'ROSSI' int_direttore_generale_cognome,
'CARLO' int_direttore_generale_nome,
'VERDI'  int_commissario_cognome,
'UGO'  int_commissario_nome,
'BIANCHI'  int_referente_pratica_cognome,
'SILVIO'  int_referente_pratica_nome,
'3510000000' int_referente_pratica_telefono,
'silvio.bianchi@aaa.com'   int_referente_pratica_email,
 (select a.int_forma_realizzativa_id from pgmeas.pgmeas_d_intervento_forma_realizzativa a where a.int_forma_realizzativa_cod='APP') int_forma_realizzativa_id,
--( select * from pgmeas_d_intervento_struttura_tipo where int_str_tipo_cod= '')  int_str_tipo_id,
--  ( select * from pgmeas_d_quadrante where quadrante_cod = '') quadrante_id,
--  ( select * from pgmeas_d_intervento_macroarea where int_macroarea_cod = '') int_macroarea_id,
 'MNTPLA70E51L219D',
 'MNTPLA70E51L219D',
  (select ente_id from pgmeas.pgmeas_d_ente a where a.ente_cod_esteso='010301') ente_id;

INSERT INTO 
  pgmeas.pgmeas_r_intervento_appalto_tipo
(
   int_id,
  int_appalto_tipo_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_appalto_tipo_id, a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a, pgmeas.pgmeas_d_intervento_appalto_tipo b
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_appalto_tipo_cod='LAV';

INSERT INTO 
  pgmeas.pgmeas_r_intervento_categoria
(
  int_id,
  int_categoria_id,
 utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_categoria_id,
 a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a, pgmeas.pgmeas_d_intervento_categoria b
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_categoria_cod='D1D1';


INSERT INTO 
  pgmeas.pgmeas_r_intervento_contratto_tipo
( int_id,
  int_contratto_tipo_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_contratto_tipo_id,
 a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a, pgmeas.pgmeas_d_intervento_contratto_tipo b
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_contratto_tipo_cod='APP_LAV';


INSERT INTO 
  pgmeas.pgmeas_r_intervento_finalita
(
  int_id,
  int_finalita_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_finalita_id,
 a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a, pgmeas.pgmeas_d_intervento_finalita b
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_finalita_cod='COM_OPE';


INSERT INTO 
  pgmeas.pgmeas_r_intervento_obiettivo
(
  int_id,
  int_obiettivo_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_obiettivo_id,
 a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a, pgmeas.pgmeas_d_intervento_obiettivo b
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_obiettivo_cod='RIOR';



INSERT INTO 
  pgmeas.pgmeas_r_intervento_stato
(
  int_id,
  int_stato_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_stato_id,
 a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a, pgmeas.pgmeas_d_intervento_stato b
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_stato_cod='APPR';


INSERT INTO 
  pgmeas.pgmeas_r_intervento_stato_progettuale
(
  int_id,
  int_stato_prog_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_stato_prog_id,
 a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a, pgmeas.pgmeas_d_intervento_stato_progettuale b
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_stato_prog_cod='PE';

INSERT INTO 
  pgmeas.pgmeas_r_intervento_tipo
(
  int_id,
  int_tipo_id,
  int_tipo_det_id,
  utente_creazione,
  utente_modifica,
  ente_id
)
select a.int_id, b.int_tipo_id, NULL,
 a.utente_creazione, a.utente_creazione, a.ente_id from pgmeas.pgmeas_t_intervento a 
 join  pgmeas.pgmeas_d_intervento_tipo b on 1=1 
left join pgmeas.pgmeas_d_intervento_tipo_det c on c.int_tipo_id=b.int_tipo_id
where a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile' and b.int_tipo_cod='COMPL'
--and c.int_tipo_det_cod=''
;
 


INSERT INTO 
  pgmeas.pgmeas_d_classif_ts_tipo
(
  classif_ts_tipo_cod,
  classif_ts_tipo_desc,
  utente_creazione,
  utente_modifica
)
select 'QE','Quadro economico','ADMIN','ADMIN';

INSERT INTO 
  pgmeas.pgmeas_d_classif_ts_tipo
(
  classif_ts_tipo_cod,
  classif_ts_tipo_desc,
  utente_creazione,
  utente_modifica
)
select 'IE','Intervento edilizioo','ADMIN','ADMIN';


INSERT INTO 
  pgmeas.pgmeas_d_classif_ts
(
  classif_ts_cod,
  classif_ts_desc,
  classif_ts_tipo_id,
  utente_creazione,
  utente_modifica
)
select 'QE_2023','QUADRO ECONOMICO (ex Allegato I.7 D. Lgs. n. 36/2023)_2023', (select classif_ts_tipo_id from pgmeas.pgmeas_d_classif_ts_tipo where classif_ts_tipo_cod='QE'), 'ADMIN','ADMIN';

INSERT INTO 
  pgmeas.pgmeas_d_classif_ts
(
  classif_ts_cod,
  classif_ts_desc,
  classif_ts_tipo_id,
  utente_creazione,
  utente_modifica
)select 'QE_2024','QUADRO ECONOMICO (ex Allegato I.7 D. Lgs. n. 36/2023)_2024',(select classif_ts_tipo_id from  pgmeas.pgmeas_d_classif_ts_tipo where classif_ts_tipo_cod='QE'), 'ADMIN','ADMIN';

INSERT INTO 
  pgmeas.pgmeas_d_classif_ts
(
  classif_ts_cod,
  classif_ts_desc,
  classif_ts_tipo_id,
  utente_creazione,
  utente_modifica
)select 'QE_2025','QUADRO ECONOMICO (ex Allegato I.7 D. Lgs. n. 36/2023)_2025',(select classif_ts_tipo_id from  pgmeas.pgmeas_d_classif_ts_tipo where classif_ts_tipo_cod='QE'), 'ADMIN','ADMIN';



INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_A','a) Lavori', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_A1','a1) a corpo ', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_A2','a2) a misura', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_B','b) Costi della sicurezza non soggetti a ribasso d''asta', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_C','c) Importo relativo all''aliquota per l''attuazione di misure volte alla prevenzione e repressione della criminalità e tentativi di infiltrazione mafiosa di cui all''articolo 204, comma 6, lettera e), del codice, non soggetto a ribasso', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_D','d) Opere di mitigazione e di compensazione dell''impatto ambientale e sociale, nel limite dell''importo del 2 per cento del costo complessivo dell''opera; costi per il monitoraggio ambientale', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_D1','d1) opere di mitigazione e di compensazione dell’impatto ambientale e sociale, nel limite di importo del 2 per cento del costo complessivo dell’opera', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_D2','d2) costi per il monitoraggio ambientale', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E','e) Somme a disposizione della Stazione Appaltante', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E1','e1) lavori in amministrazione diretta previsti in progetto ed esclusi dall’appalto, ivi inclusi i rimborsi previa fattura', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E2','e2) rilievi, accertamenti e indagini da eseguire ai diversi livelli di progettazione a cura della stazione appaltante', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E3','e3) rilievi, accertamenti e indagini da eseguire ai diversi livelli di progettazione a cura del progettista', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E4','e4) allacciamenti ai pubblici servizi e superamento eventuali interferenze', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E5','e5) imprevisti', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E6','e6) accantonamenti in relazione alle modifiche di cui agli articoli 60 e 120, comma 1, lettera a), del codice', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E7','e7) acquisizione aree o immobili, indennizzi', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E8','e8) spese tecniche relative alla progettazione, alle attività preliminari, ivi compreso l’eventuale monitoraggio di parametri necessari ai fini della progettazione ove pertinente, al coordinamento della sicurezza in fase di progettazione, alle conferenze dei servizi, alla direzione lavori e al coordinamento della sicurezza in fase di esecuzione, all’assistenza giornaliera e contabilità, all’incentivo di cui all’articolo 45 del codice, nella misura corrispondente alle prestazioni che dovranno essere svolte dal personale dipendente', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E9','e9) spese per attività tecnico-amministrative e strumentali connesse alla progettazione, di supporto al RUP qualora si tratti di personale dipendente, di assicurazione dei progettisti qualora dipendenti dell’amministrazione, ai sensi dell’articolo 2, comma 4, del codice nonché per la verifica preventiva della progettazione ai sensi dell’articolo 42 del codice', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E10','e10) spese di cui all’articolo 45, commi 6 e 7, del codice', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E11','e11) eventuali spese per commissioni giudicatrici', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E12','e12) spese per pubblicità', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E13','e13) spese per prove di laboratorio, accertamenti e verifiche tecniche obbligatorie o specificamente previste dal capitolato speciale d’appalto, di cui all’articolo 116 comma 11, del codice, nonché per l’eventuale monitoraggio successivo alla realizzazione dell’opera, ove prescritto', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E14','e14) spese per collaudo tecnico-amministrativo, collaudo statico e altri eventuali collaudi specialistici', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E15','e15) spese per la verifica preventiva dell’interesse archeologico, di cui all''articolo 41, comma 4, del codice', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E16','e16) spese per i rimedi alternativi alla tutela giurisdizionale', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E17','e17) nei casi in cui sono previste, spese per le opere artistiche di cui alla legge 20 luglio 1949, n. 717', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E18','e18) IVA ed eventuali altre imposte', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_E19','e19) IVA sui Lavori (10%)', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_F','f) Arredi e Attrezzature', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_F1','f1) Arredi', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_F2','f2) Attrezzature', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_G','g) Ribasso di gara', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_G1','g1) Ribasso su lavori', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_G2','g2) IVA ribasso lavori (10%)', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_G3','g3) Ribasso su fornitura', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_G4','g4) IVA ribasso fornitura (22%)', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_G5','g5) Ribasso su progettazione', 'ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,utente_creazione,utente_modifica) select 'QE_G6','g6) IVA Ribasso su progettazione (22%)', 'ADMIN','ADMIN';


INSERT INTO 
  pgmeas.pgmeas_d_classif_tree
(
 /* qe_ts_id,
  qe_elem_id,
  qe_elem_id_padre,
  qe_elem_livello,
  qe_elem_ordine,*/
 classif_ts_id,
  classif_id,
  classif_id_padre,
  classif_tree_livello,
  classif_tree_ordine,
  utente_creazione,
  utente_modifica
)
select tb.classif_ts_id, tb.classif_id,tb.classif_id_padre, case when tb.classif_id_padre is null then 1 else 2 end classif_tree_livello, tb.ordine
--,tb.qe_elem_cod, tb.qe_elem_desc  
,'ADMIN', 'ADMIN'
from (
select a.classif_ts_id,b.classif_id, (select z.classif_id from pgmeas.pgmeas_d_classif_elem z where z.classif_cod<>b.classif_cod and z.classif_cod=substring(b.classif_cod,1,4) )  classif_id_padre
, row_number() over () ordine, b.classif_cod, b.classif_desc
 from pgmeas.pgmeas_d_classif_ts a, pgmeas.pgmeas_d_classif_elem b
where a.classif_ts_cod='QE_2024' order by b.classif_id
) as tb;




INSERT INTO pgmeas_d_finanziamento_tipo_det (fin_tipo_det_cod, fin_tipo_det_desc, 
fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro)
select '00012CC', 'CONTO CAPITALE REGIONALE', 
(select fin_tipo_id from pgmeas_d_finanziamento_tipo a where a.fin_tipo_cod='R'),'ADMIN', 'ADMIN', 0,100,0;


INSERT INTO pgmeas_d_finanziamento_tipo_det (fin_tipo_det_cod, fin_tipo_det_desc, 
fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro)
select '00002DO', 'DONAZIONE', 
(select fin_tipo_id from pgmeas_d_finanziamento_tipo a where a.fin_tipo_cod='A'),'ADMIN', 'ADMIN', 0,0,100;


INSERT INTO pgmeas_d_finanziamento_tipo_det (fin_tipo_det_cod, fin_tipo_det_desc, 
fin_tipo_id, utente_creazione, utente_modifica, fin_tipo_det_percentuale_stato, fin_tipo_det_percentuale_regione, fin_tipo_det_percentuale_altro)
select '0001F1', 'ART. 20 L. n. 67/1988 I^ Fase (C.D.P.)', 
(select fin_tipo_id from pgmeas_d_finanziamento_tipo a where a.fin_tipo_cod='S'),'ADMIN', 'ADMIN', 95,5,0;





INSERT INTO pgmeas_t_intervento_previsione_spesa ( int_prev_spesa_anno, int_prev_spesa_importo, int_id, piano_id, utente_creazione, utente_modifica, ente_id) 
select 2022,2000000, a.int_id , b.piano_id,'ADMIN', 'ADMIN', a.ente_id  from pgmeas_d_piano_triennale b,pgmeas.pgmeas_t_intervento a where b.piano_cod ='PT-029'
and  a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile';

INSERT INTO pgmeas_t_intervento_previsione_spesa ( int_prev_spesa_anno, int_prev_spesa_importo, int_id, piano_id, utente_creazione, utente_modifica, ente_id) 
select 2023,2000000, a.int_id , b.piano_id,'ADMIN', 'ADMIN', a.ente_id  from pgmeas_d_piano_triennale b,pgmeas.pgmeas_t_intervento a where b.piano_cod ='PT-029'
and  a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile';


INSERT INTO pgmeas_t_intervento_previsione_spesa ( int_prev_spesa_anno, int_prev_spesa_importo, int_id, piano_id, utente_creazione, utente_modifica, ente_id) 
select 2024,2000000, a.int_id , b.piano_id,'ADMIN', 'ADMIN', a.ente_id  from pgmeas_d_piano_triennale b,pgmeas.pgmeas_t_intervento a where b.piano_cod ='PT-029'
and  a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile';


INSERT INTO pgmeas_t_intervento_previsione_spesa ( int_prev_spesa_anno, int_prev_spesa_importo, int_id, piano_id, utente_creazione, utente_modifica, ente_id) 
select 2025,2000000, a.int_id , b.piano_id,'ADMIN', 'ADMIN', a.ente_id  from pgmeas_d_piano_triennale b,pgmeas.pgmeas_t_intervento a where b.piano_cod ='PT-030'
and  a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile';


INSERT INTO pgmeas_t_intervento_previsione_spesa ( int_prev_spesa_anno, int_prev_spesa_importo, int_id, piano_id, utente_creazione, utente_modifica, ente_id) 
select 2026,2000000, a.int_id , b.piano_id,'ADMIN', 'ADMIN', a.ente_id  from pgmeas_d_piano_triennale b,pgmeas.pgmeas_t_intervento a where b.piano_cod ='PT-031'
and  a.int_titolo = 'P.S. V.VALLETTA - Completamento e riordino del presidio materno infantile';

insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_A','a) interventi di manutenzione ordinaria','gli interventi edilizi che riguardano le opere di riparazione, rinnovamento e sostituzione delle finiture degli edifici e quelle necessarie ad integrare o mantenere in efficienza gli impianti tecnologici esistenti','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_B','b) interventi di manutenzione straordinaria','le opere e le modifiche necessarie per rinnovare e sostituire parti anche strutturali degli edifici, nonché per realizzare ed integrare i servizi igienico-sanitari e tecnologici, sempre che non alterino la volumetria complessiva degli edifici e non comportino mutamenti urbanisticamente rilevanti delle destinazioni d’uso implicanti incremento del carico urbanistico. Nell''ambito degli interventi di manutenzione straordinaria sono ricompresi anche quelli consistenti nel frazionamento o accorpamento delle unità immobiliari con esecuzione di opere anche se comportanti la variazione delle superfici delle singole unità immobiliari nonché del carico urbanistico purché non sia modificata la volumetria complessiva degli edifici e si mantenga l''originaria destinazione d''uso. Nell’ambito degli interventi di manutenzione straordinaria sono comprese anche le modifiche ai prospetti degli edifici legittimamente realizzati necessarie per mantenere o acquisire l’agibilità dell’edificio ovvero per l’accesso allo stesso, che non pregiudichino il decoro architettonico dell’edificio, purché l’intervento risulti conforme alla vigente disciplina urbanistica ed edilizia e non abbia ad oggetto immobili sottoposti a tutela ai sensi del Codice dei beni culturali e del paesaggio di cui al decreto legislativo 22 gennaio 2004, n. 42;
(lettera così modificata dall''art. 10, comma 1, lettera b), della legge n. 120 del 2020)','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_C','c) interventi di restauro e di risanamento conservativo','gli interventi edilizi rivolti a conservare l''organismo edilizio e ad assicurarne la funzionalità mediante un insieme sistematico di opere che, nel rispetto degli elementi tipologici, formali e strutturali dell''organismo stesso, ne consentano anche il mutamento delle destinazioni d''uso purché con tali elementi compatibili, nonché conformi a quelle previste dallo strumento urbanistico generale e dai relativi piani attuativi. Tali interventi comprendono il consolidamento, il ripristino e il rinnovo degli elementi costitutivi dell''edificio, l''inserimento degli elementi accessori e degli impianti richiesti dalle esigenze dell''uso, l''eliminazione degli elementi estranei all''organismo edilizio;
(lettera modificata dall''art. 65-bis della legge n. 96 del 2017)','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_D','d) interventi di ristrutturazione edilizia','gli interventi rivolti a trasformare gli organismi edilizi mediante un insieme sistematico di opere che possono portare ad un organismo edilizio in tutto o in parte diverso dal precedente. Tali interventi comprendono il ripristino o la sostituzione di alcuni elementi costitutivi dell''edificio, l’eliminazione, la modifica e l''inserimento di nuovi elementi ed impianti. Nell’ambito degli interventi di ristrutturazione edilizia sono ricompresi altresì gli interventi di demolizione e ricostruzione di edifici esistenti con diversi sagoma, prospetti, sedime e caratteristiche planivolumetriche e tipologiche, con le innovazioni necessarie per l’adeguamento alla normativa antisismica, per l’applicazione della normativa sull’accessibilità, per l’istallazione di impianti tecnologici e per l’efficientamento energetico. L’intervento può prevedere altresì, nei soli casi espressamente previsti dalla legislazione vigente o dagli strumenti urbanistici comunali, incrementi di volumetria anche per promuovere interventi di rigenerazione urbana. Costituiscono inoltre ristrutturazione edilizia gli interventi volti al ripristino di edifici, o parti di essi, eventualmente crollati o demoliti, attraverso la loro ricostruzione, purché sia possibile accertarne la preesistente consistenza. Rimane fermo che, con riferimento agli immobili sottoposti a tutela ai sensi del Codice dei beni culturali e del paesaggio di cui al decreto legislativo 22 gennaio 2004, n. 42, ad eccezione degli edifici situati in aree tutelate ai sensi degli articoli 136, comma 1, lettere c) e d), e 142 del medesimo decreto legislativo, nonché, fatte salve le previsioni legislative e degli strumenti urbanistici, a quelli ubicati nelle zone omogenee A di cui al decreto del Ministro per i lavori pubblici 2 aprile 1968, n. 1444, o in zone a queste assimilabili in base alla normativa regionale e ai piani urbanistici comunali, nei centri e nuclei storici consolidati e negli ulteriori ambiti di particolare pregio storico e architettonico, gli interventi di demolizione e ricostruzione e gli interventi di ripristino di edifici crollati o demoliti costituiscono interventi di ristrutturazione edilizia soltanto ove siano mantenuti sagoma, prospetti, sedime e caratteristiche planivolumetriche e tipologiche dell’edificio preesistente e non siano previsti incrementi di volumetria;
(lettera modificata dall''art. 10, comma 1, lettera b), della legge n. 120 del 2020, poi dall''art. 28, comma 5-bis, lettera a), legge n. 34 del 2022, poi dall''art. 14, comma 1-ter, legge n. 91 del 2022)','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E','e) interventi di nuova costruzione','quelli di trasformazione edilizia e urbanistica del territorio non rientranti nelle categorie definite alle lettere precedenti','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E1','e.1) la costruzione di manufatti edilizi fuori terra o interrati, ovvero l''ampliamento di quelli esistenti all''esterno della sagoma esistente, fermo restando, per gli interventi pertinenziali, quanto previsto alla lettera e.6)','','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E2','e.2) gli interventi di urbanizzazione primaria e secondaria realizzati da soggetti diversi dal Comune','','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E3','e.3) la realizzazione di infrastrutture e di impianti, anche per pubblici servizi, che comporti la trasformazione in via permanente di suolo inedificato','','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E4','e.4) l’installazione di torri e tralicci per impianti radio-ricetrasmittenti e di ripetitori per i servizi di telecomunicazione;','(punto da ritenersi abrogato implicitamente dagli artt. 87 e segg. del d.lgs. n. 259 del 2003)','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E5','e.5) l’installazione di manufatti leggeri, anche prefabbricati, e di strutture di qualsiasi genere, quali roulotte, camper, case mobili, imbarcazioni, che siano utilizzati come abitazioni, ambienti di lavoro, oppure come depositi, magazzini e simili, ad eccezione di quelli che siano diretti a soddisfare esigenze meramente temporanee o delle tende e delle unità abitative mobili con meccanismi di rotazione in funzione, e loro pertinenze e accessori, che siano collocate, anche in via continuativa, in strutture ricettive all’aperto per la sosta e il soggiorno dei turisti previamente autorizzate sotto il profilo urbanistico, edilizio e, ove previsto, paesaggistico, che non posseggano alcun collegamento di natura permanente al terreno e presentino le caratteristiche dimensionali e tecnico-costruttive previste dalle normative regionali di settore ove esistenti;',' (punto sostituito dall''art. 10, comma 1, lettera b), della legge n. 120 del 2020)','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E6','e.6) gli interventi pertinenziali che le norme tecniche degli strumenti urbanistici, in relazione alla zonizzazione e al pregio ambientale e paesaggistico delle aree, qualifichino come interventi di nuova costruzione, ovvero che comportino la realizzazione di un volume superiore al 20% del volume dell’edificio principale','','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_E7','e.7) la realizzazione di depositi di merci o di materiali, la realizzazione di impianti per attività produttive all''aperto ove comportino l''esecuzione di lavori cui consegua la trasformazione permanente del suolo inedificato',' quelli rivolti a sostituire l''esistente tessuto urbanistico-edilizio con altro diverso, mediante un insieme sistematico di interventi edilizi, anche con la modificazione del disegno dei lotti, degli isolati e della rete stradale.','ADMIN','ADMIN';
insert into pgmeas_d_classif_elem(classif_cod, classif_desc, classif_desc_estesa, utente_creazione, utente_modifica) SELECT 'IE_F','f) interventi di ristrutturazione urbanistica','','ADMIN','ADMIN';


INSERT INTO 
  pgmeas.pgmeas_d_classif_tree
(
 classif_ts_id,
  classif_id,
  classif_id_padre,
  classif_tree_livello,
  classif_tree_ordine,
  utente_creazione,
  utente_modifica
)
select tb.classif_ts_id, tb.classif_id,tb.classif_id_padre, case when tb.classif_id_padre is null then 1 else 2 end classif_tree_livello, row_number() over () ordine
,'ADMIN', 'ADMIN'
from (
select a.classif_ts_id,b.classif_id, (select z.classif_id from pgmeas.pgmeas_d_classif_elem z where z.classif_cod<>b.classif_cod and z.classif_cod=substring(b.classif_cod,1,4) )  classif_id_padre
, row_number() over () ordine, b.classif_cod, b.classif_desc
 from pgmeas.pgmeas_d_classif_ts a, pgmeas.pgmeas_d_classif_elem b
where a.classif_ts_cod='IE_2024' and b.classif_cod like 'IE%' order by b.classif_id
) as tb;



insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 
	 SELECT 'ACCORDO DI PROGRAMMA',(select organo_id from pgmeas_d_organo where organo_cod='C'),'AP','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'ATTO DI LIQUIDAZIONE',(select organo_id from pgmeas_d_organo where organo_cod='R'),'AL','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'ATTO DIRIGENZIALE',(select organo_id from pgmeas_d_organo where organo_cod='C'),'AD','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'COMUNICAZIONE CEE',(select organo_id from pgmeas_d_organo where organo_cod='C'),'CE','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECISIONE CEE',(select organo_id from pgmeas_d_organo where organo_cod='C'),'EE','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO ASSESSORILE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DA','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO DEL CAPO PROVVISORIO DELLO STATO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'CP','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO DEL PRESIDENTE DEL CONSIGLIO DEI MINISTRI',(select organo_id from pgmeas_d_organo where organo_cod='G'),'CM','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO DEL PRESIDENTE DELLA REPUBBLICA',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DT','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO LEGGE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DR','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO LEGGE LUOGOTENENZIALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LL','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO LEGISLATIVO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LG','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO LEGISLATIVO DEL CAPO PROVVISORIO DELLO STATO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'PP','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO LEGISLATIVO LUOGOTENEZIALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'GT','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO LEGISLATIVO PRESIDENZIALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'GP','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO LUOGOTENENZIALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LT','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO MINISTERIALE',(select organo_id from pgmeas_d_organo where organo_cod='C'),'DM','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DECRETO PRESIDENTE GIUNTA',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DP','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DELIBERA UFFICIO PRESIDENZA CONSIGLIO REGIONALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DU','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DELIBERAZIONE C.I.P.E',(select organo_id from pgmeas_d_organo where organo_cod='G'),'PE','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DELIBERAZIONE CONSIGLIO REGIONALE',(select organo_id from pgmeas_d_organo where organo_cod='C'),'DC','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DELIBERAZIONE GIUNTA REGIONALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DG','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DISEGNO DI LEGGE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DL','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'DISEGNO LEGGE FINANZIARIA',(select organo_id from pgmeas_d_organo where organo_cod='G'),'DF','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LE','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE COSTITUZIONALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LC','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE DI APPROVAZIONE BILANCIO',(select organo_id from pgmeas_d_organo where organo_cod='C'),'LA','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE DI BILANCIO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LB','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE DI VARIAZIONE DI BILANCIO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LV','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE FINANZIARIA',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LF','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE PROVINCIALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LP','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'LEGGE REGIONALE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'LR','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'ORDINANZA',(select organo_id from pgmeas_d_organo where organo_cod='G'),'OR','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'REGIO DECRETO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'RD','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'REGIO DECRETO LEGGE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'RL','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'REGIO DECRETO LEGISLATIVO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'RG','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'REGOLAMENTO CEE',(select organo_id from pgmeas_d_organo where organo_cod='G'),'RE','ADMIN','ADMIN';
insert into pgmeas_d_provvedimento_tipo (  prov_tipo_desc, organo_id, prov_tipo_cod, utente_creazione, utente_modifica) 	 SELECT 'TRATTATO',(select organo_id from pgmeas_d_organo where organo_cod='G'),'TR','ADMIN','ADMIN';


INSERT INTO pgmeas_d_quadrante ( quadrante_cod, quadrante_desc,utente_creazione, utente_modifica) select 'QUADRANTE_METRO','Quadrante METROPOLITANO (Città Metropolitana di Torino)', 'ADMIN', 'ADMIN';
INSERT INTO pgmeas_d_quadrante ( quadrante_cod, quadrante_desc,utente_creazione, utente_modifica) select 'QUADRANTE_NORD_EST','Quadrante NORD-EST (Province di Biella, Novara, V.C.O., Vercelli)', 'ADMIN', 'ADMIN';
INSERT INTO pgmeas_d_quadrante ( quadrante_cod, quadrante_desc,utente_creazione, utente_modifica) select 'QUADRANTE_SUD_EST','Quadrante SUD-EST (Province di Alessandria, Asti)', 'ADMIN', 'ADMIN';
INSERT INTO pgmeas_d_quadrante ( quadrante_cod, quadrante_desc,utente_creazione, utente_modifica) select 'QUADRANTE_SUD_OVEST','Quadrante SUD-OVEST (Provincia di Cuneo)', 'ADMIN', 'ADMIN';



INSERT INTO pgmeas.pgmeas_d_classif_ts_tipo(classif_ts_tipo_cod, classif_ts_tipo_desc, utente_creazione, utente_modifica)
select 'DA','Dichiarazione appaltabilità','ADMIN','ADMIN';


INSERT INTO pgmeas.pgmeas_d_classif_ts (classif_ts_cod,classif_ts_desc,classif_ts_tipo_id,utente_creazione,utente_modifica)
select 'DA_2023','DICHIARAZIONE APPALTABILITA'' 2023', (select classif_ts_tipo_id from pgmeas.pgmeas_d_classif_ts_tipo where classif_ts_tipo_cod='DA'), 'ADMIN','ADMIN';

INSERT INTO pgmeas.pgmeas_d_classif_ts (classif_ts_cod,classif_ts_desc,classif_ts_tipo_id,utente_creazione,utente_modifica)
select 'DA_2024','DICHIARAZIONE APPALTABILITA'' 2024', (select classif_ts_tipo_id from pgmeas.pgmeas_d_classif_ts_tipo where classif_ts_tipo_cod='DA'), 'ADMIN','ADMIN';

INSERT INTO pgmeas.pgmeas_d_classif_ts (classif_ts_cod,classif_ts_desc,classif_ts_tipo_id,utente_creazione,utente_modifica)
select 'DA_2025','DICHIARAZIONE APPALTABILITA'' 2025', (select classif_ts_tipo_id from pgmeas.pgmeas_d_classif_ts_tipo where classif_ts_tipo_cod='DA'), 'ADMIN','ADMIN';



INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_A','a) Il progetto è completo di tutti gli elaborati tecnici','a) Il progetto è completo di tutti gli elaborati tecnici, degli elementi e particolari costruttivi necessari per l''esecuzione dell''opera compresi autorizzazioni, pareri e nulla-osta, ed è immediatamente cantierabile ai sensi della normativa vigente statale e regionale in materia di lavori, servizi e forniture pubbliche','ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_B','b) il progetto è redatto nel rispetto delle normative vigenti','b) il progetto è redatto nel rispetto delle normative vigenti in materia: statale e regionale di lavori, servizi e forniture pubbliche, di requisiti strutturali, tecnologici ed organizzativi, di cui al DPR 14/01/1997 recepito dalla Regione Piemonte con D.C.R. n° 616 del 22/02/2000 e s.m.i.','ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_C','c) il progetto è conforme alla scheda di prefattibilità','c) il progetto è conforme alla scheda di prefattibilità presente nell''applicativo informatico regionale di programmazione, gestione e monitoraggio degli investimenti in sanità','ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_D','d) il costo dell''intervento è congruo','d) il costo dell''intervento è congruo ed è dotato della copertura finanziaria come risulta dal provvedimento aziendale di approvazione del progetto','ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_E','e) l''intervento previsto risulta essere funzionale e funzionante','e) l''intervento previsto risulta essere funzionale e funzionante e comprende gli eventuali arredi e/o attrezzature sanitarie','ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_ARR_ATTR','In ordine alle acquisizioni di arredi e di attrezzature sanitarie si dichiara che','In ordine alle acquisizioni di arredi e di attrezzature sanitarie si dichiara che','ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_ARR_ATTR_A','a) la fornitura avviene sulla base di specifico capitolato/scheda','a) la fornitura avviene sulla base di specifico capitolato/scheda prestazionale corredato di/della/e apparecchiature, da un elenco sintetico dei costi presunti, da una descrizione delle eventuali opere edili ed impiantistiche di adeguamento necessarie per l''installazione, così come specificato nelle "Schede di prefattibilità"','ADMIN','ADMIN';
INSERT INTO pgmeas.pgmeas_d_classif_elem (classif_cod,classif_desc,classif_desc_estesa,utente_creazione,utente_modifica) select 'DA_ARR_ATTR_B','b) Attrezzatura/e validata/e con parere della commissione tecnica acquisito in data','b) Attrezzatura/e validata/e con parere della commissione tecnica acquisito in data','ADMIN','ADMIN';

INSERT INTO 
  pgmeas.pgmeas_d_classif_tree
(
 classif_ts_id,
  classif_id,
  classif_id_padre,
  classif_tree_livello,
  classif_tree_ordine,
  utente_creazione,
  utente_modifica
)
select tb.classif_ts_id, tb.classif_id,tb.classif_id_padre, case when tb.classif_id_padre is null then 1 else 2 end classif_tree_livello, tb.ordine
,'ADMIN', 'ADMIN'--, length(tb.classif_cod),tb.classif_cod
from (
select a.classif_ts_id,b.classif_id, 
(
select z.classif_id from pgmeas.pgmeas_d_classif_elem z where
z.classif_cod<>b.classif_cod and 
(
(z.classif_cod=substring(b.classif_cod,1,3) and length(b.classif_cod)>3 ) 
or 
(z.classif_cod=substring(b.classif_cod,1,11) and  length(b.classif_cod)>11)
)
)classif_id_padre
, row_number() over () ordine, b.classif_cod, b.classif_desc
 from pgmeas.pgmeas_d_classif_ts a, pgmeas.pgmeas_d_classif_elem b
where
a.classif_ts_cod='DA_2024' and b.classif_cod in (
'DA_A',
'DA_B',
'DA_C',
'DA_D',
'DA_E',
'DA_ARR_ATTR',
'DA_ARR_ATTR_A',
'DA_ARR_ATTR_B'
) order by b.classif_id
) as tb;


--12/06/2024
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo) 
select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 I^ Fase (C.D.P.)' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 I^ Fase (C.D.P.)' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 II^ Fase DCR 440 L 450-97' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 II^ Fase DCR 440 L 450-97' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 II^ Fase ACCORDO 2000 DCR 10-2005 ' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 II^ Fase ACCORDO 2000 DCR 10-2005 ' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 II^ Fase ACCORDO 2000 DCR 10-2005 (35%) ' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 II^ Fase ACCORDO 2000 DCR 10-2005 (35%) ' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2008' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2008' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2018' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2018' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2022' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 III^ Fase DCR 131-2007 ACCORDO 2022' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 ACCORDO 2023' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 ACCORDO 2023' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 ACCORDO 2024' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 ACCORDO 2024' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 NUOVI INTERVENTI IN PROGRAMMAZIONE' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 NUOVI INTERVENTI IN PROGRAMMAZIONE' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 DGR 2-3900/2016' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 20 L. n. 67/1988 DGR 2-3900/2016' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='D.L. 18/2020 – INTERVENTI URGENTI COVID-19' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='D.L. 18/2020 – INTERVENTI URGENTI COVID-19' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 5 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='INTRAMOENIA D. LGS. 254-2000' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 95 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='INTRAMOENIA D. LGS. 254-2000' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='OPERE OLIMPICHE – L. n. 285-2000 ' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L. n. 135/1990 AIDS' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L. n. 145 ART. 1 C. 95 INTERVENTI ANTINCENDIO/ANTISISMICA' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L. n. 160 ART. 1 C.14 EFFICIENTAMENTO ENERGETICO' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L. n. 178 ART.1 C. 445 OSSIGENO MEDICALE' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='HOSPICE' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='RADIOTERAPIA' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , null from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 71 L. n. 448/1988' and b.fin_imp_tipo_cod='IMP_STATO' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , null from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='ART. 71 L. n. 448/1988' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Altri Fondi Regionali' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Radiofarmaci' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Conto Capitale Regionale' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Fondi Regionali GSA' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Fondi Regionali derivanti da sanzioni amministrative' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L.R. N. 24/2000' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L.R. N. 25/2000' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L.R. N. 2/2003' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L.R. 55 del 01/09/1997' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='L.R. 40 del 03/07/1996' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Anticipazioni Alienazioni' and b.fin_imp_tipo_cod='IMP_REGIONE' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Alienazioni' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Altro' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='PPP' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Donazioni' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='Fondi aziendali' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='MUTUO' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='SPONSORIZZAZIONI' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='SOCIETA'' PARTECIPATE O DI SCOPO' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='FINANZA DI PROGETTO' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='CONCESSIONE DI COSTRUZIONE E GESTIONE' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='CAPITALE PRIVATO' and b.fin_imp_tipo_cod='IMP_AZ' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
INSERT INTO pgmeas_r_finanziamento_importo_tipo_det ( fin_tipo_det_id, fin_imp_tipo_id, utente_creazione, utente_modifica, ente_id, fin_percentuale_importo)  select a.fin_tipo_det_id, b.fin_imp_tipo_id, a.utente_creazione, a.utente_modifica, c.ente_id , 100 from pgmeas_d_finanziamento_tipo_det a, pgmeas_d_finanziamento_importo_tipo b, pgmeas_d_ente c where  a.fin_tipo_det_desc='INAIL NUOVI INTERVENTI' and b.fin_imp_tipo_cod='IMP_INAIL' and not exists (select 1 from pgmeas_r_finanziamento_importo_tipo_det where fin_tipo_det_id=a.fin_tipo_det_id and fin_imp_tipo_id=b.fin_imp_tipo_id and ente_id=c.ente_id);
	

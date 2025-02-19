-- Caricamento Utenti Super Admin
delete from utente;
ALTER TABLE utente AUTO_INCREMENT = 1;
INSERT INTO utente (`username`, `cognome`, `nome`, `lingua`, `data_inizio_validita`, `username_creazione`, `email`) VALUES ('DPNDME73A02L219B', 'Dipendente Uno', 'Demo', 'IT', '2022-01-01', 'SQL Script', 'demo.dipendente1@csi.it');
INSERT INTO utente (`username`, `cognome`, `nome`, `lingua`, `data_inizio_validita`, `username_creazione`, `email`) VALUES ('DPNDME73A03L219D', 'Dipendente Due', 'Demo', 'IT', '2022-01-01', 'SQL Script', 'demo.dipendente2@csi.it');
INSERT INTO utente (`username`, `cognome`, `nome`, `lingua`, `data_inizio_validita`, `username_creazione`, `email`) VALUES ('DPNDME73A04L219F', 'Dipendente Tre', 'Demo', 'IT', '2022-01-01', 'SQL Script', 'demo.dipendente3@csi.it');


INSERT INTO direzione_utente_ruolo (id_applicazione, id_utente, id_direzione, id_ruolo, flag_direzione_default, data_inizio_validita, username_creazione) 
VALUES (1, 
(select a.id_utente    from utente a   where a.username     = 'DPNDME73A02L219B'),
(select a.id_direzione from direzione a where a.descrizione = 'Direzione Generica' or a.id_direzione = 0),
(select a.id_ruolo     from ruolo a     where a.codice      = 'SUPER_ADMIN'),
1, '2022-01-01', 'ScriptSQL');

INSERT INTO direzione_utente_ruolo (id_applicazione, id_utente, id_direzione, id_ruolo, flag_direzione_default, data_inizio_validita, username_creazione) 
VALUES (1, 
(select a.id_utente    from utente a   where a.username     = 'DPNDME73A03L219D'),
(select a.id_direzione from direzione a where a.descrizione = 'Direzione Generica' or a.id_direzione = 0),
(select a.id_ruolo     from ruolo a     where a.codice      = 'SUPER_ADMIN'),
1, '2022-01-01', 'ScriptSQL');

INSERT INTO direzione_utente_ruolo (id_applicazione, id_utente, id_direzione, id_ruolo, flag_direzione_default, data_inizio_validita, username_creazione) 
VALUES (1, 
(select a.id_utente    from utente a   where a.username     = 'DPNDME73A04L219F'),
(select a.id_direzione from direzione a where a.descrizione = 'Direzione Generica' or a.id_direzione = 0),
(select a.id_ruolo     from ruolo a     where a.codice      = 'SUPER_ADMIN'),
1, '2022-01-01', 'ScriptSQL');




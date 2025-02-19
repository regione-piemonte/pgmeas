DELETE FROM direzione;
ALTER TABLE direzione AUTO_INCREMENT = 1;

DELETE FROM ente;
ALTER TABLE ente AUTO_INCREMENT = 1;

INSERT INTO ente(id_ente, descrizione, abilitato, id_immagine, data_inizio_validita, data_creazione, username_creazione, id_tipologia_ente)
SELECT id, nome, 1, idimmagine, '2022-01-01', CURRENT_TIMESTAMP(), 'scriptSQL', 1 FROM GMF_ente;
INSERT INTO ente (descrizione, abilitato, data_inizio_validita, username_creazione, id_tipologia_ente) VALUES ('Ente Generico', '1', '2022-01-01', 'scriptSql', 1);
update ente set id_ente = 0 where descrizione = 'Ente Generico';

INSERT INTO direzione(id_direzione, descrizione, data_inizio_validita, data_creazione, username_creazione, id_ente)
Select id, value, '2022-01-01', CURRENT_TIMESTAMP(), 'scriptSQL', tenant FROM gmftiplgiaprcedraaffidament;
INSERT INTO direzione(flag_titolare, descrizione, data_inizio_validita, username_creazione, id_ente) VALUES ('1', 'Direzione Generica', '2022-01-01', 'scriptSQL', 0);
update direzione set id_direzione = 0 where descrizione = 'Direzione Generica';






-- Tabella ruolo
-- Aggiunto un nuovo profilo
-- USER_READ_ONLY Utente Operativo (solo lettura)
INSERT INTO `ruolo` (`id_ruolo`, `codice`, `descrizione`) VALUES ('4', 'USER_READ_ONLY', 'Utente Operativo (solo lettura)');
INSERT INTO `ruolo_funzione` (`id_ruolo`, `id_funzione`) VALUES ('4', '2');


INSERT INTO `funzionalita` (`codice_funzionalita`,`desc_funzionalita`, `order`) VALUES ('DASHB', 'DASHBOARD',    '1');
INSERT INTO `funzionalita` (`codice_funzionalita`,`desc_funzionalita`, `order`) VALUES ('PRJ',   'PROGETTI',     '2');
INSERT INTO `funzionalita` (`codice_funzionalita`,`desc_funzionalita`, `order`) VALUES ('PROF',  'PROFILAZIONE', '3');
INSERT INTO `funzionalita` (`codice_funzionalita`,`desc_funzionalita`, `order`) VALUES ('ANAG',  'ANAGRAFICHE',  '4');


INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('1', '1');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('1', '2');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('1', '3');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('2', '1');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('2', '2');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('2', '3');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('3', '1');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('3', '2');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('4', '1');
INSERT INTO `ruolo_funzionalita` (`ruolo_id`, `funzionalita_id`) VALUES ('4', '2');

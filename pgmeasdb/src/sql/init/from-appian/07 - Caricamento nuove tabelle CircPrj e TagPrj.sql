DELETE FROM progetto_circoscrizione;
ALTER TABLE progetto_circoscrizione AUTO_INCREMENT = 1;

INSERT INTO progetto_circoscrizione (`progetto_id`, `circoscrizione_id`, `ordine`)
SELECT gmfprogetto_circoscrizionid, elt, gmfprgett_circscrizione_idx FROM gmfprogetto_circoscrizione;



DELETE FROM progetto_tag;
ALTER TABLE progetto_tag AUTO_INCREMENT = 1;

INSERT INTO progetto_tag (`progetto_id`, `tag_id`, `ordine`)
SELECT gmfprogetto_listtag_id, elt, gmfprogetto_listtag_idx FROM gmfprogetto_listtag;

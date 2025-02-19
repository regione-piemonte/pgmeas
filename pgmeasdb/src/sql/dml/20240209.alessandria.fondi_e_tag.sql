INSERT INTO gmftag (value, `order`, active, tenant)
SELECT tmp.value, (SELECT max(gmftag.`order`) + tmp.idx FROM gmftag WHERE gmftag.tenant = ente.id_ente), 1, ente.id_ente
FROM (VALUES
	ROW('Comune di Alessandria', 'Sociale', 1),
	ROW('Comune di Alessandria', 'Sport', 2)
) AS tmp(ente, value, idx)
CROSS JOIN ente ON ente.descrizione = tmp.ente
WHERE 1=1
AND NOT EXISTS (
	SELECT 1
	FROM gmftag current
	WHERE current.tenant = ente.id_ente
	AND current.value = tmp.value
);

INSERT INTO gmffondo (value, `order`, active, tenant, type_fund)
SELECT tmp.value, (SELECT max(gmffondo.`order`) + tmp.idx FROM gmffondo WHERE gmffondo.tenant = ente.id_ente), 1, ente.id_ente, tmp.type_fund
FROM (VALUES
	ROW('Comune di Alessandria', 'ReNDiS - Repertorio Nazionale degli interventi per la Difesa del Suolo', 1, 2)
) AS tmp(ente, value, idx, type_fund)
CROSS JOIN ente ON ente.descrizione = tmp.ente
WHERE 1=1
AND NOT EXISTS (
	SELECT 1
	FROM gmffondo current
	WHERE current.tenant = ente.id_ente
	AND current.value = tmp.value
);

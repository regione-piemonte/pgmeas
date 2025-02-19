SET @new_ente = 11;
SET @orig_ente = 9;

INSERT INTO elemento_gerarchia (ente_id, cod_tipologia_gerarchia, des_elemento, attivo, cod_livello_elemento, `order`)
SELECT @new_ente, curr.cod_tipologia_gerarchia, curr.des_elemento, curr.attivo, curr.cod_livello_elemento, curr.order
FROM elemento_gerarchia curr
JOIN tipologia_gerarchia ON tipologia_gerarchia.cod_tipologia_gerarchia = curr.cod_tipologia_gerarchia
WHERE curr.ente_id = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM elemento_gerarchia tmp
    WHERE tmp.ente_id = @new_ente
    AND tmp.cod_livello_elemento = curr.cod_livello_elemento
)
ORDER BY tipologia_gerarchia.livello_tipologia, curr.cod_livello_elemento;

INSERT INTO gmfasse (value, `order`, active, tenant, cod_livello_elemento)
SELECT curr.value, curr.order, curr.active, @new_ente, curr.cod_livello_elemento
FROM gmfasse curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfasse tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.cod_livello_elemento = curr.cod_livello_elemento
)
ORDER BY curr.order;

INSERT INTO gmfazioni (value, `order`, active, tenant, cod_livello_elemento)
SELECT curr.value, curr.order, curr.active, @new_ente, curr.cod_livello_elemento
FROM gmfazioni curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfazioni tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.cod_livello_elemento = curr.cod_livello_elemento
)
ORDER BY curr.order;

INSERT INTO gmfcantiere (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmfcantiere curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfcantiere tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmfclausole (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmfclausole curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfclausole tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmfcontributoprevisto (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmfcontributoprevisto curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfcontributoprevisto tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmffondo (value, `order`, active, tenant, type_fund)
SELECT curr.value, curr.order, curr.active, @new_ente, curr.type_fund
FROM gmffondo curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfcontributoprevisto tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmfinserimentostrumenti (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmfinserimentostrumenti curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfinserimentostrumenti tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmfobiettivospecifico (value, `order`, active, tenant, cod_livello_elemento)
SELECT curr.value, curr.order, curr.active, @new_ente, curr.cod_livello_elemento
FROM gmfobiettivospecifico curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfobiettivospecifico tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.cod_livello_elemento = curr.cod_livello_elemento
)
ORDER BY curr.order;

INSERT INTO gmfprogrammi (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmfprogrammi curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfprogrammi tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmfrisorse (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmfrisorse curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfrisorse tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmfstrutturadiriferimento (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmfstrutturadiriferimento curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmfstrutturadiriferimento tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmftag (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmftag curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmftag tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmftipologia (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmftipologia curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmftipologia tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO gmftipologiaprocedura (value, `order`, active, tenant)
SELECT curr.value, curr.order, curr.active, @new_ente
FROM gmftipologiaprocedura curr
WHERE curr.tenant = @orig_ente
AND NOT EXISTS (
    SELECT 1
    FROM gmftipologiaprocedura tmp
    WHERE tmp.tenant = @new_ente
    AND tmp.order = curr.order
)
ORDER BY curr.order;

INSERT INTO struttura_gerarchia (elemento_gerarchia_id_padre, elemento_gerarchia_id_figlio)
SELECT padre.id, figlio.id
FROM struttura_gerarchia curr
JOIN elemento_gerarchia padre_old ON curr.elemento_gerarchia_id_padre = padre_old.id AND padre_old.ente_id = @orig_ente
JOIN elemento_gerarchia figlio_old ON curr.elemento_gerarchia_id_figlio = figlio_old.id AND figlio_old.ente_id = @orig_ente
JOIN elemento_gerarchia padre ON padre.cod_livello_elemento = padre_old.cod_livello_elemento AND padre.ente_id = @new_ente
JOIN elemento_gerarchia figlio ON figlio.cod_livello_elemento = figlio_old.cod_livello_elemento AND figlio.ente_id = @new_ente
JOIN tipologia_gerarchia ON tipologia_gerarchia.cod_tipologia_gerarchia = padre.cod_tipologia_gerarchia
WHERE NOT EXISTS (
    SELECT 1
    FROM struttura_gerarchia tmp
    WHERE tmp.elemento_gerarchia_id_padre = padre.id
    AND tmp.elemento_gerarchia_id_figlio = figlio.id
)
ORDER BY tipologia_gerarchia.livello_tipologia, padre.cod_livello_elemento, figlio.cod_livello_elemento;

INSERT INTO tipoperaz_faseproced (tipologia_operazione_id, cod_fase, descrizione, tenant)
SELECT tipologia_new.id, curr.cod_fase, curr.descrizione, @new_ente
FROM tipoperaz_faseproced curr
JOIN gmftipologia tipologia_old ON tipologia_old.id = curr.tipologia_operazione_id
JOIN gmftipologia tipologia_new ON tipologia_new.`order` = tipologia_old.`order`
WHERE curr.tenant = @orig_ente
AND tipologia_new.tenant = @new_ente
AND NOT EXISTS (
    SELECT 1
    FROM tipoperaz_faseproced tmp
    WHERE tmp.tipologia_operazione_id = tipologia_new.id
    AND tmp.tenant = @new_ente
)
ORDER BY gmftipologia.order, tipoperaz_faseproced.cod_fase;

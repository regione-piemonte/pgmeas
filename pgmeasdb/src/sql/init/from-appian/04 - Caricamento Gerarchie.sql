delete from struttura_gerarchia;
ALTER TABLE struttura_gerarchia AUTO_INCREMENT = 1;
delete from elemento_gerarchia;
ALTER TABLE elemento_gerarchia AUTO_INCREMENT = 1;


-- Caricamento Missioni e Componenti
INSERT INTO elemento_gerarchia(`ente_id`, `cod_tipologia_gerarchia`, `des_elemento`, `cod_livello_elemento`, `order`, `id_pk_old`, `nome_tabella_old`) 
SELECT a.tenant, 'MIS', a.value, SUBSTRING_INDEX(a.value, ':', 1), a.order, a.id, 'gmfmissioni'
FROM gmfmissioni a;

INSERT INTO elemento_gerarchia(`ente_id`, `cod_tipologia_gerarchia`, `des_elemento`, `cod_livello_elemento`, `order`, `id_pk_old`, `nome_tabella_old`) 
select a.tenant, 'COM', a.Value, concat('1-', SUBSTRING_INDEX(a.value, ':', 1)), a.order, a.id, 'gmfcomponente1' from gmfcomponente1 a union
select a.tenant, 'COM', a.Value, concat('2-', SUBSTRING_INDEX(a.value, ':', 1)), a.order, a.id, 'gmfcomponente2' from gmfcomponente2 a union
select a.tenant, 'COM', a.Value, concat('3-', SUBSTRING_INDEX(a.value, ':', 1)), a.order, a.id, 'gmfcomponente3' from gmfcomponente3 a union
select a.tenant, 'COM', a.Value, concat('4-', SUBSTRING_INDEX(a.value, ':', 1)), a.order, a.id, 'gmfcomponente4' from gmfcomponente4 a union
select a.tenant, 'COM', a.Value, concat('5-', SUBSTRING_INDEX(a.value, ':', 1)), a.order, a.id, 'gmfcomponente5' from gmfcomponente5 a union
select a.tenant, 'COM', a.Value, concat('6-', SUBSTRING_INDEX(a.value, ':', 1)), a.order, a.id, 'gmfcomponente6' from gmfcomponente6 a
;

-- Caricamento elemento_gerarchia
INSERT INTO struttura_gerarchia(`elemento_gerarchia_id_padre`, `elemento_gerarchia_id_figlio`) 
select b.id, a.id
from elemento_gerarchia a,
     elemento_gerarchia b
where a.cod_tipologia_gerarchia = 'COM'
and b.cod_tipologia_gerarchia = 'MIS' and a.ente_id = b.ente_id and b.cod_livello_elemento = SUBSTRING_INDEX(a.cod_livello_elemento, '-', 1);


--
-- INSERIMENTO ELEMENTI PER INVESTIMENTO
--
-- gmfinvestimento1
update gmfinvestimento1
set cod_livello_investimento = SUBSTRING(SUBSTRING_INDEX(value, ':', 1), 1,3);

update gmfinvestimento1 set cod_livello_investimento = '' 
where LOCATE('.',SUBSTRING(value,1,4)) = 0 and LOCATE(':',SUBSTRING(value,1,4)) = 0;



-- gmfinvestimento12
update gmfinvestimento12 set cod_livello_investimento = '' 
where LOCATE('.',SUBSTRING(value,1,4)) = 0 and LOCATE(':',SUBSTRING(value,1,4)) = 0;

update gmfinvestimento12
set cod_livello_investimento = SUBSTRING(SUBSTRING_INDEX(value, ':', 1), 1,2)
where 1=1
and LOCATE(':',SUBSTRING(value,1,2)) > 0;

update gmfinvestimento12
set cod_livello_investimento = SUBSTRING(SUBSTRING_INDEX(value, '.', 1), 1,2)
where 1=1
and LOCATE('.',SUBSTRING(value,1,2)) > 0;

update gmfinvestimento12
set cod_livello_investimento = '6.1'
where 1=1
and LOCATE('6.1',SUBSTRING(value,1,3)) > 0;



-- gmfinvestimento13
update gmfinvestimento13
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento13
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento21
update gmfinvestimento21
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento21
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento22
update gmfinvestimento22
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento22
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento23
update gmfinvestimento23
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento23
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento24
update gmfinvestimento24
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;


-- gmfinvestimento31
update gmfinvestimento31
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento31
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento32
update gmfinvestimento32
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento32
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento41
update gmfinvestimento41
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento41
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento42
update gmfinvestimento42
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento42
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento51
update gmfinvestimento51
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento51
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento52
update gmfinvestimento52
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento52
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento53
update gmfinvestimento53
set cod_livello_investimento = SUBSTRING(SUBSTRING_INDEX(value, '.', 1), 1,2)
where 1=1
and LOCATE('.',SUBSTRING(value,1,2)) > 0;

update gmfinvestimento53
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento61
update gmfinvestimento61
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento61
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;


-- gmfinvestimento62
update gmfinvestimento62
set cod_livello_investimento = SUBSTRING(value,1,3)
where LOCATE('.',SUBSTRING(value,1,3)) > 0;

update gmfinvestimento62
set cod_livello_investimento = ''
where LOCATE('.',SUBSTRING(value,1,3)) = 0;

--
INSERT INTO elemento_gerarchia(`ente_id`, `cod_tipologia_gerarchia`, `des_elemento`, `cod_livello_elemento`, `order`, `id_pk_old`, `nome_tabella_old`) 
select a.tenant, 'INV', a.Value, concat('1-1-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento1'  as tabella_old from gmfinvestimento1 a union
select a.tenant, 'INV', a.Value, concat('1-2-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento12' as tabella_old from gmfinvestimento12 a union 
select a.tenant, 'INV', a.Value, concat('1-3-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento13' as tabella_old from gmfinvestimento13 a union 
select a.tenant, 'INV', a.Value, concat('2-1-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento21' as tabella_old from gmfinvestimento21 a union
select a.tenant, 'INV', a.Value, concat('2-2-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento22' as tabella_old from gmfinvestimento22 a union
select a.tenant, 'INV', a.Value, concat('2-3-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento23' as tabella_old from gmfinvestimento23 a union
select a.tenant, 'INV', a.Value, concat('2-4-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento24' as tabella_old from gmfinvestimento24 a union
select a.tenant, 'INV', a.Value, concat('3-1-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento31' as tabella_old from gmfinvestimento31 a union
select a.tenant, 'INV', a.Value, concat('3-2-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento32' as tabella_old from gmfinvestimento32 a union
select a.tenant, 'INV', a.Value, concat('4-1-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento41' as tabella_old from gmfinvestimento41 a union
select a.tenant, 'INV', a.Value, concat('4-2-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento42' as tabella_old from gmfinvestimento42 a union
select a.tenant, 'INV', a.Value, concat('5-1-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento51' as tabella_old from gmfinvestimento51 a union
select a.tenant, 'INV', a.Value, concat('5-2-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento52' as tabella_old from gmfinvestimento52 a union
select a.tenant, 'INV', a.Value, concat('5-3-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento53' as tabella_old from gmfinvestimento53 a union
select a.tenant, 'INV', a.Value, concat('6-1-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento61' as tabella_old from gmfinvestimento61 a union
select a.tenant, 'INV', a.Value, concat('6-2-', a.cod_livello_investimento), a.order, a.id as id_old, 'gmfinvestimento62' as tabella_old from gmfinvestimento62 a
;

INSERT INTO struttura_gerarchia(`elemento_gerarchia_id_padre`, `elemento_gerarchia_id_figlio`)
select b.id, a.id
from elemento_gerarchia a,
     (select * from elemento_gerarchia where cod_tipologia_gerarchia = 'COM') b
where a.ente_id = b.Ente_id
and   a.cod_tipologia_gerarchia = 'INV'
and   SUBSTRING(a.cod_livello_elemento,1,3) = b.cod_livello_elemento
;

--
-- INSERIMENTO ELEMENTI PER SUB INVESTIMENTO
--
INSERT INTO elemento_gerarchia(`ente_id`, `cod_tipologia_gerarchia`, `des_elemento`, `cod_livello_elemento`, `order`, `id_pk_old`, `nome_tabella_old`)
-- M1 C1 
select a.tenant, 'SUB', a.Value, '1-1-1.3', a.order, a.id as id_old, 'gmfsubinvestimento13'  as tabella_old from gmfsubinvestimento13 a union
select a.tenant, 'SUB', a.Value, '1-1-1.4', a.order, a.id as id_old, 'gmfsubinvestimento14'  as tabella_old from gmfsubinvestimento14 a union
select a.tenant, 'SUB', a.Value, '1-1-1.6', a.order, a.id as id_old, 'gmfsubinvestimento16'  as tabella_old from gmfsubinvestimento16 a union
select a.tenant, 'SUB', a.Value, '1-1-1.7', a.order, a.id as id_old, 'gmfsubinvestimento17'  as tabella_old from gmfsubinvestimento17 a union
select a.tenant, 'SUB', a.Value, '1-1-2.1', a.order, a.id as id_old, 'gmfsubinvestimento21'  as tabella_old from gmfsubinvestimento21 a union
select a.tenant, 'SUB', a.Value, '1-1-2.2', a.order, a.id as id_old, 'gmfsubinvestimento22'  as tabella_old from gmfsubinvestimento22 a union
select a.tenant, 'SUB', a.Value, '1-1-2.3', a.order, a.id as id_old, 'gmfsubinvestimento23'  as tabella_old from gmfsubinvestimento23 a union
-- M1 C2
select a.tenant, 'SUB', a.Value, '1-2-3', a.order, a.id as id_old, 'gmfsubinvestimento123'  as tabella_old from gmfsubinvestimento123 a union
select a.tenant, 'SUB', a.Value, '1-2-4', a.order, a.id as id_old, 'gmfsubinvestimento124'  as tabella_old from gmfsubinvestimento124 a union
select a.tenant, 'SUB', a.Value, '1-2-5', a.order, a.id as id_old, 'gmfsub125'  as tabella_old from gmfsub125 a union
select a.tenant, 'SUB', a.Value, '1-2-1', a.order, a.id as id_old, 'gmfsubinvestimento121'  as tabella_old from gmfsubinvestimento121 a union
-- M1 C3
select a.tenant, 'SUB', a.Value, '1-3-3.3', a.order, a.id as id_old, 'gmfsubinvestimento133'  as tabella_old from gmfsubinvestimento133 a union
select a.tenant, 'SUB', a.Value, '1-3-1.1', a.order, a.id as id_old, 'gmfsubinvestimento131'  as tabella_old from gmfsubinvestimento131 a union
select a.tenant, 'SUB', a.Value, '1-3-4.2', a.order, a.id as id_old, 'gmfsub1342'  as tabella_old from gmfsub1342 a union
select a.tenant, 'SUB', a.Value, '1-3-4.3', a.order, a.id as id_old, 'gmfsub1343'  as tabella_old from gmfsub1343 a union
-- M2 C2
select a.tenant, 'SUB', a.Value, '2-2-5.1', a.order, a.id as id_old, 'gmfsubinvestimento225'  as tabella_old from gmfsubinvestimento225 a union
select a.tenant, 'SUB', a.Value, '2-2-4.1', a.order, a.id as id_old, 'gmfsub221'  as tabella_old from gmfsub221 a union
select a.tenant, 'SUB', a.Value, '2-2-4.4', a.order, a.id as id_old, 'gmfsub224'  as tabella_old from gmfsub224 a union
-- M2 C4
select a.tenant, 'SUB', a.Value, '2-4-2.1', a.order, a.id as id_old, 'gmfsubinvestimento242'  as tabella_old from gmfsubinvestimento242 a union
-- M3 C1
select a.tenant, 'SUB', a.Value, '3-1-1.1', a.order, a.id as id_old, 'gmfsub311'  as tabella_old from gmfsub311 a union
select a.tenant, 'SUB', a.Value, '3-1-1.2', a.order, a.id as id_old, 'gmfsub312'  as tabella_old from gmfsub312 a union
select a.tenant, 'SUB', a.Value, '3-1-1.3', a.order, a.id as id_old, 'gmfsub313'  as tabella_old from gmfsub313 a union
-- M3 C2
select a.tenant, 'SUB', a.Value, '3-2-2.1', a.order, a.id as id_old, 'gmfsub321'  as tabella_old from gmfsub321 a union
select a.tenant, 'SUB', a.Value, '3-2-2.2', a.order, a.id as id_old, 'gmfsub322'  as tabella_old from gmfsub322 a union
-- M5 C2
select a.tenant, 'SUB', a.Value, '5-2-2.3', a.order, a.id as id_old, 'gmfsub522'  as tabella_old from gmfsub522 a union
select a.tenant, 'SUB', a.Value, '5-2-1.1', a.order, a.id as id_old, 'gmfsub521'  as tabella_old from gmfsub521 a union
-- M5 C3
select a.tenant, 'SUB', a.Value, '5-3-1', a.order, a.id as id_old, 'gmfsub531'  as tabella_old from gmfsub531 a union
select a.tenant, 'SUB', a.Value, '5-3-4', a.order, a.id as id_old, 'gmfsub53es'  as tabella_old from gmfsub53es a union
-- M6 C1
select a.tenant, 'SUB', a.Value, '6-1-1.1', a.order, a.id as id_old, 'gmfsub611'  as tabella_old from gmfsub611 a union
-- M6 C2
select a.tenant, 'SUB', a.Value, '6-2-1.1', a.order, a.id as id_old, 'gmfsub621'  as tabella_old from gmfsub621 a union
select a.tenant, 'SUB', a.Value, '6-2-1.3', a.order, a.id as id_old, 'gmfsub623'  as tabella_old from gmfsub623 a union
select a.tenant, 'SUB', a.Value, '6-6-2.2', a.order, a.id as id_old, 'gmfsub622'  as tabella_old from gmfsub622 a
;

INSERT INTO struttura_gerarchia(`elemento_gerarchia_id_padre`, `elemento_gerarchia_id_figlio`)
select b.id, a.id
from elemento_gerarchia a,
     (select * from elemento_gerarchia where cod_tipologia_gerarchia = 'INV') b
where a.ente_id = b.Ente_id
and   a.cod_tipologia_gerarchia = 'SUB'
and   a.cod_livello_elemento = b.cod_livello_elemento
order by a.id, a.order
;

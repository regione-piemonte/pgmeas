CREATE VIEW vw_riepilogo_contabile_cup AS

select cup.cup, prj.id as id_progetto, prj.tenant, prj.direzione_id, tipo.id as id_tipo_step_finanziario, tipo.descrizione, sum(dett.importo) as importo_totale 
from progetto prj

inner join ente ente
	on prj.tenant = ente.id_ente

inner join step_finanziario_cup cup
	on prj.altro = cup.cup

inner join step_finanziario_contab step
	on cup.id = step.step_finanziario_cup_id

inner join step_finanziario_contab_dettaglio dett
	on step.id = dett.step_finanziario_contab_id

inner join tipo_step_finanziario_contab tipo
	on tipo.id = step.tipo_step_finanziario_id
    
where prj.data_fine_validita is null
and ente.flag_contabilita_esterna = 1

group by cup.cup, prj.id, prj.tenant, prj.direzione_id, tipo.id, tipo.descrizione;

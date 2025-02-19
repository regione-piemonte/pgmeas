/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.dto.v2.RicercaInterventiFilterDto;
import it.csi.pgmeas.commons.dto.v2.RicercaInterventiResultDto;
import it.csi.pgmeas.commons.dto.v2.RicercaModuloAResultDto;
import it.csi.pgmeas.commons.model.Intervento;

@Repository
public interface RicercaRepository extends JpaRepository<Intervento, Integer> {
	@Query("""
			select new it.csi.pgmeas.commons.dto.v2.RicercaInterventiResultDto(
			i.intId as intId, 
			i.enteId as enteId, 
			i.intAnno as intAnno,
			i.intCup as intCup, 
			i.intCod as intCod, 
			i.intTitolo as intTitolo, 
			ris.intStatoId as intStatoId,
			i.intImporto as intImporto,
			count(*) over() as total)
			from Intervento i 
			join InterventoStruttura istr on i.intId = istr.intId
				and (istr.dataCancellazione is null or istr.dataCancellazione >= current_timestamp)
			join RInterventoStato ris on i.intId = ris.intId 
				and ris.validitaInizio <= current_timestamp 
				and (ris.validitaFine is null or ris.validitaFine >= current_timestamp) 
				and (ris.dataCancellazione is null or ris.dataCancellazione >= current_timestamp) 
			join InterventoStato is on is.intStatoId = ris.intStatoId 
				and is.validitaInizio <= current_timestamp 
				and (is.validitaFine is null or is.validitaFine >= current_timestamp) 
				and (is.dataCancellazione is null or is.dataCancellazione >= current_timestamp) 
			join RInterventoObiettivo rio on i.intId = rio.intId
				and rio.validitaInizio <= current_timestamp 
				and (rio.validitaFine is null or rio.validitaFine >= current_timestamp) 
				and (rio.dataCancellazione is null or rio.dataCancellazione >= current_timestamp) 
			join RInterventoFinalita rif on i.intId = rif.intId
				and rif.validitaInizio <= current_timestamp 
				and (rif.validitaFine is null or rif.validitaFine >= current_timestamp) 
				and (rif.dataCancellazione is null or rif.dataCancellazione >= current_timestamp) 
			join RInterventoCategoria ric on i.intId = ric.intId
				and ric.validitaInizio <= current_timestamp 
				and (ric.validitaFine is null or ric.validitaFine >= current_timestamp) 
				and (ric.dataCancellazione is null or ric.dataCancellazione >= current_timestamp) 
			join RInterventoTipo rit on i.intId = rit.intId
				and rit.validitaInizio <= current_timestamp 
				and (rit.validitaFine is null or rit.validitaFine >= current_timestamp) 
				and (rit.dataCancellazione is null or rit.dataCancellazione >= current_timestamp) 
			join RInterventoAppaltoTipo riat on i.intId = riat.intId
				and riat.validitaInizio <= current_timestamp 
				and (riat.validitaFine is null or riat.validitaFine >= current_timestamp) 
				and (riat.dataCancellazione is null or riat.dataCancellazione >= current_timestamp) 
			join RInterventoContrattoTipo rict on i.intId = rict.intId
				and rict.validitaInizio <= current_timestamp 
				and (rict.validitaFine is null or rict.validitaFine >= current_timestamp) 
				and (rict.dataCancellazione is null or rict.dataCancellazione >= current_timestamp)
			left join RInterventoStatoProgettuale risp on i.intId = risp.intId 
				and risp.validitaInizio <= current_timestamp 
				and (risp.validitaFine is null or risp.validitaFine >= current_timestamp) 
				and (risp.dataCancellazione is null or risp.dataCancellazione >= current_timestamp)
			left join Finanziamento f on i.intId = f.intId
				and (f.dataCancellazione is null or f.dataCancellazione >= current_timestamp)
			left join FinanziamentoTipoDet ftd on f.finTipoDetId = ftd.finTipoDetId
				and ftd.validitaInizio <= current_timestamp 
				and (ftd.validitaFine is null or ftd.validitaFine >= current_timestamp) 
				and (ftd.dataCancellazione is null or ftd.dataCancellazione >= current_timestamp)
			left join FinanziamentoTipo ft on ftd.finTipoId = ft.finTipoId	
				and ft.validitaInizio <= current_timestamp 
				and (ft.validitaFine is null or ft.validitaFine >= current_timestamp) 
				and (ft.dataCancellazione is null or ft.dataCancellazione >= current_timestamp)
			where (i.dataCancellazione is null or i.dataCancellazione >= current_timestamp)
			and (:#{#filters.anno} is null or i.intAnno = :#{#filters.anno}) 
			and (:#{#filters.cup} is null or :#{#filters.cup} = '' 
			     or (i.intCup is not null and i.intCup != '' and i.intCup ilike %:#{#filters.cup}%))
			and (:#{#filters.codPgmeas} is null or i.intCod ilike %:#{#filters.codPgmeas}%) 
			and (:#{#filters.titolo} is null or i.intTitolo ilike %:#{#filters.titolo}%) 
			and (:#{#filters.obiettivi == null or #filters.obiettivi.isEmpty()} = true or rio.intObiettivoId in (:#{#filters.obiettivi})) 
			and (:#{#filters.finalita == null or #filters.finalita.isEmpty()} = true or rif.intFinalitaId in (:#{#filters.finalita})) 
			and (:#{#filters.categorie == null or #filters.categorie.isEmpty()} = true or ric.intCategoriaId in (:#{#filters.categorie})) 
			and (:#{#filters.stati == null or #filters.stati.isEmpty()} = true or ris.intStatoId in (:#{#filters.stati})) 
			and (:#{#filters.tipi == null or #filters.tipi.isEmpty()} = true or rit.intTipoId in (:#{#filters.tipi})) 
			and (:#{#filters.appaltiTipo == null or #filters.appaltiTipo.isEmpty()} = true or riat.intAppaltoTipoId in (:#{#filters.appaltiTipo})) 
			and (:#{#filters.contrattiTipo == null or #filters.contrattiTipo.isEmpty()} = true or rict.intContrattoTipoId in (:#{#filters.contrattiTipo})) 
			and (:#{#filters.aziende == null or #filters.aziende.isEmpty()} = true or i.enteId in (:#{#filters.aziende})) 
			and (:#{#filters.strutture == null or #filters.strutture.isEmpty()} = true or istr.strId in (:#{#filters.strutture})) 
			and (:#{#filters.statiProgettuali == null or #filters.statiProgettuali.isEmpty()} = true or risp.intStatoProgId in (:#{#filters.statiProgettuali}))
			and (:#{#filters.soloInterventiFinanziati == null or #filters.soloInterventiFinanziati == false} = true or f.finImporto > 0) 	
			and (:#{#filters.finanziamenti == null or #filters.finanziamenti.isEmpty()} = true or ft.finTipoId in (:#{#filters.finanziamenti})) 
			and (:#{#filters.finanziamentiTipo == null or #filters.finanziamentiTipo.isEmpty()} = true or ftd.finTipoDetId in (:#{#filters.finanziamentiTipo})) 
			and is.intStatoCod not in :stati 
			group by intId, enteId, intAnno, intCup, intCod, intTitolo, intStatoId, is.intStatoCod
			""")
	List<RicercaInterventiResultDto> findAllInterventiSenzaCancellatiByAllFiltriAndStati(@Param("filters") RicercaInterventiFilterDto filters,
			@Param("stati") List<String> stati, Pageable pageable);
	
	@Query("""
			select new it.csi.pgmeas.commons.dto.v2.RicercaModuloAResultDto(
			i.intId as intId, 
			i.enteId as enteId, 
			i.intAnno as intAnno,
			i.intCup as intCup, 
			i.intCod as intCod, 
			i.intTitolo as intTitolo, 
			ris.intStatoId as intStatoId,
			i.intImporto as intImporto,
			count(*) over() as total,
			rim.rIntModuloId as rIntModuloAId,
			rim.moduloId as allegatoRichiestaAmmissioneFinanziamentoId,
			rim.moduloStatoId as allegatoRichiestaAmmissioneFinanziamentoStatoId,
			case 
				when (
					rim.moduloId is null 
					and i.intPrioritaAnno <= extract(year from current_date)
					and (select min(ips.intPrevSpesaAnno) 
						from InterventoPrevisioneSpesa ips
						where ips.validitaInizio <= current_timestamp 
						and (ips.validitaFine is null or ips.validitaFine >= current_timestamp) 
						and (ips.dataCancellazione is null or ips.dataCancellazione >= current_timestamp)
						and ips.intId = i.intId
						group by ips.intId)
					<= extract(year from current_date)
				) then true
				else false
			end as creaModuloA)
			from Intervento i 
			join InterventoStruttura istr on i.intId = istr.intId
				and (istr.dataCancellazione is null or istr.dataCancellazione >= current_timestamp)
			join RInterventoStato ris on i.intId = ris.intId 
				and ris.validitaInizio <= current_timestamp 
				and (ris.validitaFine is null or ris.validitaFine >= current_timestamp) 
				and (ris.dataCancellazione is null or ris.dataCancellazione >= current_timestamp) 
			join InterventoStato is on is.intStatoId = ris.intStatoId 
				and is.validitaInizio <= current_timestamp 
				and (is.validitaFine is null or is.validitaFine >= current_timestamp) 
				and (is.dataCancellazione is null or is.dataCancellazione >= current_timestamp) 
			join RInterventoObiettivo rio on i.intId = rio.intId
				and rio.validitaInizio <= current_timestamp 
				and (rio.validitaFine is null or rio.validitaFine >= current_timestamp) 
				and (rio.dataCancellazione is null or rio.dataCancellazione >= current_timestamp) 
			join RInterventoFinalita rif on i.intId = rif.intId
				and rif.validitaInizio <= current_timestamp 
				and (rif.validitaFine is null or rif.validitaFine >= current_timestamp) 
				and (rif.dataCancellazione is null or rif.dataCancellazione >= current_timestamp) 
			join RInterventoCategoria ric on i.intId = ric.intId
				and ric.validitaInizio <= current_timestamp 
				and (ric.validitaFine is null or ric.validitaFine >= current_timestamp) 
				and (ric.dataCancellazione is null or ric.dataCancellazione >= current_timestamp) 
			join RInterventoTipo rit on i.intId = rit.intId
				and rit.validitaInizio <= current_timestamp 
				and (rit.validitaFine is null or rit.validitaFine >= current_timestamp) 
				and (rit.dataCancellazione is null or rit.dataCancellazione >= current_timestamp) 
			join RInterventoAppaltoTipo riat on i.intId = riat.intId
				and riat.validitaInizio <= current_timestamp 
				and (riat.validitaFine is null or riat.validitaFine >= current_timestamp) 
				and (riat.dataCancellazione is null or riat.dataCancellazione >= current_timestamp) 
			join RInterventoContrattoTipo rict on i.intId = rict.intId
				and rict.validitaInizio <= current_timestamp 
				and (rict.validitaFine is null or rict.validitaFine >= current_timestamp) 
				and (rict.dataCancellazione is null or rict.dataCancellazione >= current_timestamp)
			left join RInterventoStatoProgettuale risp on i.intId = risp.intId 
				and risp.validitaInizio <= current_timestamp 
				and (risp.validitaFine is null or risp.validitaFine >= current_timestamp) 
				and (risp.dataCancellazione is null or risp.dataCancellazione >= current_timestamp)
			left join Finanziamento f on i.intId = f.intId
				and (f.dataCancellazione is null or f.dataCancellazione >= current_timestamp)
			left join FinanziamentoTipoDet ftd on f.finTipoDetId = ftd.finTipoDetId
				and ftd.validitaInizio <= current_timestamp 
				and (ftd.validitaFine is null or ftd.validitaFine >= current_timestamp) 
				and (ftd.dataCancellazione is null or ftd.dataCancellazione >= current_timestamp)
			left join FinanziamentoTipo ft on ftd.finTipoId = ft.finTipoId	
				and ft.validitaInizio <= current_timestamp 
				and (ft.validitaFine is null or ft.validitaFine >= current_timestamp) 
				and (ft.dataCancellazione is null or ft.dataCancellazione >= current_timestamp)
			left join RInterventoModulo rim on i.intId = rim.intId
				and rim.validitaInizio <= current_timestamp 
				and (rim.validitaFine is null or rim.validitaFine >= current_timestamp) 
				and (rim.dataCancellazione is null or rim.dataCancellazione >= current_timestamp)
				and rim.moduloId IN (
					select m.moduloId 
					from Modulo m 
				    where (m.moduloCod IN :moduli or m.moduloCod is null)
				    and m.validitaInizio <= current_timestamp 
				    and (m.validitaFine is null or m.validitaFine >= current_timestamp) 
				    AND (m.dataCancellazione is null or m.dataCancellazione >= current_timestamp)
				)
			where (i.dataCancellazione is null or i.dataCancellazione >= current_timestamp)
			and (:#{#filters.anno} is null or i.intAnno = :#{#filters.anno}) 
			and (:#{#filters.cup} is null or :#{#filters.cup} = '' 
			     or (i.intCup is not null and i.intCup != '' and i.intCup ilike %:#{#filters.cup}%))
			and (:#{#filters.codPgmeas} is null or i.intCod ilike %:#{#filters.codPgmeas}%) 
			and (:#{#filters.titolo} is null or i.intTitolo ilike %:#{#filters.titolo}%) 
			and (:#{#filters.obiettivi == null or #filters.obiettivi.isEmpty()} = true or rio.intObiettivoId in (:#{#filters.obiettivi})) 
			and (:#{#filters.finalita == null or #filters.finalita.isEmpty()} = true or rif.intFinalitaId in (:#{#filters.finalita})) 
			and (:#{#filters.categorie == null or #filters.categorie.isEmpty()} = true or ric.intCategoriaId in (:#{#filters.categorie})) 
			and (:#{#filters.stati == null or #filters.stati.isEmpty()} = true or ris.intStatoId in (:#{#filters.stati})) 
			and (:#{#filters.tipi == null or #filters.tipi.isEmpty()} = true or rit.intTipoId in (:#{#filters.tipi})) 
			and (:#{#filters.appaltiTipo == null or #filters.appaltiTipo.isEmpty()} = true or riat.intAppaltoTipoId in (:#{#filters.appaltiTipo})) 
			and (:#{#filters.contrattiTipo == null or #filters.contrattiTipo.isEmpty()} = true or rict.intContrattoTipoId in (:#{#filters.contrattiTipo})) 
			and (:#{#filters.aziende == null or #filters.aziende.isEmpty()} = true or i.enteId in (:#{#filters.aziende})) 
			and (:#{#filters.strutture == null or #filters.strutture.isEmpty()} = true or istr.strId in (:#{#filters.strutture})) 
			and (:#{#filters.statiProgettuali == null or #filters.statiProgettuali.isEmpty()} = true or risp.intStatoProgId in (:#{#filters.statiProgettuali}))
			and (:#{#filters.soloInterventiFinanziati == null or #filters.soloInterventiFinanziati == false} = true or f.finImporto > 0) 	
			and (:#{#filters.finanziamenti == null or #filters.finanziamenti.isEmpty()} = true or ft.finTipoId in (:#{#filters.finanziamenti})) 
			and (:#{#filters.finanziamentiTipo == null or #filters.finanziamentiTipo.isEmpty()} = true or ftd.finTipoDetId in (:#{#filters.finanziamentiTipo})) 
			and is.intStatoCod not in :stati 
			group by intId, enteId, intAnno, intCup, intCod, intTitolo, intStatoId, is.intStatoCod, rIntModuloAId, allegatoRichiestaAmmissioneFinanziamentoId,
				allegatoRichiestaAmmissioneFinanziamentoStatoId
			""")
	List<RicercaModuloAResultDto> findAllInterventiModuloASenzaCancellatiByAllFiltriAndStatiAndModuli(
			@Param("filters") RicercaInterventiFilterDto filters,
			@Param("stati") List<String> stati, @Param("moduli") List<String> moduli, Pageable pageable);
	
}

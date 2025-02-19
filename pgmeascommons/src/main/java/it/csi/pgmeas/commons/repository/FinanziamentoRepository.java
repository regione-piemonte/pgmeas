/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.Finanziamento;

@Repository
public interface FinanziamentoRepository extends JpaRepository<Finanziamento, Integer> {
	@Query("select a from Finanziamento a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp order by a.finId")
	List<Finanziamento> findAllSenzaCancellati();

	@Query("select a from Finanziamento a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :intId order by a.finId")
	List<Finanziamento> findAllSenzaCancellatiByIntId(@Param("intId") Integer intId);

	@Query("select a from Finanziamento a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.intId = :intId "
			+ "and a.enteId = :enteId "
			+ "order by a.finId")
	List<Finanziamento> findAllByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);
	
	@Query("select ft.finTipoId as tipologiaId, ft.finTipoCod as tipologiaCod, ft.finTipoDesc as tipologiaDesc, "
			+ "ftd.finTipoDetId as tipologiaDettaglioId, ftd.finTipoDetCod as tipologiaDettaglioCod, "
			+ "ftd.finTipoDetDesc as tipologiaDettaglioDesc, f.finId as finanziamentoId, "
			+ "f.finPrincipale as isPrincipale, f.finImporto as importoTotale, "
			+ "fit.finImpTipoId as finanziamentoImportoTipoId, fit.finImpTipoCod as finanziamentoImportoTipoCod, "
			+ "fit.finImpTipoDesc as finanziamentoImportoTipoDesc, fi.finImporto as finanziamentoImporto "
			+ "from Finanziamento f "
			+ "join RFinanziamentoImporto fi on f.finId = fi.finId "
			+ "join FinanziamentoImportoTipo fit on fi.finImpTipoId = fit.finImpTipoId "
			+ "join FinanziamentoTipoDet ftd on f.finTipoDetId = ftd.finTipoDetId "
			+ "join FinanziamentoTipo ft on ftd.finTipoId = ft.finTipoId "
			+ "where (f.dataCancellazione is null or f.dataCancellazione >= current_timestamp) "
			+ "and (fi.dataCancellazione is null or fi.dataCancellazione >= current_timestamp) "
			+ "and (fi.validitaFine is null or fi.validitaFine >= current_timestamp) "
			+ "and (fit.dataCancellazione is null or fit.dataCancellazione >= current_timestamp) "
			+ "and (fit.validitaFine is null or fit.validitaFine >= current_timestamp) "
			+ "and (ftd.dataCancellazione is null or ftd.dataCancellazione >= current_timestamp) "
			+ "and (ftd.validitaFine is null or ftd.validitaFine >= current_timestamp) "
			+ "and (ft.dataCancellazione is null or ft.dataCancellazione >= current_timestamp) "
			+ "and (ft.validitaFine is null or ft.validitaFine >= current_timestamp) "
			+ "and f.intId = :intId "
			+ "and f.enteId = :enteId "
			+ "order by ft.finTipoDesc, ftd.finTipoDetDesc")
	List<Map<String, Object>> findPianoFinanziarioByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);

	@Query("select ftd.finTipoDetId as tipologiaDettaglioId, "
			+ "ftd.finTipoDetCod as tipologiaDettaglioCod, "
			+ "ftd.finTipoDetDesc as tipologiaDettaglioDesc, "
			+ "ft.finTipoId as tipologiaId, ft.finTipoCod as tipologiaCod, "
			+ "ft.finTipoDesc as tipologiaDesc, "
			+ "fit.finImpTipoId as finanziamentoImportoTipoId, "
			+ "fit.finImpTipoCod as finanziamentoImportoTipoCod, "
			+ "fit.finImpTipoDesc as finanziamentoImportoTipoDesc, "
			+ "fitd.rImpTipoDetId as importoTipoDetId, "
			+ "fitd.finPercentualeImporto as finanziamentoPercentualeImporto "
			+ "from RFinanziamentoImportoTipoDet fitd "
			+ "join FinanziamentoTipoDet ftd on fitd.finTipoDetId = ftd.finTipoDetId "
			+ "join FinanziamentoTipo ft on ftd.finTipoId = ft.finTipoId "
			+ "join FinanziamentoImportoTipo fit on fit.finImpTipoId = fitd.finImpTipoId "
			+ "where fitd.validitaFine is null "
			+ "and (fitd.dataCancellazione is null or fitd.dataCancellazione >= current_timestamp) "
			+ "and ftd.validitaFine is null "
			+ "and (ftd.dataCancellazione is null or ftd.dataCancellazione >= current_timestamp) "
			+ "and ft.validitaFine is null "
			+ "and (ft.dataCancellazione is null or ft.dataCancellazione >= current_timestamp) "
			+ "and fit.validitaFine is null "
			+ "and (fit.dataCancellazione is null or fit.dataCancellazione >= current_timestamp) "
			+ "and fitd.enteId = :enteId "
			+ "order by ft.finTipoDesc, ftd.finTipoDetDesc")
	List<Map<String, Object>> findFinanziamentiByEnteId(@Param("enteId") Integer enteId);
}

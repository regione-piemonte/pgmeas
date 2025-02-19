/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.Intervento;

@Repository
public interface InterventoRepository extends JpaRepository<Intervento, Integer> {
	@Query("select a from Intervento a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :id")
	Optional<Intervento> findNonCancellatoById(@Param("id") Integer id);

	@Query("select a from Intervento a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intAnno = :intAnno")
	List<Intervento> findAllSenzaCancellatiByIntAnno(@Param("intAnno") Integer intAnno);
	
	@Query("select i from Intervento i "
			+ " join RInterventoStato ris on i.intId = ris.intId "
			+ " join InterventoStato is on is.intStatoId = ris.intStatoId "
			+ " where (i.dataCancellazione is null or i.dataCancellazione >= current_timestamp) "
			+ " and ris.validitaInizio <= current_timestamp "
			+ " and (ris.validitaFine is null or ris.validitaFine >= current_timestamp) "
			+ " and (ris.dataCancellazione is null or ris.dataCancellazione >= current_timestamp) "
			+ " and is.validitaInizio <= current_timestamp "
			+ " and (is.validitaFine is null or is.validitaFine >= current_timestamp) "
			+ " and (is.dataCancellazione is null or is.dataCancellazione >= current_timestamp) "
			+ " and i.intAnno = :intAnno"
			+ " and is.intStatoCod != 'ANN'")
	List<Intervento> findAllSenzaCancellatiAndAnnullatiByIntAnno(@Param("intAnno") Integer intAnno);

	@Query(value = "select distinct a.intAnno from Intervento a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<Integer> getAnniFromInterventiNonCancellati();

	int countByIntAnnoAndEnteId(@Param("intAnno") Integer intAnno, @Param("enteId") Integer enteId);

	@Query("select i from Intervento i "
			+ " join RInterventoStato ris on i.intId = ris.intId "
			+ " join InterventoStato is on is.intStatoId = ris.intStatoId "
			+ " where (i.dataCancellazione is null or i.dataCancellazione >= current_timestamp) "
			+ " and ris.validitaInizio <= current_timestamp "
			+ " and (ris.validitaFine is null or ris.validitaFine >= current_timestamp) "
			+ " and (ris.dataCancellazione is null or ris.dataCancellazione >= current_timestamp) "
			+ " and is.validitaInizio <= current_timestamp "
			+ " and (is.validitaFine is null or is.validitaFine >= current_timestamp) "
			+ " and (is.dataCancellazione is null or is.dataCancellazione >= current_timestamp) "
			+ " and i.intAnno != :annoCorrente "
			+ " and (:intAnno is null or i.intAnno = :intAnno) "
			+ " and (:codice is null or i.intCod ilike %:codice%) "
			+ " and (:titolo is null or i.intTitolo ilike %:titolo%) "
			+ " and (:cup is null or i.intCup ilike %:cup%) "
			+ " and is.intStatoCod in :stati")
	List<Intervento> findAllSenzaCancellatiByFiltriAndStati(@Param("intAnno") Integer intAnno, @Param("codice") String codice, 
			@Param("titolo") String titolo, @Param("cup") String cup, @Param("stati") List<String> stati, 
			@Param("annoCorrente") Integer annoCorrente);
	
	@Query("select i from Intervento i "
			+ " join RInterventoStato ris on i.intId = ris.intId "
			+ " join InterventoStato is on is.intStatoId = ris.intStatoId "
			+ " where (i.dataCancellazione is null or i.dataCancellazione >= current_timestamp) "
			+ " and ris.validitaInizio <= current_timestamp "
			+ " and (ris.validitaFine is null or ris.validitaFine >= current_timestamp) "
			+ " and (ris.dataCancellazione is null or ris.dataCancellazione >= current_timestamp) "
			+ " and is.validitaInizio <= current_timestamp "
			+ " and (is.validitaFine is null or is.validitaFine >= current_timestamp) "
			+ " and (is.dataCancellazione is null or is.dataCancellazione >= current_timestamp) "
			+ " and i.intAnno != :annoCorrente "
			+ " and (:intAnno is null or i.intAnno = :intAnno) "
			+ " and (:codice is null or i.intCod ilike %:codice%) "
			+ " and (:titolo is null or i.intTitolo ilike %:titolo%) "
			+ " and (:cup is null or i.intCup ilike %:cup%) "
			+ " and is.intStatoCod in :stati" 
			+ " and i.enteId = :enteId")
	List<Intervento> findAllSenzaCancellatiByFiltriAndStatiAndEnteId(@Param("intAnno") Integer intAnno, @Param("codice") String codice, 
			@Param("titolo") String titolo, @Param("cup") String cup, @Param("stati") List<String> stati, @Param("enteId") Integer enteId,
			@Param("annoCorrente") Integer annoCorrente);
	
	@Query("select i.intAnno as intAnno, i.intCod as intCod"
			+ " from Intervento i "
			+ " where (i.dataCancellazione is null or i.dataCancellazione >= current_timestamp) "
			+ " and i.intId = :intId " 
			+ " and i.enteId = :enteId")
	Map<String, Object> findAnnoAndCodiceByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);
	
	@Query("select i.intId from Intervento i "
			+ " join RInterventoStato ris on i.intId = ris.intId "
			+ " join InterventoStato is on is.intStatoId = ris.intStatoId "
			+ " where (i.dataCancellazione is null or i.dataCancellazione >= current_timestamp) " 
			+ " and ris.validitaInizio <= current_timestamp "
			+ " and (ris.validitaFine is null or ris.validitaFine >= current_timestamp) "
			+ " and (ris.dataCancellazione is null or ris.dataCancellazione >= current_timestamp) "
			+ " and is.validitaInizio <= current_timestamp "
			+ " and (is.validitaFine is null or is.validitaFine >= current_timestamp) "
			+ " and (is.dataCancellazione is null or is.dataCancellazione >= current_timestamp) "
			+ " and i.intCup = :intCup "
			+ " and is.intStatoCod != 'ANN'") 
	Optional<Integer> getIntIdByIntCup(@Param("intCup") String intCup);
}

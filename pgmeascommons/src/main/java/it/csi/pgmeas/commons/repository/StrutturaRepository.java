/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.Struttura;

@Repository
public interface StrutturaRepository extends JpaRepository<Struttura, Integer> {
	@Query("select a from Struttura a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "order by a.strDenominazione")
	List<Struttura> findAllValid();

	@Query("select a from Struttura a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.ente.enteCodEsteso = :enteCodEsteso "
			+ "order by a.strDenominazione")
	List<Struttura> findAllValidByCodice(@Param("enteCodEsteso") String enteCodEsteso);
	
	@Query("select a from Struttura a "
			+ "join InterventoStruttura b on a.strId = b.strId "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and (b.dataCancellazione is null or b.dataCancellazione >= current_timestamp) "
			+ "and b.intId = :intId "
			+ "order by a.strDenominazione")
	List<Struttura> findAllValidByIntId(@Param("intId") Integer intId);
	
	@Query("select a from Struttura a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.strId = :strId ")
	Struttura findValidByStrId(@Param("strId") Integer strId);
}

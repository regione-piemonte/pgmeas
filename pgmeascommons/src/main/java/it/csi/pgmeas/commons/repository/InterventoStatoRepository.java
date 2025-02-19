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

import it.csi.pgmeas.commons.model.InterventoStato;

@Repository
public interface InterventoStatoRepository extends JpaRepository<InterventoStato, Integer> {
	@Query("select a from InterventoStato a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp order by a.intStatoDesc")
	List<InterventoStato> findAllSenzaCancellati();
	
	@Query("SELECT a from InterventoStato a "
			+ "where a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine>=current_timestamp or a.validitaFine is null) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "order by a.intStatoDesc")
	List<InterventoStato> findAllValid();
	
	@Query("SELECT a from InterventoStato a "
			+ "where a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine>=current_timestamp or a.validitaFine is null) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.intStatoCod=:intStatoCod "
			+ "order by a.intStatoDesc")
	List<InterventoStato> findAllValidByIntStatoCod(@Param("intStatoCod") String intStatoCod);
	
	@Query("SELECT a from InterventoStato a "
			+ "join RInterventoStato b on a.intStatoId = b.intStatoId "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine >= current_timestamp or a.validitaFine is null) "
			+ "and (a.dataCancellazione >= current_timestamp or a.dataCancellazione is null) "
			+ "and b.validitaInizio <= current_timestamp "
			+ "and (b.validitaFine >= current_timestamp or b.validitaFine is null) "
			+ "and (b.dataCancellazione >= current_timestamp or b.dataCancellazione is null) "
			+ "and b.intId = :intId")
	InterventoStato findValidByIntId(@Param("intId") Integer intId);
}

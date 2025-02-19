/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.Ente;

@Repository
public interface EnteRepository extends JpaRepository<Ente, Integer> {
	@Query("select a from Ente a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.enteId = :id")
	Optional<Ente> findNonCancellatoById(@Param("id") Integer id);

	@Query("select a from Ente a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.enteCodEsteso = :cod")
	Optional<Ente> findNonCancellatoByCodEsteso(@Param("cod") String cod);
	
	@Query("select a from Ente a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.enteId = :id")
	Optional<Ente> findValidById(@Param("id") Integer id);

	@Query("select a from Ente a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.enteCodEsteso = :cod")
	Optional<Ente> findValidByCodEsteso(@Param("cod") String cod);
	
	@Query("select a from Ente a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "order by a.enteDesc")
	List<Ente> findAllValid();
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.csi.pgmeas.commons.model.REnteQuadrante;

public interface REnteQuadranteRepository extends JpaRepository<REnteQuadrante, Integer> {

	@Query("select a from REnteQuadrante a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) "
			+ "and a.enteId = :enteId")
	Optional<REnteQuadrante> findNonCancellatoByEnteId(@Param("enteId") Integer enteId);
	
	@Query("select a from REnteQuadrante a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.enteId = :enteId")
	Optional<REnteQuadrante> findValidByEnteId(@Param("enteId") Integer enteId);
	
}

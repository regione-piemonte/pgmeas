/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.dto.EnteQuadranteDto;
import it.csi.pgmeas.commons.model.Quadrante;

@Repository
public interface QuadranteRepository extends JpaRepository<Quadrante, Integer> {
	@Query("select a from Quadrante a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "order by a.quadranteDesc")
	List<Quadrante> findAllValid();
	
	
	 @Query("SELECT new it.csi.pgmeas.commons.dto.EnteQuadranteDto(e.enteId, e.enteDesc, q.quadranteId, q.quadranteCod, q.quadranteDesc) " +
	           "FROM Quadrante q " +
	           "JOIN REnteQuadrante r ON q.quadranteId = r.quadranteId " +
	           "JOIN Ente e ON e.enteId = r.enteId " +
	           "WHERE e.enteCodEsteso = :codEnte " +
	           "order by e.enteDesc")
	 Optional<EnteQuadranteDto> findEnteQuadranteByCodEnte(@Param("codEnte") String codEnte);
}

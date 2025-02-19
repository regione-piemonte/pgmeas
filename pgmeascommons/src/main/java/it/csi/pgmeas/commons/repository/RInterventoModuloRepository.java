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

import it.csi.pgmeas.commons.model.RInterventoModulo;

@Repository
public interface RInterventoModuloRepository extends JpaRepository<RInterventoModulo, Integer> {
	
	@Query("select a from RInterventoModulo a where "
			+ "(a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.intId = :intId "
			+ "and a.moduloId =:moduloId "
			+ "and a.enteId = :enteId")
	List<RInterventoModulo> findAllValidByIntIdAndModuloIdAndEnteId(@Param("intId") Integer intId, @Param("moduloId") Integer moduloId, @Param("enteId") Integer enteId);
	
	@Query("select a from RInterventoModulo a where "
			+ "(a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.intId = :intId "
			+ "and a.moduloId =:moduloId "
			+ "and a.moduloStatoId =:moduloStatoId "
			+ "and a.enteId = :enteId")
	List<RInterventoModulo> findAllValidByIntIdAndModuloIdAndModuloStatoIdAndEnteId(@Param("intId") Integer intId, 
			@Param("moduloId") Integer moduloId,@Param("moduloStatoId") Integer moduloStatoId, @Param("enteId") Integer enteId);
	
	@Query("select a from RInterventoModulo a where "
			+ "(a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.rIntModuloId = :rIntModuloId "
			+ "and a.enteId = :enteId")
	Optional<RInterventoModulo> findValidByRIntModuloIdAndEnteId(@Param("rIntModuloId") Integer rIntModuloId, @Param("enteId") Integer enteId);
	
	@Query("select a from RInterventoModulo a where "
			+ "(a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.rIntModuloId = :rIntModuloId "
			+ "and a.enteId = :enteId")
	Optional<RInterventoModulo> findByRIntModuloIdAndEnteId(@Param("rIntModuloId") Integer rIntModuloId, @Param("enteId") Integer enteId);
}

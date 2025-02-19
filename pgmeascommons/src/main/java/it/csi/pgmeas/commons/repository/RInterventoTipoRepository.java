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

import it.csi.pgmeas.commons.model.RInterventoTipo;

@Repository
public interface RInterventoTipoRepository extends JpaRepository<RInterventoTipo, Integer> {
	@Query("select a from RInterventoTipo a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<RInterventoTipo> findAllSenzaCancellati();

	@Query("select a from RInterventoTipo a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :intId and a.enteId = :enteId")
	List<RInterventoTipo> findAllSenzaCancellatiByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);
	
	@Query("select a from RInterventoTipo a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.intId = :intId "
			+ "and a.intTipoId = :intTipoId "
			+ "and a.enteId = :enteId")
	List<RInterventoTipo> findValidByIntIdAndIntTipoIdAndEnteId(@Param("intId") Integer intId, @Param("intTipoId") Integer intTipoId, @Param("enteId") Integer enteId);
	
	@Query("select a from RInterventoTipo a where "
			+ "(a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.intId = :intId "
			+ "and a.enteId = :enteId")
	List<RInterventoTipo> findAllValidByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);
}

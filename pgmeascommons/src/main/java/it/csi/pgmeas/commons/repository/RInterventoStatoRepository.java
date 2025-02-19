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

import it.csi.pgmeas.commons.model.RInterventoStato;

@Repository
public interface RInterventoStatoRepository extends JpaRepository<RInterventoStato, Integer> {
	@Query("select a from RInterventoStato a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<RInterventoStato> findAllSenzaCancellati();

	@Query("select a from RInterventoStato a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :intId and a.enteId = :enteId")
	List<RInterventoStato> findAllSenzaCancellatiByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);
	
	@Query("select a from RInterventoStato a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.intId = :intId "
			+ "and a.enteId = :enteId")
	RInterventoStato findValidByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);

	
	@Query("select a from RInterventoStato a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.rIntStatoId = :rIntStatoId "
			+ "and a.enteId = :enteId "
			+ "")
	RInterventoStato findValidByRIntStatoId(@Param("rIntStatoId") Integer rIntStatoId,@Param("enteId") Integer enteId);

}

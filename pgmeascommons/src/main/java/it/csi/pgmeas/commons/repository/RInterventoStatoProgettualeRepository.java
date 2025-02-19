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

import it.csi.pgmeas.commons.model.RInterventoStatoProgettuale;

@Repository
public interface RInterventoStatoProgettualeRepository extends JpaRepository<RInterventoStatoProgettuale, Integer> {
	@Query("select a from RInterventoStatoProgettuale a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<RInterventoStatoProgettuale> findAllSenzaCancellati();

	@Query("select a from RInterventoStatoProgettuale a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :intId and a.enteId = :enteId")
	List<RInterventoStatoProgettuale> findAllSenzaCancellatiByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);

	List<RInterventoStatoProgettuale> findByIntIdAndEnteId(Integer intId, Integer enteId);

	RInterventoStatoProgettuale findByIntIdAndIntStatoProgIdAndEnteId(Integer intId, Integer stato, Integer enteId);

	@Query("select a from RInterventoStatoProgettuale a where "
			+ "(a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ " and a.validitaInizio <= current_timestamp "
			+ " and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ " and a.intId = :intId "
			+ " and a.enteId = :enteId")
	List<RInterventoStatoProgettuale> findAllValidByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);

}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.IntStrInterventoEdilizioTs;

@Repository
public interface IntStrInterventoEdilizioTsRepository extends JpaRepository<IntStrInterventoEdilizioTs, Integer>{
	
	@Query("SELECT a FROM IntStrInterventoEdilizioTs a WHERE "
			+ "a.intstrId = :intStrId "
			+ "and a.enteId = :enteId "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "AND dataCancellazione is null ")
	IntStrInterventoEdilizioTs findValidByIntstrIdAndEnteId(@Param("intStrId") Integer intStrId, @Param("enteId") Integer enteId);
}

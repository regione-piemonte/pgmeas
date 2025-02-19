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

import it.csi.pgmeas.commons.model.IntStrDicAppaltabilitaTs;

@Repository
public interface IntStrDicAppaltabilitaTsRepository extends JpaRepository<IntStrDicAppaltabilitaTs, Integer> {
	@Query("select a from IntStrDicAppaltabilitaTs a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.classifTsId = :classifTsId")
	List<IntStrDicAppaltabilitaTs> findAllSenzaCancellatiByClassifTsId(@Param("classifTsId") Integer classifTsId);

	@Query("SELECT a FROM IntStrDicAppaltabilitaTs a WHERE "
			+ "a.intstrId = :intStrId "
			+ "and a.enteId = :enteId "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp)")
	IntStrDicAppaltabilitaTs findValidByIntstrIdAndEnteId(@Param("intStrId") Integer intStrId, @Param("enteId") Integer enteId);
}

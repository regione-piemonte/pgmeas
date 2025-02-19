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

import it.csi.pgmeas.commons.model.IntStrInterventoEdilizioTree;

@Repository
public interface IntStrInterventoEdilizioTreeRepository extends JpaRepository<IntStrInterventoEdilizioTree, Integer>{

	@Query("select ieTree from IntStrInterventoEdilizioTree ieTree "
			+ " join IntStrInterventoEdilizioTs ieTs on ieTree.intstrIeTsId = ieTs.intstrIeTsId "
			+ " where "
			+ " ieTs.validitaInizio<=current_timestamp "
			+ " and (ieTs.validitaFine is null or ieTs.validitaFine >= current_timestamp) "
			+ " and (ieTs.dataCancellazione is null or ieTs.dataCancellazione >= current_timestamp) "
			+ " and ieTree.validitaInizio <= current_timestamp "
			+ " and (ieTree.validitaFine is null or ieTree.validitaFine>=current_timestamp) "
			+ " and (ieTree.dataCancellazione is null or ieTree.dataCancellazione >= current_timestamp) "
			+ " and ieTs.intstrId = :intStrId "
			+ " and ieTs.enteId = :enteId "
			+ " and ieTree.enteId = :enteId")
	List<IntStrInterventoEdilizioTree> findAllValidByIntStrIdAndEnteId(@Param("intStrId") Integer intStrId, @Param("enteId") Integer enteId);

}

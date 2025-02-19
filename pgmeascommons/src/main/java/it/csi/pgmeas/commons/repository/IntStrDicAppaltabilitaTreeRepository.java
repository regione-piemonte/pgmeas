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

import it.csi.pgmeas.commons.model.IntStrDicAppaltabilitaTree;

@Repository
public interface IntStrDicAppaltabilitaTreeRepository extends JpaRepository<IntStrDicAppaltabilitaTree, Integer> {
	@Query("select a from IntStrDicAppaltabilitaTree a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<IntStrDicAppaltabilitaTree> findAllSenzaCancellati();

	IntStrDicAppaltabilitaTree findByClassifTreeIdAndEnteIdAndIntstrDaTsId(Integer k, Integer enteId,
			Integer intStrDicAppTs);
	
	@Query("select daTree from IntStrDicAppaltabilitaTree daTree "
			+ " join IntStrDicAppaltabilitaTs daTs on daTree.intstrDaTsId = daTs.intstrDaTsId "
			+ " where daTree.validitaInizio <= current_timestamp "
			+ " and (daTree.validitaFine is null or daTree.validitaFine >= current_timestamp) "
			+ " and (daTree.dataCancellazione is null or daTree.dataCancellazione >= current_timestamp) "
			+ " and daTs.validitaInizio <= current_timestamp "
			+ " and (daTs.validitaFine is null or daTs.validitaFine >= current_timestamp) "
			+ " and (daTs.dataCancellazione is null or daTs.dataCancellazione >= current_timestamp) "
			+ " and daTs.intstrId = :intStrId "
			+ " and daTree.enteId = :enteId "
			+ " and daTs.enteId = :enteId")
	List<IntStrDicAppaltabilitaTree> findAllValidByIntStrIdAndEnteId(@Param("intStrId") Integer intStrId, @Param("enteId") Integer enteId);

}

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

import it.csi.pgmeas.commons.model.IntStrQuadroEconTree;

@Repository
public interface InterventoStrutturaQuadroEconomicoTreeRepository extends JpaRepository<IntStrQuadroEconTree, Integer> {
	@Query("select a from IntStrQuadroEconTree a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<IntStrQuadroEconTree> findAllSenzaCancellati();

	@Query("select qeTree from IntStrQuadroEconTree qeTree "
			+ " join IntStrQuadroEconTs qeTs on qeTree.intstrQeTsId = qeTs.intstrQeTsId "
			+ " where "
			+ " qeTs.validitaInizio <= current_timestamp "
			+ " and (qeTs.validitaFine is null or qeTs.validitaFine >= current_timestamp) "
			+ " and (qeTs.dataCancellazione is null or qeTs.dataCancellazione >= current_timestamp) "
			+ " and qeTree.validitaInizio <= current_timestamp "
			+ " and (qeTree.validitaFine is null or qeTree.validitaFine >= current_timestamp) "
			+ " and (qeTree.dataCancellazione is null or qeTree.dataCancellazione >= current_timestamp) "
			+ " and qeTs.intstrId = :intStrId "
			+ " and qeTs.enteId = :enteId "
			+ " and qeTree.enteId = :enteId")
	List<IntStrQuadroEconTree> findAllValidByIntStrIdAndEnteId(@Param("intStrId") Integer intStrId, @Param("enteId") Integer enteId);
}

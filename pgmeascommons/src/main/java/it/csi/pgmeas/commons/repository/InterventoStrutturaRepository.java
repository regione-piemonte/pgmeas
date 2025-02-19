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

import it.csi.pgmeas.commons.model.InterventoStruttura;

@Repository
public interface InterventoStrutturaRepository extends JpaRepository<InterventoStruttura, Integer> {
	@Query("select a from InterventoStruttura a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<InterventoStruttura> findAllSenzaCancellati();
	
	@Query("select a from InterventoStruttura a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :intId")
	List<InterventoStruttura> findAllSenzaCancellatiByIntId(@Param("intId") Integer intId);

	@Query("select a from InterventoStruttura a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :intId and a.enteId = :enteId")
	List<InterventoStruttura> findAllSenzaCancellatiByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);
	
	@Query("select a from InterventoStruttura a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intStrId = :intStrId and a.enteId = :enteId")
	Optional<InterventoStruttura> findNonCancellatiByIntStrIdAndEnteId(@Param("intStrId") Integer intStrId, @Param("enteId") Integer enteId);
	
	InterventoStruttura findByIntStrId(@Param("intStrId") Integer intStrId);

}

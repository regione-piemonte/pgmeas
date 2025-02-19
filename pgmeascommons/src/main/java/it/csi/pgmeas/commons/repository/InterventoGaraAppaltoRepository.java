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

import it.csi.pgmeas.commons.model.InterventoGaraAppalto;

@Repository
public interface InterventoGaraAppaltoRepository extends JpaRepository<InterventoGaraAppalto, Integer> {
	
	@Query("select iga from InterventoGaraAppalto iga, InterventoStruttura s where (iga.dataCancellazione is null or iga.dataCancellazione > current_timestamp) and iga.intstrId = s.intStrId and s.intId = :intId")
	List<InterventoGaraAppalto> findAllSenzaCancellatiByIntId(@Param("intId") Integer intId);
}

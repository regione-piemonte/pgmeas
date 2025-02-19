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

import it.csi.pgmeas.commons.model.RInterventoFinanziamentoPrevSpesa;

@Repository
public interface RInterventoFinanziamentoPrevSpesaRepository
		extends JpaRepository<RInterventoFinanziamentoPrevSpesa, Integer> {
	@Query("select a from RInterventoFinanziamentoPrevSpesa a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.intId = :intId")
	List<RInterventoFinanziamentoPrevSpesa> findAllSenzaCancellatiByIntId(@Param("intId") Integer intId);

	public List<RInterventoFinanziamentoPrevSpesa> findByIntIdAndFinIdAndEnteIdAndIntFinPrevSpesaAnnoAndValiditaFineIsNull(
			Integer intId, Integer finId, Integer enteId, Integer intFinPrevSpesaAnno);
}

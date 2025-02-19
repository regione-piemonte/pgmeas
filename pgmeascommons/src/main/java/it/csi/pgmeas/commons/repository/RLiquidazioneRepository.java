/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.RLiquidazione;

@Repository
public interface RLiquidazioneRepository extends JpaRepository<RLiquidazione, Integer> {
	@Query("select a from RLiquidazione a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<RLiquidazione> findAllSenzaCancellati();

	RLiquidazione findByLiqRicIdAndEnteId(Integer liqRicId, Integer enteId);
}

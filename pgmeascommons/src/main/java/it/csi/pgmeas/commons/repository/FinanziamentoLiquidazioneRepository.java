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

import it.csi.pgmeas.commons.model.FinanziamentoLiquidazione;

@Repository
public interface FinanziamentoLiquidazioneRepository extends JpaRepository<FinanziamentoLiquidazione, Integer> {
	@Query("select fl from FinanziamentoLiquidazione fl, Finanziamento f where (fl.dataCancellazione is null or fl.dataCancellazione > current_timestamp) and fl.finId = f.finId and f.intId = :intId")
	List<FinanziamentoLiquidazione> findAllSenzaCancellatiByIntId(@Param("intId") Integer intId);
}

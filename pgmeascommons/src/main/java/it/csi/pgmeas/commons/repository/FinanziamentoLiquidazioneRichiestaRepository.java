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

import it.csi.pgmeas.commons.model.FinanziamentoLiquidazioneRichiesta;

@Repository
public interface FinanziamentoLiquidazioneRichiestaRepository
		extends JpaRepository<FinanziamentoLiquidazioneRichiesta, Integer> {
	@Query("select flr from FinanziamentoLiquidazioneRichiesta flr, Finanziamento f where (flr.dataCancellazione is null or flr.dataCancellazione > current_timestamp) and flr.finId = f.finId and f.intId = :intId")
	List<FinanziamentoLiquidazioneRichiesta> findAllSenzaCancellatiByIntId(@Param("intId") Integer intId);
}

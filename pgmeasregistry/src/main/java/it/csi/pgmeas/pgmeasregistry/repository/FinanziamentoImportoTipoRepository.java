/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.FinanziamentoImportoTipo;

@Repository
public interface FinanziamentoImportoTipoRepository extends JpaRepository<FinanziamentoImportoTipo, Integer> {
	@Query("select a from FinanziamentoImportoTipo a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "order by a.finImpTipoDesc")
	List<FinanziamentoImportoTipo> findAllValid();
	
}

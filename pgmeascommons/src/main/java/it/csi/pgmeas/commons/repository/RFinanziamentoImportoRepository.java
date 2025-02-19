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

import it.csi.pgmeas.commons.model.RFinanziamentoImporto;

@Repository
public interface RFinanziamentoImportoRepository extends JpaRepository<RFinanziamentoImporto, Integer> {

	@Query("select a from RFinanziamentoImporto a "
			+ "where a.validitaInizio >= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.finId = :finanziamentoId "
			+ "and a.enteId = :enteId")
	List<RFinanziamentoImporto> findAllValidByFinIdAndEnteId(@Param("finanziamentoId") Integer finanziamentoId, @Param("enteId") Integer enteId);
}

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

import it.csi.pgmeas.commons.model.Provvedimento;

@Repository
public interface ProvvedimentoRepository extends JpaRepository<Provvedimento, Integer> {
	@Query("select a from Provvedimento a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp")
	List<Provvedimento> findAllSenzaCancellati();

}

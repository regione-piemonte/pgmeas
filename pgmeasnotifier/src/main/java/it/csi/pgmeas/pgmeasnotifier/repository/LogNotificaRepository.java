/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.pgmeasnotifier.model.LogNotifica;

@Repository
public interface LogNotificaRepository extends JpaRepository<LogNotifica, Integer> {

	

}

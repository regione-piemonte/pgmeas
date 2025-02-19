/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.InterventoStorico;

@Repository
public interface InterventoStoricoRepository extends JpaRepository<InterventoStorico, Integer>{

}

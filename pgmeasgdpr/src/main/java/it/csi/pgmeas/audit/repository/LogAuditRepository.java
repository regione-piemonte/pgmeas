/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.audit.model.LogAudit;


@Repository
public interface LogAuditRepository extends JpaRepository<LogAudit, Integer>{

}

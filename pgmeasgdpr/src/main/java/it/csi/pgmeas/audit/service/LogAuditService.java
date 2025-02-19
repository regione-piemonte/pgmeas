/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.service;

import org.springframework.http.ResponseEntity;

import it.csi.pgmeas.commons.util.record.Audit;


public interface LogAuditService {
	public ResponseEntity<?> createAudit(Audit audit);

}

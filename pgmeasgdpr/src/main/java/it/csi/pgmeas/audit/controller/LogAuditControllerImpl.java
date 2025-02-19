/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.audit.service.LogAuditService;
import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.be.LogAuditApi;
import it.csi.pgmeas.commons.util.record.Audit;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class LogAuditControllerImpl extends BaseApiController implements LogAuditApi {
	@Autowired
	LogAuditService logAuditService;

	@Override
	public ResponseEntity<?> createAudit(HttpServletRequest httpRequest, Audit request) {
		ResponseEntity<?> value = logAuditService.createAudit(request);
		return value;
	}

}

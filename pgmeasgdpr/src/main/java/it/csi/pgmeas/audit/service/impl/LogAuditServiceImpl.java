/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.audit.model.LogAudit;
import it.csi.pgmeas.audit.repository.LogAuditRepository;
import it.csi.pgmeas.audit.service.LogAuditService;
import it.csi.pgmeas.commons.util.record.Audit;

@Component
public class LogAuditServiceImpl implements LogAuditService {

	private static final Logger LOG = LoggerFactory.getLogger(LogAuditServiceImpl.class);

	@Autowired
	LogAuditRepository auditRepository;

	private LogAudit saveAudit(Audit audit) {
		LogAudit logAudit = new LogAudit();
		logAudit.setRequestPayload(audit.body());
		logAudit.setResponsePayload(audit.response());
		logAudit.setDataOra(audit.dataOra());
		logAudit.setIdApp(audit.idApp());
		logAudit.setIpAddress(audit.ipAddress());
		logAudit.setUtente(audit.fiscalCode());
		logAudit.setEsitoChiamata(audit.status());
		logAudit.setOperazione(audit.method());
		logAudit.setUuid(audit.uuid());
		auditRepository.save(logAudit);
		return logAudit;
	}

	@Override
	public ResponseEntity<?> createAudit(Audit audit) {
		try {
			LogAudit logAudit = this.saveAudit(audit);
			return ResponseEntity.ok().body(logAudit);
		} catch (Exception e) {
			LOG.error("[proxy generic error] - {}", e);
			return ResponseEntity.internalServerError() 
					.body("Errore di sistema (Connection Error)");
		}

	}

}

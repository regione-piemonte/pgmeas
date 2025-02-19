/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.be.WorkflowV1Api;
import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasRuntimeException;
import it.csi.pgmeas.pgmeasproject.service.WorkflowV1Service;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class WorkFlowV1ApiControllerImpl extends BaseApiController implements WorkflowV1Api {

	private static final Logger log = LoggerFactory.getLogger(WorkFlowV1ApiControllerImpl.class);

	@Autowired
	private WorkflowV1Service workflowV1Service;

	@Override
	public ResponseEntity<?> putInterventoInvia(AllegatoLightExtDto allegatoIntervento, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		try {
			return ResponseEntity.ok()
					.body(workflowV1Service.putInterventoInvia(allegatoIntervento, interventoId, httpRequest))
					;
		} catch (PgmeasException e) {
			log.error(e.getMessage());
			return handlePgmeasException(e);
		} catch (PgmeasRuntimeException e) {
			log.error(e.getMessage());
			return handlePgmeasRuntimeException(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> putInterventoApprova(AllegatoLightExtDto allegatoIntervento, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		try {
			return ResponseEntity.ok()
					.body(workflowV1Service.putInterventoApprova(allegatoIntervento, interventoId, httpRequest))
					;
		} catch (PgmeasException e) {
			log.error(e.getMessage());
			return handlePgmeasException(e);
		} catch (PgmeasRuntimeException e) {
			log.error(e.getMessage());
			return handlePgmeasRuntimeException(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> putInterventoRifiuta(RespingimentoDto rifiutaInterventoDto, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		try {
			return ResponseEntity.ok()
					.body(workflowV1Service.putInterventoRifiuta(rifiutaInterventoDto, interventoId, httpRequest))
					;
		} catch (PgmeasException e) {
			log.error(e.getMessage());
			return handlePgmeasException(e);
		} catch (PgmeasRuntimeException e) {
			log.error(e.getMessage());
			return handlePgmeasRuntimeException(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> putInterventoElimina(Integer interventoId, HttpServletRequest httpRequest) throws Exception {
		try {
			return ResponseEntity.ok().body(workflowV1Service.putInterventoElimina(interventoId, httpRequest));
		} catch (PgmeasException pe) {
			return handlePgmeasException(pe);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
}

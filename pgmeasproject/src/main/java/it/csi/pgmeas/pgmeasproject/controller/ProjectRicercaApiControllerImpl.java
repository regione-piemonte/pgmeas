/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.be.ProjectRicercaApi;
import it.csi.pgmeas.commons.dto.v2.RicercaInterventiFilterDto;
import it.csi.pgmeas.commons.dto.v2.RicercaInterventiResultDto;
import it.csi.pgmeas.commons.dto.v2.RicercaModuloAResultDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.pgmeasproject.service.RicercaService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectRicercaApiControllerImpl extends BaseApiController implements ProjectRicercaApi {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectRicercaApiControllerImpl.class);

	@Autowired RicercaService ricercaService;
	
	@Override
	public ResponseEntity<?> ricercaByAllFilters(HttpServletRequest httpRequest, RicercaInterventiFilterDto filters) {
		try {
			List<RicercaInterventiResultDto> result = ricercaService.ricercaByAllFilters(httpRequest, filters);
			return ResponseEntity.ok().body(result);
		} catch (PgmeasException e) {
			log.error(e.getMessage());
			return handlePgmeasException(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getInterventiByAllFilters(HttpServletRequest httpRequest, RicercaInterventiFilterDto filters) {
		try {
			List<RicercaInterventiResultDto> result = ricercaService.getInterventiByAllFilters(httpRequest, filters);
			return ResponseEntity.ok().body(result);
		} catch (PgmeasException e) {
			log.error(e.getMessage());
			return handlePgmeasException(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventiModuloAByAllFilters(HttpServletRequest httpRequest,
			RicercaInterventiFilterDto filters) {
		try {
			List<RicercaModuloAResultDto> result = ricercaService.getInterventiModuloAByAllFilters(httpRequest, filters);
			return ResponseEntity.ok().body(result);
		} catch (PgmeasException e) {
			log.error(e.getMessage());
			return handlePgmeasException(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
}

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
import it.csi.pgmeas.commons.be.ProjectInterventoV2Api;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToSaveModel;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.pgmeasproject.service.InterventoV2Service;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectInterventoV2ApiControllerImpl extends BaseApiController implements ProjectInterventoV2Api {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectInterventoV2ApiControllerImpl.class);

	@Autowired InterventoV2Service interventoV2Service;
	
	@Override
	public ResponseEntity<?> postInterventoV2(InterventoToSaveModel body, HttpServletRequest httpRequest) throws Exception {
		try {
			InterventoToSaveModel result = interventoV2Service.postInterventoV2(body, httpRequest);
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
	public ResponseEntity<?> getInterventiByFilters(HttpServletRequest httpRequest, Integer anno, String codice, String titolo,
			String cup) {
		try {
			List<InterventoV2GetDto> result = interventoV2Service.getInterventiByFilters(httpRequest, anno, codice, titolo, cup);
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
	public ResponseEntity<?> getInterventiStrutturaByIntId(HttpServletRequest httpRequest, Integer intId, boolean copy) {
		try {
			List<InterventoStrutturaV2GetDto> result = interventoV2Service.getInterventiStrutturaByIntId(httpRequest, intId, copy);
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
	public ResponseEntity<?> putInterventoV2(InterventoToPutModel body, Integer interventoId, HttpServletRequest httpRequest)
			throws Exception {
		try {
			InterventoToPutModel result = interventoV2Service.putInterventoV2(body, interventoId, httpRequest);
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
	public ResponseEntity<?> getIntervento(HttpServletRequest httpRequest, Integer interventoId) {
		try {
			InterventoV2GetDto result = interventoV2Service.getIntervento(httpRequest, interventoId);
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
	public ResponseEntity<?> putInterventoV2Regione(InterventoToPutByRegioneModel body, Integer interventoId,
			HttpServletRequest httpRequest) {
		try {
			InterventoToPutByRegioneModel result = interventoV2Service.putInterventoV2ByRegione(body, interventoId, httpRequest);
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

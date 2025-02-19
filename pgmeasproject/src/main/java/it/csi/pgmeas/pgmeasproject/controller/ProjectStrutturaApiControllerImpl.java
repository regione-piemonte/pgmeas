/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.be.ProjectStrutturaApi;
import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.dto.v2.StrutturaNewDto;
import it.csi.pgmeas.pgmeasproject.service.StrutturaService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectStrutturaApiControllerImpl extends BaseApiController implements ProjectStrutturaApi {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectStrutturaApiControllerImpl.class);

	@Autowired private StrutturaService strutturaService;

	@Override
	public ResponseEntity<?> postStruttura(HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			StrutturaNewDto body) throws Exception {
		try {
			StrutturaDto result = strutturaService.postStruttura(body, httpRequest);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
}

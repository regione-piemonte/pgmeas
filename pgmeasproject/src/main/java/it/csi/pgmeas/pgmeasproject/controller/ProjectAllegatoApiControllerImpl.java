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
import it.csi.pgmeas.commons.be.ProjectAllegatoApi;
import it.csi.pgmeas.pgmeasproject.dto.FileDto;
import it.csi.pgmeas.pgmeasproject.service.AllegatoService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectAllegatoApiControllerImpl extends BaseApiController implements ProjectAllegatoApi {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectAllegatoApiControllerImpl.class);

	@Autowired private AllegatoService allegatoService;
	
	@Override
	public ResponseEntity<?> downloadAllegatoById(HttpHeaders httpHeaders,
			HttpServletRequest httpRequest, Integer id) throws Exception {
		try {
			FileDto result = allegatoService.downloadAllegatoById(id);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
}

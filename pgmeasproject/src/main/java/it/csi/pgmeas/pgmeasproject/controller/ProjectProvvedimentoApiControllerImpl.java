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
import it.csi.pgmeas.commons.be.ProjectProvvedimentoApi;
import it.csi.pgmeas.pgmeasproject.dto.ProvvedimentoResultDto;
import it.csi.pgmeas.pgmeasproject.service.ProvvedimentoService;

//TODO used?
@Component
public class ProjectProvvedimentoApiControllerImpl extends BaseApiController implements ProjectProvvedimentoApi {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectProvvedimentoApiControllerImpl.class);

	@Autowired ProvvedimentoService provvedimentoService;
	
	@Override
	public ResponseEntity<?> getAllData() {
		try {
			List<ProvvedimentoResultDto> result = provvedimentoService.getAllData();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

}

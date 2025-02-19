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
import it.csi.pgmeas.commons.be.ProjectModuloApi;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.pgmeasproject.dto.FileDto;
import it.csi.pgmeas.pgmeasproject.service.ModuloService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectModuloApiControllerImpl extends BaseApiController implements ProjectModuloApi {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectModuloApiControllerImpl.class);
	
	@Autowired 
	ModuloService moduloService;

	@Override
	public ResponseEntity<?> downloadModuloByRIntModuloId(HttpHeaders httpHeaders,
			HttpServletRequest httpRequest, Integer rIntModuloId, Integer interventoId) throws Exception {
		try {
			FileDto result = moduloService.downloadModuloByRIntModuloIdAndInterventoId(rIntModuloId, interventoId);
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

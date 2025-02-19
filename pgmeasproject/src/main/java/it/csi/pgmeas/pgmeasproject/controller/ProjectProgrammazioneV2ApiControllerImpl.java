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
import it.csi.pgmeas.commons.be.ProjectProgrammazioneV2Api;
import it.csi.pgmeas.commons.dto.EnteProrogaDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneBaseDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneProrogaDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasRuntimeException;
import it.csi.pgmeas.commons.util.record.InfoProgrammazione;
import it.csi.pgmeas.pgmeasproject.service.ProgrammazioneService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectProgrammazioneV2ApiControllerImpl extends BaseApiController implements ProjectProgrammazioneV2Api {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectProgrammazioneV2ApiControllerImpl.class);

	@Autowired private ProgrammazioneService programmazioneService;

	@Override
	public ResponseEntity<?> getInfoProgrammazioneForUser(String enteCod) {
		try {
			InfoProgrammazione result = programmazioneService.getInfoProgrammazioneForUser(enteCod);
			if(result == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getProgrammazione(String anno) {
		try {
			ProgrammazioneDto result = programmazioneService.getProgrammazione(anno);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getProrogaEnte(String anno, String enteCod) {
		try {
			EnteProrogaDto result = programmazioneService.getProrogaEnte(anno, enteCod);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> inserisciProrogaTuttiEnti(ProgrammazioneProrogaDto request, HttpServletRequest httpRequest, String anno)
			throws Exception {
		try {
			ProgrammazioneDto result = programmazioneService.inserisciProrogaTuttiEnti(request, httpRequest, anno);
			return ResponseEntity.ok().body(result);
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
	public ResponseEntity<?> deleteProgrammazione(ProgrammazioneProrogaDto request, HttpServletRequest httpRequest, String anno)
			throws Exception {
		try {
			programmazioneService.deleteProgrammazione(request, anno);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> insertProgrammazione(ProgrammazioneBaseDto request, HttpServletRequest httpRequest) throws Exception {
		try {
			ProgrammazioneDto result = programmazioneService.insertProgrammazione(request, httpRequest);
			return ResponseEntity.ok().body(result);
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
	public ResponseEntity<?> getElencoProgrammazioni() throws Exception {
		try {
			List<ProgrammazioneBaseDto> result = programmazioneService.getElencoProgrammazioni();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> insertProroga(EnteProrogaDto request, HttpServletRequest httpRequest, String anno, String enteCod) {
		try {
			EnteProrogaDto result = programmazioneService.insertProroga(request, httpRequest, anno, enteCod);
			return ResponseEntity.ok().body(result);
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
}

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.be.ProjectModuloAApi;
import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RInterventoModuloDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoPutDto;
import it.csi.pgmeas.commons.dto.v2.ModuloAPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.ModuloIntervento;
import it.csi.pgmeas.commons.dto.v2.ModuloInterventoStruttura;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasRuntimeException;
import it.csi.pgmeas.pgmeasproject.service.ModuloAService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectModuloAApiControllerImpl extends BaseApiController implements ProjectModuloAApi {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectModuloAApiControllerImpl.class);
	
	@Autowired 
	ModuloAService moduloService;

	@Override
	public ResponseEntity<?> postModuloA(HttpServletRequest httpRequest, 
			RichiestaAmmissioneFinanziamentoDto request) throws Exception {
		try {
			RInterventoModuloDto result = moduloService.postModuloA(request, httpRequest);
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
	public ResponseEntity<?> putModuloA(HttpHeaders httpHeaders,
			HttpServletRequest httpRequest, Integer rIntModuloId,
			RichiestaAmmissioneFinanziamentoPutDto request) throws Exception {
		try {
			moduloService.putModuloA(rIntModuloId, request, httpRequest);
			return ResponseEntity.ok().body(null);
		} catch (PgmeasException e) {
			log.error(e.getMessage());
			return handlePgmeasException(e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getModuloAIntervento(HttpHeaders httpHeaders,
			HttpServletRequest httpRequest, Integer interventoId) throws Exception {
		try {
			ModuloIntervento result = moduloService.getModuloAIntervento(interventoId, httpRequest);
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
	public ResponseEntity<?> getModuloAInterventoStruttura(HttpHeaders httpHeaders,
			HttpServletRequest httpRequest, Integer interventoId) throws Exception {
		try {
			List<ModuloInterventoStruttura> result = moduloService.getModuloAInterventiStruttura(interventoId, httpRequest);
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
	public ResponseEntity<?> putModuloARegione(ModuloAPutByRegioneModel body, Integer rIntModuloId,
			HttpServletRequest httpRequest) throws Exception {
		try {
			ModuloAPutByRegioneModel result = moduloService.putModuloARegione(body, rIntModuloId, httpRequest);
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
	public ResponseEntity<?> putModuloAInvia(Integer rIntModuloId, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		try {
			Integer result = moduloService.putModuloAInvia(rIntModuloId, interventoId, httpRequest);
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
	public ResponseEntity<?> putModuloAApprova(Integer rIntModuloId, Integer interventoId, AllegatoLightExtDto allegatoModuloA, 
			HttpServletRequest httpRequest) throws Exception {
		try {
			Integer result = moduloService.putModuloAApprova(rIntModuloId, interventoId, allegatoModuloA, httpRequest);
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
	public ResponseEntity<?> putModuloARespinge(RespingimentoDto respingimentoDto, Integer rIntModuloId, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		try {
			Integer result = moduloService.putModuloARespinge(respingimentoDto, rIntModuloId, interventoId, httpRequest);
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

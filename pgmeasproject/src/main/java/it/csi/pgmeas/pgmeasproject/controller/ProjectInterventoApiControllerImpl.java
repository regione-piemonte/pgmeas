/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.controller;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.be.ProjectInterventoApi;
import it.csi.pgmeas.commons.dto.AllegatoDto;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneDto;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneRichiestaDto;
import it.csi.pgmeas.commons.dto.FinanziamentoResultDto;
import it.csi.pgmeas.commons.dto.InterventoFinanziamentoPrevSpesaDto;
import it.csi.pgmeas.commons.dto.InterventoGaraAppaltoDto2;
import it.csi.pgmeas.commons.dto.InterventoResultDto;
import it.csi.pgmeas.commons.dto.InterventoStrutturaDto;
import it.csi.pgmeas.commons.dto.MonitoraggioDto;
import it.csi.pgmeas.pgmeasproject.service.FinanziamentoService;
import it.csi.pgmeas.pgmeasproject.service.InterventoService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ProjectInterventoApiControllerImpl extends BaseApiController implements ProjectInterventoApi {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectInterventoApiControllerImpl.class);

	@Autowired InterventoService interventoService;
	@Autowired FinanziamentoService finanziamentoService;

	@Override
	public ResponseEntity<?> getAllData() {
		try {
			List<FinanziamentoResultDto> result = finanziamentoService.getAllData();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getFinanziamentiByIntId(Integer intId) {
		try {
			List<FinanziamentoResultDto> result = finanziamentoService.getFinanziamentiByIntId(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> inserimentoDatiMonitoraggio(MonitoraggioDto richiestaMonitoraggioDto, HttpServletRequest httpRequest)
			throws Exception {
		try {
			AllegatoDto result = finanziamentoService.inserimentoDatiMonitoraggio(richiestaMonitoraggioDto, httpRequest);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getIntDataValidDicAppalto(Integer classifTreeId, Integer enteId, Integer intstrDaTsId) {
		try {
			Timestamp result = finanziamentoService.getIntDataValidDicAppalto(classifTreeId, enteId, intstrDaTsId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoByAnno(Integer anno) {
		try {
			List<InterventoResultDto> result = interventoService.getInterventoByAnno(anno);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoById(Integer id) {
		try {
			InterventoResultDto result = interventoService.getInterventoById(id);
			if(result == null) {
				return ResponseEntity.badRequest().body("Risorsa non trovata per ID: %d".formatted(id));
			}
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoStrutturaByIdInt(Integer intId) {
		try {
			List<InterventoStrutturaDto> result = interventoService.getInterventoStrutturaByIdInt(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoFinanziamentoPrevSpesaByIdInt(Integer intId) {
		try {
			List<InterventoFinanziamentoPrevSpesaDto> result = interventoService.getInterventoFinanziamentoPrevSpesaByIdInt(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getFinanziamentoLiquidazioneByIdInt(Integer intId) {
		try {
			List<FinanziamentoLiquidazioneDto> result = interventoService.getFinanziamentoLiquidazioneByIdInt(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getFinanziamentoLiquidazioneRichiestaByIdInt(Integer intId) {
		try {
			List<FinanziamentoLiquidazioneRichiestaDto> result = interventoService.getFinanziamentoLiquidazioneRichiestaByIdInt(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoGaraAppaltoByIdInt(Integer intId) {
		try {
			List<InterventoGaraAppaltoDto2> result = interventoService.getInterventoGaraAppaltoByIdInt(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoAllegatoByIdInt(Integer intId) {
		try {
			List<AllegatoDto> result = interventoService.getInterventoAllegatoByIdInt(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getAnniIntervento() {
		try {
			List<Integer> result = interventoService.getAnniIntervento();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getQePerIdIntervento(Integer intId) {
		try {
			List<InterventoStrutturaDto> result = interventoService.getQePerIdIntervento(intId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
}

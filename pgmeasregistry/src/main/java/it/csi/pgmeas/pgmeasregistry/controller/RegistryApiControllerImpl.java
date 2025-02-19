/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.be.RegistryApi;
import it.csi.pgmeas.commons.dto.AllegatoTipoDto;
import it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto;
import it.csi.pgmeas.commons.dto.EnteDto;
import it.csi.pgmeas.commons.dto.EnteQuadranteDto;
import it.csi.pgmeas.commons.dto.EnteTipoDto;
import it.csi.pgmeas.commons.dto.FaseDto;
import it.csi.pgmeas.commons.dto.FinanziamentoImpTipoDto;
import it.csi.pgmeas.commons.dto.FinanziamentoTipoDetDto;
import it.csi.pgmeas.commons.dto.FinanziamentoTipoDto;
import it.csi.pgmeas.commons.dto.InterventoAppaltoTipoDto;
import it.csi.pgmeas.commons.dto.InterventoCategoriaDto;
import it.csi.pgmeas.commons.dto.InterventoContrattoTipoDto;
import it.csi.pgmeas.commons.dto.InterventoFinalitaDto;
import it.csi.pgmeas.commons.dto.InterventoObiettivoDto;
import it.csi.pgmeas.commons.dto.InterventoStatoDto;
import it.csi.pgmeas.commons.dto.InterventoStatoProgettualeDto;
import it.csi.pgmeas.commons.dto.InterventoTipoDetDto;
import it.csi.pgmeas.commons.dto.InterventoTipoDto;
import it.csi.pgmeas.commons.dto.ModuloDto;
import it.csi.pgmeas.commons.dto.ModuloStatoDto;
import it.csi.pgmeas.commons.dto.OrganoDto;
import it.csi.pgmeas.commons.dto.ParametroDto;
import it.csi.pgmeas.commons.dto.ParametroTipoDto;
import it.csi.pgmeas.commons.dto.PianoTriennaleDto;
import it.csi.pgmeas.commons.dto.ProvvedimentoLivelloDto;
import it.csi.pgmeas.commons.dto.ProvvedimentoTipoDto;
import it.csi.pgmeas.commons.dto.QuadranteDto;
import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.pgmeasregistry.service.AllegatoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.ClassificazioneTreeService;
import it.csi.pgmeas.pgmeasregistry.service.EnteService;
import it.csi.pgmeas.pgmeasregistry.service.EnteTipoService;
import it.csi.pgmeas.pgmeasregistry.service.FaseService;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoImportoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoService;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoTipoDetService;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoAppaltoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoCategoriaService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoContrattoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoFinalitaService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoObiettivoService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoStatoProgettualeService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoStatoService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoTipoDetService;
import it.csi.pgmeas.pgmeasregistry.service.InterventoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.ModuloService;
import it.csi.pgmeas.pgmeasregistry.service.ModuloStatoService;
import it.csi.pgmeas.pgmeasregistry.service.OrganoService;
import it.csi.pgmeas.pgmeasregistry.service.ParametroService;
import it.csi.pgmeas.pgmeasregistry.service.ParametroTipoService;
import it.csi.pgmeas.pgmeasregistry.service.PianoTriennaleService;
import it.csi.pgmeas.pgmeasregistry.service.ProvvedimentoLivelloService;
import it.csi.pgmeas.pgmeasregistry.service.ProvvedimentoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.QuadranteService;
import it.csi.pgmeas.pgmeasregistry.service.StrutturaService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RegistryApiControllerImpl extends BaseApiController implements RegistryApi {

	private static final Logger log = LoggerFactory.getLogger(RegistryApiControllerImpl.class);

	@Autowired
	ParametroService parametroService;
	@Autowired
	ParametroTipoService parametroTipoService;
	@Autowired
	AllegatoTipoService allegatoTipoService;
	@Autowired
	ClassificazioneTreeService classificazioneTreeService;
	@Autowired
	EnteService enteService;
	@Autowired
	EnteTipoService enteTipoService;
	@Autowired
	FaseService faseService;
	@Autowired
	PianoTriennaleService pianoTriennaleService;
	@Autowired
	InterventoObiettivoService interventoObiettivoService;
	@Autowired
	InterventoFinalitaService interventoFinalitaService;
	@Autowired
	InterventoCategoriaService interventoCategoriaService;
	@Autowired
	InterventoStatoService interventoStatoService;
	@Autowired
	InterventoStatoProgettualeService interventoStatoProgettualeService;
	@Autowired
	InterventoTipoService interventoTipoService;
	@Autowired
	InterventoTipoDetService interventoTipoDetService;
	@Autowired
	ModuloService moduloService;
	@Autowired
	ModuloStatoService moduloStatoService;
	@Autowired
	OrganoService organoService;
	@Autowired
	StrutturaService strutturaService;
	@Autowired
	FinanziamentoService finanziamentoService;
	@Autowired
	FinanziamentoImportoTipoService finanziamentoImportoTipoService;
	@Autowired
	FinanziamentoTipoService finanziamentoTipoService;
	@Autowired
	FinanziamentoTipoDetService finanziamentoTipoDetService;
	@Autowired
	InterventoContrattoTipoService interventoContrattoTipoService;
	@Autowired
	QuadranteService quadranteService;
	@Autowired
	InterventoAppaltoTipoService interventoAppaltoTipoService;
	@Autowired
	ProvvedimentoLivelloService provvedimentoLivelloService;
	@Autowired
	ProvvedimentoTipoService provvedimentoTipoService;

	@Override
	public ResponseEntity<?> getParametro(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<ParametroDto> result = parametroService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getParametroTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<ParametroTipoDto> result = parametroTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getTipoAllegato(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<AllegatoTipoDto> result = allegatoTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getEnte(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<EnteDto> result = enteService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getEnteTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<EnteTipoDto> result = enteTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getFase(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<FaseDto> result = faseService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getFinanziamentoByIntId(Integer intId, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<it.csi.pgmeas.commons.dto.v2.FinanziamentoTipoDto> result = finanziamentoService.getDataByInterventoId(intId);
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
	public ResponseEntity<?> getFinanziamentoTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<FinanziamentoTipoDto> result = finanziamentoTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getFinanziamentoTipoDet(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<FinanziamentoTipoDetDto> result = finanziamentoTipoDetService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getFinanziamentoImportoTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<FinanziamentoImpTipoDto> result = finanziamentoImportoTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getInterventoAppaltoTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoAppaltoTipoDto> result = interventoAppaltoTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoCategoria(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoCategoriaDto> result = interventoCategoriaService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoContrattoTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoContrattoTipoDto> result = interventoContrattoTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoFinalita(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoFinalitaDto> result = interventoFinalitaService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoObiettivo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoObiettivoDto> result = interventoObiettivoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoStato(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoStatoDto> result = interventoStatoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoStatoProgettuale(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoStatoProgettualeDto> result = interventoStatoProgettualeService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoTipoDto> result = interventoTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoTipoDett(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<InterventoTipoDetDto> result = interventoTipoDetService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getModuloStato(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<ModuloStatoDto> result = moduloStatoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getModuloTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<ModuloDto> result = moduloService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getOrgano(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<OrganoDto> result = organoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getPianoTriennale(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<PianoTriennaleDto> result = pianoTriennaleService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getProvvedimentoLivello(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<ProvvedimentoLivelloDto> result = provvedimentoLivelloService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getProvvedimentoTipo(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<ProvvedimentoTipoDto> result = provvedimentoTipoService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getQuadrante(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<QuadranteDto> result = quadranteService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getStruttura(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			List<StrutturaDto> result = strutturaService.getAllValid();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getStrutturaByCodice(String codStruttura, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		try {
			List<StrutturaDto> result = strutturaService.getStrutturaByCodice(codStruttura);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getStrutturaByInterventoId(Integer interventoId, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		try {
			List<StrutturaDto> result = strutturaService.getStrutturaByInterventoId(interventoId);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getInterventoTipoDettByIntervTipoCod(String intTipoCod, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		try {
			List<InterventoTipoDetDto> result = interventoTipoDetService
					.getIntervTipoDettByCodiceIntervTipo(intTipoCod);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getClassifTreeByClassifTsTipoCod(String classifTsTipoCod, boolean view, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		try {
			List<ClassificazioneTreeByClassTsTipoDto> result = classificazioneTreeService
					.getClassifTreeByClassifTsTipoCod(classifTsTipoCod, view);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getQuadranteByCodEnte(String codEnte, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		try {
			Optional<EnteQuadranteDto> oq = quadranteService.getEnteQuadranteByCodEnte(codEnte);
			EnteQuadranteDto result = oq.orElseThrow(() -> new PgmeasException("non trovato"));
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}
	
	@Override
	public ResponseEntity<?> getEnteByEnteCod(String codEnte, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			EnteDto ente = enteService.getEnteByCodEnte(codEnte);
			return ResponseEntity.ok().body(ente);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

	@Override
	public ResponseEntity<?> getErrori(HttpServletRequest httpRequest) {
		try {
			List<ParametroDto> result = parametroService.getErrori();
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			return handleException(e);
		}
	}

}

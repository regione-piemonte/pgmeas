/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service;

import static it.csi.pgmeas.commons.validation.ValidationUtils.checkEntityIsPresentByProperty;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.EventoTipoDecodedDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.NotificaStato;
import it.csi.pgmeas.commons.model.Parametro;
import it.csi.pgmeas.commons.repository.ParametroRepository;
import it.csi.pgmeas.commons.service.EventoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.CategoriaErroreEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.NotificaStatoEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.ParametroBatchEnum;
import it.csi.pgmeas.pgmeasnotifier.repository.NotificaStatoRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DecodificheService {

	private static final Logger log = LoggerFactory.getLogger(DecodificheService.class);
	private EventoUtilityService eventoUtilityService;
	private NotificaStatoRepository notificaStatoRepository;
	private ParametroRepository parametroRepository;
	private ErroreService erroreService;

	private List<EventoTipoDecodedDto> dettaglioEventoTipoList;
	private List<NotificaStato> notificaStato;
	

	public DecodificheService(EventoUtilityService eventoUtilityService,
			NotificaStatoRepository notificaStatoRepository,
			ParametroRepository parametroRepository,
			ErroreService erroreService) {
		this.eventoUtilityService = eventoUtilityService;
		this.notificaStatoRepository = notificaStatoRepository;
		this.parametroRepository=parametroRepository;
		this.erroreService=erroreService;
	}

	@PostConstruct
	public void init() {
		aggiornaDecodifiche();
	}

	public void aggiornaDecodifiche() {
		try {
			Parametro p = getParametroByCode(ParametroBatchEnum.BATCH_LOOKUP);
			if (Boolean.parseBoolean(p.getParametroValore())) {
				caricaListaEventoTipoDecodedDto();
				caricaDecodificaNotificaStato();
			} else {
				log.info("Batch aggiorna decodifiche spento");
			}
		} catch (Exception e) {

			log.error("Exception Aggiorna decodifiche {}",e.getMessage(), e);
			erroreService.traceError(e, CategoriaErroreEnum.GENERICO);
		}

	}

	public void caricaDecodificaNotificaStato() {
		log.info("carica Lista DecodificaNotificaStato ");
		this.notificaStato = notificaStatoRepository.findAllValid();
	}

	public void caricaListaEventoTipoDecodedDto() {
		log.info("carica Lista EventoTipoDecodedDto ");
		this.dettaglioEventoTipoList = eventoUtilityService.findAllEventoTipoDecodedValidi();
	}

	public EventoTipoDecodedDto findByEventoTipoCod(String tipo) throws Exception {
		EventoTipoDecodedDto result = this.dettaglioEventoTipoList.stream()
				.filter(dto -> tipo.equals(dto.getEventoTipoCod())).findFirst().orElse(null); // Ritorna null se nessun
																								// elemento corrisponde

		if (result != null) {
			return result;
		} else {
			throw new PgmeasException("Tipo di notifica non supportato: " + tipo);
		}

	}

	public Integer getNotificaStatoFrom(NotificaStatoEnum stato) throws Exception {
		NotificaStato result = this.notificaStato.stream()
				.filter(dto -> stato.getCode().equals(dto.getNotificaStatoCod())).findFirst().orElse(null); // Ritorna
																											// null se
																											// nessun
																											// elemento
																											// corrisponde
		if (result != null) {
			return result.getNotificaStatoId();
		} else {
			throw new PgmeasException("Stato notifica non supportato: " + stato);
		}

	}

	public Parametro getParametroByCode(ParametroBatchEnum enumVal) throws PgmeasException {
		Optional<Parametro> optParametro = parametroRepository.findAllValidByCod(enumVal.getCode());
		checkEntityIsPresentByProperty(optParametro, enumVal.toString(), ValidationNameEnum.PARAMETRO_BY_PARAMETRO_COD);
		return optParametro.orElseThrow(PgmeasNoDataFoundException::new);
	}

}

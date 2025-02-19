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

import it.csi.pgmeas.commons.dto.EventoDecodedDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Parametro;
import it.csi.pgmeas.commons.repository.ParametroRepository;
import it.csi.pgmeas.commons.service.EventoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.CategoriaErroreEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.ParametroBatchEnum;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.repository.NotificaRepository;

@Service
public class NotificatorePgmeasService {
	private static final Logger log = LoggerFactory.getLogger(NotificatorePgmeasService.class);
	private EventoUtilityService eventoUtilityService;
	private BuilderNotificheService builderNotificheService;
	private NotificaRepository notificaRepository;
	private InviaNotificheService inviaNotificheService;
	private ParametroRepository parametroRepository;
	private ErroreService erroreService;

	public NotificatorePgmeasService(EventoUtilityService eventoUtilityService,
			BuilderNotificheService builderNotificheService, NotificaRepository notificaRepository,
			InviaNotificheService inviaNotificheService, ParametroRepository parametroRepository,
			ErroreService erroreService) {
		super();
		this.eventoUtilityService = eventoUtilityService;
		this.builderNotificheService = builderNotificheService;
		this.notificaRepository = notificaRepository;
		this.inviaNotificheService = inviaNotificheService;
		this.parametroRepository = parametroRepository;
		this.erroreService = erroreService;
	}

	public void inserisciNotifiche() {
		try {
			log.info("START STEP 1 inserisciNotifiche - {}", System.currentTimeMillis() / 1000);
			Parametro p = getParametroByCode(ParametroBatchEnum.BATCH_BUILDER);
			if (Boolean.parseBoolean(p.getParametroValore())) {
				List<EventoDecodedDto> eventiList = eventoUtilityService.findEventiDaNotificareValidi();
				log.info("eventi da elaborare: {}", eventiList.size());
				for (EventoDecodedDto e : eventiList) {
					builderNotificheService.build(e);
				}
			} else {
				log.info("BATCH inserisci notifiche disabilitato");
			}

			log.info("FINE STEP1 inserisciNotifiche ");
		} catch (Exception e) {
			log.error("Exception inserisciNotifiche {}",e.getMessage(), e);
			erroreService.traceError(e, CategoriaErroreEnum.GENERICO);
		}
	}

	public void inviaNotifiche() {
		log.info("inviaNotifiche - {}", System.currentTimeMillis() / 1000);
		try {
			log.info("START STEP 2 inviaNotifiche - {}", System.currentTimeMillis() / 1000);
			Parametro p = getParametroByCode(ParametroBatchEnum.BATCH_NOTIFIER);
			if (Boolean.parseBoolean(p.getParametroValore())) {
				List<Notifica> notificheList = notificaRepository.findValidNotSuccessNotZeroRetryCount();
				log.info("notifiche da elaborare: {}", notificheList.size());
				for (Notifica n : notificheList) {
					inviaNotificheService.inviaNotifica(n);
				}
			} else {
				log.info("BATCH Invia notifiche disabilitato");
			}

			log.info("FINE STEP 2 inviaNotifiche ");
		} catch (Exception e) {
			log.error("Exception inviaNotifiche {}",e.getMessage(), e);
			erroreService.traceError(e, CategoriaErroreEnum.GENERICO);
		}
	}

	public Parametro getParametroByCode(ParametroBatchEnum enumVal) throws PgmeasException {
		Optional<Parametro> optParametro = parametroRepository.findAllValidByCod(enumVal.getCode());
		checkEntityIsPresentByProperty(optParametro, enumVal.toString(), ValidationNameEnum.PARAMETRO_BY_PARAMETRO_COD);
		return optParametro.orElseThrow(PgmeasNoDataFoundException::new);
	}
}

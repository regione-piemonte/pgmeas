/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder;

import static it.csi.pgmeas.commons.validation.ValidationUtils.checkEntityIsPresentByProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.dto.EventoDecodedDto;
import it.csi.pgmeas.commons.dto.EventoTipoDecodedDto;
import it.csi.pgmeas.commons.dto.TemplateDecodedDto;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.repository.RInterventoModuloRepository;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.RuoloInterventoEnum;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.TemplateEnum;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.NotificaBuilderInterface;

@Component
public class RegRespModuloANotificaBuilder extends AbstractNotificaBuilder implements NotificaBuilderInterface {
	@Autowired
	InterventoUtilityService interventoUtilityService;
	@Autowired
	RInterventoModuloRepository rInterventoModuloRepository;


	@Override
	protected List<Notifica> buildNotifiche(EventoDecodedDto evento, EventoTipoDecodedDto dettaglioEvento) throws Exception{
		List<Notifica> notifiche = new ArrayList<>();
			RInterventoModulo rInterventoModulo = getEnteByREnteFaseId(evento.getEventoTabellaId(), evento.getEnteId());
			Intervento intervento = interventoUtilityService.getInterventoById(rInterventoModulo.getIntId());
			List<InterventoStrutturaV2GetDto> interventoStrutturaList = interventoUtilityService
					.getInterventiStruttura(intervento.getIntId(), intervento.getEnteId());

			// Estrazione dei template
			TemplateDecodedDto email = getTemplateByCode(dettaglioEvento.getTemplateList(), TemplateEnum.EMAIL,
					evento.getEventoTipoCod());
			TemplateDecodedDto mex = getTemplateByCode(dettaglioEvento.getTemplateList(), TemplateEnum.MEX,
					evento.getEventoTipoCod());

			String emailOggetto = email.getTemplateOggetto();
			String emailCorpo = email.getTemplateCorpo().replace("<codice intervento>", intervento.getIntCod());
			log.info("email corpo: {}", emailCorpo);
			String mexBreve = mex.getTemplateOggetto();
			String mexEsteso = mex.getTemplateCorpo().replace("<codice intervento>", intervento.getIntCod());

			// Creazione notifiche per ruoli intervento
			notifiche.add(creaNotifica(mexBreve, mexEsteso, emailOggetto, emailCorpo,
					intervento.getIntRupCf(), RuoloInterventoEnum.RUP.getCode(), dettaglioEvento,
					intervento.getEnteId()));
			log.info("notifica RUP: {}",intervento.getIntRupCf());
			
			notifiche.add(creaNotifica(mexBreve, mexEsteso, emailOggetto, emailCorpo,
					intervento.getIntReferentePraticaCf(), RuoloInterventoEnum.REF_PRATICA.getCode(), dettaglioEvento,
					intervento.getEnteId()));
			log.info("notifica REF_PRATICA: {}",intervento.getIntReferentePraticaCf());
			
			// Creazione notifiche per ruoli dinamici (strutture)
			for (InterventoStrutturaV2GetDto intStr : interventoStrutturaList) {
				log.info("notifica Responsabili Struttura Complessa: {}",intStr.getIntstrResponsabileStrutturaComplessaCf());
				aggiungiNotificaSeDestinatarioPresente(notifiche, mexBreve, mexEsteso, emailOggetto, emailCorpo,
						intStr.getIntstrResponsabileStrutturaComplessaCf(),
						RuoloInterventoEnum.RESP_STR_COMPL.getCode(), dettaglioEvento, intervento.getEnteId());
				log.info("notifica Responsabili Struttura Semplice: {}",intStr.getIntstrResponsabileStrutturaSempliceCf());
				aggiungiNotificaSeDestinatarioPresente(notifiche, mexBreve, mexEsteso, emailOggetto, emailCorpo,
						intStr.getIntstrResponsabileStrutturaSempliceCf(), RuoloInterventoEnum.RESP_STR_SEMPL.getCode(),
						dettaglioEvento, intervento.getEnteId());
			}
		return notifiche;
	}

	public RInterventoModulo getEnteByREnteFaseId(Integer rInterventoModulo, Integer enteId) throws PgmeasException {
		Optional<RInterventoModulo> rInterventoModuloOpt = rInterventoModuloRepository
				.findByRIntModuloIdAndEnteId(rInterventoModulo, enteId);
		checkEntityIsPresentByProperty(rInterventoModuloOpt, rInterventoModulo.toString(),
				ValidationNameEnum.R_INTERVENTO_MODULO_BY_R_INTERVENTO_MODULO_ID);
		return rInterventoModuloOpt.orElseThrow(PgmeasNoDataFoundException::new);
	}

}
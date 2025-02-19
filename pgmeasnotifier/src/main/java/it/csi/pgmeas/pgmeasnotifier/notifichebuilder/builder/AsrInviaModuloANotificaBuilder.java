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
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.repository.RInterventoModuloRepository;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.TemplateEnum;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.NotificaBuilderInterface;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.ConfiguratoreService;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto.ModelUtente;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto.Profilo;

@Component
public class AsrInviaModuloANotificaBuilder extends AbstractNotificaBuilder implements NotificaBuilderInterface {

	@Autowired
	InterventoUtilityService interventoUtilityService;
	@Autowired
	RInterventoModuloRepository rInterventoModuloRepository;
	@Autowired
	EnteUtilityService enteUtilityService;
	@Autowired
	ConfiguratoreService configuratoreService;

	@Override
	protected List<Notifica> buildNotifiche(EventoDecodedDto evento, EventoTipoDecodedDto dettaglioEvento) throws Exception {
		List<Notifica> notifiche = new ArrayList<>();
			RInterventoModulo rInterventoModulo = getEnteByREnteFaseId(evento.getEventoTabellaId(), evento.getEnteId());
			Intervento intervento = interventoUtilityService.getInterventoById(rInterventoModulo.getIntId());
			Ente ente = enteUtilityService.getEnteByEnteId(intervento.getEnteId());

			TemplateDecodedDto email = getTemplateByCode(dettaglioEvento.getTemplateList(), TemplateEnum.EMAIL,
					evento.getEventoTipoCod());
			TemplateDecodedDto mex = getTemplateByCode(dettaglioEvento.getTemplateList(), TemplateEnum.MEX,
					evento.getEventoTipoCod());

			String emailOggetto = email.getTemplateOggetto();
			String emailCorpo = email.getTemplateCorpo()
					.replace("<codice intervento>", intervento.getIntCod())
					.replace("<ASR>", ente.getEnteDesc());
			log.info("email corpo: {}", emailCorpo);
			String mexBreve = mex.getTemplateOggetto();
			String mexEsteso = mex.getTemplateCorpo()
					.replace("<codice intervento>", intervento.getIntCod())
					.replace("<ASR>", ente.getEnteDesc());

			List<ModelUtente> listaUtenti = configuratoreService.estraiUtentiRegione();
			log.info("utenti configuratore estratti {}",listaUtenti.size());
			log.debug("utenti configuratore: {}",listaUtenti.toString());
			for (ModelUtente utente : listaUtenti) {
				Profilo profilo = utente.getAbilitazioni().get(0).getProfilo();
				notifiche.add(creaNotifica(mexBreve, mexEsteso, emailOggetto, emailCorpo,
						utente.getCodiceFiscale(), profilo.getCodice(), dettaglioEvento, intervento.getEnteId()));
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
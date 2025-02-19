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
import it.csi.pgmeas.commons.model.EnteFase;
import it.csi.pgmeas.commons.repository.EnteFaseRepository;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.TemplateEnum;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.NotificaBuilderInterface;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.ConfiguratoreService;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto.ModelUtente;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto.Profilo;

@Component
public class RegCreaProrogaProgNotificaBuilder extends AbstractNotificaBuilder implements NotificaBuilderInterface {

	@Autowired
	EnteFaseRepository enteFaseRepository;

	@Autowired
	EnteUtilityService enteUtilityService;

	@Autowired
	ConfiguratoreService configuratoreService;


	@Override
	protected List<Notifica> buildNotifiche(EventoDecodedDto evento, EventoTipoDecodedDto dettaglioEvento) throws Exception{
		List<Notifica> notifiche = new ArrayList<>();
			// ESTRAZIONE PROGRAMMAZIONE
			EnteFase enteFase = getEnteByREnteFaseId(evento.getEventoTabellaId());
			Ente ente = enteUtilityService.getEnteByEnteId(enteFase.getEnteId());
			// R_ENTE_FASE
			String anno = "" + enteFase.getFaseInizio().getYear();
			String dataFineProroga = dateTimeformatter.format(enteFase.getFaseProrogaFine());

			TemplateDecodedDto email = getTemplateByCode(dettaglioEvento.getTemplateList(), TemplateEnum.EMAIL,
					evento.getEventoTipoCod());
			TemplateDecodedDto mex = getTemplateByCode(dettaglioEvento.getTemplateList(), TemplateEnum.MEX,
					evento.getEventoTipoCod());

			String emailOggetto = email.getTemplateOggetto();
			String emailCorpo = email.getTemplateCorpo()
					.replace("<anno>", anno)
					.replace("<data fine proroga>", dataFineProroga);
			log.info("email corpo: {}", emailCorpo);
			String mexBreve = mex.getTemplateOggetto();
			String mexEsteso = mex.getTemplateCorpo()
					.replace("<anno>", anno)
					.replace("<data fine proroga>", dataFineProroga);
			List<ModelUtente> listaUtenti = configuratoreService.estraiUtentiASRandRegione(ente.getEnteCodEsteso());
			log.info("utenti configuratore estratti {}",listaUtenti.size());
			log.debug("utenti configuratore: {}",listaUtenti.toString());
			for (ModelUtente utente : listaUtenti) {
				Profilo profilo = utente.getAbilitazioni().get(0).getProfilo();
				notifiche.add(creaNotifica(mexBreve, mexEsteso, emailOggetto, emailCorpo,
						utente.getCodiceFiscale(), profilo.getCodice(), dettaglioEvento, ente.getEnteId()));
			}
		return notifiche;
	}

	public EnteFase getEnteByREnteFaseId(Integer rEnteFaseId) throws PgmeasException {
		Optional<EnteFase> optEnteFase = enteFaseRepository.findValidByEnteFaseId(rEnteFaseId);
		checkEntityIsPresentByProperty(optEnteFase, rEnteFaseId.toString(),
				ValidationNameEnum.R_ENTE_FASE_BY_RENTE_FASE_ID);
		return optEnteFase.orElseThrow(PgmeasNoDataFoundException::new);
	}

}
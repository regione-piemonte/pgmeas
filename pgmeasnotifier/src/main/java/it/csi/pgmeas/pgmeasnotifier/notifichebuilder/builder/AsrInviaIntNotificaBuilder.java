/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.dto.EventoDecodedDto;
import it.csi.pgmeas.commons.dto.EventoTipoDecodedDto;
import it.csi.pgmeas.commons.dto.TemplateDecodedDto;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.RInterventoStato;
import it.csi.pgmeas.commons.repository.RInterventoStatoRepository;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.TemplateEnum;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.NotificaBuilderInterface;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.ConfiguratoreService;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto.ModelUtente;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto.Profilo;

@Component
public class AsrInviaIntNotificaBuilder extends AbstractNotificaBuilder implements NotificaBuilderInterface {

	@Autowired
	ConfiguratoreService configuratoreService;
	@Autowired
	InterventoUtilityService interventoUtilityService;
	@Autowired
	EnteUtilityService enteUtilityService;
	@Autowired
	RInterventoStatoRepository rInterventoStatoRepository;

	@Override
	protected List<Notifica> buildNotifiche(EventoDecodedDto evento, EventoTipoDecodedDto dettaglioEvento)throws Exception {
		List<Notifica> notifiche = new ArrayList<>();
			RInterventoStato rInterventoStato = rInterventoStatoRepository
					.findValidByRIntStatoId(evento.getEventoTabellaId(),evento.getEnteId());
			Intervento intervento = interventoUtilityService.getInterventoById(rInterventoStato.getIntId());

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

}
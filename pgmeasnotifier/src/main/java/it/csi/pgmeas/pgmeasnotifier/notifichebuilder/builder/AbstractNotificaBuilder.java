/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder;

import static it.csi.pgmeas.commons.validation.ValidationUtils.checkEntityIsPresentByProperty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pgmeas.commons.dto.EventoDecodedDto;
import it.csi.pgmeas.commons.dto.EventoTipoDecodedDto;
import it.csi.pgmeas.commons.dto.TemplateDecodedDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Evento;
import it.csi.pgmeas.commons.repository.EventoRepository;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.TemplateEnum;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.model.builder.NotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.repository.NotificaRepository;

public abstract class AbstractNotificaBuilder {
	@Autowired
	NotificaRepository notificaRepository;
	@Autowired
	EventoRepository eventoRepository;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	final Logger log= LoggerFactory.getLogger(this.getClass());
	
	protected abstract List<Notifica> buildNotifiche(EventoDecodedDto evento, EventoTipoDecodedDto dettaglioEvento) throws Exception;

	private static final String UTENTE_BATCH_1 = "BATCH_CREAZIONE_NOTIFICHE";
	private static final String NOME_APPLICAZIONE_MITTENTE = "PGMEAS";

	
	public void build(EventoDecodedDto eventoDto, EventoTipoDecodedDto attributiTipoEvento) throws Exception{
		log.info("build  per l'evento: {}", eventoDto.toString());
		List<Notifica> notifiche = buildNotifiche(eventoDto, attributiTipoEvento);
		log.info("notifiche generate: {}", notifiche.size());
		if (notifiche != null && notifiche.size() > 0) {
			salvaNotificheAndUpdateEvento(eventoDto, notifiche);
		}else {
			// Associo all'evento la notifica -1 in quanto non esistono notifiche da inviare per l'evento elaborato
			aggiornaEventoNotificaDefault( eventoDto);
		}
	}
	
	private void aggiornaEventoNotificaDefault(EventoDecodedDto eventoDto) throws PgmeasException {
		log.warn("Si associa all'evento {} la notifica di default perchè non sono presenti ",eventoDto.getEventoId());
		Evento ev = getEventoByEventoId(eventoDto.getEventoId());
		ev.setDataCancellazione(new Timestamp(System.currentTimeMillis()));
		ev.setDataModifica(new Timestamp(System.currentTimeMillis()));
		ev.setUtenteModifica("NESSUNA NOTIFICA DA INVIARE --- "+UTENTE_BATCH_1);
		eventoRepository.save(ev);
	}

	protected void salvaNotificheAndUpdateEvento(EventoDecodedDto eventoDto, List<Notifica> notificaList) throws Exception {
			log.info("salvaNotificheAndUpdateEvento");
			log.debug("notifiche da salvare: {}",notificaList.toString());
			List<Notifica> notifiche = notificaRepository.saveAllAndFlush(notificaList);
			log.info("estrazione evento by evento id: "+eventoDto.getEventoId());
			Evento ev = getEventoByEventoId(eventoDto.getEventoId());
			Notifica not = notifiche.get(0);
			ev.setNotificaId(not.getNotificaId());
			ev.setDataModifica(new Timestamp(System.currentTimeMillis()));
			ev.setUtenteModifica(UTENTE_BATCH_1);
			eventoRepository.save(ev);
	}

	public TemplateDecodedDto getTemplateByCode(List<TemplateDecodedDto> templates, TemplateEnum code,
			String codiceEvento) throws PgmeasException {
		TemplateDecodedDto result = templates.stream()
				.filter(template -> code.getCode().equals(template.getTemplateTipoCod())).findFirst().orElse(null);
		if (result != null) {
			return result;
		} else {
			throw new PgmeasException(
					"Template di tipo: " + code + " non supportato  per l'evento : " + codiceEvento);
		}
	}

	public Notifica creaNotifica(String mexBreve, String mexEsteso, String emailOggetto, String emailCorpo,
			String destinatarioCf, String destinatarioRuolo, EventoTipoDecodedDto dettaglioEvento, Integer enteId) {
		Timestamp now= new Timestamp(System.currentTimeMillis());
		Notifica result = new NotificaBuilder().withNotificaDataInizio(new Timestamp(System.currentTimeMillis()))
				.withNotificaMexBreve(mexBreve)
				.withNotificaMexEsteso(mexEsteso)
				.withNotificaEmailOggetto(emailOggetto)
				.withNotificaEmailCorpo(emailCorpo)
				.withNotificaDestinatatioCf(destinatarioCf)
				.withNotificaDestinatatioApplicazione(NOME_APPLICAZIONE_MITTENTE)
				.withNotificaDestinatatioRuolo(destinatarioRuolo)
				.withNotificaRetryContatore(dettaglioEvento.getEventoMaxNumeroRetryNotifica()).withEnteId(enteId)
				.withUtenteCreazione(UTENTE_BATCH_1).withUtenteModifica(UTENTE_BATCH_1).withDataCreazione(now)
				.withDataModifica(now).build();
		return result;
	}

	protected void aggiungiNotificaSeDestinatarioPresente(List<Notifica> notifiche, String mexBreve, String mexEsteso,
			String emailOggetto, String emailCorpo, String cf, String ruolo, EventoTipoDecodedDto dettaglioEvento, Integer enteId) {
		if (StringUtils.isNotBlank(cf)) {
			notifiche.add(creaNotifica(mexBreve, mexEsteso, emailOggetto, emailCorpo, cf, ruolo, dettaglioEvento, enteId));
		}
	}
	
	private Evento getEventoByEventoId(Integer eventoId) throws PgmeasException {
		Optional<Evento> optEvento = eventoRepository.findById(eventoId);
		checkEntityIsPresentByProperty(optEvento, eventoId.toString(), ValidationNameEnum.EVENTO_BY_EVENTO_ID);
		return optEvento.orElseThrow(PgmeasNoDataFoundException::new);
	}
	

}

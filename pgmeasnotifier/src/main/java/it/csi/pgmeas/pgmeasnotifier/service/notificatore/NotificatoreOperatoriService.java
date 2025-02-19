/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service.notificatore;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.ApiHeaderParamEnum;
import it.csi.pgmeas.pgmeasnotifier.exception.NotificatoreException;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto.Email;
import it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto.Message;
import it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto.Mex;
import it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto.Payload;
import it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto.Push;

@Service
public class NotificatoreOperatoriService {

	private static final Logger log = LoggerFactory.getLogger(NotificatoreOperatoriService.class);
	@Value("${notificatore.medico.applicazione}")
	private String nomeApplicazione;
	@Value("${notificatore.medico.url}")
	private String urlNotificatoreMedico = "";
	@Value("${notificatore.medico.token}")
	private String tokenApplicativo;
	@Value("${notificatore.medico.tag}")
	private String tag;
	@Value("${notificatore.medico.templateId}")
	private String templateId;
//	private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	@Autowired
	@Qualifier("notificatore")
	private RestTemplate notificatoreRestTemplate;

//	public String inviaNotificaOperatori(Notifica notificaDb) throws Exception {
//			Message message = buildMessage(notificaDb);
//			String result =sendNotificaOperatoriSanitari(message);
//			return result;
//	}

	public Message buildMessage(Notifica notificaDb) {
		Message message = new Message();
		String uuidIn = getUUID();
		message.setUuid(uuidIn);
		message.setToBeRetried(false);
		Payload payload = buildPayload(notificaDb, uuidIn);
		message.setPayload(payload);
		return message;
	}

	private String getUUID() {
		return UUID.randomUUID().toString();
	}

	private Payload buildPayload(Notifica notificaDb, String uuidIn) {
		Payload payload = new Payload();
		payload.setId(uuidIn);
		payload.setUserId(notificaDb.getNotificaDestinatatioCf());
//		payload.setRuolo(notificaDb.getNotificaDestinatatioRuolo());
		payload.setApplicazione(nomeApplicazione);
		payload.setTag(tag);
		payload.setEmail(buildEmail(notificaDb));
		payload.setPush(buildPush(notificaDb));
		payload.setMex(buildMex(notificaDb));
		return payload;

	}

	private Mex buildMex(Notifica notificaDb) {
		Mex mex = new Mex();
		mex.setTitle(notificaDb.getNotificaEmailOggetto());
		String body = notificaDb.getNotificaMexEsteso();
		mex.setBody(body);
		return mex;
	}

	private Push buildPush(Notifica notificaDb) {
		Push push = new Push();
		push.setTitle(notificaDb.getNotificaEmailOggetto());
		String body = notificaDb.getNotificaEmailCorpo();
		push.setBody(body);
		return push;
	}

	private Email buildEmail(Notifica notificaDb) {
		Email email = new Email();
		email.setSubject(notificaDb.getNotificaEmailOggetto());
		email.setBody(notificaDb.getNotificaMexEsteso());
		email.setTemplateId(templateId);
		return email;
	}

	private String fromObjectToJsonString(Message notificaCustom) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(notificaCustom);
	}

	/**
	 * 
	 * @param notifica
	 * @param cfSoggetto
	 * @param xRequestId
	 * @param xForwardedFor
	 * @return
	 * @throws Exception
	 */
	public String sendNotificaOperatoriSanitari(String jsonPayloadString,Message message) throws Exception {
		String result;
		try {
			log.info("invio notifica {}", message.getUuid());
			log.debug("payload message {}", message.toString());


			// Create headers
			HttpHeaders headers = new HttpHeaders();
			headers.set(ApiHeaderParamEnum.X_REQUEST_ID.getCode(), getUUID());
			headers.set(ApiHeaderParamEnum.X_AUTHENTICATION.getCode(), tokenApplicativo);
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Build request entity
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayloadString, headers);

			// Initialize RestTemplate

			ResponseEntity<String> response = notificatoreRestTemplate.exchange(urlNotificatoreMedico, HttpMethod.POST,
					requestEntity, String.class);

			// Log response
			log.info("Response =>", "status: " + response.getStatusCode() + " - notifica_uuid: " + message.getUuid());
			log.info("sendNotificaEventoMedico", "responsebody: " + response.getBody());

			// Check status code
			if (!(response.getStatusCode().is2xxSuccessful())) {
				log.error("sendNotificaEventoMedico", response.getBody() + " - notifica_uuid: " + message.getUuid());
				throw new NotificatoreException("Esito risposta diverso da 200 per notifica uuid: " + message.getUuid(),
						response.getBody());
			}

			result = response.getBody();
		} catch (RestClientException e) {
			String errorPayload = null;
			log.error("sendNotificaEventoMedico", "Errore nella chiamata al notificatore: " + e, e);
			if (e instanceof HttpStatusCodeException) {
				HttpStatusCodeException httpException = (HttpStatusCodeException) e;
				errorPayload = httpException.getResponseBodyAsString();
				log.error("sendNotificaEventoMedico: Errore nel payload di risposta: {}", errorPayload);
			} else {
				log.error("sendNotificaEventoMedico: Errore nella chiamata al notificatore", e);
			}

			throw new NotificatoreException("Errore durante la chiamata al configuratore", e, errorPayload);
		}

		return result;
	}

	public String convertMessageToJsonObject(Message message) throws JsonProcessingException {
		String jsonPayloadString = fromObjectToJsonString(message);
		log.info("jsonPayloadString - {}", jsonPayloadString);
		return jsonPayloadString;
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service.configuratore;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.util.UriComponentsBuilder;

import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.ApiHeaderParamEnum;
import it.csi.pgmeas.pgmeasnotifier.exception.ConfiguratoreException;
import it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto.ModelUtente;
import jakarta.annotation.PostConstruct;

@Service
public class ConfiguratoreService {
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguratoreService.class);
	@Value("${configuratore.applicazione:PGMEAS}")
	private String nomeApplicazione;
	@Value("${configuratore.url}")
	private String urlBaseConfiguratore;
	@Value(("${configuratore.codiceRegione:010002}"))
	private String codiceRegione;

	@Autowired
	@Qualifier("configuratore")
	private RestTemplate configuratoreRestTemplate;
	
	
	public List<ModelUtente> estraiUtentiRegione()throws Exception {
		return estraiUtentiASRandRegione(codiceRegione);
	}
	@PostConstruct
	public void init() throws UnknownHostException {
		LOG.info("IP_ADDRESS BATCH {}", InetAddress.getLocalHost().getHostAddress());
	}
	
	public List<ModelUtente> estraiUtentiASRandRegione (String codiceAzienda) throws ConfiguratoreException,Exception {
		LOG.info("estraiUtentiASR codiceAzienda: {}",codiceAzienda);

		String url = UriComponentsBuilder.fromHttpUrl(urlBaseConfiguratore)
				.path(nomeApplicazione)
				.path("/abilitazioni")
                .queryParam("offset", 0)
                .queryParam("codice_azienda", codiceAzienda)
                .toUriString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(ApiHeaderParamEnum.SHIB_IDENTITA_CODICEFISCALE.getCode(), "CALLBATCHPGMEAS");
		headers.set(ApiHeaderParamEnum.X_REQUEST_ID.getCode(), UUID.randomUUID().toString());
		headers.set(ApiHeaderParamEnum.X_CODICE_SERVIZIO.getCode(), nomeApplicazione);
			headers.set(ApiHeaderParamEnum.X_FORWARDED_FOR.getCode(), InetAddress.getLocalHost().getHostAddress());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>( headers);

		try {
			LOG.info("chiamata al configuratore url: {}",url);
			ResponseEntity<ModelUtente[]> response = configuratoreRestTemplate.exchange(url, HttpMethod.GET,
					entity, ModelUtente[].class);
			String body=response.getBody()!=null ? Arrays.toString(response.getBody()): "payload mancante"; 
			// Log response
			LOG.info("estraiUtentiASR","status: {}",response.getStatusCode() );
			LOG.info("estraiUtentiASR , responsebody: {}" ,body);

			// Check status code
			if (!(response.getStatusCode().is2xxSuccessful())) {
				LOG.error("estraiUtentiASR {}", body);
			}
			return List.of(response.getBody());
		} catch (RestClientException e) {
			String errorPayload=null;
			LOG.error("estraiUtentiASR", "Errore nella chiamata al configuratore: " + e,e);
			if (e instanceof HttpStatusCodeException) {
	            HttpStatusCodeException httpException = (HttpStatusCodeException) e;
	            errorPayload=httpException.getResponseBodyAsString();
	            LOG.error("estraiUtentiASR: Errore nel payload di risposta: {}", errorPayload );
	        } else {
	            LOG.error("estraiUtentiASR: Errore nella chiamata al configuratore", e);
	        }

	        throw new ConfiguratoreException("Errore durante la chiamata al configuratore "+codiceAzienda, e, errorPayload);
	    }

	}
}

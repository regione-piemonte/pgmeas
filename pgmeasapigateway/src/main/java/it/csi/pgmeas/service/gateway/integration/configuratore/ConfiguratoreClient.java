/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.integration.configuratore;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.csi.pgmeas.commons.integration.configuratore.dto.ModelTokenInformazione;
import jakarta.servlet.http.HttpServletRequest;

@Component
@RefreshScope
@Configuration
@EnableAutoConfiguration
public class ConfiguratoreClient {
	
	private static final String LOGIN_TOKEN_INFORMATION = "v1/login/token-information2";
	public static final List<String> HEADER_PARAMS = Arrays.asList("Shib-Identita-CodiceFiscale","X-Request-Id","X-Forwarded-For","X-Codice-Servizio");
	
	
	@Value("${configuratore.XCodiceServizio:PGMEAS}")
	private String XCodiceServizio ;
	
	@Value("${configuratore.base.url}")
	private String baseUrl;
	
	@Value("${configuratore.auth.base64}")
	private String auth;
	
	@Value("${configuratore.conn.timeout}")
	private int timeout;

	public Optional<ModelTokenInformazione> getLoginTokenInfo(HttpServletRequest inRequest,String tokenLcce) throws RestClientException, Exception {		
		
		HttpEntity<?> requestEntity = new HttpEntity<Object>(buildHttpHeaders(inRequest,tokenLcce));
		String url = buildUrl(LOGIN_TOKEN_INFORMATION);
		ResponseEntity<ModelTokenInformazione> responseEntity = buildRestTemplate()
				.exchange(url, HttpMethod.GET, requestEntity, ModelTokenInformazione.class);
		ModelTokenInformazione tokenInformazione = responseEntity.getBody();
		return Optional.ofNullable(tokenInformazione);
	}
	
	private RestTemplate buildRestTemplate() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
	
		MappingJackson2HttpMessageConverter mapper = new MappingJackson2HttpMessageConverter();		
		restTemplate.getMessageConverters().add(mapper);
		
        SimpleClientHttpRequestFactory factory = 
                (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        factory.setConnectTimeout(timeout);
        return restTemplate;
	}

	
	private HttpHeaders buildHttpHeaders(HttpServletRequest inRequest,String tokenLcce) {

		HttpHeaders headers = new HttpHeaders();
       
		for (String headerParam: HEADER_PARAMS) {
			headers.add(headerParam, inRequest.getHeader(headerParam));
		}
		headers.add("Token", tokenLcce);
		//TODO: se necessario cablare qua il codice servizio per il configuratore
		headers.set("X-Codice-Servizio", XCodiceServizio); 
		headers.set("X-Request-Id", UUID.randomUUID().toString());
		headers.setBasicAuth(auth);
		return headers;
	}
	
	private String buildUrl(String path) {
		StringBuffer sb = new StringBuffer();
		sb.append(baseUrl);
		sb.append(path);
		return sb.toString();				
	}

}

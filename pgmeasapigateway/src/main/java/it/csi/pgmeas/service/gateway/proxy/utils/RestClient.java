/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.proxy.utils;

import static it.csi.pgmeas.commons.util.HeadersUtils.getHttpHeaders;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.proxy.ProxyConfiguration;
import it.csi.pgmeas.service.gateway.proxy.ProxyUrlModel;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RestClient {
	private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

	private static final String CONTEXT = "/context";

	private static final long  CACHE_TIMEOUT = 6000;

	private Map<String, Object> cache = new LinkedHashMap<String, Object>();
	private Map<String, Long> cacheTimeout = new LinkedHashMap<String, Long>();

	@Autowired
	private ProxyUrlModel proxyConfiguration;

	public <T> T get(Class<T> clazz, HttpServletRequest request, String urlServizio, Object... param)
			throws CustomLoginException, URISyntaxException {
		HttpHeaders headers = getHttpHeaders(request);
		return get(clazz, headers, urlServizio, param);

	}

	public <T> T get(Class<T> clazz, HttpServletRequest request, UserInfoRecord user, String urlServizio,
			Object... param) throws CustomLoginException, URISyntaxException {
		HttpHeaders headers = getHttpHeaders(request, user);
		return get(clazz, headers, urlServizio, param);

	}

	public <T, B> T post(Class<T> clazz, HttpServletRequest request, B body, String urlServizio)
			throws CustomLoginException, URISyntaxException {
		HttpHeaders headers = getHttpHeaders(request);
		return post(clazz, headers, body, urlServizio);
	}

	public boolean isCacheValidFor(String url) {
		boolean retunValue=false;
		if (cache.containsKey(url)) {
			long creationDate = cacheTimeout.get(url);
			retunValue= (System.currentTimeMillis() - creationDate) < CACHE_TIMEOUT;
		}
		
		return retunValue;
	}

	public <T> T get(Class<T> clazz, HttpHeaders headers, String urlServizio, Object... param)
			throws URISyntaxException {
		String url = proxyConfiguration.getNewRequestUrl(CONTEXT + "/" + ProxyConfiguration.PROXY_PATH + urlServizio,
				CONTEXT);

		T data = null;

		// gestione della cache
		if ((param == null || param.length == 0) && isCacheValidFor(url)) {
			LOG.debug("[RestClient::get] >> rcupero dalla cache di:: {} ", urlServizio);
			data = (T) cache.get(url);
		} else {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<T> value = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), clazz,
					param);
			data = value.getBody();
			if (param == null || param.length == 0) {
				LOG.debug("[RestClient::get] [cache::store] {}", urlServizio);
				cache.put(url, data);
				cacheTimeout.put(url, System.currentTimeMillis());
			}
		}
		return data;

	}

	public <T> Map<Integer, String> getLinkedHashMap(Class<T[]> clazz, HttpHeaders headers, String urlServizio, //
			Function<T, Integer> keyMapper, //
			Function<T, String> valueMapper, //
			Object... param) throws URISyntaxException {

		// Ottieni l'array di oggetti
		T[] data = get(clazz, headers, urlServizio, param);

		// Controlla se i dati sono null
		if (data == null) {
			return Collections.emptyMap(); // Restituisce una mappa vuota se i dati sono null
		}

		// Converte l'array in un stream e crea la mappa
		return Arrays.stream(data)
				.collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1, LinkedHashMap::new));
	}

	public <K, T> Map<K, T> getLinkedHashMap(Class<T[]> clazz, HttpHeaders headers, String urlServizio, //
			Function<T, K> keyMapper, //
			Object... param) throws URISyntaxException {

		// Ottieni l'array di oggetti
		T[] data = get(clazz, headers, urlServizio, param);

		// Controlla se i dati sono null
		if (data == null) {
			return Collections.emptyMap(); // Restituisce una mappa vuota se i dati sono null
		}

		// Converte l'array in un stream e crea la mappa
		// Usa Function.identity() per mantenere T come valore
		return Arrays.stream(data)
				.collect(Collectors.toMap(keyMapper, Function.identity(), (v1, v2) -> v1, LinkedHashMap::new));

	}

	public <T, B> T post(Class<T> clazz, HttpHeaders headers, B body, String urlServizio) throws URISyntaxException {
		String url = proxyConfiguration.getNewRequestUrl(CONTEXT + "/" + ProxyConfiguration.PROXY_PATH + urlServizio,
				CONTEXT);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<T> value = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), clazz);

		return value.getBody();
	}

	public <T, B> T put(Class<T> clazz, HttpHeaders headers, B body, String urlServizio, Object... param)
			throws URISyntaxException {
		String url = proxyConfiguration.getNewRequestUrl(CONTEXT + "/" + ProxyConfiguration.PROXY_PATH + urlServizio,
				CONTEXT);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<T> value = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, headers), clazz,
				param);

		return value.getBody();
	}

	/***
	 * Rimozione header "Content-Length" Per evitare errore
	 * org.springframework.web.client.ResourceAccessException: I/O error on PUT
	 * request: too many bytes written
	 */
	public <T, B> T put(Class<T> clazz, HttpServletRequest request, B body, String urlServizio, Object... param)
			throws URISyntaxException, CustomLoginException {
		HttpHeaders headers = getHttpHeaders(request);
		headers.remove("Content-Length");
		return put(clazz, headers, body, urlServizio, param);
	}
}

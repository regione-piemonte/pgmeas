/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.proxy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import it.csi.pgmeas.service.gateway.proxy.record.MatchedRouteRecord;
import it.csi.pgmeas.service.gateway.proxy.record.RouteRecord;
import jakarta.servlet.http.HttpServletRequest;

public class ProxyUrlModel {

	private static final Logger LOG = LoggerFactory.getLogger(ProxyUrlModel.class);

	private Map<String, RouteRecord> paths = new LinkedHashMap<String, RouteRecord>();

	public void registerMapping(String regexPath, String targetHost, String targetUri) {
		LOG.debug("[ProxyUrlModel::registerMapping] ## registering proxy for: {} -> {}{}", regexPath, targetHost,
				targetUri);

		paths.put(regexPath, new RouteRecord(targetHost, targetUri));
	}

	private static String getMatch(String requestUrl, String mapKey) {
		String match = null;
		Pattern pattern = Pattern.compile(mapKey, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(requestUrl);
		if (matcher.find()) {
			match = (matcher.groupCount() > 0 ? matcher.group(1) : "");
		}

		return match;
	}

	public MatchedRouteRecord findMatch(String requestUrl) {
		MatchedRouteRecord matchedRouteRecord = null;
		Iterator<String> keys = paths.keySet().iterator();

		while (keys.hasNext() && matchedRouteRecord == null) {
			String key = keys.next();
			String match = getMatch(requestUrl, key);
			if (match != null) {
				RouteRecord route = paths.get(key);
				matchedRouteRecord = new MatchedRouteRecord(route.targetHost(), route.targetUri(), match);
			}
		}

		return matchedRouteRecord;
	}

	public URI getNewRequestUri(HttpServletRequest request) throws URISyntaxException {
		String url = getNewRequestUrl(request.getRequestURI(), request.getContextPath());
		URI targetUri = new URI(url);

		return UriComponentsBuilder.fromUri(targetUri).query(request.getQueryString()).build(true).toUri();

	}

	public String getNewRequestUrl(String requestUrl, String contextPath) throws URISyntaxException {

		String proxyPath = contextPath + "/" + ProxyConfiguration.PROXY_PATH;
		String relativePath = requestUrl.substring(proxyPath.length());

		MatchedRouteRecord matchedRouteRecord = findMatch(relativePath);

		String newRequestUrl = matchedRouteRecord.targetUri() + matchedRouteRecord.matchedString();
		String returnUrl = matchedRouteRecord.targetHost() + newRequestUrl;

		LOG.debug("[ProxyUrlModel::getNewRequestUrl] ## requestUrl::" + requestUrl);
		LOG.debug("[ProxyUrlModel::getNewRequestUrl] ## relativePath::" + relativePath);
		LOG.debug("[ProxyUrlModel::getNewRequestUrl] ## contextPath::" + contextPath);
		LOG.debug("[ProxyUrlModel::getNewRequestUrl] ## newRequestUrl::" + newRequestUrl);
		LOG.debug("[ProxyUrlModel::getNewRequestUrl] ## returnUrl::" + returnUrl);

		return returnUrl;

	}

}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.proxy;

import static it.csi.pgmeas.commons.util.HeadersUtils.getHttpHeaders;
import static it.csi.pgmeas.commons.util.HeadersUtils.getUser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.record.Audit;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.controller.BaseGatewayController;
import it.csi.pgmeas.service.gateway.exception.PgmeasProxyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = ProxyConfiguration.PROXY_PATH)
public class ProxyController extends BaseGatewayController {

	private static final Logger LOG = LoggerFactory.getLogger(ProxyController.class);

	public final static String X_FORWARD = "X-Forwarded-For";
	public final static String X_REQUEST = "X-Request-Id";

	@Value("${log.audit.uri}")
	private String auditUri;

	@Autowired
	private ProxyUrlModel proxyConfiguration;

	@Value("${log.ProxyUrl.debugOn:false}")
	private boolean proxyUrlDebugOn;

	@PutMapping("/**")
	public ResponseEntity<?> putMappingMirrorRest(@RequestBody(required = false) String body, HttpMethod method,
			HttpServletRequest request, HttpServletResponse response) throws PgmeasProxyException {
		return mirrorRest(body, method, request, response);
	}

	@GetMapping("/**")
	public ResponseEntity<?> getMappingMirrorRest(@RequestBody(required = false) String body, HttpMethod method,
			HttpServletRequest request, HttpServletResponse response) throws PgmeasProxyException {
		return mirrorRest(body, method, request, response);
	}

	@PostMapping("/**")
	public ResponseEntity<?> postMappingMirrorRest(@RequestBody(required = false) String body, HttpMethod method,
			HttpServletRequest request, HttpServletResponse response) throws PgmeasProxyException {
		return mirrorRest(body, method, request, response);
	}

	@DeleteMapping("/**")
	public ResponseEntity<?> deleteMappingMirrorRest(@RequestBody(required = false) String body, HttpMethod method,
			HttpServletRequest request, HttpServletResponse response) throws PgmeasProxyException {

		return mirrorRest(body, method, request, response);
	}

	@PatchMapping("/**")
	public ResponseEntity<?> patchMappingMirrorRest(@RequestBody(required = false) String body, HttpMethod method,
			HttpServletRequest request, HttpServletResponse response) throws PgmeasProxyException {
		return mirrorRest(body, method, request, response);
	}

	public ResponseEntity<?> mirrorRest(@RequestBody(required = false) String body, HttpMethod method,
			HttpServletRequest request, HttpServletResponse response) throws PgmeasProxyException {

		try {
			UserInfoRecord userInfo = getUser(request);
			URI reqUri = proxyConfiguration.getNewRequestUri(request);

			String appName = reqUri.getPath();
			ResponseEntity<?> resp = doMirrorRest(body, method, request, response);

//			SOLUZIONE ALTERNATIVA AD AGGIUNGERE CONTENT LENGTH
//			HttpHeaders headers = new HttpHeaders();
//			headers.putAll(resp.getHeaders());
//			headers.remove(HttpHeaders.TRANSFER_ENCODING);
//			resp = new ResponseEntity<>(resp.getBody(), headers, resp.getStatusCode());

			Audit audit = buildAudit(request, body, resp, appName, userInfo.codiceFiscale());
			ResponseEntity<?> auditRes = doAuditLog(audit, request);

			if (proxyUrlDebugOn) {
				LOG.info("## Audit status ::" + auditRes.getStatusCode());
			}

			return resp;
		} catch (Exception e) {
			LOG.warn("[ProxyController::mirrorRest] ", e);
			throw new PgmeasProxyException(e);
		}
	}

	public ResponseEntity<?> doMirrorRest(String body, HttpMethod method, HttpServletRequest request,
			HttpServletResponse response) throws URISyntaxException, NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, CustomLoginException {
		URI uri = proxyConfiguration.getNewRequestUri(request);

		if (proxyUrlDebugOn) {
			LOG.info("## uri ::" + uri.toString());
		}

		HttpEntity<String> httpEntity = new HttpEntity<>(body, getHttpHeaders(request));
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.exchange(uri, method, httpEntity, String.class);
		} catch (HttpStatusCodeException e) {
			LOG.error("[ProxyController::doMirrorRest] {}", uri.toString());
			LOG.error("[ProxyController::doMirrorRest] ", e);
			return handleHttpStatusCodeException(e);
		} catch (Exception e) {
			LOG.error("[ProxyController::doMirrorRest] {}", uri.toString());
			LOG.error("[ProxyController::doMirrorRest] ", e);
			return handleExceptionRE(e);
		}
	}

	private ResponseEntity<?> doAuditLog(Audit audit, HttpServletRequest request)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, CustomLoginException {

		HttpHeaders httpHeaders = getHttpHeaders(request);
		httpHeaders.remove("content-length");
		HttpEntity<Audit> httpEntity = new HttpEntity<>(audit, httpHeaders);

		RestTemplate restTemplate = new RestTemplate();
		try {
			if (proxyUrlDebugOn) {
				LOG.info("Chiamata rest verso il servizio di auditing");
			}
			return restTemplate.exchange(auditUri, HttpMethod.POST, httpEntity, Audit.class);
		} catch (HttpStatusCodeException ex) {
			return ResponseEntity.status(ex.getStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		} catch (Exception e) {
			LOG.error("[proxy generic error] - {}", e);
//			return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
//					.body(new ErrorRecord("Errore di sistema (Connection Error)"));
			return handleExceptionRE(e);
		}

	}

	private Audit buildAudit(HttpServletRequest request, String body, ResponseEntity<?> resp, String idApp,
			String fiscalCode) {
		Timestamp dataIns = new Timestamp(System.currentTimeMillis());
		String ipAddress = request.getHeader(X_FORWARD) == null ? "" : request.getHeader(X_FORWARD);
		String uuid = request.getHeader(X_REQUEST) == null ? "" : request.getHeader(X_REQUEST);
		String metodo = request.getMethod();
		// dall'uri ricavo ID_APP
		int status = resp.getStatusCode().value();
		
		//MODIFICA PER SONARQUBE
		String response = null;		
		if(resp != null && resp.getBody() != null) {
			Object oggetto = resp.getBody();
			if(oggetto != null) {
				response = oggetto.toString();
			}			
		}			
		
		Audit audit = new Audit(body, response,
				dataIns, ipAddress, idApp, fiscalCode, status, metodo, uuid);
		return audit;
	}

}

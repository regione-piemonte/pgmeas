/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller;

import static it.csi.pgmeas.commons.util.HeadersUtils.getUser;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.InterventoPdf;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;
import it.csi.pgmeas.service.gateway.service.InterventoPdfUtilityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("moduli")
public class StampaInterventoController extends BaseGatewayController {

	private static final Logger LOG = LoggerFactory.getLogger(StampaInterventoController.class);

	@Autowired
	private InterventoPdfUtilityService interventoUtilityService;


	// http://localhost:9090/pgmeasapigateway/moduli/v1/intervento//192/stampa
	// http://localhost:9090/pgmeasapigateway/moduli/v1/intervento/183/stampa
	// http://localhost:9090/pgmeasapigateway/moduli/v1/intervento/252/stampa
	@GetMapping(value = "/v1/intervento/{id}/stampa", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> downloadPdf(HttpServletRequest request, HttpServletResponse response, //
			@PathVariable Integer id) {
		try {
			LOG.info("stampa intervento:" + id);
			UserInfoRecord userInfo = getUser(request);  
			ByteArrayOutputStream allegtoPdf = new ByteArrayOutputStream();
			InterventoPdf interventoPdf = interventoUtilityService.getIntervento(request, id, userInfo, allegtoPdf);

			response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename= " + interventoPdf.getCodice() + ".pdf");
			response.addHeader("Access-Control-Expose-Headers", HttpHeaders.CONTENT_DISPOSITION);
			return ResponseEntity.ok().body(allegtoPdf.toByteArray());
		} catch (ApiGatewayCustomException ae) {
			LOG.error("[StampaInterventoController::downloadPdf] ", ae);
			return handleApiGatewayCustomException(ae);
		} catch (HttpClientErrorException he) {
			LOG.error("[StampaInterventoController::downloadPdf] ", he);
			return handleHttpClientErrorException(he);
		} catch (Exception e) {
			LOG.error("[StampaInterventoController::downloadPdf] ", e);
			return handleExceptionRE(e);
		}
	}

	// http://localhost:9090/pgmeasapigateway/moduli/v1/intervento/183/modulo/A/stampa
	@GetMapping(value = "/v1/intervento/{id}/modulo/A/stampa", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> downloadModuloAPdf(HttpServletRequest request, HttpServletResponse response, //
			@PathVariable Integer id) {
		try {
			LOG.info("stampa modulo A intervento:" + id);
			UserInfoRecord userInfo = getUser(request); 
			ByteArrayOutputStream allegatoPdf = new ByteArrayOutputStream();
			InterventoPdf interventoPdf = interventoUtilityService.getInterventoModuloA(request,
					id, userInfo, allegatoPdf);

			response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename= ModuloA" + interventoPdf.getCodice() + ".pdf");
			response.addHeader("Access-Control-Expose-Headers", HttpHeaders.CONTENT_DISPOSITION);
			return ResponseEntity.ok().body(allegatoPdf.toByteArray());

		} catch (HttpClientErrorException he) {
			LOG.error("[StampaInterventoController::downloadModuloAPdf] ", he);
			return handleHttpClientErrorException(he);
		} catch (ApiGatewayCustomException ae) {
			LOG.error("[StampaInterventoController::downloadModuloAPdf] ", ae);
			return handleApiGatewayCustomException(ae);
		} catch (Exception e) {
			LOG.error("[StampaInterventoController::downloadModuloAPdf] ", e);
			return handleExceptionRE(e);

		}
	}

}

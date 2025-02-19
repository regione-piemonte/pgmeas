/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller;

import static it.csi.pgmeas.commons.util.HeadersUtils.getUser;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.InterventoPdf;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;
import it.csi.pgmeas.service.gateway.proxy.utils.RestClient;
import it.csi.pgmeas.service.gateway.service.InterventoPdfUtilityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("iter")
public class IterInterventoController extends BaseGatewayController {

	// http://localhost:9090/pgmeasapigateway/iter/v1/intervento/192/invia
	private static final Logger LOG = LoggerFactory.getLogger(IterInterventoController.class);
	@Autowired
	private InterventoPdfUtilityService interventoUtilityService;
	@Autowired
	private RestClient client;

	@PutMapping(value = "/v1/intervento/{interventoId}/invia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoInvia(@PathVariable("interventoId") Integer interventoId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ByteArrayOutputStream allegtoPdf = new ByteArrayOutputStream();
			UserInfoRecord userInfo = getUser(request); 
			InterventoPdf interventoPdf = interventoUtilityService.getIntervento(request, interventoId, userInfo, allegtoPdf);

			byte[] allegatob46 = Base64.getEncoder().encode(allegtoPdf.toByteArray());
			AllegatoLightExtDto allegatoIntervento = new AllegatoLightExtDto();
			allegatoIntervento.setFileType("application/pdf");
			allegatoIntervento.setBase64(new String(allegatob46));
			allegatoIntervento.setFileNameUser(interventoPdf.getCodice() + ".pdf");

			Integer ret = client.put(Integer.class, request, allegatoIntervento, //
					"/api/v1/wf/intervento/{interventoId}/invia", // da inserire il path giusto
					interventoId);

			return ResponseEntity.ok(ret);

		} catch (ApiGatewayCustomException ae) {
			LOG.error("[StampaInterventoController::putInterventoInvia] ", ae);
			return handleApiGatewayCustomException(ae);
		} catch (HttpClientErrorException he) {
			LOG.error("[StampaInterventoController::putInterventoInvia] ", he);
			return handleHttpClientErrorException(he);
		}catch (Exception e) {
			LOG.error("[StampaInterventoController::putInterventoInvia] ", e);
			return handleExceptionRE(e);
		}

	}

	@PutMapping(value = "/v1/intervento/{interventoId}/approva", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoApprova(@PathVariable("interventoId") Integer interventoId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ByteArrayOutputStream allegtoPdf = new ByteArrayOutputStream();
			UserInfoRecord userInfo = getUser(request); 
			InterventoPdf interventoPdf = interventoUtilityService.getIntervento(request, interventoId, userInfo, allegtoPdf);

			byte[] allegatob46 = Base64.getEncoder().encode(allegtoPdf.toByteArray());
			AllegatoLightExtDto allegatoIntervento = new AllegatoLightExtDto();
			allegatoIntervento.setFileType("application/pdf");
			allegatoIntervento.setBase64(new String(allegatob46));
			allegatoIntervento.setFileNameUser(interventoPdf.getCodice() + ".pdf");

			Integer ret = client.put(Integer.class, request, allegatoIntervento, //
					"/api/v1/wf/intervento/{interventoId}/approva", // da inserire il path giusto
					interventoId);

			return ResponseEntity.ok(ret);
		} catch (ApiGatewayCustomException ae) {
			LOG.error("[StampaInterventoController::putInterventoApprova] ", ae);
			return handleApiGatewayCustomException(ae);
		} catch (HttpClientErrorException he) {
			LOG.error("[StampaInterventoController::putInterventoApprova] ", he);
			return handleHttpClientErrorException(he);
		} catch (Exception e) {
			LOG.error("[StampaInterventoController::putInterventoApprova] ", e);
			return handleExceptionRE(e);
		}

	}

	@PutMapping(value = "/v1/intervento/{interventoId}/rifiuta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoRifiuta(@RequestBody RespingimentoDto rifiutaInterventoDto,
			@PathVariable("interventoId") Integer interventoId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Integer ret = client.put(Integer.class, request, rifiutaInterventoDto, //
					"/api/v1/wf/intervento/{interventoId}/rifiuta", // da inserire il path giusto
					interventoId);

			return ResponseEntity.ok(ret);
		
		} catch (HttpClientErrorException he) {
			LOG.error("[StampaInterventoController::putInterventoRifiuta] ", he);
			return handleHttpClientErrorException(he);
		} catch (Exception e) {
			LOG.error("[StampaInterventoController::putInterventoRifiuta] ", e);
			return handleExceptionRE(e);
		}
	}

	@PutMapping(value = "/v1/intervento/{interventoId}/elimina", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoElimina(@PathVariable("interventoId") Integer interventoId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer ret = client.put(Integer.class, request, null, //
					"/api/v1/wf/intervento/{interventoId}/elimina", // da inserire il path giusto
					interventoId);

			return ResponseEntity.ok(ret);
		} catch (HttpClientErrorException he) {
			LOG.error("[StampaInterventoController::putInterventoElimina] ", he);
			return handleHttpClientErrorException(he);
		} catch (Exception e) {
			LOG.error("[StampaInterventoController::putInterventoElimina] ", e);
			return handleExceptionRE(e);
		}
	}
}

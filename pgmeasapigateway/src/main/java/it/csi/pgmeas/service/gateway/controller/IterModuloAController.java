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
public class IterModuloAController extends BaseGatewayController {

	// http://localhost:9090/pgmeasapigateway/iter/v1/intervento/192/invia
	private static final Logger LOG = LoggerFactory.getLogger(IterModuloAController.class);
	@Autowired
	private InterventoPdfUtilityService interventoUtilityService;
	@Autowired
	private RestClient client;

	@PutMapping(value = "/v1/modulo/A/{rIntModuloId}/intervento/{interventoId}/invia", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putAmmissioneFinanziamentoInvia(@PathVariable("rIntModuloId") Integer rIntModuloId, @PathVariable("interventoId") Integer interventoId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer ret = client.put(Integer.class, request, null, "/api/modulo/A/{rIntModuloId}/intervento/{interventoId}/invia", rIntModuloId, interventoId);

			return ResponseEntity.ok(ret);
			
		} catch (HttpClientErrorException he) {
			LOG.error("[IterModuloAController::putAmmissioneFinanziamentoInvia] ", he);
			return handleHttpClientErrorException(he);
		} catch (Exception e) {
			LOG.error("[IterModuloAmmissioneFinanziamentoController::putAmmissioneFinanziamentoInvia] ", e);
			return handleExceptionRE(e);

		}
	}

	@PutMapping(value = "/v1/modulo/A/{rIntModuloId}/intervento/{interventoId}/approva", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putAmmissioneFinanziamentoApprova(@PathVariable("rIntModuloId") Integer rIntModuloId, @PathVariable("interventoId") Integer interventoId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ByteArrayOutputStream allegatoPdf = new ByteArrayOutputStream();
			UserInfoRecord userInfo = getUser(request); 
			
			InterventoPdf interventoPdf = interventoUtilityService.getInterventoModuloA(request,
					interventoId, userInfo, allegatoPdf);

			byte[] allegatob46 = Base64.getEncoder().encode(allegatoPdf.toByteArray());
			AllegatoLightExtDto allegatoIntervento = new AllegatoLightExtDto();
			allegatoIntervento.setFileType("application/pdf");
			allegatoIntervento.setBase64(new String(allegatob46));
			allegatoIntervento.setFileNameUser(interventoPdf.getCodice() + ".pdf");

			Integer ret = client.put(Integer.class, request, allegatoIntervento, "/api/modulo/A/{rIntModuloId}/intervento/{interventoId}/approva",
					rIntModuloId, interventoId);

			return ResponseEntity.ok(ret);
		} catch (ApiGatewayCustomException ae) {
			LOG.error("[IterModuloAmmissioneFinanziamentoController::putAmmissioneFinanziamentoApprova] ", ae);
			return handleApiGatewayCustomException(ae);
		} catch (HttpClientErrorException he) {
			LOG.error("[IterModuloAController::putAmmissioneFinanziamentoApprova] ", he);
			return handleHttpClientErrorException(he);
		} catch (Exception e) {
			LOG.error("[IterModuloAmmissioneFinanziamentoController::putAmmissioneFinanziamentoApprova] ", e);
			return handleExceptionRE(e);
		}
	}

	@PutMapping(value = "/v1/modulo/A/{rIntModuloId}/intervento/{interventoId}/respinge", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putAmmissioneFinanziamentoRespinge(@RequestBody RespingimentoDto respingiInterventoDto,
			@PathVariable("rIntModuloId") Integer rIntModuloId, @PathVariable("interventoId") Integer interventoId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Integer ret = client.put(Integer.class, request, respingiInterventoDto,
					"/api/modulo/A/{rIntModuloId}/intervento/{interventoId}/respinge", rIntModuloId, interventoId);

			return ResponseEntity.ok(ret);
		} catch (HttpClientErrorException he) {
			LOG.error("[IterModuloAController::putAmmissioneFinanziamentoRespinge] ", he);
			return handleHttpClientErrorException(he);
		} catch (Exception e) {
			LOG.error("[IterModuloAmmissioneFinanziamentoController::putAmmissioneFinanziamentoRespinge] ", e);
			return handleExceptionRE(e);
		}
	}

}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.service;

import static it.csi.pgmeas.commons.util.HeadersUtils.getHttpHeaders;

import java.io.OutputStream;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.dto.v2.ModuloIntervento;
import it.csi.pgmeas.commons.dto.v2.ModuloInterventoStruttura;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.InterventoPdf;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.InterventoPdfMapper;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.ModuloInterventoPdfMapper;
import it.csi.pgmeas.service.gateway.controller.utils.ThymeleafTemplateProcessor;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;
import it.csi.pgmeas.service.gateway.proxy.utils.RestClient;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class InterventoPdfUtilityService {

	private static final Logger LOG = LoggerFactory.getLogger(InterventoPdfUtilityService.class);
	
	@Autowired
	private RestClient client;
	
	// TODO refactoring i due metodi funzionano con lo stesso mapper unica differenza il template
	public InterventoPdf getIntervento(HttpServletRequest request, //
			Integer id, UserInfoRecord userInfo,
			OutputStream outputStream) throws ApiGatewayCustomException, CustomLoginException, URISyntaxException {
		HttpHeaders headers = getHttpHeaders(request);
		InterventoPdfMapper mapper = new InterventoPdfMapper();
		mapper.initDecode(client, headers);

		// Ottenere l'intervento dal client
		InterventoV2GetDto intervento = client.get(InterventoV2GetDto.class, headers, "/api/v2/intervento/{id}",
				id);

		InterventoStrutturaV2GetDto[] interventiStruttura = client.get(InterventoStrutturaV2GetDto[].class, headers,
				"/api/v2/intervento/{id}/interventiStruttura", id);
		
		// Creiamo l'oggetto dedicato alla stampa
		InterventoPdf interventoPdf = mapper.getInterventoPdf(intervento, interventiStruttura, userInfo);

		// Configuriamo il processor di Thymeleaf
		ThymeleafTemplateProcessor processor = new ThymeleafTemplateProcessor("intervento_template");
		processor.setOutputStream(outputStream);
		processor.setVariable("data", interventoPdf); // Passiamo l'oggetto 'interventoPdf' al template

		// Generiamo il PDF
		processor.generatePdf();

		LOG.debug("[StampaInterventoController::getIntervento] PDF generato!");

		return interventoPdf;
	}
	
	public InterventoPdf getInterventoModuloA(HttpServletRequest request, 
			Integer id, UserInfoRecord userInfo,
			OutputStream outputStream) throws HttpClientErrorException, CustomLoginException, URISyntaxException, ApiGatewayCustomException {
		HttpHeaders headers = getHttpHeaders(request);
		ModuloInterventoPdfMapper mapper = new ModuloInterventoPdfMapper();
		mapper.initDecode(client, headers);

		// Ottenere l'intervento dal client
		ModuloIntervento moduloIntervento = client.get(ModuloIntervento.class, headers, "/api/modulo/A/{id}",
				id);
		
		ModuloInterventoStruttura[] moduloInterventiStruttura = client.get(ModuloInterventoStruttura[].class, headers,
				"/api/modulo/A/{id}/interventiStruttura", id);

		// Creiamo l'oggetto dedicato alla stampa
		InterventoPdf interventoPdf = mapper.getModuloInterventoPdf(moduloIntervento, moduloInterventiStruttura, userInfo);

		// Configuriamo il processor di Thymeleaf
		ThymeleafTemplateProcessor processor = new ThymeleafTemplateProcessor("modulo_richiesta_finanziamento_template.html");
		processor.setOutputStream(outputStream);
		processor.setVariable("data", interventoPdf); // Passiamo l'oggetto 'interventoPdf' al template

		// Generiamo il PDF
		processor.generatePdf();

		LOG.debug("[StampaInterventoController::getIntervento] PDF generato!");

		return interventoPdf;
	}
}
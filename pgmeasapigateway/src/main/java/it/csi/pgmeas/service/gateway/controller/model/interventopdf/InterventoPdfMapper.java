/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf;

import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfAsr;
import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfRegione;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;

import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.util.PdfMapper;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;
import it.csi.pgmeas.service.gateway.proxy.utils.RestClient;

public class InterventoPdfMapper extends PdfMapper {

	public InterventoPdf getInterventoPdf(InterventoV2GetDto dto, InterventoStrutturaV2GetDto[] interventoStruttura,
			UserInfoRecord userInfo) throws ApiGatewayCustomException, CustomLoginException {
		InterventoPdf interventoPdf = getGeneralInterventoPdf(dto, interventoStruttura, userInfo);
		
		String stato = buildDecode(statiMap, dto.getStato());
		boolean flgVisualizzaCampiRegione = checkIfRegione(userInfo)
				|| (checkIfAsr(userInfo) && 
						(stato.equals(InterventoStatoEnum.FINANZIABILE.getCode()) || stato.equals(InterventoStatoEnum.AMMESSO_AL_FINANZIAMENTO.getCode())));
		interventoPdf.setFlgVisualizzaCampiRegione(flgVisualizzaCampiRegione);
		
		if (interventoStruttura != null) {
			List<InterventoStrutturaPdf> ispdf = Arrays.asList(interventoStruttura).stream()
					.map(s -> getInterventoStrutturaPdf(s)) 
					.toList();
			interventoPdf.setInterventoStrutturaPdf(ispdf);
		}
		
		return interventoPdf;
	}

	public InterventoStrutturaPdf getInterventoStrutturaPdf(InterventoStrutturaV2GetDto dto) {
		InterventoStrutturaPdf pdf = getGeneralInterventoStrutturaPdf(dto);
		return pdf;
	}

	public void initDecode(RestClient client, HttpHeaders headers) throws URISyntaxException {
		initGeneralDecode(client, headers);
	}

}

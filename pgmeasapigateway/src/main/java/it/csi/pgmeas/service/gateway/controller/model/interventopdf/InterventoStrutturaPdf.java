/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf;

import java.util.Map;

import it.csi.pgmeas.commons.dto.v2.InterventoParereDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterventoStrutturaPdf {
	private Integer strId;
	private Integer intId;
	private Boolean strPgmeas;
	private Boolean strNonCensita;
	private Boolean strNuova;
	private String strDescrizione;
	private String strComune;
	private String strIndirizzo;
	private String strDatiCatastali;
	private String strNote;
	private String importo;
	private String responsabileStrutturaComplessa;
	private String responsabileStrutturaSemplice;
	private InterventoParereDto parerePPP;
	private InterventoParereDto parereHta;
	//
	private StimeDurateInt stimeDurataInt;
	private Boolean appaltoIntegrato;

	private Map<Integer, QuadroEconomicoPdf> quadroEconomico;
	private Map<Integer, InterventoEdilizioPdf> interventoEdilizio;
	private Map<Integer, DichiarazioneAppaltabilitaPdf> dichiarazioneAppaltabilita;



}

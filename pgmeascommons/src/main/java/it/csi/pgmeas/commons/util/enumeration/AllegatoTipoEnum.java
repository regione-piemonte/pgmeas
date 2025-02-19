/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

//TODO verificare correttezza valori
public enum AllegatoTipoEnum {
	PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE("PROV_AZ_APP"),
	RELAZIONE_TECNICA("REL_TEC"),
	SCHEDA_C_R("SCH_C_R"),
	SCHEDA_C_S("SCH_C_S"),
	DGR_DI_APPROVAZIONE("DGR_APPR"),
	DGR_DI_PROPOSTA("DGR_PROP_CR"),
	DCR_DI_APPROVAZIONE("DCR_APPR"),
	DETERMINA_DIRIGENZIALE_REGIONALE("DDR"),
	DELIBERA_AZIENDALE_DI_APPROVAZIONE("DAZ_APPR"),
	PARERE_PPP("PAR_PPP"),
	PARERE_HTA("PAR_HTA"),
	NULLA_OSTA("NULLA_OSTA"),
	DECRETO_MINISTERIALE("DEC_MIN");
	
	private String code;

	private AllegatoTipoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

//TODO verificare correttezza valori
public enum InterventoStatoEnum {
	INSERITO("INS"),
	ANNULLATO("ANN"),
	PROPOSTO("PROP"),
	FINANZIABILE("FIN"),
	AMMESSO_AL_FINANZIAMENTO("AMM_FIN");
	
	private String code;
	
	private InterventoStatoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	
	
	
}

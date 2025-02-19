/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum PgmeasClassifTsEnum {

	INTERVENTO_EDILIZIO("IE"),
	QUADRO_ECONOMICO("QE"),
	DICHIARAZIONE_APPALTABILITA("DA"),
	DICHIARAZIONE_APPALTABILITA_ATTREZZATURE("DAA");
	
	private String code;

	private PgmeasClassifTsEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	
}

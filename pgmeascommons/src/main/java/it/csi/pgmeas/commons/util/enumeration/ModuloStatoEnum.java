/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum ModuloStatoEnum {
	//TODO RIVEDERE QUANDO CI SARANNO I VALORI
	ANNULLATO("ANN"),
	FINANZIATO("FIN"),
	PROPOSTO("PROP"),
	INSERITO("INS"),
//	COLLAUDATO("COLL"),
//	IN_CORSO_OPERA("IN_CORSO_OPERA"),
//	NON_FINANZIATO("NON_FIN"),
//	REVOCATO_DA_MINISTERO_DELLA_SALUTE("REV_MINISTERO"),
//	REVOCATO_DA_REGIONE_PIEMONTE("REV_REGIONE"),
	APPROVATO("APPR"),
	PRESENTATO("PRES");
	
	
	private String code;
	
	private ModuloStatoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	
	
	
}

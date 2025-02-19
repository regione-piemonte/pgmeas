/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum RuoloInterventoEnum {
	RUP("RUP"),
	REF_PRATICA("REFERENTE_PRATICA"),
	RESP_STR_COMPL("RESPONSABILE STRUTTURA COMPLESSA"),
	RESP_STR_SEMPL("RESPONSABILE STRUTTURA SEMPLICE");
	
	private String code;

	private RuoloInterventoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

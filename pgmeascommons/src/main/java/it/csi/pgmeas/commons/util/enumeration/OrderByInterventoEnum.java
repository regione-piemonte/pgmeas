/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum OrderByInterventoEnum {
	AZIENDA("i.enteId"),
	INTCUP("i.intCup"),
	INTCOD("i.intCod"),
	INTTITOLO("i.intTitolo"),
	STATOINTERVENTO("is.intStatoCod"),
	INTIMPORTO("i.intImporto");
	
	private String code;

	private OrderByInterventoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

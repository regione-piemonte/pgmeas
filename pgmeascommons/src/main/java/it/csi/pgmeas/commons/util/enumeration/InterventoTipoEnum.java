/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum InterventoTipoEnum {
	ACQ_ATTR("ACQ_ATTR");
	
	private String code;

	private InterventoTipoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum OrderDirectionEnum {
	ASC("asc"),
	DESC("desc");
	
	private String code;

	private OrderDirectionEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

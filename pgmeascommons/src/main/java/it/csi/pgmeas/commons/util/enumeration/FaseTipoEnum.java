/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum FaseTipoEnum {
	PROGRAMMAZIONE("P"),
	GESTIONE("G"),
	MONITORAGGIO("M");
	
	private String code;

	private FaseTipoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

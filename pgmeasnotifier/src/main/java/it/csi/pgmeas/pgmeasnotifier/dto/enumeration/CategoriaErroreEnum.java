/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.dto.enumeration;

public enum CategoriaErroreEnum {
	PGMEAS_EXC("PGMEAS_EXC"),
	BATCH_BUILDER("BATCH_BUILDER"),
	CONFIGURATORE("CONFIGURATORE"),
	NOTIFICATORE("NOTIFICATORE"),
	GENERICO("GENERICO");

	private String code;

	private CategoriaErroreEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

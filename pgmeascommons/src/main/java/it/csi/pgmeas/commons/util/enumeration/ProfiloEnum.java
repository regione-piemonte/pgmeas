/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum ProfiloEnum {
	FUNZIONARIO_REGIONE("F_REG"),
	DIRIGENTE_REGIONE("D_REG"),
	FUNZIONARIO_ASR("F_ASR"),
	DIRIGENTE_ASR("D_ASR");
	
	private String code;

	private ProfiloEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum ModuloTipoEnum {
	//TODO RIVEDERE QUANDO CI SARANNO I VALORI
	MODULO_A("MOD_A"),
	MODULO_A_A("MOD_A_A"),
	MODULO_A_C("MOD_A_C"),
	MODULO_A_P("MOD_A_P"),
	MODULO_B_A("MOD_B_A"),
	MODULO_B_R("MOD_B_R"),
	MODULO_B_S("MOD_B_S"),
	MODULO_E("MOD_E"),
	MODULO_R_A("MOD_R_A"),
	MODULO_R_E("MOD_R_E"),
	SCHEDA_C("SCH_C"),
	DOCUMENTO_DATI_INTERVENTO("INT");
	
	private String code;

	private ModuloTipoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static ModuloTipoEnum fromCode(String code) {
        for (ModuloTipoEnum modulo : ModuloTipoEnum.values()) {
            if (modulo.getCode().equals(code)) {
                return modulo;
            }
        }
        throw new IllegalArgumentException("Nessun valore di ModuloTipoEnum per il codice: " + code);
    }
}

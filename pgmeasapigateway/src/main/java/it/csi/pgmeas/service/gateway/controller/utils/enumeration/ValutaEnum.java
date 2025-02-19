/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.utils.enumeration;

public enum ValutaEnum {
	EURO("€"),
	PERCENTUALE("%");
	
	private String code;

	private ValutaEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static ValutaEnum fromCode(String code) {
        for (ValutaEnum valuta : ValutaEnum.values()) {
            if (valuta.getCode().equals(code)) {
                return valuta;
            }
        }
        throw new IllegalArgumentException("Nessun valore di ValutaEnum per il codice: " + code);
    }
}

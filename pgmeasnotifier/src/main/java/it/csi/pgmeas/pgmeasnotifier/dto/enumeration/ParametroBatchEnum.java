/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.dto.enumeration;

public enum ParametroBatchEnum {
	BATCH_BUILDER("BATCH_BUILDER"),
	BATCH_NOTIFIER("BATCH_NOTIFIER"),
	BATCH_LOOKUP("BATCH_LOOKUP");
	
	private String code;

	private ParametroBatchEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

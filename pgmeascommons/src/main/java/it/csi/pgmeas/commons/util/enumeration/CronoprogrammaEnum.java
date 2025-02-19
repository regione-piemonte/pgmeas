/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum CronoprogrammaEnum {
	AFFIDAMENTO_LAVORI_GG("Affidamento Lavori gg", "AFFIDAMENTO LAVORI"),
	ESECUZIONE_LAVORI_GG("Esecuzione Lavori gg","ESECUZIONE LAVORI"),
	PROGETTAZIONE_GG("Progettazione gg","PROGETTAZIONE"),
	COLLAUDO_GG("Collaudo gg","COLLAUDO");
	
	private String code;
	private String description;

	private CronoprogrammaEnum(String code,String descritpion) {
		this.code = code;
		this.description = descritpion;
	}

	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
}

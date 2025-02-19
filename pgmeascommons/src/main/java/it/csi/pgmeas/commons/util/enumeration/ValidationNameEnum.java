/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum ValidationNameEnum {
	ENTE_ID("Ente", "enteId"),
	ENTE_COD_ESTESO("Ente", "enteCodEsteso"),
	INTERVENTO_ID("Intervento", "interventoId"),
	INTERVENTO_STRUTTURA_ID("Intervento Struttura", "interventoStrutturaId"),
	ALLEGATO_ID("Alllegato","allegatoId"),
	R_INTERVENTO_STATO_BY_INTERVENTO_ID("RInterventoStato","interventoId"),
	R_ENTE_FASE_BY_ENTE("REnteFase Programmazione","ente"),
	R_ENTE_FASE_BY_RENTE_FASE_ID("REnteFase Programmazione","rEnteFaseId"),
	R_INTERVENTO_MODULO_BY_R_INTERVENTO_MODULO_ID("RInterventoModulo","rInterventoModuloId"),
	PARAMETRO_BY_PARAMETRO_COD("Parametro","parametroCod"),
	EVENTO_BY_EVENTO_ID("Evento","eventoId");
	
	private String entita;
	private String attributo;
	

	private ValidationNameEnum(String entita, String attributo) {
		this.entita = entita;
		this.attributo = attributo;
	}

	public String getEntita() {
		return entita;
	}
	
	public String getAttributo() {
		return attributo;
	}
}

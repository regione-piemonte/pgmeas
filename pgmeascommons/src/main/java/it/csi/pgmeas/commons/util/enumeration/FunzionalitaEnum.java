/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum FunzionalitaEnum {
	OP_RICERCA_INTERVENTO("OP-RicercaIntervento"),
	OP_INSERISCI_INTERVENTO("OP-InsIntervento"),
	OP_MODIFICA_INTERVENTO("OP-ModIntervento"),
	OP_CONSULTA_INTERVENTO("OP-ConsIntervento"),
	OP_INVIA_INTERVENTO("OP-InviaIntervento"),
	OP_APPROVA_INTERVENTO("OP-ApprIntervento"),
	OP_RESPINGI_INTERVENTO("OP-RespIntervento"),
	OP_ELIMINA_INTERVENTO("OP-ElimIntervento"),
	
	OP_CONSULTA_MONITORAGGIO("OP-ConsMonitorag"),
	OP_INSERISCI_MONITORAGGIO("OP-InsMonitorag"),
	
	OP_INSERISCI_PROGRAMMAZIONE("OP-RegDefProg"),
	OP_PROROGA_FINE_PROGRAMMAZIONE("OP-RegProroga"),
	
	OP_INSERISCI_MODULO_A("OP-InsModA"),
	OP_MODIFICA_MODULO_A("OP-ModModA"),
	OP_CONSULTA_MODULO_A("OP-ConsModA"),
	OP_INVIA_MODULO_A("OP-InviaModA"),
	OP_APPROVA_MODULO_A("OP-ApprModA"),
	OP_RESPINGI_MODULO_A("OP-RespModA");
	
	private String code;

	private FunzionalitaEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

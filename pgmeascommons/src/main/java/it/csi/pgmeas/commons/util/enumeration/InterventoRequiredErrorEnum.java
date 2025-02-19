/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

//TODO verificare correttezza valori
public enum InterventoRequiredErrorEnum {
//	DATA_FINE_PROROGA("La data fine proroga non è stata valorizzata. Inserire un valore."),
//	ANNO_PROGRAMMAZIONE("L'anno non è stato valorizzato. Inserire un valore."),
//	DATA_INIZIO_PROGRAMMAZIONE("La data inizio programmazione non è stata valorizzata. Inserire un valore."),
//	DATA_FINE_PROGRAMMAZIONE("La data fine programmazione non è stata valorizzata. Inserire un valore."),
//	MOTIVO_RESPINGIMENTO("Il motivo del respingimento non è stato valorizzato. Inserire un valore."),
//	INTERVENTO("L'intervento non è stato valorizzato"),
//	APPALTO_INTEGRATO("L'appalto integrato non è stato valorizzato"),
	
	LISTA_INTERVENTO_STRUTTURE("La lista delle strutture non è stata valorizzata"),
	LISTA_OBIETTIVO_INTERVENTO("L'elenco obiettivi dell'intervento non è stato valorizzato"),
	LISTA_FINALITA_INTERVENTO("L'elenco finalità dell'intervento non è stato valorizzato"),
	LISTA_TIPO_INTERVENTO("L'elenco tipi dell'intervento non è stato valorizzato"),
	LISTA_CATEGORIE_INTERVENTO("L'elenco categorie dell'intervento non è stato valorizzato"),
	LISTA_CONTRATTO_TIPO("L'elenco contratti dell'intervento non è stato valorizzato"),
	LISTA_APPALTO_TIPO("L'elenco tipi di appalti dell'intervento non è stato valorizzato"),
	LISTA_PIANO_FINANZIARIO("MSG-076: Il piano finanziario non è stato valorizzato"),
	LISTA_PREVISIONE_DI_SPESA("MSG-075: La previsione di spesa non è stata valorizzata" );
	
	private String codeForNull;

	private InterventoRequiredErrorEnum(String codeForNull) {
		this.codeForNull = codeForNull;
	}

	public String getCodeForNull() {
		return codeForNull;
	}
}

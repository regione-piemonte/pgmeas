/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.record;

import java.io.Serializable;
import java.util.List;

import it.csi.pgmeas.commons.integration.configuratore.dto.Funzionalita;

public record UserInfoRecord(String nome, String cognome, String codiceFiscale, String ruolo, String profilo, Integer enteId,
		String codiceAzienda, String codiceColl, String descAzienda, String descCollocazione, 
		String idQuadrante, String descrQuadrante, 
		List<Funzionalita> listaFunzionalita,
		InfoProgrammazione programmazione
		) implements Serializable {

} 

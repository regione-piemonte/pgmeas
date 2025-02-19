/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto;

import lombok.Data;

@Data
public class Abilitazione {
	private Ruolo ruolo;
    private Collocazione collocazione;
    private Profilo profilo;
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommonDto {
	
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;

}

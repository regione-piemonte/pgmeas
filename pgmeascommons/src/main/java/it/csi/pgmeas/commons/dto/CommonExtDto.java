/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommonExtDto extends CommonDto {
	
	private Timestamp validitaInizio;
	private Timestamp validitaFine;
}

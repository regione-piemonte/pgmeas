/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

public record InterventoParereSaveExtDto(
	Integer intStrId,
	InterventoParereSaveDto parerePpp,
	InterventoParereSaveDto parereHta) {
}

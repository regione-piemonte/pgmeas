/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.util.List;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;

public record InterventoParereDto(
	Boolean parere,
	List<AllegatoLightExtDto> allegati) {
}

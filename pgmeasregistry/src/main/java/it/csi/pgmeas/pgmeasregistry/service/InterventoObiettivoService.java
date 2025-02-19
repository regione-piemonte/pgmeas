/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service;

import java.util.List;

import it.csi.pgmeas.commons.dto.InterventoObiettivoDto;

public interface InterventoObiettivoService {

	public List<InterventoObiettivoDto> getAllValid();

}

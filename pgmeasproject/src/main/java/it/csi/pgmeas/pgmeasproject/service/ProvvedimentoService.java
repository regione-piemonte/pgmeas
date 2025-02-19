/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.util.List;

import it.csi.pgmeas.pgmeasproject.dto.ProvvedimentoResultDto;

public interface ProvvedimentoService {
	public List<ProvvedimentoResultDto> getAllData();
}

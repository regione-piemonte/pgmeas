/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service;

import java.util.List;
import java.util.Optional;

import it.csi.pgmeas.commons.dto.EnteQuadranteDto;
import it.csi.pgmeas.commons.dto.QuadranteDto;

public interface QuadranteService {

	public List<QuadranteDto> getAllValid();

	public Optional<EnteQuadranteDto> getEnteQuadranteByCodEnte(String codEnte);

}

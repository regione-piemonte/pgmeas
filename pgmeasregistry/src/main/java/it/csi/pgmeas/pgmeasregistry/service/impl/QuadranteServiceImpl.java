/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.EnteQuadranteDto;
import it.csi.pgmeas.commons.dto.QuadranteDto;
import it.csi.pgmeas.pgmeasregistry.repository.QuadranteRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.QuadranteService;

@Service
public class QuadranteServiceImpl implements QuadranteService {
	@Autowired
	QuadranteRepository quadranteRepository;

	@Override
	public List<QuadranteDto> getAllValid() {
		return quadranteRepository.findAllValid().stream().map(from -> MappingUtils.copy(from, new QuadranteDto()))
				.toList();
	}

	@Override
	public Optional<EnteQuadranteDto> getEnteQuadranteByCodEnte(String codEnte) {
		return quadranteRepository.findEnteQuadranteByCodEnte(codEnte);
	}
}

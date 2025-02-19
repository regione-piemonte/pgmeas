/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.EnteDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.repository.EnteRepository;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.pgmeasregistry.service.EnteService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class EnteServiceImpl implements EnteService {
	@Autowired
	EnteRepository enteRepository;
	
	@Autowired
	EnteUtilityService enteUtilityService;

	@Override
	public List<EnteDto> getAllValid() {
		return enteRepository.findAllValid().stream().map(from -> MappingUtils.copy(from, new EnteDto()))
				.toList();
	}

	@Override
	public EnteDto getEnteByCodEnte(String codEnte) throws PgmeasException {
		Ente ente = enteUtilityService.getEnteByCodiceEsteso(codEnte);
		EnteDto enteDto = MappingUtils.copy(ente, new EnteDto());
		return enteDto;
	}
}

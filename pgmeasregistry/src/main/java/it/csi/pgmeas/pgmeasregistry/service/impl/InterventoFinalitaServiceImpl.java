/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoFinalitaDto;
import it.csi.pgmeas.pgmeasregistry.repository.InterventoFinalitaRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoFinalitaService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoFinalitaServiceImpl implements InterventoFinalitaService {
	@Autowired
	InterventoFinalitaRepository interventoFinalitaRepository;

	@Override
	public List<InterventoFinalitaDto> getAllValid() {
		return interventoFinalitaRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoFinalitaDto())).toList();
	}
}

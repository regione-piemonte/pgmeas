/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoObiettivoDto;
import it.csi.pgmeas.pgmeasregistry.repository.InterventoObiettivoRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoObiettivoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoObiettivoServiceImpl implements InterventoObiettivoService {
	@Autowired
	InterventoObiettivoRepository interventoObiettivoRepository;

	@Override
	public List<InterventoObiettivoDto> getAllValid() {
		return interventoObiettivoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoObiettivoDto())).toList();
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.PianoTriennaleDto;
import it.csi.pgmeas.pgmeasregistry.repository.PianoTriennaleRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.PianoTriennaleService;

@Service
public class PianoTriennaleServiceImpl implements PianoTriennaleService {
	@Autowired
	PianoTriennaleRepository pianoTriennaleRepository;

	@Override
	public List<PianoTriennaleDto> getAllValid() {
		return pianoTriennaleRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new PianoTriennaleDto())).toList();
	}
}

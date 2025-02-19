/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.FaseDto;
import it.csi.pgmeas.commons.repository.FaseRepository;
import it.csi.pgmeas.pgmeasregistry.service.FaseService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class FaseServiceImpl implements FaseService {
	@Autowired
	FaseRepository faseRepository;

	@Override
	public List<FaseDto> getAllValid() {
		return faseRepository.findAllValid().stream().map(from -> MappingUtils.copy(from, new FaseDto()))
				.toList();
	}
}

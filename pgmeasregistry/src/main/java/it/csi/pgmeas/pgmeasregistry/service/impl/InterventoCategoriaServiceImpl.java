/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoCategoriaDto;
import it.csi.pgmeas.pgmeasregistry.repository.InterventoCategoriaRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoCategoriaService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoCategoriaServiceImpl implements InterventoCategoriaService {
	@Autowired
	InterventoCategoriaRepository interventoCategoriaRepository;

	@Override
	public List<InterventoCategoriaDto> getAllValid() {
		return interventoCategoriaRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoCategoriaDto())).toList();
	}
}

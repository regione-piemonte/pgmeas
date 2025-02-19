/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ModuloDto;
import it.csi.pgmeas.commons.repository.ModuloRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.ModuloService;

@Service
public class ModuloServiceImpl implements ModuloService {
	@Autowired
	ModuloRepository moduloRepository;

	@Override
	public List<ModuloDto> getAllValid() {
		return moduloRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new ModuloDto())).toList();
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ModuloStatoDto;
import it.csi.pgmeas.commons.repository.ModuloStatoRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.ModuloStatoService;

@Service
public class ModuloStatoServiceImpl implements ModuloStatoService {
	@Autowired
	ModuloStatoRepository moduloStatoRepository;

	@Override
	public List<ModuloStatoDto> getAllValid() {
		return moduloStatoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new ModuloStatoDto())).toList();
	}
}

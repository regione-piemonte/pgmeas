/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.EnteTipoDto;
import it.csi.pgmeas.pgmeasregistry.repository.EnteTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.EnteTipoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class EnteTipoServiceImpl implements EnteTipoService {
	@Autowired
	EnteTipoRepository enteTipoRepository;

	@Override
	public List<EnteTipoDto> getAllValid() {
		return enteTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new EnteTipoDto())).toList();
	}
}

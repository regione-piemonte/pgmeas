/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ParametroTipoDto;
import it.csi.pgmeas.pgmeasregistry.repository.ParametroTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.ParametroTipoService;

@Service
public class ParametroTipoServiceImpl implements ParametroTipoService {
	@Autowired
	ParametroTipoRepository parametroTipoRepository;

	@Override
	public List<ParametroTipoDto> getAllValid() {
		return parametroTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new ParametroTipoDto())).toList();
	}
}

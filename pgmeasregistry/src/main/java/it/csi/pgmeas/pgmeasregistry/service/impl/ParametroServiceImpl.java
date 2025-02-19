/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ParametroDto;
import it.csi.pgmeas.commons.repository.ParametroRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.ParametroService;

@Service
public class ParametroServiceImpl implements ParametroService {
	@Autowired
	ParametroRepository parametroRepository;
	

	@Override
	public List<ParametroDto> getAllValid() {
		return parametroRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new ParametroDto())).toList();
	}


	@Override
	public List<ParametroDto> getErrori() {
		 return parametroRepository.findAllErroriValid().stream()
			.map(from -> MappingUtils.copy(from, new ParametroDto())).toList();
	}
}

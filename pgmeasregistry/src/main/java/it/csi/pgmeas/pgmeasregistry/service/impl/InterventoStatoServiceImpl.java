/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoStatoDto;
import it.csi.pgmeas.commons.repository.InterventoStatoRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoStatoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoStatoServiceImpl implements InterventoStatoService {
	@Autowired
	InterventoStatoRepository interventoStatoRepository;

	@Override
	public List<InterventoStatoDto> getAllValid() {
		return interventoStatoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoStatoDto())).toList();
	}
}

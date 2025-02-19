/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoTipoDto;
import it.csi.pgmeas.commons.repository.InterventoTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoTipoServiceImpl implements InterventoTipoService {
	@Autowired
	InterventoTipoRepository interventoTipoRepository;

	@Override
	public List<InterventoTipoDto> getAllValid() {
		return interventoTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoTipoDto())).toList();
	}
}

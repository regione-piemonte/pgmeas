/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoAppaltoTipoDto;
import it.csi.pgmeas.pgmeasregistry.repository.InterventoAppaltoTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoAppaltoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoAppaltoTipoServiceImpl implements InterventoAppaltoTipoService {
	@Autowired
	InterventoAppaltoTipoRepository interventoAppaltoTipoRepository;

	@Override
	public List<InterventoAppaltoTipoDto> getAllValid() {
		return interventoAppaltoTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoAppaltoTipoDto())).toList();
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoContrattoTipoDto;
import it.csi.pgmeas.pgmeasregistry.repository.InterventoContrattoTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoContrattoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoContrattoTipoServiceImpl implements InterventoContrattoTipoService {
	@Autowired
	InterventoContrattoTipoRepository interventoContrattoTipoRepository;

	@Override
	public List<InterventoContrattoTipoDto> getAllValid() {
		return interventoContrattoTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoContrattoTipoDto())).toList();
	}
}

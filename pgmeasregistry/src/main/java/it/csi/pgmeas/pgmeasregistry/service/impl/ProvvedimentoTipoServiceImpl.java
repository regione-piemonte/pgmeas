/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ProvvedimentoTipoDto;
import it.csi.pgmeas.pgmeasregistry.repository.ProvvedimentoTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.ProvvedimentoTipoService;

@Service
public class ProvvedimentoTipoServiceImpl implements ProvvedimentoTipoService {
	@Autowired
	ProvvedimentoTipoRepository provvedimentoTipoRepository;

	@Override
	public List<ProvvedimentoTipoDto> getAllValid() {
		return provvedimentoTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new ProvvedimentoTipoDto())).toList();
	}

}

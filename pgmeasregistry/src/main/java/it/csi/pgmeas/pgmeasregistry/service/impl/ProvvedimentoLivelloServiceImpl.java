/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ProvvedimentoLivelloDto;
import it.csi.pgmeas.pgmeasregistry.repository.ProvvedimentoLivelloRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.ProvvedimentoLivelloService;

@Service
public class ProvvedimentoLivelloServiceImpl implements ProvvedimentoLivelloService {
	@Autowired
	ProvvedimentoLivelloRepository organoRepository;

	@Override
	public List<ProvvedimentoLivelloDto> getAllValid() {
		return organoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new ProvvedimentoLivelloDto())).toList();
	}

}

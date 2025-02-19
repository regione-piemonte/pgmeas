/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.FinanziamentoTipoDto;
import it.csi.pgmeas.pgmeasregistry.repository.FinanziamentoTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class FinanziamentoTipoServiceImpl implements FinanziamentoTipoService {
	@Autowired
	FinanziamentoTipoRepository finanziamentoTipoRepository;

	@Override
	public List<FinanziamentoTipoDto> getAllValid() {
		return finanziamentoTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new FinanziamentoTipoDto())).toList();
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.FinanziamentoImpTipoDto;
import it.csi.pgmeas.pgmeasregistry.repository.FinanziamentoImportoTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoImportoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class FinanziamentoImportoTipoServiceImpl implements FinanziamentoImportoTipoService {
	@Autowired
	FinanziamentoImportoTipoRepository finanziamentoImportoTipoRepository;
	
	@Override
	public List<FinanziamentoImpTipoDto> getAllValid() {
		return finanziamentoImportoTipoRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new FinanziamentoImpTipoDto())).toList();
	}
}

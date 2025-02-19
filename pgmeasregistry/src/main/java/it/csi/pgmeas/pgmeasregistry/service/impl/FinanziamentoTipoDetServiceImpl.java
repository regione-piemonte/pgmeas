/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.FinanziamentoTipoDetDto;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.pgmeasregistry.repository.FinanziamentoTipoDetRepository;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoTipoDetService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class FinanziamentoTipoDetServiceImpl implements FinanziamentoTipoDetService {
	@Autowired
	FinanziamentoTipoDetRepository finanziamentoTipoDetRepository;
	
	@Autowired
	InterventoUtilityService interventoUtilityService;

	@Override
	public List<FinanziamentoTipoDetDto> getAllValid() {
		return finanziamentoTipoDetRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new FinanziamentoTipoDetDto())).toList();
	}
}

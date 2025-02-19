/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.InterventoStatoProgettualeDto;
import it.csi.pgmeas.pgmeasregistry.repository.InterventoStatoProgettualeRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoStatoProgettualeService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoStatoProgettualeServiceImpl implements InterventoStatoProgettualeService {
	@Autowired
	InterventoStatoProgettualeRepository interventoStatoProgettualeRepository;

	@Override
	public List<InterventoStatoProgettualeDto> getAllValid() {
		return interventoStatoProgettualeRepository.findAllValid().stream()
				.map(from -> MappingUtils.copy(from, new InterventoStatoProgettualeDto())).toList();
	}
}

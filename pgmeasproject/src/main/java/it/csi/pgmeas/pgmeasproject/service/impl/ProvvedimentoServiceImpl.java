/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.repository.ProvvedimentoRepository;
import it.csi.pgmeas.pgmeasproject.dto.ProvvedimentoResultDto;
import it.csi.pgmeas.pgmeasproject.service.ProvvedimentoService;

//TODO delete
@Service
public class ProvvedimentoServiceImpl implements ProvvedimentoService {
	@Autowired
	ProvvedimentoRepository provvedimentoRepository;

	@Override
	public List<ProvvedimentoResultDto> getAllData() {
		List<ProvvedimentoResultDto> data = provvedimentoRepository.findAllSenzaCancellati().stream()
				.map(from -> MappingUtils.copy(from, new ProvvedimentoResultDto())).toList();
		return data;
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.AllegatoTipoDto;
import it.csi.pgmeas.commons.repository.AllegatoTipoRepository;
import it.csi.pgmeas.pgmeasregistry.service.AllegatoTipoService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class AllegatoTipoServiceImpl implements AllegatoTipoService {
	@Autowired
	AllegatoTipoRepository allegatoTipoRepository;

	@Override
	public List<AllegatoTipoDto> getAllValid() {
		return allegatoTipoRepository
				.findAllValid().stream().map(from -> MappingUtils.copy(from, new AllegatoTipoDto())).toList();
	}
}

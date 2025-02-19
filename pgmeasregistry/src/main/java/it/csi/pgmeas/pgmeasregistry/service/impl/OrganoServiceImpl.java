/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.OrganoDto;
import it.csi.pgmeas.pgmeasregistry.repository.OrganoRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.OrganoService;

@Service
public class OrganoServiceImpl implements OrganoService {
	@Autowired
	OrganoRepository organoRepository;

	@Override
	public List<OrganoDto> getAllValid() {
		return organoRepository.findAllValid().stream().map(from -> MappingUtils.copy(from, new OrganoDto()))
				.toList();
	}

}

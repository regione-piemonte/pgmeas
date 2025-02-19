/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pgmeas.commons.dto.InterventoTipoDetDto;
import it.csi.pgmeas.commons.dto.InterventoTipoDto;
import it.csi.pgmeas.commons.model.InterventoTipoDet;
import it.csi.pgmeas.commons.repository.InterventoTipoDetRepository;
import it.csi.pgmeas.pgmeasregistry.service.InterventoTipoDetService;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;

@Service
public class InterventoTipoDetServiceImpl implements InterventoTipoDetService {
	@Autowired
	InterventoTipoDetRepository interventoTipoDetRepository;

	@Override
	public List<InterventoTipoDetDto> getAllValid() {
		return getData(interventoTipoDetRepository.findAllValid(), false);
	}

	@Override
	public List<InterventoTipoDetDto> getIntervTipoDettByCodiceIntervTipo(String intTipoCod) {
		return getData(interventoTipoDetRepository.findAllValidByIntTipoCod(intTipoCod), false);
	}
	
	private List<InterventoTipoDetDto> getData(List<InterventoTipoDet> listaIntervTipoDett, boolean loadIntervTipoDetail) {
		return listaIntervTipoDett.stream().map(intervTipoDett -> {
			InterventoTipoDetDto intervTipoDettDto = MappingUtils.copy(intervTipoDett, new InterventoTipoDetDto());

			if (loadIntervTipoDetail && intervTipoDett.getInterventoTipo() != null) {
				InterventoTipoDto intervTipoDto = MappingUtils.copy(intervTipoDett.getInterventoTipo(), new InterventoTipoDto());
				intervTipoDettDto.setInterventoTipo(intervTipoDto);
			}

			return intervTipoDettDto;
		}).toList();
	}
}

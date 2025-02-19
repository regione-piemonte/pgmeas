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

import it.csi.pgmeas.commons.dto.EnteDto;
import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.model.Struttura;
import it.csi.pgmeas.commons.repository.StrutturaRepository;
import it.csi.pgmeas.pgmeasregistry.service.MappingUtils;
import it.csi.pgmeas.pgmeasregistry.service.StrutturaService;

@Service
public class StrutturaServiceImpl implements StrutturaService {
	@Autowired
	StrutturaRepository strutturaRepository;
	
	@Override
	public List<StrutturaDto> getAllValid() {
		return getData(strutturaRepository.findAllValid(), false);
	}

	@Override
	public List<StrutturaDto> getStrutturaByCodice(String codStruttura) {
		return getData(strutturaRepository.findAllValidByCodice(codStruttura), false);
	}
	
	@Override
	public List<StrutturaDto> getStrutturaByInterventoId(Integer interventoId) {
		return getData(strutturaRepository.findAllValidByIntId(interventoId), false);
	}

	private List<StrutturaDto> getData(List<Struttura> listaStrutture, boolean loadEntiDetail) {
		return listaStrutture.stream().map(struttura -> {
			// Mappa la Struttura in StrutturaDto
			StrutturaDto strutturaDto = MappingUtils.copy(struttura, new StrutturaDto());

			// Mappa anche l'oggetto Ente associato in EnteDto
			if (loadEntiDetail && struttura.getEnte() != null) {
				EnteDto enteDto = MappingUtils.copy(struttura.getEnte(), new EnteDto());
				strutturaDto.setEnte(enteDto);
			}

			return strutturaDto;
		}).toList();
	}
}
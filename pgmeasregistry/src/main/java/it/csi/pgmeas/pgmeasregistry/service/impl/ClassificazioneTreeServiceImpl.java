/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto;
import it.csi.pgmeas.commons.repository.ClassificazioneTreeRepository;
import it.csi.pgmeas.pgmeasregistry.service.ClassificazioneTreeService;

@Service
public class ClassificazioneTreeServiceImpl implements ClassificazioneTreeService {
	@Autowired
	ClassificazioneTreeRepository classificazioneTreeRepository;

	@Override
	public List<ClassificazioneTreeByClassTsTipoDto> getClassifTreeByClassifTsTipoCod(String classifTsTipoCod, boolean view) {
		if(view) {
			return classificazioneTreeRepository.findAllTreeByClassifTsTipoCod(classifTsTipoCod);
		}
		
		return classificazioneTreeRepository.findValidTreeByClassifTsTipoCod(classifTsTipoCod);
	}
}

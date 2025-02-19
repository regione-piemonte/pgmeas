/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.repository.ClassificazioneTreeRepository;

@Service
public class ClassificazioneTreeUtilityService {

	@Autowired
	ClassificazioneTreeRepository classificazioneTreeRepository;

	public List<Integer> getElementiQuadroEconomicoPerControllo() {
		return classificazioneTreeRepository.findTreeQuadroEconomicoNotToTal();
	}
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.ClassificazioneTs;
import it.csi.pgmeas.commons.repository.ClassificazioneTsRepository;
import it.csi.pgmeas.commons.util.enumeration.PgmeasClassifTsEnum;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class ClassificazioneTsUtilityService {

	@Autowired 
	ClassificazioneTsRepository classificazioneTsRepository;
	
	
	public ClassificazioneTs getClassificazioneTsByClassificazioneTipoCod(PgmeasClassifTsEnum classifTipoEnum) throws PgmeasException {
		
		List<ClassificazioneTs> classificazioneTs= classificazioneTsRepository.findAllValidByClassificazioneTsTipoCod(classifTipoEnum.getCode());
		if(classificazioneTs!=null && classificazioneTs.size()>0) {
			return classificazioneTs.get(0);
		}else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "codice classificazione ts non trovato",
					new ArrayList<ErroreDettaglio>(), "codice classificazione ts non trovato :" + classifTipoEnum.getCode());
		}
		return null;
	}
	
	public Long countByClassificazioneTsId(Integer classifTsId) {
		Long count = 0L;
		
		Optional<Long> countOpt = classificazioneTsRepository.countByClassificazioneTsId(classifTsId);
		if(countOpt.isPresent()) {
			count = countOpt.get();
		}
		
		return count;
	}
}

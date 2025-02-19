/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.AllegatoTipo;
import it.csi.pgmeas.commons.repository.AllegatoTipoRepository;
import it.csi.pgmeas.commons.util.enumeration.AllegatoTipoEnum;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class AllegatoTipoUtilityService {

	@Autowired 
	AllegatoTipoRepository allegatoTipoRepository;
	
	public AllegatoTipo getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum allegatoTipEnum) throws PgmeasException {
		List<AllegatoTipo> allegatiTipo = allegatoTipoRepository.findAllValidByAllegatoTipoCod(allegatoTipEnum.getCode());
		if(allegatiTipo!=null && allegatiTipo.size()>0) {
			return allegatiTipo.get(0);
		}else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "Codice allegatoTipo non trovato",
					new ArrayList<ErroreDettaglio>(), "Codice allegatoTipo non trovato :" + allegatoTipEnum.getCode());
			
		}
		return null;
	}
}



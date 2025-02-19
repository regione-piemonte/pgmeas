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
import it.csi.pgmeas.commons.model.InterventoTipo;
import it.csi.pgmeas.commons.repository.InterventoTipoRepository;
import it.csi.pgmeas.commons.util.enumeration.InterventoTipoEnum;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class InterventoTipoUtilityService {

	@Autowired 
	InterventoTipoRepository interventoTipoRepository;
	
	public InterventoTipo getInterventoTipoValidoByInterventoTipoCod(InterventoTipoEnum interventoTipoEnum) throws PgmeasException {
		
		List<InterventoTipo> interventoTipo = interventoTipoRepository.findAllValidByIntTipoCod(interventoTipoEnum.getCode());
		if(interventoTipo != null && interventoTipo.size() > 0) {
			return interventoTipo.get(0);
		} else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "codice InterventoTipo non trovato",
					new ArrayList<ErroreDettaglio>(), "codice InterventoTipo non trovato :" + interventoTipoEnum.getCode());
		}
		return null;
	}
}

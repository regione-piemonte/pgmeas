/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import static it.csi.pgmeas.commons.validation.ValidationUtils.checkEntityIsPresentByProperty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.repository.EnteRepository;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;

@Service
public class EnteUtilityService {

	@Autowired
	private EnteRepository enteRepository;

	public Ente getEnteByCodiceEsteso(String codiceEnte) throws PgmeasException {
		Optional<Ente> ente = enteRepository.findValidByCodEsteso(codiceEnte);
		checkEntityIsPresentByProperty(ente, codiceEnte, ValidationNameEnum.ENTE_COD_ESTESO);
		return ente.orElseThrow(PgmeasNoDataFoundException::new);
	}

	public Ente getEnteByEnteId(Integer intEnteId) throws PgmeasException {
		Optional<Ente> optEnte = enteRepository.findValidById(intEnteId);
		checkEntityIsPresentByProperty(optEnte, intEnteId.toString(), ValidationNameEnum.ENTE_ID);
		return optEnte.orElseThrow(PgmeasNoDataFoundException::new);
	}
}

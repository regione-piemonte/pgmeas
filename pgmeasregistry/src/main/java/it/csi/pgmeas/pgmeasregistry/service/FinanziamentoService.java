/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service;

import java.util.List;

import it.csi.pgmeas.commons.dto.v2.FinanziamentoTipoDto;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.exception.PgmeasException;

public interface FinanziamentoService {

	public List<FinanziamentoTipoDto> getDataByInterventoId(Integer intId) throws CustomLoginException, PgmeasException; 

}

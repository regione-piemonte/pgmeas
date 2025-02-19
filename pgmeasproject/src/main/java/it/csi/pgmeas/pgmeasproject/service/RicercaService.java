/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.csi.pgmeas.commons.dto.v2.RicercaInterventiFilterDto;
import it.csi.pgmeas.commons.dto.v2.RicercaInterventiResultDto;
import it.csi.pgmeas.commons.dto.v2.RicercaModuloAResultDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import jakarta.servlet.http.HttpServletRequest;

public interface RicercaService {

	List<RicercaInterventiResultDto> ricercaByAllFilters(HttpServletRequest httpRequest,
			RicercaInterventiFilterDto filters) throws PgmeasException;
	
	List<RicercaInterventiResultDto> getInterventiByAllFilters(HttpServletRequest httpRequest, 
			RicercaInterventiFilterDto filters) throws PgmeasException, ExecutionException;
	
	List<RicercaModuloAResultDto> getInterventiModuloAByAllFilters(HttpServletRequest httpRequest, 
			RicercaInterventiFilterDto filters) throws PgmeasException, ExecutionException;
	
}

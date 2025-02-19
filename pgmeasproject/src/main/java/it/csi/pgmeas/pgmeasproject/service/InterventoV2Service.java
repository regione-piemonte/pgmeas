/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToSaveModel;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import jakarta.servlet.http.HttpServletRequest;

public interface InterventoV2Service {

	InterventoToSaveModel postInterventoV2(InterventoToSaveModel body, HttpServletRequest httpRequest)
			throws PgmeasException, IOException;

	InterventoToPutModel putInterventoV2(InterventoToPutModel body, Integer interventoId,
			HttpServletRequest httpRequest) throws PgmeasException, IOException;

	List<InterventoV2GetDto> getInterventiByFilters(HttpServletRequest httpRequest, Integer anno, String codice,
			String titolo, String cup) throws PgmeasException, ExecutionException;

	InterventoV2GetDto getIntervento(HttpServletRequest httpRequest, Integer interventoId)
			throws PgmeasException, ExecutionException;

	InterventoToPutByRegioneModel putInterventoV2ByRegione(InterventoToPutByRegioneModel body, Integer interventoId,
			HttpServletRequest httpRequest) throws PgmeasException, IOException;

	List<InterventoStrutturaV2GetDto> getInterventiStrutturaByIntId(HttpServletRequest httpRequest, Integer intId,
			boolean copy) throws PgmeasException;

}

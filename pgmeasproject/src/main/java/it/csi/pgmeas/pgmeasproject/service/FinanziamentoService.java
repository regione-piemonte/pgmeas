/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.sql.Timestamp;
import java.util.List;

import it.csi.pgmeas.commons.dto.AllegatoDto;
import it.csi.pgmeas.commons.dto.FinanziamentoResultDto;
import it.csi.pgmeas.commons.dto.MonitoraggioDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import jakarta.servlet.http.HttpServletRequest;

public interface FinanziamentoService {

	public List<FinanziamentoResultDto> getAllData();

	public List<FinanziamentoResultDto> getFinanziamentiByIntId(Integer intId);

	public Timestamp getIntDataValidDicAppalto(Integer classifTreeId, Integer enteId, Integer intstrDaTsId);

	AllegatoDto inserimentoDatiMonitoraggio(MonitoraggioDto richiestaMonitoraggioDto, HttpServletRequest httpRequest)
			throws PgmeasException;
}

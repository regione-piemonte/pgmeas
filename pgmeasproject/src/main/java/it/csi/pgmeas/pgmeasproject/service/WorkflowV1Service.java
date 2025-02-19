/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import jakarta.servlet.http.HttpServletRequest;

public interface WorkflowV1Service {

	public Integer putInterventoInvia(AllegatoLightExtDto allegatoIntervento, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;

	public Integer putInterventoApprova(AllegatoLightExtDto allegatoIntervento, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;

	public Integer putInterventoRifiuta(RespingimentoDto rifiutaInterventoDto, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;

	public Integer putInterventoElimina( Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;
}

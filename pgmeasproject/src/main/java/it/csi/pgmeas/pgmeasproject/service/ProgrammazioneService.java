/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.pgmeas.commons.dto.EnteProrogaDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneBaseDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneProrogaDto;
import it.csi.pgmeas.commons.util.record.InfoProgrammazione;
import jakarta.servlet.http.HttpServletRequest;

public interface ProgrammazioneService {

	InfoProgrammazione getInfoProgrammazioneForUser(String enteCod);

	ProgrammazioneDto getProgrammazione(String anno);

	EnteProrogaDto getProrogaEnte(String anno, String enteCod);

	ProgrammazioneDto inserisciProrogaTuttiEnti(ProgrammazioneProrogaDto request, HttpServletRequest httpRequest,
			String anno) throws Exception;

	void deleteProgrammazione(ProgrammazioneProrogaDto request,	String anno) throws Exception;

	public ProgrammazioneDto insertProgrammazione(ProgrammazioneBaseDto request, HttpServletRequest httpRequest)
			throws Exception;

	public List<ProgrammazioneBaseDto> getElencoProgrammazioni() throws Exception;

	EnteProrogaDto insertProroga(@RequestBody EnteProrogaDto request, HttpServletRequest httpRequest,
			String anno,String enteCod)throws Exception;

}
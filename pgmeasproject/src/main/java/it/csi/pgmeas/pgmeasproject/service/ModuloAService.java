/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.io.IOException;
import java.util.List;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RInterventoModuloDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoPutDto;
import it.csi.pgmeas.commons.dto.v2.ModuloAPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.ModuloIntervento;
import it.csi.pgmeas.commons.dto.v2.ModuloInterventoStruttura;
import it.csi.pgmeas.commons.exception.PgmeasException;
import jakarta.servlet.http.HttpServletRequest;

public interface ModuloAService {

	public RInterventoModuloDto postModuloA(RichiestaAmmissioneFinanziamentoDto request,
			HttpServletRequest httpRequest) throws PgmeasException, IOException;
	
	public void putModuloA(Integer rIntModuloId,
			RichiestaAmmissioneFinanziamentoPutDto request,
			HttpServletRequest httpRequest) throws Exception, PgmeasException;
	
	public ModuloIntervento getModuloAIntervento(Integer interventoId, HttpServletRequest httpRequest) throws Exception;
	
	public List<ModuloInterventoStruttura> getModuloAInterventiStruttura(Integer interventoId, HttpServletRequest httpRequest) throws Exception;

	public ModuloAPutByRegioneModel putModuloARegione(ModuloAPutByRegioneModel body, Integer rIntModuloId,
			HttpServletRequest httpRequest) throws Exception;
	
	public Integer putModuloAInvia(Integer rIntModuloId, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;

	public Integer putModuloAApprova(Integer rIntModuloId, Integer interventoId, AllegatoLightExtDto moduloA, 
			HttpServletRequest httpRequest) throws Exception;

	public Integer putModuloARespinge(RespingimentoDto respingimentoDto, Integer rIntModuloId, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;

}

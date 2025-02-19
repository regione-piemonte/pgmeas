/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import static it.csi.pgmeas.commons.util.ricerca.RicercaUtils.checkAnnoCorrente;
import static it.csi.pgmeas.commons.util.ricerca.RicercaUtils.genericCheck;
import static it.csi.pgmeas.commons.util.ricerca.RicercaUtils.getCodiciModuloA;
import static it.csi.pgmeas.commons.util.ricerca.RicercaUtils.getPageRequestFromFilter;
import static it.csi.pgmeas.commons.util.ricerca.RicercaUtils.getStatiEsclusiRicercaModuloA;
import static it.csi.pgmeas.commons.util.ricerca.RicercaUtils.getStatiEsclusiRicercaProgrammazione;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.v2.RicercaInterventiFilterDto;
import it.csi.pgmeas.commons.dto.v2.RicercaInterventiResultDto;
import it.csi.pgmeas.commons.dto.v2.RicercaModuloAResultDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.repository.RicercaRepository;
import it.csi.pgmeas.pgmeasproject.service.RicercaService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class RicercaServiceImpl implements RicercaService {
	@Autowired
	private RicercaRepository ricercaRepository;

	//TODO fare decodifica degli stati sulla base del contesto??
	
	@Override
	public List<RicercaInterventiResultDto> ricercaByAllFilters(HttpServletRequest httpRequest,
			RicercaInterventiFilterDto filters) throws PgmeasException {
		genericCheck(httpRequest, filters);
		
		List<String> stati = getStatiEsclusiRicercaProgrammazione(); //TODO da capire
		
		PageRequest pageRequest = getPageRequestFromFilter(filters);
		
		//TODO da capire
		List<RicercaInterventiResultDto> interventi = ricercaRepository
				.findAllInterventiSenzaCancellatiByAllFiltriAndStati(filters, stati, pageRequest);

		return interventi;
	}
	
	@Override
	public List<RicercaInterventiResultDto> getInterventiByAllFilters(HttpServletRequest httpRequest, 
			RicercaInterventiFilterDto filters) throws PgmeasException, ExecutionException {
		genericCheck(httpRequest, filters);
		checkAnnoCorrente(filters.getAnno());
		
		//GESTIONE - !AMMISSIONE FINANZIAMENTO
		//stato intervento = tutti tranne annullato, inserito, proposto e finanziabile (lato fe e da query)
		
		//MONITORAGGIO
		//stato intervento = tutti tranne annullato, inserito, proposto e finanziato (lato fe e da query)
		
		List<String> stati = getStatiEsclusiRicercaProgrammazione();
		
		PageRequest pageRequest = getPageRequestFromFilter(filters);
		
		List<RicercaInterventiResultDto> interventi = ricercaRepository
				.findAllInterventiSenzaCancellatiByAllFiltriAndStati(filters, stati, pageRequest);

		return interventi;
	}

	@Override
	public List<RicercaModuloAResultDto> getInterventiModuloAByAllFilters(HttpServletRequest httpRequest,
			RicercaInterventiFilterDto filters) throws PgmeasException, ExecutionException {
		genericCheck(httpRequest, filters);
		
		List<String> stati = getStatiEsclusiRicercaModuloA();
		List<String> moduli = getCodiciModuloA();
		
		PageRequest pageRequest = getPageRequestFromFilter(filters);
		
		List<RicercaModuloAResultDto> interventi = ricercaRepository
				.findAllInterventiModuloASenzaCancellatiByAllFiltriAndStatiAndModuli(filters, stati, moduli,
						pageRequest);

		return interventi;
	}

}

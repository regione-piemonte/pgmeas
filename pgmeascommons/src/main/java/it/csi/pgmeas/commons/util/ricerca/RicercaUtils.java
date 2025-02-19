/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.ricerca;

import static it.csi.pgmeas.commons.util.APISecurityFilterUtils.getUser;
import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfAsr;
import static it.csi.pgmeas.commons.util.ProfileUtils.hasFunctionality;
import static it.csi.pgmeas.commons.validation.ValidationUtils.integerNullOrNotValidValidator;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.v2.RicercaInterventiFilterDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.OrderByInterventoEnum;
import it.csi.pgmeas.commons.util.enumeration.OrderDirectionEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.commons.validation.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;

public class RicercaUtils {
	
	public static void genericCheck(HttpServletRequest httpRequest,
			RicercaInterventiFilterDto filters) throws PgmeasException {
		UserInfoRecord userInfo = getUser(httpRequest);

		hasFunctionality(userInfo, FunzionalitaEnum.OP_RICERCA_INTERVENTO);
		
		checkBodyFilters(filters);
		
		if(checkIfAsr(userInfo)) {
			List<Integer> aziende = new ArrayList<Integer>();
			aziende.add(userInfo.enteId());
			filters.setAziende(aziende);
		}
	}
	
	public static List<String> getStatiEsclusiRicercaProgrammazione() {
		List<String> stati = new ArrayList<String>();
		stati.add(InterventoStatoEnum.ANNULLATO.getCode());
		
		return stati;
	}
	
	public static List<String> getStatiEsclusiRicercaModuloA() {
		List<String> stati = new ArrayList<String>();
		stati.add(InterventoStatoEnum.ANNULLATO.getCode());
		stati.add(InterventoStatoEnum.INSERITO.getCode());
		stati.add(InterventoStatoEnum.PROPOSTO.getCode());
		
		return stati;
	}
	
	public static List<String> getCodiciModuloA() {
		List<String> moduli = new ArrayList<String>();
		moduli.add(ModuloTipoEnum.MODULO_A.getCode());
		moduli.add(ModuloTipoEnum.MODULO_A_A.getCode());
		
		return moduli;
	}
	
	public static void checkBodyFilters(RicercaInterventiFilterDto filters) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
		integerNullOrNotValidValidator("filtro anno", filters.getAnno(), listaErroriRilevati);
		
		if (listaErroriRilevati.size() > 0) { 
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Payload non compliant",
					listaErroriRilevati, "");
		}
	}
	
	public static void checkAnnoCorrente(Integer anno) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
		int currentYear = Year.now().getValue();
		if (anno != currentYear) {
			listaErroriRilevati.add(new ErroreDettaglio("",
					"L'anno " +  anno + " inserito nel filtro non corrisponde all'anno corrente (" + currentYear + ")."));
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Payload non compliant", listaErroriRilevati,
					"");
		}
	}
	
	public static PageRequest getPageRequestFromFilter(RicercaInterventiFilterDto filters) {
		String orderBy = getOrderBy(filters.getOrderBy());
		Sort.Direction orderDirection = getOrderDirection(filters.getOrderDirection());
		Integer rowPerPage = getRowPerPage(filters.getRowPerPage());
		Integer pageNumber = getPageNumber(filters.getPageNumber());
		
		return PageRequest.of(pageNumber, rowPerPage, Sort.by(orderDirection, orderBy));
	}

	
	private static String getOrderBy(String orderByFilter) {
		String orderBy = OrderByInterventoEnum.INTCUP.getCode();
		
		if(Strings.isNotEmpty(orderByFilter)) {
			orderBy = OrderByInterventoEnum.valueOf(orderByFilter.toUpperCase()).getCode();
		}
		
		return orderBy;
	}
	
	private static Sort.Direction getOrderDirection(String orderDirectionFilter) {
		String orderDirection = OrderDirectionEnum.ASC.getCode();
		
		if(Strings.isNotEmpty(orderDirectionFilter)) {
			orderDirection = OrderDirectionEnum.valueOf(orderDirectionFilter.toUpperCase()).getCode();
		}
		
		return Sort.Direction.fromString(orderDirection);
	}
	
	private static Integer getPageNumber(Integer pageNumberFilter) {
		Integer pageNumber = 0;
		
		if(pageNumberFilter != null) {
			pageNumber = pageNumberFilter;
		}
		
		return pageNumber;
	}
	
	private static Integer getRowPerPage(Integer rowPerPageFilter) {
		Integer rowPerPage = 50;
		
		if(rowPerPageFilter != null) {
			rowPerPage = rowPerPageFilter;
		}
		
		return rowPerPage;
	}
}

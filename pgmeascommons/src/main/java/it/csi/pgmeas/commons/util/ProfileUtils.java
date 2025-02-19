/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util;

import static it.csi.pgmeas.commons.validation.ValidationUtils.generatePgmeasException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.integration.configuratore.dto.Funzionalita;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.enumeration.ProfiloEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

public class ProfileUtils {

	private static void isCorrectProfile(UserInfoRecord user, ProfiloEnum profile) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
		boolean isCorrectProfile = profile.getCode().equals(user.profilo());

		if(!isCorrectProfile) {
			listaErroriRilevati.add(new ErroreDettaglio("profilo", "Profilo non ammesso"));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Profilo non ammesso",
					listaErroriRilevati, "Profilo non ammesso: " + user.profilo());
		}
	}
	
	public static void isFunzionarioAsr(UserInfoRecord user) throws PgmeasException {
		isCorrectProfile(user, ProfiloEnum.FUNZIONARIO_ASR);
	}
	
	public static void isDirigenteAsr(UserInfoRecord user) throws PgmeasException {
		isCorrectProfile(user, ProfiloEnum.DIRIGENTE_ASR);
	}
	
	public static void isFunzionarioRegione(UserInfoRecord user) throws PgmeasException {
		isCorrectProfile(user, ProfiloEnum.FUNZIONARIO_REGIONE);
	}
	
	public static void isDirigenteRegione(UserInfoRecord user) throws PgmeasException {
		isCorrectProfile(user, ProfiloEnum.DIRIGENTE_REGIONE);
	}
	
	private static void isCorrectProfiles(UserInfoRecord user, List<ProfiloEnum> profiles) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		boolean isCorrectProfile = profiles.stream().anyMatch(prof -> prof.getCode().equals(user.profilo()));

		if(!isCorrectProfile) {
			listaErroriRilevati.add(new ErroreDettaglio("profilo", "Profilo non ammesso"));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Profilo non ammesso",
					listaErroriRilevati, "Profilo non ammesso: " + user.profilo());
		}
	}
	
	public static void isAsr(UserInfoRecord user) throws PgmeasException {
		List<ProfiloEnum> profili = new ArrayList<ProfiloEnum>();
		profili.add(ProfiloEnum.FUNZIONARIO_ASR);
		profili.add(ProfiloEnum.DIRIGENTE_ASR);
		isCorrectProfiles(user, profili);
	}
	
	public static boolean checkIfAsr(UserInfoRecord user) {
		List<ProfiloEnum> profili = new ArrayList<ProfiloEnum>();
		profili.add(ProfiloEnum.FUNZIONARIO_ASR);
		profili.add(ProfiloEnum.DIRIGENTE_ASR);
		return profili.stream().anyMatch(prof -> prof.getCode().equals(user.profilo()));
	}
	
	public static void isRegione(UserInfoRecord user) throws PgmeasException {
		List<ProfiloEnum> profili = new ArrayList<ProfiloEnum>();
		profili.add(ProfiloEnum.FUNZIONARIO_REGIONE);
		profili.add(ProfiloEnum.DIRIGENTE_REGIONE);
		isCorrectProfiles(user, profili);
	}
	
	public static boolean checkIfRegione(UserInfoRecord user) {
		List<ProfiloEnum> profili = new ArrayList<ProfiloEnum>();
		profili.add(ProfiloEnum.FUNZIONARIO_REGIONE);
		profili.add(ProfiloEnum.DIRIGENTE_REGIONE);
		return profili.stream().anyMatch(prof -> prof.getCode().equals(user.profilo()));
	}
	
	public static void hasFunctionality(UserInfoRecord user, FunzionalitaEnum funzionalitaEnum) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
		Optional<Funzionalita> funzionalita = user.listaFunzionalita().stream()
				.filter(funz -> funz.getCodice().equals(funzionalitaEnum.getCode())).findFirst();
		if(funzionalita.isEmpty()) {
			listaErroriRilevati.add(new ErroreDettaglio("funzionalità", "Funzionalità non ammessa"));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Funzionalità non ammessa",
					listaErroriRilevati, "Funzionalità non ammessa: " + funzionalitaEnum.getCode());
		}
	}
}

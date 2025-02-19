/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.dev;

import java.time.Year;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.csi.iride2.policy.entity.Identita;
import it.csi.pgmeas.commons.integration.configuratore.dto.Funzionalita;
import it.csi.pgmeas.commons.util.CommonConstants;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.enumeration.ProfiloEnum;
import it.csi.pgmeas.commons.util.record.InfoProgrammazione;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import jakarta.servlet.http.HttpServletRequest;

public class UserDevUtils implements CommonConstants {
	private static final Logger LOG = LoggerFactory.getLogger(UserDevUtils.class);

	public static UserInfoRecord createDevUser(HttpServletRequest httpRequest, Identita identita) {
		String lcceToken = (String) httpRequest.getParameter(LCCE_TOKEN);
		String profilo = parseDevLcceTokenForProfilo(lcceToken);

		List<Funzionalita> funzionalita = List.of(createFunzionalita(profilo, null),
				createFunzionalita(FunzionalitaEnum.OP_RICERCA_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_INSERISCI_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_MODIFICA_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_CONSULTA_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_INVIA_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_APPROVA_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_RESPINGI_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_ELIMINA_INTERVENTO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_CONSULTA_MONITORAGGIO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_INSERISCI_MONITORAGGIO.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_INSERISCI_PROGRAMMAZIONE.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_PROROGA_FINE_PROGRAMMAZIONE.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_INSERISCI_MODULO_A.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_MODIFICA_MODULO_A.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_CONSULTA_MODULO_A.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_INVIA_MODULO_A.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_APPROVA_MODULO_A.getCode(), profilo),
				createFunzionalita(FunzionalitaEnum.OP_RESPINGI_MODULO_A.getCode(), profilo));

		DevLcceTokenInfo dlti = parseDevLcceToken(lcceToken);

		Integer currentYear = Year.now().getValue();
		String triennio = currentYear + "-" + (currentYear + 2);
		InfoProgrammazione prog = new InfoProgrammazione(true, triennio, currentYear.toString(), "", "", "");
		// getProgrammazione(httpRequest, dlti.azienda());
		return new UserInfoRecord(identita.getNome(), identita.getCognome(), identita.getCodFiscale(), dlti.ruolo(),
				profilo, 12, dlti.azienda(), dlti.azienda(), "Azienda " + dlti.azienda(), "Azienda " + dlti.azienda(), //
				"2", "Quadrante " + "PROVA", // String idQuadrante, String descrQuadrante
				funzionalita, //
				prog);
	}

	private static Funzionalita createFunzionalita(String codice, String codiceFunzionalitaPadre) {
		Funzionalita funzionalita = new Funzionalita();
		funzionalita.setCodice(codice);
		funzionalita.setCodiceFunzionalitaPadre(codiceFunzionalitaPadre);
		return funzionalita;
	}

	private static String parseDevLcceTokenForProfilo(String lcceToken) {
		String prof = ProfiloEnum.FUNZIONARIO_ASR.getCode();
		try {
			String[] l = lcceToken.split("-");
			if (l != null && l.length >= 3) {
				prof = l[2];
			}
		} catch (Exception e) {
			LOG.debug("[LoginController::parseDevLcceToken] ", e);
		}

		return prof;
	}

	private static DevLcceTokenInfo parseDevLcceToken(String lcceToken) {
		DevLcceTokenInfo i = new DevLcceTokenInfo("1", "010301");
		try {
			String[] l = lcceToken.split("-");
			if (l != null && l.length >= 2) {
				i = new DevLcceTokenInfo(l[0], l[1]);
			}
		} catch (Exception e) {
			LOG.debug("[LoginController::parseDevLcceToken] ", e);
		}

		return i;
	}

	public static String getShibbolethTokenDevMode(HttpServletRequest httpRequest) {
		LOG.info(" **** DEV-MODE ON -- getIrideTokenDevMode");
		String marker = (String) httpRequest.getParameter(AUTH_ID_MARKER);
		if (marker == null) {
			marker = (String) httpRequest.getParameter(AUTH_ID_MARKER2);
		}

		// use default
		if (marker == null) {
			marker = gatFakeMarkerIdentita();
		}
		return marker;
	}

	public static Identita gatFakeIdentita() {
		Identita i = new Identita();
		i.setCodFiscale("TRNGNN58T44G138K");
		i.setCognome("Piemonte");
		i.setNome("Demo 21");
		i.setIdProvider("SHIB");
		i.setTimestamp("timestamp");
		i.setLivelloAutenticazione(0);
		i.setMac("mac");

		return i;
	}

	public static String gatFakeMarkerIdentita() {

		return gatFakeIdentita().toString();
	}

}

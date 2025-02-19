/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.EnteDto;
import it.csi.pgmeas.commons.dto.EnteQuadranteDto;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.integration.configuratore.dto.ModelTokenInformazione;
import it.csi.pgmeas.commons.integration.configuratore.dto.Richiedente;
import it.csi.pgmeas.commons.util.CommonConstants;
import it.csi.pgmeas.commons.util.record.InfoProgrammazione;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.exception.ConfiguratoreException;
import it.csi.pgmeas.service.gateway.integration.configuratore.ConfiguratoreClient;
import it.csi.pgmeas.service.gateway.proxy.utils.RestClient;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ConfiguratoreService implements CommonConstants {
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguratoreService.class);

	@Value("${IrideIdAdapterFilter.devmode:false}")
	private boolean devmode;

	@Autowired
	ConfiguratoreClient configuratoreClient;

	@Autowired
	private RestClient client;

	private InfoProgrammazione getProgrammazione(HttpServletRequest inRequest, UserInfoRecord tmpuser,
			String codAzienda) {
		InfoProgrammazione prog = null;
		try {
			prog = client.get(InfoProgrammazione.class, inRequest, tmpuser, //
					"/api/programmazione/info/{codEnte}", //
					codAzienda);
		} catch (Exception e) {
			LOG.warn("[ConfiguratoreService::getUserInfoFromTokenLcce] ", e);
			prog = new InfoProgrammazione(false, null, null, null, null, null);
		}

		return prog;
	}

	// http://localhost:9090/pgmeasapigateway/proxy/api/tipologiche/quadrante/010207?lcceToken=1
	private EnteQuadranteDto getEnteQuadrante(HttpServletRequest inRequest, UserInfoRecord tmpuser, String codEnte) {
		EnteQuadranteDto enteQuadrante = null;
		try {
			enteQuadrante = client.get(EnteQuadranteDto.class, inRequest, tmpuser, //
					"/api/tipologiche/quadrante/{codEnte}", //
					codEnte);
		} catch (Exception e) {
			LOG.warn("[ConfiguratoreService::getEnteQuadrante] ", e);
			enteQuadrante = new EnteQuadranteDto();
		}

		return enteQuadrante;
	}
	
	// http://localhost:9090/pgmeasapigateway/proxy/api/tipologiche/quadrante/010207?lcceToken=1
	private EnteDto getEnte(HttpServletRequest inRequest, UserInfoRecord tmpuser, String codEnte) {
		EnteDto ente = null;
		try {
			ente = client.get(EnteDto.class, inRequest, tmpuser, //
					"/api/tipologiche/ente/{codEnte}", //
					codEnte);
		} catch (Exception e) {
			LOG.warn("[ConfiguratoreService::getEnte] ", e);
			ente = new EnteDto();
		}

		return ente;
	}

	public UserInfoRecord getUserInfoRecord(HttpServletRequest httpRequest) throws CustomLoginException {
		UserInfoRecord user = null;
		EnteDto ente;
		EnteQuadranteDto enteQuadrante;
		String lcceToken = (String) httpRequest.getParameter(LCCE_TOKEN);

		if (lcceToken != null && lcceToken.length() > 0) {
			// chiamata a lcce / configuratore
			try {
				LOG.debug(" ** aggancio lcce");
				Optional<ModelTokenInformazione> tokenInformazione = configuratoreClient.getLoginTokenInfo(httpRequest,
						lcceToken);

				Richiedente richiedente = null;

				if (tokenInformazione.isPresent()) {
					richiedente = tokenInformazione.get().getRichiedente();
					richiedente.setListaFunzionalita(tokenInformazione.get().getFunzionalita());
					richiedente.setCollocazione(tokenInformazione.get().getRichiedente().getCollocazione());
					// richiedente.getCollocazione().setCodiceAzienda(tokenInformazione.get().getRichiedente().getCollocazione().getCodiceAzienda());
				}

				if (richiedente == null) {
					throw new ConfiguratoreException("Risposta configuratore non valida");
				}

				UserInfoRecord tmpuser = new UserInfoRecord(richiedente.getNome(), richiedente.getCognome(),
						richiedente.getCodiceFiscale(), "n/a", "n/a", 0, "n/a", "n/a", "n/a", "n/a", //
						"n/a", "n/a", // String idQuadrante, String descrQuadrante
						richiedente.getListaFunzionalita(), new InfoProgrammazione(true, "", "", "", "", ""));

				
				enteQuadrante = getEnteQuadrante(httpRequest, tmpuser, richiedente.getCollocazione().getCodiceAzienda());
				ente = getEnte(httpRequest, tmpuser, richiedente.getCollocazione().getCodiceAzienda());
				InfoProgrammazione programmazione = getProgrammazione(httpRequest, tmpuser,
						richiedente.getCollocazione().getCodiceAzienda());

				user = new UserInfoRecord(richiedente.getNome(), richiedente.getCognome(),
						richiedente.getCodiceFiscale(), richiedente.getRuolo(), richiedente.getProfilo(),
						(ente.getEnteId() != null ? ente.getEnteId() : 0),
						richiedente.getCollocazione().getCodiceAzienda(),
						richiedente.getCollocazione().getCodiceCollocazione(),
						richiedente.getCollocazione().getDescrizioneAzienda(),
						richiedente.getCollocazione().getDescrizioneCollocazione(), //
						(enteQuadrante.getQuadranteId() != null ? enteQuadrante.getQuadranteId().toString() : ""), //
						enteQuadrante.getQuadranteDesc(), // String idQuadrante, String descrQuadrante
						richiedente.getListaFunzionalita(), programmazione);

				LOG.warn(String.format(
						"[ConfiguratoreService::getUserInfoRecord]  Richiedente [nome=%s, cognome=%s, codiceFiscale=%s]",
						richiedente.getNome(), richiedente.getCognome(), richiedente.getCodiceFiscale()));

			} catch (Exception e) {
				LOG.error("Errore chiamata configuratore - {}", e);
				throw new CustomLoginException("Errore chiamata al configuratore " + e.getMessage());
			}

		} else {
			throw new CustomLoginException("Manca il token di lcce");
		}

		return user;
	}
	
	public UserInfoRecord aggiornaProgrammazioneUtente(HttpServletRequest httpRequest, UserInfoRecord userOld) throws CustomLoginException {
		InfoProgrammazione programmazione = getProgrammazione(httpRequest, userOld,
				userOld.codiceAzienda());

		UserInfoRecord user = new UserInfoRecord(userOld.nome(), userOld.cognome(),
				userOld.codiceFiscale(), userOld.ruolo(), userOld.profilo(), userOld.enteId(),
				userOld.codiceAzienda(), userOld.codiceColl(),
				userOld.descAzienda(), userOld.descCollocazione(),
				userOld.idQuadrante(), userOld.descrQuadrante(),
				userOld.listaFunzionalita(), programmazione);

		LOG.debug(String.format(
				"[ConfiguratoreService::aggiornaProgrammazioneUtente]  Richiedente [nome=%s, cognome=%s, codiceFiscale=%s]",
				userOld.nome(), userOld.cognome(), userOld.codiceFiscale()));

		return user;
	}

}

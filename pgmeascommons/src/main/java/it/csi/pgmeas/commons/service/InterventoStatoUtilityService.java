/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import static it.csi.pgmeas.commons.validation.ValidationUtils.checkObjectIsPresentByProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.InterventoStato;
import it.csi.pgmeas.commons.model.RInterventoStato;
import it.csi.pgmeas.commons.repository.InterventoStatoRepository;
import it.csi.pgmeas.commons.repository.RInterventoStatoRepository;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class InterventoStatoUtilityService {

	@Autowired 
	InterventoStatoRepository interventoStatoRepository;
	@Autowired
	private RInterventoStatoRepository rInterventoStatoRepository;
	
	public InterventoStato getInterventoStatoByInterventoStatoCod(InterventoStatoEnum interventoStato) throws PgmeasException {
		List<InterventoStato> resInterventoStato = interventoStatoRepository.findAllValidByIntStatoCod(interventoStato.getCode());
		if(resInterventoStato!=null && resInterventoStato.size()>0) {
			return resInterventoStato.get(0);
		}else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "codice InterventoStato non trovato",
					new ArrayList<ErroreDettaglio>(), "codice InterventoStato non trovato :" + interventoStato.getCode());
		}
		return null;
	}
	
	public InterventoStato getInterventoStatoByInterventoId(Integer interventoId) throws PgmeasException {
		InterventoStato interventoStato = interventoStatoRepository.findValidByIntId(interventoId);
		if (interventoStato != null) {
			return interventoStato;
		} else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "InterventoStato non trovato",
					new ArrayList<ErroreDettaglio>(), "InterventoStato non trovato :" + interventoId.toString());
		}
		return null;
	}
	
	public RInterventoStato getRInterventoStatoCurrent(Integer interventoId, Integer enteId) throws PgmeasException {
		RInterventoStato rInterventoStatoOld = rInterventoStatoRepository.findValidByIntIdAndEnteId(interventoId, enteId);
		checkObjectIsPresentByProperty(rInterventoStatoOld, interventoId.toString(), ValidationNameEnum.R_INTERVENTO_STATO_BY_INTERVENTO_ID);
		return rInterventoStatoOld;
	}

	public void salvaStatoInterventoInserito(UserInfoRecord user, Timestamp now, Ente ente, Integer interventoId) throws PgmeasException {
		 salvaRInterventoStato(user, now, ente, interventoId,InterventoStatoEnum.INSERITO);
	}
	
	public RInterventoStato salvaStatoInterventoProposto(UserInfoRecord user, Timestamp now, Ente ente, Integer interventoId) throws PgmeasException {
		RInterventoStato rInterventoStatoOdl = getRInterventoStatoCurrent(interventoId, ente.getEnteId());
		storicizzaRInterventoStatoPrecedente(user,now,ente,rInterventoStatoOdl); 
		return salvaRInterventoStato(user, now, ente, interventoId,InterventoStatoEnum.PROPOSTO);
	}
	
	public RInterventoStato salvaStatoInterventoFinanziato(UserInfoRecord user, Timestamp now, Ente ente, Integer interventoId) throws PgmeasException {
		RInterventoStato rInterventoStatoOdl = getRInterventoStatoCurrent(interventoId, ente.getEnteId());
		storicizzaRInterventoStatoPrecedente(user,now,ente,rInterventoStatoOdl); 
		//TODO GIUSTO FINAZIATO SU DIAGRAMMA FINANZIABILE
		return salvaRInterventoStato(user, now, ente, interventoId,InterventoStatoEnum.FINANZIABILE);
	}
	
	public void salvaStatoInterventoAmmessoAlFinanziamento(UserInfoRecord user, Timestamp now, Ente ente, Integer interventoId) throws PgmeasException {
		RInterventoStato rInterventoStatoOdl = getRInterventoStatoCurrent(interventoId, ente.getEnteId());
		storicizzaRInterventoStatoPrecedente(user,now,ente,rInterventoStatoOdl); 
		salvaRInterventoStato(user, now, ente, interventoId,InterventoStatoEnum.AMMESSO_AL_FINANZIAMENTO);
	}
	
	private void storicizzaRInterventoStatoPrecedente(UserInfoRecord user, Timestamp now, Ente ente,
			RInterventoStato rInterventoStatoOdl) throws PgmeasException {
		rInterventoStatoOdl.setValiditaFine(now);
		rInterventoStatoOdl.setUtenteModifica(user.codiceFiscale());
		rInterventoStatoOdl.setDataModifica(now);
		rInterventoStatoRepository.save(rInterventoStatoOdl);
	}

	private RInterventoStato salvaRInterventoStato(UserInfoRecord userInfo, Timestamp now, Ente ente, Integer interventoId,InterventoStatoEnum interventoStatoEnum,String note) throws PgmeasException {
		InterventoStato statoInserito = getInterventoStatoByInterventoStatoCod(interventoStatoEnum);
		RInterventoStato rInterventoStato = buildRInterventoStato(userInfo, now, ente, interventoId, statoInserito,note);
		return rInterventoStatoRepository.saveAndFlush(rInterventoStato);
	}
	
	private RInterventoStato salvaRInterventoStato(UserInfoRecord userInfo, Timestamp now, Ente ente, Integer interventoId,InterventoStatoEnum interventoStatoEnum) throws PgmeasException {
		InterventoStato statoInserito = getInterventoStatoByInterventoStatoCod(interventoStatoEnum);
		RInterventoStato rInterventoStato = buildRInterventoStato(userInfo, now, ente, interventoId, statoInserito,null);
		return rInterventoStatoRepository.saveAndFlush(rInterventoStato);
	}
	
	public RInterventoStato salvaStatoInterventoRifiutato(UserInfoRecord user, Timestamp now, Ente ente, Integer interventoId,
			String note) throws Exception {
		RInterventoStato rInterventoStatoOdl = getRInterventoStatoCurrent(interventoId, ente.getEnteId());
		storicizzaRInterventoStatoPrecedente(user,now,ente,rInterventoStatoOdl); 
		//TODO VERIFICARE CON NICOLA
		// CERCARE MODULO SU R MODULO E STORICIZZARE
		return salvaRInterventoStato(user, now, ente, interventoId,InterventoStatoEnum.INSERITO,note);
	}

	public void salvaStatoInterventoEliminato(UserInfoRecord user, Timestamp now, Ente ente, Integer interventoId) throws Exception {
		RInterventoStato rInterventoStatoOdl = getRInterventoStatoCurrent(interventoId, ente.getEnteId());
		storicizzaRInterventoStatoPrecedente(user,now,ente,rInterventoStatoOdl); 
		salvaRInterventoStato(user, now, ente, interventoId,InterventoStatoEnum.ANNULLATO);
	}
	
	private static RInterventoStato buildRInterventoStato(UserInfoRecord userInfo, Timestamp now, Ente ente,
			Integer interventoId, InterventoStato statoInserito, String note) {
		RInterventoStato rInterventoStato = new RInterventoStato();
		rInterventoStato.setIntId(interventoId);
		rInterventoStato.setEnteId(ente.getEnteId());
		rInterventoStato.setIntStatoId(statoInserito.getIntStatoId());
		rInterventoStato.setUtenteCreazione(userInfo.codiceFiscale());
		rInterventoStato.setUtenteModifica(userInfo.codiceFiscale());
		rInterventoStato.setValiditaInizio(now);
		rInterventoStato.setDataCreazione(now);
		rInterventoStato.setDataModifica(now);
		rInterventoStato.setNote(note);
		return rInterventoStato;
	}
}

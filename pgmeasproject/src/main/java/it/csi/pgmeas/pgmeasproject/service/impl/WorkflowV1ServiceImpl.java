/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import static it.csi.pgmeas.commons.util.APISecurityFilterUtils.getUser;
import static it.csi.pgmeas.commons.util.ProfileUtils.hasFunctionality;
import static it.csi.pgmeas.commons.util.ProfileUtils.isAsr;
import static it.csi.pgmeas.commons.util.ProfileUtils.isDirigenteRegione;
import static it.csi.pgmeas.commons.util.ProfileUtils.isRegione;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.checkCostoInterventoAndQE;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkAnnoIntervento;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkCfRup;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkStatoIntervento;
import static it.csi.pgmeas.commons.validation.ValidationUtils.validateRespingimentoDto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoStato;
import it.csi.pgmeas.commons.model.RInterventoStato;
import it.csi.pgmeas.commons.service.ClassificazioneTreeUtilityService;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.service.EventoUtilityService;
import it.csi.pgmeas.commons.service.InterventoStatoUtilityService;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.commons.service.ModuloUtilityService;
import it.csi.pgmeas.commons.util.enumeration.EventoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.pgmeasproject.service.WorkflowV1Service;
import it.csi.pgmeas.pgmeasproject.util.service.ModuloFileUtilityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class WorkflowV1ServiceImpl implements WorkflowV1Service {
	@Autowired
	private EnteUtilityService enteUtilityService;
	@Autowired
	private InterventoUtilityService interventoUtilityService;
	@Autowired
	private InterventoStatoUtilityService interventoStatoUtilityService;
	@Autowired
	private ModuloUtilityService moduloUtilityService;
	@Autowired
	private ModuloFileUtilityService moduloFileUtilityService;
	@Autowired
	private EventoUtilityService eventoUtilityService;
	@Autowired
	private ClassificazioneTreeUtilityService classificazioneTreeUtilityService;
	
// TODO INSERISCI INTERVENTO RIGA R_INTERVENTO MODULO DOCUMENTO_DATI_INTERVENTO STATO INSERITO

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Integer putInterventoInvia(AllegatoLightExtDto allegatoIntervento,Integer interventoId, HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		
		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_INVIA_INTERVENTO);
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		checkCfRup(intervento.getIntRupCf(), userInfo.codiceFiscale()); //questa operazione è permessa solo ai rup dell'intervento stesso
		checkAnnoIntervento("intAnno", intervento.getIntAnno());
		
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(interventoId);
		checkStatoIntervento(stato, InterventoStatoEnum.INSERITO);
		
		interventoUtilityService.validateRequiredInviaInterventoARegione(intervento);
		
		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());
		
		List<InterventoStrutturaV2GetDto> interventiStruttura = interventoUtilityService.getInterventiStruttura(interventoId, ente.getEnteId());
		
		List<Integer> qeNoTotList = classificazioneTreeUtilityService.getElementiQuadroEconomicoPerControllo();
		List<BigDecimal> qeStruttura = new ArrayList<BigDecimal>();
		
		interventiStruttura.stream().forEach(intStr -> {
		    // Filtra la mappa rimuovendo le chiavi presenti in qeNoTotList
		    Map<Integer, BigDecimal> filteredMap = intStr.getQuadroEconomico().entrySet().stream()
		            .filter(entry -> qeNoTotList.contains(entry.getKey()))
		            .collect(Collectors.toMap(Map.Entry::getKey,  
		            		entry -> entry.getValue() != null ? entry.getValue() : BigDecimal.ZERO ));

		    // Aggiungi i valori della mappa filtrata alla lista qeStruttura
		    qeStruttura.addAll(filteredMap.values());
		});
		checkCostoInterventoAndQE(intervento.getIntImporto(), qeStruttura);
		
		//TODO controllo se programmazione è aperta o in proroga per ente dell'utente collegato
		
		Timestamp now = Timestamp.from(Instant.now());
		
		// IL METODO SOLLEVA UNA PGMEAS EXCEPTION SE IL CAMBIO STATO NON E' CONSENTITO
		moduloUtilityService.storicizzaModuloByInterventoIdAndEnteId(interventoId,ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO,now,userInfo.codiceFiscale(), ente.getEnteId());
		moduloFileUtilityService.salvaModulo(allegatoIntervento, userInfo, now, intervento, ente, ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO, ModuloStatoEnum.PROPOSTO);

		RInterventoStato rInterventoStato = interventoStatoUtilityService.salvaStatoInterventoProposto(userInfo, now, ente, interventoId);
		eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.ASR_INVIA_A_REGIONE_UN_INTERVENTO, ente.getEnteId(), now, rInterventoStato.getRIntStatoId(), userInfo.codiceFiscale());
		
		return interventoId;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Integer putInterventoApprova(AllegatoLightExtDto allegatoIntervento, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		
		isDirigenteRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_APPROVA_INTERVENTO);
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		checkAnnoIntervento("intAnno", intervento.getIntAnno());
		
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(interventoId);
		checkStatoIntervento(stato, InterventoStatoEnum.PROPOSTO);
		
		interventoUtilityService.validateRequiredApprovaInterventoByRegione(intervento, userInfo, stato.getIntStatoCod());
		
		Ente ente = enteUtilityService.getEnteByEnteId(intervento.getEnteId());

		Timestamp now = Timestamp.from(Instant.now());
		
		moduloUtilityService.storicizzaModuloByInterventoIdAndEnteId(interventoId,ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO,now,userInfo.codiceFiscale(), ente.getEnteId());
		moduloFileUtilityService.salvaModulo(allegatoIntervento, userInfo, now, intervento, ente, ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO, ModuloStatoEnum.FINANZIATO);

		RInterventoStato rInterventoStato = interventoStatoUtilityService.salvaStatoInterventoFinanziato(userInfo, now, ente, interventoId);
		eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.REGIONE_APPROVA_INTERVENTO, ente.getEnteId(), now, rInterventoStato.getRIntStatoId(), userInfo.codiceFiscale());
		
		return interventoId;
	}
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public Integer putInterventoRifiuta(RespingimentoDto rifiutaInterventoDto, Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		
		isRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_RESPINGI_INTERVENTO);
		
		validateRespingimentoDto(rifiutaInterventoDto);
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		checkAnnoIntervento("intAnno", intervento.getIntAnno());
		
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(interventoId);
		checkStatoIntervento(stato, InterventoStatoEnum.PROPOSTO);
		
		Timestamp now = Timestamp.from(Instant.now());
		Ente ente = enteUtilityService.getEnteByEnteId(intervento.getEnteId());
		
		moduloUtilityService.cambioStatoModuloByInterventoId(interventoId, ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO, ModuloStatoEnum.INSERITO, now, userInfo, ente);
		
		RInterventoStato rInterventoStato = interventoStatoUtilityService.salvaStatoInterventoRifiutato(userInfo, now, ente, interventoId,rifiutaInterventoDto.getNote());
		eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.REGIONE_RESPINGE_INTERVENTO, ente.getEnteId(), now, rInterventoStato.getRIntStatoId(), userInfo.codiceFiscale());
		
		return interventoId;
	}
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public Integer putInterventoElimina(Integer interventoId,
			HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		
		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_ELIMINA_INTERVENTO);
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		checkAnnoIntervento("intAnno", intervento.getIntAnno());
		
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(interventoId);
		checkStatoIntervento(stato, InterventoStatoEnum.INSERITO);
		
		//TODO controllo se programmazione è aperta o in proroga per ente dell'utente collegato
		//TODO L’intervento non è ancora stato inviato a Regione Piemonte per approvazione. 
		
		Timestamp now = Timestamp.from(Instant.now());
		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());

		moduloUtilityService.cambioStatoModuloByInterventoId(interventoId, ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO, ModuloStatoEnum.ANNULLATO, now, userInfo, ente);
		interventoStatoUtilityService.salvaStatoInterventoEliminato(userInfo, now, ente, interventoId);

		return interventoId;
	}
}

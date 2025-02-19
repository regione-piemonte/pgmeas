/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import static it.csi.pgmeas.commons.service.ModuloUtilityService.validateRequiredPutModuloByRegione;
import static it.csi.pgmeas.commons.util.APISecurityFilterUtils.getUser;
import static it.csi.pgmeas.commons.util.ProfileUtils.hasFunctionality;
import static it.csi.pgmeas.commons.util.ProfileUtils.isAsr;
import static it.csi.pgmeas.commons.util.ProfileUtils.isDirigenteRegione;
import static it.csi.pgmeas.commons.util.ProfileUtils.isRegione;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.checkCostoInterventoAndQE;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkCfRup;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkStatoIntervento;
import static it.csi.pgmeas.commons.validation.ValidationUtils.validateRespingimentoDto;

import java.io.IOException;
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
import it.csi.pgmeas.commons.dto.RInterventoModuloDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoPutDto;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.dto.v2.ModuloAPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.ModuloIntervento;
import it.csi.pgmeas.commons.dto.v2.ModuloInterventoStruttura;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoStato;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.repository.InterventoRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaRepository;
import it.csi.pgmeas.commons.service.ClassificazioneTreeUtilityService;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.service.EventoUtilityService;
import it.csi.pgmeas.commons.service.InterventoStatoUtilityService;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.commons.service.ModuloUtilityService;
import it.csi.pgmeas.commons.service.QuadroEconomicoUtilityService;
import it.csi.pgmeas.commons.service.RInterventoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.AllegatoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.EventoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.pgmeasproject.service.ModuloAService;
import it.csi.pgmeas.pgmeasproject.util.service.AllegatoUtilityService;
import it.csi.pgmeas.pgmeasproject.util.service.DichiarazioneAppaltabilitaUtilityService;
import it.csi.pgmeas.pgmeasproject.util.service.ModuloFileUtilityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class ModuloAServiceImpl implements ModuloAService {
	@Autowired
	private EnteUtilityService enteUtilityService;
	@Autowired
	private InterventoRepository interventoRepository;
	@Autowired
	private InterventoUtilityService interventoUtilityService;
	@Autowired
	private InterventoStatoUtilityService interventoStatoUtilityService;
	@Autowired
	private RInterventoUtilityService rInterventoUtilityService;
	@Autowired
	private AllegatoUtilityService allegatoUtilityService;
	@Autowired
	private ModuloUtilityService moduloUtilityService;
	@Autowired
	private ModuloFileUtilityService moduloFileUtilityService;
	@Autowired
	private DichiarazioneAppaltabilitaUtilityService dichiarazioneAppaltabilitaUtilityService;
	@Autowired
	private QuadroEconomicoUtilityService quadroEconomicoUtilityService;
	@Autowired
	private InterventoStrutturaRepository interventoStrutturaRepository;
	@Autowired
	private EventoUtilityService eventoUtilityService;
	@Autowired
	private ClassificazioneTreeUtilityService classificazioneTreeUtilityService;
	
	/**
	 * Inserisci modulo A come per il "modulo Intervento" viene inserito un
	 * segnaposto e verrà permesso il download del modoulo on the fly Controlli
	 * preliminari non esiste un modulo a legato all'intervento non esiste un modulo
	 * aa legato all'intervento intervento.prioritaAnno <= anno corrente anno
	 * corrente >= primo anno della previsione di spesa
	 * 
	 */
	@Transactional(rollbackOn = Exception.class)
	@Override
	public RInterventoModuloDto postModuloA(RichiestaAmmissioneFinanziamentoDto request,
			HttpServletRequest httpRequest) throws PgmeasException, IOException{
		UserInfoRecord userInfo = getUser(httpRequest);
		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_INSERISCI_MODULO_A);
		
		Intervento intervento = interventoUtilityService.getInterventoById(request.getIntId());
		
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(intervento.getIntId());
		checkStatoIntervento(stato, InterventoStatoEnum.FINANZIABILE);
		
		Ente ente = enteUtilityService.getEnteByEnteId(intervento.getEnteId());
		Integer enteId = ente.getEnteId();
		
		List<Integer> qeNoTotList = classificazioneTreeUtilityService.getElementiQuadroEconomicoPerControllo();
		List<BigDecimal> qeStruttura = new ArrayList<BigDecimal>();
		
		request.getInterventoStrutturaMap().values().forEach(intStr -> {
		    // Filtra la mappa rimuovendo le chiavi presenti in qeNoTotList
		    Map<Integer, BigDecimal> filteredMap = intStr.getQuadroEconMap().entrySet().stream()
		            .filter(entry -> qeNoTotList.contains(entry.getKey()))
		            .collect(Collectors.toMap(Map.Entry::getKey,  
		            		entry -> entry.getValue() != null ? entry.getValue() : BigDecimal.ZERO ));

		    // Aggiungi i valori della mappa filtrata alla lista qeStruttura
		    qeStruttura.addAll(filteredMap.values());
		});
		
		checkCostoInterventoAndQE(intervento.getIntImporto(), qeStruttura);

		var now = Timestamp.from(Instant.now());

		moduloUtilityService.validateRequiredPostModuloAOrAA(request);
		moduloUtilityService.validateModuleAOrAAUnique(intervento, ente.getEnteId());
		moduloUtilityService.validateConsistencyPayload(request, intervento);
		
		List<InterventoStruttura> interventiStrutturaList = interventoStrutturaRepository
				.findAllSenzaCancellatiByIntIdAndEnteId(intervento.getIntId(), ente.getEnteId());
		
		moduloUtilityService.checkQuadroEconomicoPerStruttura(request, interventiStrutturaList);
		ModuloTipoEnum moduloTipo=
				ModuloTipoEnum.fromCode(request.getModuloTipo());
		
		RInterventoModuloDto result=moduloUtilityService.inserisciModuloFittizioByIntervento(intervento, ente, moduloTipo,
				ModuloStatoEnum.INSERITO, userInfo, now);

		allegatoUtilityService.salvaAllegato(request.getAllegatoProvAzApp(), userInfo.codiceFiscale(), now, intervento,
				ente, AllegatoTipoEnum.PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE);
		allegatoUtilityService.salvaAllegato(request.getAllegatoRelTec(), userInfo.codiceFiscale(), now, intervento,
				ente, AllegatoTipoEnum.RELAZIONE_TECNICA);
		
		rInterventoUtilityService.storicizzaRInterventoStatoProgettuale(intervento.getIntId(), userInfo, now, enteId);
		rInterventoUtilityService.salvaRInterventoStatoProgettuale(request, userInfo, now, intervento);

		
		for (InterventoStruttura to : interventiStrutturaList) {
			var from = request.getInterventoStrutturaMap().get(to.getIntStrId());
			if (from != null) {
				interventoUtilityService.storicizzaInterventoStruttura(to, now, userInfo.codiceFiscale());
				// TODO STORICIZZA_INTERNVENTO STRUTTURA
				moduloUtilityService.aggiornaInterventoStrutturaByRafInterventoStrutturaDto(now, userInfo, to, from);
				// PRIMO SALVATAGGIO DICHIARAZIONE APPALTABILITA

				dichiarazioneAppaltabilitaUtilityService.salvaDichiarazioneAppaltabilitaFromModuloTipoEnum(userInfo, now, ente,
						 from.getDicAppMap(), to.getIntStrId(),moduloTipo);

				quadroEconomicoUtilityService.storicizzaQuadroEconomico(userInfo, now, to.getIntStrId(), enteId);
				quadroEconomicoUtilityService.salvaQuadroEconomico(userInfo, now, ente, intervento,
						from.getQuadroEconMap(), to.getIntStrId());
			} else {
				moduloUtilityService.generaErrorePerInterventoStrutturaNonTrovato(to);
			}
		}
		
		interventoUtilityService.storicizzaIntervento(userInfo, now, intervento);
		moduloUtilityService.makeInterventoFromRichiestaAmmissioneFinanziamento(intervento, now, userInfo, request);
		intervento = interventoRepository.saveAndFlush(intervento);

		moduloUtilityService.aggiornaInterventoByRichiestaAmmissioneFinanziamentoDto(request, intervento, now, userInfo);
		
		return result;
	}
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public void putModuloA(Integer rIntModuloId,
			RichiestaAmmissioneFinanziamentoPutDto request, HttpServletRequest httpRequest)
			throws Exception, PgmeasException {
		var userInfo = getUser(httpRequest);
		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_MODIFICA_MODULO_A);
		
		Intervento intervento = interventoUtilityService.getInterventoById(request.getIntId());
		Ente ente = enteUtilityService.getEnteByEnteId(intervento.getEnteId());
		Integer enteId = ente.getEnteId();
		
		List<Integer> qeNoTotList = classificazioneTreeUtilityService.getElementiQuadroEconomicoPerControllo();
		List<BigDecimal> qeStruttura = new ArrayList<BigDecimal>();
		
		request.getInterventoStrutturaMap().values().forEach(intStr -> {
		    // Filtra la mappa rimuovendo le chiavi presenti in qeNoTotList
		    Map<Integer, BigDecimal> filteredMap = intStr.getQuadroEconMap().entrySet().stream()
		            .filter(entry -> qeNoTotList.contains(entry.getKey()))
		            .collect(Collectors.toMap(Map.Entry::getKey,  
		            		entry -> entry.getValue() != null ? entry.getValue() : BigDecimal.ZERO ));

		    // Aggiungi i valori della mappa filtrata alla lista qeStruttura
		    qeStruttura.addAll(filteredMap.values());
		});
		
		checkCostoInterventoAndQE(intervento.getIntImporto(), qeStruttura);
		
		var now = Timestamp.from(Instant.now());

		moduloUtilityService.validateRequiredPutModuloAOrAA(request, rIntModuloId);
		moduloUtilityService.validateConsistencyPayload(request, intervento);
		moduloUtilityService.validateConsistecyModuloAOrAA(request, intervento, rIntModuloId, ente.getEnteId());
		
		List<InterventoStruttura> interventiStrutturaList = interventoStrutturaRepository
				.findAllSenzaCancellatiByIntIdAndEnteId(intervento.getIntId(), ente.getEnteId());
		
		moduloUtilityService.checkQuadroEconomicoPerStruttura(request, interventiStrutturaList);

		ModuloTipoEnum moduloTipo=ModuloTipoEnum.fromCode(request.getModuloTipo());
//		errore trova il vecchio modulo e fallo fuori
		RInterventoModulo rInterventoModuloOld = moduloUtilityService.getRInterventoModuloByIdAndEnteId(rIntModuloId, ente.getEnteId());
		moduloUtilityService.storicizzaModuloByRInterventoModulo(rInterventoModuloOld, now, userInfo.codiceFiscale());
		moduloUtilityService.inserisciModuloFittizioByRInterventoModuloOld(intervento, ente, moduloTipo,
				ModuloStatoEnum.INSERITO, userInfo, now, rInterventoModuloOld);

		if (request.getAllegatoProvAzAppToDelete() != null && request.getAllegatoProvAzAppToDelete().getIdAllegato() != null) {
			allegatoUtilityService.storicizzaAllegato(request.getAllegatoProvAzAppToDelete(), now,
					userInfo.codiceFiscale(), ente);
		}
		
		if(request.getAllegatoProvAzApp() != null) {
			allegatoUtilityService.salvaAllegato(request.getAllegatoProvAzApp(), userInfo.codiceFiscale(), now,
					intervento, ente, AllegatoTipoEnum.PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE);
		}
		
		if (request.getAllegatoRelTecToDelete() != null && request.getAllegatoRelTecToDelete().getIdAllegato() != null) {
			allegatoUtilityService.storicizzaAllegato(request.getAllegatoRelTecToDelete(), now,
					userInfo.codiceFiscale(), ente);
		}
		
		if(request.getAllegatoRelTec() != null) {
			allegatoUtilityService.salvaAllegato(request.getAllegatoRelTec(), userInfo.codiceFiscale(), now,
					intervento, ente, AllegatoTipoEnum.RELAZIONE_TECNICA);
		}
		
		rInterventoUtilityService.storicizzaRInterventoStatoProgettuale(intervento.getIntId(), userInfo, now, enteId);
		rInterventoUtilityService.salvaRInterventoStatoProgettuale(request, userInfo, now, intervento);

		for (InterventoStruttura to : interventiStrutturaList) {
			var from = request.getInterventoStrutturaMap().get(to.getIntStrId());
			if (from != null) {
				interventoUtilityService.storicizzaInterventoStruttura(to, now, userInfo.codiceFiscale());
				// TODO STORICIZZA_INTERNVENTO STRUTTURA
				moduloUtilityService.aggiornaInterventoStrutturaByRafInterventoStrutturaDto(now, userInfo, to, from);

				dichiarazioneAppaltabilitaUtilityService.storicizzaDichiarazioneAppaltabilita(userInfo, now, to.getIntStrId(), enteId);
				dichiarazioneAppaltabilitaUtilityService.salvaDichiarazioneAppaltabilitaFromModuloTipoEnum(userInfo, now, ente,
						 from.getDicAppMap(), to.getIntStrId(),moduloTipo);

				quadroEconomicoUtilityService.storicizzaQuadroEconomico(userInfo, now, to.getIntStrId(), enteId);
				quadroEconomicoUtilityService.salvaQuadroEconomico(userInfo, now, ente, intervento,
						from.getQuadroEconMap(), to.getIntStrId());
			} else {
				moduloUtilityService.generaErrorePerInterventoStrutturaNonTrovato(to);
			}
		}

		interventoUtilityService.storicizzaIntervento(userInfo, now, intervento);
		moduloUtilityService.makeInterventoFromRichiestaAmmissioneFinanziamento(intervento, now, userInfo, request);
		intervento = interventoRepository.saveAndFlush(intervento);
		
		moduloUtilityService.aggiornaInterventoByRichiestaAmmissioneFinanziamentoDto(request, intervento, now, userInfo);
	}
	
	@Override
	public ModuloIntervento getModuloAIntervento(Integer interventoId, HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		
		hasFunctionality(userInfo, FunzionalitaEnum.OP_CONSULTA_MODULO_A); 
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(interventoId);
		List<InterventoStatoEnum> statiAmmessi = new ArrayList<InterventoStatoEnum>();
		statiAmmessi.add(InterventoStatoEnum.FINANZIABILE);
		statiAmmessi.add(InterventoStatoEnum.AMMESSO_AL_FINANZIAMENTO);
		
		checkStatoIntervento(stato, statiAmmessi);
		
		Integer enteId = intervento.getEnteId();
		
		InterventoV2GetDto interventoDto = interventoUtilityService.getInterventoDto(intervento, enteId, userInfo, stato.getIntStatoCod());
		ModuloIntervento moduloInterventoDto = MappingUtils.copy(interventoDto, new ModuloIntervento());
		
		moduloInterventoDto.setAllegatoProvvedimentoAziendaleApprovazione(allegatoUtilityService.buildInterventoAllegatiDto(intervento.getIntId(), enteId,
				AllegatoTipoEnum.PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE)); 
		moduloInterventoDto.setAllegatoRelazioneTecnica(allegatoUtilityService.buildInterventoAllegatiDto(intervento.getIntId(), enteId,
				AllegatoTipoEnum.RELAZIONE_TECNICA)); 
		
		moduloInterventoDto.setAllegatoNullaOsta(allegatoUtilityService.buildInterventoAllegatiDto(intervento.getIntId(), enteId,
				AllegatoTipoEnum.NULLA_OSTA)); 
		moduloInterventoDto.setAllegatoDecretoMinisteriale(allegatoUtilityService.buildInterventoAllegatiDto(intervento.getIntId(), enteId,
				AllegatoTipoEnum.DECRETO_MINISTERIALE)); 
		
		RInterventoModulo rInterventoModulo = moduloUtilityService.getModuloAByInterventoIdAndEnteId(interventoId, enteId);
		if(rInterventoModulo != null) {
			ModuloTipoEnum moduloTipo = moduloUtilityService.getModuloTipoEnumByRInterventoModulo(rInterventoModulo);
			moduloInterventoDto.setRIntModuloId(rInterventoModulo.getRIntModuloId());
			moduloInterventoDto.setRIntModuloStatoId(rInterventoModulo.getModuloStatoId());
			moduloInterventoDto.setNoteRespingimentoModulo(rInterventoModulo.getNote());
			moduloInterventoDto.setModuloTipo(moduloTipo.getCode()); 
		}
		
		return moduloInterventoDto;
	}

	@Override
	public List<ModuloInterventoStruttura> getModuloAInterventiStruttura(Integer interventoId, HttpServletRequest httpRequest) throws Exception {
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		Integer enteId = intervento.getEnteId();
		
		List<InterventoStrutturaV2GetDto> interventiStruttura = interventoUtilityService.getInterventiStruttura(interventoId, enteId);
		List<ModuloInterventoStruttura> moduloInterventoStrutturaDto = 
				new ArrayList<ModuloInterventoStruttura>();
		
		interventiStruttura.stream().forEach(is -> {
			ModuloInterventoStruttura modInterventoStruttura = MappingUtils.copy(is, new ModuloInterventoStruttura());
			modInterventoStruttura.setDichiarazioneAppaltabilita(dichiarazioneAppaltabilitaUtilityService.getDichiarazioneAppaltabilita(is.getIntStrId(), enteId));
			moduloInterventoStrutturaDto.add(modInterventoStruttura);
		});
		
		return moduloInterventoStrutturaDto;
	}
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public ModuloAPutByRegioneModel putModuloARegione(ModuloAPutByRegioneModel body, Integer rIntModuloId, HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		isRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_MODIFICA_MODULO_A);
		
		validateRequiredPutModuloByRegione(body);
		
		Intervento intervento = interventoUtilityService.getInterventoById(body.getIntId());
		Integer enteId = intervento.getEnteId();
		Ente ente = enteUtilityService.getEnteByEnteId(enteId);
		
		RInterventoModulo rInterventoModulo = moduloUtilityService.getRInterventoModuloByIdAndEnteId(rIntModuloId, enteId);
		
		moduloUtilityService.checkStatusModuloByRInterventoModuloAndEnteId(rInterventoModulo, ModuloStatoEnum.PRESENTATO, enteId);
		
		Timestamp now = Timestamp.from(Instant.now());
		
		allegatoUtilityService.salvaAllegatiModuloARegione(body, userInfo, now, intervento, ente);
		
		return body;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Integer putModuloAInvia(Integer rIntModuloId, Integer interventoId, HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_INVIA_MODULO_A); 
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		moduloUtilityService.validateRequiredInviaModuloAARegione(intervento);
		
		Integer enteId = intervento.getEnteId();
		Ente ente = enteUtilityService.getEnteByEnteId(enteId);
		
		RInterventoModulo rInterventoModulo = moduloUtilityService.getRInterventoModuloByIdAndEnteId(rIntModuloId, enteId);
		
		checkCfRup(intervento.getIntRupCf(), userInfo.codiceFiscale()); //questa operazione è permessa solo ai rup dell'intervento stesso
		
		moduloUtilityService.checkStatusModuloByRInterventoModuloAndEnteId(rInterventoModulo, ModuloStatoEnum.INSERITO, enteId);
		
		Timestamp now = Timestamp.from(Instant.now());
		
		rInterventoModulo = moduloUtilityService.cambioStatoModuloByRInterventoModulo(rInterventoModulo, ModuloStatoEnum.PRESENTATO, 
				now, userInfo, ente);
		eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.ASR_INVIA_A_REGIONE_MODULO_A_AA, enteId, now, rInterventoModulo.getRIntModuloId(), userInfo.codiceFiscale());
		
		return interventoId;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Integer putModuloAApprova(Integer rIntModuloId, Integer interventoId, AllegatoLightExtDto moduloA, HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		isDirigenteRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_APPROVA_MODULO_A);
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		moduloUtilityService.validateRequiredApprovaModuloA(intervento);
		
		Integer enteId = intervento.getEnteId();
		Ente ente = enteUtilityService.getEnteByEnteId(enteId);
		
		RInterventoModulo rInterventoModulo = moduloUtilityService.getRInterventoModuloByIdAndEnteId(rIntModuloId, enteId);
		moduloUtilityService.checkStatusModuloByRInterventoModuloAndEnteId(rInterventoModulo, ModuloStatoEnum.PRESENTATO, enteId);
		
		Timestamp now = Timestamp.from(Instant.now());
		
		ModuloTipoEnum moduloTipo = moduloUtilityService.getModuloTipoEnumByRInterventoModulo(rInterventoModulo);
		
		moduloUtilityService.storicizzaModuloByRInterventoModulo(rInterventoModulo, now, userInfo.codiceFiscale()); 
		rInterventoModulo = moduloFileUtilityService.salvaModulo(moduloA, userInfo, now, intervento, ente, moduloTipo, ModuloStatoEnum.APPROVATO);
		
		interventoStatoUtilityService.salvaStatoInterventoAmmessoAlFinanziamento(userInfo, now, ente, interventoId);
		eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.REGIONE_APPROVA_MODULO_A_AA, enteId, now, rInterventoModulo.getRIntModuloId(), userInfo.codiceFiscale());
		
		return interventoId;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public Integer putModuloARespinge(RespingimentoDto respingimentoDto, Integer rIntModuloId, Integer interventoId, HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		isRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_RESPINGI_MODULO_A);
		
		validateRespingimentoDto(respingimentoDto);
		
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		Integer enteId = intervento.getEnteId();
		Ente ente = enteUtilityService.getEnteByEnteId(enteId);
		
		RInterventoModulo rInterventoModulo = moduloUtilityService.getRInterventoModuloByIdAndEnteId(rIntModuloId, enteId);
		moduloUtilityService.checkStatusModuloByRInterventoModuloAndEnteId(rInterventoModulo, ModuloStatoEnum.PRESENTATO, enteId);
		
		Timestamp now = Timestamp.from(Instant.now());
		
		rInterventoModulo = moduloUtilityService.cambioStatoModuloByRInterventoModulo(rInterventoModulo, ModuloStatoEnum.INSERITO, 
				now, userInfo, ente, respingimentoDto.getNote());
		eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.REGIONE_RESPINGE_MODULO_A_AA, enteId, now, rInterventoModulo.getRIntModuloId(), userInfo.codiceFiscale());
		
		return interventoId;
	}
	
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import static it.csi.pgmeas.commons.util.APISecurityFilterUtils.getUser;
import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfRegione;
import static it.csi.pgmeas.commons.util.ProfileUtils.hasFunctionality;
import static it.csi.pgmeas.commons.util.ProfileUtils.isAsr;
import static it.csi.pgmeas.commons.util.ProfileUtils.isRegione;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.checkCostoInterventoAndCostoStrutture;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.checkCostoInterventoAndQE;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkAnnoDuePrecedenti;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkAnnoIntervento;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkQueryParamGetIntervento;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkStatoIntervento;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.v2.InterventoBase;
import it.csi.pgmeas.commons.dto.v2.InterventoParereSaveExtDto;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaToSave;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToSaveModel;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoStato;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.repository.InterventoRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaRepository;
import it.csi.pgmeas.commons.service.ClassificazioneTreeUtilityService;
import it.csi.pgmeas.commons.service.CronoProgrammaUtilityService;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.service.FinanziamentoUtilityService;
import it.csi.pgmeas.commons.service.InterventoStatoUtilityService;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.commons.service.ModuloUtilityService;
import it.csi.pgmeas.commons.service.PrevisioniDiSpesaUtilityService;
import it.csi.pgmeas.commons.service.RInterventoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.AllegatoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.intervento.InterventoUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.pgmeasproject.service.InterventoV2Service;
import it.csi.pgmeas.pgmeasproject.util.service.AllegatoUtilityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class InterventoV2ServiceImpl implements InterventoV2Service {
	@Autowired
	private InterventoRepository interventoRepository;
	@Autowired
	private InterventoStrutturaRepository interventoStrutturaRepository;
	@Autowired
	private AllegatoUtilityService allegatoUtilityService;
	@Autowired
	private FinanziamentoUtilityService finanziamentoUtilityService;
	@Autowired
	private InterventoUtilityService interventoUtilityService;
	@Autowired
	private InterventoStatoUtilityService interventoStatoUtilityService;
	@Autowired
	private EnteUtilityService enteUtilityService;
	@Autowired
	private RInterventoUtilityService rInterventoUtilityService;
	@Autowired
	private PrevisioniDiSpesaUtilityService previsioniDiSpesaUtilityService;
	@Autowired
	private CronoProgrammaUtilityService cronoProgrammaUtilityService;
	@Autowired
	private ModuloUtilityService moduloUtilityService;
	@Autowired
	private ClassificazioneTreeUtilityService classificazioneTreeUtilityService;

	/*
	 * TODO staccare la validazione del model dal Flusso principale per snellire il
	 * codice codice TODO centralizzare service Allegati per scrittura file TODO
	 * GESTIRE i giorni sull'intervento struttura
	 */
	@Transactional(rollbackOn = Exception.class)
	@Override
	public InterventoToSaveModel postInterventoV2(InterventoToSaveModel body, HttpServletRequest httpRequest) throws PgmeasException, IOException
			 {
		// init utility
		UserInfoRecord userInfo = getUser(httpRequest);

		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_INSERISCI_INTERVENTO);
		
		// 18/12/2024 - Eliminazione requisito cup univoco
//		interventoUtilityService.checkUniqueCup(body.getIntCup(), 0);
		
		//TODO controllo periodo di raccolta dati della fase di programmazione è attivo occorre verificare che la data di 
//		sistema in cui l’utente è collegato sia compresa tra le date di inizio e fine fase o data proroga inizio e data proroga fine della tabella  pgmeas_r_ente_fase.
		
		Timestamp now = Timestamp.from(Instant.now());
		InterventoUtils.validateRequiredPostIntervento(body);
		// TODO Controlli integrita intervento
		// somma
		
		BigDecimal costoStrutture = body.getInterventoStrutturaList().stream()
				.map(InterventoStrutturaToSave::getIntStrImporto) 
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		checkCostoInterventoAndCostoStrutture(body.getIntImporto(), costoStrutture);
		
		List<Integer> qeNoTotList = classificazioneTreeUtilityService.getElementiQuadroEconomicoPerControllo();
		List<BigDecimal> qeStruttura = new ArrayList<BigDecimal>();
		
		body.getInterventoStrutturaList().forEach(intStr -> {
		    // Filtra la mappa rimuovendo le chiavi presenti in qeNoTotList
		    Map<Integer, BigDecimal> filteredMap = intStr.getQuadroEconMap().entrySet().stream()
		            .filter(entry -> qeNoTotList.contains(entry.getKey()))
		            .collect(Collectors.toMap(Map.Entry::getKey,  
		            		entry -> entry.getValue() != null ? entry.getValue() : BigDecimal.ZERO ));

		    // Aggiungi i valori della mappa filtrata alla lista qeStruttura
		    qeStruttura.addAll(filteredMap.values());
		});
		
		checkCostoInterventoAndQE(body.getIntImporto(), qeStruttura);

		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());
		// TODO R INTERVENTO TIPO CODICE ACQ_ATTR
		interventoUtilityService.checkInterventoTipo(body);

		Intervento intervento = makeInterventoFromInterventoToSaveModel(body, now, userInfo, ente, null);
		cronoProgrammaUtilityService.checkCronoProgramma(body.getInterventoStrutturaList(), intervento);
		var interventoSaved = interventoRepository.saveAndFlush(intervento);

		interventoStatoUtilityService.salvaStatoInterventoInserito(userInfo, now, ente, interventoSaved.getIntId());

		rInterventoUtilityService.salvaRInterventoObiettivo(body, userInfo, now, interventoSaved);
		rInterventoUtilityService.salvaRInterventoFinalita(body, userInfo, now, interventoSaved);
		rInterventoUtilityService.salvaRInterventoTipo(body, userInfo, now, interventoSaved);
		rInterventoUtilityService.salvaRInterventoCategoria(body, userInfo, now, interventoSaved);
		rInterventoUtilityService.salvaRInteventoContrattoTipo(body, userInfo, now, interventoSaved);
		rInterventoUtilityService.salvaRInteventoAppaltoTipo(body, userInfo, now, interventoSaved);
		rInterventoUtilityService.salvaRInterventoStatoProgettuale(body, userInfo, now, interventoSaved);

		// INTERVENTO STRUTTURA LIST
		interventoUtilityService.salvaListaInterventiStruttura(body, userInfo, now, ente, interventoSaved);

		allegatoUtilityService.salvaAllegato(body.getIntAllegatoDelibera(), userInfo.codiceFiscale(), now,
				interventoSaved, ente, AllegatoTipoEnum.DELIBERA_AZIENDALE_DI_APPROVAZIONE);
		moduloUtilityService.inserisciModuloFittizioByIntervento(intervento, ente, ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO,
				ModuloStatoEnum.INSERITO, userInfo, now);
		body.setIntId(interventoSaved.getIntId());
		body.setIntCodPgmeas(interventoSaved.getIntCod());
		body.setDataCreazione(interventoSaved.getDataCreazione());

		return body;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public InterventoToPutModel putInterventoV2(InterventoToPutModel body, Integer interventoId,
			HttpServletRequest httpRequest) throws PgmeasException, IOException  {
		// init utility
		UserInfoRecord userInfo = getUser(httpRequest);
		
		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_MODIFICA_INTERVENTO);
		
		// 18/12/2024 - Eliminazione requisito cup univoco
//		interventoUtilityService.checkUniqueCup(body.getIntCup(), interventoId);
		
		Timestamp now = Timestamp.from(Instant.now());
		InterventoUtils.validateRequiredPutIntervento(body);
		
		BigDecimal costoStrutture = body.getInterventoStrutturaList().stream()
				.map(InterventoStrutturaToSave::getIntStrImporto) 
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		checkCostoInterventoAndCostoStrutture(body.getIntImporto(), costoStrutture);
		
		List<Integer> qeNoTotList = classificazioneTreeUtilityService.getElementiQuadroEconomicoPerControllo();
		List<BigDecimal> qeStruttura = new ArrayList<BigDecimal>();
		
		body.getInterventoStrutturaList().forEach(intStr -> {
		    // Filtra la mappa rimuovendo le chiavi presenti in qeNoTotList
		    Map<Integer, BigDecimal> filteredMap = intStr.getQuadroEconMap().entrySet().stream()
		            .filter(entry -> qeNoTotList.contains(entry.getKey()))
		            .collect(Collectors.toMap(Map.Entry::getKey,  
		            		entry -> entry.getValue() != null ? entry.getValue() : BigDecimal.ZERO ));

		    // Aggiungi i valori della mappa filtrata alla lista qeStruttura
		    qeStruttura.addAll(filteredMap.values());
		});
		
		checkCostoInterventoAndQE(body.getIntImporto(), qeStruttura);
		
		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());
		Integer enteId = ente.getEnteId();
		
		// confronto dati intervento se diversi => storico vecchio => salvo nuovo
//		Interventoid ==body.getIntId()
		// TODO R INTERVENTO TIPO CODICE ACQ_ATTR
		interventoUtilityService.checkInterventoTipo(body);
		Intervento interventoSaved = interventoUtilityService.getInterventoById(interventoId);
		// CODICE UTENTE CREAZIONE DATA CREAZIONE DAL VECCHIO
		Intervento intervento = makeInterventoFromInterventoToSaveModel(body, now, userInfo, ente, interventoSaved);
		cronoProgrammaUtilityService.checkCronoProgramma(body.getInterventoStrutturaList(), intervento);
		if (InterventoUtils.isInterventoDifferent(interventoSaved, intervento)) {
			interventoUtilityService.storicizzaIntervento(userInfo, now, interventoSaved);
			interventoSaved = interventoRepository.saveAndFlush(intervento);
		}

		rInterventoUtilityService.storicizzaRInterventoObiettivo(interventoId, userInfo, now, enteId);
		rInterventoUtilityService.salvaRInterventoObiettivo(body, userInfo, now, interventoSaved);

		rInterventoUtilityService.storicizzaRInterventoFinalita(interventoId, userInfo, now, enteId);
		rInterventoUtilityService.salvaRInterventoFinalita(body, userInfo, now, interventoSaved);

		rInterventoUtilityService.storicizzaRInterventoTIpo(interventoId, userInfo, now, enteId);
		rInterventoUtilityService.salvaRInterventoTipo(body, userInfo, now, interventoSaved);

		rInterventoUtilityService.storicizzaRInterventoCategoria(interventoId, userInfo, now, enteId);
		rInterventoUtilityService.salvaRInterventoCategoria(body, userInfo, now, interventoSaved);

		rInterventoUtilityService.storicizzaRInterventoContrattoTipo(interventoId, userInfo, now, enteId);
		rInterventoUtilityService.salvaRInteventoContrattoTipo(body, userInfo, now, interventoSaved);

		rInterventoUtilityService.storicizzaRInterventoAppaltoTipo(interventoId, userInfo, now, enteId);
		rInterventoUtilityService.salvaRInteventoAppaltoTipo(body, userInfo, now, interventoSaved);

		rInterventoUtilityService.storicizzaRInterventoStatoProgettuale(interventoId, userInfo, now, enteId);
		rInterventoUtilityService.salvaRInterventoStatoProgettuale(body, userInfo, now, interventoSaved);

		interventoUtilityService.storicizzaListaInterventiStruttura(interventoId, userInfo, now, enteId);
		interventoUtilityService.salvaListaInterventiStruttura(body, userInfo, now, ente, interventoSaved);

		// TODO CONTROLLO SE DELETE é POPOLATA E NEW NON POPOLATA ERRORE
		if(body.getIntAllegatoDeliberaToDelete() != null) {
			allegatoUtilityService.storicizzaAllegato(body.getIntAllegatoDeliberaToDelete(), now,
					userInfo.codiceFiscale(), ente);
		} 
		if(body.getIntAllegatoDeliberaNew() != null) {
			allegatoUtilityService.salvaAllegato(body.getIntAllegatoDeliberaNew(), userInfo.codiceFiscale(), now,
					interventoSaved, ente, AllegatoTipoEnum.DELIBERA_AZIENDALE_DI_APPROVAZIONE);
		}
		RInterventoModulo rInterventoModuloOld=moduloUtilityService.storicizzaModuloByInterventoIdAndEnteId(intervento.getIntId(), ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO, now,
				userInfo.codiceFiscale(), ente.getEnteId());
		moduloUtilityService.inserisciModuloFittizioByRInterventoModuloOld( intervento,  ente, ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO,
				ModuloStatoEnum.INSERITO,userInfo,  now, rInterventoModuloOld);
		body.setIntId(interventoSaved.getIntId());
		body.setIntCodPgmeas(interventoSaved.getIntCod());
		body.setDataCreazione(interventoSaved.getDataCreazione());

		return body;
	}

	private Intervento makeInterventoFromInterventoToSaveModel(InterventoBase body, Timestamp now,
			UserInfoRecord userInfo, Ente ente, Intervento old) {

		String codicePgmeas = calcolaCod(ente, body.getIntAnno());
		return InterventoUtils.buildInterventoFromInterventoToSaveModel(body, now, userInfo, ente, codicePgmeas, old);
	}

	/**
	 * Campo costruito dal sistema dopo il salvataggio dei dati come segue:
	 * 010<codice ASR>_<anno di inserimento>_<progressivo all’interno dell’anno
	 * fillato con zeri di 6 cifre> Ad esempio: 010301_2024_000001
	 * 
	 * @param intEnteId
	 * @param intAnno
	 * @return
	 */
	private String calcolaCod(Ente ente, Integer intAnno) {
		int count = interventoRepository.countByIntAnnoAndEnteId(intAnno, ente.getEnteId());
		String countFormatted = String.format("%06d", count);
		StringBuffer sb = new StringBuffer();
		sb.append("010").append(ente.getEnteCod()).append("_").append(intAnno).append("_").append(countFormatted);
		return sb.toString();
	}
	
	public List<InterventoV2GetDto> getInterventiByFilters(HttpServletRequest httpRequest, Integer anno, String codice,
			String titolo, String cup) throws PgmeasException, ExecutionException  {

		UserInfoRecord userInfo = getUser(httpRequest);

		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_INSERISCI_INTERVENTO); //perché relativo al cdu 15
		
		checkQueryParamGetIntervento(anno, codice, titolo, cup);
		checkAnnoDuePrecedenti("intAnno", anno);
		
		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());
		Integer enteId = ente.getEnteId();

		List<String> stati = new ArrayList<>();
		stati.add(InterventoStatoEnum.INSERITO.getCode());
		stati.add(InterventoStatoEnum.PROPOSTO.getCode());
		
		List<Intervento> interventi = null;
		if(checkIfRegione(userInfo)) {
			 interventi = interventoRepository.findAllSenzaCancellatiByFiltriAndStati(anno, codice, titolo,
				cup, stati, Year.now().getValue());
		} else {
			 interventi = interventoRepository.findAllSenzaCancellatiByFiltriAndStatiAndEnteId(anno, codice, titolo, cup, stati, enteId, Year.now().getValue());
		}

		List<InterventoV2GetDto> interventiDto = new ArrayList<InterventoV2GetDto>();
		for (Intervento intervento : interventi) {
			InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(intervento.getIntId());
			InterventoV2GetDto interventoDto = interventoUtilityService.getInterventoDto(intervento, intervento.getEnteId(), userInfo, stato.getIntStatoCod());
			interventoDto.setStatoNota(null); // Per il copia non deve essere passata la nota
			interventiDto.add(interventoDto);
		}

		return interventiDto;
	}

	@Override
	public List<InterventoStrutturaV2GetDto> getInterventiStrutturaByIntId(HttpServletRequest httpRequest,
			Integer intId, boolean copy) throws PgmeasException  {
//		UserInfoRecord userInfo = getUser(httpRequest);
		
		//TODO hasFunctionality(userInfo, FunzionalitaEnum.OP_VISUALIZZA_INTERVENTO);
		
		Intervento intervento = interventoUtilityService.getInterventoById(intId);
		Integer enteId = intervento.getEnteId();
		
//		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());
//		Integer enteId = ente.getEnteId();

		return interventoUtilityService.getInterventiStruttura(intId, enteId, copy);
	}

	@Override
	public InterventoV2GetDto getIntervento(HttpServletRequest httpRequest, Integer interventoId)
			throws PgmeasException, ExecutionException {
		UserInfoRecord userInfo = getUser(httpRequest);

		hasFunctionality(userInfo, FunzionalitaEnum.OP_CONSULTA_INTERVENTO);
		
//		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());
//		Integer enteId = ente.getEnteId();

		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		Integer enteId = intervento.getEnteId();
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(intervento.getIntId());
		InterventoV2GetDto interventoDto = interventoUtilityService.getInterventoDto(intervento, enteId, userInfo, stato.getIntStatoCod());

		return interventoDto;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public InterventoToPutByRegioneModel putInterventoV2ByRegione(InterventoToPutByRegioneModel body,
			Integer interventoId, HttpServletRequest httpRequest) throws PgmeasException, IOException {
		UserInfoRecord userInfo = getUser(httpRequest);
		
		isRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_MODIFICA_INTERVENTO);
		
		Intervento interventoSaved = interventoUtilityService.getInterventoById(interventoId);
		checkAnnoIntervento("intAnno", interventoSaved.getIntAnno());
		
		//CONTROLLI COMMENTATI SPOSTATI IN APPROVAZIONE
//		validateRequiredPutInterventoByRegione(body, interventoSaved.getIntImporto());
		
		InterventoStato stato = interventoStatoUtilityService.getInterventoStatoByInterventoId(interventoId);
		checkStatoIntervento(stato, InterventoStatoEnum.PROPOSTO);
		
		Ente ente = enteUtilityService.getEnteByEnteId(interventoSaved.getEnteId());
		Integer enteId = ente.getEnteId();
		
//		BigDecimal totalePianoFinanziario = body.getPianiFinanziari().stream()
//			.map(PianoFinanziarioSaveDto::getImportoTotale) 
//			.reduce(BigDecimal.ZERO, BigDecimal::add);
//		
//		checkCostoInterventoAndPianoFinanziario(interventoSaved.getIntImporto(), totalePianoFinanziario);
		
		Timestamp now = Timestamp.from(Instant.now());
		
		if (interventoSaved.getIntFinanziabile() == null || 
				(body.getFinanziabile() != null && interventoSaved.getIntFinanziabile() != null 
				&& !interventoSaved.getIntFinanziabile().equals(body.getFinanziabile()))) {
			interventoUtilityService.storicizzaIntervento(userInfo, now, interventoSaved);

			interventoSaved.setIntFinanziabile(body.getFinanziabile());
			interventoSaved = interventoRepository.saveAndFlush(interventoSaved);
		}

		// salva parere
		salvaPareri(body, userInfo, now, interventoSaved, ente);

		// salva prev spesa
		previsioniDiSpesaUtilityService.storicizzaPrevisioneSpesa(interventoId, userInfo, now, enteId);
		previsioniDiSpesaUtilityService.salvaPrevisioneSpesa(body, userInfo, now, interventoSaved);

		// salva finanziamento
		finanziamentoUtilityService.storicizzaFinanziamento(interventoId, ente.getEnteId(), userInfo, now,
				body.getPianiFinanziari());
		finanziamentoUtilityService.salvaFinanziamento(body.getPianiFinanziari(), userInfo, now, interventoSaved);

		// salva allegati
		allegatoUtilityService.salvaAllegatiRegione(body, userInfo, now, interventoSaved, ente);
		
		RInterventoModulo rInterventoModuloOld=moduloUtilityService.storicizzaModuloByInterventoIdAndEnteId(interventoSaved.getIntId(), ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO, now,
				userInfo.codiceFiscale(), ente.getEnteId());
		moduloUtilityService.inserisciModuloFittizioByRInterventoModuloOld( interventoSaved,  ente, ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO,
				ModuloStatoEnum.INSERITO,userInfo,  now, rInterventoModuloOld);
		return body; // TODO ha senso?
	}

	private void salvaPareri(InterventoToPutByRegioneModel body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved, Ente ente) throws PgmeasException, IOException {
		for (InterventoParereSaveExtDto parere : body.getPareri()) {
			InterventoStruttura interventoStrutturaSaved = interventoUtilityService
					.getInterventoStrutturaByIdAndEnteId(parere.intStrId(), ente.getEnteId());

			if (!parere.parerePpp().parere().equals(interventoStrutturaSaved.getIntstrParerePpp())
					|| !parere.parereHta().parere().equals(interventoStrutturaSaved.getIntstrParereHta())) {
				interventoUtilityService.storicizzaInterventoStruttura(interventoStrutturaSaved, now,
						userInfo.codiceFiscale());

				interventoStrutturaSaved.setIntstrParerePpp(parere.parerePpp().parere());
				interventoStrutturaSaved.setIntstrParereHta(parere.parereHta().parere());
				interventoStrutturaSaved = interventoStrutturaRepository.saveAndFlush(interventoStrutturaSaved);
			}

			allegatoUtilityService.salvaAllegatiPareriRegione(userInfo, now, interventoSaved, ente, parere,
					interventoStrutturaSaved);
		}
	}

}

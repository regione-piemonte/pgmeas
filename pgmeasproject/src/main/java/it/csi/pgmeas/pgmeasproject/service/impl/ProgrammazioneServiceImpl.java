/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import static it.csi.pgmeas.commons.util.APISecurityFilterUtils.getUser;
import static it.csi.pgmeas.commons.util.ProfileUtils.hasFunctionality;
import static it.csi.pgmeas.commons.util.ProfileUtils.isRegione;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkEntityIsPresentByProperty;
import static it.csi.pgmeas.commons.validation.ValidationUtils.generatePgmeasException;
import static it.csi.pgmeas.commons.validation.ValidationUtils.validaInserisciProgrammazione;
import static it.csi.pgmeas.commons.validation.ValidationUtils.validateEnteProrogaDto;
import static it.csi.pgmeas.commons.validation.ValidationUtils.validateProgrammazioneProrogaDto;
import static it.csi.pgmeas.commons.validation.ValidationUtils.validateProrogaProgrammazioneFasi;
import static it.csi.pgmeas.pgmeasproject.util.service.ProgrammazioneUtilityService.buildProgrammazione;
import static it.csi.pgmeas.pgmeasproject.util.service.ProgrammazioneUtilityService.date;
import static it.csi.pgmeas.pgmeasproject.util.service.ProgrammazioneUtilityService.localDateTime;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.EnteProrogaDto;
import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.ProgrammazioneBaseDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneProrogaDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.EnteFase;
import it.csi.pgmeas.commons.model.Fase;
import it.csi.pgmeas.commons.repository.EnteFaseRepository;
import it.csi.pgmeas.commons.repository.EnteRepository;
import it.csi.pgmeas.commons.repository.FaseRepository;
import it.csi.pgmeas.commons.service.EventoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ErroreEnum;
import it.csi.pgmeas.commons.util.enumeration.EventoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.FaseTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.commons.util.record.InfoProgrammazione;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.pgmeasproject.service.ProgrammazioneService;
import it.csi.pgmeas.pgmeasproject.util.service.ProgrammazioneUtilityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class ProgrammazioneServiceImpl implements ProgrammazioneService {
	private static final Logger LOG = LoggerFactory.getLogger(ProgrammazioneServiceImpl.class);

	@Autowired
	EnteRepository enteRepository;

	@Autowired
	EnteFaseRepository enteFaseRepository;

	@Autowired
	FaseRepository faseRepository;
	
	@Autowired
	EventoUtilityService eventoUtilityService;
	

	/**
	 * Recupera le informazioni di programmazione per un dato ente.
	 * 
	 * @param enteCod codice dell'ente
	 * @return risposta HTTP contenente le informazioni di programmazione o 404 se
	 *         non trovato
	 */
	@Override
	public InfoProgrammazione getInfoProgrammazioneForUser(String enteCod) {
		Optional<EnteFase> progr = enteFaseRepository.findByEnteCodAndFaseAndAnno(enteCod, FaseTipoEnum.PROGRAMMAZIONE.getCode(),
				LocalDate.now().getYear());
		if (progr.isPresent()) {
			InfoProgrammazione data = MappingUtils.getFrom(progr.get());
			return data;
		} else {
			// Se non presente, restituisce 404 Not Found
			return null;
		}
	}

	/**
	 * Recupera un elenco di programmazioni.
	 * 
	 * @param httpHeaders intestazioni HTTP
	 * @return risposta HTTP contenente l'elenco delle programmazioni o 404 se non
	 *         trovato
	 * @throws Exception in caso di errore durante il recupero
	 */
	@Override
	public List<ProgrammazioneBaseDto> getElencoProgrammazioni() throws Exception {
		List<ProgrammazioneBaseDto> data = new ArrayList<ProgrammazioneBaseDto>();
		List<EnteFase> progr = enteFaseRepository.findByFase(FaseTipoEnum.PROGRAMMAZIONE.getCode());
		if (progr.size() > 0) {
			data = progr.stream()
					.map(ef -> new ProgrammazioneBaseDto(ef.getFaseInizio().getYear(), 
							date(ef.getFaseInizio()), 
							date(ef.getFaseFine())))
					.distinct() 
					.collect(Collectors.toList());
		}

		return data;
	}

	/**
	 * Recupera la programmazione per un anno specifico.
	 * 
	 * @param anno anno per cui recuperare la programmazione
	 * @return risposta HTTP contenente la programmazione o 404 se non trovato
	 */
	@Transactional
	@Override
	public ProgrammazioneDto getProgrammazione(String anno) {
		ProgrammazioneDto data = new ProgrammazioneDto();
		List<EnteFase> progr = enteFaseRepository.findByFaseAndAnno(FaseTipoEnum.PROGRAMMAZIONE.getCode(), Integer.parseInt(anno));
		if (progr.size() > 0) {
			data = buildProgrammazione(Integer.parseInt(anno), progr);
		}

		return data;
	}

	/**
	 * Inserisce una nuova programmazione.
	 * 
	 * @param request     dati della programmazione da inserire
	 * @param httpHeaders intestazioni HTTP
	 * @return risposta HTTP con il risultato dell'inserimento
	 * @throws Exception in caso di errore durante l'inserimento
	 */
	@Transactional
	@Override
	public ProgrammazioneDto insertProgrammazione(ProgrammazioneBaseDto request, HttpServletRequest httpRequest) throws Exception {
		UserInfoRecord userInfo = getUser(httpRequest);
		isRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_INSERISCI_PROGRAMMAZIONE);
		
		validaInserisciProgrammazione(request);

		LOG.info("[ProgrammazioneServiceImpl::insertProgrammazione] " + request.toString());
		
		String faseCod = FaseTipoEnum.PROGRAMMAZIONE.getCode();
		Long count = enteFaseRepository.countByFaseAndAnnoWithProroga(faseCod, 
				request.getAnno()).orElse(Long.valueOf(0));

		if (count.longValue() > 0) {
			LOG.info("[ProgrammazioneServiceImpl::insertProgrammazione] son già presenti proroghe");
			List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
			generatePgmeasException(HttpStatus.BAD_REQUEST, ErroreEnum.MSG_094.getCode() + ": Attenzione! Non è possibile modificare le date di programmazione perché esistono già delle proroghe.",
					listaErroriRilevati, ErroreEnum.MSG_094.getCode() + ": Attenzione! Non è possibile modificare le date di programmazione perché esistono già delle proroghe.");
		}

		enteFaseRepository.updateValiditaFineByFaseAndAnno(faseCod, request.getAnno(), userInfo.codiceFiscale());

		List<Ente> enti = enteRepository.findAllValid();
		Fase fase = faseRepository.findByFaseCod(faseCod).orElseThrow(PgmeasNoDataFoundException::new);
		Timestamp now = Timestamp.from(Instant.now());
		
		enti.forEach(e -> {
			EnteFase nuovoEnteFase = new EnteFase();
			nuovoEnteFase.setFase(fase);
			nuovoEnteFase.setFaseId(fase.getFaseId());
			nuovoEnteFase.setEnte(e);
			nuovoEnteFase.setEnteId(e.getEnteId());

			nuovoEnteFase.setValiditaInizio(now);

			nuovoEnteFase.setFaseInizio(localDateTime(request.getFaseInizio()));
			nuovoEnteFase.setFaseFine(localDateTime(request.getFaseFine()));

			nuovoEnteFase.setUtenteCreazione(userInfo.codiceFiscale());
			nuovoEnteFase.setUtenteModifica(userInfo.codiceFiscale());

			nuovoEnteFase.setDataCreazione(now);
			nuovoEnteFase.setDataModifica(now);

			nuovoEnteFase=enteFaseRepository.saveAndFlush(nuovoEnteFase);
			eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.REGIONE_DEFINISCE_PERIODO_PROGRAMMAZIONE, e.getEnteId(), now, nuovoEnteFase.getREnteFaseId(), userInfo.codiceFiscale());
		});

		ProgrammazioneDto programmazione = getProgrammazione(request.getAnno().toString());
		
		return programmazione;
	}

	/**
	 * Recupera la proroga per un ente specifico.
	 * 
	 * @param anno    anno della proroga
	 * @param enteCod codice dell'ente
	 * @return risposta HTTP contenente la proroga o 404 se non trovata
	 */
	@Transactional
	@Override
	public EnteProrogaDto getProrogaEnte(String anno, String enteCod) {
		EnteProrogaDto ente = new EnteProrogaDto();
		Optional<EnteFase> progr = enteFaseRepository.findByEnteCodAndFaseAndAnno(enteCod, FaseTipoEnum.PROGRAMMAZIONE.getCode(), Integer.parseInt(anno));
		if (progr.isPresent()) {
			ente = progr.stream()
					.map(ef -> new EnteProrogaDto(ef.getEnteId(), ef.getEnte().getEnteCodEsteso(),
							ef.getEnte().getEnteDesc(), date(ef.getFaseProrogaInizio()), date(ef.getFaseProrogaFine())))
					.distinct() // Filtra i duplicati
					.findFirst() // Restituisce il primo elemento come Optional
					.orElse(null); // Se non ci sono elementi, restituisce null
		}
		
		return ente;
	}

	/**
	 * Aggiorna la proroga per un ente specifico.
	 * 
	 * @param anno    anno della proroga
	 * @param enteCod codice dell'ente
	 * @param request dati della proroga da aggiornare
	 * @return risposta HTTP con il risultato dell'aggiornamento
	 * @throws Exception in caso di errore durante l'aggiornamento
	 */
	@Transactional
	@Override
	public EnteProrogaDto insertProroga(EnteProrogaDto request, HttpServletRequest httpRequest, String anno, String enteCod) throws Exception {
		
		LOG.info("[ProgrammazioneServiceImpl::insertProroga] " + request.toString());
		UserInfoRecord userInfo = getUser(httpRequest);
		isRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_PROROGA_FINE_PROGRAMMAZIONE);
		
		validateEnteProrogaDto(request,anno);

		EnteFase progr = getEnteFaseByEnteCodAndAnno(anno, enteCod);
		
		validateProrogaProgrammazione(progr,request);
		
		EnteFase enteFase = insertProrogaEnte(progr, request.getFaseProrogaInizio(), request.getFaseProrogaFine(),userInfo.codiceFiscale());
		eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.REGIONE_DEFINISCE_PROROGA, enteFase.getEnteId(), enteFase.getDataCreazione(), enteFase.getREnteFaseId(), userInfo.codiceFiscale());
		
		return getProrogaEnte(anno, enteCod);
		
	}

	private void validateProrogaProgrammazione(EnteFase progr, EnteProrogaDto request) throws PgmeasException {
		Date dataInizioProgr= ProgrammazioneUtilityService.date(progr.getFaseInizio());
		Date dataFineProgr= ProgrammazioneUtilityService.date(progr.getFaseFine());
		validateProrogaProgrammazioneFasi(dataInizioProgr,dataFineProgr,request);
	}
	

	private EnteFase getEnteFaseByEnteCodAndAnno(String anno, String enteCod) throws PgmeasException {
		Optional<EnteFase> progr = enteFaseRepository.findByEnteCodAndFaseAndAnno(enteCod, FaseTipoEnum.PROGRAMMAZIONE.getCode(), Integer.parseInt(anno));
		checkEntityIsPresentByProperty(progr, enteCod, ValidationNameEnum.R_ENTE_FASE_BY_ENTE);
		return progr.orElseThrow(PgmeasNoDataFoundException::new);
	}

	@Override
	public void deleteProgrammazione(ProgrammazioneProrogaDto request, String anno)
			throws Exception {
		enteFaseRepository.updateDataCancellazioneByFaseAndAnno(FaseTipoEnum.PROGRAMMAZIONE.getCode(), request.getAnno());
	}
	
	@Transactional
	private EnteFase insertProrogaEnte(EnteFase oldEnteFase, Date faseProrogaInizio, Date faseProrogaFine,String cfUtente) {
		EnteFase nuovoEnteFase = new EnteFase();
		MappingUtils.copy(oldEnteFase, nuovoEnteFase);
		Timestamp now = Timestamp.from(Instant.now());
		
		oldEnteFase.setValiditaFine(now);
		oldEnteFase.setUtenteModifica(cfUtente);
		enteFaseRepository.save(oldEnteFase);

		nuovoEnteFase.setREnteFaseId(null);

		nuovoEnteFase.setFaseProrogaInizio(localDateTime(faseProrogaInizio));
		nuovoEnteFase.setFaseProrogaFine(localDateTime(faseProrogaFine));
		nuovoEnteFase.setUtenteCreazione(cfUtente);
		nuovoEnteFase.setUtenteModifica(cfUtente);

		nuovoEnteFase.setDataCreazione(now);
		nuovoEnteFase.setDataModifica(now);
		
		return enteFaseRepository.saveAndFlush(nuovoEnteFase);
	}

	@Transactional
	@Override
	public ProgrammazioneDto inserisciProrogaTuttiEnti(ProgrammazioneProrogaDto request, HttpServletRequest httpRequest, String anno)
			throws Exception {
		LOG.info("[ProgrammazioneServiceImpl::inserisciProrogaTuttiEnti] " + request.toString());

		UserInfoRecord userInfo = getUser(httpRequest);
		isRegione(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_PROROGA_FINE_PROGRAMMAZIONE);
		
		validateProgrammazioneProrogaDto(request);
		List<EnteFase> progr = enteFaseRepository.findByFaseAndAnno(FaseTipoEnum.PROGRAMMAZIONE.getCode(), Integer.parseInt(anno));
			
		if (progr.size() > 0) {
			progr.forEach(e ->{
				 EnteFase enteFase = insertProrogaEnte(e, request.getFaseProrogaInizio(), request.getFaseProrogaFine(), userInfo.codiceFiscale());
				 eventoUtilityService.inserisciEventoNotifica(EventoTipoEnum.REGIONE_DEFINISCE_PROROGA, enteFase.getEnteId(), enteFase.getDataCreazione(), enteFase.getREnteFaseId(), userInfo.codiceFiscale());	
			});
		}
		
		return getProgrammazione(anno);
	}
}

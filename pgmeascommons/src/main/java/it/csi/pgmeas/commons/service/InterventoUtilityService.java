/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import static it.csi.pgmeas.commons.util.allegato.AllegatoUtility.checkAllegatoObbligatorio;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.buildPianoFinanziario;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.buildPrevisioniSpesa;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.checkCostoInterventoAndPianoFinanziario;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkEntityIsPresentByProperty;
import static it.csi.pgmeas.commons.validation.ValidationUtils.listNullOrEmptyValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.v2.FinanziamentoImportoTipoDto;
import it.csi.pgmeas.commons.dto.v2.InterventoBase;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaToSave;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.dto.v2.PianoFinanziarioDto;
import it.csi.pgmeas.commons.dto.v2.PrevisioneSpesaDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Allegato;
import it.csi.pgmeas.commons.model.AllegatoTipo;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoPrevisioneSpesa;
import it.csi.pgmeas.commons.model.InterventoStato;
import it.csi.pgmeas.commons.model.InterventoStorico;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.model.InterventoStrutturaStorico;
import it.csi.pgmeas.commons.model.InterventoTipo;
import it.csi.pgmeas.commons.model.REnteQuadrante;
import it.csi.pgmeas.commons.model.RInterventoAppaltoTipo;
import it.csi.pgmeas.commons.model.RInterventoCategoria;
import it.csi.pgmeas.commons.model.RInterventoContrattoTipo;
import it.csi.pgmeas.commons.model.RInterventoFinalita;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.model.RInterventoObiettivo;
import it.csi.pgmeas.commons.model.RInterventoStato;
import it.csi.pgmeas.commons.model.RInterventoStatoProgettuale;
import it.csi.pgmeas.commons.model.RInterventoTipo;
import it.csi.pgmeas.commons.repository.InterventoPrevisioneSpesaRepository;
import it.csi.pgmeas.commons.repository.InterventoRepository;
import it.csi.pgmeas.commons.repository.InterventoStoricoRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaStoricoRepository;
import it.csi.pgmeas.commons.repository.REnteQuadranteRepository;
import it.csi.pgmeas.commons.repository.RInterventoAppaltoTipoRepository;
import it.csi.pgmeas.commons.repository.RInterventoCategoriaRepository;
import it.csi.pgmeas.commons.repository.RInterventoContrattoTipoRepository;
import it.csi.pgmeas.commons.repository.RInterventoFinalitaRepository;
import it.csi.pgmeas.commons.repository.RInterventoObiettivoRepository;
import it.csi.pgmeas.commons.repository.RInterventoStatoProgettualeRepository;
import it.csi.pgmeas.commons.repository.RInterventoTipoRepository;
import it.csi.pgmeas.commons.util.allegato.AllegatoUtility;
import it.csi.pgmeas.commons.util.enumeration.AllegatoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.ErroreEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoRequiredErrorEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.commons.util.intervento.InterventoUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class InterventoUtilityService {

	@Autowired
	private InterventoRepository interventoRepository;
	@Autowired
	private InterventoStrutturaRepository interventoStrutturaRepository;
	@Autowired
	private InterventoStatoUtilityService interventoStatoUtilityService;
	@Autowired
	private InterventoStoricoRepository interventoStoricoRepository;
	@Autowired
	private InterventoStrutturaStoricoRepository interventoStrutturaStoricoRepository;
	@Autowired
	private InterventoTipoUtilityService interventoTipoServiceUtility;
	@Autowired
	private REnteQuadranteRepository rEnteQuadranteRepository;
	@Autowired
	private RInterventoFinalitaRepository rInterventoFinalitaRepository;
	@Autowired
	private RInterventoObiettivoRepository rInterventoObiettivoRepository;
	@Autowired
	private RInterventoCategoriaRepository rInterventoCategoriaRepository;
	@Autowired
	private RInterventoStatoProgettualeRepository rInterventoStatoProgRepository;
	@Autowired
	private RInterventoTipoRepository rInterventoTipoRepository;
	@Autowired
	private RInterventoAppaltoTipoRepository rInterventoAppTipoRepository;
	@Autowired
	private RInterventoContrattoTipoRepository rInterventoContTipoRepository;
	@Autowired
	private FinanziamentoUtilityService finanziamentoUtilityService;
	@Autowired
	private InterventoPrevisioneSpesaRepository interventoPrevisioneSpesaRepository;
	@Autowired
	private AllegatoUtility allegatoUtility;
	@Autowired
	private AllegatoTipoUtilityService allegatoTipoUtilityService;
	@Autowired
	private InterventoEdilizioUtilityService interventoEdilizioUtilityService;
	@Autowired
	private QuadroEconomicoUtilityService quadroEconomicoUtilityService;
	@Autowired
	private ModuloUtilityService moduloUtilityService;

	public void checkUniqueCup(String intCup, Integer intId) throws PgmeasException {
		if (intCup == null) {
			return;
		}
		Optional<Integer> intIdDb = interventoRepository.getIntIdByIntCup(intCup);
		if (intIdDb.isPresent() && !intIdDb.get().equals(intId)) {
			List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
			listaErroriRilevati.add(new ErroreDettaglio(ErroreEnum.MSG_070.getCode(),
					"Attenzione! Non è possibile inserire due interventi con lo stesso cup"));
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD",
					listaErroriRilevati,
					"Cup non univoco");
		}
	}
	
	public Intervento getInterventoById(Integer interventoId) throws PgmeasException {
		Optional<Intervento> intervento = interventoRepository.findNonCancellatoById(interventoId);
		checkEntityIsPresentByProperty(intervento, interventoId.toString(), ValidationNameEnum.INTERVENTO_ID);
		return intervento.orElseThrow(PgmeasNoDataFoundException::new);
	}

	public InterventoStruttura getInterventoStrutturaByIdAndEnteId(Integer interventoStrutturaId, Integer enteId) throws PgmeasException {
		Optional<InterventoStruttura> interventoStruttura = interventoStrutturaRepository
				.findNonCancellatiByIntStrIdAndEnteId(interventoStrutturaId, enteId);
		checkEntityIsPresentByProperty(interventoStruttura, interventoStrutturaId.toString(),
				ValidationNameEnum.INTERVENTO_STRUTTURA_ID);
		return interventoStruttura.orElseThrow(PgmeasNoDataFoundException::new);
	}

	public InterventoStato getStatoByInterventoId(Integer interventoId) throws Exception {
		InterventoStato interventoStato = interventoStatoUtilityService.getInterventoStatoByInterventoId(interventoId);
		return interventoStato;
	}

	public void storicizzaIntervento(UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		InterventoStorico interventoStorico = InterventoUtils.buildInterventoStorico(interventoSaved, now,
				userInfo.codiceFiscale());
		interventoStoricoRepository.saveAndFlush(interventoStorico);
	}

	public Map<String, Object> findAnnoAndCodiceByIntIdAndEnteId(Integer interventoId, Integer enteId) {
		Map<String, Object> intervento = interventoRepository.findAnnoAndCodiceByIntIdAndEnteId(interventoId, enteId);
		return intervento;
	}

	public void storicizzaInterventoStruttura(InterventoStruttura intStr, Timestamp now, String codiceFiscale) {
		InterventoStrutturaStorico interventoStrutturaStorico = InterventoUtils
				.buildInterventoStrutturaStoricoFromInterventoStruttura(intStr, now, codiceFiscale);
		interventoStrutturaStoricoRepository.save(interventoStrutturaStorico);
	}

	public InterventoV2GetDto getInterventoDto(Intervento intervento, Integer enteId, UserInfoRecord userInfo,
			String statoIntervento) throws PgmeasException {
		InterventoTipo interventoTipo = interventoTipoServiceUtility
				.getInterventoTipoValidoByInterventoTipoCod(InterventoTipoEnum.ACQ_ATTR);

		RInterventoStato stato = interventoStatoUtilityService.getRInterventoStatoCurrent(intervento.getIntId(), enteId);

		CompletableFuture<Optional<REnteQuadrante>> enteQuadranteFuture = CompletableFuture
				.supplyAsync(() -> rEnteQuadranteRepository.findValidByEnteId(enteId));

		CompletableFuture<List<RInterventoObiettivo>> obiettiviFuture = CompletableFuture
				.supplyAsync(() -> rInterventoObiettivoRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<RInterventoFinalita>> finalitaFuture = CompletableFuture
				.supplyAsync(() -> rInterventoFinalitaRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<RInterventoTipo>> tipiFuture = CompletableFuture
				.supplyAsync(() -> rInterventoTipoRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<RInterventoTipo>> descrizioniAttrezzatureFuture = CompletableFuture
				.supplyAsync(() -> rInterventoTipoRepository.findValidByIntIdAndIntTipoIdAndEnteId(intervento.getIntId(),
						interventoTipo.getIntTipoId(), enteId));

		CompletableFuture<List<RInterventoCategoria>> categorieFuture = CompletableFuture
				.supplyAsync(() -> rInterventoCategoriaRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<RInterventoContrattoTipo>> contrattiTipoFuture = CompletableFuture
				.supplyAsync(() -> rInterventoContTipoRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<RInterventoAppaltoTipo>> appaltiTipoFuture = CompletableFuture
				.supplyAsync(() -> rInterventoAppTipoRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<RInterventoStatoProgettuale>> statiProgettualiFuture = CompletableFuture
				.supplyAsync(() -> rInterventoStatoProgRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<InterventoStruttura>> interventiStrutturaFuture = CompletableFuture
				.supplyAsync(() -> interventoStrutturaRepository.findAllSenzaCancellatiByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<InterventoPrevisioneSpesa>> interventoPrevisioneSpesaFuture = CompletableFuture
				.supplyAsync(() -> interventoPrevisioneSpesaRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));

		CompletableFuture<List<Map<String, Object>>> pianoFinanziarioFuture = CompletableFuture
				.supplyAsync(() -> finanziamentoUtilityService
						.getPianoFinanziarioByIntIdAndEnteId(intervento.getIntId(), intervento.getEnteId()));
		
		CompletableFuture
				.allOf(enteQuadranteFuture, obiettiviFuture, finalitaFuture, tipiFuture, descrizioniAttrezzatureFuture,
						categorieFuture, contrattiTipoFuture, appaltiTipoFuture, statiProgettualiFuture,
						interventiStrutturaFuture, interventoPrevisioneSpesaFuture, pianoFinanziarioFuture)
				.join();

		Optional<REnteQuadrante> enteQuadrante;
		List<RInterventoObiettivo> obiettivi;
		List<RInterventoFinalita> finalita;
		List<RInterventoTipo> tipi;
		List<RInterventoTipo> descrizioniAttrezzature;
		List<RInterventoCategoria> categorie;
		List<RInterventoContrattoTipo> contrattiTipo;
		List<RInterventoAppaltoTipo> appaltiTipo;
		List<RInterventoStatoProgettuale> statiProgettuali;
		List<InterventoStruttura> interventiStruttura;
		List<InterventoPrevisioneSpesa> previsioniSpesa;
		List<Map<String, Object>> pianoFinanziario;
		try {
			enteQuadrante = enteQuadranteFuture.get();
			obiettivi = obiettiviFuture.get();
			finalita = finalitaFuture.get();
			tipi = tipiFuture.get();
			descrizioniAttrezzature = descrizioniAttrezzatureFuture.get();
			categorie = categorieFuture.get();
			contrattiTipo = contrattiTipoFuture.get();
			appaltiTipo = appaltiTipoFuture.get();
			statiProgettuali = statiProgettualiFuture.get();
			interventiStruttura = interventiStrutturaFuture.get();
			previsioniSpesa = interventoPrevisioneSpesaFuture.get();
			pianoFinanziario = pianoFinanziarioFuture.get();
		} catch (Exception e) {
			if (e instanceof InterruptedException)
				Thread.currentThread().interrupt();				
			throw new PgmeasException(e);
		}
		
		RInterventoModulo rInterventoModulo = moduloUtilityService.getModuloIntByInterventoIdAndEnteId(
				intervento.getIntId(), enteId);

		return buildInterventoDto(intervento, enteId, enteQuadrante, obiettivi, finalita, tipi, descrizioniAttrezzature,
				categorie, contrattiTipo, appaltiTipo, statiProgettuali, stato, interventiStruttura, previsioniSpesa,
				pianoFinanziario, userInfo, statoIntervento, rInterventoModulo);
	}

	private InterventoV2GetDto buildInterventoDto(Intervento intervento, Integer enteId,
			Optional<REnteQuadrante> enteQuadrante, List<RInterventoObiettivo> obiettivi,
			List<RInterventoFinalita> finalita, List<RInterventoTipo> tipi,
			List<RInterventoTipo> descrizioniAttrezzature, List<RInterventoCategoria> categorie,
			List<RInterventoContrattoTipo> contrattiTipo, List<RInterventoAppaltoTipo> appaltiTipo,
			List<RInterventoStatoProgettuale> statiProgettuali, RInterventoStato stato,
			List<InterventoStruttura> interventiStruttura, List<InterventoPrevisioneSpesa> previsioniSpesa,
			List<Map<String, Object>> pianoFinanziario, UserInfoRecord userInfo, String statoIntervento,
			RInterventoModulo rInterventoModulo)
			throws PgmeasException {

		InterventoV2GetDto interventoDto = MappingUtils.copy(intervento, new InterventoV2GetDto());

		interventoDto.setQuadranteId(enteQuadrante.isPresent() ? enteQuadrante.get().getQuadranteId() : null);
		interventoDto.setObiettivi(obiettivi.stream().map(obj -> obj.getIntObiettivoId()).toList());
		interventoDto.setFinalita(finalita.stream().map(obj -> obj.getIntFinalitaId()).toList());
		List<Integer> tipiList = tipi.stream().map(obj -> obj.getIntTipoId()).toList();
		interventoDto.setTipi(tipiList.stream().distinct().collect(Collectors.toList()));
		interventoDto.setDescrizioniAttrezzature(descrizioniAttrezzature.stream().map(obj -> obj.getIntTipoDetId()).toList());
		interventoDto.setCategorie(categorie.stream().map(obj -> obj.getIntCategoriaId()).toList());
		interventoDto.setContrattiTipo(contrattiTipo.stream().map(obj -> obj.getIntContrattoTipoId()).toList());
		interventoDto.setAppaltiTipo(appaltiTipo.stream().map(obj -> obj.getIntAppaltoTipoId()).toList());
		interventoDto.setStatiProgettuali(statiProgettuali.stream().map(obj -> obj.getIntStatoProgId()).toList());
		interventoDto.setStato(stato.getIntStatoId());
		interventoDto.setStatoNota(stato.getNote());
		interventoDto.setInterventiStruttura(interventiStruttura.stream().map(obj -> obj.getIntStrId()).toList());
		interventoDto.setPrevisioniDiSpesa(buildPrevisioniSpesa(previsioniSpesa));
		interventoDto.setPianiFinanziari(buildPianoFinanziario(pianoFinanziario, userInfo, statoIntervento));
		interventoDto.setAllegatoDeliberaApprovazione(allegatoUtility.buildInterventoAllegatiDto(
				intervento.getIntId(), enteId, AllegatoTipoEnum.DELIBERA_AZIENDALE_DI_APPROVAZIONE));
		interventoDto.setAllegatoDgrApprovazione(allegatoUtility
				.buildInterventoAllegatiDto(intervento.getIntId(), enteId, AllegatoTipoEnum.DGR_DI_APPROVAZIONE));
		interventoDto.setAllegatoDgrPropostaCR(allegatoUtility.buildInterventoAllegatiDto(intervento.getIntId(),
				enteId, AllegatoTipoEnum.DGR_DI_PROPOSTA));
		interventoDto.setAllegatoDcrApprovazione(allegatoUtility
				.buildInterventoAllegatiDto(intervento.getIntId(), enteId, AllegatoTipoEnum.DCR_DI_APPROVAZIONE));
		interventoDto.setAllegatiDeterminazioniDirigenziali(allegatoUtility.buildInterventoAllegatiDto(
				intervento.getIntId(), enteId, AllegatoTipoEnum.DETERMINA_DIRIGENZIALE_REGIONALE));

		// se copiato
		if (interventoDto.getCopiatoDaIntId() != null && interventoDto.getCopiatoDaEnteId() != null) {
			Map<String, Object> interventoCopiato = findAnnoAndCodiceByIntIdAndEnteId(intervento.getCopiatoDaIntId(),
					intervento.getCopiatoDaEnteId());

			interventoDto.setCopiatoDaAnno(Integer.parseInt(interventoCopiato.get("intAnno").toString()));
			interventoDto.setCopiatoDaCodicePgmeas(interventoCopiato.get("intCod").toString());
		}
		
		//modulo INT
		if(rInterventoModulo != null) {
			interventoDto.setRIntModuloId(rInterventoModulo.getRIntModuloId());
			interventoDto.setRIntModuloStatoId(rInterventoModulo.getModuloStatoId());
		}

		return interventoDto;
	}

	public List<InterventoStrutturaV2GetDto> getInterventiStruttura(Integer intId, Integer enteId)
			throws PgmeasException {
		return getInterventiStruttura(intId, enteId, false);
	}

	public List<InterventoStrutturaV2GetDto> getInterventiStruttura(Integer intId, Integer enteId, boolean copy)
			throws PgmeasException {
		List<InterventoStruttura> interventiStruttura = interventoStrutturaRepository
				.findAllSenzaCancellatiByIntIdAndEnteId(intId, enteId);

		List<InterventoStrutturaV2GetDto> interventiStrutturaDto = new ArrayList<InterventoStrutturaV2GetDto>();
		for (InterventoStruttura interventoStruttura : interventiStruttura) {
			InterventoStrutturaV2GetDto interventoStrutturaDto = MappingUtils.copy(interventoStruttura,
					new InterventoStrutturaV2GetDto());

			Integer intStrId = interventoStrutturaDto.getIntStrId();

			Map<Integer, BigDecimal> quadroEconomico = quadroEconomicoUtilityService.getQuadroEconomico(intStrId, copy, enteId);
			Map<Integer, Boolean> interventoEdilizio = interventoEdilizioUtilityService.getInterventoEdilizio(intStrId,
					copy, enteId);

			interventoStrutturaDto.setQuadroEconomico(quadroEconomico);
			interventoStrutturaDto.setInterventoEdilizio(interventoEdilizio);
			interventoStrutturaDto.setParerePPP(allegatoUtility.buildInterventoParereDto(interventoStruttura,
					enteId, AllegatoTipoEnum.PARERE_PPP));
			interventoStrutturaDto.setParereHta(allegatoUtility.buildInterventoParereDto(interventoStruttura,
					enteId, AllegatoTipoEnum.PARERE_HTA));

			interventiStrutturaDto.add(interventoStrutturaDto);
		}

		return interventiStrutturaDto;
	}

	// TODO _CAPIRE SE CORRETTO STORICIZZAZIONE INTERVENTO STRUTTURA
	public void storicizzaListaInterventiStruttura(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		//
		List<InterventoStruttura> interventoStrutturaListOld = interventoStrutturaRepository
				.findAllSenzaCancellatiByIntIdAndEnteId(interventoId, enteId);
		interventoStrutturaListOld.stream().forEach(interventoStrutturaOld -> {
			storicizzaInterventoStruttura(interventoStrutturaOld, now, userInfo.codiceFiscale());
// DA TESTARE => CASO AUTORIZZATO DI CANCELLAZIONE 
			interventoStrutturaOld.setDataCancellazione(now);
			interventoStrutturaOld.setUtenteCancellazione("STORICIZZAZIONE DA PARTE DI " + userInfo.codiceFiscale());

			// DUBBIO => NON VA BENE NON TIENE CONTO DEI CANCELLATI o fine validità
			interventoEdilizioUtilityService.storicizzaInterventoEdilizio(userInfo, now, interventoStrutturaOld, enteId);

			// QUADRO ECONOMICO
			quadroEconomicoUtilityService.storicizzaQuadroEconomico(userInfo, now,
					interventoStrutturaOld.getIntStrId(), enteId);

			interventoStrutturaRepository.save(interventoStrutturaOld);
		});
	}

	public void salvaListaInterventiStruttura(InterventoBase body, UserInfoRecord userInfo, Timestamp now, Ente ente,
			Intervento interventoSaved) throws PgmeasException, IOException {
		for (InterventoStrutturaToSave from : body.getInterventoStrutturaList()) {
			InterventoStruttura interventoStrutturaOld = 
					from.getIntStrId() != null ? interventoStrutturaRepository.findByIntStrId(from.getIntStrId()) : null;
			InterventoStruttura interventoStruttura = InterventoUtils
					.buildInterventoStrutturaFromInterventoStrutturaToSaveModel(body, userInfo, now, interventoSaved,
							from, ente, interventoStrutturaOld);
			InterventoStruttura interventoStrutturaSaved = interventoStrutturaRepository.saveAndFlush(interventoStruttura);
			interventoEdilizioUtilityService.salvaInterventoEdilizio(userInfo, now, ente, interventoStrutturaSaved,
					from, interventoSaved);

			// QUADRO ECONOMICO TS
			quadroEconomicoUtilityService.salvaQuadroEconomico(userInfo, now, ente, interventoSaved,
					from.getQuadroEconMap(), interventoStrutturaSaved.getIntStrId());
			
			if(interventoStrutturaOld != null) {
				allegatoUtility.storicizzaESalvaPareri(userInfo, now, interventoSaved.getIntId(), ente.getEnteId(), 
					interventoStrutturaOld, interventoStruttura);
			}

		}
	}

	public void checkInterventoTipo(InterventoBase body) throws PgmeasException {
		InterventoTipo interventoTipo = interventoTipoServiceUtility
				.getInterventoTipoValidoByInterventoTipoCod(InterventoTipoEnum.ACQ_ATTR);
		boolean trovato = body.getListaIntTipoId().stream().anyMatch(x -> x.equals(interventoTipo.getIntTipoId()));
		if (trovato && (body.getListaIntTipoDetId() == null || body.getListaIntTipoDetId().isEmpty())) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Payload non compliant per intervento tipo descrizione attrezzature obbligario inserire intTipoDetId",
					new ArrayList<ErroreDettaglio>(),
					"MSG-005: Inserire campo obbligatorio: Descrizione attrezzatura");
		}
	}
	
	public void validateRequiredInviaInterventoARegione(Intervento intervento) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
				
		List<Allegato> allegati = allegatoUtility.getAllegatiByIntervento(intervento.getIntId(), intervento.getEnteId());
		validateAllegatiPutAzienda(allegati, listaErroriRilevati);
		
		if (listaErroriRilevati.size() > 0) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "MSG-094: Attenzione! Per poter inviare l'intervento a Regione deve essere valorizzato la sezione 'delibera aziendale di approvazione'",
					listaErroriRilevati, "MSG-094: Attenzione! Per poter inviare l'intervento a Regione deve essere valorizzato la sezione 'delibera aziendale di approvazione'");
		}
	}
	
	public void validateRequiredApprovaInterventoByRegione(Intervento intervento, UserInfoRecord userInfo, String statoIntervento) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
		if(intervento.getIntFinanziabile() == null || (intervento.getIntFinanziabile() != null && !intervento.getIntFinanziabile())) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(ErroreEnum.MSG_074.getCode());
			err.setValore("Attenzione! Non è possibile approvare l'intervento perché il campo finanziabile non è stato selezionato");
			listaErroriRilevati.add(err);
		}
		
		List<Allegato> allegati = allegatoUtility.getAllegatiByIntervento(intervento.getIntId(), intervento.getEnteId());
		validatePareriPutRegione(intervento, allegati, listaErroriRilevati);
		validatePrevisioniDiSpesaPutRegione(intervento, listaErroriRilevati);
		validatePianoFinanziarioPutRegione(intervento, userInfo, statoIntervento, listaErroriRilevati);
		validateAllegatiPutRegione(allegati, listaErroriRilevati);
		
		if (listaErroriRilevati.size() > 0) { //TODO vedi issues
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Payload non compliant",
					listaErroriRilevati, "");
		}
	}
	
	private void validatePareriPutRegione(Intervento intervento, List<Allegato> allegati, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		List<InterventoStruttura> interventiStruttura = interventoStrutturaRepository
				.findAllSenzaCancellatiByIntIdAndEnteId(intervento.getIntId(), intervento.getEnteId());
		
		for(int i = 0; i < interventiStruttura.size(); i++) {
			InterventoStruttura intStr = interventiStruttura.get(i);
			if(intStr.getIntstrParerePpp() != null && intStr.getIntstrParerePpp()) {
				AllegatoTipoEnum allegatoTipoEnum = AllegatoTipoEnum.PARERE_PPP;
				AllegatoTipo allegatoTipo = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(allegatoTipoEnum);
				Optional<Allegato> allegatoOpt = allegati.stream()
						.filter(a -> a.getAllegatoTipoId().equals(allegatoTipo.getAllegatoTipoId())).findFirst();
				checkAllegatoObbligatorio(allegatoTipoEnum, allegatoOpt, listaErroriRilevati);
			}
			
			if(intStr.getIntstrParereHta() != null && intStr.getIntstrParereHta()) {
				AllegatoTipoEnum allegatoTipoEnum = AllegatoTipoEnum.PARERE_HTA;
				AllegatoTipo allegatoTipo = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(allegatoTipoEnum);
				Optional<Allegato> allegatoOpt = allegati.stream()
						.filter(a -> a.getAllegatoTipoId().equals(allegatoTipo.getAllegatoTipoId())).findFirst();
				checkAllegatoObbligatorio(allegatoTipoEnum, allegatoOpt, listaErroriRilevati);
			}
		}
	}
	
	private void validatePrevisioniDiSpesaPutRegione(Intervento intervento, List<ErroreDettaglio> listaErroriRilevati) {
		List<InterventoPrevisioneSpesa> interventoPrevisioneSpesa = interventoPrevisioneSpesaRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), intervento.getEnteId());
		List<PrevisioneSpesaDto> previsioniDiSpesa = buildPrevisioniSpesa(interventoPrevisioneSpesa);
		
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_PREVISIONE_DI_SPESA, previsioniDiSpesa, listaErroriRilevati);
	    if (previsioniDiSpesa != null) {
	        // Controllo se ci sono anni duplicati nelle previsioni di spesa
	        Set<Integer> anni = previsioniDiSpesa.stream().map(ps -> ps.anno()).collect(Collectors.toSet());
	        if (previsioniDiSpesa.size() != anni.size()) {
	            ErroreDettaglio err = new ErroreDettaglio();
	            err.setChiave("anniPrevisioniDiSpesa");
	            err.setValore(
	                "Gli anni delle previsioni di spesa contengono duplicati. Assicurati che ogni anno sia unico per le previsioni di spesa inserite."
	            );
	            listaErroriRilevati.add(err);
	        }

	        // Verifica che gli anni delle previsioni di spesa non siano precedenti all'anno corrente
	        int currentYear = Year.now().getValue();
	        boolean anniPrecedenti = anni.stream().anyMatch(anno -> anno.compareTo(currentYear) < 0);
	        if (anniPrecedenti) {
	            ErroreDettaglio err = new ErroreDettaglio();
	            err.setChiave("anniPrevisioniDiSpesa");
	            err.setValore(
	                "Gli anni delle previsioni di spesa non possono essere precedenti all'anno corrente (" + currentYear + "). Correggi l'anno inserito."
	            );
	            listaErroriRilevati.add(err);
	        }

	        // Controllo che il totale degli importi delle previsioni corrisponda all'importo dell'intervento
	        BigDecimal importoTotalePrevisioneSpesa = previsioniDiSpesa.stream()
	                .map(PrevisioneSpesaDto::importo).reduce(BigDecimal.ZERO, BigDecimal::add);
	        if (importoTotalePrevisioneSpesa.compareTo(intervento.getIntImporto()) != 0) {
	            ErroreDettaglio err = new ErroreDettaglio();
	            err.setChiave("importoPrevisioneDiSpesa");
	            err.setValore(
	                "MSG-077: Il totale degli importi delle previsioni di spesa (" + importoTotalePrevisioneSpesa + 
	                ") non corrisponde all'importo complessivo dell'intervento (" + intervento.getIntImporto() + 
	                "). Verifica e correggi gli importi inseriti."
	            );
	            listaErroriRilevati.add(err);
	        }
	    }
	}

	private void validatePianoFinanziarioPutRegione(Intervento intervento, UserInfoRecord userInfo, String statoIntervento,
			List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		List<Map<String, Object>> pianoFinanziarioDb = finanziamentoUtilityService
						.getPianoFinanziarioByIntIdAndEnteId(intervento.getIntId(), intervento.getEnteId());
		
		List<PianoFinanziarioDto> pianiFinanziari = buildPianoFinanziario(pianoFinanziarioDb, userInfo, statoIntervento);
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_PIANO_FINANZIARIO, pianiFinanziari, listaErroriRilevati);
	    if (pianiFinanziari != null) {
	        // Verifica che esista un piano finanziario principale
	        Optional<PianoFinanziarioDto> pianoFinanziarioPrincipaleOptional = pianiFinanziari.stream()
	                .filter(pf -> pf.getIsPrincipale()).findFirst();
	        if (pianoFinanziarioPrincipaleOptional.isEmpty()) {
	            ErroreDettaglio err = new ErroreDettaglio();
	            err.setChiave(ErroreEnum.MSG_069.getCode());
	            err.setValore(ErroreEnum.MSG_069.getCode() + ": " + 
	                "Attenzione! Nel piano finanziario deve essere presente un finanziamento principale"
	            );
	            listaErroriRilevati.add(err);
	        }

	        // Verifica che il totale degli importi dei finanziamenti corrisponda al totale complessivo
	        for (PianoFinanziarioDto pianoFinanziario : pianiFinanziari) {
	            BigDecimal importoTotaleFinanziamenti = pianoFinanziario.getFinanziamentoImportoTipo().stream()
	                    .map(FinanziamentoImportoTipoDto::getFinanziamentoImporto)
	                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP); 
	            if (pianoFinanziario.getImportoTotale().compareTo(importoTotaleFinanziamenti) != 0) {
	                ErroreDettaglio err = new ErroreDettaglio();
	                err.setChiave("importoPianoFinanziario");
	                err.setValore(
	                    "Il totale degli importi dei finanziamenti (" + importoTotaleFinanziamenti + 
	                    ") non coincide con il totale complessivo del piano finanziario (" + pianoFinanziario.getImportoTotale() + 
	                    "). Verifica i dettagli degli importi per correggere l'errore."
	                );
	                listaErroriRilevati.add(err);
	            }
	        }
	        
			BigDecimal totalePianoFinanziario = pianiFinanziari.stream()
					.map(PianoFinanziarioDto::getImportoTotale) 
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			
			checkCostoInterventoAndPianoFinanziario(intervento.getIntImporto(), totalePianoFinanziario, listaErroriRilevati);
	    }
	}
	
	private void validateAllegatiPutAzienda(List<Allegato> allegati, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		AllegatoTipo allegatoTipoDelibera = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.DELIBERA_AZIENDALE_DI_APPROVAZIONE);
		Optional<Allegato> allegatoOptDelibera = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoTipoDelibera.getAllegatoTipoId())).findFirst();
		
		checkAllegatoObbligatorio(AllegatoTipoEnum.DELIBERA_AZIENDALE_DI_APPROVAZIONE, allegatoOptDelibera, listaErroriRilevati);
	}	
	
	private void validateAllegatiPutRegione(List<Allegato> allegati, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		AllegatoTipo allegatoTipoDGRAppr = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.DGR_DI_APPROVAZIONE);
		Optional<Allegato> allegatoOptDGRAppr = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoTipoDGRAppr.getAllegatoTipoId())).findFirst();
		
		AllegatoTipo allegatoTipoDGRPropCR = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.DGR_DI_PROPOSTA);
		Optional<Allegato> allegatoOptDGRPropCR = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoTipoDGRPropCR.getAllegatoTipoId())).findFirst();
		
		AllegatoTipo allegatoTipoDCRAppr = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.DCR_DI_APPROVAZIONE);
		Optional<Allegato> allegatoOptDCRAppr = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoTipoDCRAppr.getAllegatoTipoId())).findFirst();
		
		List<ErroreDettaglio> listaErroriRilevatiAllegati1 = new ArrayList<ErroreDettaglio>();
		List<ErroreDettaglio> listaErroriRilevatiAllegati2 = new ArrayList<ErroreDettaglio>();

		checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_APPROVAZIONE, allegatoOptDGRAppr, listaErroriRilevatiAllegati1);
		if (listaErroriRilevatiAllegati1.size() > 0) {
			checkAllegatoObbligatorio(AllegatoTipoEnum.DCR_DI_APPROVAZIONE, allegatoOptDCRAppr, listaErroriRilevatiAllegati2);
			checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_PROPOSTA, allegatoOptDGRPropCR, listaErroriRilevatiAllegati2);
			if (listaErroriRilevatiAllegati2.size() > 0) {
				ErroreDettaglio err = new ErroreDettaglio();
				err.setChiave(ErroreEnum.MSG_079.getCode());
				err.setValore(ErroreEnum.MSG_079.getCode() + ": Deve essere valorizzata una D.G.R. di approvazione oppure una D.G.R di proposta al consiglio regionale e una D.C.R. di approvazione");
				listaErroriRilevati.add(err);
			}
		}
	}
}

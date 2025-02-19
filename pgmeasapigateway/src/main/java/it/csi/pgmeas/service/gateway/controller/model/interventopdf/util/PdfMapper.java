/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf.util;

import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfRegione;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;

import it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto;
import it.csi.pgmeas.commons.dto.EnteDto;
import it.csi.pgmeas.commons.dto.InterventoAppaltoTipoDto;
import it.csi.pgmeas.commons.dto.InterventoCategoriaDto;
import it.csi.pgmeas.commons.dto.InterventoContrattoTipoDto;
import it.csi.pgmeas.commons.dto.InterventoFinalitaDto;
import it.csi.pgmeas.commons.dto.InterventoObiettivoDto;
import it.csi.pgmeas.commons.dto.InterventoStatoDto;
import it.csi.pgmeas.commons.dto.InterventoStatoProgettualeDto;
import it.csi.pgmeas.commons.dto.InterventoTipoDetDto;
import it.csi.pgmeas.commons.dto.InterventoTipoDto;
import it.csi.pgmeas.commons.dto.QuadranteDto;
import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.dto.v2.FinanziamentoImportoTipoDto;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaV2GetDto;
import it.csi.pgmeas.commons.dto.v2.InterventoV2GetDto;
import it.csi.pgmeas.commons.dto.v2.PianoFinanziarioDto;
import it.csi.pgmeas.commons.dto.v2.PrevisioneSpesaDto;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.InterventoEdilizioPdf;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.InterventoPdf;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.InterventoStrutturaPdf;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.PianoFinanziarioPdf;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.PrevisioneSpesaPdf;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.QuadroEconomicoPdf;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.StimeDurateInt;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.TotaliFinanziamento;
import it.csi.pgmeas.service.gateway.controller.utils.enumeration.ValutaEnum;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;
import it.csi.pgmeas.service.gateway.proxy.utils.RestClient;

public class PdfMapper {

	public Map<Integer, String> quadranteMap;
	public Map<Integer, StrutturaDto> strutturaMap;
	public Map<Integer, String> enteMap;
	public Map<Integer, String> interventoObiettivoMap;
	public Map<Integer, String> interventoFinalitaMap;
	public Map<Integer, String> interventoTipiMap;
	public Map<Integer, String> contrattiTipoMap;
	public Map<Integer, String> appaltiTipoMap;
	public Map<Integer, String> statiMap;
	public Map<Integer, String> statiProgettualiMap;
	public Map<Integer, ClassificazioneTreeByClassTsTipoDto> classificazioneTreeIEMap;
	public Map<Integer, ClassificazioneTreeByClassTsTipoDto> classificazioneTreeQEMap;
	public Map<Integer, String> intervTipoDettMap;
	public Map<Integer, String> interventoTipiCodiceMap;
	public Map<Integer, String> interventoCategorieMap;

	private static final String IMP_STATO = "IMP_STATO";
	private static final String IMP_REGIONE = "IMP_REGIONE";
	
	// Metodo che restituisce un oggetto InterventoPdf a partire da un
	// InterventoV2GetDto
	public InterventoPdf getGeneralInterventoPdf(InterventoV2GetDto dto, 
			InterventoStrutturaV2GetDto[] interventoStruttura, UserInfoRecord userInfo)
			throws ApiGatewayCustomException, CustomLoginException {
		InterventoPdf interventoPdf = new InterventoPdf();
		List<String> codTipiList = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		interventoPdf.setIsRegione(checkIfRegione(userInfo));
		
		interventoPdf.setDataOraGenerazione(now());
		
		interventoPdf.setAnno(dto.getIntAnno() != null ? dto.getIntAnno().toString() : null);
		interventoPdf.setDataInserimentoIntervento(sdf.format(dto.getDataCreazione()));
		
		// Decodifiche
		interventoPdf.setEnte(enteMap.get(dto.getEnteId()));
		interventoPdf.setQuadrante(quadranteMap.get(dto.getQuadranteId()));
		
		// Mappatura dei campi
		interventoPdf.setCup(dto.getIntCup());
		interventoPdf.setCodice(dto.getIntCod());
		interventoPdf.setCodiceOrig(dto.getCopiatoDaCodicePgmeas());
		interventoPdf.setAnnoOrig(dto.getCopiatoDaAnno());
		interventoPdf.setCodiceNSIS(dto.getIntCodicNsis());
		interventoPdf.setTitolo(dto.getIntTitolo());
		interventoPdf.setObiettivi(buildDecode(interventoObiettivoMap, dto.getObiettivi()));
		interventoPdf.setFinalita(buildDecode(interventoFinalitaMap, dto.getFinalita()));
		interventoPdf.setTipi(buildDecode(interventoTipiMap, dto.getTipi()));
		codTipiList = buildDecode(interventoTipiCodiceMap, dto.getTipi());
		interventoPdf.setFlgAttrezzatura(codTipiList != null && codTipiList.size() > 0 && codTipiList.contains("ACQ_ATTR"));
		interventoPdf.setDescrizioniAttrezzature(buildDecode(intervTipoDettMap, dto.getDescrizioniAttrezzature()));
		interventoPdf.setCategorie(buildDecode(interventoCategorieMap, dto.getCategorie()));
		
		interventoPdf.setAnnoPriorita(dto.getIntPrioritaAnno());
		interventoPdf.setPriorita(dto.getIntPriorita());
		interventoPdf.setSottopriorita(dto.getIntSottopriorita());
		
		interventoPdf.setContrattiTipo(buildDecode(contrattiTipoMap, dto.getContrattiTipo()));
		interventoPdf.setAppaltiTipo(buildDecode(appaltiTipoMap, dto.getAppaltiTipo()));
		interventoPdf.setStatiProgettuali(buildDecode(statiProgettualiMap, dto.getStatiProgettuali()));
		
		interventoPdf.setImporto(toEuro(dto.getIntImporto()));
		
		// Impostazione delle durate
		interventoPdf.setStimeDurataInt(new StimeDurateInt(dto.getProgettazioneGg(), dto.getAffidamentoLavoriGg(),
				dto.getEsecuzioneLavoriGg(), dto.getCollaudoGg()));
		interventoPdf.setAppaltoIntegrato(dto.getAppaltoIntegrato());

		// Combinazione del nome e cognome per Direttore Generale, Commissario,
		// Referente e RUP
		interventoPdf.setDirettoreGenerale(
				formatNomeCompleto(dto.getIntDirettoreGeneraleNome(), dto.getIntDirettoreGeneraleCognome(), dto.getIntDirettoreGeneraleCf()));
		interventoPdf.setCommissario(formatNomeCompleto(dto.getIntCommissarioNome(), dto.getIntCommissarioCognome(), dto.getIntCommissarioCf()));
		interventoPdf.setRup(formatNomeCompleto(dto.getIntRupNome(), dto.getIntRupCognome(), dto.getIntRupCf()));
		interventoPdf.setReferentePratica(
				formatReferente(dto.getIntReferentePraticaNome(), dto.getIntReferentePraticaCognome(), dto.getIntReferentePraticaCf(),
						dto.getIntReferentePraticaTelefono(), dto.getIntReferentePraticaEmail()));
		
		interventoPdf.setAllegatoDeliberaApprovazione(dto.getAllegatoDeliberaApprovazione());
		
		interventoPdf.setNote(dto.getNote());
		interventoPdf.setIntFinanziabile(dto.getIntFinanziabile());

		// Conversione delle previsioni di spesa
		if (dto.getPrevisioniDiSpesa() != null) {
			List<PrevisioneSpesaPdf> previsioniDiSpesaPdf = dto.getPrevisioniDiSpesa().stream()
					.map(previsione -> new PrevisioneSpesaPdf(previsione.anno().toString(), toEuro(previsione.importo())

					)).toList();
			interventoPdf.setPrevisioniDiSpesa(previsioniDiSpesaPdf);

			BigDecimal totPrevisioniDiSpesa = dto.getPrevisioniDiSpesa().stream()
					.map(PrevisioneSpesaDto::importo) //
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			interventoPdf.setTotalePrevisioniDiSpesa(toEuro(totPrevisioniDiSpesa));
		}

		// Conversione del piano finanziario
		if (dto.getPianiFinanziari() != null) {
			List<PianoFinanziarioPdf> pianiFinanziariPrincipaliPdf = new ArrayList<PianoFinanziarioPdf>();
			List<PianoFinanziarioDto> pfPrincipali = dto.getPianiFinanziari().stream().filter(pf -> pf.getIsPrincipale()).collect(Collectors.toList());
			
			TotaliFinanziamento totaliPrincipali = getPianiFinanziari(pfPrincipali, pianiFinanziariPrincipaliPdf);
			interventoPdf.setPianoFinanziarioPrincipale(pianiFinanziariPrincipaliPdf);
			interventoPdf.setTotalePianoFinanziarioStatoPrincipale(toEuro(totaliPrincipali.getTotaleStato()));
			interventoPdf.setTotalePianoFinanziarioRegionePrincipale(toEuro(totaliPrincipali.getTotaleRegione()));
			interventoPdf.setTotalePianoFinanziarioPrincipale(toEuro(totaliPrincipali.getTotale()));
			
			List<PianoFinanziarioPdf> pianiFinanziariAltriPdf = new ArrayList<PianoFinanziarioPdf>();
			List<PianoFinanziarioDto> pfAltri = dto.getPianiFinanziari().stream().filter(pf -> !pf.getIsPrincipale()).collect(Collectors.toList());
			
			TotaliFinanziamento totaliAltri = getPianiFinanziari(pfAltri, pianiFinanziariAltriPdf);
			interventoPdf.setPianoFinanziarioAltro(pianiFinanziariAltriPdf);
			interventoPdf.setTotalePianoFinanziarioStatoAltro(toEuro(totaliAltri.getTotaleStato()));
			interventoPdf.setTotalePianoFinanziarioRegioneAltro(toEuro(totaliAltri.getTotaleRegione()));
			interventoPdf.setTotalePianoFinanziarioAltro(toEuro(totaliAltri.getTotale()));
			
			interventoPdf.setTotalePianoFinanziarioStato(toEuro(totaliPrincipali.getTotaleStato().add(totaliAltri.getTotaleStato())));
			interventoPdf.setTotalePianoFinanziarioRegione(toEuro(totaliPrincipali.getTotaleRegione().add(totaliAltri.getTotaleRegione())));
			interventoPdf.setTotalePianoFinanziario(toEuro(totaliPrincipali.getTotale().add(totaliAltri.getTotale())));
		}
		
		interventoPdf.setAllegatoDgrApprovazione(dto.getAllegatoDgrApprovazione());
		interventoPdf.setAllegatoDgrPropostaCR(dto.getAllegatoDgrPropostaCR());
		interventoPdf.setAllegatoDcrApprovazione(dto.getAllegatoDcrApprovazione());
		interventoPdf.setAllegatiDeterminazioniDirigenziali(dto.getAllegatiDeterminazioniDirigenziali());

		return interventoPdf;
	}
	
	private TotaliFinanziamento getPianiFinanziari(List<PianoFinanziarioDto> pf, List<PianoFinanziarioPdf> pianiFinanziariPdf) {
		BigDecimal pianoFinanziarioStato = BigDecimal.ZERO; 
		BigDecimal pianoFinanziarioRegione = BigDecimal.ZERO;
		BigDecimal pianoFinanziarioTotale = BigDecimal.ZERO;
		
		for(PianoFinanziarioDto pianoFinanziario : pf) {
			PianoFinanziarioPdf pianoFinanziarioPdf = new PianoFinanziarioPdf();
			pianoFinanziarioPdf.setTipologia(pianoFinanziario.getTipologiaDesc());
			pianoFinanziarioPdf.setDettaglio(pianoFinanziario.getTipologiaDettaglioDesc());
			pianoFinanziarioPdf.setImporto(toEuro(pianoFinanziario.getImportoTotale()));
			pianoFinanziarioTotale = pianoFinanziarioTotale.add(pianoFinanziario.getImportoTotale());

			Optional<FinanziamentoImportoTipoDto> finStato = pianoFinanziario.getFinanziamentoImportoTipo().stream()
					.filter(fin -> fin.getFinanziamentoImportoTipoCod().equals(IMP_STATO)).findAny();
			if(finStato.isPresent()) {
				BigDecimal finImpStato = finStato.get().getFinanziamentoImporto();
				pianoFinanziarioPdf.setImportoStato(toEuro(finImpStato));
				pianoFinanziarioStato = pianoFinanziarioStato.add(finImpStato);
			} else {
				pianoFinanziarioPdf.setImportoStato(toEuro(BigDecimal.ZERO));
			}
			Optional<FinanziamentoImportoTipoDto> finRegione = pianoFinanziario.getFinanziamentoImportoTipo().stream()
					.filter(fin -> fin.getFinanziamentoImportoTipoCod().equals(IMP_REGIONE)).findAny();
			if(finRegione.isPresent()) {
				BigDecimal finImpRegione = finRegione.get().getFinanziamentoImporto();
				pianoFinanziarioPdf.setImportoRegione(toEuro(finImpRegione));
				pianoFinanziarioRegione = pianoFinanziarioRegione.add(finImpRegione);
			} else {
				pianoFinanziarioPdf.setImportoRegione(toEuro(BigDecimal.ZERO));
			}
			
			pianiFinanziariPdf.add(pianoFinanziarioPdf);
		}
		
		TotaliFinanziamento totaliFinanziamento = new TotaliFinanziamento(pianoFinanziarioRegione, pianoFinanziarioStato, pianoFinanziarioTotale);
		return totaliFinanziamento;
	}

	protected static String toEuro(BigDecimal importo) {
		if (importo == null) {
			return "0,00"; // Valore predefinito se l'importo è null
		}

		// Formatta il BigDecimal con due cifre decimali
		DecimalFormat euroFormat = new DecimalFormat("#,##0.00");
		euroFormat.setRoundingMode(RoundingMode.HALF_UP); // Arrotondamento al più vicino

		return euroFormat.format(importo);
	}
	
	protected static String formatEuroOrPercent(BigDecimal importo, String simbolo) {
		ValutaEnum valutaBySimbolo = ValutaEnum.fromCode(simbolo);
		String returnValue = "0,00";
		String decimalFormat = "#,##0.00";
		
		if(ValutaEnum.PERCENTUALE.getCode().equals(valutaBySimbolo.getCode())) {
			returnValue = "0,00000";
			decimalFormat = "#,#####0.00000";
		}
		
		if (importo == null) {
			return returnValue; // Valore predefinito se l'importo è null
		}

		// Formatta il BigDecimal con due cifre decimali
		DecimalFormat euroFormat = new DecimalFormat(decimalFormat);
		euroFormat.setRoundingMode(RoundingMode.HALF_UP); // Arrotondamento al più vicino

		return euroFormat.format(importo);
	}
	
	protected static String now() {
        // Ottieni la data e l'ora attuali
        LocalDateTime now = LocalDateTime.now();
        
        // Definisci il formato desiderato: dd/MM/yyyy hh24:mi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        // Restituisci la data formattata come stringa
        return now.format(formatter);
    }
	
	protected String buildDecode(Map<Integer, String> decoder, Integer valore) {
		return valore == null ? Strings.EMPTY
				: decoder.get(valore);
	}

	protected List<String> buildDecode(Map<Integer, String> decoder, List<Integer> valori) {
		return valori == null ? new ArrayList<>()
				: valori.stream() //
						.map(id -> decoder.getOrDefault(id, "--")) //
						// Mappa l'ID all'obiettivo o usa "--"
						.collect(Collectors.toList());
	}
	
	public InterventoStrutturaPdf getGeneralInterventoStrutturaPdf(InterventoStrutturaV2GetDto dto) {
		InterventoStrutturaPdf pdf = new InterventoStrutturaPdf();

		// Mappatura dei campi
		pdf.setStrId(dto.getStrId());
		StrutturaDto struttura = strutturaMap.get(dto.getStrId());
		pdf.setStrComune(struttura.getStrComune());
		pdf.setStrIndirizzo(struttura.getStrIndirizzo());
		pdf.setStrPgmeas(struttura.getStrPgmeas());
		if(struttura.getStrPgmeas()) {
			pdf.setStrNonCensita(struttura.getStrNonCensita());
			pdf.setStrNuova(struttura.getStrNuova());
			pdf.setStrDescrizione(struttura.getStrDenominazione());
			pdf.setStrDatiCatastali(struttura.getStrDatiCatastali());
			pdf.setStrNote(struttura.getNote());
		} else {
			String descrizioneStruttura = struttura.getStrDenominazione() + " (" + struttura.getStrCod() + ")";
			pdf.setStrDescrizione(descrizioneStruttura);
		}
		
		pdf.setIntId(dto.getIntId());
		pdf.setImporto(toEuro(dto.getIntstrImporto()));

		// Combinazione del nome e cognome per Responsabile Procedimento
		pdf.setResponsabileStrutturaComplessa(formatNomeCompleto(dto.getIntstrResponsabileStrutturaComplessaNome(),
				dto.getIntstrResponsabileStrutturaComplessaCognome(), dto.getIntstrResponsabileStrutturaComplessaCf()));
		pdf.setResponsabileStrutturaSemplice(formatNomeCompleto(dto.getIntstrResponsabileStrutturaSempliceNome(),
				dto.getIntstrResponsabileStrutturaSempliceCognome(), dto.getIntstrResponsabileStrutturaSempliceCf()));

		pdf.setParerePPP(dto.getParerePPP());
		pdf.setParereHta(dto.getParereHta());

		pdf.setStimeDurataInt(new StimeDurateInt(dto.getProgettazioneGg(), dto.getAffidamentoLavoriGg(),
				dto.getEsecuzioneLavoriGg(), dto.getCollaudoGg()));
		pdf.setAppaltoIntegrato(dto.getAppaltoIntegrato());

		pdf.setQuadroEconomico(getQuadroEconomico(dto.getQuadroEconomico()));

		pdf.setInterventoEdilizio(getInterventoEdilizio(dto.getInterventoEdilizio()));

		return pdf;
	}

	protected Map<Integer, QuadroEconomicoPdf> getQuadroEconomico(Map<Integer, BigDecimal> quadroEconomico) {
		Map<Integer, QuadroEconomicoPdf> quadroEconomicoPdf = new LinkedHashMap<>();

		// Popolazione della mappa quadroEconomicoPdf con i dati di
		// classificazioneTreeQEMap
		for (Map.Entry<Integer, ClassificazioneTreeByClassTsTipoDto> entry : classificazioneTreeQEMap.entrySet()) {
			Integer id = entry.getKey();
			ClassificazioneTreeByClassTsTipoDto classificazione = entry.getValue();

			// Crea un nuovo oggetto QuadroEconomicoPdf per ogni entry in
			// classificazioneTreeQEMap
			QuadroEconomicoPdf qeconomicPdf = new QuadroEconomicoPdf();
			qeconomicPdf.setId(id);
			qeconomicPdf.setDescrizione(classificazione.getDescrizione());
			qeconomicPdf.setLivello(classificazione.getLivello());
			qeconomicPdf.setEditabile(classificazione.getClassifTreeEditabile());
			qeconomicPdf.setConImporto(classificazione.getClassifTreeConImporto());
			qeconomicPdf.setSimbolo(classificazione.getClassifSimbolo());

			// Verifica se l'ID esiste in quadroEconomico
			if (quadroEconomico.containsKey(id)) {
				// Se l'ID è presente, imposta l'importo
				qeconomicPdf.setImporto(formatEuroOrPercent(quadroEconomico.get(id), classificazione.getClassifSimbolo()));
			} else {
				// Se l'ID non è presente, imposta "--"
				qeconomicPdf.setImporto("--");
			}

			// Aggiungi l'oggetto al Map
			quadroEconomicoPdf.put(id, qeconomicPdf);
		}

		return quadroEconomicoPdf;
	}

	protected Map<Integer, InterventoEdilizioPdf> getInterventoEdilizio(Map<Integer, Boolean> interventoEdilizioDto) {
		Map<Integer, InterventoEdilizioPdf> interventoEdilizioPdf = new LinkedHashMap<>();

		// Popolazione della mappa interventoEdilizioPdf con i dati di
		// classificazioneTreeEIMap
		for (Map.Entry<Integer, ClassificazioneTreeByClassTsTipoDto> entry : classificazioneTreeIEMap.entrySet()) {
			Integer id = entry.getKey();
			ClassificazioneTreeByClassTsTipoDto classificazione = entry.getValue();

			// Crea un nuovo oggetto InterventoEdilizioPdf per ogni entry in
			// classificazioneTreeEIMap
			InterventoEdilizioPdf interventoPdf = new InterventoEdilizioPdf();
			interventoPdf.setId(id);
			interventoPdf.setDescrizione(classificazione.getDescrizione());
			interventoPdf.setLivello(classificazione.getLivello());
			interventoPdf.setEditabile(classificazione.getSelezionabile()); //TODO
			interventoPdf.setConImporto(classificazione.getClassifTreeConImporto());

			// Se l'ID è presente in interventoEdilizioDto, imposta setSelezionato su
			// true/false
			// altrimenti imposta setSelezionato su false
			Boolean selezionato = interventoEdilizioDto.get(id);
			if (selezionato != null) {
				interventoPdf.setSelezionato(selezionato);
			} else {
				interventoPdf.setSelezionato(false); // Imposta su false se l'ID non è presente
			}

			// Aggiungi l'oggetto alla mappa
			interventoEdilizioPdf.put(id, interventoPdf);
		}

		return interventoEdilizioPdf;
	}

	// Metodo di utilità per formattare nome e cognome
	protected String formatNomeCompleto(String nome, String cognome, String cf) {
		return (nome == null ? "" : "Nome: " + nome + "   ") + (cognome == null ? "" : "Cognome: " + cognome+ "   ")
				+ (cf == null ? "" : "Codice fiscale: " + cf);
	}
	
	protected String formatReferente(String nome, String cognome, String cf, String telefono, String email) {
		return (nome == null ? "" : "Nome: " + nome + "   ") + (cognome == null ? "" : "Cognome: " + cognome + "   ")
				+ (cf == null ? "" : "Codice fiscale: " + cf + "   ") + (telefono == null ? "" : "Telefono: " + telefono + "   ")
				+ (cf == null ? "" : "E-mail: " + email);
	}

	public void initGeneralDecode(RestClient client, HttpHeaders headers) throws URISyntaxException {

		classificazioneTreeQEMap = client.getLinkedHashMap(ClassificazioneTreeByClassTsTipoDto[].class, headers,
				"/api/tipologiche/classifTree/QE", //
				ClassificazioneTreeByClassTsTipoDto::getClassifTreeId);
		
		classificazioneTreeIEMap = client.getLinkedHashMap(ClassificazioneTreeByClassTsTipoDto[].class, headers,
				"/api/tipologiche/classifTree/IE", //
				ClassificazioneTreeByClassTsTipoDto::getClassifTreeId);

		quadranteMap = client.getLinkedHashMap(QuadranteDto[].class, headers, "/api/tipologiche/quadrante", //
				QuadranteDto::getQuadranteId, QuadranteDto::getQuadranteDesc);

		strutturaMap = client.getLinkedHashMap(StrutturaDto[].class, headers, "/api/tipologiche/struttura", //
				StrutturaDto::getStrId);
		
		enteMap = client.getLinkedHashMap(EnteDto[].class, headers, "/api/tipologiche/ente", //
				EnteDto::getEnteId, EnteDto::getEnteDesc);

		interventoObiettivoMap = client.getLinkedHashMap(InterventoObiettivoDto[].class, headers,
				"/api/tipologiche/intervObiettivo", //
				InterventoObiettivoDto::getIntObiettivoId, InterventoObiettivoDto::getIntObiettivoDesc);

		interventoFinalitaMap = client.getLinkedHashMap(InterventoFinalitaDto[].class, headers, "/api/tipologiche/intervFinalita", //
				InterventoFinalitaDto::getIntFinalitaId, InterventoFinalitaDto::getIntfinalitaDesc);

		interventoTipiMap = client.getLinkedHashMap(InterventoTipoDto[].class, headers, "/api/tipologiche/intervTipo", //
				InterventoTipoDto::getIntTipoId, InterventoTipoDto::getIntTipoDesc);

		interventoTipiCodiceMap = client.getLinkedHashMap(InterventoTipoDto[].class, headers, "/api/tipologiche/intervTipo", //
				InterventoTipoDto::getIntTipoId, InterventoTipoDto::getIntTipoCod);

		contrattiTipoMap = client.getLinkedHashMap(InterventoContrattoTipoDto[].class, headers,
				"/api/tipologiche/intervContrattoTipo", //
				InterventoContrattoTipoDto::getIntContrattoTipoId, InterventoContrattoTipoDto::getIntContrattoTipoDesc);

		appaltiTipoMap = client.getLinkedHashMap(InterventoAppaltoTipoDto[].class, headers, "/api/tipologiche/intervAppaltoTipo", //
				InterventoAppaltoTipoDto::getIntAppaltoTipoId, InterventoAppaltoTipoDto::getIntAppaltoTipoDesc);

		statiMap = client.getLinkedHashMap(InterventoStatoDto[].class, headers, "/api/tipologiche/intervStato", //
				InterventoStatoDto::getIntStatoId, InterventoStatoDto::getIntStatoCod);
		
		statiProgettualiMap = client.getLinkedHashMap(InterventoStatoProgettualeDto[].class, headers,
				"/api/tipologiche/intervStatoProgettuale", //
				InterventoStatoProgettualeDto::getIntStatoProgId, InterventoStatoProgettualeDto::getIntStatoProgDesc);

		intervTipoDettMap = client.getLinkedHashMap(InterventoTipoDetDto[].class, headers, "/api/tipologiche/intervTipoDett", //
				InterventoTipoDetDto::getIntTipoDetId, InterventoTipoDetDto::getIntTipoDetDesc);
		
		interventoCategorieMap = client.getLinkedHashMap(InterventoCategoriaDto[].class, headers, "/api/tipologiche/intervCategoria", //
				InterventoCategoriaDto::getIntCategoriaId, InterventoCategoriaDto::getIntCategoriaDesc);

	}

}

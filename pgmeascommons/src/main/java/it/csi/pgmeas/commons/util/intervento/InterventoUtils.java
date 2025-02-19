/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.intervento;

import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfAsr;
import static it.csi.pgmeas.commons.validation.ValidationUtils.bigDecimalNullOrNotValidValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkAnnoDueSuccessivi;
import static it.csi.pgmeas.commons.validation.ValidationUtils.checkSottopriorita;
import static it.csi.pgmeas.commons.validation.ValidationUtils.integerNullOrNotValidValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.integerNullOrZeroValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.listNullOrEmptyValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.mapNullOrEmptyValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.objectNullValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.stringNullOrEmptyValidator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import it.csi.pgmeas.commons.dto.DichAppaltabilitaDto;
import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.v2.FinanziamentoImportoTipoDto;
import it.csi.pgmeas.commons.dto.v2.FinanziamentoImportoTipoSaveDto;
import it.csi.pgmeas.commons.dto.v2.InterventoBase;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaToSave;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToSaveModel;
import it.csi.pgmeas.commons.dto.v2.PianoFinanziarioDto;
import it.csi.pgmeas.commons.dto.v2.PianoFinanziarioSaveDto;
import it.csi.pgmeas.commons.dto.v2.PrevisioneSpesaDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.ClassificazioneTs;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Finanziamento;
import it.csi.pgmeas.commons.model.IntStrDicAppaltabilitaTree;
import it.csi.pgmeas.commons.model.IntStrDicAppaltabilitaTs;
import it.csi.pgmeas.commons.model.IntStrInterventoEdilizioTree;
import it.csi.pgmeas.commons.model.IntStrInterventoEdilizioTs;
import it.csi.pgmeas.commons.model.IntStrQuadroEconTree;
import it.csi.pgmeas.commons.model.IntStrQuadroEconTs;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoPrevisioneSpesa;
import it.csi.pgmeas.commons.model.InterventoStorico;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.model.InterventoStrutturaStorico;
import it.csi.pgmeas.commons.model.InterventoTipo;
import it.csi.pgmeas.commons.model.RFinanziamentoImporto;
import it.csi.pgmeas.commons.model.RInterventoAppaltoTipo;
import it.csi.pgmeas.commons.model.RInterventoCategoria;
import it.csi.pgmeas.commons.model.RInterventoContrattoTipo;
import it.csi.pgmeas.commons.model.RInterventoFinalita;
import it.csi.pgmeas.commons.model.RInterventoObiettivo;
import it.csi.pgmeas.commons.model.RInterventoStatoProgettuale;
import it.csi.pgmeas.commons.model.RInterventoTipo;
import it.csi.pgmeas.commons.util.enumeration.InterventoRequiredErrorEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.commons.validation.ValidationUtils;

public class InterventoUtils {
	
	// TODO SALVARE IL DET SOLO PER ACQ_ATTR
	public static List<RInterventoTipo> buildInterventoTipoListFromModel(InterventoBase body, UserInfoRecord userInfo,
			Timestamp now, Intervento interventoSaved,InterventoTipo interventoTipo) {
		List<RInterventoTipo> rInterventoTipoList = body.getListaIntTipoId().stream().flatMap(from -> {
			List<RInterventoTipo> resultList= new ArrayList<>();
			
			if(interventoTipo.getIntTipoId().equals(from)) {
				for(Integer intTipoDetId : body.getListaIntTipoDetId()) {
					RInterventoTipo to = buildRInterventoTipo(userInfo, now, interventoSaved, from);
					to.setIntTipoDetId(intTipoDetId);
					resultList.add(to);
				}
			}else {
				RInterventoTipo to = buildRInterventoTipo(userInfo, now, interventoSaved, from);
				resultList.add(to);
			}
		
			
			return resultList.stream();
		}).toList();
		return rInterventoTipoList;

	}

	private static RInterventoTipo buildRInterventoTipo(UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved, Integer from) {
		RInterventoTipo to = new RInterventoTipo();
		to.setDataCreazione(now);
		to.setDataModifica(now);
		to.setEnteId(interventoSaved.getEnteId());
		to.setIntId(interventoSaved.getIntId());
		to.setIntTipoId(from);
		to.setUtenteCreazione(userInfo.codiceFiscale());
		to.setUtenteModifica(userInfo.codiceFiscale());
		to.setValiditaInizio(now);
		return to;
	}

	public static List<RInterventoStatoProgettuale> buildStatoProgettualeInterventoFromModel(
			List<Integer> statoProgettualeIdList, UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		List<RInterventoStatoProgettuale> rInterventoStatoProgettualeList = statoProgettualeIdList.stream()
				.map(from -> {
					RInterventoStatoProgettuale to = new RInterventoStatoProgettuale();
					to.setDataCreazione(now);
					to.setDataModifica(now);
					to.setEnteId(interventoSaved.getEnteId());
					to.setIntId(interventoSaved.getIntId());
					to.setIntStatoProgId(from);
					to.setUtenteCreazione(userInfo.codiceFiscale());
					to.setUtenteModifica(userInfo.codiceFiscale());
					to.setValiditaInizio(now);

					return to;
				}).toList();
		return rInterventoStatoProgettualeList;
	}

	public static List<RInterventoAppaltoTipo> buildTipologiaAppaltoInterventoFromModel(InterventoBase body,
			UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		List<RInterventoAppaltoTipo> rInterventoAppaltoTipoList = body.getListaIntAppaltoTipoId().stream().map(from -> {
			RInterventoAppaltoTipo to = new RInterventoAppaltoTipo();
			to.setDataCreazione(now);
			to.setDataModifica(now);
			to.setEnteId(interventoSaved.getEnteId());
			to.setIntId(interventoSaved.getIntId());
			to.setIntAppaltoTipoId(from);
			to.setUtenteCreazione(userInfo.codiceFiscale());
			to.setUtenteModifica(userInfo.codiceFiscale());
			to.setValiditaInizio(now);
			return to;
		}).toList();
		return rInterventoAppaltoTipoList;
	}

	public static List<RInterventoContrattoTipo> buildContrattoInterventoListFromModel(InterventoBase body,
			UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		List<RInterventoContrattoTipo> rInterventoContrattoTipoList = body.getListaIntContrattoId().stream()
				.map(from -> {
					RInterventoContrattoTipo to = new RInterventoContrattoTipo();
					to.setDataCreazione(now);
					to.setDataModifica(now);
					to.setEnteId(interventoSaved.getEnteId());
					to.setIntId(interventoSaved.getIntId());
					to.setIntContrattoTipoId(from);
					to.setUtenteCreazione(userInfo.codiceFiscale());
					to.setUtenteModifica(userInfo.codiceFiscale());
					to.setValiditaInizio(now);
					return to;
				}).toList();
		return rInterventoContrattoTipoList;
	}

	public static List<RInterventoCategoria> buildCategoriaInterventoListFromModel(InterventoBase body,
			UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		List<RInterventoCategoria> rInterventoCategoriaList = body.getListaIntCategoriaId().stream().map(from -> {
			RInterventoCategoria to = new RInterventoCategoria();
			to.setDataCreazione(now);
			to.setDataModifica(now);
			to.setEnteId(interventoSaved.getEnteId());
			to.setIntId(interventoSaved.getIntId());
			to.setIntCategoriaId(from);
			to.setUtenteCreazione(userInfo.codiceFiscale());
			to.setUtenteModifica(userInfo.codiceFiscale());
			to.setValiditaInizio(now);
			return to;
		}).toList();
		return rInterventoCategoriaList;
	}

	public static List<RInterventoFinalita> buildFinalitaInterventoListFromModel(InterventoBase body,
			UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		List<RInterventoFinalita> rInterventoFinalitaList = body.getListaIntFinalitaId().stream().map(from -> {
			RInterventoFinalita to = new RInterventoFinalita();
			to.setDataCreazione(now);
			to.setDataModifica(now);
			to.setEnteId(interventoSaved.getEnteId());
			to.setIntId(interventoSaved.getIntId());
			to.setIntFinalitaId(from);
			to.setUtenteCreazione(userInfo.codiceFiscale());
			to.setUtenteModifica(userInfo.codiceFiscale());
			to.setValiditaInizio(now);
			return to;
		}).toList();
		return rInterventoFinalitaList;
	}

	public static List<RInterventoObiettivo> buildObiettivoInterventoListFromModel(InterventoBase body,
			UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		List<RInterventoObiettivo> rInterventoObiettoList = body.getListaIntObiettivoId().stream().map(from -> {
			RInterventoObiettivo to = new RInterventoObiettivo();
			to.setDataCreazione(now);
			to.setDataModifica(now);
			to.setEnteId(interventoSaved.getEnteId());
			to.setIntId(interventoSaved.getIntId());
			to.setIntObiettivoId(from);
			to.setUtenteCreazione(userInfo.codiceFiscale());
			to.setUtenteModifica(userInfo.codiceFiscale());
			to.setValiditaInizio(now);
			return to;
		}).toList();
		return rInterventoObiettoList;
	}

	public static InterventoStruttura buildInterventoStrutturaFromInterventoStrutturaToSaveModel(InterventoBase body,
			UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved, InterventoStrutturaToSave from,
			Ente ente, InterventoStruttura old) {
		InterventoStruttura interventoStruttura = new InterventoStruttura();
		interventoStruttura.setStrId(from.getStrId());
		interventoStruttura.setIntId(interventoSaved.getIntId());
		interventoStruttura.setEnteId(ente.getEnteId());
		interventoStruttura.setIntstrImporto(from.getIntStrImporto());
		interventoStruttura.setAppaltoIntegrato(from.getIntStrAppaltoIntegrato());
		interventoStruttura.setAffidamentoLavoriGg(from.getIntStrAffidamentoLavoriGg());
		interventoStruttura.setProgettazioneGg(from.getIntStrProgettazioneGg());
		interventoStruttura.setEsecuzioneLavoriGg(from.getIntStrEsecuzioneLavoriGg());
		interventoStruttura.setCollaudoGg(from.getIntStrCollaudoGg());
		interventoStruttura.setIntstrResponsabileStrutturaComplessaCognome(from.getIntstrRespStrComplesCognome());
		interventoStruttura.setIntstrResponsabileStrutturaComplessaNome(from.getIntstrRespStrComplesNome());
		interventoStruttura.setIntstrResponsabileStrutturaComplessaCf(from.getIntstrRespStrComplesCf());
		interventoStruttura.setIntstrResponsabileStrutturaSempliceCognome(from.getIntstrRespStrSemplCognome());
		interventoStruttura.setIntstrResponsabileStrutturaSempliceNome(from.getIntstrRespStrSemplNome());
		interventoStruttura.setIntstrResponsabileStrutturaSempliceCf(from.getIntstrRespStrSemplCf());
		interventoStruttura.setDataCreazione(now);
		interventoStruttura.setDataModifica(now);
		interventoStruttura.setUtenteCreazione(userInfo.codiceFiscale());
		interventoStruttura.setUtenteModifica(userInfo.codiceFiscale());
		
		if(old != null) {
			interventoStruttura.setIntstrParereHta(old.getIntstrParereHta());
			interventoStruttura.setIntstrParerePpp(old.getIntstrParerePpp());
		}
		
		return interventoStruttura;
	}

	public static IntStrQuadroEconTs buildIntStrQuadroEconomTs(UserInfoRecord userInfo, Timestamp now, Ente ente,
			Integer interventoStrutturaId, ClassificazioneTs clasTipoQuadroEcon) {
		var intStrQuadroEconTs = new IntStrQuadroEconTs();
		intStrQuadroEconTs.setClassifTsId(clasTipoQuadroEcon.getClassifTsId());
		intStrQuadroEconTs.setDataCreazione(now);
		intStrQuadroEconTs.setDataModifica(now);
		intStrQuadroEconTs.setEnteId(ente.getEnteId());
		intStrQuadroEconTs.setIntstrId(interventoStrutturaId);
		intStrQuadroEconTs.setUtenteCreazione(userInfo.codiceFiscale());
		intStrQuadroEconTs.setUtenteModifica(userInfo.codiceFiscale());
		intStrQuadroEconTs.setValiditaInizio(now);
		return intStrQuadroEconTs;
	}

	public static List<IntStrQuadroEconTree> buildIntStrQuadroEconomList(UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved, Map<Integer, BigDecimal> quadroEconomicoMap,
			IntStrQuadroEconTs IntStrQuadroEconTsSaved) {
		var quadroEconList = quadroEconomicoMap.entrySet().stream().map(entry -> {
			var quadroEcon = new IntStrQuadroEconTree();
			quadroEcon.setClassifTreeId(entry.getKey());
			quadroEcon.setDataCreazione(now);
			quadroEcon.setDataModifica(now);
			quadroEcon.setEnteId(interventoSaved.getEnteId());
			quadroEcon.setIntstrQeTreeImporto(entry.getValue() !=null ? entry.getValue() : BigDecimal.ZERO );
			quadroEcon.setIntstrQeTsId(IntStrQuadroEconTsSaved.getIntstrQeTsId());
			quadroEcon.setIntstrQeTreeVisibile(true);
			quadroEcon.setUtenteCreazione(userInfo.codiceFiscale());
			quadroEcon.setUtenteModifica(userInfo.codiceFiscale());
			quadroEcon.setValiditaInizio(now);
			return quadroEcon;
		}).toList();
		return quadroEconList;
	}

	public static IntStrInterventoEdilizioTs buildIntStrInterventoEdilizioTs(UserInfoRecord userInfo, Timestamp now,
			Ente ente, Integer interventoStrutturaId, ClassificazioneTs clasTipoIntEdil) {
		var intStrInterventoEdilizioTs = new IntStrInterventoEdilizioTs();
		intStrInterventoEdilizioTs.setClassifTsId(clasTipoIntEdil.getClassifTsId());
		intStrInterventoEdilizioTs.setDataCreazione(now);
		intStrInterventoEdilizioTs.setDataModifica(now);
		intStrInterventoEdilizioTs.setEnteId(ente.getEnteId());
		intStrInterventoEdilizioTs.setIntstrId(interventoStrutturaId);
		intStrInterventoEdilizioTs.setUtenteCreazione(userInfo.codiceFiscale());
		intStrInterventoEdilizioTs.setUtenteModifica(userInfo.codiceFiscale());
		intStrInterventoEdilizioTs.setValiditaInizio(now);
		return intStrInterventoEdilizioTs;
	}

	public static List<IntStrInterventoEdilizioTree> buildintStrInterventoEdilizioList(UserInfoRecord userInfo,
			Timestamp now, Intervento interventoSaved, InterventoStrutturaToSave from,
			IntStrInterventoEdilizioTs intStrInterventoEdilizioTsSaved) {
		var intStrInterventoEdilizioList = from.getIntStrTipoIntEdilMap().entrySet().stream().map(entry -> {
			var interventoEdilizio = new IntStrInterventoEdilizioTree();
			interventoEdilizio.setDataCreazione(now);
			interventoEdilizio.setDataModifica(now);
			interventoEdilizio.setEnteId(interventoSaved.getEnteId());
			interventoEdilizio.setClassifTreeId(entry.getKey());
			interventoEdilizio.setIntstrIeTsId(intStrInterventoEdilizioTsSaved.getIntstrIeTsId());
			interventoEdilizio.setIntstrIeTreeVisibile(entry.getValue()!=null? entry.getValue():false);
			interventoEdilizio.setUtenteCreazione(userInfo.codiceFiscale());
			interventoEdilizio.setUtenteModifica(userInfo.codiceFiscale());
			interventoEdilizio.setValiditaInizio(now);
			return interventoEdilizio;
		}).toList();
		return intStrInterventoEdilizioList;
	}

	public static boolean isInterventoDifferent(Intervento interventoOld, Intervento interventoNew) {
		if (interventoOld == interventoNew) {
			return false; // Sono la stessa istanza
		}
		if (interventoOld == null || interventoNew == null) {
			return true; // Se uno dei due è null, sono diverse
		}
		// Confronto di ogni campo significativo tramite i getter
		return !Objects.equals(interventoOld.getIntCod(), interventoNew.getIntCod())
				|| !Objects.equals(interventoOld.getIntTitolo(), interventoNew.getIntTitolo())
				|| !Objects.equals(interventoOld.getIntAnno(), interventoNew.getIntAnno())
				|| !Objects.equals(interventoOld.getIntCup(), interventoNew.getIntCup())
				|| !Objects.equals(interventoOld.getIntImporto(), interventoNew.getIntImporto())
				|| !Objects.equals(interventoOld.getIntCodicNsis(), interventoNew.getIntCodicNsis())
				|| !Objects.equals(interventoOld.getIntDirettoreGeneraleCognome(),
						interventoNew.getIntDirettoreGeneraleCognome())
				|| !Objects.equals(interventoOld.getIntDirettoreGeneraleNome(),
						interventoNew.getIntDirettoreGeneraleNome())
				|| !Objects.equals(interventoOld.getIntDirettoreGeneraleCf(), interventoNew.getIntDirettoreGeneraleCf())
				|| !Objects.equals(interventoOld.getIntCommissarioCognome(), interventoNew.getIntCommissarioCognome())
				|| !Objects.equals(interventoOld.getIntCommissarioNome(), interventoNew.getIntCommissarioNome())
				|| !Objects.equals(interventoOld.getIntCommissarioCf(), interventoNew.getIntCommissarioCf())
				|| !Objects.equals(interventoOld.getIntReferentePraticaCognome(),
						interventoNew.getIntReferentePraticaCognome())
				|| !Objects.equals(interventoOld.getIntReferentePraticaNome(),
						interventoNew.getIntReferentePraticaNome())
				|| !Objects.equals(interventoOld.getIntReferentePraticaCf(), interventoNew.getIntReferentePraticaCf())
				|| !Objects.equals(interventoOld.getIntReferentePraticaTelefono(),
						interventoNew.getIntReferentePraticaTelefono())
				|| !Objects.equals(interventoOld.getIntReferentePraticaEmail(),
						interventoNew.getIntReferentePraticaEmail())
				|| !Objects.equals(interventoOld.getIntQuadranteId(), interventoNew.getIntQuadranteId())
				|| !Objects.equals(interventoOld.getEnteId(), interventoNew.getEnteId())
				|| !Objects.equals(interventoOld.getIntRupCognome(), interventoNew.getIntRupCognome())
				|| !Objects.equals(interventoOld.getIntRupNome(), interventoNew.getIntRupNome())
				|| !Objects.equals(interventoOld.getIntRupCf(), interventoNew.getIntRupCf())
				|| !Objects.equals(interventoOld.getIntPrioritaAnno(), interventoNew.getIntPrioritaAnno())
				|| !Objects.equals(interventoOld.getIntPriorita(), interventoNew.getIntPriorita())
				|| !Objects.equals(interventoOld.getIntSottopriorita(), interventoNew.getIntSottopriorita())
				|| !Objects.equals(interventoOld.getIntFinanziabile(), interventoNew.getIntFinanziabile())
				|| !Objects.equals(interventoOld.getCopiatoDaIntId(), interventoNew.getCopiatoDaIntId())
				|| !Objects.equals(interventoOld.getCopiatoDaEnteId(), interventoNew.getCopiatoDaEnteId())
				|| !Objects.equals(interventoOld.getAppaltoIntegrato(), interventoNew.getAppaltoIntegrato())
				|| !Objects.equals(interventoOld.getAffidamentoLavoriGg(), interventoNew.getAffidamentoLavoriGg())
				|| !Objects.equals(interventoOld.getEsecuzioneLavoriGg(), interventoNew.getEsecuzioneLavoriGg())
				|| !Objects.equals(interventoOld.getProgettazioneGg(), interventoNew.getProgettazioneGg())
				|| !Objects.equals(interventoOld.getCollaudoGg(), interventoNew.getCollaudoGg())
				|| !Objects.equals(interventoOld.getNote(), interventoNew.getNote());
	}

	public static InterventoStorico buildInterventoStorico(Intervento intervento, Timestamp now, String cfUtente) {
		InterventoStorico storico = new InterventoStorico();
//	        TODO
		// Copia dei campi
		storico.setIntId(intervento.getIntId());
		storico.setIntCod(intervento.getIntCod());
		storico.setIntTitolo(intervento.getIntTitolo());
		storico.setIntAnno(intervento.getIntAnno());
		storico.setIntCup(intervento.getIntCup());
		storico.setIntImporto(intervento.getIntImporto());
		storico.setIntCodicNsis(intervento.getIntCodicNsis());
		storico.setIntDirettoreGeneraleCognome(intervento.getIntDirettoreGeneraleCognome());
		storico.setIntDirettoreGeneraleNome(intervento.getIntDirettoreGeneraleNome());
		storico.setIntDirettoreGeneraleCf(intervento.getIntDirettoreGeneraleCf());
		storico.setIntCommissarioCognome(intervento.getIntCommissarioCognome());
		storico.setIntCommissarioNome(intervento.getIntCommissarioNome());
		storico.setIntCommissarioCf(intervento.getIntCommissarioCf());
		storico.setIntReferentePraticaCognome(intervento.getIntReferentePraticaCognome());
		storico.setIntReferentePraticaNome(intervento.getIntReferentePraticaNome());
		storico.setIntReferentePraticaTelefono(intervento.getIntReferentePraticaTelefono());
		storico.setIntReferentePraticaEmail(intervento.getIntReferentePraticaEmail());
		storico.setIntReferentePraticaCf(intervento.getIntReferentePraticaCf());
		storico.setIntRupCognome(intervento.getIntRupCognome());
		storico.setIntRupNome(intervento.getIntRupNome());
		storico.setIntRupCf(intervento.getIntRupCf());
		storico.setIntQuadranteId(intervento.getIntQuadranteId());
		storico.setDataCreazione(intervento.getDataCreazione());
		storico.setDataModifica(intervento.getDataModifica());
		storico.setUtenteCreazione(intervento.getUtenteCreazione());
		storico.setUtenteModifica(cfUtente);
		storico.setEnteId(intervento.getEnteId());
//	        storico.setCopiatoDaIntId(intervento.getCopiatoDaIntId());
//	        storico.getCopiatoDaEnteId(intervento.getCopiatoDaEnteId());
		storico.setIntPrioritaAnno(intervento.getIntPrioritaAnno());
		storico.setIntPriorita(intervento.getIntPriorita());
		storico.setIntSottopriorita(intervento.getIntSottopriorita());
		storico.setIntFinanziabile(intervento.getIntFinanziabile());
		storico.setAppaltoIntegrato(intervento.getAppaltoIntegrato());
		storico.setProgettazioneGg(intervento.getProgettazioneGg());
		storico.setAffidamentoLavoriGg(intervento.getAffidamentoLavoriGg());
		storico.setEsecuzioneLavoriGg(intervento.getEsecuzioneLavoriGg());
		storico.setCollaudoGg(intervento.getCollaudoGg());
		storico.setValiditaInizio(intervento.getDataCreazione());
		Timestamp validitaFine = reduceTime(now);
		storico.setValiditaFine(validitaFine);
		storico.setNote(intervento.getNote());
		return storico;
	}

	public static InterventoStrutturaStorico buildInterventoStrutturaStoricoFromInterventoStruttura(
			InterventoStruttura interventoStruttura, Timestamp now, String cfUtente) {
		InterventoStrutturaStorico storico = new InterventoStrutturaStorico();
		// Copia dei campiF
		storico.setIntStrId(interventoStruttura.getIntStrId());
		storico.setIntId(interventoStruttura.getIntId());
		storico.setStrId(interventoStruttura.getStrId());
		storico.setIntstrImporto(interventoStruttura.getIntstrImporto());
		storico.setIntstrAperturaCantiereDataPrevista(interventoStruttura.getIntstrAperturaCantiereDataPrevista());
		storico.setIntstrAperturaCantiereDataEffettiva(interventoStruttura.getIntstrAperturaCantiereDataEffettiva());
		storico.setIntstrCollaudoDataPrevista(interventoStruttura.getIntstrCollaudoDataPrevista());
		storico.setIntstrCollaudoDataEffettiva(interventoStruttura.getIntstrCollaudoDataEffettiva());
		storico.setAppaltoIntegrato(interventoStruttura.getAppaltoIntegrato());
		storico.setAffidamentoLavoriGg(interventoStruttura.getAffidamentoLavoriGg());
		storico.setProgettazioneGg(interventoStruttura.getProgettazioneGg());
		storico.setEsecuzioneLavoriGg(interventoStruttura.getEsecuzioneLavoriGg());
		storico.setCollaudoGg(interventoStruttura.getCollaudoGg());
		storico.setDataCreazione(interventoStruttura.getDataCreazione());
		storico.setDataModifica(interventoStruttura.getDataModifica());
		storico.setUtenteCreazione(interventoStruttura.getUtenteCreazione());
		storico.setUtenteModifica(cfUtente);
		storico.setEnteId(interventoStruttura.getEnteId());
		storico.setIntstrResponsabileStrutturaComplessaCognome(
				interventoStruttura.getIntstrResponsabileStrutturaComplessaCognome());
		storico.setIntstrResponsabileStrutturaComplessaNome(
				interventoStruttura.getIntstrResponsabileStrutturaComplessaNome());
		storico.setIntstrResponsabileStrutturaComplessaCf(
				interventoStruttura.getIntstrResponsabileStrutturaComplessaCf());
		storico.setIntstrResponsabileStrutturaSempliceCognome(
				interventoStruttura.getIntstrResponsabileStrutturaSempliceCognome());
		storico.setIntstrResponsabileStrutturaSempliceNome(
				interventoStruttura.getIntstrResponsabileStrutturaSempliceNome());
		storico.setIntstrResponsabileStrutturaSempliceCf(
				interventoStruttura.getIntstrResponsabileStrutturaSempliceCf());
		storico.setIntstrParerePpp(interventoStruttura.getIntstrParerePpp());
		storico.setIntstrParereHta(interventoStruttura.getIntstrParereHta());
		storico.setValiditaInizio(interventoStruttura.getDataCreazione());
		Timestamp validitaFine = reduceTime(now);
		storico.setValiditaFine(validitaFine);
		return storico;
	}

	private static Timestamp reduceTime(Timestamp now) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now.getTime());
		cal.add(Calendar.SECOND, -1);
		Timestamp after = new Timestamp(cal.getTime().getTime());
		return after;
	}


	public static Intervento buildInterventoFromInterventoToSaveModel(InterventoBase body, Timestamp now,
			UserInfoRecord userInfo, Ente ente, String codiceIntervento,Intervento old) {
		Intervento intervento =new Intervento();
		intervento.setDataCreazione(now);
		intervento.setUtenteCreazione(userInfo.codiceFiscale());
		intervento.setIntCod(codiceIntervento);// generato
		if(old!=null) {
			// SOVRASCRIVO I VALORI CON QUELLI VECCHI id cod utente creazione data creazione
			intervento.setDataCreazione(old.getDataCreazione());
			intervento.setUtenteCreazione(old.getUtenteCreazione());
			intervento.setIntCod(old.getIntCod());// dal vecchio
			intervento.setIntId(old.getIntId());
			intervento.setIntFinanziabile(old.getIntFinanziabile()); //tengo le cose di regione
		}
		intervento.setDataModifica(now);
		intervento.setUtenteModifica(userInfo.codiceFiscale());
		intervento.setIntAnno(body.getIntAnno());
		intervento.setEnteId(ente.getEnteId());
		intervento.setIntQuadranteId(body.getIntQuadranteId());
		intervento.setIntTitolo(body.getIntTitolo());
		intervento.setCopiatoDaIntId(body.getIntCopiatoId());
		intervento.setCopiatoDaEnteId(body.getIntCopiatoEnteId());
		intervento.setIntCodicNsis(body.getIntCodicNsis());
		intervento.setIntPrioritaAnno(body.getIntPrioritaAnno());
		intervento.setIntPriorita(body.getIntPriorita());
		intervento.setIntSottopriorita(body.getIntSottopriorita());
		intervento.setIntImporto(body.getIntImporto());
		// SEZIONE RESPONSABILI INTERVENTO
		intervento.setIntDirettoreGeneraleCognome(body.getIntDirettoreGeneraleCognome());
		intervento.setIntDirettoreGeneraleNome(body.getIntDirettoreGeneraleNome());
		intervento.setIntDirettoreGeneraleCf(body.getIntDirettoreGeneraleCf());
		intervento.setIntCommissarioCognome(body.getIntCommissarioCognome());
		intervento.setIntCommissarioNome(body.getIntCommissarioNome());
		intervento.setIntCommissarioCf(body.getIntCommissarioCf());
		intervento.setIntRupCognome(body.getIntRupCognome());
		intervento.setIntRupNome(body.getIntRupNome());
		// TODO chiedere se sono obbligatori i cf rup visto che servono per la
		// validazione sull'invia
		intervento.setIntRupCf(body.getIntRupCf());
		intervento.setIntReferentePraticaCognome(body.getIntReferentePraticaCognome());
		intervento.setIntReferentePraticaNome(body.getIntReferentePraticaNome());
		intervento.setIntReferentePraticaEmail(body.getIntReferentePraticaEmail());
		intervento.setIntReferentePraticaTelefono(body.getIntReferentePraticaTelefono());
		intervento.setIntReferentePraticaCf(body.getIntReferentePraticaCf());
		intervento.setAppaltoIntegrato(body.getIntAppaltoIntegrato());
		intervento.setAffidamentoLavoriGg(body.getIntAffidamentoLavoriGg());
		intervento.setProgettazioneGg(body.getIntProgettazioneGg());
		intervento.setEsecuzioneLavoriGg(body.getIntEsecuzioneLavoriGg());
		intervento.setCollaudoGg(body.getIntCollaudoGg());
		intervento.setIntCup(body.getIntCup());
		intervento.setNote(body.getIntNote());
		return intervento;
	}

	public static List<PrevisioneSpesaDto> buildPrevisioniSpesa(
			List<InterventoPrevisioneSpesa> interventoPrevisioniSpesa) {
		List<PrevisioneSpesaDto> previsioniSpesa = new ArrayList<PrevisioneSpesaDto>();
		interventoPrevisioniSpesa.stream().forEach(ps -> {
			previsioniSpesa.add(new PrevisioneSpesaDto(ps.getIntPrevSpesaAnno(), ps.getIntPrevSpesaImporto()));
		});

		return previsioniSpesa;
	}

	private static FinanziamentoImportoTipoDto buildFinanziamentoImportoTipo(Map<String, Object> pfDb) {
		FinanziamentoImportoTipoDto finanziamentoImportoTipo = new FinanziamentoImportoTipoDto();
		finanziamentoImportoTipo
				.setFinanziamentoImportoTipoId(Integer.parseInt(pfDb.get("finanziamentoImportoTipoId").toString()));
		finanziamentoImportoTipo.setFinanziamentoImportoTipoCod(pfDb.get("finanziamentoImportoTipoCod").toString());
		finanziamentoImportoTipo.setFinanziamentoImportoTipoDesc(pfDb.get("finanziamentoImportoTipoDesc").toString());
		finanziamentoImportoTipo.setFinanziamentoImporto(new BigDecimal(pfDb.get("finanziamentoImporto").toString()));
		return finanziamentoImportoTipo;
	}

	public static PianoFinanziarioDto buildPianoFinanziario(Map<String, Object> pfDb, UserInfoRecord userInfo, String stato) {
		//se stato inserito o proposto e is asr -> importo = 0
		PianoFinanziarioDto pianoFinanziario = new PianoFinanziarioDto();

		if((stato.equals(InterventoStatoEnum.INSERITO.getCode()) || stato.equals(InterventoStatoEnum.PROPOSTO.getCode()))
				&& checkIfAsr(userInfo)) {
			pianoFinanziario.setImportoTotale(BigDecimal.ZERO);
		} else {
			pianoFinanziario.setImportoTotale(new BigDecimal(pfDb.get("importoTotale").toString()));
		}
		
		pianoFinanziario.setFinanziamentoId(Integer.parseInt(pfDb.get("finanziamentoId").toString()));
		pianoFinanziario.setIsPrincipale(Boolean.valueOf(pfDb.get("isPrincipale").toString()));
		pianoFinanziario.setTipologiaId(Integer.parseInt(pfDb.get("tipologiaId").toString()));
		pianoFinanziario.setTipologiaCod(pfDb.get("tipologiaCod").toString());
		pianoFinanziario.setTipologiaDesc(pfDb.get("tipologiaDesc").toString());
		pianoFinanziario.setTipologiaDettaglioId(Integer.parseInt(pfDb.get("tipologiaDettaglioId").toString()));
		pianoFinanziario.setTipologiaDettaglioCod(pfDb.get("tipologiaDettaglioCod").toString());
		pianoFinanziario.setTipologiaDettaglioDesc(pfDb.get("tipologiaDettaglioDesc").toString());
		pianoFinanziario.setFinanziamentoImportoTipo(new ArrayList<FinanziamentoImportoTipoDto>());

		return pianoFinanziario;
	}

	public static List<PianoFinanziarioDto> buildPianoFinanziario(List<Map<String, Object>> pianoFinanziarioDb, 
			UserInfoRecord userInfo, String stato) {
		List<PianoFinanziarioDto> pianoFinanziarioList = new ArrayList<PianoFinanziarioDto>();
		pianoFinanziarioDb.stream().forEach(pfDb -> {

			Optional<PianoFinanziarioDto> pfOptional = pianoFinanziarioList.stream()
					.filter(pf -> pf.getFinanziamentoId().equals(pfDb.get("finanziamentoId"))).findFirst();
			if (pfOptional.isPresent()) {
				PianoFinanziarioDto pianoFinanziario = pfOptional.get();
				pianoFinanziario.getFinanziamentoImportoTipo().add(buildFinanziamentoImportoTipo(pfDb));
			} else {
				PianoFinanziarioDto pianoFinanziario = buildPianoFinanziario(pfDb, userInfo, stato);
				pianoFinanziario.getFinanziamentoImportoTipo().add(buildFinanziamentoImportoTipo(pfDb));
				pianoFinanziarioList.add(pianoFinanziario);
			}

		});

		return pianoFinanziarioList;
	}

	public static void checkCostoInterventoAndQE(BigDecimal costoIntervento, List<BigDecimal> qeStruttura) throws PgmeasException {
	    List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();

	    BigDecimal totaleQE = qeStruttura.stream()
	        .filter(Objects::nonNull)
	        .reduce(BigDecimal.ZERO, BigDecimal::add);

	    if (totaleQE.compareTo(BigDecimal.ZERO) != 0) {
	        if (costoIntervento.compareTo(totaleQE) != 0) {
	            ErroreDettaglio err = new ErroreDettaglio();
	            err.setChiave("Costo Intervento e Quadro Economico");
	            err.setValore("Il costo dell'intervento non corrisponde al totale del quadro economico. Controlla i valori inseriti e assicurati che siano uguali.");
	            errorList.add(err);

	            ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Errore nei dati forniti", errorList,
	                "");
	        }
	    }
	}
	
	public static void checkCostoInterventoAndCostoStrutture(BigDecimal costoIntervento, BigDecimal costoStrutture) throws PgmeasException {
	    List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();

        if (costoIntervento.compareTo(costoStrutture) != 0) {
            ErroreDettaglio err = new ErroreDettaglio();
            err.setChiave("Costo Intervento e Costo Strutture");
            err.setValore("Il costo dell'intervento non corrisponde al totale del costo delle strutture. Controlla i valori inseriti e assicurati che siano uguali.");
            errorList.add(err);

            ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Errore nei dati forniti", errorList,
                "");
        }
	}

	public static void checkCostoInterventoAndPianoFinanziario(BigDecimal costoIntervento, BigDecimal pianoFinanziario, 
			List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {

	    if (costoIntervento.compareTo(pianoFinanziario) != 0) {
	        ErroreDettaglio err = new ErroreDettaglio();
	        err.setChiave("Costo Intervento e Piano Finanziario");
	        err.setValore("MSG-078: Il costo dell'intervento non corrisponde al piano finanziario. Assicurati che i due valori siano uguali.");
	        listaErroriRilevati.add(err);
	    }
	}
	
	//TODO si possono unire i successivi due metodi
	public static void validateRequiredPostIntervento(InterventoToSaveModel body) throws PgmeasException {
	    List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
	    checkInterventoToSaveModel(body, errorList);
//	    checkAllegatoObbligatorio(AllegatoTipoEnum.DELIBERA_AZIENDALE_DI_APPROVAZIONE, body.getIntAllegatoDelibera(), errorList);

	    if (errorList.size() > 0) {
	        ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "MSG-005: Inserire campo obbligatorio", errorList,
	            "MSG-005: Inserire campo obbligatorio");
	    }
	}

	public static void validateRequiredPutIntervento(InterventoToPutModel body) throws PgmeasException {
	    List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
	    checkInterventoToSaveModel(body, errorList);

//	    if (body.getIntAllegatoDeliberaNew() != null && body.getIntAllegatoDeliberaToDelete() == null) {
//	        ErroreDettaglio err = new ErroreDettaglio();
//	        err.setChiave("cancellazione dell'allegato di delibera");
//	        err.setValore("Se aggiungi un nuovo allegato, devi indicare quale allegato eliminare. Completa tutti i campi richiesti.");
//	        errorList.add(err);
//	    } else if (body.getIntAllegatoDeliberaToDelete() != null
//	            && body.getIntAllegatoDeliberaToDelete().getIdAllegato() != null) {
//	        checkAllegatoObbligatorio(AllegatoTipoEnum.DELIBERA_AZIENDALE_DI_APPROVAZIONE, body.getIntAllegatoDeliberaNew(), errorList);
//	    }

	    if (errorList.size() > 0) {
	        ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Errore nei dati forniti", errorList,
	            "Il payload contiene dati non corretti. Verifica e riprova.");
	    }
	}

	private static void checkLengthCup(String intCup, List<ErroreDettaglio> listaErroriRilevati) {
		if(Strings.isEmpty(intCup)) {
			return;
		}
		
		if(intCup.length() != 15) {
			listaErroriRilevati.add(new ErroreDettaglio("intCup", 
					"Il Cup deve essere di 15 caratteri"));
		}
	}
	
	// CONTROLLO ENTE UTENTE FORBIDDEN
	private static void checkInterventoToSaveModel(InterventoBase body, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
	    objectNullValidator("intervento", body, listaErroriRilevati);
	    integerNullOrNotValidValidator("anno dell'intervento", body.getIntAnno(), listaErroriRilevati);
	    integerNullOrNotValidValidator("quadrante dell'intervento", body.getIntQuadranteId(), listaErroriRilevati);
	    bigDecimalNullOrNotValidValidator("importo dell'intervento", body.getIntImporto(), listaErroriRilevati);
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_INTERVENTO_STRUTTURE, body.getInterventoStrutturaList(), listaErroriRilevati);

	    if (body.getInterventoStrutturaList() != null && body.getInterventoStrutturaList().size() > 0) {
	        IntStream.range(0, body.getInterventoStrutturaList().size()).forEach(
	            i -> checkInterventoStruttura(body.getInterventoStrutturaList().get(i), listaErroriRilevati, i));
	    }
	    checkInterventoOrigine(body, listaErroriRilevati);

	    stringNullOrEmptyValidator("titolo dell'intervento", body.getIntTitolo(), listaErroriRilevati);
	    
	    checkLengthCup(body.getIntCup(), listaErroriRilevati);
	    
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_OBIETTIVO_INTERVENTO, body.getListaIntObiettivoId(), listaErroriRilevati);
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_FINALITA_INTERVENTO, body.getListaIntFinalitaId(), listaErroriRilevati);
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_TIPO_INTERVENTO, body.getListaIntTipoId(), listaErroriRilevati);
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_CATEGORIE_INTERVENTO, body.getListaIntCategoriaId(), listaErroriRilevati);
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_CONTRATTO_TIPO, body.getListaIntContrattoId(), listaErroriRilevati);
	    listNullOrEmptyValidator(InterventoRequiredErrorEnum.LISTA_APPALTO_TIPO, body.getListaIntAppaltoTipoId(), listaErroriRilevati);

	    integerNullOrNotValidValidator("anno della priorità dell'intervento", body.getIntPrioritaAnno(), listaErroriRilevati);
	    if(body.getIntPrioritaAnno() != null) {
	    	checkAnnoDueSuccessivi("anno della priorità dell'intervento", body.getIntPrioritaAnno(), listaErroriRilevati);
	    }
	    
	    integerNullOrZeroValidator("priorità dell'intervento", body.getIntPriorita(), listaErroriRilevati);
	    if(body.getIntSottopriorita() != null) {
	    	checkSottopriorita("sottopriorità dell'intervento", body.getIntSottopriorita(), listaErroriRilevati);
	    }

        if(!StringUtils.isBlank(body.getIntDirettoreGeneraleCognome()) || !StringUtils.isBlank(body.getIntDirettoreGeneraleNome())
        		|| !StringUtils.isBlank(body.getIntDirettoreGeneraleCf())) {
            stringNullOrEmptyValidator("cognome del direttore generale", body.getIntDirettoreGeneraleCognome(), listaErroriRilevati);
            stringNullOrEmptyValidator("codice fiscale del direttore generale", body.getIntDirettoreGeneraleCf(), listaErroriRilevati);
            stringNullOrEmptyValidator("nome del direttore generale", body.getIntDirettoreGeneraleNome(), listaErroriRilevati);
        } 
        if(!StringUtils.isBlank(body.getIntCommissarioCognome()) || !StringUtils.isBlank(body.getIntCommissarioNome())
        		|| !StringUtils.isBlank(body.getIntCommissarioCf())) {
            stringNullOrEmptyValidator("cognome del commissario", body.getIntCommissarioCognome(), listaErroriRilevati);
            stringNullOrEmptyValidator("nome del commissario", body.getIntCommissarioNome(), listaErroriRilevati);
            stringNullOrEmptyValidator("codice fiscale del commissario", body.getIntCommissarioCf(), listaErroriRilevati);
        } 
        
	    stringNullOrEmptyValidator("cognome del RUP", body.getIntRupCognome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("nome del RUP", body.getIntRupNome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("codice fiscale del RUP", body.getIntRupCf(), listaErroriRilevati);
	    stringNullOrEmptyValidator("cognome del referente pratica", body.getIntReferentePraticaCognome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("nome del referente pratica", body.getIntReferentePraticaNome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("codice fiscale del referente pratica", body.getIntReferentePraticaCf(), listaErroriRilevati);

	    integerNullOrNotValidValidator("giorni di progettazione", body.getIntProgettazioneGg(), listaErroriRilevati);
	    integerNullOrNotValidValidator("giorni per l'affidamento dei lavori", body.getIntAffidamentoLavoriGg(), listaErroriRilevati);
	    integerNullOrNotValidValidator("giorni di esecuzione dei lavori", body.getIntEsecuzioneLavoriGg(), listaErroriRilevati);
	    integerNullOrNotValidValidator("giorni per il collaudo", body.getIntCollaudoGg(), listaErroriRilevati);
	    objectNullValidator("appalto integrato", body.getIntAppaltoIntegrato(), listaErroriRilevati);
	}

	private static void checkInterventoStruttura(InterventoStrutturaToSave intStr,
	        List<ErroreDettaglio> listaErroriRilevati, int index) {
	    integerNullOrNotValidValidator("[" + index + "]__strId", intStr.getStrId(), listaErroriRilevati);
	    mapNullOrEmptyValidator("[" + index + "]_IntStrTipoIntEdilMap", intStr.getIntStrTipoIntEdilMap(), listaErroriRilevati);
	    bigDecimalNullOrNotValidValidator("[" + index + "]_IntStrImporto", intStr.getIntStrImporto(), listaErroriRilevati);
	    integerNullOrNotValidValidator("[" + index + "]_intStrProgettazioneGg", intStr.getIntStrProgettazioneGg(), listaErroriRilevati);
	    integerNullOrNotValidValidator("[" + index + "]_intStrAffidamentoLavoriGg", intStr.getIntStrAffidamentoLavoriGg(), listaErroriRilevati);
	    integerNullOrNotValidValidator("[" + index + "]_intStrEsecuzioneLavoriGg", intStr.getIntStrEsecuzioneLavoriGg(), listaErroriRilevati);
	    integerNullOrNotValidValidator("[" + index + "]_intStrCollaudoGg", intStr.getIntStrCollaudoGg(), listaErroriRilevati);
	    objectNullValidator("[" + index + "]_intStrAppaltoIntegrato", intStr.getIntStrAppaltoIntegrato(), listaErroriRilevati);
//	    mapNullOrEmptyValidator("[" + index + "]_quadroEconMap", intStr.getQuadroEconMap(), listaErroriRilevati); //TODO RIMETTERE
	    if(StringUtils.isNotBlank(intStr.getIntstrRespStrComplesNome()) 
	    		|| StringUtils.isNotBlank(intStr.getIntstrRespStrComplesCognome()) 
	    		|| StringUtils.isNotBlank(intStr.getIntstrRespStrComplesCf())) {
	        stringNullOrEmptyValidator("[" + index + "]_intstrRespStrComplesNome", intStr.getIntstrRespStrComplesNome(), listaErroriRilevati);
		    stringNullOrEmptyValidator("[" + index + "]_intstrRespStrComplesCognome", intStr.getIntstrRespStrComplesCognome(), listaErroriRilevati);
		    stringNullOrEmptyValidator("[" + index + "]_intstrRespStrComplesCf", intStr.getIntstrRespStrComplesCf(), listaErroriRilevati);
	    }
	    if(StringUtils.isNotBlank(intStr.getIntstrRespStrSemplNome()) 
	    		|| StringUtils.isNotBlank(intStr.getIntstrRespStrSemplCognome()) 
	    		|| StringUtils.isNotBlank(intStr.getIntstrRespStrSemplCf())) {
	        stringNullOrEmptyValidator("[" + index + "]_intstrRespStrSemplNome", intStr.getIntstrRespStrSemplNome(), listaErroriRilevati);
		    stringNullOrEmptyValidator("[" + index + "]_intstrRespStrSemplCognome", intStr.getIntstrRespStrSemplCognome(), listaErroriRilevati);
		    stringNullOrEmptyValidator("[" + index + "]_intstrRespStrSemplCf", intStr.getIntstrRespStrSemplCf(), listaErroriRilevati);
	    }
	}

	//--
	private static void checkInterventoOrigine(InterventoBase body, List<ErroreDettaglio> listaErroriRilevati) {
	    // Se almeno uno è popolato, l'intervento è copiato, quindi tutti devono essere presenti
	    if (StringUtils.isNotBlank(body.getIntCodPgmeasOrig()) || body.getIntAnnoOrig() != null
	            || body.getIntCopiatoId() != null || body.getIntCopiatoEnteId() != null) {
	        StringBuilder sb = new StringBuilder();
	        sb.append(StringUtils.isNotBlank(body.getIntCodPgmeasOrig()) ? "" : "Codice intervento originale (intCodPgmeasOrig), ");
	        sb.append(body.getIntAnnoOrig() != null ? "" : "Anno dell'intervento originale (intAnnoOrig), ");
	        sb.append(body.getIntCopiatoId() != null ? "" : "Identificativo dell'intervento copiato (intCopiatoId), ");
	        sb.append(body.getIntCopiatoEnteId() != null ? "" : "Identificativo dell'ente di origine (intCopiatoEnteId), ");

	        if (sb.toString().length() > 0) {
	            ErroreDettaglio err = new ErroreDettaglio();
	            err.setChiave("InterventoOrigine");
	            err.setValore(
	                "Alcuni dati dell'intervento di origine sono mancanti o incompleti: " +
	                sb.toString().substring(0, sb.length() - 2) + 
	                ". Assicurati di fornire tutti i dettagli richiesti per procedere."
	            );
	            listaErroriRilevati.add(err);
	        }
	    }
	}
	
//	private static void validatePareriPutRegione(List<InterventoParereSaveExtDto> pareri, List<ErroreDettaglio> listaErroriRilevati) {
//	    listNullOrEmptyValidator("pareri", pareri, listaErroriRilevati);
//	    if (pareri != null) {
//	        pareri.forEach(parere -> {
//	            integerNullOrNotValidValidator(
//	                "identificativo della struttura dell'intervento (intStrId)", 
//	                parere.intStrId(), 
//	                listaErroriRilevati
//	            );
//	            objectNullValidator(
//	                "parere PPP (Parere di Programma, Progetto e Pianificazione)", 
//	                parere.parerePpp(), 
//	                listaErroriRilevati
//	            );
//
//	            if (parere.parerePpp() != null && parere.parerePpp().parere()) {
//	                InterventoParereSaveDto parerePpp = parere.parerePpp();
//                    checkAllegatoObbligatorio(
//                        AllegatoTipoEnum.PARERE_PPP, 
//                        parerePpp.allegatoNew(), 
//                        listaErroriRilevati
//                    );
//	            } 
//
//	            objectNullValidator(
//	                "parere HTA (Health Technology Assessment)", 
//	                parere.parereHta(), 
//	                listaErroriRilevati
//	            );
//
//	            if (parere.parereHta() != null && parere.parereHta().parere()) {
//	                InterventoParereSaveDto parereHta = parere.parereHta();
//                    checkAllegatoObbligatorio(
//                        AllegatoTipoEnum.PARERE_HTA, 
//                        parereHta.allegatoNew(), 
//                        listaErroriRilevati
//                    );
//	            } 
//	        });
//	    }
//	}
//	
//	private static void validatePrevisioniDiSpesaPutRegione(List<PrevisioneSpesaDto> previsioniDiSpesa, BigDecimal importoIntervento, List<ErroreDettaglio> listaErroriRilevati) {
//	    listNullOrEmptyValidator("previsioniDiSpesa", previsioniDiSpesa, listaErroriRilevati);
//	    if (previsioniDiSpesa != null) {
//	        // Controllo se ci sono anni duplicati nelle previsioni di spesa
//	        Set<Integer> anni = previsioniDiSpesa.stream().map(ps -> ps.anno()).collect(Collectors.toSet());
//	        if (previsioniDiSpesa.size() != anni.size()) {
//	            ErroreDettaglio err = new ErroreDettaglio();
//	            err.setChiave("anniPrevisioniDiSpesa");
//	            err.setValore(
//	                "Gli anni delle previsioni di spesa contengono duplicati. Assicurati che ogni anno sia unico per le previsioni di spesa inserite."
//	            );
//	            listaErroriRilevati.add(err);
//	        }
//
//	        // Verifica che gli anni delle previsioni di spesa non siano precedenti all'anno corrente
//	        int currentYear = Year.now().getValue();
//	        boolean anniPrecedenti = anni.stream().anyMatch(anno -> anno.compareTo(currentYear) < 0);
//	        if (anniPrecedenti) {
//	            ErroreDettaglio err = new ErroreDettaglio();
//	            err.setChiave("anniPrevisioniDiSpesa");
//	            err.setValore(
//	                "Gli anni delle previsioni di spesa non possono essere precedenti all'anno corrente (" + currentYear + "). Correggi l'anno inserito."
//	            );
//	            listaErroriRilevati.add(err);
//	        }
//
//	        // Controllo che il totale degli importi delle previsioni corrisponda all'importo dell'intervento
//	        BigDecimal importoTotalePrevisioneSpesa = previsioniDiSpesa.stream()
//	                .map(PrevisioneSpesaDto::importo).reduce(BigDecimal.ZERO, BigDecimal::add);
//	        if (importoTotalePrevisioneSpesa.compareTo(importoIntervento) != 0) {
//	            ErroreDettaglio err = new ErroreDettaglio();
//	            err.setChiave("importoPrevisioneDiSpesa");
//	            err.setValore(
//	                "Il totale degli importi delle previsioni di spesa (" + importoTotalePrevisioneSpesa + 
//	                ") non corrisponde all'importo complessivo dell'intervento (" + importoIntervento + 
//	                "). Verifica e correggi gli importi inseriti."
//	            );
//	            listaErroriRilevati.add(err);
//	        }
//	    }
//	}
//
//	private static void validatePianoFinanziarioPutRegione(List<PianoFinanziarioSaveDto> pianiFinanziari, List<ErroreDettaglio> listaErroriRilevati) {
//	    listNullOrEmptyValidator("pianoFinanziario", pianiFinanziari, listaErroriRilevati);
//	    if (pianiFinanziari != null) {
//	        // Verifica che esista un piano finanziario principale
//	        Optional<PianoFinanziarioSaveDto> pianoFinanziarioPrincipaleOptional = pianiFinanziari.stream()
//	                .filter(pf -> pf.getIsPrincipale()).findFirst();
//	        if (pianoFinanziarioPrincipaleOptional.isEmpty()) {
//	            ErroreDettaglio err = new ErroreDettaglio();
//	            err.setChiave("pianoFinanziarioPrincipale");
//	            err.setValore(
//	                "Non è stato specificato un piano finanziario principale. Assicurati di selezionare almeno un piano come principale."
//	            );
//	            listaErroriRilevati.add(err);
//	        }
//
//	        // Verifica che il totale degli importi dei finanziamenti corrisponda al totale complessivo
//	        for (PianoFinanziarioSaveDto pianoFinanziario : pianiFinanziari) {
//	            BigDecimal importoTotaleFinanziamenti = pianoFinanziario.getFinanziamentoImportoTipo().stream()
//	                    .map(FinanziamentoImportoTipoSaveDto::getFinanziamentoImporto)
//	                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//	            if (pianoFinanziario.getImportoTotale().compareTo(importoTotaleFinanziamenti) != 0) {
//	                ErroreDettaglio err = new ErroreDettaglio();
//	                err.setChiave("importoPianoFinanziario");
//	                err.setValore(
//	                    "Il totale degli importi dei finanziamenti (" + importoTotaleFinanziamenti + 
//	                    ") non coincide con il totale complessivo del piano finanziario (" + pianoFinanziario.getImportoTotale() + 
//	                    "). Verifica i dettagli degli importi per correggere l'errore."
//	                );
//	                listaErroriRilevati.add(err);
//	            }
//	        }
//	    }
//	}
//
//	
//	private static void validateAllegatiPutRegione(InterventoToPutByRegioneModel body, List<ErroreDettaglio> listaErroriRilevati) {
//		listNullOrEmptyValidator("determinazioniDirigenziali", body.getDeterminazioniDirigenzialiNew(),
//				listaErroriRilevati);
//		if (body.getDeterminazioniDirigenzialiNew() != null) {
//			body.getDeterminazioniDirigenzialiNew().forEach(allegato -> {
//				checkAllegatoObbligatorio(AllegatoTipoEnum.DETERMINA_DIRIGENZIALE_REGIONALE,allegato, listaErroriRilevati);
//			});
//		}
//
//		List<ErroreDettaglio> listaErroriRilevatiAllegati1 = new ArrayList<ErroreDettaglio>();
//		List<ErroreDettaglio> listaErroriRilevatiAllegati2 = new ArrayList<ErroreDettaglio>();
//
//		// TODO confronto con allegati già in possesso dell'intervento ???
//		// caso put
//		if (body.getDgrApprovazioneToDelete() != null && body.getDgrApprovazioneToDelete().getIdAllegato() != null) {
//			checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_APPROVAZIONE,body.getDgrApprovazioneNew(), listaErroriRilevatiAllegati1);
//			if (listaErroriRilevatiAllegati1.size() > 0) {
//				checkAllegatoObbligatorio(AllegatoTipoEnum.DCR_DI_APPROVAZIONE,body.getDcrApprovazioneNew(), listaErroriRilevatiAllegati2);
//				checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_PROPOSTA,body.getDgrConsiglioRegionaleNew(), listaErroriRilevatiAllegati2);
//				if (listaErroriRilevatiAllegati2.size() > 0) {
//					listaErroriRilevati.addAll(listaErroriRilevatiAllegati2);
//				}
//			}
//		} else if ((body.getDcrApprovazioneToDelete() != null
//				&& body.getDcrApprovazioneToDelete().getIdAllegato() != null)
//				|| (body.getDgrConsiglioRegionaleToDelete() != null
//						&& body.getDgrConsiglioRegionaleToDelete().getIdAllegato() != null)) {
//			checkAllegatoObbligatorio(AllegatoTipoEnum.DCR_DI_APPROVAZIONE,body.getDcrApprovazioneNew(), listaErroriRilevatiAllegati2);
//			checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_PROPOSTA,body.getDgrConsiglioRegionaleNew(), listaErroriRilevatiAllegati2);
//			if (listaErroriRilevatiAllegati2.size() > 0) {
//				checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_APPROVAZIONE,body.getDgrApprovazioneNew(), listaErroriRilevatiAllegati1);
//				if (listaErroriRilevatiAllegati1.size() > 0) {
//					listaErroriRilevati.addAll(listaErroriRilevatiAllegati1);
//				}
//			}
//		} else { // caso post
//			checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_APPROVAZIONE,body.getDgrApprovazioneNew(), listaErroriRilevatiAllegati1);
//			if (listaErroriRilevatiAllegati1.size() > 0) {
//				checkAllegatoObbligatorio(AllegatoTipoEnum.DCR_DI_APPROVAZIONE,body.getDcrApprovazioneNew(), listaErroriRilevatiAllegati2);
//				checkAllegatoObbligatorio(AllegatoTipoEnum.DGR_DI_PROPOSTA,body.getDgrConsiglioRegionaleNew(), listaErroriRilevatiAllegati2);
// 				if (listaErroriRilevatiAllegati2.size() > 0) {
//					listaErroriRilevati.addAll(listaErroriRilevatiAllegati2);
//				}
//			}
//		}
//	}
	
//	public static void validateRequiredPutInterventoByRegione(InterventoToPutByRegioneModel body,
//			BigDecimal importoIntervento) throws PgmeasException {
//		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
//
//		objectNullValidator("interventoRegione", body, listaErroriRilevati);
//		validatePareriPutRegione(body.getPareri(), listaErroriRilevati);
//		validatePrevisioniDiSpesaPutRegione(body.getPrevisioniDiSpesa(), importoIntervento, listaErroriRilevati);
//		validatePianoFinanziarioPutRegione(body.getPianiFinanziari(), listaErroriRilevati);
//		validateAllegatiPutRegione(body, listaErroriRilevati);
//		
//		if (listaErroriRilevati.size() > 0) {
//			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD",
//					listaErroriRilevati, "Payload non compliant");
//		}
//	}

	public static List<InterventoPrevisioneSpesa> buildPrevisioniSpesaFromDto(
			List<PrevisioneSpesaDto> previsioniSpesaDto, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<InterventoPrevisioneSpesa> previsioniSpesa = previsioniSpesaDto.stream().map(ps -> {
			InterventoPrevisioneSpesa previsioneSpesa = new InterventoPrevisioneSpesa();
			previsioneSpesa.setEnteId(interventoSaved.getEnteId());
			previsioneSpesa.setIntId(interventoSaved.getIntId());
			previsioneSpesa.setIntPrevSpesaAnno(ps.anno());
			previsioneSpesa.setIntPrevSpesaImporto(ps.importo());
			previsioneSpesa.setPianoId(null); // TODO delete?
			previsioneSpesa.setDataCreazione(now);
			previsioneSpesa.setDataModifica(now);
			previsioneSpesa.setUtenteCreazione(userInfo.codiceFiscale());
			previsioneSpesa.setUtenteModifica(userInfo.codiceFiscale());
			previsioneSpesa.setValiditaInizio(now);
			return previsioneSpesa;
		}).toList();

		return previsioniSpesa;
	}

	public static Finanziamento buildFinanziamentoFromDto(PianoFinanziarioSaveDto finanziamentoDto,
			UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved) {
		Finanziamento finanziamento = new Finanziamento();
		finanziamento.setDataCreazione(now);
		finanziamento.setDataModifica(now);
		finanziamento.setEnteId(interventoSaved.getEnteId());
		finanziamento.setFinCod(null);
		finanziamento.setFinImporto(finanziamentoDto.getImportoTotale());
		finanziamento.setFinNote(null);
		finanziamento.setFinPrincipale(finanziamentoDto.getIsPrincipale());
		finanziamento.setFinTipoDetId(finanziamentoDto.getTipologiaDettaglioId());
		finanziamento.setIntId(interventoSaved.getIntId());
		finanziamento.setProvId(null); // non c'è provvedimento in fase di programmazione
		finanziamento.setUtenteCreazione(userInfo.codiceFiscale());
		finanziamento.setUtenteModifica(userInfo.codiceFiscale());
		return finanziamento;
	}

	public static List<RFinanziamentoImporto> buildRFinanziamentoImportoFromDto(
			List<FinanziamentoImportoTipoSaveDto> finanziamentoImportiDto, UserInfoRecord userInfo, Timestamp now,
			Finanziamento finanziamentoSaved) {
		List<RFinanziamentoImporto> finanziamentoImporti = finanziamentoImportiDto.stream().map(fi -> {
			RFinanziamentoImporto finanziamentoImporto = new RFinanziamentoImporto();
			finanziamentoImporto.setDataCreazione(now);
			finanziamentoImporto.setDataModifica(now);
			finanziamentoImporto.setEnteId(finanziamentoSaved.getEnteId());
			finanziamentoImporto.setFinId(finanziamentoSaved.getFinId());
			finanziamentoImporto.setFinImporto(fi.getFinanziamentoImporto());
			finanziamentoImporto.setFinImpTipoId(fi.getFinanziamentoImportoTipoId());
			finanziamentoImporto.setUtenteCreazione(userInfo.codiceFiscale());
			finanziamentoImporto.setUtenteModifica(userInfo.codiceFiscale());
			finanziamentoImporto.setValiditaInizio(now);
			return finanziamentoImporto;
		}).toList();
		return finanziamentoImporti;
	}
	
	public static IntStrDicAppaltabilitaTs buildIntStrDicAppaltabilitaTs(UserInfoRecord userInfo, Timestamp now,
			Ente ente, Integer interventoStrutturaId,ClassificazioneTs clasTipoDicApp) {
		var intStrDicAppaltabilitaTs = new IntStrDicAppaltabilitaTs();
		intStrDicAppaltabilitaTs.setClassifTsId(clasTipoDicApp.getClassifTsId());
		intStrDicAppaltabilitaTs.setDataCreazione(now);
		intStrDicAppaltabilitaTs.setDataModifica(now);
		intStrDicAppaltabilitaTs.setEnteId(ente.getEnteId());
		intStrDicAppaltabilitaTs.setIntstrId(interventoStrutturaId);
		intStrDicAppaltabilitaTs.setUtenteCreazione(userInfo.codiceFiscale());
		intStrDicAppaltabilitaTs.setUtenteModifica(userInfo.codiceFiscale());
		intStrDicAppaltabilitaTs.setValiditaInizio(now);
		return intStrDicAppaltabilitaTs;
	}
	
	public static List<IntStrDicAppaltabilitaTree>buildIntStrDicAppaltabilitaList(UserInfoRecord userInfo,Timestamp now,Map<Integer, DichAppaltabilitaDto> dicAppMap, Ente ente,IntStrDicAppaltabilitaTs intStrDicAppaltabilitaTs){
		var dicAppList = dicAppMap.entrySet().stream().map(entry -> {
			var dicApp = new IntStrDicAppaltabilitaTree();
			dicApp.setClassifTreeId(entry.getKey());
			dicApp.setDataCreazione(now);
			dicApp.setDataModifica(now);
			dicApp.setEnteId(ente.getEnteId());
			dicApp.setIntstrDaTreeSelezionata(entry.getValue().getIntstrDaTreeSelezionata());
			dicApp.setIntstrDaTreeValidazioneData(entry.getValue().getIntstrDaTreeValidazioneData());
			dicApp.setAttoNumero(entry.getValue().getAttoNumero());
			dicApp.setIntstrDaTsId(intStrDicAppaltabilitaTs.getIntstrDaTsId());
			dicApp.setUtenteCreazione(userInfo.codiceFiscale());
			dicApp.setUtenteModifica(userInfo.codiceFiscale());
			dicApp.setValiditaInizio(now);
			return dicApp;
		}).toList();
		return dicAppList;
	}
	
	
}

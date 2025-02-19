/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.RafInterventoStrutturaDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoDto;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaToSave;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.util.enumeration.CronoprogrammaEnum;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class CronoProgrammaUtilityService {
	
	private static final String MES62="MSG-062: "
			+ "Attenzione! Il numero di giorni per la fase %s dell'intervento deve essere >= del massimo numero di giorni della fase %s indicato nelle strutture dell'intervento";
//			+ "Attenzione! Il numero di gg %s per la fase  "
//			+ "dell'intervento deve essere >= del valore massimo  della fase  delle strutture dell'intervento"
//			+ "				dove fase =PROGETTAZIONE, AFFIDAMENTO LAVORI, ESECUZIONE LAVORI, COLLAUDO";
	

	private static final String MSG83="MSG-83:"
			+ "  Attenzione! Il numero di giorni per la fase %s dell'intervento deve essere uguale al numero di giorni per per la fase %s della struttura dell'intervento";
//			+ " Attenzione! Il numero di gg %s per la fase  dell'intervento deve essere uguale = al numero di giorni per per la fase   della struttura dell'intervento"
//			+ "dove fase =PROGETTAZIONE, AFFIDAMENTO LAVORI, ESECUZIONE LAVORI, COLLAUDO";
	public void checkCronoProgramma(List<InterventoStrutturaToSave> intStrToSaveList,Intervento intervento) throws PgmeasException {
		List<CronoProgrammaRecord> cronoProgrammaList = intStrToSaveList.stream()
				.map(from -> {
					CronoProgrammaRecord to = new CronoProgrammaRecord(
					from.getIntStrAffidamentoLavoriGg(),
					from.getIntStrEsecuzioneLavoriGg(),
					from.getIntStrProgettazioneGg(),
					from.getIntStrCollaudoGg()
							);
					return to;
				}).toList();
		checkCronoProgrammaRecord(cronoProgrammaList,  intervento);
	}
	
	public void checkCronoProgramma(RichiestaAmmissioneFinanziamentoDto request, Intervento intervento) throws PgmeasException {
		List<CronoProgrammaRecord> cronoProgrammaList = new ArrayList<>();
		 for (Entry<Integer, RafInterventoStrutturaDto> entry :  request.getInterventoStrutturaMap().entrySet()) {
		        RafInterventoStrutturaDto tmp = entry.getValue();
		        CronoProgrammaRecord cronoProgramma = new CronoProgrammaRecord(
		                tmp.getIntStrAffidamentoLavoriGg(),
		                tmp.getIntStrEsecuzioneLavoriGg(),
		                tmp.getIntStrProgettazioneGg(),
		                tmp.getIntStrCollaudoGg()
		        );
		        cronoProgrammaList.add(cronoProgramma);
		    }
		 checkCronoProgrammaRecord(cronoProgrammaList,  intervento);
	}
	
	public void checkCronoProgrammaRecord(List<CronoProgrammaRecord> list, Intervento intervento)
			throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		// TODO Auto-generated method stub
//		Integer intStrAffidamentoLavoriGgTot = 0; // >= int
//		Integer intStrEsecuzioneLavoriGgTot = 0;
//		Integer intStrProgettazioneGgTot = 0;
//		Integer intStrCollaudoGgTot = 0;

		Integer intStrAffidamentoLavoriGgMax = -1; // <= int
		Integer intStrEsecuzioneLavoriGgMax = -1;
		Integer intStrProgettazioneGgMax = -1;
		Integer intStrCollaudoGgMax = -1;
		// PER OGNI VALORE CALCOLO MAX E SOMMA
		for (CronoProgrammaRecord tmp : list) {
			if (intStrAffidamentoLavoriGgMax < tmp.affidamentoLavoriGg()) {
				intStrAffidamentoLavoriGgMax = tmp.affidamentoLavoriGg();
			}
			if (intStrEsecuzioneLavoriGgMax < tmp.esecuzioneLavoriGg()) {
				intStrEsecuzioneLavoriGgMax = tmp.esecuzioneLavoriGg();
			}
			if (intStrProgettazioneGgMax < tmp.progettazioneGg()) {
				intStrProgettazioneGgMax = tmp.progettazioneGg();
			}
			if (intStrCollaudoGgMax < tmp.collaudoGg()) {
				intStrCollaudoGgMax = tmp.collaudoGg();
			}

//			intStrAffidamentoLavoriGgTot += tmp.affidamentoLavoriGg();
//			intStrEsecuzioneLavoriGgTot += tmp.esecuzioneLavoriGg();
//			intStrProgettazioneGgTot += tmp.progettazioneGg();
//			intStrCollaudoGgTot += tmp.collaudoGg();
		}
		if(list.size()==1) {
			checkEqualsGiorni(intStrAffidamentoLavoriGgMax, intervento.getAffidamentoLavoriGg(),CronoprogrammaEnum.AFFIDAMENTO_LAVORI_GG, errorList);
			checkEqualsGiorni(intStrEsecuzioneLavoriGgMax, intervento.getEsecuzioneLavoriGg(),CronoprogrammaEnum.ESECUZIONE_LAVORI_GG, errorList);
			checkEqualsGiorni(intStrProgettazioneGgMax, intervento.getProgettazioneGg(), CronoprogrammaEnum.PROGETTAZIONE_GG, errorList);
			checkEqualsGiorni(intStrCollaudoGgMax, intervento.getCollaudoGg(), CronoprogrammaEnum.COLLAUDO_GG, errorList);
		}else {
			checkMaxGiorni(intStrAffidamentoLavoriGgMax, intervento.getAffidamentoLavoriGg(),CronoprogrammaEnum.AFFIDAMENTO_LAVORI_GG, errorList);
			checkMaxGiorni(intStrEsecuzioneLavoriGgMax, intervento.getEsecuzioneLavoriGg(),CronoprogrammaEnum.ESECUZIONE_LAVORI_GG, errorList);
			checkMaxGiorni(intStrProgettazioneGgMax, intervento.getProgettazioneGg(), CronoprogrammaEnum.PROGETTAZIONE_GG, errorList);
			checkMaxGiorni(intStrCollaudoGgMax, intervento.getCollaudoGg(), CronoprogrammaEnum.COLLAUDO_GG, errorList);
		}

		if(errorList.size() > 0) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "Cronoprogramma non valido", 
					errorList, "");
		}
		
//		checkSommeGiorni(intStrAffidamentoLavoriGgTot, intervento.getAffidamentoLavoriGg(),CronoprogrammaEnum.AFFIDAMENTO_LAVORI_GG);
//		checkSommeGiorni(intStrEsecuzioneLavoriGgTot, intervento.getEsecuzioneLavoriGg(),CronoprogrammaEnum.ESECUZIONE_LAVORI_GG);
//		checkSommeGiorni(intStrProgettazioneGgTot, intervento.getProgettazioneGg(),CronoprogrammaEnum.PROGETTAZIONE_GG);
//		checkSommeGiorni(intStrCollaudoGgTot, intervento.getCollaudoGg(),CronoprogrammaEnum.COLLAUDO_GG);
	}

//	private void checkSommeGiorni(Integer giorniTotIntStr, Integer giorniInt,
//			CronoprogrammaEnum cronoProgrammaEnum) throws PgmeasException {
//		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
//		if (giorniTotIntStr < giorniInt) {
//			ErroreDettaglio err = new ErroreDettaglio();
//			err.setChiave(
//					"Totale giorni per " + cronoProgrammaEnum.getCode() + " inferiore al valore dell'intervento ");
//			err.setValore(giorniTotIntStr +"<"+ giorniInt);
//			errorList.add(err);
//			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST", errorList,
//					"Payload non valido per " + cronoProgrammaEnum.getCode());
//		}
//	}
	
	private void checkMaxGiorni(Integer maxGiorniIntStr, Integer giorniInt, CronoprogrammaEnum cronoProgrammaEnum, List<ErroreDettaglio> errorList)
			throws PgmeasException {
		if (maxGiorniIntStr.intValue() > giorniInt.intValue()) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(
					"Massimo inserito per " + cronoProgrammaEnum.getCode() + " superiore al valore dell'intervento ");
//			err.setValore(String.format(MES62, maxGiorniIntStr));
			err.setValore(String.format(MES62, cronoProgrammaEnum.getDescription(),cronoProgrammaEnum.getDescription()));
			
			errorList.add(err);
		}

	}
	
	private void checkEqualsGiorni(Integer maxGiorniIntStr, Integer giorniInt, CronoprogrammaEnum cronoProgrammaEnum, List<ErroreDettaglio> errorList)
			throws PgmeasException {
		if (maxGiorniIntStr.intValue() != giorniInt.intValue()) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(
					"Massimo inserito per " + cronoProgrammaEnum.getCode() + " diverso dal valore dell'intervento ");
//			err.setValore(String.format(MSG83, maxGiorniIntStr));
			err.setValore(String.format(MSG83, cronoProgrammaEnum.getDescription(),cronoProgrammaEnum.getDescription()));
			errorList.add(err);
		}

	}
	
	
	
	
	public record CronoProgrammaRecord( 
			Integer affidamentoLavoriGg,
		    Integer esecuzioneLavoriGg,
		    Integer progettazioneGg,
		    Integer collaudoGg
	) {

	}


}

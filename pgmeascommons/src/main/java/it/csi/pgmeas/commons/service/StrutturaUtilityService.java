/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import static it.csi.pgmeas.commons.validation.ValidationUtils.integerNullOrNotValidValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.stringNullOrEmptyValidator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pgmeas.commons.dto.EnteDto;
import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.dto.v2.StrutturaNewDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Struttura;
import it.csi.pgmeas.commons.repository.StrutturaRepository;
import it.csi.pgmeas.commons.util.enumeration.ErroreEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class StrutturaUtilityService {

	@Autowired
	StrutturaRepository strutturaRepository;
	
	public static Struttura buildStrutturaFromStrutturaNewDto(StrutturaNewDto strutturaNew, Timestamp now, 
			UserInfoRecord userInfo, Ente ente) {
		Struttura struttura = new Struttura();
		struttura.setDataCancellazione(null);
		struttura.setDataCreazione(now);
		struttura.setDataModifica(now);
		struttura.setEnte(ente);
		struttura.setEnteId(ente.getEnteId());
		struttura.setNote(strutturaNew.getNote());
		struttura.setStrBisCod(null);
		struttura.setStrCod(null);
		struttura.setStrComune(strutturaNew.getStrComune());
		struttura.setStrComuneIstatCod(null);
		struttura.setStrCoordinataX(null);
		struttura.setStrCoordinataY(null);
		struttura.setStrDatiCatastali(strutturaNew.getStrDatiCatastali());
		struttura.setStrDenominazione(strutturaNew.getStrDenominazione());
		struttura.setStrFimCod(null);
		struttura.setStrHsp11Cod(null);
		struttura.setStrId(null);
		struttura.setStrIdPadre(null);
		struttura.setStrIndirizzo(strutturaNew.getStrIndirizzo());
		struttura.setStrNonCensita(strutturaNew.getStrNonCensita());
		struttura.setStrNuova(strutturaNew.getStrNuova());
		struttura.setStrPgmeas(true);
		struttura.setUtenteCancellazione(null);
		struttura.setUtenteCreazione(userInfo.codiceFiscale());
		struttura.setUtenteModifica(userInfo.codiceFiscale());
		struttura.setValiditaInizio(now);
		struttura.setValiditaFine(null);
		return struttura;
	}
	
	public static void validateNewStruttura(StrutturaNewDto strutturaNew) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
		integerNullOrNotValidValidator("ente della struttura", strutturaNew.getEnteId(), listaErroriRilevati);
		stringNullOrEmptyValidator("comune della struttura", strutturaNew.getStrComune(), listaErroriRilevati);
		stringNullOrEmptyValidator("denominazione della struttura", strutturaNew.getStrDenominazione(), listaErroriRilevati);
		
		Boolean strNonCensita = strutturaNew.getStrNonCensita();
		Boolean strNuova = strutturaNew.getStrNuova();
		if((strNonCensita == null && strNuova == null) || (!strNonCensita && !strNuova ) || (strNonCensita && strNuova)) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(ErroreEnum.MSG_093.toString());
			err.setValore(ErroreEnum.MSG_093.toString() + ": Valorizzare struttura non censita o struttura nuova");
			listaErroriRilevati.add(err);
		}
		
		String datiCatastali = strutturaNew.getStrDatiCatastali();
		String indirizzo = strutturaNew.getStrIndirizzo();
		if((StringUtils.isBlank(datiCatastali) && StringUtils.isBlank(indirizzo)) || 
				(!StringUtils.isBlank(datiCatastali) && !StringUtils.isBlank(indirizzo))) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(ErroreEnum.MSG_092.toString());
			err.setValore(ErroreEnum.MSG_092.toString() + ": Valorizzare l'indirizzo oppure i dati catastali");
			listaErroriRilevati.add(err);
		}
		
		if (listaErroriRilevati.size() > 0) {
			String errore = ErroreEnum.MSG_090.toString() + ": Per la struttura non censita in ARPE valorizzare: "
	        		+ "la tipologia, la denominazione, il comune, l'indirizzo oppure i dati catastali";
	        ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, ErroreEnum.MSG_090.toString(), listaErroriRilevati,
	            errore);
	    }
	}
	
	@Transactional(readOnly = true)
	public List<StrutturaDto> getData(List<Struttura> listaStrutture, boolean loadEntiDetail) {
		return listaStrutture.stream().map(struttura -> {
			// Mappa la Struttura in StrutturaDto
			StrutturaDto strutturaDto = MappingUtils.copy(struttura, new StrutturaDto());

			// Mappa anche l'oggetto Ente associato in EnteDto
			if (loadEntiDetail && struttura.getEnte() != null) {
				EnteDto enteDto = MappingUtils.copy(struttura.getEnte(), new EnteDto());
				strutturaDto.setEnte(enteDto);
			}

			return strutturaDto;
		}).toList();
	}
	
	public Struttura getSrutturaById(Integer strId) {
		return strutturaRepository.findValidByStrId(strId);
	}
	
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto;
import it.csi.pgmeas.commons.dto.v2.InterventoStrutturaToSave;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.ClassificazioneTs;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.IntStrInterventoEdilizioTree;
import it.csi.pgmeas.commons.model.IntStrInterventoEdilizioTs;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.repository.ClassificazioneTreeRepository;
import it.csi.pgmeas.commons.repository.IntStrInterventoEdilizioTreeRepository;
import it.csi.pgmeas.commons.repository.IntStrInterventoEdilizioTsRepository;
import it.csi.pgmeas.commons.util.enumeration.PgmeasClassifTsEnum;
import it.csi.pgmeas.commons.util.intervento.InterventoUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

@Service
public class InterventoEdilizioUtilityService {

	@Autowired
	private ClassificazioneTsUtilityService classificazioneTsUtilityService;
	@Autowired
	private ClassificazioneTreeRepository classificazioneTreeRepository;
	@Autowired
	private IntStrInterventoEdilizioTreeRepository intStrInterventoEdilizioTreeRepository;
	@Autowired
	private IntStrInterventoEdilizioTsRepository intStrInterventoEdilizioTsRepository;

	public Map<Integer, Boolean> getInterventoEdilizio(Integer interventoStrutturaId, boolean copy, Integer enteId) {
		if(copy) {
			return getInterventoEdilizioFromCopy(interventoStrutturaId, enteId);
		}
		
		return getInterventoEdilizio(interventoStrutturaId, enteId);
	}
	
	public Map<Integer, Boolean> getInterventoEdilizio(Integer interventoStrutturaId, Integer enteId) {
		Map<Integer, Boolean> interventoEdilizioMap = new HashMap<Integer, Boolean>();

		List<IntStrInterventoEdilizioTree> interventoEdilizio = intStrInterventoEdilizioTreeRepository
				.findAllValidByIntStrIdAndEnteId(interventoStrutturaId, enteId);
		interventoEdilizio.stream().forEach(ie -> {
			if (ie.getIntstrIeTreeVisibile()) {
				interventoEdilizioMap.put(ie.getClassifTreeId(), ie.getIntstrIeTreeVisibile());
			}
		});
			
		return interventoEdilizioMap;
	}
	
	private Map<Integer, Boolean> getInterventoEdilizioFromCopy(Integer interventoStrutturaId, Integer enteId) {
		Map<Integer, Boolean> interventoEdilizioMap = new HashMap<Integer, Boolean>();

		IntStrInterventoEdilizioTs interventoEdilizioTs = intStrInterventoEdilizioTsRepository
				.findValidByIntstrIdAndEnteId(interventoStrutturaId, enteId);
		if(interventoEdilizioTs != null) {
			Long isValid = classificazioneTsUtilityService
				.countByClassificazioneTsId(interventoEdilizioTs.getClassifTsId());

			if (isValid >= 1) {
				return getInterventoEdilizio(interventoStrutturaId, enteId);
			}
		}
		
		List<ClassificazioneTreeByClassTsTipoDto> interventoEdilizio = classificazioneTreeRepository
				.findValidTreeByClassifTsTipoCod(PgmeasClassifTsEnum.INTERVENTO_EDILIZIO.getCode());
		interventoEdilizio.stream().forEach(ie -> {
			interventoEdilizioMap.put(ie.getClassifTreeId(), false);
		});

		return interventoEdilizioMap;
	}
	
	public void salvaInterventoEdilizio(UserInfoRecord userInfo, Timestamp now,
			Ente ente, InterventoStruttura interventoStrutturaSaved, InterventoStrutturaToSave from, Intervento interventoSaved) throws PgmeasException {
//		TIPO INTERVENTO EDILIZIO TS
		IntStrInterventoEdilizioTs intStrInterventoEdilizioTs;
			intStrInterventoEdilizioTs = makeIntStrIntervEdilTs(userInfo, now, ente,
					interventoStrutturaSaved.getIntStrId());
		var intStrInterventoEdilizioTsSaved = intStrInterventoEdilizioTsRepository
				.saveAndFlush(intStrInterventoEdilizioTs);
//		TIPO INTERVENTO EDILIZIO LISTA
		var intStrInterventoEdilizioList = InterventoUtils.buildintStrInterventoEdilizioList(userInfo, now, interventoSaved, from,
				intStrInterventoEdilizioTsSaved);
		intStrInterventoEdilizioTreeRepository.saveAll(intStrInterventoEdilizioList);
	}
	
	private IntStrInterventoEdilizioTs makeIntStrIntervEdilTs(UserInfoRecord userInfo, Timestamp now, Ente ente,
			Integer interventoStrutturaId) throws PgmeasException {
		ClassificazioneTs clasTipoIntEdil = classificazioneTsUtilityService
				.getClassificazioneTsByClassificazioneTipoCod(PgmeasClassifTsEnum.INTERVENTO_EDILIZIO);
		var intStrInterventoEdilizioTs = InterventoUtils.buildIntStrInterventoEdilizioTs(userInfo, now, ente, interventoStrutturaId,
				clasTipoIntEdil);
		return intStrInterventoEdilizioTs;
	}
	
	public void storicizzaInterventoEdilizio(UserInfoRecord userInfo, Timestamp now,
			InterventoStruttura interventoStrutturaOld, Integer enteId) {
		IntStrInterventoEdilizioTs intStrIntEdilTsOld =  intStrInterventoEdilizioTsRepository.findValidByIntstrIdAndEnteId(interventoStrutturaOld.getIntStrId(), enteId);
		intStrIntEdilTsOld.setValiditaFine(now);
		intStrIntEdilTsOld.setDataModifica(now);
		intStrIntEdilTsOld.setUtenteModifica(userInfo.codiceFiscale());
		
		List<IntStrInterventoEdilizioTree> interventoEdilizioTreeListOld =  intStrInterventoEdilizioTreeRepository.findAllValidByIntStrIdAndEnteId(interventoStrutturaOld.getIntStrId(), enteId);
		for (IntStrInterventoEdilizioTree interventoEdilizioTree : interventoEdilizioTreeListOld) {
			interventoEdilizioTree.setUtenteModifica(userInfo.codiceFiscale());
			interventoEdilizioTree.setDataModifica(now);
			interventoEdilizioTree.setValiditaFine(now);
		}
		
		intStrInterventoEdilizioTreeRepository.saveAll(interventoEdilizioTreeListOld);
		intStrInterventoEdilizioTsRepository.save(intStrIntEdilTsOld);
	}

}

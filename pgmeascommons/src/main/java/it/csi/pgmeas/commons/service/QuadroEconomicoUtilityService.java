/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.ClassificazioneTs;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.IntStrQuadroEconTree;
import it.csi.pgmeas.commons.model.IntStrQuadroEconTs;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.repository.ClassificazioneTreeRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaQuadroEconomicoTreeRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaQuadroEconomicoTsRepository;
import it.csi.pgmeas.commons.util.enumeration.PgmeasClassifTsEnum;
import it.csi.pgmeas.commons.util.intervento.InterventoUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

@Service
public class QuadroEconomicoUtilityService {

	@Autowired
	private ClassificazioneTsUtilityService classificazioneTsUtilityService;
	@Autowired
	private ClassificazioneTreeRepository classificazioneTreeRepository;
	@Autowired
	private InterventoStrutturaQuadroEconomicoTreeRepository interventoStrutturaQuadroEconomicoTreeRepository;
	@Autowired
	private InterventoStrutturaQuadroEconomicoTsRepository interventoStrutturaQuadroEconomicoTsRepository;

	public void salvaQuadroEconomico(UserInfoRecord userInfo, Timestamp now, Ente ente, Intervento interventoSaved,
			Map<Integer, BigDecimal> quadroEconomicoMap, Integer interventoStrutturaSavedId) throws PgmeasException {
		var intStrQuadroEconTs = makeIntStrQuadroEconomTs(userInfo, now, ente, interventoStrutturaSavedId);
		var IntStrQuadroEconTsSaved = interventoStrutturaQuadroEconomicoTsRepository.saveAndFlush(intStrQuadroEconTs); // TODO:
		var quadroEconList = InterventoUtils.buildIntStrQuadroEconomList(userInfo, now, interventoSaved, quadroEconomicoMap,
				IntStrQuadroEconTsSaved);
		interventoStrutturaQuadroEconomicoTreeRepository.saveAll(quadroEconList);
	}

	public void storicizzaQuadroEconomico(UserInfoRecord userInfo, Timestamp now,
			Integer interventoStrutturaOldId, Integer enteId) {
		IntStrQuadroEconTs intStrQuardoEconTsOld = interventoStrutturaQuadroEconomicoTsRepository
				.findValidByIntstrIdAndEnteId(interventoStrutturaOldId, enteId);
		intStrQuardoEconTsOld.setValiditaFine(now);
		intStrQuardoEconTsOld.setDataModifica(now);
		intStrQuardoEconTsOld.setUtenteModifica(userInfo.codiceFiscale());

		List<IntStrQuadroEconTree> intStrQuadroEconTreeList = interventoStrutturaQuadroEconomicoTreeRepository
				.findAllValidByIntStrIdAndEnteId(interventoStrutturaOldId, enteId);
		for (IntStrQuadroEconTree intStrQuadroEconTree : intStrQuadroEconTreeList) {
			intStrQuadroEconTree.setValiditaFine(now);
			intStrQuadroEconTree.setDataModifica(now);
			intStrQuadroEconTree.setUtenteModifica(userInfo.codiceFiscale());
		}

		interventoStrutturaQuadroEconomicoTreeRepository.saveAll(intStrQuadroEconTreeList);
		interventoStrutturaQuadroEconomicoTsRepository.save(intStrQuardoEconTsOld);
	}

	private IntStrQuadroEconTs makeIntStrQuadroEconomTs(UserInfoRecord userInfo, Timestamp now, Ente ente,
			Integer interventoStrutturaId) throws PgmeasException {
		ClassificazioneTs clasTipoQuadroEcon = classificazioneTsUtilityService
				.getClassificazioneTsByClassificazioneTipoCod(PgmeasClassifTsEnum.QUADRO_ECONOMICO);
		var intStrQuadroEconTs = InterventoUtils.buildIntStrQuadroEconomTs(userInfo, now, ente, interventoStrutturaId,
				clasTipoQuadroEcon);
		return intStrQuadroEconTs;
	}
	
	public Map<Integer, BigDecimal> getQuadroEconomico(Integer interventoStrutturaId, boolean copy, Integer enteId) {
		if(copy) {
			return getQuadroEconomicoFromCopy(interventoStrutturaId, enteId);
		}
		
		return getQuadroEconomico(interventoStrutturaId, enteId);
	}

	public Map<Integer, BigDecimal> getQuadroEconomico(Integer interventoStrutturaId, Integer enteId) {
		Map<Integer, BigDecimal> quadroEconomicoMap = new HashMap<Integer, BigDecimal>();

		List<IntStrQuadroEconTree> quadroEconomico = interventoStrutturaQuadroEconomicoTreeRepository
				.findAllValidByIntStrIdAndEnteId(interventoStrutturaId, enteId);
		quadroEconomico.stream().forEach(qe -> {
			if (qe.getIntstrQeTreeVisibile()) {
				quadroEconomicoMap.put(qe.getClassifTreeId(), qe.getIntstrQeTreeImporto());
			}
		});

		return quadroEconomicoMap;
	}
	
	private Map<Integer, BigDecimal> getQuadroEconomicoFromCopy(Integer interventoStrutturaId, Integer enteId) {
		Map<Integer, BigDecimal> quadroEconomicoMap = new HashMap<Integer, BigDecimal>();

		IntStrQuadroEconTs quadroEconomicoTs = interventoStrutturaQuadroEconomicoTsRepository
				.findValidByIntstrIdAndEnteId(interventoStrutturaId, enteId);
		if(quadroEconomicoTs != null) {
			Long isValid = classificazioneTsUtilityService
				.countByClassificazioneTsId(quadroEconomicoTs.getClassifTsId());

			if (isValid >= 1) {
				return getQuadroEconomico(interventoStrutturaId, enteId);
			}
		}
		
		List<ClassificazioneTreeByClassTsTipoDto> quadroEconomico = classificazioneTreeRepository
				.findValidTreeByClassifTsTipoCod(PgmeasClassifTsEnum.QUADRO_ECONOMICO.getCode());
		quadroEconomico.stream().forEach(qe -> {
			quadroEconomicoMap.put(qe.getClassifTreeId(), BigDecimal.ZERO);
		});

		return quadroEconomicoMap;
	}
}

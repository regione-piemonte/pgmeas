/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.util.service;
 
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.DichAppaltabilitaDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.ClassificazioneTs;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.IntStrDicAppaltabilitaTree;
import it.csi.pgmeas.commons.model.IntStrDicAppaltabilitaTs;
import it.csi.pgmeas.commons.repository.IntStrDicAppaltabilitaTreeRepository;
import it.csi.pgmeas.commons.repository.IntStrDicAppaltabilitaTsRepository;
import it.csi.pgmeas.commons.service.ClassificazioneTsUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.PgmeasClassifTsEnum;
import it.csi.pgmeas.commons.util.intervento.InterventoUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
 
@Service
public class DichiarazioneAppaltabilitaUtilityService {
 
	@Autowired
	private ClassificazioneTsUtilityService classificazioneTsUtilityService;
	@Autowired
	private IntStrDicAppaltabilitaTreeRepository intStrDicAppaltabilitaTreeRepository;
	@Autowired
	private IntStrDicAppaltabilitaTsRepository intStrDicAppaltabilitaTsRepository;
 
	// todo da rivedere
	public Map<Integer, DichAppaltabilitaDto> getDichiarazioneAppaltabilita(Integer interventoStrutturaId, Integer enteId) {
		Map<Integer, DichAppaltabilitaDto> dichiarazioneAppaltabilitaMap = new HashMap<Integer, DichAppaltabilitaDto>();
 
		List<IntStrDicAppaltabilitaTree> dichiarazioneAppaltabilita = intStrDicAppaltabilitaTreeRepository
				.findAllValidByIntStrIdAndEnteId(interventoStrutturaId, enteId);
		dichiarazioneAppaltabilita.stream().forEach(da -> {
			DichAppaltabilitaDto dichApp = new DichAppaltabilitaDto();
			dichApp.setIntstrDaTreeSelezionata(da.getIntstrDaTreeSelezionata());
			dichApp.setIntstrDaTreeValidazioneData(da.getIntstrDaTreeValidazioneData());
			dichApp.setAttoNumero(da.getAttoNumero());
			dichiarazioneAppaltabilitaMap.put(da.getClassifTreeId(), dichApp);
		});
 
		return dichiarazioneAppaltabilitaMap;
	}
 
	public void salvaDichiarazioneAppaltabilitaFromModuloTipoEnum(UserInfoRecord userInfo, Timestamp now, Ente ente,
			Map<Integer, DichAppaltabilitaDto> dicAppMap, Integer interventoStrutturaSavedId, ModuloTipoEnum moduloTipo)
			throws PgmeasException {
		var intStrDicAppaltabilitaTs = makeIntStrDicAppaltabilitaTs(userInfo, now, ente, interventoStrutturaSavedId,
				moduloTipo);
		var intStrDicAppaltabilitaTsSaved = intStrDicAppaltabilitaTsRepository.saveAndFlush(intStrDicAppaltabilitaTs);
		var dicAppaltabilitaList = InterventoUtils.buildIntStrDicAppaltabilitaList(userInfo, now, dicAppMap, ente,
				intStrDicAppaltabilitaTsSaved);
		intStrDicAppaltabilitaTreeRepository.saveAll(dicAppaltabilitaList);
	}
 
	public void storicizzaDichiarazioneAppaltabilita(UserInfoRecord userInfo, Timestamp now,
			Integer interventoStrutturaId, Integer enteId) {
		IntStrDicAppaltabilitaTs intStrDicAppaltabilitaTsOld = intStrDicAppaltabilitaTsRepository
				.findValidByIntstrIdAndEnteId(interventoStrutturaId, enteId);
		intStrDicAppaltabilitaTsOld.setValiditaFine(now);
		intStrDicAppaltabilitaTsOld.setDataModifica(now);
		intStrDicAppaltabilitaTsOld.setUtenteModifica(userInfo.codiceFiscale());
 
		List<IntStrDicAppaltabilitaTree> intStrDicAppaltabilitaTreeList = intStrDicAppaltabilitaTreeRepository
				.findAllValidByIntStrIdAndEnteId(interventoStrutturaId, enteId);
		for (IntStrDicAppaltabilitaTree intStrDicAppaltabilitaTree : intStrDicAppaltabilitaTreeList) {
			intStrDicAppaltabilitaTree.setValiditaFine(now);
			intStrDicAppaltabilitaTree.setDataModifica(now);
			intStrDicAppaltabilitaTree.setUtenteModifica(userInfo.codiceFiscale());
		}
 
		intStrDicAppaltabilitaTreeRepository.saveAll(intStrDicAppaltabilitaTreeList);
		intStrDicAppaltabilitaTsRepository.save(intStrDicAppaltabilitaTsOld);
	}
 
	private IntStrDicAppaltabilitaTs makeIntStrDicAppaltabilitaTs(UserInfoRecord userInfo, Timestamp now, Ente ente,
			Integer interventoStrutturaId, ModuloTipoEnum moduloTipo) throws PgmeasException {
 
		ClassificazioneTs clasTipoDicApp = classificazioneTsUtilityService
				.getClassificazioneTsByClassificazioneTipoCod(PgmeasClassifTsEnum.DICHIARAZIONE_APPALTABILITA);
		if (moduloTipo == ModuloTipoEnum.MODULO_A_A) {
			clasTipoDicApp = classificazioneTsUtilityService.getClassificazioneTsByClassificazioneTipoCod(
					PgmeasClassifTsEnum.DICHIARAZIONE_APPALTABILITA_ATTREZZATURE);
		}
		IntStrDicAppaltabilitaTs intStrDicAppaltabilitaTs = InterventoUtils.buildIntStrDicAppaltabilitaTs(userInfo, now,
				ente, interventoStrutturaId, clasTipoDicApp);
		return intStrDicAppaltabilitaTs;
	}
}

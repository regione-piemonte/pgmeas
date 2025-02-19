/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.v2.InterventoToPutByRegioneModel;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoPrevisioneSpesa;
import it.csi.pgmeas.commons.repository.InterventoPrevisioneSpesaRepository;
import it.csi.pgmeas.commons.util.intervento.InterventoUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

/**
 * Service per esporre i metodi di finanziamento usati sia su interventov2 che sui moduli
 */
@Service
public class PrevisioniDiSpesaUtilityService {
	@Autowired
	private InterventoPrevisioneSpesaRepository interventoPrevisioneSpesaRepository;
	
	public void storicizzaPrevisioneSpesa(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<InterventoPrevisioneSpesa> interventoPrevisioneSpesaListOld = interventoPrevisioneSpesaRepository.findAllValidByIntIdAndEnteId(interventoId, enteId);
		for (InterventoPrevisioneSpesa interventoPrevisioneSpesa : interventoPrevisioneSpesaListOld) {
			interventoPrevisioneSpesa.setValiditaFine(now);
			interventoPrevisioneSpesa.setDataModifica(now);
			interventoPrevisioneSpesa.setUtenteModifica(userInfo.codiceFiscale());
		}
		interventoPrevisioneSpesaRepository.saveAll(interventoPrevisioneSpesaListOld);
	}
	
	public void salvaPrevisioneSpesa(InterventoToPutByRegioneModel body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<InterventoPrevisioneSpesa> previsioniSpesaListNew  = InterventoUtils.buildPrevisioniSpesaFromDto(body.getPrevisioniDiSpesa(),
				userInfo, now, interventoSaved);
		interventoPrevisioneSpesaRepository.saveAll(previsioniSpesaListNew);
	}
	
	public List<InterventoPrevisioneSpesa> getInterventoPrevisioneSpesaListByInterventoId(Integer interventoId, Integer enteId) {
		 return interventoPrevisioneSpesaRepository.findAllValidByIntIdAndEnteId(interventoId, enteId);
	}
	
}

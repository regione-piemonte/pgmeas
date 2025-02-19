/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.buildFinanziamentoFromDto;
import static it.csi.pgmeas.commons.util.intervento.InterventoUtils.buildRFinanziamentoImportoFromDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.v2.PianoFinanziarioSaveDto;
import it.csi.pgmeas.commons.model.Finanziamento;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.RFinanziamentoImporto;
import it.csi.pgmeas.commons.repository.FinanziamentoRepository;
import it.csi.pgmeas.commons.repository.RFinanziamentoImportoRepository;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

/**
 * Service per esporre i metodi di finanziamento usati sia su interventov2 che sui moduli
 */
@Service
public class FinanziamentoUtilityService {
	@Autowired
	private FinanziamentoRepository finanziamentoRepository;
	@Autowired
	private RFinanziamentoImportoRepository rFinanziamentoImportoRepository;
	
	public void storicizzaFinanziamento(Integer interventoId, Integer enteId, UserInfoRecord userInfo, Timestamp now, List<PianoFinanziarioSaveDto> finanziamentiDto) {
		List<Finanziamento> finanziamentoListOld = finanziamentoRepository.findAllByIntIdAndEnteId(interventoId, enteId);
		
		Set<Integer> idSetListaDto = finanziamentiDto.stream()
                .map(PianoFinanziarioSaveDto::getFinanziamentoId) 
                .collect(Collectors.toSet());
		
		List<Finanziamento> differenza = finanziamentoListOld.stream()
              .filter(f -> !idSetListaDto.contains(f.getFinId()))
              .collect(Collectors.toList());

		for (Finanziamento finanziamento : differenza) {
			finanziamento.setDataCancellazione(now);
			finanziamento.setUtenteCancellazione("STORICIZZAZIONE DA PARTE DI " + userInfo.codiceFiscale());
			
			List<RFinanziamentoImporto> finanziamentoImportiOld = 
					rFinanziamentoImportoRepository.findAllValidByFinIdAndEnteId(finanziamento.getFinId(), finanziamento.getEnteId());
			for (RFinanziamentoImporto finanziamentoImporto : finanziamentoImportiOld) {
				finanziamentoImporto.setValiditaFine(now);
				finanziamentoImporto.setDataModifica(now);
				finanziamentoImporto.setUtenteModifica(userInfo.codiceFiscale());
			}
			rFinanziamentoImportoRepository.saveAll(finanziamentoImportiOld);
		}
		
		finanziamentoRepository.saveAll(finanziamentoListOld);
	}
	
	public void salvaFinanziamento(List<PianoFinanziarioSaveDto> pianiFinanziari, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<PianoFinanziarioSaveDto> finanziamentoListNew = pianiFinanziari.stream().filter(pf -> pf.getFinanziamentoId() == null).collect(Collectors.toList());
 		finanziamentoListNew.stream().forEach(f -> {
 			Finanziamento finanziamento = buildFinanziamentoFromDto(f,
 					userInfo, now, interventoSaved);
 			finanziamento = finanziamentoRepository.save(finanziamento);
 			
			List<RFinanziamentoImporto> rFinanziamentoImporto = buildRFinanziamentoImportoFromDto(f.getFinanziamentoImportoTipo(),
					userInfo, now, finanziamento);
			rFinanziamentoImportoRepository.saveAll(rFinanziamentoImporto);
 		});
	}
	
	public List<Map<String, Object>> getPianoFinanziarioByIntIdAndEnteId(Integer intId, Integer enteId){
		return finanziamentoRepository.findPianoFinanziarioByIntIdAndEnteId(intId, enteId);
	}
	
}

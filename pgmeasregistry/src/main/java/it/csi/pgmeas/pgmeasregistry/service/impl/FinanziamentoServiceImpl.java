/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.v2.FinanziamentoDettaglioTipoDto;
import it.csi.pgmeas.commons.dto.v2.FinanziamentoImportoTipoDecodificaDto;
import it.csi.pgmeas.commons.dto.v2.FinanziamentoTipoDto;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.repository.FinanziamentoRepository;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.pgmeasregistry.service.FinanziamentoService;

@Service
public class FinanziamentoServiceImpl implements FinanziamentoService {
	@Autowired
	FinanziamentoRepository finanziamentoRepository;
	
	@Autowired
	EnteUtilityService enteUtilityService;
	
	@Autowired
	InterventoUtilityService interventoUtilityService;

	@Override
	public List<FinanziamentoTipoDto> getDataByInterventoId(Integer intId) throws CustomLoginException, PgmeasException { 
		Intervento intervento = interventoUtilityService.getInterventoById(intId);
		List<Map<String, Object>> finanziamentiDb = finanziamentoRepository.findFinanziamentiByEnteId(intervento.getEnteId());
		List<FinanziamentoTipoDto> finanziamenti = buildFinanziamenti(finanziamentiDb);
		return finanziamenti;
	}
	
	private List<FinanziamentoTipoDto> buildFinanziamenti(List<Map<String, Object>> finanziamentiObj) {
		List<FinanziamentoTipoDto> finanziamentiTipo = new ArrayList<FinanziamentoTipoDto>();
		
		finanziamentiObj.stream().forEach(finDb -> {
			
			Optional<FinanziamentoTipoDto> finTipoOptional = finanziamentiTipo.stream()
					.filter(finTipo -> finTipo.getTipologiaId().equals(finDb.get("tipologiaId"))).findFirst();
			if(finTipoOptional.isPresent()) {
				FinanziamentoTipoDto finanziamentoTipo = finTipoOptional.get();
				buildFinanziamentoDettaglioTipoList(finDb, finanziamentoTipo);
			} else {
				FinanziamentoTipoDto finanziamentoTipo = buildFinanziamentoTipo(finDb);
				buildFinanziamentoDettaglioTipoList(finDb, finanziamentoTipo);
				finanziamentiTipo.add(finanziamentoTipo);
			}
		});
		
		return finanziamentiTipo;
	}
	
	private FinanziamentoTipoDto buildFinanziamentoTipo(Map<String, Object> finanziamentoObj) {
		FinanziamentoTipoDto finanziamentoTipo = new FinanziamentoTipoDto();
		finanziamentoTipo.setTipologiaId(Integer.parseInt(finanziamentoObj.get("tipologiaId").toString()));
		finanziamentoTipo.setTipologiaCod(finanziamentoObj.get("tipologiaCod").toString());
		finanziamentoTipo.setTipologiaDesc(finanziamentoObj.get("tipologiaDesc").toString());
		finanziamentoTipo.setFinanziamentoTipiDettaglio(new ArrayList<FinanziamentoDettaglioTipoDto>());
		return finanziamentoTipo;
	}
	
	private void buildFinanziamentoDettaglioTipoList(Map<String, Object> finanziamentoObj, FinanziamentoTipoDto finanziamentoTipo) {
		Optional<FinanziamentoDettaglioTipoDto> finDettaglioTipoOptional = finanziamentoTipo.getFinanziamentoTipiDettaglio().stream()
				.filter(finDettTipo -> finDettTipo.getTipologiaDettaglioId().equals(finanziamentoObj.get("tipologiaDettaglioId"))).findFirst();
		if(finDettaglioTipoOptional.isPresent()) {
			FinanziamentoDettaglioTipoDto finanziamentoDettaglioTipo = finDettaglioTipoOptional.get();
			finanziamentoDettaglioTipo.getFinanziamentoImportiTipo().add(buildFinanziamentoImportiTipo(finanziamentoObj));
		} else {
			FinanziamentoDettaglioTipoDto finanziamentoDettaglioTipo = buildFinanziamentoDettaglioTipo(finanziamentoObj);
			finanziamentoDettaglioTipo.getFinanziamentoImportiTipo().add(buildFinanziamentoImportiTipo(finanziamentoObj));
			finanziamentoTipo.getFinanziamentoTipiDettaglio().add(finanziamentoDettaglioTipo);
		}
	}
	
	private FinanziamentoDettaglioTipoDto buildFinanziamentoDettaglioTipo(Map<String, Object> finanziamentoObj) {
		FinanziamentoDettaglioTipoDto finanziamentoTipoDettaglio = new FinanziamentoDettaglioTipoDto();
		finanziamentoTipoDettaglio.setTipologiaDettaglioId(Integer.parseInt(finanziamentoObj.get("tipologiaDettaglioId").toString()));
		finanziamentoTipoDettaglio.setTipologiaDettaglioCod(finanziamentoObj.get("tipologiaDettaglioCod").toString());
		finanziamentoTipoDettaglio.setTipologiaDettaglioDesc(finanziamentoObj.get("tipologiaDettaglioDesc").toString());
		finanziamentoTipoDettaglio.setFinanziamentoImportiTipo(new ArrayList<FinanziamentoImportoTipoDecodificaDto>());
		return finanziamentoTipoDettaglio;
	}
	
	private FinanziamentoImportoTipoDecodificaDto buildFinanziamentoImportiTipo(Map<String, Object> finanziamentoObj) {
		FinanziamentoImportoTipoDecodificaDto finanziamentoImportoTipo = new FinanziamentoImportoTipoDecodificaDto();
		finanziamentoImportoTipo.setFinanziamentoImportoTipoId(Integer.parseInt(finanziamentoObj.get("finanziamentoImportoTipoId").toString()));
		finanziamentoImportoTipo.setFinanziamentoImportoTipoCod(finanziamentoObj.get("finanziamentoImportoTipoCod").toString());
		finanziamentoImportoTipo.setFinanziamentoImportoTipoDesc(finanziamentoObj.get("finanziamentoImportoTipoDesc").toString());
		finanziamentoImportoTipo.setImportoTipoDetId(Integer.parseInt(finanziamentoObj.get("importoTipoDetId").toString()));
		Integer percentualeImporto = finanziamentoObj.get("finanziamentoPercentualeImporto") == null ? null :
			Integer.parseInt(finanziamentoObj.get("finanziamentoPercentualeImporto").toString());
		finanziamentoImportoTipo.setFinanziamentoPercentuale(percentualeImporto);
		return finanziamentoImportoTipo;
	}
}

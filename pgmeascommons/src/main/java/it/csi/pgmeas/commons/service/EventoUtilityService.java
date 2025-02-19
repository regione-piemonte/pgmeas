/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.EventoDecodedDto;
import it.csi.pgmeas.commons.dto.EventoTipoDecodedDto;
import it.csi.pgmeas.commons.dto.TemplateDecodedDto;
import it.csi.pgmeas.commons.exception.PgmeasRuntimeException;
import it.csi.pgmeas.commons.model.Evento;
import it.csi.pgmeas.commons.model.EventoTipo;
import it.csi.pgmeas.commons.repository.EventoRepository;
import it.csi.pgmeas.commons.repository.EventoTipoRepository;
import it.csi.pgmeas.commons.repository.TemplateRepository;
import it.csi.pgmeas.commons.util.enumeration.EventoTipoEnum;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class EventoUtilityService {

	@Autowired
	private EventoTipoRepository eventoTipoRepository;

	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private TemplateRepository templateRepository;
	

	public void inserisciEventoNotifica(EventoTipoEnum eventoTipoEnum, Integer enteId, Timestamp eventoData,
			Integer eventoTabellaId, String cfOperatore) throws PgmeasRuntimeException {

		EventoTipo eventoTipo = getEventoTipoByEventoTipoEnum(eventoTipoEnum);

		Evento evento = buildEvento(enteId, eventoData, eventoTabellaId, cfOperatore, eventoTipo);

		eventoRepository.save(evento);
	}

	private Evento buildEvento(Integer enteId, Timestamp eventoData, Integer eventoTabellaId, String cfOperatore,
			EventoTipo eventoTipo) {		
		
		Evento evento = new Evento();
		evento.setEnteId(enteId);
		evento.setEventoData(eventoData);
		//CORREZIONE SONARQUBE
		if(eventoTipo != null) {
			if(eventoTipo.getEventoTipoId() != null) {
				evento.setEventoTipoId(eventoTipo.getEventoTipoId());
			}else {
				evento.setEventoTipoId(null);
			}
		}
		evento.setEventoTabellaId(eventoTabellaId);
		evento.setDataCreazione(eventoData);
		evento.setDataModifica(eventoData);
		evento.setUtenteCreazione(cfOperatore);
		evento.setUtenteModifica(cfOperatore);
		return evento;
	}

	private EventoTipo getEventoTipoByEventoTipoEnum(EventoTipoEnum eventoTipoEnum) throws PgmeasRuntimeException{
		List<EventoTipo> eventiTipo = eventoTipoRepository.findAllValidByEventoTipoCod(eventoTipoEnum.getCode());
		if (eventiTipo != null && eventiTipo.size() > 0) {
			return eventiTipo.get(0);
		} else {
			ValidationUtils.generatePgmeasRuntimeException(HttpStatus.NOT_FOUND, "Codice eventoTipo non trovato",
					new ArrayList<ErroreDettaglio>(), "Codice eventoTipo non trovato :" + eventoTipoEnum.getCode());
		}
		return null;
	}
	
//	public List<Evento> getEventiNonNotificatiByEventoTipo(List<EventoTipoEnum> eventoTipoEnum){
//		List<String>eventoTipoList=eventoTipoEnum.stream().map(EventoTipoEnum :: getCode).toList();
//		List<Evento> eventiList = eventoRepository.findAllByEventoTipoListAndNotificaIdNull(eventoTipoList);
//		return eventiList;
//	}
//	
//	public LinkedHashMap<Integer, List<Evento>>getEventiNonNotificatiByEventoTipoGroupByEnteId(List<EventoTipoEnum> eventoTipoEnum){
//		List<String>eventoTipoList=eventoTipoEnum.stream().map(EventoTipoEnum :: getCode).toList();
//		 return eventoRepository.findAllByEventoTipoListAndNotificaIdNull(eventoTipoList).stream()
//                 .collect(Collectors.groupingBy(
//                     Evento::getEnteId,
//                     LinkedHashMap::new,
//                     Collectors.toList()
//                 ));
//	}
	
	public List<EventoDecodedDto> findEventiDaNotificareValidi(){
		return eventoRepository.findEventiDaNotificareValidi();
	}
	
	public List<EventoTipoDecodedDto>findAllEventoTipoDecodedValidi(){
		List<EventoTipoDecodedDto>result= eventoTipoRepository.findAllEventoTipoDecodedValid();
		result.stream().forEach((ev)->{
			List<TemplateDecodedDto> templateList=templateRepository.findAllEventoTipoDecodedValidByEventoTipoId(ev.getEventoTipoId());
			ev.setTemplateList(templateList);
		});
	return result;
	}
}

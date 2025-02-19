/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.dto.EventoDecodedDto;
import it.csi.pgmeas.commons.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

	@Query("select a from Evento a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.notificaId is null ")
	List<Evento> findAllAndNotificaIdNull();
	
//	@Query("select a from Evento e "
//			+ "JOIN EventoTipo et on et.eventoTipoId =e.eventoTipoId "
//			+ "where (e.dataCancellazione is null or e.dataCancellazione >= current_timestamp) "
//			+ "(et.dataCancellazione is null or et.dataCancellazione >= current_timestamp) "
//			+ "and et.validitaInizio<=current_timestamp "
//			+ "and (et.validitaFine is null or et.validitaFine>=current_timestamp)"
//			+ "and e.notificaId is null"
//			+ "amd et.eventoTipoCod IN (:tipoList)")
//	List<Evento> findAllByEventoTipoListAndNotificaIdNull(@Param("tipoList")List<String> eventoTipoList);
	
	
	@Query(""" 
			SELECT new it.csi.pgmeas.commons.dto.EventoDecodedDto( 
			   e.eventoId as eventoId, 
			   e.enteId as enteId, 
			   e.eventoData as eventoData, 
			   e.eventoTipoId as eventoTipoId, 
			   et.eventoTipoCod as eventoTipoCod, 
			   e.eventoTabellaId as eventoTabellaId, 
			   e.notificaId as notificaId) 
			   from Evento e 
			   JOIN EventoTipo et on et.eventoTipoId =e.eventoTipoId 
			   where (e.dataCancellazione is null or e.dataCancellazione >= current_timestamp) 
			   and (et.dataCancellazione is null or et.dataCancellazione >= current_timestamp) 
			   and et.validitaInizio<=current_timestamp 
			   and (et.validitaFine is null or et.validitaFine>=current_timestamp)
			   and e.notificaId is null
			""")
	List<EventoDecodedDto> findEventiDaNotificareValidi();
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.dto.EventoTipoDecodedDto;
import it.csi.pgmeas.commons.model.EventoTipo;

@Repository
public interface EventoTipoRepository extends JpaRepository<EventoTipo, Integer> {
	@Query("select a from EventoTipo a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine>=current_timestamp) "
			+ "order by a.eventoTipoDesc")
	List<EventoTipo> findAllValid();
	
	@Query("select a from EventoTipo a "
			+ "where a.dataCancellazione is null "
			+ "and a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine>=current_timestamp) "
			+ "and a.eventoTipoCod=:tipoCod "
			+ "order by a.eventoTipoDesc")
	List<EventoTipo> findAllValidByEventoTipoCod(@Param("tipoCod") String tipoCod);
	
	
    @Query("""
            SELECT new it.csi.pgmeas.commons.dto.EventoTipoDecodedDto(
                et.eventoTipoId as eventoTipoId,
                et.eventoTipoCod as eventoTipoCod, 
                et.eventoTipoDesc as eventoTipoDesc, 
                et.eventoMaxNumeroRetryNotifica as eventoMaxNumeroRetryNotifica, 
                et.eventoMaxGgRetryNotifica as eventoMaxGgRetryNotifica, 
                eg.eventoGruppoId as eventoGruppoId, 
                eg.eventoGruppoCod as eventoGruppoCod,
                eg.eventoGruppoDesc as eventoGruppoDesc
            )
            FROM EventoTipo et
            JOIN EventoGruppo eg ON et.eventoGruppoId =eg.eventoGruppoId
            where 
             et.dataCancellazione is null 
			 and et.validitaInizio<=current_timestamp 
			 and (et.validitaFine is null or et.validitaFine>=current_timestamp)
			 and eg.dataCancellazione is null
			 and eg.validitaInizio<=current_timestamp 
			 and (eg.validitaFine is null or eg.validitaFine>=current_timestamp)
        """)
        List<EventoTipoDecodedDto> findAllEventoTipoDecodedValid();
    
}

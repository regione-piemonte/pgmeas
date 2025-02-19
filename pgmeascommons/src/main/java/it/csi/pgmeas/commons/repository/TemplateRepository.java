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

import it.csi.pgmeas.commons.dto.TemplateDecodedDto;
import it.csi.pgmeas.commons.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
	
	
    @Query("""
            SELECT new it.csi.pgmeas.commons.dto.TemplateDecodedDto(
                t.templateId as templateId,
                t.templateCod as templateCod,
                t.templateDesc as templateDesc,
                t.templateOggetto as templateOggetto,
                t.templateCorpo as templateCorpo,
                tt.templateTipoCod as templateTipoCod
            )
            FROM Template t
            JOIN TemplateTipo tt ON tt.templateTipoId =t.templateTipoId
            where 
             t.dataCancellazione is null 
			 and t.validitaInizio<=current_timestamp 
			 and (t.validitaFine is null or t.validitaFine>=current_timestamp)
			 
			 and tt.validitaInizio<=current_timestamp 
			 and (tt.validitaFine is null or tt.validitaFine>=current_timestamp)
			 and t.eventoTipoId=:eventoTipoId
        """)
        List<TemplateDecodedDto> findAllEventoTipoDecodedValidByEventoTipoId(@Param("eventoTipoId") Integer eventoTipoId);
    
}

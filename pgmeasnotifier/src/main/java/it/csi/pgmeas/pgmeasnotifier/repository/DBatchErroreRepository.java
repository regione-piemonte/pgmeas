/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.pgmeasnotifier.model.DBatchErrore;

@Repository
public interface DBatchErroreRepository extends JpaRepository< DBatchErrore, Integer> {

//	and date_trunc('date',a.batchErrData) = current_date 
	
	
	@Query(""" 
			select a from DBatchErrore a 
			where a.dataCancellazione is null 
			and a.batchErrData > CURRENT_TIMESTAMP - 1 day
			and a.errCategoriaId =:idcategoria
			and a.batchErrMessaggio=:message 
			and a.batchErrDovutoaTabId =:idCausa 
			and a.batchErrDovutoaTabNome =:tabella 
			""" )
	List<DBatchErrore> findCurrendDayByParameters(@Param("message")String message,
			@Param("idcategoria")Integer idcategoria, 
			@Param("tabella") String tabella, 
			@Param("idCausa") Long idCausa);

	
	
	@Query(""" 
			select a from DBatchErrore a 
			where a.dataCancellazione is null 
			and a.batchErrData > CURRENT_TIMESTAMP - 1 day
			and a.errCategoriaId =:idcategoria
			and a.batchErrMessaggio=:message 
			""" )
	List<DBatchErrore> findCurrendDayByParameters(@Param("message")String message,
			@Param("idcategoria")Integer idcategoria);

	
	
}

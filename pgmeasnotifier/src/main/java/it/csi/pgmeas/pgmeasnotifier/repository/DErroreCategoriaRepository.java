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

import it.csi.pgmeas.pgmeasnotifier.model.DErroreCategoria;

@Repository
public interface DErroreCategoriaRepository extends JpaRepository<DErroreCategoria, Integer> {

	
	@Query("select a from DErroreCategoria a "
			+ "where a.dataCancellazione is null "
			+ "and a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine>=current_timestamp) "
			+ "and a.errCategoriaCod=:errCategoriaCod")
	List<DErroreCategoria> findAllValidByErroreCategoriaCod(@Param("errCategoriaCod") String errCategoriaCod);
	
	
	@Query(""" 
			select a from DErroreCategoria a 
			where a.dataCancellazione is null 
			and a.validitaInizio<=current_timestamp 
			and (a.validitaFine is null or a.validitaFine>=current_timestamp)
			""")
	List<DErroreCategoria> findAllValid();
}

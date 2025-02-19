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

import it.csi.pgmeas.commons.model.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

	@Query("SELECT a from Modulo a "
			+ "where a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine>=current_timestamp or a.validitaFine is null) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp)"
			+ "order by a.moduloDesc")
	List<Modulo> findAllValid();
	
	@Query("select a from Modulo a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.moduloCod=:moduloCod "
			+ "order by a.moduloDesc")
	List<Modulo> findAllByModuloCod(@Param("moduloCod") String moduloCod);
	
	@Query("select a from Modulo a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.moduloId=:moduloId")
	Modulo findByModuloId(@Param("moduloId") Integer moduloId);

}

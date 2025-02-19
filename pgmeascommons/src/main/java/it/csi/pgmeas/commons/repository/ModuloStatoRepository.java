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

import it.csi.pgmeas.commons.model.ModuloStato;

@Repository
public interface ModuloStatoRepository extends JpaRepository<ModuloStato, Integer> {

	@Query("SELECT a from ModuloStato a "
			+ "where a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine>=current_timestamp or a.validitaFine is null) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp)"
			+ "order by a.moduloStatoDesc")
	List<ModuloStato> findAllValid();
	
	@Query("select a from ModuloStato a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.moduloStatoCod = :moduloStatoCod "
			+ "order by a.moduloStatoDesc")
	List<ModuloStato> findAllByModuloStatoCod(@Param("moduloStatoCod") String moduloStatoCod);
	
	@Query("select a from ModuloStato a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and a.moduloStatoId = :moduloStatoId")
	ModuloStato findByModuloStatoId(@Param("moduloStatoId") Integer moduloStatoId);
}

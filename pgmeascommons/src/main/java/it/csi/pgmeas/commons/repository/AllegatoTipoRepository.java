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

import it.csi.pgmeas.commons.model.AllegatoTipo;

@Repository
public interface AllegatoTipoRepository extends JpaRepository<AllegatoTipo, Integer> {
	@Query("select a from AllegatoTipo a "
			+ "where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine>=current_timestamp) "
			+ "order by a.allegatoTipoDesc")
	List<AllegatoTipo> findAllValid();
	
	@Query("select a from AllegatoTipo a "
			+ "where a.dataCancellazione is null "
			+ "and a.validitaInizio<=current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine>=current_timestamp) "
			+ "and a.allegatoTipoCod=:tipoCod "
			+ "order by a.allegatoTipoDesc")
	List<AllegatoTipo> findAllValidByAllegatoTipoCod(@Param("tipoCod") String tipoCod);
}

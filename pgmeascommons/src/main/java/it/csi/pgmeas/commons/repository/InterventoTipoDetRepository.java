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

import it.csi.pgmeas.commons.model.InterventoTipoDet;

@Repository
public interface InterventoTipoDetRepository extends JpaRepository<InterventoTipoDet, Integer> {
	@Query("select a from InterventoTipoDet a where a.dataCancellazione is null or a.dataCancellazione > current_timestamp order by a.intTipoDetDesc")
	List<InterventoTipoDet> findAllSenzaCancellati();
	
	@Query("select a from InterventoTipoDet a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "order by a.intTipoDetDesc")
	List<InterventoTipoDet> findAllValid();
	
	@Query("select a from InterventoTipoDet a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp)"
			+ "and a.interventoTipo.intTipoCod = :intTipoCod "
			+ "order by a.intTipoDetDesc")
	List<InterventoTipoDet> findAllValidByIntTipoCod(@Param("intTipoCod") String intTipoCod);
}

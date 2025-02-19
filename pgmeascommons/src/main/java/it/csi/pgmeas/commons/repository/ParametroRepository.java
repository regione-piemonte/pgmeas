/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.Parametro;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {
	@Query("select a from Parametro a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "order by a.parametroDesc")
	List<Parametro> findAllValid();
	
	@Query("select a from Parametro a "
			+ "join ParametroTipo pt on a.parametroTipoId = pt.parametroTipoId "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and (pt.dataCancellazione is null or pt.dataCancellazione >= current_timestamp) "
			+ "and pt.parametroTipoCod = 'ERRORE' "
			+ "order by a.parametroDesc")
	List<Parametro> findAllErroriValid();
	
	
	@Query("select a from Parametro a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and parametroCod= :parametroCod")
	Optional<Parametro> findAllValidByCod(@Param("parametroCod")String parametroCod);
}

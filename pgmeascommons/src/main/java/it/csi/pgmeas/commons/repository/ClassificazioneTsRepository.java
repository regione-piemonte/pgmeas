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

import it.csi.pgmeas.commons.model.ClassificazioneTs;

@Repository
public interface ClassificazioneTsRepository extends JpaRepository<ClassificazioneTs, Integer> {
	@Query(value = "select pdct.* "
			+ "from pgmeas_d_classif_ts pdct "
			+ "join pgmeas_d_classif_ts_tipo pdctt on pdctt.classif_ts_tipo_id=pdct.classif_ts_tipo_id "
			+ "where (current_timestamp<=pdct.validita_fine or pdct.validita_fine is null) "
			+ "and current_timestamp>=pdct.validita_inizio "
			+ "and pdct.data_cancellazione is null and pdctt.data_cancellazione is null "
			+ "and pdctt.classif_ts_tipo_cod= :tipoCod "
			+ "order by pdct.classif_ts_desc",nativeQuery = true)
	List<ClassificazioneTs> findAllValidByClassificazioneTsTipoCod(@Param("tipoCod") String tipoCod);
	
	@Query("select count(1) from ClassificazioneTs pdct "
			+ " where (pdct.validitaFine >= current_timestamp or pdct.validitaFine is null) "
			+ " and pdct.validitaInizio <= current_timestamp "
			+ " and (pdct.dataCancellazione is null or pdct.dataCancellazione >= current_timestamp)"
			+ " and pdct.classifTsId = :classifTsId")
	Optional<Long> countByClassificazioneTsId(@Param("classifTsId") Integer classifTsId);
}

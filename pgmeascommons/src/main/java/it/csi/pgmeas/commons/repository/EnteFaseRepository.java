/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.EnteFase;

@Repository
public interface EnteFaseRepository extends JpaRepository<EnteFase, Long> {

	@Query("SELECT COUNT(ef) FROM EnteFase ef " + 
			"JOIN ef.ente e " + 
			"JOIN ef.fase f " + 
			"WHERE ef.validitaFine IS NULL " + 
			"AND ef.dataCancellazione IS NULL " + 
			"AND YEAR(ef.faseInizio) = :anno " + 
			"AND f.faseCod = :codFase " + 
			"AND ef.faseProrogaFine IS NOT NULL") 
	Optional<Long> countByFaseAndAnnoWithProroga(@Param("codFase") String codFase, @Param("anno") int anno);

	@Query("SELECT ef FROM EnteFase ef " + 
			"JOIN ef.ente e " + 
			"JOIN ef.fase f " + 
			"WHERE e.enteCodEsteso = :enteCod " + 
			"AND ef.validitaFine IS NULL " + 
			"AND ef.dataCancellazione IS NULL " + 
			"AND YEAR(ef.faseInizio) = :anno " + 
			"AND f.faseCod = :codFase") 
	Optional<EnteFase> findByEnteCodAndFaseAndAnno(@Param("enteCod") String enteCod, @Param("codFase") String codFase,
			@Param("anno") int anno);

	@Query("SELECT ef FROM EnteFase ef " + 
			"JOIN ef.ente e " + 
			"JOIN ef.fase f " + 
			"WHERE ef.validitaFine IS NULL " + 
			"AND ef.dataCancellazione IS NULL " + 
			"AND YEAR(ef.faseInizio) = :anno " + 
			"AND f.faseCod = :codFase") 
	List<EnteFase> findByFaseAndAnno(@Param("codFase") String codFase, @Param("anno") int anno);

	@Query("SELECT ef FROM EnteFase ef " + 
			"JOIN ef.ente e " + 
			"JOIN ef.fase f " + 
			"WHERE ef.validitaFine IS NULL " + 
			"AND ef.dataCancellazione IS NULL " + 
			"AND f.faseCod = :codFase") 
	List<EnteFase> findByFase(@Param("codFase") String codFase);
	
	@Modifying
	@Query("UPDATE EnteFase ef " 
			+ "SET ef.validitaFine = CURRENT_TIMESTAMP, " 
			+ "ef.utenteModifica = :utenteModifica "
			+ "WHERE ef.validitaFine IS NULL "
			+ "AND ef.dataCancellazione IS NULL " 
			+ "AND YEAR(ef.faseInizio) = :anno "
			+ "AND ef.faseId IN (SELECT f.faseId FROM Fase f WHERE f.faseCod = :codFase) ")
	void updateValiditaFineByFaseAndAnno(@Param("codFase") String codFase, @Param("anno") int anno, 
			@Param("utenteModifica") String utenteModifica);
	
	@Modifying
	@Query("UPDATE EnteFase ef " +
	       "SET ef.dataCancellazione = CURRENT_TIMESTAMP " +
	       "WHERE ef.validitaFine IS NULL " +
	       "AND ef.dataCancellazione IS NULL " +
	       "AND YEAR(ef.faseInizio) = :anno " +
	       "AND ef.faseId IN (SELECT f.faseId FROM Fase f WHERE f.faseCod = :codFase) ")
	void updateDataCancellazioneByFaseAndAnno(@Param("codFase") String codFase, @Param("anno") int anno);
	
	
	
	@Query("SELECT ef FROM EnteFase ef " + 
			"WHERE ef.dataCancellazione IS NULL " + 
			"AND ef.rEnteFaseId = :rEnteFaseId") 
	Optional<EnteFase> findValidByEnteFaseId(@Param("rEnteFaseId") Integer rEnteFaseId);
	


}
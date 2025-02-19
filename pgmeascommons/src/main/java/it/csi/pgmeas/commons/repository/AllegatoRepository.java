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

import it.csi.pgmeas.commons.model.Allegato;

@Repository
public interface AllegatoRepository extends JpaRepository<Allegato, Integer> {
	@Query("select a from Allegato a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) ")
	List<Allegato> findAllValid();

	@Query("select a from Allegato a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.intId = :intId ")
	List<Allegato> findAllValidByIntId(@Param("intId") Integer intId);
	
	@Query("select a from Allegato a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.intId = :intId "
			+ "and a.enteId = :enteId")
	List<Allegato> findAllValidByIntIdAndEnteId(@Param("intId") Integer intId, @Param("enteId") Integer enteId);

	@Query("select a from Allegato a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.intId = :intId and a.enteId = :enteId and a.allegatoTipoId =:allegatoTipoId")
	List<Allegato> findAllValidByIntIdAndEnteIdAndAllegatoTipoId(@Param("intId") Integer intId, @Param("enteId") Integer enteId, 
			@Param("allegatoTipoId") Integer allegatoTipoId);
	
	@Query("select a from Allegato a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.intId = :intId and a.enteId = :enteId and a.intstrId = :intstrId and a.allegatoTipoId =:allegatoTipoId")
	List<Allegato> findAllValidByIntIdAndEnteIdAndIntStrIdAndAllegatoTipoId(@Param("intId") Integer intId, @Param("enteId") Integer enteId, 
			@Param("intstrId") Integer intstrId, @Param("allegatoTipoId") Integer allegatoTipoId);

	@Query("select a from Allegato a "
			+ "where a.validitaInizio <= current_timestamp "
			+ "and (a.validitaFine is null or a.validitaFine >= current_timestamp) "
			+ "and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) "
			+ "and a.allegatoId = :allegatoId ")
	Optional<Allegato> findValidByAllegatoId(@Param("allegatoId") Integer allegatoId);
}

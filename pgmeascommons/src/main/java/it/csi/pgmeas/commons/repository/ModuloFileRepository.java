/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.model.ModuloFile;

@Repository
public interface ModuloFileRepository extends JpaRepository<ModuloFile, Integer> {
	
	@Query("""
			select a from ModuloFile a 
			where a.validitaInizio <= current_timestamp
			and (a.validitaFine >= current_timestamp or a.validitaFine is null) 
			and (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp)
			and a.fileId = :fileId
			and a.enteId = :enteId
			""")
	Optional<ModuloFile> findValidByFileIdAndEnteId(@Param("fileId") Integer fileId, @Param("enteId") Integer enteId);
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.pgmeasnotifier.model.Notifica;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica, Integer> {

	
	
	@Query(""" 
			SELECT a
			   from Notifica a 
			   where (a.dataCancellazione is null or a.dataCancellazione >= current_timestamp) 
			   and a.notificaRetryContatore >0
			   and (a.notificaStatoId is null or a.notificaStatoId = (SELECT ns.notificaStatoId 
	   FROM NotificaStato ns where ns.notificaStatoCod='E' 
	   and (ns.dataCancellazione is null or ns.dataCancellazione >= current_timestamp)
	   and (ns.validitaFine is null or ns.validitaFine >= current_timestamp) 
	   and ns.validitaInizio <= current_timestamp ))

			""")
	List<Notifica> findValidNotSuccessNotZeroRetryCount();
}

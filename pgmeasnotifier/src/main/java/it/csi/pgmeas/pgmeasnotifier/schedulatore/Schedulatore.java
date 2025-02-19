/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.schedulatore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.pgmeasnotifier.service.DecodificheService;
import it.csi.pgmeas.pgmeasnotifier.service.NotificatorePgmeasService;

@Component
public class Schedulatore {
	
	@Autowired
	NotificatorePgmeasService notificatoreService;
	@Autowired
	DecodificheService decodificheService;
	
	
	@Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}") //TODO cron
	public void scheduleInserisciNotifiche() throws InterruptedException {
	    notificatoreService.inserisciNotifiche();
	}
	
	@Scheduled(fixedDelayString = "${fixedDelay3.in.milliseconds}") 
	public void scheduleAggiornaTemplate() {
		decodificheService.aggiornaDecodifiche();
	}
	
	
	@Scheduled(fixedDelayString = "${fixedDelay2.in.milliseconds}") //TODO cron
	public void scheduleInviaNotifiche() {
	    notificatoreService.inviaNotifiche();
	}
	
	
}

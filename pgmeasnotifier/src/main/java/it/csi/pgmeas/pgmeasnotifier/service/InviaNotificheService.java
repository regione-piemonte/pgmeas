/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.CategoriaErroreEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.NotificaStatoEnum;
import it.csi.pgmeas.pgmeasnotifier.exception.NotificatoreException;
import it.csi.pgmeas.pgmeasnotifier.model.LogNotifica;
import it.csi.pgmeas.pgmeasnotifier.model.Notifica;
import it.csi.pgmeas.pgmeasnotifier.repository.LogNotificaRepository;
import it.csi.pgmeas.pgmeasnotifier.repository.NotificaRepository;
import it.csi.pgmeas.pgmeasnotifier.service.notificatore.NotificatoreOperatoriService;
import it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto.Message;

@Service
public class InviaNotificheService {
	
	private static final Logger log = LoggerFactory.getLogger(InviaNotificheService.class);
	private static final String UTENTE_BATCH_2 = "BATCH_INVIO_NOTIFICHE";
	
	@Autowired
	NotificaRepository notificaRepository;
	@Autowired
	LogNotificaRepository logNotificaRepository;
	@Autowired
	NotificatoreOperatoriService notificatoreOperatoriService;
	@Autowired
	DecodificheService decodificheService;
	@Autowired
	ErroreService erroreService;
	
	
	
	public void inviaNotifica(Notifica n) throws Exception {
	    LogNotifica ln = buildLogNotifica(n);

	    try {
	    	Message message = notificatoreOperatoriService.buildMessage(n);
	    	String jsonPayload = notificatoreOperatoriService.convertMessageToJsonObject(message);
	  	    ln.setNLogRequest(jsonPayload);
	  	    String result = inviaAlNotificatoreOperatori(jsonPayload,message);
	  	    ln.setNLogResponse(result);
	        ln.setNotificaStatoId(decodificheService.getNotificaStatoFrom(NotificaStatoEnum.SUCCESSO));
	        ln.setNLogEsito("OK");
	        ln.setNlogDataFine(new Timestamp(System.currentTimeMillis()));
	        aggiornaNotifica(n, NotificaStatoEnum.SUCCESSO);
	    } catch (Exception e) {
	        gestisciErrore(n, ln, e);
	    } finally {
	        salvaLogNotifica(ln);
	    }
	}

	private String inviaAlNotificatoreOperatori(String jsonPayload ,Message message) throws Exception {
	    String result = notificatoreOperatoriService.sendNotificaOperatoriSanitari(jsonPayload,message);
	    return result;
	}

	private void gestisciErrore(Notifica n, LogNotifica ln, Exception e) throws Exception {
	    log.error("Errore durante l'invio della notifica {}",e.getMessage(), e);
	    String tabella="Notifica";
	    Long causa = n.getNotificaId();
	    String errorPayload = null;
	    if (e instanceof NotificatoreException) {
	    	NotificatoreException ce = (NotificatoreException) e;
	        errorPayload = ce.getErrorPayload();
	        log.error("NotificatoreException: {}", errorPayload);
	        erroreService.traceError(e, CategoriaErroreEnum.NOTIFICATORE, tabella, causa);
	    } else {
	        errorPayload = e.getMessage();
	        erroreService.traceError(e, CategoriaErroreEnum.GENERICO, tabella, causa);
	    }
	    // GESTISCO IL LOG DEL CASO ERRORE
	    ln.setNotificaStatoId(decodificheService.getNotificaStatoFrom(NotificaStatoEnum.ERRORE));
	    ln.setNLogEsito("ERRORE");
	    ln.setNLogResponse(errorPayload);
	    ln.setNLogErrore(errorPayload);
	    ln.setNlogDataFine(new Timestamp(System.currentTimeMillis()));
	    ln.setNLogErrore(getStackTraceAsString(e));
	   
	    aggiornaNotifica(n, NotificaStatoEnum.ERRORE);
	}

	 private void aggiornaNotifica(Notifica n, NotificaStatoEnum stato) throws Exception {
		 int contatore = n.getNotificaRetryContatore();
		    n.setNotificaRetryContatore(contatore--);
		    n.setDataModifica(new Timestamp(System.currentTimeMillis()));
		    n.setUtenteModifica(UTENTE_BATCH_2);
		    n.setNotificaStatoId(decodificheService.getNotificaStatoFrom(stato));
		    notificaRepository.save(n);
	}
	 
	public static String getStackTraceAsString(Exception exception) {
	        if (exception == null) {
	            return "Exception is null";
	        }
	        StringWriter stringWriter = new StringWriter();
	        PrintWriter printWriter = new PrintWriter(stringWriter);
	        exception.printStackTrace(printWriter);
	        return stringWriter.toString();
	    }

	
	
	private void salvaLogNotifica(LogNotifica ln) {
			logNotificaRepository.save(ln);
	}


	private LogNotifica buildLogNotifica(Notifica n) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		LogNotifica ln =new LogNotifica();
		ln.setNotificaId(n.getNotificaId());
		ln.setNotificaRetryContatore(n.getNotificaRetryContatore());
		ln.setEnteId(n.getEnteId());
		ln.setNLogDataOperazione(now);
		ln.setNlogDataInizio(now);
		return ln;
	}

	
	
}

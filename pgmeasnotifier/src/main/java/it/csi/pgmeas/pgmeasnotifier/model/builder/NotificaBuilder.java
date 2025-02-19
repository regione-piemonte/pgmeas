/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.model.builder;

import java.sql.Timestamp;

import it.csi.pgmeas.pgmeasnotifier.model.Notifica;

public class NotificaBuilder {

    private final Notifica notifica;

    public NotificaBuilder() {
        this.notifica = new Notifica();
    }

    public NotificaBuilder withNotificaDataInizio(Timestamp notificaDataInizio) {
        this.notifica.setNotificaDataInizio(notificaDataInizio);
        return this;
    }

    public NotificaBuilder withNotificaDataFine(Timestamp notificaDataFine) {
        this.notifica.setNotificaDataFine(notificaDataFine);
        return this;
    }

    public NotificaBuilder withNotificaMexBreve(String notificaMexBreve) {
        this.notifica.setNotificaMexBreve(notificaMexBreve);
        return this;
    }

    public NotificaBuilder withNotificaMexEsteso(String notificaMexEsteso) {
        this.notifica.setNotificaMexEsteso(notificaMexEsteso);
        return this;
    }

    public NotificaBuilder withNotificaEmailOggetto(String notificaEmailOggetto) {
        this.notifica.setNotificaEmailOggetto(notificaEmailOggetto);
        return this;
    }

    public NotificaBuilder withNotificaEmailCorpo(String notificaEmailCorpo) {
        this.notifica.setNotificaEmailCorpo(notificaEmailCorpo);
        return this;
    }

    public NotificaBuilder withNotificaDestinatatioCf(String notificaDestinatatioCf) {
        this.notifica.setNotificaDestinatatioCf(notificaDestinatatioCf);
        return this;
    }

    public NotificaBuilder withNotificaDestinatatioApplicazione(String notificaDestinatatioApplicazione) {
        this.notifica.setNotificaDestinatatioApplicazione(notificaDestinatatioApplicazione);
        return this;
    }

    public NotificaBuilder withNotificaDestinatatioRuolo(String notificaDestinatatioRuolo) {
        this.notifica.setNotificaDestinatatioRuolo(notificaDestinatatioRuolo);
        return this;
    }

    public NotificaBuilder withNotificaRetryContatore(Integer notificaRetryContatore) {
        this.notifica.setNotificaRetryContatore(notificaRetryContatore);
        return this;
    }

    public NotificaBuilder withNotificaEndpoint(String notificaEndpoint) {
        this.notifica.setNotificaEndpoint(notificaEndpoint);
        return this;
    }

    public NotificaBuilder withNotificaStatoId(Integer notificaStatoId) {
        this.notifica.setNotificaStatoId(notificaStatoId);
        return this;
    }

    public NotificaBuilder withEnteId(Integer enteId) {
        this.notifica.setEnteId(enteId);
        return this;
    }

    public NotificaBuilder withDataCreazione(Timestamp dataCreazione) {
        this.notifica.setDataCreazione(dataCreazione);
        return this;
    }
    
    public NotificaBuilder withDataModifica(Timestamp dataModifica) {
        this.notifica.setDataModifica(dataModifica);
        return this;
    }
    
    public NotificaBuilder withDataCancellazione(Timestamp dataCancellazione) {
        this.notifica.setDataCancellazione(dataCancellazione);
        return this;
    }
	
    public NotificaBuilder withUtenteCreazione(String utenteCreazione) {
        this.notifica.setUtenteCreazione(utenteCreazione);
        return this;
    }
    
    public NotificaBuilder withUtenteModifica(String utenteModifica) {
        this.notifica.setUtenteModifica(utenteModifica);
        return this;
    }
    
    public NotificaBuilder withUtenteCancellazione(String utenteCancellazione) {
        this.notifica.setUtenteCancellazione(utenteCancellazione);
        return this;
    }
    public Notifica build() {
        return this.notifica;
    }
}
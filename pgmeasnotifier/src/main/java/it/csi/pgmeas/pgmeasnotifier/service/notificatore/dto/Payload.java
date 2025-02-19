/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class Payload {
	private String id;
	@JsonProperty("bulk_id")
	private String bulkId;
	private String applicazione;
	@JsonProperty("user_id")
	private String userId;
//	private String ruolo;
	private Email email;
	private Push push;
	private Mex mex;
	private Memo memo;
	private Sms sms;
	private String tag;
	    
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service.notificatore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class Message{
    public String uuid;
    public Payload payload;
	@JsonProperty("to_be_retried")
    public boolean toBeRetried;
}
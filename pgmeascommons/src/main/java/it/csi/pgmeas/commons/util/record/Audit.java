/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.record;

import java.io.Serializable;
import java.sql.Timestamp;

public record Audit(
		String body, String response,
		Timestamp dataOra, String ipAddress, String idApp, String fiscalCode, int status, String method,
		String uuid) implements Serializable {

}

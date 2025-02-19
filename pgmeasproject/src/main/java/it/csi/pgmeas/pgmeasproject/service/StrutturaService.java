/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.io.IOException;

import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.dto.v2.StrutturaNewDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import jakarta.servlet.http.HttpServletRequest;

public interface StrutturaService {

	StrutturaDto postStruttura(StrutturaNewDto body, HttpServletRequest httpRequest) throws IOException, PgmeasException;
	
}

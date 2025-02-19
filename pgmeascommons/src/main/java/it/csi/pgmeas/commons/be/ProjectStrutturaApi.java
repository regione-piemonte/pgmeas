/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.be;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.v2.StrutturaNewDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/struttura")
public interface ProjectStrutturaApi {

	@PostMapping
	ResponseEntity<?> postStruttura(@RequestHeader HttpHeaders httpHeaders,
			HttpServletRequest httpRequest, @RequestBody StrutturaNewDto body) throws Exception;

}

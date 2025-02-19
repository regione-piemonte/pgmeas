/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.be;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.v2.RicercaInterventiFilterDto;
import jakarta.servlet.http.HttpServletRequest;

//CDU01
@RestController
@RequestMapping("/api/ricerca")
public interface ProjectRicercaApi {

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ricercaByAllFilters(HttpServletRequest httpRequest, 
			@RequestBody RicercaInterventiFilterDto filters);
		
	@PostMapping(value = "/interventi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventiByAllFilters(HttpServletRequest httpRequest, 
			@RequestBody RicercaInterventiFilterDto filters);
	
	@PostMapping(value = "/moduloA", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventiModuloAByAllFilters(HttpServletRequest httpRequest, 
			@RequestBody RicercaInterventiFilterDto filters);
		
}

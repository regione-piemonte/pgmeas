/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.be;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/modulo")
public interface ProjectModuloApi {

	@GetMapping(value = "/{rIntModuloId}/intervento/{interventoId}/download", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> downloadModuloByRIntModuloId(@RequestHeader HttpHeaders httpHeaders,
			HttpServletRequest httpRequest, @PathVariable("rIntModuloId") Integer rIntModuloId,
			@PathVariable("interventoId") Integer interventoId) throws Exception;
	
}

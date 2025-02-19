/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.be;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.v2.InterventoToPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutModel;
import it.csi.pgmeas.commons.dto.v2.InterventoToSaveModel;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/intervento")
public interface ProjectInterventoV2Api {

	@PostMapping
	public ResponseEntity<?> postInterventoV2(@RequestBody InterventoToSaveModel body, HttpServletRequest httpRequest)
			throws Exception;

	// CDU15 Copia Intervento
	@GetMapping(value = "/interventi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventiByFilters(HttpServletRequest httpRequest, @RequestParam(value="anno", required = false) Integer anno,
			@RequestParam(value="codice", required = false) String codice, 
			@RequestParam(value="titolo", required = false) String titolo, 
			@RequestParam(value="cup", required = false) String cup);

	// CDU15 Copia Intervento
	@GetMapping(value = "/{interventoId}/interventiStruttura", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventiStrutturaByIntId(HttpServletRequest httpRequest, @PathVariable("interventoId") Integer interventoId,
			@RequestParam(defaultValue = "false") boolean copy);

	@PutMapping(value = "/{interventoId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoV2(@RequestBody InterventoToPutModel body,
			@PathVariable("interventoId") Integer interventoId, HttpServletRequest httpRequest) throws Exception;

	// CDU17 Get Intervento
	@GetMapping(value = "/{interventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getIntervento(HttpServletRequest httpRequest, @PathVariable("interventoId") Integer interventoId);

	// CDU5 Put intervento
	@PutMapping(value = "/{interventoId}/datiRegione", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoV2Regione(@RequestBody InterventoToPutByRegioneModel body,
			@PathVariable("interventoId") Integer interventoId, HttpServletRequest httpRequest);
	
}

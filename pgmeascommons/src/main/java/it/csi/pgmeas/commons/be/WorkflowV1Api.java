/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.be;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/wf")
public interface WorkflowV1Api {

	@PutMapping(value = "/intervento/{interventoId}/invia", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoInvia(@RequestBody AllegatoLightExtDto allegatoIntervento,
			@PathVariable("interventoId") Integer interventoId, HttpServletRequest httpRequest) throws Exception;

	@PutMapping(value = "/intervento/{interventoId}/approva", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoApprova(@RequestBody AllegatoLightExtDto allegatoIntervento,
			@PathVariable("interventoId") Integer interventoId, HttpServletRequest httpRequest) throws Exception;
	
	@PutMapping(value = "/intervento/{interventoId}/rifiuta", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoRifiuta(@RequestBody RespingimentoDto rifiutaInterventoDto,
			@PathVariable("interventoId") Integer interventoId, HttpServletRequest httpRequest) throws Exception;
	
	@PutMapping(value = "/intervento/{interventoId}/elimina", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putInterventoElimina(
			@PathVariable("interventoId") Integer interventoId, HttpServletRequest httpRequest) throws Exception;
}

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoPutDto;
import it.csi.pgmeas.commons.dto.v2.ModuloAPutByRegioneModel;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/modulo/A")
public interface ProjectModuloAApi {

	@PostMapping
	public ResponseEntity<?> postModuloA(
			HttpServletRequest httpRequest,
			@RequestBody RichiestaAmmissioneFinanziamentoDto request) throws Exception;
	
	@PutMapping(value = "/{rIntModuloId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putModuloA(
			@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			@PathVariable("rIntModuloId") Integer rIntModuloId, 
			@RequestBody RichiestaAmmissioneFinanziamentoPutDto request) throws Exception;
	
	@GetMapping(value = "/{interventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getModuloAIntervento(
			@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			@PathVariable("interventoId") Integer interventoId) throws Exception;
	
	@GetMapping(value = "/{interventoId}/interventiStruttura", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getModuloAInterventoStruttura(
			@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			@PathVariable("interventoId") Integer interventoId) throws Exception;

	@PutMapping(value = "/{rIntModuloId}/datiRegione", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putModuloARegione(@RequestBody ModuloAPutByRegioneModel body,
			@PathVariable("rIntModuloId") Integer rIntModuloId,
			HttpServletRequest httpRequest) throws Exception;
	
	@PutMapping(value = "/{rIntModuloId}/intervento/{interventoId}/invia", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putModuloAInvia(@PathVariable("rIntModuloId") Integer rIntModuloId,  @PathVariable("interventoId") Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;

	@PutMapping(value = "/{rIntModuloId}/intervento/{interventoId}/approva", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putModuloAApprova(@PathVariable("rIntModuloId") Integer rIntModuloId,  @PathVariable("interventoId") Integer interventoId,
			@RequestBody AllegatoLightExtDto allegatoModuloA, 
			HttpServletRequest httpRequest) throws Exception;

	@PutMapping(value = "/{rIntModuloId}/intervento/{interventoId}/respinge", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putModuloARespinge(@RequestBody RespingimentoDto rifiutaModuloADto,
			@PathVariable("rIntModuloId") Integer rIntModuloId,  @PathVariable("interventoId") Integer interventoId,
			HttpServletRequest httpRequest) throws Exception;
	
}

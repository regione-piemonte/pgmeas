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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tipologiche")
public interface RegistryApi {

	@GetMapping(value = "/parametro", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getParametro(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/parametroTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getParametroTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/tipoAllegato", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getTipoAllegato(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/classifTree/{classifTsTipoCod}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getClassifTreeByClassifTsTipoCod(@PathVariable("classifTsTipoCod") String classTsTipoCod, 
			@RequestParam(defaultValue = "false") boolean view,
			@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/ente", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getEnte(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

    @GetMapping(value = "/ente/{codEnte}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getEnteByEnteCod(@PathVariable("codEnte") String codEnte, @RequestHeader HttpHeaders httpHeaders, 
            HttpServletRequest httpRequest);
    
	@GetMapping(value = "/enteTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getEnteTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/fase", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getFase(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/finanziamento/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getFinanziamentoByIntId(@PathVariable("intId") Integer intId, @RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/finanzTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getFinanziamentoTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/finanzTipoDet", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getFinanziamentoTipoDet(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);
	
	@GetMapping(value = "/finanzImportoTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getFinanziamentoImportoTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);
	
	@GetMapping(value = "/intervAppaltoTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoAppaltoTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervCategoria", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoCategoria(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervContrattoTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoContrattoTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervFinalita", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoFinalita(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervObiettivo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoObiettivo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervStato", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoStato(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervStatoProgettuale", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoStatoProgettuale(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervTipoDett", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoTipoDett(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/intervTipoDett/{intTipoCod}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInterventoTipoDettByIntervTipoCod(@PathVariable("intTipoCod") String intTipoCod, @RequestHeader HttpHeaders httpHeaders,
			HttpServletRequest httpRequest);
	
	@GetMapping(value = "/moduloStato", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getModuloStato(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);
	
	@GetMapping(value = "/moduloTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getModuloTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/organo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getOrgano(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/piano", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getPianoTriennale(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/provvedimentoLivello", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getProvvedimentoLivello(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/provvedimentoTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getProvvedimentoTipo(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/quadrante", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getQuadrante(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/quadrante/{codEnte}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getQuadranteByCodEnte(@PathVariable("codEnte") String codEnte, @RequestHeader HttpHeaders httpHeaders,
			HttpServletRequest httpRequest);

	@GetMapping(value = "/struttura", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getStruttura(@RequestHeader HttpHeaders httpHeaders, HttpServletRequest httpRequest);

	@GetMapping(value = "/struttura/{codStruttura}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getStrutturaByCodice(@PathVariable("codStruttura") String codStruttura, @RequestHeader HttpHeaders httpHeaders,
			HttpServletRequest httpRequest);
	
	@GetMapping(value = "/struttura/intervento/{interventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getStrutturaByInterventoId(@PathVariable("interventoId") Integer interventoId, @RequestHeader HttpHeaders httpHeaders,
			HttpServletRequest httpRequest);

	@GetMapping(value = "/errori", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getErrori(HttpServletRequest httpRequest);
}

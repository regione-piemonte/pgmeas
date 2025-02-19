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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.MonitoraggioDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/intervento")
public interface ProjectInterventoApi {

	//TODO delete
	@GetMapping(value = "/finanziamenti", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllData();

	//TODO delete
	@GetMapping(value = "/finanziamenti/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFinanziamentiByIntId(@PathVariable("intId") Integer intId);
	
	//TODO delete
	@PostMapping(value = "/finanziamento/datiMonitoraggio", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inserimentoDatiMonitoraggio(@RequestBody MonitoraggioDto richiestaMonitoraggioDto, HttpServletRequest httpRequest) throws Exception;

	//TODO delete
	@GetMapping(value = "/dicappaltabilitavalidazione/{classifTreeId}/{enteId}/{intstrDaTsId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getIntDataValidDicAppalto(@PathVariable("classifTreeId") Integer classifTreeId, @PathVariable("enteId") Integer enteId, @PathVariable("intstrDaTsId") Integer intstrDaTsId);
	
	//CDU01 Lista di tutti gli interventi in un determinato anno
	@GetMapping(value = "/interventi/{anno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventoByAnno(@PathVariable("anno") Integer anno);
	
	//CDU01 Dettaglio di un intervento //TODO delete
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventoById(@PathVariable("id") Integer id);

	//CDU01 Lista di tutte le strutture per un dato intervento //TODO delete
	@GetMapping(value = "/struttura/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventoStrutturaByIdInt(@PathVariable("intId") Integer intId);

	//TODO delete
	@GetMapping(value = "/finanziamentoPrevSpesa/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventoFinanziamentoPrevSpesaByIdInt(@PathVariable("intId") Integer intId);

	//TODO ???
	@GetMapping(value = "/finanziamentoLiquidazione/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFinanziamentoLiquidazioneByIdInt(@PathVariable("intId") Integer intId);

	//TODO ???
	@GetMapping(value = "/finanziamentoLiquidazioneRichiesta/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFinanziamentoLiquidazioneRichiestaByIdInt(@PathVariable("intId") Integer intId);

	//TODO delete
	@GetMapping(value = "/garaAppalto/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventoGaraAppaltoByIdInt(@PathVariable("intId") Integer intId);

	@GetMapping(value = "/allegato/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterventoAllegatoByIdInt(@PathVariable("intId") Integer intId);

	//CDU01 Lista di tutti gli anni presenti nella tabella intervento 
	@GetMapping(value = "/anni", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAnniIntervento();
	
	//CDU44 Estrazione quadri economici e dichiarazione di appaltabilita per struttura/idIntervento  TODO delete
	@GetMapping(value = "/quadroeconomico/{intId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getQePerIdIntervento(@PathVariable("intId") Integer intId);

}

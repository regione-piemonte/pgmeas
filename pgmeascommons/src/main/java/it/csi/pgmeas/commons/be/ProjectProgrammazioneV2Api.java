/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.be;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.EnteProrogaDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneBaseDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneProrogaDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/programmazione")
public interface ProjectProgrammazioneV2Api {

	@GetMapping(value = "/info/{enteCod}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getInfoProgrammazioneForUser(@PathVariable("enteCod") String enteCod);

	@GetMapping(value = "/{anno}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getProgrammazione(@PathVariable("anno") String anno);

	@GetMapping(value = "/{anno}/{enteCod}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getProrogaEnte(@PathVariable("anno") String anno, @PathVariable("enteCod") String enteCod);

	@PutMapping(value = "/{anno}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> inserisciProrogaTuttiEnti(@RequestBody ProgrammazioneProrogaDto request, HttpServletRequest httpRequest,
			@PathVariable("anno") String anno) throws Exception;

	@DeleteMapping(value = "/{anno}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> deleteProgrammazione(@RequestBody ProgrammazioneProrogaDto request, HttpServletRequest httpRequest,
			@PathVariable("anno") String anno) throws Exception;

	@PostMapping
	public ResponseEntity<?> insertProgrammazione(@RequestBody ProgrammazioneBaseDto request, HttpServletRequest httpRequest)
			throws Exception;

	@GetMapping
	public ResponseEntity<?> getElencoProgrammazioni() throws Exception;

	@PutMapping(value = "/{anno}/{enteCod}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> insertProroga(@RequestBody EnteProrogaDto request, HttpServletRequest httpRequest,
			@PathVariable("anno") String anno, @PathVariable("enteCod") String enteCod);

}

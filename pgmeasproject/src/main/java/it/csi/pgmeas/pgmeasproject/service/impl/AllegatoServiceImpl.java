/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.repository.AllegatoRepository;
import it.csi.pgmeas.pgmeasproject.dto.FileDto;
import it.csi.pgmeas.pgmeasproject.service.AllegatoService;

@Service
public class AllegatoServiceImpl implements AllegatoService {
	@Value("${download.base.path}")
	private String basePath;

	@Autowired
	private AllegatoRepository allegatoRepository;
	
	@Override
	public FileDto downloadAllegatoById(Integer id) throws IOException {
		var allegato = allegatoRepository.findValidByAllegatoId(id).orElseThrow();
		var path = Path.of(basePath, allegato.getFilePath(), allegato.getFileNameSystem());
		var bytes = Files.readAllBytes(path);
		var base64 = Base64.getEncoder().encodeToString(bytes);
		var fileName = allegato.getFileNameUser() != null ? allegato.getFileNameUser() : allegato.getFileNameSystem();
		FileDto dto = FileDto.builder().base64(base64).fileName(fileName).fileType(allegato.getFileType()).build();
		return dto;
	}
	
}

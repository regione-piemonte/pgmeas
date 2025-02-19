/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.ModuloFile;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.service.InterventoUtilityService;
import it.csi.pgmeas.commons.service.ModuloUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ModuloStatoEnum;
import it.csi.pgmeas.pgmeasproject.dto.FileDto;
import it.csi.pgmeas.pgmeasproject.service.ModuloService;

@Service
public class ModuloServiceImpl implements ModuloService {
	@Value("${download.base.path}")
	private String basePath;
	
	@Autowired
	private EnteUtilityService enteUtilityService;
	@Autowired
	private InterventoUtilityService interventoUtilityService;
	@Autowired
	private ModuloUtilityService moduloUtilityService;
	
	@Override
	public FileDto downloadModuloByRIntModuloIdAndInterventoId(Integer rIntModuloId, Integer interventoId) throws IOException, PgmeasException {
		Intervento intervento = interventoUtilityService.getInterventoById(interventoId);
		Ente ente = enteUtilityService.getEnteByEnteId(intervento.getEnteId());
		Integer enteId = ente.getEnteId();
		RInterventoModulo rInterventoModulo = moduloUtilityService.getRInterventoModuloByIdAndEnteId(rIntModuloId, enteId);
		
		List<ModuloStatoEnum> statiAmmessi = new ArrayList<ModuloStatoEnum>();
		statiAmmessi.add(ModuloStatoEnum.APPROVATO); //Modulo A
		statiAmmessi.add(ModuloStatoEnum.FINANZIATO); //Modulo Intervento
		moduloUtilityService.checkStatusModuloByRInterventoModuloAndEnteId(rInterventoModulo, statiAmmessi, enteId);
		moduloUtilityService.checkFileByRInterventoModulo(rInterventoModulo);
		
		ModuloFile modulo = moduloUtilityService.getModuloFileFromFileIdAndEnteId(rInterventoModulo.getFileId(), ente.getEnteId());
		
		Path path = Path.of(basePath, modulo.getFilePath(), modulo.getFileNameSystem());
		byte[] bytes = Files.readAllBytes(path);
		String base64 = Base64.getEncoder().encodeToString(bytes);
		String fileName = modulo.getFileNameUser() != null ? modulo.getFileNameUser() : modulo.getFileNameSystem();
		FileDto file = FileDto.builder().base64(base64).fileName(fileName).fileType(modulo.getFileType()).build();
		return file;
	}
}

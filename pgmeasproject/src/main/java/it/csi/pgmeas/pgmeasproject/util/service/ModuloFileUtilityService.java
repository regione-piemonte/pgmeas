/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.util.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Year;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.Modulo;
import it.csi.pgmeas.commons.model.ModuloFile;
import it.csi.pgmeas.commons.model.ModuloStato;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.repository.ModuloFileRepository;
import it.csi.pgmeas.commons.service.ModuloUtilityService;
import it.csi.pgmeas.commons.util.enumeration.ModuloStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

@Service
public class ModuloFileUtilityService extends ModuloUtilityService {
	@Value("${download.base.path}")
	private String basePath;
	@Autowired
	private ModuloFileRepository moduloFileRepository;

	public RInterventoModulo salvaModulo(AllegatoLightExtDto dtoAllegatoIn, UserInfoRecord userInfo, Timestamp now,
			Intervento intervento, Ente ente, ModuloTipoEnum moduloTipoEnum, ModuloStatoEnum moduloStatoEnum)
			throws IOException, PgmeasException {
		String filePath = "%s/%s/%s".formatted(ente.getEnteCod(), Year.now(), intervento.getIntCod());

		var ext = FilenameUtils.getExtension(dtoAllegatoIn.getFileNameUser());
		var allegatoFileNameSystem = "%s.%s".formatted(UUID.randomUUID(), ext);
		var allegatoBytes = Base64.getDecoder().decode(dtoAllegatoIn.getBase64());
		// CREA LA DIRECTORY E
		Files.createDirectories(Path.of(basePath, filePath));
		Files.write(Path.of(basePath, filePath, allegatoFileNameSystem), allegatoBytes);
		
		ModuloFile moduloFile = buildModuloFile(dtoAllegatoIn, userInfo, now, ente, filePath, allegatoFileNameSystem);
		moduloFile = moduloFileRepository.saveAndFlush(moduloFile);
		
		Modulo moduloTipo = getModuloByModuloCod(moduloTipoEnum);
		ModuloStato moduloStato = getModuloStatoByModuloStatoCod(moduloStatoEnum);
		return inserisciRInterventoModulo(intervento.getIntId(), ente.getEnteId(), userInfo, now, get(moduloTipo), get(moduloStato), 
				get(moduloFile), userInfo.codiceFiscale(), now, getUtenteNomeCognome(userInfo), null);
	}

}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import static it.csi.pgmeas.commons.service.StrutturaUtilityService.buildStrutturaFromStrutturaNewDto;
import static it.csi.pgmeas.commons.service.StrutturaUtilityService.validateNewStruttura;
import static it.csi.pgmeas.commons.util.APISecurityFilterUtils.getUser;
import static it.csi.pgmeas.commons.util.ProfileUtils.hasFunctionality;
import static it.csi.pgmeas.commons.util.ProfileUtils.isAsr;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.dto.v2.StrutturaNewDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Struttura;
import it.csi.pgmeas.commons.repository.StrutturaRepository;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.util.enumeration.FunzionalitaEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.pgmeasproject.service.StrutturaService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class StrutturaServiceImpl implements StrutturaService {
	@Autowired
	private StrutturaRepository strutturaRepository;
	
	@Autowired
	private EnteUtilityService enteUtilityService;

	@Override
	public StrutturaDto postStruttura(StrutturaNewDto body, HttpServletRequest httpRequest) throws IOException, PgmeasException {
		UserInfoRecord userInfo = getUser(httpRequest);
		isAsr(userInfo);
		hasFunctionality(userInfo, FunzionalitaEnum.OP_INSERISCI_INTERVENTO);

		Timestamp now = Timestamp.from(Instant.now());
		validateNewStruttura(body);

		Ente ente = enteUtilityService.getEnteByCodiceEsteso(userInfo.codiceAzienda());

		Struttura struttura = buildStrutturaFromStrutturaNewDto(body, now, userInfo, ente);
		
		Struttura strutturaSaved = strutturaRepository.saveAndFlush(struttura);
		StrutturaDto strutturaDto = MappingUtils.copy(strutturaSaved, new StrutturaDto());
		
		return strutturaDto;
	}

}

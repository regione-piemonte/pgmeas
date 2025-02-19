/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.io.IOException;

import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.pgmeasproject.dto.FileDto;

public interface ModuloService {

	public FileDto downloadModuloByRIntModuloIdAndInterventoId(Integer rIntModuloId, Integer interventoId) throws IOException, PgmeasException;
}

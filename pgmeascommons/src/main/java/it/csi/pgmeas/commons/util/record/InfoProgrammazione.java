/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.record;

import java.io.Serializable;

public record InfoProgrammazione(boolean programmazioneAperta, //
		String triennio, String annoInserimentIntervento, String dataInizioProgrammazione,
		String DataFineProgrammazione, String dataFineProroga) implements Serializable {

}

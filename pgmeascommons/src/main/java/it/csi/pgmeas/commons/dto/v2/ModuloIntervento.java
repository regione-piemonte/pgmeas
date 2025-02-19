/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;
import java.util.List;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import lombok.Data;

@Data
public class ModuloIntervento extends InterventoV2GetDto implements Serializable {

	private static final long serialVersionUID = 8639925314641644606L;

	private String moduloTipo = null;
	private List<AllegatoLightExtDto> allegatoProvvedimentoAziendaleApprovazione = null;
	private List<AllegatoLightExtDto> allegatoRelazioneTecnica = null;
	private String noteRespingimentoModulo = null;
	private List<AllegatoLightExtDto> allegatoNullaOsta = null;
	private List<AllegatoLightExtDto> allegatoDecretoMinisteriale = null;
}

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
public class InterventoToPutByRegioneModel implements Serializable {
	
	private static final long serialVersionUID = -3069681570839783834L;
	
	private List<InterventoParereSaveExtDto> pareri = null;
	private Boolean finanziabile = null;
	private List<PrevisioneSpesaDto> previsioniDiSpesa = null;
	private List<PianoFinanziarioSaveDto> pianiFinanziari = null;
	private AllegatoLightExtDto dgrApprovazioneNew = null;
	private AllegatoLightExtDto dgrApprovazioneToDelete = null;
	private AllegatoLightExtDto dgrConsiglioRegionaleNew = null;
	private AllegatoLightExtDto dgrConsiglioRegionaleToDelete = null;
	private AllegatoLightExtDto dcrApprovazioneNew = null;
	private AllegatoLightExtDto dcrApprovazioneToDelete = null;
	private List<AllegatoLightExtDto> determinazioniDirigenzialiNew = null;
	private List<AllegatoLightExtDto> determinazioniDirigenzialiToDelete = null;
}

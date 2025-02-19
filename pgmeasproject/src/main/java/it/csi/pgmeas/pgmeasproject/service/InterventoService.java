/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service;

import java.util.List;

import it.csi.pgmeas.commons.dto.AllegatoDto;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneDto;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneRichiestaDto;
import it.csi.pgmeas.commons.dto.InterventoFinanziamentoPrevSpesaDto;
import it.csi.pgmeas.commons.dto.InterventoGaraAppaltoDto2;
import it.csi.pgmeas.commons.dto.InterventoResultDto;
import it.csi.pgmeas.commons.dto.InterventoStrutturaDto;

public interface InterventoService {

	public List<InterventoResultDto> getInterventoByAnno(Integer anno);

	public InterventoResultDto getInterventoById(Integer id);

	public List<InterventoStrutturaDto> getInterventoStrutturaByIdInt(Integer intId);

	public List<InterventoFinanziamentoPrevSpesaDto> getInterventoFinanziamentoPrevSpesaByIdInt(Integer intId);

	public List<FinanziamentoLiquidazioneDto> getFinanziamentoLiquidazioneByIdInt(Integer intId);

	public List<FinanziamentoLiquidazioneRichiestaDto> getFinanziamentoLiquidazioneRichiestaByIdInt(Integer intId);

	public List<InterventoGaraAppaltoDto2> getInterventoGaraAppaltoByIdInt(Integer intId);

	public List<AllegatoDto> getInterventoAllegatoByIdInt(Integer intId);

	public List<Integer> getAnniIntervento();

	public List<InterventoStrutturaDto> getQePerIdIntervento(Integer intId);
}

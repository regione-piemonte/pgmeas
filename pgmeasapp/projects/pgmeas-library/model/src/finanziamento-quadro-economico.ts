/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {BaseModel} from '@pgmeas-library/model/src/base-model';

export interface FinanziamentoQuadroEconomico extends BaseModel {
  ntStrId: number
  intId: number
  strId: number
  intstrImporto: number
  intstrResponsabileCognome: string
  intstrResponsabileNome: string
  intstrResponsabileProcedimentoCognome: string
  intstrResponsabileProcedimentoNome: string
  intstrParereVincolantePpp: boolean
  intstrParereVincolantePppNprotocollo: string
  intstrParereCabinaDiRegia: boolean
  intstrParereCabinaDiRegiaNprotocollo: string
  intstrAperturaCantiereDataPrevista: any
  intstrAperturaCantiereDataEffettiva: any
  intstrPrioritaAnno: any
  intstrPriorita: number
  intstrSottopriorita: string
  intstrCollaudoDataPrevista: any
  intstrCollaudoDataEffettiva: any
  intstrPftePrevisioneDurataGg: any
  intstrGaraPrevisioneDurataGg: any
  intstrCollaudoPrevisioneDurataGg: any
  intstrProgettoesecutivoPrevisioneDurataGg: any
  intstrLavoriPrevisioneDurataGg: any
  intstrAttivazionePrevisioneDurataGg: any
  mapClassfifTreeIdImporto: MapClassfifTreeIdImporto[]
  mapClassfifTreeIdDaSelezionata: MapClassfifTreeIdDaSelezionata;
  dataCreazione: number
  dataModifica: number
  dataCancellazione: any
  utenteCreazione: string
  utenteModifica: string
  utenteCancellazione: any
  enteId: number
}

export interface MapClassfifTreeIdImporto {
  [key : string] : null | number;
}

interface MapClassfifTreeIdDaSelezionata {
  [key : string] : { selezionata: boolean; validazioneData: number };
}


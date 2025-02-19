/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";
import { ClassificazioneTree } from "./classificazione-tree";
import { Struttura } from "./struttura";

export interface StrutturaInserimentoIntervento extends BaseModel {
  struttura: Struttura;
  costoStruttura?: number;
  progettazione?: number;
  affidamentoLavori?: number;
  esecuzioneLavori?: number;
  collaudo?: number;
  totaleDurataIntervento?: number;
  appaltoIntegrato?: boolean;
  totaleDurataStimataIntervento?: number;
  listaInterventiEdilizi?: ClassificazioneTree[];
  listaQuadroEconomico?: ClassificazioneTree[];
  intstrRespStrComplesNome: string;
  intstrRespStrComplesCognome: string;
  intstrRespStrSemplNome: string;
  intstrRespStrSemplCognome: string
}

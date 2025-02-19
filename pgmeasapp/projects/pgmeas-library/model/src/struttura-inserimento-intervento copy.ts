/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";
import { ClassificazioneTree } from "./classificazione-tree";
import { Struttura } from "./struttura";

export interface StrutturaInserimentoInterventoCopy extends BaseModel {
  struttura: Struttura;
  intStrId: number;
  intId: number;
  strId: number;
  intstrImporto: number | null;
  intstrResponsabileProcedimento: Responsabile;
  parerePPP: Parere;
  parereHta: Parere;
  intstrParereCabinaDiRegia: boolean | null;
  intstrParereCabinaDiRegiaNProtocollo: string | null;
  intstrAperturaCantiereDataPrevista: number;
  intstrAperturaCantiereDataEffettiva: number | null;
  intstrCollaudoDataPrevista: number;
  intstrCollaudoDataEffettiva: number | null;
  enteId: number;
  intstrResponsabileStrutturaComplessaNome: string;
  intstrResponsabileStrutturaComplessaCognome: string;
  intstrResponsabileStrutturaComplessaCf: string;
  intstrResponsabileStrutturaSempliceNome: string;
  intstrResponsabileStrutturaSempliceCognome: string; // You may need to adjust this if it's always null
  intstrResponsabileStrutturaSempliceCf: string
  appaltoIntegrato: boolean; // You can specify a more precise type if you know its structure
  progettazioneGg: number | null;
  affidamentoLavoriGg: number | null;
  esecuzioneLavoriGg: number | null;
  collaudoGg: number | null;
  quadroEconomico: QuadroEconomico;
  interventoEdilizio: InterventoEdilizio;
}

export interface Parere extends BaseModel {
  parere: boolean | null;
  allegati: Allegato[]; // You can specify a more precise type if you know the structure of allegati
}

export interface QuadroEconomico {
  [key: string]: number; // Assuming the keys are dynamic (1, 2, ..., 38)
}

interface InterventoEdilizio {
  [key: string]: boolean; // Assuming the keys are dynamic (39, 40, ..., 51)
}

interface Responsabile {
  cognome: string | null;
  nome: string | null;
  cf: string | null;
}

interface Allegato {
  idAllegato: number | null;
  intAllegatoNumero: string | null;
  intAllegatoData: Date | null;
}


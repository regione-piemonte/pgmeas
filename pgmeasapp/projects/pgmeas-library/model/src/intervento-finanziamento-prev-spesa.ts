/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface InterventoFinanziamentoPrevSpesa extends BaseModel {
  intFinPrevSpesaId: number;
  intId: number;
  finId: number;
  intFinPrevSpesaAnno: number;
  intFinPrevSpesaImporto: number;
  validitaInizio: number;
  validitaFine: number;
  enteId: number;
}

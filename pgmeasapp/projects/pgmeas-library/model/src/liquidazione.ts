/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface Liquidazione extends BaseModel {
  rLiqRicId: number;
  liqRicId: number;
  liqId: number;
  validitaInizio: number;
  validitaFine: number;
  enteId: number;
  liqImportoErogato: number;
  liqImportoIncassato: number;
  liqImportoTotaleSpesoAzienda: number;
}

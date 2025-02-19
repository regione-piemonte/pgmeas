/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface FinanziamentoLiquidazione extends BaseModel {
  liqId: number;
  liqNumero: number;
  liqDataDa: number;
  liqDataA: number;
  liqImporto: number;
  finId: number;
  enteId: number;
}

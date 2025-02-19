/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface InterventoGaraAppalto extends BaseModel {
  garaAppaltoId: number;
  garaAppaltoCigCod: string;
  garaAppaltoData: number;
  intstrId: number;
  enteId: number;
}

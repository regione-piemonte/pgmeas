/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface Tipologica extends BaseModel {
  validitaInizio: number;
  validitaFine: number | null;
}

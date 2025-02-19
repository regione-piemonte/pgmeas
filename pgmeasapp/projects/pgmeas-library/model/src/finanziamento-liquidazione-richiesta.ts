/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";
import { Liquidazione } from "./liquidazione";

export interface FinanziamentoLiquidazioneRichiesta extends BaseModel {
  liqRicId: number;
  liqRicNumero: number;
  liqRicProtocollo: string;
  liqRicProtocolloData: number;
  liqRicImporto: number;
  finId: number;
  enteId: number;
  listaLiquidazione: Liquidazione[];
}

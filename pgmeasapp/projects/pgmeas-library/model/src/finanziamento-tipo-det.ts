/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface FinanziamentoTipoDet extends Tipologica {
  finTipoDetId: number;
  finTipoDetCod: string;
  finTipoDetDesc: string;
  finTipoId: number;
  finTipoDetPercentualeStato?: number;
  finTipoDetPercentualeRegione?: number;
  finTipoDetPercentualeAltro?: number;
}

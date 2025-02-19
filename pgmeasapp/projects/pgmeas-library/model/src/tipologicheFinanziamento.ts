/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface TipologicaFinanziamento {
  tipologiaId: number;
  tipologiaCod: string;
  tipologiaDesc: string;
  finanziamentoTipiDettaglio: FinanziamentoDettaglio[];
}

export interface FinanziamentoDettaglio {
  tipologiaDettaglioId: number;
  tipologiaDettaglioCod: string;
  tipologiaDettaglioDesc: string;
  finanziamentoImportiTipo: FinanziamentoImporto[];
}

export interface FinanziamentoImporto {
  finanziamentoImportoTipoId: number;
  finanziamentoImportoTipoCod: string;
  finanziamentoImportoTipoDesc: string;
  importoTipoDetId: number;
  finanziamentoPercentuale: number | null;
}

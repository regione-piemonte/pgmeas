/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface InterventoContrattoTipo extends Tipologica {
  intContrattoTipoId: number;
  intContrattoTipoCod: string;
  intContrattoTipoDesc: string;
}

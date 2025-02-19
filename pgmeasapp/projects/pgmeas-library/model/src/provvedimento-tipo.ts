/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface ProvvedimentoTipo extends Tipologica {
  provTipoId: number;
  provTipoCod: string;
  provTipoDesc: string;
  organoId: number;
}

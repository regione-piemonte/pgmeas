/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface ParametroTipo extends Tipologica {
  parametroTipoId: number;
  parametroTipoCod: string;
  parametroTipoDesc: string;
}

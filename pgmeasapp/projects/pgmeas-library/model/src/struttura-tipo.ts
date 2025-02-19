/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface StrutturaTipo extends Tipologica {
  strutturaTipoId: number;
  strutturaTipoCod: string;
  strutturaTipoDesc: string;
}

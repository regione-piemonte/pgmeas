/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";
import { StrutturaInserimentoIntervento } from "./struttura-inserimento-intervento";

export interface InserimentoIntervento extends BaseModel {
  struttureInserimento: StrutturaInserimentoIntervento[];
}

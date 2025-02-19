/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface ProvvedimentoLivello extends Tipologica {
  provLivId: number;
  provLivCod: string;
  provLivDesc: string;
}

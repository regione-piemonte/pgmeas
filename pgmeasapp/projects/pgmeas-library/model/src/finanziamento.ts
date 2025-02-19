/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface Finanziamento extends Tipologica {
  finId: number;
  finCod: string;
  finImporto: number;
  finNote: string;
  finTipoDetId: number;
  finPrincipale: boolean;
  provId: number;
  intId: number;
  dataCreazione: number;
  dataModifica: number;
  dataCancellazione: number;
  utenteCreazione: string;
  utenteModifica: string;
  utenteCancellazione: string;
  enteId: number;
}

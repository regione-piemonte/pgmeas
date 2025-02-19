/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface Provvedimento extends BaseModel {
  provId: number;
  provIdPadre: number;
  provTitolo: string;
  provData: number;
  provImporto: number;
  provEnteProvenienza: string;
  provOggetto: string;
  provNumero: number;
  provNumero2: number;
  provNote: string;
  provLivId: number;
  finTipoDetId: number;
  provTipoId: number;
  provIdSostituito: number;
  enteId: number;
}

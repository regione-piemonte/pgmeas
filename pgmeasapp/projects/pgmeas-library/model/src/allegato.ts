/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface Allegato extends BaseModel {
  allegatoId: number;
  allegatoOggetto: string;
  allegatoProtocolloNumero: string;
  allegatoProtocolloData: number;
  fileNameUser: string;
  fileNameSystem: string;
  fileType: string;
  filePath: string;
  allegatoTipoId: number;
  intId: number;
  enteId: number;
}

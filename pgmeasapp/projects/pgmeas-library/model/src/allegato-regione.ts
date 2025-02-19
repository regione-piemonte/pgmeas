/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface AllegatoRegione extends BaseModel {
  idAllegato?: number;
  intAllegatoNumero?: string;
  intAllegatoData?: Date;
  fileNameUser?: string;
  fileType?: string;
  base64?: string;
}

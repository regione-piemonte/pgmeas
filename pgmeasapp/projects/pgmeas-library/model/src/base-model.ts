/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export interface BaseModel {
  dataCreazione?: number | null;
  dataModifica?: number | null;
  dataCancellazione?: number | null;
  utenteCreazione?: string;
  utenteModifica?: string;
  utenteCancellazione?: string;
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export interface ProrogaEnte {
  enteId: number,
  enteCodEsteso: string,
  enteDesc: string,
  faseProrogaInizio: string | null,
  faseProrogaFine: string
}

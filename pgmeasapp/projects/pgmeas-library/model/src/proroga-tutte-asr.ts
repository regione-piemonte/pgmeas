/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export interface ProrogaTutteAsr {
  anno: number,
  faseInizio: string,
  faseFine: string,
  faseProrogaInizio: string | null,
  faseProrogaFine: string
}

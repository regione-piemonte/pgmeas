/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export function yesNo(value: any): string {
  switch (value) {
    case true:
      return 'Sì';
    case false:
      return 'No';
    default:
      return value;
  }
}

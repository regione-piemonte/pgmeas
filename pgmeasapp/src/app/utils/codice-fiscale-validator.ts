/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
 
export function codiceFiscaleValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const codiceFiscale = control.value?.toUpperCase(); // Converti in maiuscolo
    if (!codiceFiscale) {
      return null; // Non valido se vuoto
    }
 
    // Controlla la lunghezza
    if (codiceFiscale.length !== 16) {
      return { codiceFiscaleInvalid: 'La lunghezza deve essere di 16 caratteri.' };
    }
 
    // Regex per il formato
    const regex = /^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/;
    if (!regex.test(codiceFiscale)) {
      return { codiceFiscaleInvalid: 'Il formato non è valido.' };
    }
 
    return null; // Valido
  };
}
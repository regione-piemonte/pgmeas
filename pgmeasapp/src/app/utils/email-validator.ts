/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
 
export function emailValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const email = control.value; // Converti in maiuscolo
    if (!email) {
      return null; // Non valido se vuoto
    }
 
    // Regex per il formato
    const regex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    if (!regex.test(email)) {
      return { emailInvalid: 'Il formato non è valido.' };
    }
 
    return null; // Valido
  };
}
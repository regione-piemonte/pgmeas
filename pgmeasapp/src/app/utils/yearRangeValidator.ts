/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function yearRangeValidator(currentYear: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    const minYear = currentYear;
    const maxYear = currentYear + 2;

    if (value < minYear || value > maxYear) {
      return { yearOutOfRange: { minYear, maxYear, actual: value } };
    }
    return null;
  };
}
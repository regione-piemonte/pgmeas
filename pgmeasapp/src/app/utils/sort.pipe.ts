/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sort',
})
export class SortPipe implements PipeTransform {
  transform(value: any, field?: string): any {
    if (Array.isArray(value) && value.length) {
      const type = typeof value[0];
      let compareFn;

      if (type === 'number') {
        compareFn = (a: number, b: number) => a - b;
      } else if (type === 'object' && field) {
        const fieldType = typeof value[0][field];

        if (fieldType === 'number') {
          compareFn = (a: any, b: any) => a[field] - b[field];
        } else {
          compareFn = (a: any, b: any) => a[field].localeCompare(b[field]);
        }
      }

      return value.sort(compareFn);
    }

    return value;
  }
}

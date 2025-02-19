/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'orDash',
})
export class OrDashPipe implements PipeTransform {
  transform(value: any): any {
    return value ?? '-';
  }
}

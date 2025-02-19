/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { MatPaginatorIntl } from "@angular/material/paginator";
import { Subject } from "rxjs";

@Injectable()
export class MyCustomPaginatorIntl implements MatPaginatorIntl {
  changes = new Subject<void>();

  firstPageLabel = 'Prima pagina';
  itemsPerPageLabel = 'Righe per pagina:';
  lastPageLabel = 'Ultima pagina';
  nextPageLabel = 'Pagina successiva';
  previousPageLabel = 'Pagina precedente';

  getRangeLabel(page: number, pageSize: number, length: number): string {
    if (length === 0) {
      return '1 di 1';
    }
    const amountPages = Math.ceil(length / pageSize);
    return `${page + 1} di ${amountPages}`;
  }
}

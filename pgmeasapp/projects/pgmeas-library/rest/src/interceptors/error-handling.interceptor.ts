/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpContextToken,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { MessageService } from 'src/app/services/message.service';

export const ERROR_HANDLING_MODE = new HttpContextToken<
  'page' | 'snackbar' | 'off'
>(() => 'page');

@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {
  constructor(private router: Router, private message: MessageService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    let mode = request.context.get(ERROR_HANDLING_MODE);

    if (mode !== 'off') {
      return next.handle(request).pipe(
        tap({
          error: (error) => {
            if(error.url.includes("invia") || error.url.includes("approva") || error.url.includes("rifiuta") || error.url.includes("elimina")) {
              mode = 'snackbar';
            }
            
            if (mode === 'snackbar') {
              if(error.status == 400) {
                let messaggio='';
                let dettaglio = error.error.detail
                ? error.error.detail
                    .map(
                      (obj: { chiave: string; valore: string }) =>
                        `${obj.valore}`
                    )
                    .join('\n')
                : '';
                messaggio = error.error.title + '\n' + dettaglio;
                // let errore = error.error ? error.error.title : 'Errore generico'
                this.message.error(messaggio);
              } else {
                this.message.error(error.message ?? 'Errore generico');
              }
            } else {
              const state =
                error instanceof HttpErrorResponse
                  ? {
                      message: error.message,
                      dump: JSON.stringify(error.error),
                    }
                  : undefined;
              if(!error.url.includes("invia") && !error.url.includes("approva") && !error.url.includes("rifiuta") && !error.url.includes("elimina")) {
                this.router.navigate(['/', 'error'], { state });
              }
            }
          },
        })
      );
    }

    return next.handle(request);
  }
}

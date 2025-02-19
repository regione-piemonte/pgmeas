/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Inject, Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse
} from '@angular/common/http';
import { catchError, finalize, Observable, tap } from 'rxjs';

import { AuthenticationService } from '@pgmeas-library/authentication';

@Injectable()
export class JwtUserInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthenticationService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let updatedRequest = request;

    if (this.authService.jwtUser) {
      updatedRequest = request.clone({
        setHeaders: {
          'jwt-user': this.authService.jwtUser
        }
      });
    }
    return next.handle(updatedRequest).pipe(
      tap(event => {
        // console.log('Event type:', event.constructor.name); // Logga il tipo di evento
        // console.log('Event instance:', event);
        if (event instanceof HttpResponse) {
          this.extractJwtUser(updatedRequest, event);
        }
      }),
      catchError(err => {
        // console.error('Errore nella pipeline HTTP:', err);
        // if (err.status === 401) {
        // this._jwtUser = null;
        // console.error('Errore 401: Unauthorized.');
        // }
        // Rilancia l'errore per permettere ad altre logiche di error handling di attivarsi

        this.extractJwtUser(updatedRequest, err);
        throw err;
      })
    );
  }

  extractJwtUser(request: HttpRequest<unknown>, event:HttpResponse<any>){
    const newJwtUser = event.headers.get('jwt-user');
    if (newJwtUser) {
      this.authService.jwtUser = newJwtUser; // Aggiorna il valore di jwt-user
      // console.log('JWT User Token - aggiornato:', updatedRequest.url); // Log della URL invocata
    } else {
      console.log('Token non trovato per la URL:', request.url); // Log anche se il token non è trovato
    }
  }

}

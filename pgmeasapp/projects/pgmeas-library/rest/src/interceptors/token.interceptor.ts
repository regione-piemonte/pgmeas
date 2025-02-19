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
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Environments } from '@pgmeas-library/contracts';
import { ENVIRONMENT } from '@pgmeas-library/src';

import { AuthenticationService } from '@pgmeas-library/authentication';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthenticationService,
    @Inject(ENVIRONMENT) private env: Environments
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {


    request = request.clone({
      setParams: {
        lcceToken: this.authService.token ?? ''
      }
    });

    return next.handle(request);
  }
}

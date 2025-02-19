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

import { ENVIRONMENT } from '@pgmeas-library/src';
import { Environments } from '@pgmeas-library/contracts';

@Injectable()
export class HttpUrlInterceptor implements HttpInterceptor {

  constructor(
    @Inject(ENVIRONMENT) private env: Environments
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    request = request.clone({
      url: request.url.replace(/^[~]/, this.env.baseUrl)
    });

    request = request.clone({
      url: request.url.replace(/^[*]/, this.env.simpleBaseUrl)
    });

    return next.handle(request);
  }
}

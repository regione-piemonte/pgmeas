/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RequestCache {
  private cache = new Map<string, HttpResponse<unknown>>();

  put(request: HttpRequest<unknown>, event: HttpResponse<unknown>) {
    this.cache.set(request.urlWithParams, event);
  }

  get(request: HttpRequest<unknown>) {
    return this.cache.get(request.urlWithParams);
  }
}

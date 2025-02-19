/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpInterceptor } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap, of } from "rxjs";
import { RequestCache } from "../services/request-cache.service";

function isCacheable(request: HttpRequest<unknown>): boolean {
  return request.url.startsWith('~/tipologiche/');
}

function sendRequest(
  request: HttpRequest<unknown>,
  next: HttpHandler,
  cache: RequestCache): Observable<HttpEvent<unknown>> {
  return next.handle(request).pipe(
    tap(event => {
      if (event instanceof HttpResponse) {
        cache.put(request, event);
      }
    })
  );
}

@Injectable()
export class CachingInterceptor implements HttpInterceptor {
  constructor(private cache: RequestCache) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!isCacheable(request)) { return next.handle(request); }

    const cachedResponse = this.cache.get(request);
    return cachedResponse ?
      of(cachedResponse) : sendRequest(request, next, this.cache);
  }
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { UserInfo } from '@pgmeas-library/model';
import { Observable } from 'rxjs';
import {ENVIRONMENT} from '@pgmeas-library/src';
import {Environments} from '@pgmeas-library/contracts';

@Injectable({
  providedIn: 'root',
})
export class UserRestService {
  private headers: {
    [k: string]: string;
  };

  constructor(
    private http: HttpClient,
    @Inject(ENVIRONMENT) private env: Environments
  ) {
    this.initHeaders();
  }

  private initHeaders() {
    this.headers = this.env.production ? {
      Authorization: 'Basic YXBpcmlscHJlcHJvZDphcGlyaWxldmF6JjAx'
    } : {
      Authorization: 'Basic YXBpcmlscHJlcHJvZDphcGlyaWxldmF6JjAx',
      'Shib-Iride-IdentitaDigitale': this.env.secrets.ShibIrideIdentitaDigitale
    };
  }

  getUserInfo(): Observable<UserInfo> {
    return this.http.get<UserInfo>(`*/user/info`, { headers: this.headers });
  }

  getUserInfoExt(): Observable<HttpResponse<UserInfo>> {
    return this.http.get<UserInfo>(`*/user/info`, {
      headers: this.headers,
      observe: 'response'
    });
  }
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';

import { AuthenticationService } from '@pgmeas-library/authentication';
import { NgxPermissionsService } from 'ngx-permissions';

@Injectable({
  providedIn: 'root'
})
export class AppInitializerService {
  constructor(
    private authService: AuthenticationService,
    private permissions: NgxPermissionsService
  ) {}

  init(): Promise<void> {
    return new Promise<void>(async (resolve, _) => {
      await this.authService.autoSignIn();

      const authStatus: boolean = this.authService.isAuth;

      if(authStatus) {
        const user = await this.authService.loadUser();
        const perms = user?.listaFunzionalita?.length ? user.listaFunzionalita.map(f => f.codice) : [];
        // const perms = ['OP-RicercaIntervento', 'OP-InsModA', 'OP-InsMonitorag', 'OP-ConsMonitorag', 'OP-ConsModA'];
        this.permissions.loadPermissions(perms);
      }

      resolve();
    });
  }
}

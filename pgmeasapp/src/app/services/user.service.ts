/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { AuthenticationService } from '@pgmeas-library/authentication';
import { UserInfo } from '@pgmeas-library/model';

const SUPER_USERS = ['D_REG', 'F_REG'];

const DIRIGENTE_REGIONALE = ['D_REG'];

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private auth: AuthenticationService) {}

  getUser(): UserInfo {
    return this.auth.user!;
  }

  isSuperUser(): boolean {
    return this.getUser().listaFunzionalita.some((f) =>
      SUPER_USERS.includes(f.codice)
    );
  }

  isDirigenteRegionale(): boolean {
    return this.getUser().listaFunzionalita.some((f) =>
      DIRIGENTE_REGIONALE.includes(f.codice)
    );
  }


  getLabel(): string {
    const codice = this.getUser().listaFunzionalita.find(
      (f) => f.codice_funzionalita_padre === null
    )?.codice;

    switch (codice) {
      case 'F_ASR':
        return 'Funzionario ASR';
      case 'D_ASR':
        return 'Dirigente ASR';
      case 'D_REG':
        return 'Dirigente regionale';
      case 'F_REG':
        return 'Funzionario regionale';
      default:
        return 'Utente sconosciuto';
    }
  }
}

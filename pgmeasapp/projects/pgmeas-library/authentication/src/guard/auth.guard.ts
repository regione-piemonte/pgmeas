/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

import { AuthenticationService } from '@pgmeas-library/authentication';

/**
 * Controlla lo stato dell'autenticazione.
 * Se l'utente è attualmente autenticato, abilita l'attivazione delle route, altrimenti esegue il redirect alla pagina
 * pubblica.
 *
 * @param route
 * @param state
 */
export const authGuard: CanActivateFn = (route, state) => {
  const router: Router = inject(Router);
  const authService: AuthenticationService = inject(AuthenticationService);

  if(authService.isAuth) return true;
  else return router.parseUrl('unauthenticated');

};

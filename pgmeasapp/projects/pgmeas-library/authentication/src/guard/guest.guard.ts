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
 * Se l'utente attualmente non è autenticato, abilita l'attivazione delle route
 *
 * @param route
 * @param state
 */
export const guestGuard: CanActivateFn = (route, state) => {
  const router: Router = inject(Router);
  const authService: AuthenticationService = inject(AuthenticationService);

  /* Si sta controllando se l'utente è un guest, di conseguenza non deve essere autenticato */
  if(!authService.isAuth) return true;
  else return router.parseUrl('');
};

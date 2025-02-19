/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Inject, Injectable} from '@angular/core';
import {BehaviorSubject, firstValueFrom, Observable,} from 'rxjs';

import {StorageService} from '@pgmeas-library/utils';
import {ENVIRONMENT} from '@pgmeas-library/src';
import {Environments} from '@pgmeas-library/contracts';

import {UserRestService} from '@pgmeas-library/rest';

import { UserInfo } from '@pgmeas-library/model';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private readonly STORAGE_KEY = {
    TOKEN: 'lcce-token'
  }

  private authStatus: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private _token: BehaviorSubject<string|null> = new BehaviorSubject<string|null>(null);
  private _user: BehaviorSubject<UserInfo|null> = new BehaviorSubject<UserInfo|null>(null);

  private _jwtUser: BehaviorSubject<string|null> = new BehaviorSubject<string|null>(null);

  constructor(
    private storage: StorageService,
    private userRestService: UserRestService,
    @Inject(ENVIRONMENT) private env: Environments
  ) { }

  public get token(): string|null {
    return this._token.value;
  }

  /**
   * Restituisce un observable contenente un booleano per effettuare la sottoscrizione allo stato di autentizaione.
   * Il suo valore cambia durante l'utilizzo dell'applicativo
   *
   * @version 1.0.0
   */
  public get isAuthObservable(): Observable<boolean> {
    return this.authStatus.asObservable();
  }

  /**
   * Restusce un boleano indicante l'attuale stato dell'autenticazione
   *
   * @version 1.0.0
   */
  public get isAuth(): boolean {
    return this.authStatus.value;
  }

  /**
   * Resituisce l'utente attualmente autenticato, null se non è stata eseguita l'autenticazione oppure se i dati
   * dell'utente non sono disponibili
   *
   * @version 1.0.0
   */
  public get user(): UserInfo|null {
    return this._user.value;
  }

  /**
   * Esegue la procedura di autoSignIn.
   * Il suo funzionamento si basa sulla procedura si SignIn
   *
   * @version 1.0.0
   */
  public async autoSignIn() {
    if(this.env.disableSignIn) {
      this.authStatus.next(true);
    }
    else {
      await this.signIn();
    }
  }

  /**
   * Esegue la procedura di SignIn verificando la presenza del token e la sua validità .
   * Nel caso in cui il token dovesse essere presente ma non valido, esegue la procedura di SignOut.
   *
   * Se l'autenticazione va a buon fine, memorizza il token nella lokal storage e lo propaga all'interno dell'app e
   * cambia lo stato del flag di autenticazione
   *
   * @version 1.0.0
   */
  public async signIn() {
    //const params:any = await lastValueFrom(this.getQueryParams());

    const url = new URL(window.location.href);
    const params = url.searchParams;

    const tokenString = params.get('lcceToken');

    //Verifica sulla presenza e validità del token
    if(params.has('lcceToken') && tokenString && tokenString.length > 0) {
      this._token.next(tokenString);
      this.storage.setItem(this.STORAGE_KEY.TOKEN, {token: this.token});
      this.authStatus.next(true);
    }
    else {
      //verifica della presenza del token in local storage per autenticazione già eseguita
      const token = this.storage.getItem(this.STORAGE_KEY.TOKEN);

      if(token && token.token) {
        if(token.token.length > 0) {
          this.authStatus.next(true);
          this._token.next(token.token);
          this._jwtUser.next(null);
        }
        else {
          //Il token in local Storage non è valido, viene eseguita la procedura di SignOut per la pulizia del token
          this._jwtUser.next(null);
          await this.signOut();
        }
      }
    }
  }

  /**
   * Esegue la cancellazione del token attualmente presente in local storage ed imposta tutte le variabili di
   * autenticazione a false
   *
   * @version 1.0.0
   */
  public async signOut() {
    this.storage.removeItem(this.STORAGE_KEY.TOKEN);
    this._token.next(null);
    this._user.next(null);
    this._jwtUser.next(null);
    this.authStatus.next(false);
  }

  /**
   * Esegue il caricamento delle informazioni dell'utente.
   * Se l'autenticazione non è stata eseguita oppure non è stato possibile recuperare i dati. restituisce null.
   *
   * @version 1.0.0
   */
  public async loadUser(){
    if(!this.isAuth) {
      this._user.next(null);
      this._jwtUser.next(null);
      return null;
    }
    // const user =  await firstValueFrom(this.userRestService.getUserInfo());
    const response = await firstValueFrom(this.userRestService.getUserInfoExt());
    const user = response.body; // Corpo della risposta (UserInfo)
    const headers = response.headers; // Intestazioni della risposta
    this.jwtUser = headers.get('jwt-user'); // Recupera il valore dell'intestazione 'jwt-user'

    this._user.next(user);
    return user;
  }

    /**
   * Getter per _jwtUser
   */
    public get jwtUser(): string | null {
      return this._jwtUser.value;
    }
  
    /**
     * Setter per _jwtUser
     */
    public set jwtUser(value: string | null) {
      this._jwtUser.next(value);
      // if (value!==null) { this.logexpirationDate(value); }
      
    }
    
    logexpirationDate(token: string): any {
      if (token) {
        // Il JWT è diviso in tre parti separate da un punto '.'
        const payloadBase64Url = token.split('.')[1]; // La seconda parte è il payload
        if (payloadBase64Url) {

          // Decodifica il payload base64-url
          const payloadBase64 = payloadBase64Url.replace(/-/g, '+').replace(/_/g, '/');
          const decodedPayload = atob(payloadBase64);

          // Restituisce l'oggetto JSON del payload
          const decoded = JSON.parse(decodedPayload);

          const expiration = decoded.exp; // Timestamp di scadenza (Unix epoch)
          if (expiration) {
            const expirationDate = new Date(expiration * 1000); // Converti in millisecondi
            console.log('Data di scadenza:', expirationDate);
          }
        }
      }
    }
}

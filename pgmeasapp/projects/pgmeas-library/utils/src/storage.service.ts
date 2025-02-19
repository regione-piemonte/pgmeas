/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private storage:any = localStorage;

  constructor() { }

  public setItem(key: string, value: any) {
    this.storage.setItem(key, JSON.stringify(value));
  }

  public getItem(key: string) {
    const item = this.storage.getItem(key);

    return item ? JSON.parse(item) : null;
  }

  public removeItem(key: string) {
    this.storage.removeItem(key);
  }

  public clear() {
    this.storage.clear();
  }

}

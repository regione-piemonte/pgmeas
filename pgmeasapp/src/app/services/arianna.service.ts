/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AriannaService {
  private storageKey = 'pgmeas_arianna';
  private titles: string[] = [];
  private urls: string[] = [];

  constructor() {
    this.loadTitlesFromStorage();
  }

  getTitles(): string[] {
    return this.titles;
  }

  addTitle(title: string, url:string): void {
    // Aggiunge il titolo solo se non già presente
    if (!this.titles.includes(title)) {
      this.titles.push(title);
      this.urls.push(url.split(/[?#]/)[0]);
      this.saveTitlesToStorage();
    }
  }

  getIndietroUrl(): string | null {
    if (this.urls.length > 1) {
      return this.urls[this.urls.length - 2];
    }
    return null;
  }

  resetTitles(): void {
    this.titles = [];
    this.urls = [];
    this.saveTitlesToStorage();
  }

  // Carica i titoli dal localStorage
  private loadTitlesFromStorage(): void {
    const storedTitles = localStorage.getItem(this.storageKey);
    if (storedTitles) {
      const date = JSON.parse(storedTitles);
      this.titles = date.titles;
      this.urls = date.urls;
    }
  }

  // Salva i titoli nel localStorage
  private saveTitlesToStorage(): void {
    const data = {
      titles: this.titles,
      urls: this.urls
    }
    localStorage.setItem(this.storageKey, JSON.stringify(data));
  }

  public editTitle(titleIn:string,nuovoTitolo:string){
    var index = this.titles.indexOf(titleIn);
    if (index !== -1) {
      this.titles[index] = nuovoTitolo;
    }
  }
}

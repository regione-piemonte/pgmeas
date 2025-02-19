/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'asset-loader',
  templateUrl: './asset-loader.component.html',
  styleUrls: ['./asset-loader.component.scss']
})

export class AssetLoaderComponent implements OnInit {
  templateName: string = '';
  htmlContent: string = '';
  
  constructor(private http: HttpClient,
        private router: Router,
        private route: ActivatedRoute,
    
  ) {}

  ngOnInit(): void {
    const currentRoute = this.getCurrentRoute(this.router.routerState.root);      
    this.templateName = currentRoute?.snapshot.data['page'];

    this.loadTemplate();
  }

  private getCurrentRoute(route: ActivatedRoute): ActivatedRoute | null {
    while (route.firstChild) {
      route = route.firstChild;
    }
    return route;
  }

  loadTemplate(): void {
    const filePath = `assets/html/${this.templateName}.html`; // Percorso ai tuoi file
    this.http.get(filePath, { responseType: 'text' }).subscribe({
      next: (data) => (this.htmlContent = data),
      error: (err) => (this.htmlContent = '<p>Errore nel caricamento del file.</p>'),
    });
  }
}

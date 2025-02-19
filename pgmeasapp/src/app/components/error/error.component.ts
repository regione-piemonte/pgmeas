/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {
  message = 'Errore generico';
  dump: string;

  constructor(private router: Router) {}

  ngOnInit() {
    const state = this.router.lastSuccessfulNavigation?.extras.state;

    if (state) {
      this.message = state['message'];
      this.dump = state['dump'];
    }
  }
}

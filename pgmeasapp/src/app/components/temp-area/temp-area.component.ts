/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { AuthenticationService } from '@pgmeas-library/authentication';


@Component({
  selector: 'temp-area',
  templateUrl: './temp-area.component.html',
  styleUrls: ['./temp-area.component.scss'],
})
export class TempArea implements OnInit {
  sel: string | null;
  constructor(
    private route: ActivatedRoute,
    private authService: AuthenticationService) {}

  ngOnInit() {
    this.sel = this.route.snapshot.paramMap.get('sel');
 }
  
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { UserInfo } from '@pgmeas-library/model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'profilo-utente',
  templateUrl: './profilo-utente.component.html',
  styleUrls: ['./profilo-utente.component.scss']
})

export class ProfiloUtenteComponent implements OnInit {
  user: UserInfo;

  constructor(
    private userService: UserService
  ) {}


  ngOnInit(): void {
    this.user = this.userService.getUser();
  }


}

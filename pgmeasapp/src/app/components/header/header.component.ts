/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RoutesRecognized } from '@angular/router';
import { Observable, filter, map } from 'rxjs';

import { AuthenticationService } from '@pgmeas-library/authentication';
import { UserInfo } from '@pgmeas-library/model';
import { ENVIRONMENT } from '@pgmeas-library/src';
import { Environments } from '@pgmeas-library/contracts';
import { AriannaService } from 'src/app/services/arianna.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  nomeCognome: string;
  toolbarTitle$: Observable<String>;
  titles: string[] = [];

  protected isAuth: boolean = false;

  @Input() showMenuButton: boolean = false;

  @Output() toggleMenu = new EventEmitter<void>();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private ariannaService: AriannaService,
    @Inject(ENVIRONMENT) private env: Environments
  ) {}

  ngOnInit() {
    this.toolbarTitle$ = this.router.events.pipe(
      filter((data) => data instanceof RoutesRecognized),
      map((data) => (data as any).state.root.firstChild.data.toolbarTitle)
    );

    this.router.events
    .pipe(filter((event) => event instanceof NavigationEnd))
    .subscribe((data) => {


      const testRoute = this.getCurrentRoute(this.router.routerState.root);

      const currentRoute = this.getCurrentRoute(this.router.routerState.root);

      //const data = currentRoute?.snapshot.data;
      const toolbarTitle = currentRoute?.snapshot.data['toolbarTitle'];
      const resetArianna = currentRoute?.snapshot.data['resetArianna'];

      // console.log('NavigationEnd     toolbarTitle:', toolbarTitle);
      // console.log('NavigationEnd     resetArianna:', resetArianna);
      // console.log('NavigationEnd     url:', this.router.url);

      // Reset lista se `resetArianna` è true
      if (resetArianna) {
        this.ariannaService.resetTitles();
      }

      // Aggiungi titolo se presente
      if (toolbarTitle) {
        if (Array.isArray(toolbarTitle)) {
          toolbarTitle.forEach(title => {
            this.ariannaService.addTitle(title, this.router.url);
          });
        } else {
          this.ariannaService.addTitle(toolbarTitle, this.router.url);
        }
      }

      // Aggiorna i titoli locali
      this.titles = this.ariannaService.getTitles();
    });

    this.authService.isAuthObservable.subscribe((status: boolean) => {
      this.isAuth = status;

      if (status) {
        const user = this.authService.user as UserInfo;
        this.nomeCognome = `${user.nome} ${user.cognome}`;
      }
    });
  }

  emitLogout() {
      location.href = this.env.simpleBaseUrl + "/user/logout";

  }

  private getCurrentRoute(route: ActivatedRoute): ActivatedRoute | null {
    while (route.firstChild) {
      route = route.firstChild;
    }
    return route;
  }
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';

import { AuthenticationService } from '@pgmeas-library/authentication';
import {MatSidenav} from "@angular/material/sidenav";
import { HeaderComponent } from './components/header/header.component';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  @ViewChild('sidenav', { static: false }) private sidenav: MatSidenav
  @ViewChild(HeaderComponent, { read: ElementRef }) header: ElementRef;

  isStickyMenu = false;
  isStrollable: boolean = false;

  protected isAuth: boolean = false;

  constructor(
    private router: Router, 
    private route: ActivatedRoute,
    private authService: AuthenticationService) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Controlla se la rotta è /home, ignorando l'hash (frammento URL)
        // this.isHomePage = event.urlAfterRedirects.split('#')[0] === '/home';
        const currentRoute = this.getCurrentRoute(this.router.routerState.root);      
        if (currentRoute?.snapshot.data['scroll'] !== undefined && currentRoute?.snapshot.data['scroll'] !== null) {
          this.isStrollable = currentRoute.snapshot.data['scroll'];
        } else {
          this.isStrollable = true;
        }
        
      }    });    
  }

  ngOnInit() {
    this.authService.isAuthObservable.subscribe((status: boolean) => this.isAuth = status);
  }

  @HostListener('window:resize')
  @HostListener('window:scroll')
  onResizeOrScroll() {
    this.isStickyMenu = window.scrollY > this.header.nativeElement.offsetHeight;
  }

  
  private getCurrentRoute(route: ActivatedRoute): ActivatedRoute | null {
    while (route.firstChild) {
      route = route.firstChild;
    }
    return route;
  }


  protected toggleMenu() {
    if(this.sidenav) {
      this.sidenav?.toggle()
    }
  }
}

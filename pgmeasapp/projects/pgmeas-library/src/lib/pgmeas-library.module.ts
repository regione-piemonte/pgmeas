/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { InjectionToken, ModuleWithProviders, NgModule } from '@angular/core';
import { PgmeasLibraryComponent } from './pgmeas-library.component';

import { Environments } from '../../contracts/src/environments';


export const ENVIRONMENT: InjectionToken<Environments> = new InjectionToken('ENVIRONMENT');


@NgModule({
  declarations: [
    PgmeasLibraryComponent
  ],
  imports: [
  ],
  exports: [
    PgmeasLibraryComponent
  ]
})
export class PgmeasLibraryModule {
  static forRoot(env: Environments): ModuleWithProviders<any> {
    return {
      ngModule: PgmeasLibraryModule,
      providers: [{
        provide: ENVIRONMENT,
        useValue: env
      }]
    }
  }
 }

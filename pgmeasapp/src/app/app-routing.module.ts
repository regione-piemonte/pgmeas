/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InterventiListComponent } from './components/interventi-list/interventi-list.component';
import { InterventiDetailComponent } from './components/interventi-detail/interventi-detail.component';
import { HomeComponent } from './components/home/home.component';
import { ModuloCsComponent } from './components/modulo-cs/modulo-cs.component';
import { ErrorComponent } from './components/error/error.component';
import { authGuard } from '@pgmeas-library/authentication';
import { UnauthenticatedPageComponent } from './components/unauthenticated-page/unauthenticated-page.component';
import { MissingPermissionComponent } from './components/missing-permission/missing-permission.component';
import { ngxPermissionsGuard } from 'ngx-permissions';
import { SuccessComponent } from './components/success/success.component';
import { GestioneComponent } from './components/programmazione/gestione/gestione.component';
import { TempArea } from './components/temp-area/temp-area.component';
import { ProgrammazioneInsertionComponent } from './components/programmazione-insertion/programmazione-insertion.component';
import { InterventiEditComponent } from './components/interventi-edit/interventi-edit.component';
import { InterventiEditRegioneComponent } from './components/interventi-edit-regione/interventi-edit-regione.component';
import { ManageProgrammingComponent } from './components/manage-programming/manage-programming.component';
import { ExtendProgrammingComponent } from './components/extend-programming/extend-programming.component';
import { AssetLoaderComponent } from './components/asset-loader/asset-loader.component';
import { ProfiloUtenteComponent } from './components/profilo-utente/profilo-utente.component';
import { PgmeasContextEnum } from './utils/pgmeas_context_enum';
import { GestioneApprovatoComponent } from './components/programmazione/gestione-approvato/gestione-approvato.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    data: {
      toolbarTitle: 'Home',
      resetArianna: true,
      scroll: false
    },
    canActivate: [authGuard],
  },
  {
    path: 'user-info',
    component: ProfiloUtenteComponent,
    data: {
      toolbarTitle: 'Profilo Utente',
      resetArianna: true,
      scroll: false
    },
    canActivate: [authGuard],
  },
  {
    path: 'ricerca-interventi',
    component: InterventiListComponent,
    data: {
      toolbarTitle: 'Ricerca Interventi',
      resetArianna: true,
      cache: true,
      context:PgmeasContextEnum.RICERCA_INTERVENTI,
      permissions: {
        only: ['OP-RicercaIntervento'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'programmazione-html',
    component: AssetLoaderComponent,
    data: {
      toolbarTitle: ['Programmazione'],
      page: 'programmazione',
      resetArianna: true,
      cache: true,
      scroll: false
    },
    canActivate: [authGuard],
  },
  {
    path: 'gestione-html',
    component: AssetLoaderComponent,
    data: {
      toolbarTitle: ['Gestione'],
      page: 'gestione',
      resetArianna: true,
      cache: true,
      scroll: false
    },
    canActivate: [authGuard],
  },
  {
    path: 'monitoraggio-html',
    component: AssetLoaderComponent,
    data: {
      toolbarTitle: ['monitoraggio'],
      page: 'monitoraggio',
      resetArianna: true,
      cache: true,
      scroll: false
    },
    canActivate: [authGuard],
  },
  {
    path: 'consultazione-interventi',
    component: InterventiListComponent,
    data: {
      toolbarTitle: ['Programmazione', 'Consultazione interventi'],
      resetArianna: true,
      cache: true,
      context:PgmeasContextEnum.PROGRAMMAZIONE_INTERVENTI,
      permissions: {
        only: ['OP-RicercaIntervento'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'consultazione-interventi/:id',
    component: InterventiDetailComponent,
    data: {
      toolbarTitle: 'Visualizza intervento',
      permissions: {
        only: ['OP-RicercaIntervento'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  // {
  //   path: 'ricerca-storico-interventi',
  //   component: InterventiListComponent,
  //   data: {
  //     toolbarTitle: 'Ricerca storico interventi',
  //     cache: true,
  //     permissions: {
  //       only: ['OP-RicercaIntervento'],
  //       redirectTo: '/missing-permission',
  //     },
  //   },
  //   canActivate: [ngxPermissionsGuard],
  // },
  // {
  //   path: 'richiesta-ammissione-finanziamento',
  //   component: InterventiListComponent,
  //   data: {
  //     toolbarTitle: 'Ammissione Finanziamento',
  //     resetArianna: true,
  //     context:PgmeasContextEnum.GESTIONE_INTERVENTI,
  //     cache: true,
  //     permissions: {
  //       only: ['OP-InsModA'],
  //       redirectTo: '/missing-permission',
  //     },
  //   },
  //   canActivate: [ngxPermissionsGuard],
  // },
  {
    path: 'richiesta-ammissione-finanziamento/:id',
    component: GestioneComponent,
    data: {
      toolbarTitle: 'dettaglio',
      permissions: {
        only: ['OP-InsModA'],
        redirectTo: '/missing-permission'
      }
    },
    canActivate: [ngxPermissionsGuard]
  },
  {
    path: 'consulta-richiesta-ammissione-finanziamento',
    component: InterventiListComponent,
    data: {
      toolbarTitle: 'Ammissione finanziamento',
      resetArianna: true,
      context:PgmeasContextEnum.GESTIONE_INTERVENTI,
      cache: true,
      permissions: {
        only: ['OP-ConsModA' , 'OP-RespModA', 'OP-ApprModA', 'OP-ModModA'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'richiesta-ammissione-finanziamento/:id/:allegatoId/:mode',
    component: GestioneComponent,
    data: {
      toolbarTitle: 'Richiesta Finanziamento',
      permissions: {
        only: ['OP-ConsModA', 'OP-InsModA'],
        redirectTo: '/missing-permission'
      },
    },
    canActivate: [ngxPermissionsGuard]
  },
  {
    path: 'richiesta-ammissione-finanziamento-approvato/:id/:allegatoId/:mode',
    component: GestioneApprovatoComponent,
    data: {
      toolbarTitle: 'Richiesta Finanziamento',
      permissions: {
        only: ['OP-ConsModA'],
        redirectTo: '/missing-permission'
      },
    },
    canActivate: [ngxPermissionsGuard]
  },
  /** menù statici */
  {
    path: 'stage-area/:sel',
    component: TempArea,
    data: {
      toolbarTitle: 'Area temporanea',
      resetArianna: true,
      permissions: {
        only: ['OP-ConsModA', 'OP-InsModA'],
        redirectTo: '/missing-permission'
      },
    },
    canActivate: [ngxPermissionsGuard]
  },
  /** fine menù statici */
  {
    path: 'modulo-c',
    component: InterventiListComponent,
    data: {
      toolbarTitle: ['Monitoraggio', 'Inserisci dati monitoraggio'],
      resetArianna: true,
      context:PgmeasContextEnum.MONITORAGGIO,
      cache: true,
      permissions: {
        only: ['OP-InsMonitorag'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'modulo-c/:id',
    component: ModuloCsComponent,
    data: {
      toolbarTitle: 'Dettaglio monitoraggio',
      permissions: {
        only: ['OP-InsMonitorag'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'consulta-monitoraggio',
    component: InterventiListComponent,
    data: {
      toolbarTitle: ['Monitoraggio', 'Consultazione monitoraggio'],
      resetArianna: true,
      context:PgmeasContextEnum.MONITORAGGIO,
      cache: true,
      permissions: {
        only: ['OP-ConsMonitorag'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'gestisci-programmazione',
    component: ManageProgrammingComponent,
    data: {
      toolbarTitle: ['Programmazione', 'Gestisci programmazione'] ,
      resetArianna: true,
      permissions: {
        only: ['OP-RegDefProg'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'proroga-programmazione',
    component: ExtendProgrammingComponent,
    data: {
      toolbarTitle: 'Proroga',
      permissions: {
        only: ['OP-RegDefProg'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'inserisci-intervento',
    component: ProgrammazioneInsertionComponent,
    data: {
      toolbarTitle: ['Programmazione', 'Inserisci intervento'],
      resetArianna: true,
      permissions: {
        only: ['OP-InsIntervento'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'modifica/:id',
    component: InterventiEditComponent,
    data: {
      toolbarTitle: 'Modifica intervento',
      permissions: {
        only: ['OP-ModIntervento'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'modifica-regione/:id',
    component: InterventiEditRegioneComponent,
    data: {
      toolbarTitle: 'Modifica intervento',
      permissions: {
        only: ['OP-ModIntervento'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    /* path: 'modulo-c/:id/:allegatoId/:mode', */
    path: 'modulo-c/:id/:mode',
    component: ModuloCsComponent,
    data: {
      toolbarTitle: 'Monitoraggio',
      permissions: {
        only: ['OP-ConsMonitorag', 'OP-InsMonitorag'],
        redirectTo: '/missing-permission',
      },
    },
    canActivate: [ngxPermissionsGuard],
  },
  {
    path: 'unauthenticated',
    component: UnauthenticatedPageComponent,
    data: { toolbarTitle: 'Non autenticato', resetArianna: true},
  },
  {
    path: 'missing-permission',
    component: MissingPermissionComponent,
    data: { toolbarTitle: 'Funzionalità non abilitata', resetArianna: true },
  },
  {
    path: 'success',
    component: SuccessComponent,
    data: {toolbarTitle: 'Successo'}
  },
  {
    path: 'error',
    component: ErrorComponent,
    data: { toolbarTitle: 'Errore' },
  },
  { path: '**', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}

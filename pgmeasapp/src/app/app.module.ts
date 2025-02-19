/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {APP_INITIALIZER, DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { MenuComponent } from './components/menu/menu.component';
import { InterventiListComponent } from './components/interventi-list/interventi-list.component';
import { InterventiDetailComponent } from './components/interventi-detail/interventi-detail.component';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTreeModule } from '@angular/material/tree';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSidenavModule } from '@angular/material/sidenav';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatRadioModule} from '@angular/material/radio';
import {MatChipsModule} from '@angular/material/chips';
import {MAT_SNACK_BAR_DEFAULT_OPTIONS, MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTabsModule} from '@angular/material/tabs';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatListModule} from '@angular/material/list';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { HomeComponent } from './components/home/home.component';
import { FooterComponent } from './components/footer/footer.component';
import { MyCustomPaginatorIntl } from './services/my-custom-paginator-intl';

import { PgmeasLibraryModule } from '@pgmeas-library/src';
import { environment } from 'src/environments/environment';
import { NgxCurrencyDirective } from "ngx-currency";
import {
  HttpUrlInterceptor,
  TokenInterceptor,
  CachingInterceptor,
  ErrorHandlingInterceptor
} from '@pgmeas-library/rest';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouteReuseStrategy } from '@angular/router';
import { CustomReuseStrategy } from './custom-reuse-strategy';
import { ModuloCsComponent } from './components/modulo-cs/modulo-cs.component';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { ErrorComponent } from './components/error/error.component';
import { UnauthenticatedPageComponent } from './components/unauthenticated-page/unauthenticated-page.component';
import { YesNoPipe } from './utils/yes-no.pipe'
import {AppInitializerService} from "./services/app-initializer.service";
import { OrDashPipe } from './utils/or-dash.pipe';
import { NgxPermissionsModule } from 'ngx-permissions';
import { MissingPermissionComponent } from './components/missing-permission/missing-permission.component';
import { SuccessComponent } from './components/success/success.component';
import { MatDialogModule } from '@angular/material/dialog';
import { AllegatiComponent } from './components/programmazione/gestione/allegati/allegati.component';
import { AppaltoComponent } from './components/programmazione/gestione/appalto/appalto.component';
import { DeliberazioniComponent } from './components/programmazione/gestione/deliberazioni/deliberazioni.component';
import { DichiarazioneAppalatabilitaComponent } from './components/programmazione/gestione/dichiarazione-appalatabilita/dichiarazione-appalatabilita.component';
import { GestioneComponent } from './components/programmazione/gestione/gestione.component';
import { PianoCronologicoComponent } from './components/programmazione/gestione/piano-cronologico/piano-cronologico.component';
import { PianoCronologicoStrutturaComponent } from './components/programmazione/gestione/piano-cronologico-struttura/piano-cronologico-struttura.component';
import { ModaleAggiuntaFinanziamentoComponent } from './components/programmazione/gestione/piano-finanziario/modale-aggiunta-finanziamento/modale-aggiunta-finanziamento.component';
import { PianoFinanziarioComponent } from './components/programmazione/gestione/piano-finanziario/piano-finanziario.component';
import { QuadroEconomicoComponent } from './components/programmazione/gestione/quadro-economico/quadro-economico.component';
import { ResponsabiliComponent } from './components/programmazione/gestione/responsabili/responsabili.component';
import { AutoCommaDirective } from './utils/auto-comma.directive';
import { YearSelectionDialogComponent } from './components/year-selection-dialog/year-selection-dialog.component';
import { ForceTwoDecimalsDirective } from './utils/force-two-decimals.directive';
import { LoadingDialogComponent } from './components/loading-dialog/loading-dialog.component';
import { SortPipe } from './utils/sort.pipe';
import { SuccessDialogComponent } from './components/success-dialog/success-dialog.component';
import { TempArea } from './components/temp-area/temp-area.component';
import { ProgrammazioneInsertionComponent } from './components/programmazione-insertion/programmazione-insertion.component';
import { ModaleConfermaComponent } from './components/programmazione/modale-conferma/modale-conferma.component';
import { ModaleCopiaInterventoComponent } from './components/programmazione-insertion/modale-copia-intervento/modale-copia-intervento.component';
import { InterventiEditComponent } from './components/interventi-edit/interventi-edit.component';
import { ModaleAzioneComponent } from './components/modale-azione/modale-azione.component';
import { ModaleAzioneFinanziamentoComponent } from './components/modale-azione-finanziamento/modale-azione-finanziamento.component';
import { InterventiEditRegioneComponent } from './components/interventi-edit-regione/interventi-edit-regione.component';
import { ManageProgrammingComponent } from './components/manage-programming/manage-programming.component';
import { ModaleProgrammazioneComponent } from './components/modale-programmazione/modale-programmazione.component';
import { ExtendProgrammingComponent } from './components/extend-programming/extend-programming.component';
import { ModaleExtendProgrammingComponent } from './components/modale-extend-programming/modale-extend-programming.component';
import { NumericLimiterDirective } from './utils/numeric-limiter.directive';
import { ModaleErroreComponent } from './components/modale-errore/modale-errore.component';
import { QuadroEconomicoCentralizedComponent } from './components/quadro-economico-centralized/quadro-economico-centralized.component';
import { JwtUserInterceptor } from '@pgmeas-library/rest/src/interceptors/jwt-user.interceptor';
import { UppercaseDirective } from './utils/upper-case.directive';
import { AssetLoaderComponent } from './components/asset-loader/asset-loader.component';
import { ProfiloUtenteComponent } from './components/profilo-utente/profilo-utente.component';
import { AllegatiRegioneComponent } from './components/programmazione/gestione/allegati-regione/allegati-regione.component';
import { AddStruttureComponent } from './components/add-strutture/add-strutture.component';
import { ModaleStruttureComponent } from './components/modale-strutture/modale-strutture.component';
import { GestioneApprovatoComponent } from './components/programmazione/gestione-approvato/gestione-approvato.component';


registerLocaleData(localeIt, 'it');

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuComponent,
    InterventiListComponent,
    InterventiDetailComponent,
    AssetLoaderComponent,
    HomeComponent,
    FooterComponent,
    ModuloCsComponent,
    ErrorComponent,
    UnauthenticatedPageComponent,
    YesNoPipe,
    OrDashPipe,
    MissingPermissionComponent,
    SuccessComponent,
    GestioneComponent,
    GestioneApprovatoComponent,
    QuadroEconomicoComponent,
    DichiarazioneAppalatabilitaComponent,
    AllegatiComponent,
    ResponsabiliComponent,
    AppaltoComponent,
    DeliberazioniComponent,
    PianoFinanziarioComponent,
    PianoCronologicoComponent,
    PianoCronologicoStrutturaComponent,
    AllegatiRegioneComponent,
    AutoCommaDirective,
    NumericLimiterDirective,
    ModaleAggiuntaFinanziamentoComponent,
    ModaleConfermaComponent,
    ModaleCopiaInterventoComponent,
    YearSelectionDialogComponent,
    ForceTwoDecimalsDirective,
    LoadingDialogComponent,
    SortPipe,
    SuccessDialogComponent,
    TempArea,
    ProgrammazioneInsertionComponent,
    InterventiEditComponent,
    ModaleAzioneComponent,
    ModaleAzioneFinanziamentoComponent,
    InterventiEditRegioneComponent,
    ManageProgrammingComponent,
    ModaleProgrammazioneComponent,
    ExtendProgrammingComponent,
    ModaleExtendProgrammingComponent,
    ModaleErroreComponent,
    UppercaseDirective,
    ProfiloUtenteComponent,
    AddStruttureComponent,
    ModaleStruttureComponent,
  ],
  imports: [
    BrowserModule,
    PgmeasLibraryModule.forRoot(environment),
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
    MatSelectModule,
    MatTableModule,
    MatToolbarModule,
    MatTreeModule,
    MatProgressSpinnerModule,
    MatSortModule,
    MatMenuModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatCardModule,
    MatSlideToggleModule,
    MatSidenavModule,
    MatTooltipModule,
    MatRadioModule,
    MatChipsModule,
    MatSnackBarModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatDialogModule,
    MatListModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    QuadroEconomicoCentralizedComponent,
    NgxPermissionsModule.forRoot(),
    NgxCurrencyDirective
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'it-IT' },
    { provide: DEFAULT_CURRENCY_CODE, useValue: 'EUR' },
    // { provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 10000} },
    {
      provide: MatPaginatorIntl,
      useClass: MyCustomPaginatorIntl
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CachingInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpUrlInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtUserInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlingInterceptor,
      multi: true
    },
    {
      provide: RouteReuseStrategy,
      useClass: CustomReuseStrategy
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      deps: [ AppInitializerService ],
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function initializeApp(appInitService: AppInitializerService) {
  return (): Promise<void> => appInitService.init();
}

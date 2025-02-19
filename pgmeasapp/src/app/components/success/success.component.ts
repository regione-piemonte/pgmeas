/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { RegistryApiService } from 'src/app/services/registry-api.service';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { ModuloTipo } from '@pgmeas-library/model/src/modulo-tipo';
import { catchError, combineLatest, of, tap } from 'rxjs';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.scss']
})
export class SuccessComponent implements OnInit {

  @Input() message: string;
  @Input() selettore: string;
  @Input() intId: number | null;
  @Input() canEditRegione:boolean|null;
  @Input() moduloCod:string|null;

  loading = false;
  moduloTipoList:ModuloTipo[];

  constructor(private router: Router,
    private projectApiService: ProjectApiService,private registryApiService:RegistryApiService) {}

  ngOnInit() {
    this.loading=true;
    const state = this.router.lastSuccessfulNavigation?.extras.state;
    // si puo' usare il tap per definire degli step durante la subscription
    //https://rxjs.dev/api/operators/tap
    this.registryApiService.getModuloTipoList()
      .pipe(
        tap(response => {
          this.moduloTipoList = response; // Imposta la lista
          this.loading = false
        }),
        catchError(err => {
          console.error(err);
          return of([]); // Restituisce una lista vuota in caso di errore
        }),
        tap(() => this.loading = false) // Side effect alla fine
      )
      .subscribe();

    if (state) {
      this.message = state['message'];
    }
  }



  tornaAllaRicerca() {
    this.router.navigateByUrl('/consultazione-interventi', { skipLocationChange: false }).then(() => {
      this.router.navigate(['/consultazione-interventi'], { queryParams: { reload: 'last' } });
    });
  }

  tornaAllaRicercaModulo(){
    this.router.navigateByUrl('/consulta-richiesta-ammissione-finanziamento', { skipLocationChange: false }).then(() => {
      this.router.navigate(['/consulta-richiesta-ammissione-finanziamento'], { queryParams: { reload: 'last' } });
    });
  }
  stampaModulo(){
    if(this.intId){
      this.loading=true;
      this.projectApiService.downloadModuloAById(this.intId).subscribe(response => {
        if(response.body){
            const contentDisposition = response.headers.get('Content-Disposition');

            const filename = contentDisposition
              ? contentDisposition.split(';').find(n => n.includes('filename'))?.split('=')[1].trim()
              : 'default-filename.pdf';

            const blob = new Blob([response.body], { type: response.body.type });
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = filename ? filename.replace(/"/g, '') : 'file.pdf';
            link.click();
        }
        this.message = 'Pdf scaricato con successo';
        this.loading=false;
      }, err=>{
        this.loading=false;
      });
    }
  }


  tornaModificaInterventoAsr() {
    if (this.intId !== null) {
      console.log(`Forcing reload to /modifica/${this.intId}`);
      this.router.navigateByUrl('/consultazione-interventi', { skipLocationChange: true }).then(() => {
        this.router.navigate(['/modifica', this.intId]);
      });
    } else {
      console.warn('intId is null, cannot navigate to modifica.');
    }
  }

  tornaModificaInterventoRegione() {

    if (this.intId !== null) {
      console.log(`Forcing reload to /modifica-regione/${this.intId}`);
      this.router.navigateByUrl('/consultazione-interventi', { skipLocationChange: true }).then(() => {
        this.router.navigate(['/modifica-regione', this.intId]);
      });
    } else {
      console.warn('intId is null, cannot navigate to modifica.');
    }

  }

  tornaModificaModuloAAsr() {
    if (this.intId !== null) {
      // dato l'intervento id cerco il modulo corrispondente in base al parametro moduloTipoCod
      const moduloTipo= this.moduloCod ? this.getModuloByCod(this.moduloCod) : null;
      if(moduloTipo){
        console.log(`Forcing reload to /torna al modulo/${this.intId}/moduloTipo/MOD_A`);
          this.router.navigateByUrl('/consultazione-interventi', { skipLocationChange: true }).then(() => {
            if(this.canEditRegione){
              this.router.navigate(['/richiesta-ammissione-finanziamento', this.intId,moduloTipo.moduloId,'editRegione']);
            } else {
              this.router.navigate(['/richiesta-ammissione-finanziamento', this.intId,moduloTipo.moduloId,'editASR']);
            }
        });
      }else{
        console.warn('modulo tipo');
      }
    } else {
      console.warn('intId is null, cannot navigate to modifica.');
    }
  }

  stampaIntervento(){
    if(this.intId){
      this.loading=true;
      this.projectApiService.stampaIntervento(this.intId).subscribe(response => {
        if(response.body){
            const contentDisposition = response.headers.get('Content-Disposition');

            const filename = contentDisposition
              ? contentDisposition.split(';').find(n => n.includes('filename'))?.split('=')[1].trim()
              : 'default-filename.pdf';

            const blob = new Blob([response.body], { type: response.body.type });
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = filename ? filename.replace(/"/g, '') : 'file.pdf';
            link.click();
        }
        this.message = 'Pdf scaricato con successo';
        this.loading=false;
      }, err=>{
        this.loading=false;
      });
    }
  }


  getModuloByCod( codice: string | null): ModuloTipo | undefined {
    if (!this.moduloTipoList || !codice) {
      return undefined;
    }
    return this.moduloTipoList.find(modulo => modulo.moduloCod === codice);
  }
}

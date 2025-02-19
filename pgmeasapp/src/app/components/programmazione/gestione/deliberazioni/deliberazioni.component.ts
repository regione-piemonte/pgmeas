/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject, takeUntil} from 'rxjs';
import {Ente, Intervento, InterventoStruttura, Struttura} from '@pgmeas-library/model';
import { AllegatoLightExt } from '@pgmeas-library/model/src/allegato-light-ext';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { LoadingService } from "src/app/services/loading.service";

@Component({
  selector: 'app-deliberazioni',
  templateUrl: './deliberazioni.component.html',
  styleUrls: ['./deliberazioni.component.scss']
})
export class DeliberazioniComponent implements OnInit, OnDestroy, OnChanges {
  @Input() intervento: Intervento;
  @Input() canEdit = true;
  @Input() allegati: any[] = [];
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  @Output() formValidity: EventEmitter<boolean> = new EventEmitter<boolean>();
  protected appaltoForm: FormGroup;
  private destroy$: Subject<boolean> = new Subject<boolean>();

  protected allegatoProvvedimentoAziendaleApprovazione: AllegatoLightExt[];
  protected allegatoRelazioneTecnica: AllegatoLightExt[];
  protected allegatoNullaOsta: AllegatoLightExt[];
  protected allegatoDecretoMinisteriale: AllegatoLightExt[];
  protected allegatoDeliberaApprovazione: AllegatoLightExt[]; //NON VIENE USATO
  protected allegatoDgrApprovazione: AllegatoLightExt[]; 
  protected allegatoDgrPropostaCR: AllegatoLightExt[];
  protected allegatoDcrApprovazione: AllegatoLightExt[];
  protected allegatiDeterminazioniDirigenziali: AllegatoLightExt[];

  constructor(
    private projectApiService: ProjectApiService, 
    private loadingService: LoadingService,  
  ) {
    this.appaltoForm = new FormGroup({

      })
    ;
  }


  ngOnChanges(changes: SimpleChanges): void {
   
  }  

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }

  ngOnInit(): void {
    if (this.intervento) {
      if(this.intervento.allegatiDeterminazioniDirigenziali!=null){
        this.allegatiDeterminazioniDirigenziali = this.intervento.allegatiDeterminazioniDirigenziali;
      }
      if(this.intervento.allegatoDcrApprovazione!=null){
        this.allegatoDcrApprovazione = this.intervento.allegatoDcrApprovazione;
      }
      if(this.intervento.allegatoDecretoMinisteriale!=null){
        this.allegatoDecretoMinisteriale = this.intervento.allegatoDecretoMinisteriale;
      }
      if(this.intervento.allegatoDeliberaApprovazione!=null){
        this.allegatoDeliberaApprovazione = this.intervento.allegatoDeliberaApprovazione;
      }
      if(this.intervento.allegatoDgrApprovazione!=null){
        this.allegatoDgrApprovazione = this.intervento.allegatoDgrApprovazione;
      }
      if(this.intervento.allegatoDgrPropostaCR!=null){
        this.allegatoDgrPropostaCR = this.intervento.allegatoDgrPropostaCR;
      }
      if(this.intervento.allegatoNullaOsta!=null){
        this.allegatoNullaOsta = this.intervento.allegatoNullaOsta;
      }
      // if(this.intervento.allegatoProvvedimentoAziendaleApprovazione!=null){
      //   this.allegatoProvvedimentoAziendaleApprovazione = this.intervento.allegatoProvvedimentoAziendaleApprovazione;
      // }
      // if(this.intervento.allegatoRelazioneTecnica!=null){;
      //   this.allegatoRelazioneTecnica = this.intervento.allegatoRelazioneTecnica;
      // }
    }
  }

  downloadPdf(id: number) {
    this.loadingService.on();

    this.projectApiService.downloadAllegatoById(id).subscribe({
      next: (file) => {
        const tab = window.open();

        if (tab) {
          const src = `data:application/pdf;base64,${encodeURI(file.base64)}`;
          const html = `<iframe src="${src}" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;"></iframe>`;
          tab.document.write(html);
        }

        this.loadingService.off();
      },
      error: () => this.loadingService.off(),
    });
  }
}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

import {AfterViewInit, ChangeDetectorRef, Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {FinanziamentoQuadroEconomico} from '@pgmeas-library/model/src/finanziamento-quadro-economico';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ClassificazioneTree, InterventoStruttura, Struttura, UserInfo} from '@pgmeas-library/model';
import {combineLatest, debounceTime, Subject, takeUntil} from 'rxjs';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { UserService } from 'src/app/services/user.service';
import { quadroEconomicoService } from 'src/app/services/quadroEconomico.service';

const DELAY = 1000;

@Component({
  selector: 'app-quadro-economico',
  templateUrl: './quadro-economico.component.html',
  styleUrls: ['./quadro-economico.component.scss']
})

export class QuadroEconomicoComponent implements OnInit, OnDestroy {

  @Input() interventoStruttura: InterventoStruttura[] = [];
  @Input() denominazioneStruttura: Struttura[] = [];
  @Input() canEdit = true;
  @Input() canEditRegione = true;
  @Input() appaltoIntegratoStruttura: { idStruttura: number, appaltoIntegrato: boolean };
  @Output() quadroEconomicoValue: EventEmitter<any> = new EventEmitter<any>();
  protected items: any[] = [];
  protected itemsGroup: any[] = [];
  protected quadroEconomicoForm: FormGroup;
  protected readonly Object = Object;
  protected totalSum = 0;
  private destroy$: Subject<boolean> = new Subject<boolean>();
  strutture: FormArray;
  idIntervento: number;
  quadroEconomicoList: ClassificazioneTree[];
  user: UserInfo;
  strutturaList: Struttura[];
  arrayAppaltiIntegrati: { idStruttura: number, appaltoIntegrato: boolean }[] = [];

  constructor(
    private fb: FormBuilder,
    private registryApiService: RegistryApiService,
    private userService: UserService,
    private quadroEconomicoService: quadroEconomicoService,
    private cdr: ChangeDetectorRef) {}

  ngOnDestroy(): void {
    this.destroy$.next(false);
    this.destroy$.complete();
  }

  disableRegionali(): boolean {
    return this.canEditRegione || (!this.canEdit && !this.canEditRegione);
  }

  ngOnInit(): void {

    this.createAppaltiIntegratiArray();
    this.user = this.userService.getUser();
    combineLatest([
      this.registryApiService.getClassificazioneTreeList('QE'),
      this.registryApiService.getStrutturaListByInterventoId(this.interventoStruttura[0].intId),
    ]).subscribe(
      ([
        quadroEconomicoTree,
        strutturaList
      ]) => {
        this.strutturaList = strutturaList;
        // Logica nel caso di ricezione di dati
        this.quadroEconomicoList = quadroEconomicoTree;
        this.quadroEconomicoForm = this.quadroEconomicoBuilder();
        this.buildStruttureFormGroup();
        if(this.interventoStruttura.length) {
          this.idIntervento = this.interventoStruttura[0].intId;
        }
      }
    );


  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['appaltoIntegratoStruttura'] && !changes['appaltoIntegratoStruttura'].firstChange) {
      console.log('Valore di appaltoIntegratoStruttura cambiato:', this.appaltoIntegratoStruttura);
      for (let strutturaAppalti of this.arrayAppaltiIntegrati) {
        if(this.appaltoIntegratoStruttura.idStruttura === strutturaAppalti.idStruttura) {
          strutturaAppalti.appaltoIntegrato = this.appaltoIntegratoStruttura.appaltoIntegrato;
        }
      }
    }

    console.log(this.arrayAppaltiIntegrati);
  }

  private quadroEconomicoBuilder(): FormGroup {
    const formGroup = this.fb.group({
    });

    return formGroup;
  }

  private buildStruttureFormGroup() {
    this.strutture = this.fb.array([]);
    for(let struttura of this.interventoStruttura){
      let strutturaSelezionata = this.strutturaList.find(str => str.strId==struttura.strId);
      let strutturaFormGroup = this.fb.group({
        struttura: [strutturaSelezionata],
        listaQuadroEconomico: this.fb.array([])
      });

      this.populateQuadroEconomico(strutturaFormGroup);

      this.strutture.push(strutturaFormGroup);
    }

      // Ascolta i cambiamenti a livello globale del FormArray strutture
    this.strutture.valueChanges.subscribe(() => {

      const value = this.strutture.getRawValue();
      let valoriDaEmettere = [];
      if(value && value.length) {
          for (let quadro of value) {
            if(quadro.struttura) {
              let idStruttura;
              for(let interventoStruttura of this.interventoStruttura) {
                if (quadro.struttura.strId === interventoStruttura.strId) {
                  idStruttura = interventoStruttura.intStrId
                }
              }
              let mappa = this.quadroEconomicoService.buildQuadroEconomicoMap(quadro.quadroEconomicoArray);
              let valore = {
                idStruttura: idStruttura,
                valori: mappa
              }
              valoriDaEmettere.push(valore);
            }
          }
      }

      // Emetti l'evento o esegui logica specifica
      this.quadroEconomicoValue.emit({ valoriDaEmettere });
    });

  }

  getStrutturaForm(index: number) {
    return this.strutture.at(index) as FormGroup;
  }

  populateQuadroEconomico(formGroup: FormGroup) {
    const listaQuadroEconomicoArray = formGroup.get('listaQuadroEconomico') as FormArray;
    this.quadroEconomicoList.forEach(quadro => {
      const interventoGroup = this.fb.group({
        id: [quadro.classifTreeId],
        valoreNumerico: [quadro.valoreNumerico, [Validators.min(0)]],
        livello: [quadro.livello],
        descrizione: [quadro.descrizione],
        selezionabile: [quadro.selezionabile]
      });
      listaQuadroEconomicoArray.push(interventoGroup);
    });
  }

  private createAppaltiIntegratiArray() {
    if(this.interventoStruttura.length) {
      this.interventoStruttura.forEach(struttura => {
        this.arrayAppaltiIntegrati.push({idStruttura: struttura.intStrId, appaltoIntegrato: struttura.appaltoIntegrato});
      })
    }
  }

}

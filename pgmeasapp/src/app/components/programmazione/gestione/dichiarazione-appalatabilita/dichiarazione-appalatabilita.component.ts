/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {FinanziamentoQuadroEconomico} from '@pgmeas-library/model/src/finanziamento-quadro-economico';
import {ClassificazioneTree, Intervento, InterventoStruttura, InterventoTipoDet, Struttura} from '@pgmeas-library/model';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Subject, takeUntil} from 'rxjs';
import { RegistryApiService } from 'src/app/services/registry-api.service';

@Component({
  selector: 'app-dichiarazione-appalatabilita',
  templateUrl: './dichiarazione-appalatabilita.component.html',
  styleUrls: ['./dichiarazione-appalatabilita.component.scss'],
})
export class DichiarazioneAppalatabilitaComponent
  implements  OnInit, OnDestroy
{
  @Input() classificazioneTreeDA: ClassificazioneTree[];
  @Input() classificazioneTreeDAA: ClassificazioneTree[];
  @Input() interventoTipoDet: InterventoTipoDet[];
  @Input() interventoStruttura: InterventoStruttura[];
  @Input() denominazioneStruttura: Struttura[] = [];
  @Input() intervento: Intervento;
  @Input() canEdit = true;
  @Input() canEditRegione = true;
  @Input() tipoModelloInit: string;
  @Input() tipoModelloChild: string;
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  protected interventiStruttura: string[];
  protected modA: boolean;
  protected dichiarazioneDiAppaltabilitaModelloA: FormGroup;
  protected dichiarazioneDiAppaltabilitaModelloAA: FormGroup;
  protected  attrezzatureComponent:InterventoTipoDet[] = [];
  private destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private fb: FormBuilder, private registryService: RegistryApiService) {}


  ngOnDestroy(): void {
    this.destroy$.next(false);
    this.destroy$.complete();
  }

  updateModelloTipo(data: string) {
    // console.log(this.dichiarazioneDiAppaltabilita)
    // console.log('DICH APPALTABILITA updateModelloTipo: ' + data);
    this.tipoModelloChild = data;

    //QUI RICEVE IL VALORE DALLA CLASSE APPALTO
    if (this.tipoModelloChild != undefined && this.tipoModelloChild != null) {
      if (this.tipoModelloChild === 'MOD_A') {
        this.modA = true;
        const formValue = {
          treeDA: this.dichiarazioneDiAppaltabilitaModelloA.value,
        };

        this.formData.emit(formValue);
      } else {
        this.modA = false;
        const formValue = {
          treeDA: this.dichiarazioneDiAppaltabilitaModelloAA.value,
        };

        this.formData.emit(formValue);
      }
    }
  }



  ngOnInit(): void {
    //QUI RICEVE IL VALORE DALL'INIT DATA DI GESTIONE
    if (this.intervento.moduloTipo != undefined && this.intervento.moduloTipo != null) {
      if (this.intervento.moduloTipo === 'MOD_A') {
        this.modA = true;
      } else {
        this.modA = false;
      }
    }



    this.dichiarazioneDiAppaltabilitaModelloA =this.fb.group({
        listaDichiarazioneDiAppaltabilitaModelloA: this.fb.array([])
      }
    )
    this.interventoStruttura.forEach(interventoStruttura =>{
      let listaDichiarazioniCurr:FormGroup[]=[]

      this.classificazioneTreeDA.forEach((listaDichA) => {
        let descrizione = listaDichA.descrizione
        if(listaDichA.descrizione.indexOf('{')!=-1){
          descrizione=listaDichA.descrizione.substring(0,listaDichA.descrizione.indexOf('{'))
        }
        this.attrezzatureComponent =[];
        this.interventoTipoDet.forEach(interventoTipo=>{
          this.intervento.descrizioniAttrezzature.forEach(attrezzaturaId =>
          {
            if(interventoTipo.intTipoDetId==attrezzaturaId){
              this.attrezzatureComponent.push(interventoTipo)
            }
          }
          );
        })
        let dichiarazioneCurr:FormGroup =  this.fb.group({
          intstrDaTreeSelezionata:  new FormControl({
            value: interventoStruttura.dichiarazioneAppaltabilita[listaDichA.classifTreeId]==undefined?false:interventoStruttura.dichiarazioneAppaltabilita[listaDichA.classifTreeId].intstrDaTreeSelezionata, // Booleano predefinito
            disabled: !this.canEdit || this.canEditRegione
          }),
          descrizione: [descrizione],
          classifTreeId: [listaDichA.classifTreeId],
          dataValidazione: [''],
          atto: [''],
          attrezzature: this.attrezzatureComponent,
        });
        listaDichiarazioniCurr.push(dichiarazioneCurr)
      })
      let dichiarazioneStruttura = this.fb.group(
        {
          listaDichiarazioni: this.fb.array(listaDichiarazioniCurr),
          idStruttura: interventoStruttura.intStrId
        }
      );
      (this.dichiarazioneDiAppaltabilitaModelloA.get('listaDichiarazioneDiAppaltabilitaModelloA')as FormArray).push(dichiarazioneStruttura);
    })

    this.dichiarazioneDiAppaltabilitaModelloA.valueChanges
       .pipe(takeUntil(this.destroy$))
       .subscribe({
         next: (): void => {
           const formValue = {
             treeDA: this.dichiarazioneDiAppaltabilitaModelloA.value,
           };
           this.formData.emit(formValue);
         },
       });


    this.dichiarazioneDiAppaltabilitaModelloAA =this.fb.group({
        listaDichiarazioneDiAppaltabilitaModelloAA: this.fb.array([])
      }
    )
    this.interventoStruttura.forEach(interventoStruttura =>{
      let listaDichiarazioniCurr:FormGroup[]=[]

      this.classificazioneTreeDAA.forEach((listaDichA) => {
        let descrizione = listaDichA.descrizione
        if(listaDichA.descrizione.indexOf('{')!=-1){
          descrizione=listaDichA.descrizione.substring(0,listaDichA.descrizione.indexOf('{'))
        }
        this.attrezzatureComponent =[];
        this.interventoTipoDet.forEach(interventoTipo=>{
          this.intervento.descrizioniAttrezzature.forEach(attrezzaturaId =>
          {
            if(interventoTipo.intTipoDetId==attrezzaturaId){
              this.attrezzatureComponent.push(interventoTipo)
            }
          }
          );
        })

        let dichiarazioneCurr:FormGroup =  this.fb.group({
          intstrDaTreeSelezionata:  new FormControl({
            value: true,
            disabled: !this.canEdit || this.canEditRegione
          }),
          descrizione: [descrizione],
          classifTreeId: [listaDichA.classifTreeId],
          dataValidazione: [interventoStruttura.dichiarazioneAppaltabilita[listaDichA.classifTreeId] && interventoStruttura.dichiarazioneAppaltabilita[listaDichA.classifTreeId].intstrDaTreeValidazioneData?new Date(interventoStruttura.dichiarazioneAppaltabilita[listaDichA.classifTreeId].intstrDaTreeValidazioneData):null],
          atto: [interventoStruttura.dichiarazioneAppaltabilita[listaDichA.classifTreeId]==undefined?null:interventoStruttura.dichiarazioneAppaltabilita[listaDichA.classifTreeId].attoNumero],
          attrezzature: this.attrezzatureComponent,
        });
        listaDichiarazioniCurr.push(dichiarazioneCurr)
      })
      let dichiarazioneStruttura = this.fb.group(
        {
          listaDichiarazioni: this.fb.array(listaDichiarazioniCurr),
          idStruttura: interventoStruttura.intStrId
        }
      );
      (this.dichiarazioneDiAppaltabilitaModelloAA.get('listaDichiarazioneDiAppaltabilitaModelloAA')as FormArray).push(dichiarazioneStruttura);
    })
    this.dichiarazioneDiAppaltabilitaModelloAA.valueChanges
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (): void => {
        const formValue = {
          treeDA: this.dichiarazioneDiAppaltabilitaModelloAA.value,
        };
        this.formData.emit(formValue);
      },
    });
    if(this.modA){
      const formValue = {
        treeDA: this.dichiarazioneDiAppaltabilitaModelloA.value,
      };
      this.formData.emit(formValue);
    }else{
      const formValue = {
        treeDA: this.dichiarazioneDiAppaltabilitaModelloAA.value,
      };
      this.formData.emit(formValue);
    }
  }
  getModelloA(): FormArray {
    return (this.dichiarazioneDiAppaltabilitaModelloA.get('listaDichiarazioneDiAppaltabilitaModelloA')as FormArray);
  }
  getModelloAA(): FormArray {
    return (this.dichiarazioneDiAppaltabilitaModelloAA.get('listaDichiarazioneDiAppaltabilitaModelloAA')as FormArray);
  }
  getListaDichiarazioni(dichiarazioneStrutturaIndex: number): FormArray {
    return ((this.dichiarazioneDiAppaltabilitaModelloA.get('listaDichiarazioneDiAppaltabilitaModelloA')as FormArray).at(dichiarazioneStrutturaIndex) as FormGroup).get('listaDichiarazioni') as FormArray;
  }

  getListaDichiarazioniAA(dichiarazioneStrutturaIndex: number): FormArray {
    return ((this.dichiarazioneDiAppaltabilitaModelloAA.get('listaDichiarazioneDiAppaltabilitaModelloAA')as FormArray).at(dichiarazioneStrutturaIndex) as FormGroup).get('listaDichiarazioni') as FormArray;
  }

  getDichiarazioneAppaltabilita(id: number) {
    let classifElem = this.classificazioneTreeDAA.find((elem) => elem.classifTreeId === id);
    return classifElem?.classifElemCod === 'DAA_ARR_ATTR_B'
  }
}

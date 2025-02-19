/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

import { AriannaService } from 'src/app/services/arianna.service';
import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {catchError, combineLatest, finalize, map, of, switchMap, tap} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {ClassificazioneTree, Ente, Finanziamento, FinanziamentoTipoDet, Intervento, InterventoStatoProgettuale, InterventoStruttura, InterventoTipoDet, Provvedimento} from '@pgmeas-library/model';
import {ProjectApiService} from '../../../services/project-api.service';
import {RegistryApiService} from '../../../services/registry-api.service';
import {allegatoRelazioneTecnicaRequestForm, allegatoProvvedimentoAziendaleRequestForm, interventiStrutturaRequestForm,
  transformToArray, allegatoNullaOstaRequestForm, allegatoDecretoMinisterialeRequestForm} from '../../../utils/intervento-utils';
import {AuthenticationService} from '@pgmeas-library/authentication';
import {MatDialog} from '@angular/material/dialog';
import {AppaltoComponent} from './appalto/appalto.component';
import {ResponsabiliComponent} from './responsabili/responsabili.component';
import {MessageService} from 'src/app/services/message.service';
import { AllegatoLightExt } from '@pgmeas-library/model/src/allegato-light-ext';
import { PianoCronologicoStrutturaComponent } from './piano-cronologico-struttura/piano-cronologico-struttura.component';
import { DichiarazioneAppalatabilitaComponent } from './dichiarazione-appalatabilita/dichiarazione-appalatabilita.component';
import { UserService } from 'src/app/services/user.service';
import { ModuloStato } from '@pgmeas-library/model/src/modulo-stato';

@Component({
  selector: 'app-gestione',
  templateUrl: './gestione.component.html',
  styleUrls: ['./gestione.component.scss']
})
export class GestioneComponent implements OnInit {
  @ViewChild('appaltoComponent') appaltoComponent: AppaltoComponent;
  @ViewChild('pianoCronologicoStrutturaComponent') pianoCronologicoStrutturaComponent: PianoCronologicoStrutturaComponent;
  @ViewChild('responsabiliComponent') responsabiliComponent: ResponsabiliComponent;
  @ViewChild('dichiarazioneAppalatabilitaComponent') dichiarazioneAppalatabilitaComponent: DichiarazioneAppalatabilitaComponent;
  @Input() tipoModelloChild: string;
  protected intervento: Intervento;
  protected statoProgList: InterventoStatoProgettuale[]
  protected classificazioneTreeDA: ClassificazioneTree[];
  protected classificazioneTreeDAA: ClassificazioneTree[];
  protected interventoStruttura: InterventoStruttura[] = [];
  protected dichiarazioniAppalatibilitaIntervento: any[] = [];
  protected enti: Ente[] = [];
  protected finanziamento: Finanziamento[] = [];
  protected denominazioneStrutture: any[] = [];
  protected tipiFinanziamento: FinanziamentoTipoDet[] = [];
  protected dataFromBe: any[] = [];
  protected allegatiBe: any[] = [];
  protected interventoTipoDet: InterventoTipoDet[];
  protected allegatoOggetto: any;
  protected isLoading = false;
  protected canEdit: boolean;
  protected canEditRegione: boolean;
  protected creation: boolean;
  protected user: any;
  protected note: string;
  protected isConfirmOnGoing: boolean = false;
  protected moduloTipoId: number;
  protected salvataggioEffettuato:boolean = false;
  protected appaltoIntegrato: boolean = false;
  protected appaltoIntegratoStruttura: { idStruttura: number, appaltoIntegrato: boolean };
  private title1: string;
  moduloStatoList: ModuloStato[];

  protected isDownloadOnGoing = false;
  private data: any = {
    appalto: {},
    deliberazioni: {},
    pianoFinanziario: {},
    quadroEconomico: {},
    pianoCronologico: {},
    pianoCronologicoStruttura: {},
    dichiarazioneAppaltabilita: {},
    allegati: {},
    responsabili: {},
    allegatiRegione: {}
  };
  private intDgRegionaleApprovazione: string;
  tipoModello:string;
  tipoModuloInit:string; 

  constructor(private route: ActivatedRoute,
              private authService: AuthenticationService,
              private projectApiService: ProjectApiService,
              private registryApiService: RegistryApiService,
              private message: MessageService,
              private router: Router,
              private dialog: MatDialog,
              private ariannaService:AriannaService,
              private userService:UserService) {
  }

  isSuperUser(){
    return this.userService.isSuperUser();
  }
  get allFormsValid(): number {
    let valid=0;
    if(this.responsabiliComponent && this.responsabiliComponent.responsabiliForm && !this.responsabiliComponent.responsabiliForm.valid){
      valid=1
    }
    if(this.pianoCronologicoStrutturaComponent &&  this.pianoCronologicoStrutturaComponent.pianoCronologicoForm && !this.pianoCronologicoStrutturaComponent.pianoCronologicoForm.valid){
      valid=2 ;
    }
    return valid;
  }

  get title(): string {
    return this.title1;
  }

  ngOnInit(): void {
    this.initData();
    this.user = this.authService.user;

  }

  onTipoModello(nuovoModello:string){
    if(this.intervento){
      // setTimeout(() => {

          this.tipoModelloChild=nuovoModello;
          if(this.dichiarazioneAppalatabilitaComponent){
            // console.log("TIPO MODELLO CHE ARRIVA DA APPALTO: "+this.tipoModelloChild);
            this.dichiarazioneAppalatabilitaComponent.updateModelloTipo(this.tipoModelloChild);
          }
      // }, 1);
    }
  }

  receiveData(section: string, eventData: any): void {
    this.data[section] = eventData;
  }



  buildIntervento() {
    let interventoNew = this.intervento;
    interventoNew.intDirettoreGeneraleNome = this.data.responsabili.nomeDirettore;
    interventoNew.intDirettoreGeneraleCognome = this.data.responsabili.cognomeDirettore;
    interventoNew.intDirettoreGeneraleCf = this.data.responsabili.codiceFiscaleDirettore;
    interventoNew.intCommissarioNome = this.data.responsabili.nomeCommissario;
    interventoNew.intCommissarioCognome = this.data.responsabili.cognomeCommissario;
    interventoNew.intCommissarioCf = this.data.responsabili.codiceFiscaleCommissario;
    interventoNew.intRupNome = this.data.responsabili.nomeRup;
    interventoNew.intRupCognome = this.data.responsabili.cognomeRup;
    interventoNew.intRupCf = this.data.responsabili.codiceFiscaleRup;
    interventoNew.intReferentePraticaCognome = this.data.responsabili.cognomeReferentePratica;
    interventoNew.intReferentePraticaNome = this.data.responsabili.nomeReferentePratica;
    interventoNew.intReferentePraticaCf = this.data.responsabili.codiceFiscaleReferentePratica;
    interventoNew.intReferentePraticaEmail = this.data.responsabili.emailReferentePratica;
    interventoNew.intReferentePraticaTelefono = this.data.responsabili.telefonoReferentePratica;
    interventoNew.note = this.data.allegati.note;
    return interventoNew;
  }



  validateData():string|null{
   // Controlla se almeno un campo è popolato per il Direttore
    const direttorePopolato = (this.data.responsabili.nomeDirettore &&
      this.data.responsabili.cognomeDirettore &&
      this.data.responsabili.codiceFiscaleDirettore) ? true : false;

    const commissarioPopolato = ( this.data.responsabili.nomeCommissario &&
      this.data.responsabili.cognomeCommissario
      && this.data.responsabili.codiceFiscaleCommissario) ? true : false;
    if (!direttorePopolato && !commissarioPopolato) {
      return "L'inserimento dei dati relativi del Direttore Generale o del Commissario è obbligatorio."
    }
    if ((direttorePopolato && commissarioPopolato) || (!direttorePopolato && !commissarioPopolato)) {
      return "MSG-082: Valorizzare i dati del direttore generale o del commissario, non entrambi."
    }

    //provvedimento aziendale di approvazione
    if(
      (!this.data.allegati.intAllegatoNumero && this.data.allegati.intAllegatoData ) ||
      (!this.data.allegati.intAllegatoNumero && this.data.allegati.allegatoProvvedimentoAziendaleApprovazione) ||
      (!this.data.allegati.intAllegatoData && this.data.allegati.intAllegatoNumero) ||
      (!this.data.allegati.intAllegatoData && this.data.allegati.allegatoProvvedimentoAziendaleApprovazione) ||
      (!this.data.allegati.allegatoProvvedimentoAziendaleApprovazione && this.data.allegati.intAllegatoData) ||
      (!this.data.allegati.allegatoProvvedimentoAziendaleApprovazione && this.data.allegati.intAllegatoNumero)
    ){
      this.isConfirmOnGoing = false;
      return "MSG-109: Provvedimento di approvazione del progetto (DDG) non valorizzato correttamente: inserire numero, data e allegato"
    }
    //relazione tecnica
    if(
      (!this.data.allegati.intAllegatoNumeroRT && this.data.allegati.intAllegatoDataRT ) ||
      (!this.data.allegati.intAllegatoNumeroRT && this.data.allegati.allegatoRelazioneTecnica) ||
      (!this.data.allegati.intAllegatoDataRT && this.data.allegati.intAllegatoNumeroRT) ||
      (!this.data.allegati.intAllegatoDataRT && this.data.allegati.allegatoRelazioneTecnica) ||
      (!this.data.allegati.allegatoRelazioneTecnica && this.data.allegati.intAllegatoDataRT) ||
      (!this.data.allegati.allegatoRelazioneTecnica && this.data.allegati.intAllegatoNumeroRT)
    ){
      this.isConfirmOnGoing = false;
      return "MSG-110: Relazione tecnica non valorizzata correttamente: inserire numero, data e allegato"
    }

    return null;
  }

  saveData(): void {
    const uuid = crypto.randomUUID();

    if (this.allFormsValid==1) {
       this.message.error("MSG-005 Dati obbligatori: valorizzare i dati dei responsabili dell'intervento");
       this.isConfirmOnGoing = false;
       return;
    }
    if (this.allFormsValid==2) {
      this.message.error("MSG-005 Inserire campo obbligatorio: Valorizzare il cronoprogramma della struttura");
      this.isConfirmOnGoing = false;
      return;
   }
    let messageError:string|null = this.validateData();
    if(messageError){
      this.message.error(messageError);
      return;
    }
    this.isConfirmOnGoing = true;

    let interventoNew = this.buildIntervento()

    let interventoStruttura = interventiStrutturaRequestForm(interventoNew.moduloTipo, this.data.pianoCronologicoStruttura, this.data.dichiarazioneAppaltabilita, this.data.quadroEconomico)
    if(this.canEditRegione){
      const allegato1Regione:AllegatoLightExt|null = allegatoNullaOstaRequestForm(this.data.allegatiRegione)  ;
      const allegato2Regione:AllegatoLightExt|null = allegatoDecretoMinisterialeRequestForm(this.data.allegatiRegione);

      if(allegato1Regione == null && allegato2Regione ==null ){
        this.message.error("MSG-005 Inserire campo obbligatorio: Caricare almeno un allegato a cura di Regione Piemonte");
        this.isConfirmOnGoing = false;
        return;
      }
      const allegatoNullaOstaToDelete:AllegatoLightExt|null = interventoNew.allegatoNullaOsta==undefined?null:interventoNew.allegatoNullaOsta[0];
      const allegatoDecretoMinisterialeToDelete:AllegatoLightExt|null = interventoNew.allegatoDecretoMinisteriale==undefined?null:interventoNew.allegatoDecretoMinisteriale[0];
      //console.log("NullaOsta: "+allegato1Regione);  
      //console.log("NullaOstaToDelete: "+allegatoNullaOstaToDelete);     
      if(allegato1Regione != null){
        if(allegato1Regione.base64 !=null){
          if(allegatoNullaOstaToDelete ==null){
            if(allegato1Regione.intAllegatoData == null || allegato1Regione.intAllegatoNumero == null){
              this.isConfirmOnGoing = false;
              this.message.error("MSG-109: Nulla osta di ammissione non valorizzato correttamente: inserire numero, data e allegato");
              return;
              }     
          }                
        }
      }
      //console.log("DecretoMinisteriale: "+allegato2Regione);           
      //console.log("DecretoMinisterialeToDelete: "+allegatoDecretoMinisterialeToDelete);
      if(allegato2Regione != null){
        if(allegato2Regione.base64 !=null){
          if(allegatoDecretoMinisterialeToDelete ==null){
            if(allegato2Regione.intAllegatoData == null || allegato2Regione.intAllegatoNumero == null){
              this.isConfirmOnGoing = false;
              this.message.error("MSG-110: Decreto ministeriale di ammissione al finanziamento: inserire numero, data e allegato");
              return;
              }     
          }                
        }         
      }
      
      this.projectApiService.aggiornamentoAmmissioneFinanziamentoRegione(interventoNew,allegato1Regione,allegato2Regione, allegatoNullaOstaToDelete, allegatoDecretoMinisterialeToDelete).subscribe({
        next: res => {

          this.isConfirmOnGoing = false
          //this.id=res.rIntModuloId
          this.salvataggioEffettuato = true;
        },
        error: () => this.isConfirmOnGoing = false
      });
    } else {
      const allegato1:AllegatoLightExt|null = allegatoProvvedimentoAziendaleRequestForm(this.data.allegati)  ;
      const allegato2:AllegatoLightExt|null = allegatoRelazioneTecnicaRequestForm(this.data.allegati);     

      const allegatoProvvedimentoAziendaleToDelete:AllegatoLightExt|null = interventoNew.allegatoProvvedimentoAziendaleApprovazione==undefined?null:interventoNew.allegatoProvvedimentoAziendaleApprovazione[0];
      const allegatoRelazioneTecnicaToDelete:AllegatoLightExt|null = interventoNew.allegatoRelazioneTecnica==undefined?null:interventoNew.allegatoRelazioneTecnica[0];

      if(this.moduloTipoId){
        this.projectApiService.aggiornamentoAmmissioneFinanziamento(this.data, interventoNew, interventoStruttura,
          allegato1, allegato2,
          allegatoProvvedimentoAziendaleToDelete, allegatoRelazioneTecnicaToDelete).subscribe({
          next: res => {
            this.isConfirmOnGoing = false
            //this.id=res.rIntModuloId
            this.salvataggioEffettuato = true;
          },
          error: () => this.isConfirmOnGoing = false
        });
      }else{
        this.projectApiService.salvataggioAmmissioneFinanziamento(this.data, interventoNew, interventoStruttura, allegato1, allegato2).subscribe({
          next: res => {
            console.log("postAggiornaAmmissioneFinanziamento: ok");
            this.isConfirmOnGoing = false
            this.moduloTipoId=res.rIntModuloId
            this.salvataggioEffettuato = true;
          },
          error: () => this.isConfirmOnGoing = false
        });
      }
    }
  }

  onPrintPdf(id: number): void {
    this.isDownloadOnGoing = true;
    this.projectApiService.downloadModuloAById(id).subscribe(response => {
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

        this.isDownloadOnGoing = false;
      }, err=>{
        this.isDownloadOnGoing=false;
      });
  }

  retrievePianiFinanziari(data: any) {
    const finanziamenti = data.pianoFinanziario?.finanziamentiForm || [];
    const altriFinanziamenti = data.pianoFinanziario?.altriFinanziamenti || [];
    return [...finanziamenti, ...altriFinanziamenti];
  }

  retriveDataIfOnEditFalse() {
    const intId = this.intervento.intId;
    combineLatest([
      this.projectApiService.getInterventoGaraAppaltoListByIntervento(intId),
      //this.projectApiService.getInterventoStrutturaListByIntervento(intId), //SOSTITUITO
      this.projectApiService.getModuloAInterventoStrutturaDetail(intId),
      this.projectApiService.getInterventoAllegatoListByIntervento(intId)
    ]).subscribe(([garaAppalto, struttura, allegati]) => {
      this.allegatoOggetto = allegati.filter((allegati: any) => allegati.allegatoTipoId === 1);
      this.dataFromBe.push({
          garaAppalto: garaAppalto,
          strutture: struttura,
          allegatoOggetto: this.allegatoOggetto
        }
      );
      const flattenedData = this.dataFromBe.reduce((acc, innerArray) => acc.concat(innerArray), []);
      this.dataFromBe = flattenedData;
      this.allegatiBe = allegati.filter((allegati: any) => allegati.allegatoTipoId === 12 || allegati.allegatoTipoId === 11);
    });
  }

  back() {
    const back = history.state.back ?? '/consulta-richiesta-ammissione-finanziamento';
    this.router.navigateByUrl(back);
  }

  isModuloStatoApprovato(statoModulo:number){
    const stato = this.moduloStatoList.find((stato)=> stato.moduloStatoId === statoModulo );
    return stato?.moduloStatoCod ==='APPR';
  }

  isModuloStatoPresentato(statoModulo:number){
    const stato = this.moduloStatoList.find((stato)=> stato.moduloStatoId === statoModulo );
    return stato?.moduloStatoCod ==='PRES';
  }

  checkButtonModificaModuloAReg(moduloStato:number) {
    let flag = false;
    if(moduloStato!=null){
      if(this.isModuloStatoApprovato(moduloStato)||this.isModuloStatoPresentato(moduloStato)){
        flag = true;
      }
    }
    return flag;
  }

 

  checkAllegatiRegione():boolean {
    return (this.isSuperUser() && this.checkButtonModificaModuloAReg(this.intervento.rIntModuloStatoAId) )||(!this.isSuperUser() && this.isModuloStatoApprovato(this.intervento.rIntModuloStatoAId))
  }

  private initData(): void {
    this.isLoading = true;
    this.route.paramMap.pipe(
      map((params: any) => {
        // console.log("DENTRO IL MAP");
        // console.log("valore: "+params.params.mode);
        if(params.params.mode){
          this.canEdit = !(params.params.mode && params.params.mode === 'view');

          this.canEditRegione = (params.params.mode && params.params.mode === 'editRegione');
        }else{
          this.canEdit=true;
          this.canEditRegione=false;
        }

        this.moduloTipoId=params.params.allegatoId
        this.creation = params.params.allegatoId==undefined || (params.params.mode && params.params.mode === 'editRegione')
        if(params.params.mode == 'editRegione' || params.params.mode =='editASR'){
          this.ariannaService.editTitle('Richiesta Finanziamento','modifica ammissione finanziamento');
          this.title1 = 'Modifica Richiesta ammissione al finanziamento'
        }else if(params.params.mode =='view'){
          this.ariannaService.editTitle('Richiesta Finanziamento','consultazione ammissione finanziamento');
          this.title1 =   'Visualizzazione ammissione al finanziamento'
        }else{
          this.ariannaService.editTitle('dettaglio','inserisci ammissione finanziamento');
          this.title1 =   'Inserisci ammissione al finanziamento'
        }

        return params.get('id')!;
      }),
      switchMap((id) =>
        combineLatest([
          this.projectApiService.getModuloAInterventoDetail(id),
          this.projectApiService.getModuloAInterventoStrutturaDetail(id),
          this.registryApiService.getEnteList(), 
          this.registryApiService.getClassificazioneTreeList("DA"),
          this.registryApiService.getClassificazioneTreeList("DAA"),
          this.registryApiService.getInterventoTipoDetList("ACQ_ATTR"),
          this.registryApiService.getInterventoStatoProgettualeList(),
          this.registryApiService.getStrutturaList(),
          this.registryApiService.getModuloStatoList(),

        ])
      )).subscribe({
      next: ([intervento, interventoStrutturaList, enti, classificazioneTreeDA, classificazioneTreeDAA, interventoTipoDet, statoProgList, strutture, moduloStatoList]) => {
        this.statoProgList=statoProgList;
        this.interventoStruttura = interventoStrutturaList;
        this.enti = enti;
        this.moduloStatoList = moduloStatoList;

        let struttureList = strutture;
        const strIds = this.interventoStruttura.map(is => is.strId);
        const items = struttureList.filter(s => strIds.includes(s.strId));
        this.denominazioneStrutture.push(...items);
        this.classificazioneTreeDA = classificazioneTreeDA;
        this.classificazioneTreeDAA = classificazioneTreeDAA;
        
        this.isLoading = false;
        this.interventoTipoDet = interventoTipoDet;

        this.intervento = intervento;
        this.tipoModuloInit = intervento.moduloTipo;
        if (!this.canEdit) {
          this.retriveDataIfOnEditFalse();
        }
      }
    });
  }

  updateAppaltoIntegrato(event: any) {
    this.appaltoIntegratoStruttura = event;
  }
  updateQuadroEconomico(event: any) {
    this.data.quadroEconomico = event.valoriDaEmettere;
  }
}

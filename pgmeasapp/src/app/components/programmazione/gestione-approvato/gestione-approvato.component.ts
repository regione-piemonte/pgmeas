import { AriannaService } from 'src/app/services/arianna.service';
import {Component, Input, OnInit} from '@angular/core';
import { combineLatest,  map, switchMap} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {ClassificazioneTree, Ente, FileBase64, Finanziamento, FinanziamentoTipoDet, Intervento, InterventoStatoProgettuale, InterventoStruttura, InterventoTipoDet, Provvedimento} from '@pgmeas-library/model';
import {ProjectApiService} from '../../../services/project-api.service';
import {RegistryApiService} from '../../../services/registry-api.service';
import {AuthenticationService} from '@pgmeas-library/authentication';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-gestione-approvato',
  templateUrl: './gestione-approvato.component.html',
  styleUrls: ['./gestione-approvato.component.scss']
})
export class GestioneApprovatoComponent implements OnInit {
  @Input() tipoModelloChild: string;
  protected intervento: Intervento;
  protected ente: Ente[] = [];

  protected statoProgList: InterventoStatoProgettuale[]
  protected classificazioneTreeDA: ClassificazioneTree[];
  protected classificazioneTreeDAA: ClassificazioneTree[];
  protected interventoStruttura: InterventoStruttura[] = [];
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
  private title1: string;

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
              private router: Router,
              private ariannaService:AriannaService,
              private userService:UserService) {
  }

  isSuperUser(){
    return this.userService.isSuperUser();
  }

  get title(): string {
    return this.title1;
  }

  ngOnInit(): void {
    this.initData();
    this.user = this.authService.user;

  }

  filterEnte(intervento: Intervento): Ente[] {
    return this.enti.filter(ente => ente.enteId === intervento.enteId);
  }

  receiveData(section: string, eventData: any): void {
    this.data[section] = eventData;
  }

  downloadFile(fileName: string, fileType: string, base64: string): File {
    if(base64){
      const byteCharacters = atob(base64);
      const byteNumbers = new Array(byteCharacters.length);

      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: fileType });

      return new File([blob], fileName, { type: fileType });
    }
    return new File([],fileName);
  }

  onPrintPdf(rIntModuloId: number, intId: number): void {
    this.isDownloadOnGoing = true;

    this.projectApiService.downloadModuloByRIntModuloIdAndInterventoId(rIntModuloId, intId).subscribe(modulo => {
      let file = this.downloadFile(modulo.fileName, modulo.fileType, modulo.base64);
      if(file){
        const filename = modulo.fileName;
        const blob = new Blob([file], { type: modulo.fileType });
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


      this.allegatiBe.forEach(allegato => {
    

      });
    });
  }

  back() {
    const back = history.state.back ?? '/consulta-richiesta-ammissione-finanziamento';
    this.router.navigateByUrl(back);
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
          // this.registryApiService.getClassificazioneElementoList(), //SOSTITUIRE con getClassificazioneTreeList
          this.registryApiService.getEnteList(), 
          this.registryApiService.getClassificazioneTreeList("DA"),
          this.registryApiService.getClassificazioneTreeList("DAA"),
          this.registryApiService.getInterventoTipoDetList("ACQ_ATTR"),
          this.registryApiService.getInterventoStatoProgettualeList()
        ])
      )).subscribe({
      next: ([intervento, interventoStrutturaList, enti,  classificazioneTreeDA, classificazioneTreeDAA, interventoTipoDet, statoProgList]) => {
        this.intervento = intervento;

        this.statoProgList=statoProgList;
        this.interventoStruttura = interventoStrutturaList;
        this.enti = enti;
        this.ente = this.filterEnte(this.intervento);

        if (!this.canEdit) {
          this.retriveDataIfOnEditFalse();
        }
        // this.dichiarazioneAppOriginale = classifElement;
        this.registryApiService.getStrutturaList().subscribe(result => {
          const strIds = this.interventoStruttura.map(is => is.strId);
          const items = result.filter(s => strIds.includes(s.strId));
          this.denominazioneStrutture.push(...items);
        });
        
        this.classificazioneTreeDA = classificazioneTreeDA;
        this.classificazioneTreeDAA = classificazioneTreeDAA;
        this.tipoModuloInit = intervento.moduloTipo;
        this.isLoading = false;
        this.interventoTipoDet = interventoTipoDet;
      }
    });
  }

}
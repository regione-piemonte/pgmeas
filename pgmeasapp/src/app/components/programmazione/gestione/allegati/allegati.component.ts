/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Subject, takeUntil} from 'rxjs';
import {ProjectApiService} from '../../../../services/project-api.service';
import {AllegatoLightExt, Ente, FileBase64, Intervento} from '@pgmeas-library/model';
import {LoadingService} from '../../../../services/loading.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-allegati',
  templateUrl: './allegati.component.html',
  styleUrls: ['./allegati.component.scss']
})
export class AllegatiComponent implements OnInit, OnDestroy, OnChanges {
  protected allegatoProvvedimentoAziendaleApprovazione: AllegatoLightExt[];
  protected allegatoRelazioneTecnica: AllegatoLightExt[];
  maxDate= new Date();
  @Input() enti: Ente[] = [];
  @Input() intervento: Intervento;
  @Input() canEdit = true;
  @Input() noteAttive=true;
  @Input() canEditRegione = true;
  @Input() user: any;
  @Input() dataFromBe: any[] = [];
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  @Input() note: string;
  protected allegatiForm: FormGroup;
  protected fileNameRelazioneTecnica: string | null = null;
  protected fileNameProvvedimentoAziendaleDiApprovazione: string | null = null;
  protected fileRelazioneTecnica: File | null = null;
  protected fileProvvedimentoAziendaleDiApprovazione: File | null = null;
  protected fileUrlRelazioneTecnica: string | null = null;
  protected fileUrlProvvedimentoAziendaleDiApprovazione: string | null = null;
  private destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private fb: FormBuilder,
              private projectApiService: ProjectApiService,
              public messageService:MessageService,
              private loadingService: LoadingService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.canEdit ) {
      this.initForm(this.intervento);

    }
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {
    this.initForm(this.intervento);

    this.allegatiForm.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe({
          next: (): void => {
            const formValue = this.allegatiForm.getRawValue();
            this.formData.emit(formValue);
          }
        }
      );
  }

  disableRegionali(): boolean {
    return (this.canEditRegione||!this.canEdit);
    }

  onFileSelectedProvvedimentoAziendaleDiApprovazione(event: any): void {
    const maxFileSize = 20 * 1024 * 1024; // 20MB in byte
    const input = event.target as HTMLInputElement;
    const file: File = event.target.files[0];
    if (file) {
      if (file.type != 'application/pdf') {
        this.messageService.error('Errore: il file deve essere in formato PDF.')
        input.value = ''; // Resetta l'input
        return;
      }  
      if (file.size > maxFileSize) {
        this.messageService.error('Errore: il file deve essere inferiore a 20MB.')
        input.value = ''; // Resetta l'input
        return;
      }
    }

    if (file) {
      this.fileNameProvvedimentoAziendaleDiApprovazione = file.name;
      this.fileProvvedimentoAziendaleDiApprovazione = file;
      this.fileToBase64(file).then(base64 => {
        this.allegatiForm.patchValue({
          allegatoProvvedimentoAziendaleApprovazione: base64.split(',')[1],
          nomeAllegatoProvvedimentoAziendaleDiApprovazione: file.name,
          tipoAllegatoProvvedimentoAziendaleDiApprovazione: file.type
        });
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrlProvvedimentoAziendaleDiApprovazione = URL.createObjectURL(file);
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
    }
  }


  onFileSelectedRelazioneTecnica(event: any): void {
    const maxFileSize = 20 * 1024 * 1024; // 20MB in byte
    const input = event.target as HTMLInputElement;
    const file: File = event.target.files[0];
    if (file) {
      if (file.type != 'application/pdf') {
        this.messageService.error('Errore: il file deve essere in formato PDF.')
        input.value = ''; // Resetta l'input
        return;
      }  
      if (file.size > maxFileSize) {
        this.messageService.error('Errore: il file deve essere inferiore a 20MB.')
        input.value = ''; // Resetta l'input
        return;
      }
    }

    if (file) {
      this.fileNameRelazioneTecnica = file.name;
      this.fileRelazioneTecnica = file;
      this.fileToBase64(file).then(base64 => {
        this.allegatiForm.patchValue({
          allegatoRelazioneTecnica: base64.split(',')[1],
          nomeAllegatoRelazioneTecnica: file.name,
          tipoAllegatoRelazioneTecnica: file.type
        });
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrlRelazioneTecnica = URL.createObjectURL(file);
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
    }
  }



  fileToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        // Ritorna la stringa Base64
        resolve(reader.result as string);
      };
      reader.onerror = reject; // Gestisci eventuali errori
      reader.readAsDataURL(file); // Leggi il file come URL data
    });
  }

  myDateParser(date : any) : string {
    let formattedDate = '';
    const data = new Date(date); // Crea un oggetto Date
        // Formatta la data in "yyyy-mm-dd"
        const year = data.getFullYear();
        const month = String(data.getMonth() + 1).padStart(2, '0'); // i mesi vanno da 0 a 11
        const day = String(data.getDate()).padStart(2, '0');
        formattedDate = `${year}-${month}-${day}`;
        // console.log("formattedDate: "+formattedDate);
    return formattedDate;
  }




  initForm(intervento: Intervento) {
    let that = this;
    if(intervento.allegatoProvvedimentoAziendaleApprovazione && intervento.allegatoProvvedimentoAziendaleApprovazione.length>0){
      this.projectApiService.downloadAllegatoById(intervento.allegatoProvvedimentoAziendaleApprovazione[0].idAllegato!).subscribe({
        next: (fileScaricato:FileBase64) => {
          intervento.allegatoProvvedimentoAziendaleApprovazione[0].base64=fileScaricato.base64;
          let file: File = that.downloadFile(intervento.allegatoProvvedimentoAziendaleApprovazione[0].fileNameUser, intervento.allegatoProvvedimentoAziendaleApprovazione[0].fileType, intervento.allegatoProvvedimentoAziendaleApprovazione[0].base64);
          that.fileUrlProvvedimentoAziendaleDiApprovazione = URL.createObjectURL(file);
          that.fileProvvedimentoAziendaleDiApprovazione = file;
          that.fileNameProvvedimentoAziendaleDiApprovazione=intervento.allegatoProvvedimentoAziendaleApprovazione[0].fileNameUser
          that.allegatiForm.controls['allegatoProvvedimentoAziendaleApprovazione'].setValue(fileScaricato.base64)
          const formValue = this.allegatiForm.getRawValue();
          this.formData.emit(formValue);
        },
        error: (err) => {
          console.error('File download error:', err);
        }
      });
    }
    if(intervento.allegatoRelazioneTecnica && intervento.allegatoRelazioneTecnica.length>0){
      this.projectApiService.downloadAllegatoById(intervento.allegatoRelazioneTecnica[0].idAllegato!).subscribe({
        next: (fileScaricato:FileBase64) => {
          intervento.allegatoRelazioneTecnica[0].base64=fileScaricato.base64;
          let file: File = this.downloadFile(intervento.allegatoRelazioneTecnica[0].fileNameUser, intervento.allegatoRelazioneTecnica[0].fileType, intervento.allegatoRelazioneTecnica[0].base64);
          that.fileUrlRelazioneTecnica = URL.createObjectURL(file);
          that.fileRelazioneTecnica = file;
          that.fileNameRelazioneTecnica=intervento.allegatoRelazioneTecnica[0].fileNameUser
          that.allegatiForm.controls['allegatoRelazioneTecnica'].setValue(fileScaricato.base64)
          const formValue = this.allegatiForm.getRawValue();
          this.formData.emit(formValue);
        },
        error: (err) => {
          console.error('File download error:', err);
        }
      });
    }
    this.allegatiForm = this.fb.group({
      allegatoProvvedimentoAziendaleApprovazione: [intervento.allegatoProvvedimentoAziendaleApprovazione&& intervento.allegatoProvvedimentoAziendaleApprovazione.length>0 ? intervento.allegatoProvvedimentoAziendaleApprovazione[0].base64: null],
      nomeAllegatoProvvedimentoAziendaleDiApprovazione: [intervento.allegatoProvvedimentoAziendaleApprovazione && intervento.allegatoProvvedimentoAziendaleApprovazione.length>0 ? intervento.allegatoProvvedimentoAziendaleApprovazione[0].fileNameUser: null],
      tipoAllegatoProvvedimentoAziendaleDiApprovazione: [intervento.allegatoProvvedimentoAziendaleApprovazione && intervento.allegatoProvvedimentoAziendaleApprovazione.length>0 ? intervento.allegatoProvvedimentoAziendaleApprovazione[0].fileType: null],
      allegatoRelazioneTecnica: [intervento.allegatoRelazioneTecnica && intervento.allegatoRelazioneTecnica.length>0 ? intervento.allegatoRelazioneTecnica[0].base64: null],
      nomeAllegatoRelazioneTecnica: [intervento.allegatoRelazioneTecnica && intervento.allegatoRelazioneTecnica.length>0 ? intervento.allegatoRelazioneTecnica[0].fileNameUser: null],
      tipoAllegatoRelazioneTecnica: [intervento.allegatoRelazioneTecnica && intervento.allegatoRelazioneTecnica.length>0 ? intervento.allegatoRelazioneTecnica[0].fileType: null],
      note: [intervento.note],
      intAllegatoNumero: [intervento.allegatoProvvedimentoAziendaleApprovazione && intervento.allegatoProvvedimentoAziendaleApprovazione.length>0 ? intervento.allegatoProvvedimentoAziendaleApprovazione[0].intAllegatoNumero: null],
      intAllegatoData: [intervento.allegatoProvvedimentoAziendaleApprovazione && intervento.allegatoProvvedimentoAziendaleApprovazione.length>0 ? intervento.allegatoProvvedimentoAziendaleApprovazione[0].intAllegatoData: null],
      intAllegatoNumeroRT: [intervento.allegatoRelazioneTecnica && intervento.allegatoRelazioneTecnica.length>0 ? intervento.allegatoRelazioneTecnica[0].intAllegatoNumero: null],
      intAllegatoDataRT: [intervento.allegatoRelazioneTecnica && intervento.allegatoRelazioneTecnica.length>0 ? intervento.allegatoRelazioneTecnica[0].intAllegatoData: null],
    });
    const formValue = this.allegatiForm.getRawValue();
    this.formData.emit(formValue);
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

  removeFileProvvedimentoAziendaleDiApprovazione(): void {
    this.fileNameProvvedimentoAziendaleDiApprovazione = null;
    this.fileUrlProvvedimentoAziendaleDiApprovazione = null;
    this.fileProvvedimentoAziendaleDiApprovazione = null;
    this.allegatiForm.patchValue({
      allegatoProvvedimentoAziendaleApprovazione: null, 
      nomeAllegatoProvvedimentoAziendaleDiApprovazione: null,
      tipoAllegatoProvvedimentoAziendaleDiApprovazione: null,
      intAllegatoNumero:null,
      intAllegatoData: null
    });
    const formValue = this.allegatiForm.getRawValue();
    this.formData.emit(formValue);
  }

  removeFileRelazioneTecnica(): void {
    this.fileNameRelazioneTecnica = null;
    this.fileUrlRelazioneTecnica = null;
    this.fileRelazioneTecnica = null;
    this.allegatiForm.patchValue({
      allegatoRelazioneTecnica: null,
      intAllegatoNumeroRT:null,
      intAllegatoDataRT: null,
      
    });
    const formValue = this.allegatiForm.getRawValue();
    this.formData.emit(formValue);
  }
  toBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = reject;
    });
  }


}

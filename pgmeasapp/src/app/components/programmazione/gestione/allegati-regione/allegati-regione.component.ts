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
  selector: 'app-allegati-regione',
  templateUrl: './allegati-regione.component.html',
  styleUrls: ['./allegati-regione.component.scss']
})
export class AllegatiRegioneComponent implements OnInit, OnDestroy, OnChanges {
  protected allegatoNullaOsta: AllegatoLightExt[];
  protected allegatoDecretoMinisteriale: AllegatoLightExt[];
  maxDate= new Date();
  @Input() enti: Ente[] = [];
  @Input() intervento: Intervento;
  @Input() canEdit = true;
  @Input() canEditRegione = true;
  @Input() user: any;
  @Input() dataFromBe: any[] = [];
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();

  protected allegatiForm: FormGroup;
  protected fileNameDecretoMinisteriale: string | null = null;
  protected fileNameNullaOsta: string | null = null;
  protected fileDecretoMinisteriale: File | null = null;
  protected fileNullaOsta: File | null = null;
  protected fileUrlDecretoMinisteriale: string | null = null;
  protected fileUrlNullaOsta: string | null = null;
  private destroy$: Subject<boolean> = new Subject<boolean>();
  //CAMPI USATI PER NASCONDERE IL TASTO "allega" SE AGGIUNTO UN DOCUMENTO
  //CAMPI USATI PER IMPEDIRE L'INSERIMENTO SIA DEL NULLA OSTA SIA DEL DECRETO
  protected nullaOsta = true;
  protected decretoMin = true;

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
    return (!this.canEditRegione);
    }

  onFileSelectedNullaOsta(event: any): void {
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
      this.fileNameNullaOsta = file.name;
      this.fileNullaOsta = file;
      this.fileToBase64(file).then(base64 => {
        this.allegatiForm.patchValue({
          allegatoNullaOsta: base64.split(',')[1],
          nomeAllegatoNullaOsta: file.name,
          tipoAllegatoNullaOsta: file.type
        });
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrlNullaOsta = URL.createObjectURL(file);
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
      this.nullaOsta=false;
      this.decretoMin=false;
    }
  }


  onFileSelectedDecretoMinisteriale(event: any): void {    
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
      this.fileNameDecretoMinisteriale = file.name;
      this.fileDecretoMinisteriale = file;
      this.fileToBase64(file).then(base64 => {
        this.allegatiForm.patchValue({
          allegatoDecretoMinisteriale: base64.split(',')[1],
          nomeAllegatoDecretoMinisteriale: file.name,
          tipoAllegatoDecretoMinisteriale: file.type
        });
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrlDecretoMinisteriale = URL.createObjectURL(file);
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
      this.decretoMin=false;
      this.nullaOsta=false;
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
    if(intervento.allegatoNullaOsta && intervento.allegatoNullaOsta.length>0){
      this.nullaOsta=false;
      this.decretoMin=false;
      this.projectApiService.downloadAllegatoById(intervento.allegatoNullaOsta[0].idAllegato!).subscribe({
        next: (fileScaricato:FileBase64) => {
          intervento.allegatoNullaOsta[0].base64=fileScaricato.base64;
          let file: File = that.downloadFile(intervento.allegatoNullaOsta[0].fileNameUser, intervento.allegatoNullaOsta[0].fileType, intervento.allegatoNullaOsta[0].base64);
          that.fileUrlNullaOsta = URL.createObjectURL(file);
          that.fileNullaOsta = file;
          that.fileNameNullaOsta=intervento.allegatoNullaOsta[0].fileNameUser
          that.allegatiForm.controls['allegatoNullaOsta'].setValue(fileScaricato.base64)
          const formValue = this.allegatiForm.getRawValue();
          this.formData.emit(formValue);
        },
        error: (err) => {
          console.error('File download error:', err);
        }
      });
    }
    if(intervento.allegatoDecretoMinisteriale && intervento.allegatoDecretoMinisteriale.length>0){
      this.nullaOsta=false;
      this.decretoMin=false;
      this.projectApiService.downloadAllegatoById(intervento.allegatoDecretoMinisteriale[0].idAllegato!).subscribe({
        next: (fileScaricato:FileBase64) => {
          intervento.allegatoDecretoMinisteriale[0].base64=fileScaricato.base64;
          let file: File = this.downloadFile(intervento.allegatoDecretoMinisteriale[0].fileNameUser, intervento.allegatoDecretoMinisteriale[0].fileType, intervento.allegatoDecretoMinisteriale[0].base64);
          that.fileUrlDecretoMinisteriale = URL.createObjectURL(file);
          that.fileDecretoMinisteriale = file;
          that.fileNameDecretoMinisteriale=intervento.allegatoDecretoMinisteriale[0].fileNameUser
          that.allegatiForm.controls['allegatoDecretoMinisteriale'].setValue(fileScaricato.base64)
          const formValue = this.allegatiForm.getRawValue();
          this.formData.emit(formValue);
        },
        error: (err) => {
          console.error('File download error:', err);
        }
      });
    }
    this.allegatiForm = this.fb.group({
      allegatoNullaOsta: [intervento.allegatoNullaOsta&& intervento.allegatoNullaOsta.length>0 ? intervento.allegatoNullaOsta[0].base64: null],
      nomeAllegatoNullaOsta: [intervento.allegatoNullaOsta && intervento.allegatoNullaOsta.length>0 ? intervento.allegatoNullaOsta[0].fileNameUser: null],
      tipoAllegatoNullaOsta: [intervento.allegatoNullaOsta && intervento.allegatoNullaOsta.length>0 ? intervento.allegatoNullaOsta[0].fileType: null],
      allegatoDecretoMinisteriale: [intervento.allegatoDecretoMinisteriale && intervento.allegatoDecretoMinisteriale.length>0 ? intervento.allegatoDecretoMinisteriale[0].base64: null],
      nomeAllegatoDecretoMinisteriale: [intervento.allegatoDecretoMinisteriale && intervento.allegatoDecretoMinisteriale.length>0 ? intervento.allegatoDecretoMinisteriale[0].fileNameUser: null],
      tipoAllegatoDecretoMinisteriale: [intervento.allegatoDecretoMinisteriale && intervento.allegatoDecretoMinisteriale.length>0 ? intervento.allegatoDecretoMinisteriale[0].fileType: null],

      intAllegatoNumeroNO: [intervento.allegatoNullaOsta && intervento.allegatoNullaOsta.length>0 ? intervento.allegatoNullaOsta[0].intAllegatoNumero: null],
      intAllegatoDataNO: [intervento.allegatoNullaOsta && intervento.allegatoNullaOsta.length>0 ? intervento.allegatoNullaOsta[0].intAllegatoData: null],
      intAllegatoNumeroDM: [intervento.allegatoDecretoMinisteriale && intervento.allegatoDecretoMinisteriale.length>0 ? intervento.allegatoDecretoMinisteriale[0].intAllegatoNumero: null],
      intAllegatoDataDM: [intervento.allegatoDecretoMinisteriale && intervento.allegatoDecretoMinisteriale.length>0 ? intervento.allegatoDecretoMinisteriale[0].intAllegatoData: null],
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

  removeFileNullaOsta(): void {
    this.fileNameNullaOsta = null;
    this.fileUrlNullaOsta = null;
    this.fileNullaOsta = null;
    this.nullaOsta = true;
    this.decretoMin=true;
    this.allegatiForm.patchValue({
      allegatoNullaOsta: null, 
      intAllegatoNumeroNO:null,
      intAllegatoDataNO:null,
    });

  }

  removeFileDecretoMinisteriale(): void {
    this.fileNameDecretoMinisteriale = null;
    this.fileUrlDecretoMinisteriale = null;
    this.fileDecretoMinisteriale = null;
    this.nullaOsta = true;
    this.decretoMin=true;
    this.allegatiForm.patchValue({
      allegatoDecretoMinisteriale: null, 
      intAllegatoNumeroDM: null,
      intAllegatoDataDM:null,
    });

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

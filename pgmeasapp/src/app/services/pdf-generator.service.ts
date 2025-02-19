// /*
// * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
// *
// * SPDX-License-Identifier: EUPL-1.2 
// */

// import { Injectable } from '@angular/core';
// import {jsPDF} from 'jspdf';

// enum StatoProgetto {
//   'DOCFAP' = 1,
//   'DIP' = 2,
//   'PFTE' = 3,
//   'PROGETTO ESECUTIVO' = 4,
//   'CAPITOLATO PRESTAZIONALE FORNITURE' = 5
// }

// @Injectable({
//   providedIn: 'root'
// })
// export class PdfGeneratorService {

//   mappaStatoProgetto(stati: number[]): string[] {
//     return stati.map(stato => StatoProgetto[stato]);
//   }

//   constructor() {
//   }

//   generatePDF(data: any): string {
//     const doc = new jsPDF();
//     const ente = data.intestazioneEnteAtt;
//     const appalto = data.appalto;
//     const intervento = data.documento;
//     const quadroEconomicoOriginale = data.quadroEconomicoOriginale;
//     const pianoCronologico = data.pianoCronologico;
//     const dichiarazioneAppaltabilita = data.dichiarazioneAppaltabilita;
//     const strutture = data.strutture;
//     const allegati = data.allegati

//     const statiProgettoMappati = this.mappaStatoProgetto(appalto.statoProgetto);

//     doc.setFillColor(200, 200, 200);
//     doc.rect(5, 5, 200, 40, 'F');
//     doc.setFontSize(12);
//     doc.setTextColor(0, 0, 0);
//     doc.setFont('helvetica', 'bold');
//     doc.text(ente.nomeEnte, 10, 15);
//     doc.setFontSize(10);
//     doc.text(ente.dipartimento, 10, 22);
//     doc.setFont('helvetica', 'regular');
//     doc.text(`${ente.indirizzo.via} - ${ente.indirizzo.cap} ${ente.indirizzo.citta}`, 10, 27);

//     doc.setDrawColor(0, 0, 0);
//     doc.setFillColor(255, 255, 255);
//     const smallRectWidth = 50;
//     const smallRectHeight = 10;
//     const smallRectX = 150;
//     const smallRectY = 55;
//     doc.rect(smallRectX, smallRectY, smallRectWidth, smallRectHeight, 'FD');
//     doc.setFontSize(12);
//     doc.setTextColor(0, 0, 0);
//     const textX = smallRectX + smallRectWidth / 2;
//     const textY = smallRectY + smallRectHeight / 2 + 2;
//     doc.setFont('helvetica', 'bold');
//     doc.text('MODULO A', textX, textY, {align: 'center'});

//     const startX = 10;
//     let startY = 70;
//     const lineHeight = 7; // Altezza della linea costante per spaziatura omogenea

//     doc.setFont('helvetica', 'bold');
//     doc.text(`Prot. nÂ° : ${intervento.numProtocollo ?? ''}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Del : ${intervento.numProtocolloData ?? ''}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Al settore : `, startX, startY);
//     startY += lineHeight;
//     doc.text(`di : `, startX, startY);

//     doc.setFontSize(10);
//     doc.setFont('helvetica', 'regular');
//     startY += lineHeight;
//     doc.text(`Oggetto del finanziamento: ${appalto.oggetto}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Stato del progetto: ${statiProgettoMappati.join(', ')}`, startX, startY);

//     // Add strutture information
//     appalto.strutture.forEach((struttura: any, index: number) => {
//       startY += lineHeight * 2; // Extra space before each struttura
//       doc.setFont('helvetica', 'bold');
//       doc.text(strutture[index].strDenominazione, startX, startY);
//       startY += lineHeight;
//       doc.text(`Localizzazione: ${strutture[index].strIndirizzo}`, startX, startY);
//       startY += lineHeight;
//       doc.setFont('helvetica', 'regular');
//       startY += lineHeight;
//       doc.text(`CIG: ${struttura.cig}`, startX, startY);
//       startY += lineHeight;

//       doc.text(`Parere regionale PPP:`, startX, startY);
//       if (struttura.parereRegionePPP) {
//         doc.rect(startX + 60, startY - 3, 5, 5); // Disegna un rettangolo per la checkbox
//         doc.text('X', startX + 62, startY); // Aggiungi una X dentro la checkbox
//       } else {
//         doc.rect(startX + 60, startY - 3, 5, 5); // Disegna un rettangolo per la checkbox vuota
//       }
//       startY += lineHeight;

//       doc.text(`Determina di accertamento e/o impegno: ${struttura.determinaAccertamento}`, startX, startY);
//       startY += lineHeight;
//       doc.setDrawColor(169, 169, 169); // Colore grigio
//       doc.line(startX, startY, startX + 190, startY); // Linea grigia
//     });

//     doc.setFontSize(10);
//     doc.setFont('helvetica', 'regular');
//     startY += lineHeight;
//     doc.text("Fonti di finanziamento:", startX, startY);
//     startY += lineHeight;
//     doc.text(data.fontiFin.join(', '), startX, startY);
//     startY += lineHeight;
//     doc.text(`Ente attuatore: ${data.enteAttuatore}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Titolo intervento: ${data.titoloIntervento}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Codice intervento: ${data.intCode}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Codice cup: ${data.cupCode}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Costo complessivo intervento: ${data.importoComplessivo}â¬`, startX, startY);
//     // Aggiungi il campo Allegati
//     startY += lineHeight * 2; // Spazio extra prima di Allegati
//     doc.setFont('helvetica', 'bold');
//     doc.text("Allegati:", startX, startY);

//     doc.setFont('helvetica', 'regular');
//     startY += lineHeight;
//     doc.text(`Provvedimento aziendale di approvazione: ${allegati.provvedimentoAziendaleDiApprovazione?.fileNameUser ?? ''}`, startX, startY);
//     startY += lineHeight;
//     doc.text(`Relazione tecnica: ${allegati.relazioneTecnica?.fileNameUser ?? ''}`, startX, startY);

//     doc.addPage();

// // Piano finanziario in tabelle separate
// //25_07_2024 cr piano finaziario rappresentazione singola del piano
//     doc.setFont('helvetica', 'bold');
//     doc.text("PIANO FINANZIARIO:", 10, 10);
//     doc.setFont('helvetica', 'normal');
//     let y = 20; // Posizione iniziale per la prima tabella del piano finanziario
//       const pianoFinanziarioData = data.pianoFinanziarioPdf.vociPianoFinanziario.map((piano: any) => [
//         piano.tipo.finTipoDetDesc,
//         piano.atto,
//         `${piano.importo.toFixed(2)}`
//       ]);
//       (doc as any).autoTable({
//         startY: y + 10,
//         head: [['Specifiche del finanziamento', 'Atto', 'Importo(â¬)']],
//         body: pianoFinanziarioData,
//         theme: 'grid',
//         headStyles: {
//           fillColor: [3, 129, 214] // Colore dell'header in formato RGB
//         },
//         styles: {
//           fontSize: 10
//         }
//       });

// // Spazio tra le sezioni
//     y = (doc as any).autoTable.previous.finalY + 10;
//     doc.setFont('helvetica', 'bold');
//     doc.text(`Totale Piano finanziario: ${data.totalePianoFin.toFixed(2)} â¬`, 10, y);

//     doc.addPage();

//     doc.text("QUADRO ECONOMICO:", 10, 10);
//     let startYtable = 20; // Posizione iniziale per la prima tabella del quadro economico

//     data.quadroEconomico.forEach((quadro: any, index: number) => {
//       if (index > 0) {
//         startYtable = (doc as any).autoTable.previous.finalY + 15;
//       }
//       doc.text(strutture[index].strDenominazione, 10, startYtable);

//       // Preparare i dati per la tabella
//       const tableData: any[] = [];
//       const notIncludedImportKey = ['a)', 'd)', 'e)', 'f)', 'g)'];
//       for (const [key, value] of Object.entries(quadro)) {
//         // @ts-ignore
//         for (const [subKey, subValue] of Object.entries(value)) {
//           const desc = quadroEconomicoOriginale.find((qeo : any) => qeo.classifId === Number(subKey))?.classifDesc;
//           if (notIncludedImportKey.some(prefix => desc.startsWith(prefix))) {
//             tableData.push({subKey: desc, subValue: '', styles: {fontStyle: 'bold'}});
//           } else {
//             // @ts-ignore
//             tableData.push({subKey: desc, subValue: `${subValue.toFixed(2)}`, styles: {fontStyle: 'normal'}});
//           }
//         }
//       }

//       (doc as any).autoTable({
//         startY: startYtable + 10,
//         head: [['Descrizione', 'Importo(â¬)']],
//         body: tableData.map(row => [row.subKey, row.subValue]),
//         theme: 'grid',
//         headStyles: {
//           fillColor: [3, 129, 214]
//         },
//         styles: {
//           fontSize: 10
//         },
//         didParseCell: function (data: any) {
//           const row = tableData[data.row.index];
//           if (row.styles.fontStyle === 'bold') {
//             data.cell.styles.fontStyle = 'bold';
//           }
//         }
//       });
//     });

//     const yQuadro = (doc as any).previousAutoTable.finalY + 5;
//     doc.setFont('helvetica', 'bold');
//     doc.text(`Totale Quadro Economico: ${data.totaleQuadroEconomico.toFixed(2)} â¬`, 10, yQuadro);

//     // Definisci la posizione iniziale del Piano Cronologico
//     const yPianoCronologico = (doc as any).previousAutoTable.finalY + 25;
//     doc.text("PIANO CRONOLOGICO DI ATTIVAZIONE DELL'INTERVENTO (in giorni):", 10, yPianoCronologico);

//     // Definisci la posizione iniziale per le voci del piano cronologico
//     let yOffset = yPianoCronologico + 10;

//     // Itera sulle voci del piano cronologico e aggiungi il testo al PDF
//     pianoCronologico.forEach((voce: any, index: number) => {
//       doc.text(
//         `PFTE: ${voce.tempoPrevistoPerPFTE}, GARA: ${voce.tempoPrevistoPerGARA}, PROGETTO ESECUTIVO: ${voce.tempoPrevistoPerProgettoEsecutivo}, LAVORI: ${voce.tempoPrevistoPerLavori}, COLLAUDO: ${voce.tempoPrevistoPerCollaudo}`,
//         10,
//         yOffset
//       );
//       yOffset += 10; // Incrementa l'offset per la prossima voce
//     });

//     // @ts-ignore
//     function addTextWithOverflowCheck(doc, text, x, y, lineHeight, maxTextWidth, fontSize = 10, fontType = 'normal') {
//       const pageHeight = doc.internal.pageSize.height;
//       doc.setFontSize(fontSize);
//       doc.setFont('helvetica', fontType);
//       const wrappedText = doc.splitTextToSize(text, maxTextWidth);
//       // @ts-ignore
//       wrappedText.forEach(line => {
//         if (y + lineHeight > pageHeight - 20) { // Adjust 20 to your margin needs
//           doc.addPage();
//           y = 20; // Reset y to top margin
//         }
//         doc.text(line, x, y);
//         y += lineHeight;
//       });
//       return y;
//     }

//     // @ts-ignore
//     function addCheckbox(doc, yPos, flag) {
//       if (flag) {
//         doc.rect(190, yPos - 3, 5, 5); // Disegna un rettangolo per la checkbox
//         doc.text('X', 192, yPos); // Aggiungi una X dentro la checkbox
//       } else {
//         doc.rect(190, yPos - 3, 5, 5); // Disegna un rettangolo per la checkbox vuota
//       }
//     }

//     // Inizio della sezione dichiarazioneAppaltabilita su una nuova pagina
//     doc.addPage();
//     let currentY = 20; // Inizio della nuova pagina

//     doc.text("DICHIARAZIONE DI APPALTABILITA' DELL'INTERVENTO:", 10, currentY);
//     currentY += 10; // Spazio dopo il titolo

//     const pageWidth = doc.internal.pageSize.width;
//     const maxTextWidth = pageWidth - 60; // Regola la larghezza massima del testo per il wrappamento
//     const lineHeightDichiarazione = 10; // Altezza della linea costante per spaziatura omogenea

//     dichiarazioneAppaltabilita.forEach((section: any) => {
//       // Aggiungi il titolo della sezione
//       doc.setFont('helvetica', 'bold');
//       currentY = addTextWithOverflowCheck(doc, section.titolo, 10, currentY, lineHeightDichiarazione, maxTextWidth, 12, 'bold');

//       // Aggiungi le dichiarazioni
//       section.dichiarazioni.forEach((dichiarazione: any, index: number) => {
//         currentY = addTextWithOverflowCheck(doc, dichiarazione.voce, 10, currentY, lineHeightDichiarazione, maxTextWidth);

//         // Aggiungi la checkbox se il testo non è quello specificato
//         if (dichiarazione.voce !== "In ordine alle acquisizioni di arredi e di attrezzature sanitarie si dichiara che") {
//           const boxPosY = currentY - lineHeightDichiarazione; // Calcola la posizione Y della checkbox
//           addCheckbox(doc, boxPosY, dichiarazione.flag);
//         }

//         // Se è l'ultimo campo e ha flag true, aggiungi "Data di Validazione"
//         if (index === section.dichiarazioni.length - 1 && dichiarazione.flag) {
//           currentY += 10; // Spazio aggiuntivo prima di "Data di Validazione"
//           doc.setFont('helvetica', 'bold');
//           if (dichiarazione.validazioneData) {
//             const validationDate = new Date(dichiarazione.validazioneData);
//             const formattedDate = validationDate.toLocaleDateString('it-IT'); // Formattazione della data in gg/mm/aaaa
//             currentY = addTextWithOverflowCheck(doc, `Data di Validazione: ${formattedDate}`, 10, currentY, lineHeightDichiarazione, maxTextWidth, 10, 'bold');
//           } else {
//             currentY = addTextWithOverflowCheck(doc, `Data di Validazione:`, 10, currentY, lineHeightDichiarazione, maxTextWidth, 10, 'bold');
//           }
//         }
//       });

//       currentY += 10;
//     });

//     // Posizionamento del rettangolo dei responsabili
//     doc.addPage();
//     const numeroResponsabili = data.responsabili.length;
//     const altezzaRettangolo = 10 + (numeroResponsabili * 5) + 5 + 5 + 5 + 5 + 5 + 20; // 10 per il titolo, 5 per ogni responsabile, 5 per ogni campo successivo, 20 per il margine inferiore
//     const rectY = doc.internal.pageSize.getHeight() - altezzaRettangolo - 10; // 10 units di margine inferiore
//     doc.setDrawColor(0, 0, 0); // Nero per il bordo
//     doc.setFillColor(255, 255, 255); // Bianco per il riempimento
//     doc.rect(10, rectY, 190, altezzaRettangolo, 'FD'); // x, y, larghezza, altezza, tipo di riempimento ('FD' per fill and draw)

//     // Aggiunta dei responsabili nel rettangolo
//     doc.setFont("helvetica", "normal");
//     doc.setTextColor(0, 0, 0); // Nero per il testo
//     doc.text(`Responsabili procedimento:`, 15, rectY + 10);
//     let responsabiliY = rectY + 15;
//     data.responsabili.forEach((responsabile: any) => {
//       const nomeCognome = `${responsabile.respProcedimentoNome} ${responsabile.respProcedimentoCognome}`;
//       doc.text(nomeCognome, 15, responsabiliY);
//       responsabiliY += 5; // Spazio per il prossimo responsabile
//     });

//     // Aggiunta del Direttore Generale nel rettangolo
//     doc.text(`Direttore Generale:`, 15, responsabiliY + 5);
//     doc.text(`${data.dirGeneraleNome} ${data.dirGeneraleCognome}`, 55, responsabiliY + 5);
//     responsabiliY += 10;

//     // Aggiunta del Commissario nel rettangolo
//     doc.text(`Commissario:`, 15, responsabiliY);
//     doc.text(`${data.intCommissarioNome} ${data.intCommissarioCognome}`, 55, responsabiliY);
//     responsabiliY += 10;

//     // Aggiunta del Referente della Pratica nel rettangolo
//     doc.text(`Referente della Pratica:`, 15, responsabiliY);
//     doc.text(`${data.refPratica.refPraticaNome} ${data.refPratica.refPraticaCognome}`, 55, responsabiliY);
//     responsabiliY += 10;

//     // Aggiunta di Telefono, Fax e Email nel rettangolo
//     doc.text(`Telefono: ${data.refPratica.refPraticaTel}`, 15, responsabiliY);
//     responsabiliY += 5;
//     doc.text(`Fax: ${data.refPratica.refPraticaFax}`, 15, responsabiliY);
//     responsabiliY += 5;
//     doc.text(`Email: ${data.refPratica.refPraticaEmail}`, 15, responsabiliY);
//     responsabiliY += 5;

//     return doc.output('datauristring').split(',')[1];
//   }
// }

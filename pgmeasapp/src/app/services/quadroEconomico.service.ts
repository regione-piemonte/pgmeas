/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

import { Injectable } from "@angular/core";


@Injectable({
  providedIn: 'root',
})
export class quadroEconomicoService {

  //partendo dal quadro economico costruisce la mappa per la chiamata
  buildQuadroEconomicoMap(quadroEconList: any[]): { [key: number]: number } {
    let quadroEconMap: { [key: number]: number } = {};

    if (quadroEconList) {
      quadroEconList.forEach(quadro => {
        let id = Number(quadro.classifTreeId); // Converti la chiave in numero

        let valore: number;
        if (quadro.classifTreeConImporto || quadro.classifTreeEditabile || quadro.valoreNumerico) {
          let decimals = quadro.classifTreeImportoDecimali;

          // Gestisci valoreNumerico come stringa o numero
          let valoreNumerico = quadro.valoreNumerico != null
            ? parseFloat(quadro.valoreNumerico.toString().replace(',', '.'))
            : 0;

          valore = parseFloat(valoreNumerico.toFixed(decimals));
        } else {
          valore = 0; // Assegna 0 invece di null per rispettare il tipo
        }

        quadroEconMap[id] = valore;
      });
    }

    return quadroEconMap;
  }

}

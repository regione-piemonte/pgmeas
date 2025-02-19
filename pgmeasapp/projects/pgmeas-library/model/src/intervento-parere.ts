/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatoLightExt } from "./allegato-light-ext";

export interface InterventoParere {
    parere: boolean;
    allegati: AllegatoLightExt[];      
}
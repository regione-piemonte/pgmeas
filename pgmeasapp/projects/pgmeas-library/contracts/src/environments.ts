/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export interface Environments {
    appName: string;
    production: boolean;
    disableSignIn: boolean;
    baseUrl: string;
    simpleBaseUrl: string;
    apiEndpoint: any;
    secrets: any;
}

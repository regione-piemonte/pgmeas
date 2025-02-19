/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;

import java.util.List;

public record ImportiSnse(List<VociImportiSnse> voci, VociImportiSnse totali ) {

}

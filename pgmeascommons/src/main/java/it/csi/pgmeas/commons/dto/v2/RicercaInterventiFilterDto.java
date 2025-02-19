/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;
import java.util.List;

import it.csi.pgmeas.commons.util.enumeration.OrderByInterventoEnum;
import it.csi.pgmeas.commons.util.enumeration.OrderDirectionEnum;
import lombok.Data;

@Data
public class RicercaInterventiFilterDto implements Serializable {

	private static final long serialVersionUID = 8639925314641644606L;

	//Riferimenti temporali
	private Integer anno = null;
	
	//Principali riferimenti dell'intervento
	private String cup = null;
	private String codPgmeas = null;
	private String titolo = null;
	private List<Integer> obiettivi = null;
	private List<Integer> finalita = null;
	private List<Integer> categorie = null;
	private List<Integer> stati = null;
	private List<Integer> statiProgettuali = null;
	
	//Ambito e tipologia
	private List<Integer> tipi = null;
	private List<Integer> appaltiTipo = null;
	private List<Integer> contrattiTipo = null;
	
	//Localizzazione
	private List<Integer> aziende = null;
	private List<Integer> strutture = null;
	
	//Riferimenti economici e finanziari
	private Boolean soloInterventiFinanziati = null; //FIN_IMPORTO>0
	private List<Integer> finanziamenti = null;
	private List<Integer> finanziamentiTipo = null;
	
	//TODO
	private String orderBy = null; 
	private String orderDirection = null;
	private Integer rowPerPage = null;
	private Integer pageNumber = null;
}

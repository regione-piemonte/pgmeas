/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;

import it.csi.pgmeas.commons.model.base.DecodificaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="pgmeas_d_finanziamento_importo_tipo")
@Data
public class FinanziamentoImportoTipo extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 2606176076536022942L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fin_imp_tipo_id")
	private Integer finImpTipoId;
	
	@Column(name = "fin_imp_tipo_cod")
	private String finImpTipoCod;
	
	@Column(name = "fin_imp_tipo_desc")
	private String finImpTipoDesc;
	
	@Column(name = "etichetta_interfaccia")
	private String etichettaInterfaccia;
	
}

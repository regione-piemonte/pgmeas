/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

import it.csi.pgmeas.commons.model.base.DecodificaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="pgmeas_r_finanziamento_importo")
public class RFinanziamentoImporto extends DecodificaBaseEntity implements Serializable {
	
	private static final long serialVersionUID = -9209079147614443516L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fin_imp_id")
	private Integer finImpId;
	
	@Column(name = "fin_id")
	private Integer finId;
	
	@Column(name = "fin_imp_tipo_id")
	private Integer finImpTipoId;
	
	@Column(name = "fin_importo")
	private BigDecimal finImporto;
		
	@Column(name = "ente_id")
	private Integer enteId; 

}

/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.csi.pgmeas.commons.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pgmeas_d_batch_errore")
public class DBatchErrore extends BaseEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batch_err_id")
	private Integer batchErrId;

	@Column(name = "batch_err_data")
	private Timestamp batchErrData;

	@Column(name = "batch_err_dovutoa_tab_nome")
	private String batchErrDovutoaTabNome;

	@Column(name = "batch_err_dovutoa_tab_id")
	private Long batchErrDovutoaTabId;

	@Column(name = "batch_err_messaggio")
	private String batchErrMessaggio;

	@Column(name = "batch_err_descrizione")
	private String batchErrDescrizione;

	@Column(name = "err_categoria_id")
	private Integer errCategoriaId;

}

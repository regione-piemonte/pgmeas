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
@Table(name="pgmeas_t_modulo_file")
@Data
public class ModuloFile extends DecodificaBaseEntity implements Serializable{
	 
	private static final long serialVersionUID = -4951966519491238057L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Integer fileId;
	
	@Column(name = "file_name_user")
	private String fileNameUser;
	
	@Column(name = "file_name_system")
	private String fileNameSystem;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "file_path")
	private String filePath;

	@Column(name = "ente_id")
	private Integer enteId;
	
}

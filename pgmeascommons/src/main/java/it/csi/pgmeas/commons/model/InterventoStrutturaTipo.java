//package it.csi.pgmeas.commons.model;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name="pgmeas_d_intervento_struttura_tipo")
//public class InterventoStrutturaTipo implements Serializable {
//
//	private static final long serialVersionUID = -8486433602325138560L;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "int_str_tipo_id")
//	private Integer intStrTipoId;
//	
//	@Column(name = "int_str_tipo_cod")
//	private String intStrTipoCod;
//	
//	@Column(name = "int_str_tipo_desc")
//	private String intStrTipoDesc;
//	
//	@Column(name = "validita_inizio")
//	private Timestamp validitaInizio;
//	
//	@Column(name = "validita_fine")
//	private Timestamp validitaFine;
//	
//	@Column(name = "data_creazione")
//	private Timestamp dataCreazione;
//	
//	@Column(name = "data_modifica")
//	private Timestamp dataModifica;
//	
//	@Column(name = "data_cancellazione")
//	private Timestamp dataCancellazione;
//	
//	@Column(name = "utente_creazione")
//	private String utenteCreazione;
//	
//	@Column(name = "utente_modifica")
//	private String utenteModifica;
//	
//	@Column(name = "utente_cancellazione")
//	private String utenteCancellazione;
//	
//}

package com.seguridadlimite.models.entity;

public class FormatoEvaluacionPractica {
  
	private String nombregrupo;
	private Integer orden;
	private String nombrepregunta;
	private String cumple;
	private String nocumple;
	private Double notapractica;
	private Integer ordengrupopregunta;
	private String sourcefirma;

  public FormatoEvaluacionPractica() {
  }
	
	
  public String getNombregrupo() {
		return nombregrupo;
  }
	
	
	public void setNombregrupo(String nombregrupo) {
		this.nombregrupo = nombregrupo;
	}
	
	
	public Integer getOrden() {
		return orden;
	}
	
	
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
	
	public String getNombrepregunta() {
		return nombrepregunta;
	}
	
	
	public void setNombrepregunta(String nombrepregunta) {
		this.nombrepregunta = nombrepregunta;
	}
	
	
	public String getCumple() {
		return cumple;
	}
	
	
	public void setCumple(String cumple) {
		this.cumple = cumple;
	}
	
	
	public String getNocumple() {
		return nocumple;
	}
	
	
	public void setNocumple(String nocumple) {
		this.nocumple = nocumple;
	}
	
	
	public Double getNotapractica() {
		return notapractica;
	}
	
	
	public void setNotapractica(Double notapractica) {
		this.notapractica = notapractica;
	}
	
	
	public Integer getOrdengrupopregunta() {
		return ordengrupopregunta;
	}
	
	
	public void setOrdengrupopregunta(Integer ordengrupopregunta) {
		this.ordengrupopregunta = ordengrupopregunta;
	}


	public String getSourcefirma() {
		return sourcefirma;
	}


	public void setSourcefirma(String sourcefirma) {
		this.sourcefirma = sourcefirma;
	}
	
	  
}

package com.cmc.gestion.talento.notification;

public class ExceptionModel {
	private String codError;
	private String descripcion;
	
	public ExceptionModel(String codError,String descripcion) {
		this.codError=codError;
		this.descripcion=descripcion;
	}

	public String getCodError() {
		return codError;
	}

	public void setCodError(String codError) {
		this.codError = codError;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

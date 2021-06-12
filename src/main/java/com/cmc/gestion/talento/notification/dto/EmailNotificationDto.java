package com.cmc.gestion.talento.notification.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmailNotificationDto {
	
	@NotNull(message = "El correo es importante")
	@Email(message = "Correo invalido")
	private String correoDestinatario;
	
	@NotBlank(message = "El asunto es importante")
	private String asunto;
	
	@NotBlank(message = "El template es importante")
	private String template;
	private List<MapParametriaDto> parametria;
	private List<AttachmentDto> attachment;

	public String getCorreoDestinatario() {
		return correoDestinatario;
	}

	public void setCorreoDestinatario(String correoDestinatario) {
		this.correoDestinatario = correoDestinatario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<MapParametriaDto> getParametria() {
		return parametria;
	}

	public void setParametria(List<MapParametriaDto> parametria) {
		this.parametria = parametria;
	}

	public List<AttachmentDto> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<AttachmentDto> attachment) {
		this.attachment = attachment;
	}

}

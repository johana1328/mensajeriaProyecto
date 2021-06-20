package com.cmc.gestion.talento.notification.dto;

public class AttachmentDto {

	private String name;
	private String path;
	private AttachmenType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AttachmentDto [name=" + name + "]";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AttachmenType getType() {
		return type;
	}

	public void setType(AttachmenType type) {
		this.type = type;
	}

}

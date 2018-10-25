package com.ftd.smartshare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class DownloadDto {
	
	@XmlElement(name = "fileBytes")
	private byte[] fileBytes;

	public DownloadDto() {}
	
	public DownloadDto(byte[] fileBytes) {
		super();
		this.fileBytes = fileBytes;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}
	
}

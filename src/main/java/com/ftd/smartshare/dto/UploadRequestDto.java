package com.ftd.smartshare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UploadRequestDto {
	
	@XmlElement(name = "fileName")
	private String fileName;
	
	@XmlElement(name = "fileBytes")
	private byte[] fileBytes;

	@XmlElement(name = "password")
	private String password;
	
	@XmlElement(name = "expiration")
	private int expiration;
	
	@XmlElement(name = "maxDownloads")
	private int maxDownloads;
	
	public UploadRequestDto() {}

	public UploadRequestDto(String fileName, byte[] fileBytes, String password, int expiration, int maxDownloads) {
		super();
		this.fileName = fileName;
		this.fileBytes = fileBytes;
		this.password = password;
		this.expiration = expiration;
		this.maxDownloads = maxDownloads;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public int getMaxDownloads() {
		return maxDownloads;
	}

	public void setMaxDownloads(int maxDownloads) {
		this.maxDownloads = maxDownloads;
	}
	
}

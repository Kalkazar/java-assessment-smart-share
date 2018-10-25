package com.ftd.smartshare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ViewDto {
	@XmlElement(name = "downloadsLeft")
	private long downloadsLeft;
	
	@XmlElement(name = "timeLeft")
	private long timeLeft;
	
	public ViewDto() {}
	
	public ViewDto(long[] downloadsAndTime) {
		super();
		this.downloadsLeft = downloadsAndTime[0];
		this.timeLeft = downloadsAndTime[1];
	}

	public long getDownloadsLeft() {
		return downloadsLeft;
	}

	public void setDownloadsLeft(long downloadsLeft) {
		this.downloadsLeft = downloadsLeft;
	}

	public long getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(long timeLeft) {
		this.timeLeft = timeLeft;
	}
}

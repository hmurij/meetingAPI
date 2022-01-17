package com.visma.internship.exception;

import java.time.LocalDateTime;

public class MeetingError {
	private int httpCode;
	private String message;
	private LocalDateTime timeStamp;

	public MeetingError() {
	}

	public MeetingError(int httpCode, String message, LocalDateTime timeStamp) {
		this.httpCode = httpCode;
		this.message = message;
		this.timeStamp = timeStamp;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "MeetingError [httpCode=" + httpCode + ", message=" + message + ", timeStamp=" + timeStamp + "]";
	}

}

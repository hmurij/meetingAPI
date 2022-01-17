package com.visma.internship.entity;

public enum MeetingType {
	LIVE("Live"), IN_PERSON("InPerson");

	private String type;

	private MeetingType(String type) {
		this.type = type;
	}

	public String type() {
		return type;
	}
}

package com.visma.internship.entity;

public enum MeetingCategory {
	CODE_MONKEY("CodeMonkey"), HUB("Hub"), SHORT("Short"), TEAM_BUILDING("TeamBuilding");
	
	private String category;

	private MeetingCategory(String category){
		this.category = category;
	}
	
	public String category() {
		return category;
	}
}

package com.visma.internship.entity;

import java.time.LocalDate;
import java.util.List;

public class Meeting {


	private String name;
	private String responsiblePerson;
	private String description;
	private MeetingCategory category;
	private MeetingType type;
	private List<Attendee> attendees;
	private LocalDate startDate;
	private LocalDate endDate;

	public Meeting() {
	}

	public Meeting(String name, String responsiblePerson, String description, MeetingCategory category,
			MeetingType type, List<Attendee> attendees, LocalDate startDate, LocalDate endDate) {
		this.name = name;
		this.responsiblePerson = responsiblePerson;
		this.description = description;
		this.category = category;
		this.type = type;
		this.attendees = attendees;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MeetingCategory getCategory() {
		return category;
	}

	public void setCategory(MeetingCategory category) {
		this.category = category;
	}

	public MeetingType getType() {
		return type;
	}

	public void setType(MeetingType type) {
		this.type = type;
	}

	public List<Attendee> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Meeting [name=" + name + ", responsiblePerson=" + responsiblePerson + ", description=" + description
				+ ", category=" + category + ", type=" + type + ", attendees=" + attendees + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

}

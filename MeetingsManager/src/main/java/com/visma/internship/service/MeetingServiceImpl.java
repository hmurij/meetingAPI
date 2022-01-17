package com.visma.internship.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visma.internship.dao.MeetingDAO;
import com.visma.internship.entity.Attendee;
import com.visma.internship.entity.Meeting;
import com.visma.internship.entity.MeetingCategory;
import com.visma.internship.entity.MeetingType;

@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	MeetingDAO meetingDAO;

	@Override
	public List<Meeting> findAll() {
		return meetingDAO.findAll();
	}

	@Override
	public Meeting findByName(String name) {
		return meetingDAO.findByName(name);
	}

	@Override
	public Meeting save(Meeting meeting) {
		return meetingDAO.save(meeting);
	}

	@Override
	public void delete(Meeting meeting) {
		meetingDAO.delete(meeting);

	}

	@Override
	public void addAttendeeToMeeting(Meeting meeting, Attendee person) {
		meetingDAO.addAttendeeToMeeting(meeting, person);
	}

	@Override
	public void removeAttendee(Attendee person, Meeting meeting) {
		meetingDAO.removeAttendee(person, meeting);

	}

	@Override
	public List<Meeting> findByDescription(String description) {
		return meetingDAO.findByDescription(description);

	}

	@Override
	public List<Meeting> findByResponsiblePerson(String person) {
		return meetingDAO.findByResponsiblePerson(person);
	}

	@Override
	public List<Meeting> findByCategory(MeetingCategory category) {
		return meetingDAO.findByCategory(category);
	}

	@Override
	public List<Meeting> findByType(MeetingType type) {
		return meetingDAO.findByType(type);
	}

	@Override
	public List<Meeting> findStartingFromDate(LocalDate date) {
		return meetingDAO.findStartingFromDate(date);
	}

	@Override
	public List<Meeting> findByDateRange(LocalDate start, LocalDate end) {
		return meetingDAO.findByDateRange(start, end);
	}

	@Override
	public List<Meeting> findByMinAttendees(int number) {
		return meetingDAO.findByMinAttendees(number);
	}

}

/*




























*/

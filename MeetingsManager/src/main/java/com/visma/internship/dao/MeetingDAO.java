package com.visma.internship.dao;

import java.time.LocalDate;
import java.util.List;

import com.visma.internship.entity.Attendee;
import com.visma.internship.entity.Meeting;
import com.visma.internship.entity.MeetingCategory;
import com.visma.internship.entity.MeetingType;

public interface MeetingDAO {

	/**
	 * Find all meetings
	 * 
	 * @return List of Meeting objects of empty list if not found
	 */
	public List<Meeting> findAll();

	/**
	 * Find meeting by name
	 * 
	 * @param name - meeting name
	 * @return Meeting object with specified name or null if not found
	 */
	public Meeting findByName(String name);

	/**
	 * Saves new meeting
	 * 
	 * @param meeting - Meeting object to be saved
	 * @return saved Meeting object
	 */
	public Meeting save(Meeting meeting);

	/**
	 * Delete meeting
	 * 
	 * @param meeting - meeting to be deleted
	 */
	public void delete(Meeting meeting);

	/**
	 * Adds new attendee to the meeting
	 * 
	 * @param meeting - Meeting object new attendee to be added
	 * @param person  - new attendee to be added to the meeting
	 */
	public void addAttendeeToMeeting(Meeting meeting, Attendee person);

	/**
	 * Removes attendee from the meeting
	 * 
	 * @param person  - attendee to be removed from the meeting
	 * @param meeting - meeting attendee to be removed from
	 */
	public void removeAttendee(Attendee person, Meeting meeting);

	/**
	 * Find meeting by text containing in meeting meeting description
	 * 
	 * @param description - text to search for in meeting description
	 * @return List of Meeting objects or empty List if not found
	 */
	public List<Meeting> findByDescription(String description);

	/**
	 * Find meeting by responsible person
	 * 
	 * @param person - name of responsible person as String object
	 * @return List of Meeting objects or empty List if not found
	 */
	public List<Meeting> findByResponsiblePerson(String person);

	/**
	 * Find meeting by specified meeting category
	 * 
	 * @param category - one of fixed type CODE_MONKEY, HUB, SHORT, TEAM_BUILDING
	 *                 values
	 * @return List of Meeting objects or empty List if not found
	 */
	public List<Meeting> findByCategory(MeetingCategory category);

	/**
	 * Find meeting by specified meeting type
	 * 
	 * @param type - one of fixed type LIVE, IN_PERSON values
	 * @return List of Meeting objects or empty List if not found
	 */
	public List<Meeting> findByType(MeetingType type);

	/**
	 * Find meetings as from given date
	 * 
	 * @param date - any arbitrary date
	 * @return List if Meeting objects starting from given date if not found returns
	 *         empty List
	 */
	public List<Meeting> findStartingFromDate(LocalDate date);

	/**
	 * Find meetings starting in given date range
	 * 
	 * @param start - range start search date
	 * @param end   - range end search date
	 * @return List of Meeting objects starting in given date range if not found
	 *         returns empty List
	 */
	public List<Meeting> findByDateRange(LocalDate start, LocalDate end);

	/**
	 * Find meetings with minimum number of attendees
	 * 
	 * @param number - any arbitrary number > 0
	 * @return List of Meeting objects with given min number of attendees and empty
	 *         list if not found
	 */
	public List<Meeting> findByMinAttendees(int number);

}

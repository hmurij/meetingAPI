package com.visma.internship.controller;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.visma.internship.entity.Attendee;
import com.visma.internship.entity.Meeting;
import com.visma.internship.entity.MeetingCategory;
import com.visma.internship.entity.MeetingType;
import com.visma.internship.exception.MeetingNotFoudException;
import com.visma.internship.service.MeetingService;

@RestController
@RequestMapping("/api")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@Value("${file.path}")
	private String filePath;

	/**
	 * Handles GET request to get all Meetings
	 * 
	 * @return List of Meeting objects or empty list if not found
	 */
	@GetMapping("/meetings")
	public List<Meeting> getMeetings() {

		if (!Files.exists(Path.of(filePath), new LinkOption[0])) {
			throw new RuntimeException("Can't find file: " + Path.of(filePath).toString());
		}

		return meetingService.findAll();
	}

	/**
	 * Handles GET request to get meeting by name
	 * 
	 * @param name - meeting name
	 * @return Meeting object with specified name
	 * @throws MeetingNotFoudException if not found
	 */
	@GetMapping("/meetings/search/findByName")
	public Meeting getMeetingByName(@RequestParam("name") String name) {
		Meeting meeting = meetingService.findByName(name);

		if (meeting == null) {
			throw new MeetingNotFoudException("Can't find meeting with name: " + name);
		}

		return meeting;
	}

	/**
	 * Handles GET request to get meeting by text containing in meeting meeting
	 * description
	 * 
	 * @param description - text to search for in meeting description
	 * @return List of Meeting objects or empty List if not found
	 */
	@GetMapping("/meetings/search/findByDescription")
	public List<Meeting> getMeetingByDescription(@RequestParam("description") String description) {

		return meetingService.findByDescription(description);
	}

	/**
	 * Handles GET request to get meeting by responsible person
	 * 
	 * @param person - name of responsible person as String object
	 * @return List of Meeting objects or empty List if not found
	 */
	@GetMapping("/meetings/search/findByResponsiblePerson")
	public List<Meeting> getMeetingByResponsiblePerson(@RequestParam("person") String person) {

		return meetingService.findByResponsiblePerson(person);
	}

	/**
	 * Handles GET request to get meeting by specified meeting category
	 * 
	 * @param category - one of fixed type CODE_MONKEY, HUB, SHORT, TEAM_BUILDING
	 *                 values
	 * @return List of Meeting objects or empty List if not found
	 */
	@GetMapping("/meetings/search/findByCategory")
	public List<Meeting> getMeetingByCategory(@RequestParam("category") MeetingCategory category) {

		return meetingService.findByCategory(category);
	}

	/**
	 * Handles GET request to get meeting by specified meeting type
	 * 
	 * @param type - one of fixed type LIVE, IN_PERSON values
	 * @return List of Meeting objects or empty List if not found
	 */
	@GetMapping("/meetings/search/findByType")
	public List<Meeting> getMeetingByType(@RequestParam("type") MeetingType type) {

		return meetingService.findByType(type);
	}

	/**
	 * Handles GET request to search for meetings as from given date
	 * 
	 * @param date - search date in format yyyy-mm-dd
	 * @return List of Meeting objects starting from given date if not found returns
	 *         empty List
	 * @throws DateTimeParseException if the date cannot be parsed
	 */
	@GetMapping("/meetings/search/findStartingFromDate")
	public List<Meeting> getMeetingStartingFromDate(@RequestParam("date") String date) {

		return meetingService.findStartingFromDate(LocalDate.parse(date));
	}

	/**
	 * Handles GET request to search for meetings starting in given date range
	 * 
	 * @param start - range start search date in format yyyy-mm-dd
	 * @param end   - range end search date in format yyyy-mm-dd
	 * @return List of Meeting objects starting in given date range if not found
	 *         returns empty List
	 * @throws DateTimeParseException if the start/end dates cannot be parsed
	 * @throws RuntimeException       if start date is later than end date
	 */
	@GetMapping("/meetings/search/findByDateRange")
	public List<Meeting> getMeetingByDateRange(@RequestParam("start") String start, @RequestParam("end") String end) {

		LocalDate startDate = LocalDate.parse(start);
		LocalDate endDate = LocalDate.parse(end);

		if (startDate.compareTo(endDate) > 0) {
			throw new RuntimeException(
					"Invalid date range. Start date: " + startDate + " can't be after end date: " + endDate);
		}

		return meetingService.findByDateRange(startDate, endDate);
	}

	/**
	 * Handles GET request to search for meetings with minimum number of attendees
	 * 
	 * @param number - any arbitrary number > 0
	 * @return List of Meeting objects with given min number of attendees and empty
	 *         list if not found
	 * @exception RuntimeException if number <= 0
	 */
	@GetMapping("/meetings/search/findByMinAttendees")
	public List<Meeting> getMeetingByMinAttendees(@RequestParam("number") int number) {

		if (number <= 0) {
			throw new RuntimeException("Invalid number: " + number + " of attendees, should be greater than 0");
		}

		return meetingService.findByMinAttendees(number);
	}

	/**
	 * Handles POST request to save meeting
	 * 
	 * @param meeting - Meeting object to be saved
	 * @return saved Meeting object
	 * @throws RuntimeException if meeting with the same name already exists
	 */
	@PostMapping("/meetings")
	public Meeting save(@RequestBody Meeting meeting) {

		if (meetingService.findByName(meeting.getName()) != null) {
			throw new RuntimeException("Meeting with name: " + meeting.getName() + " already exsists");
		}

		return meetingService.save(meeting);
	}

	/**
	 * Handles PUT request add new attendee to the meeting
	 * 
	 * @param meetingName - name of existing meeting String object
	 * @param attendee    - name of new attendee String object
	 * @param time        - time when new attendee was added to the meeting String
	 *                    object in hh:mm format
	 * @return String message, confirming that new attendee was added to the
	 *         specified meeting at given time
	 * @throws ParseException          if time provided in incorrect format
	 * @throws MeetingNotFoudException if meeting with specified name not found
	 * @throws RuntimeException        if attendee is already in the meeting
	 * @throws RuntimeException        if attendee is already in a meeting which
	 *                                 intersects with the one being added
	 */
	@PutMapping("/meetings")
	public String addAttendee(@RequestParam("meetingName") String meetingName,
			@RequestParam("attendee") String attendee, @RequestParam("time") String time) throws ParseException {

		LocalTime localTime = LocalTime.parse(time);
		Attendee person = new Attendee(attendee.substring(0, attendee.indexOf(" ")),
				attendee.substring(attendee.indexOf(" ") + 1));

		Meeting meeting = meetingService.findByName(meetingName);
		if (meeting == null) {
			throw new MeetingNotFoudException("Can't find meeting with name: " + meetingName);
		}
		if (meeting.getAttendees().contains(person)) {
			throw new RuntimeException("Person name: " + attendee + " already in the meeting name: " + meetingName);
		}

		checkForIntersectingMeetings(person, meeting);

		meetingService.addAttendeeToMeeting(meeting, person);

		return "Attendee name: " + attendee + " added to meeting name: " + meetingName + " at time: " + localTime;
	}

	/**
	 * Check if attendee is already in a meeting which intersects with the one being
	 * added
	 * 
	 * @param person  - Attendee object of new attendee to be added to the meeting
	 * @param meeting - Meeting object new attendee to be added to
	 * @throws ParseException   if wrong date format
	 * @throws RuntimeException if new attendee is in intersecting meeting
	 */
	private void checkForIntersectingMeetings(Attendee person, Meeting meeting) throws ParseException {
		// Check of intersecting meetings
		var potentiallyIntersectingMeetings = meetingService.findAll().stream()
				.filter(m -> m.getAttendees().contains(person)).collect(Collectors.toList());

		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

		Date s1 = s.parse(meeting.getStartDate().toString());
		Date e1 = s.parse(meeting.getEndDate().toString());

		for (Meeting m : potentiallyIntersectingMeetings) {

			Date s2 = s.parse(m.getStartDate().toString());
			Date e2 = s.parse(m.getEndDate().toString());

			if (s1.before(s2) && e1.after(s2) || s1.before(e2) && e1.after(e2) || s1.before(s2) && e1.after(e2)
					|| s1.after(s2) && e1.before(e2)) {
				throw new RuntimeException("Person name: " + person.getFirstName() + " " + person.getLastName()
						+ " has intersecting meetings. Meeting name: " + m.getName() + " from " + m.getStartDate()
						+ " till " + m.getEndDate() + ". Meeting name: " + meeting.getName() + " from "
						+ meeting.getStartDate() + " till " + meeting.getEndDate());
			}

		}
	}

	/**
	 * Handles DELETE request to delete meeting by responsible person
	 * 
	 * @param responsiblePerson - name of responsible person of the meeting
	 * @param meetingName       - name of meeting to be deleted
	 * @return message confirming meeting with specified meeting name was delete by
	 *         responsible person
	 * @throws MeetingNotFoudException if meeting with specified name not found
	 * @throws RuntimeException        if invalid responsible person name
	 */
	@DeleteMapping("/meetings")
	public String delete(@RequestParam("responsiblePerson") String responsiblePerson,
			@RequestParam("meetingName") String meetingName) {

		Meeting meeting = meetingService.findByName(meetingName);
		if (meeting == null) {
			throw new MeetingNotFoudException("Can't find meeting with name: " + meetingName);
		}
		if (!meeting.getResponsiblePerson().equals(responsiblePerson)) {
			throw new RuntimeException("Only responsible persone: " + meeting.getResponsiblePerson()
					+ " can delete meeting name: " + meeting.getName());
		}

		meetingService.delete(meeting);

		return "Deleted meeting name: " + meetingName + " by responsible persone: " + responsiblePerson;
	}

	/**
	 * Handles DELETE request to remove attendee from the meeting
	 * 
	 * @param meetingName - name of the meeting attendee to be removed from
	 * @param attendee    - name of attendee to be removed from the meeting
	 * @return String message confirming specified attendee was removed from the
	 *         meeting
	 * @throws MeetingNotFoudException if meeting with specified name not found
	 * @throws RuntimeException        if attendee is not in the meeting
	 * @throws RuntimeException        if attendee is responsible person
	 */
	@DeleteMapping("/attendees")
	public String removeAttendee(@RequestParam("meetingName") String meetingName,
			@RequestParam("attendee") String attendee) {

		Meeting meeting = meetingService.findByName(meetingName);
		if (meeting == null) {
			throw new MeetingNotFoudException("Can't find meeting with name: " + meetingName);
		}

		Attendee person = new Attendee(attendee.substring(0, attendee.indexOf(" ")),
				attendee.substring(attendee.indexOf(" ") + 1));

		if (!meeting.getAttendees().contains(person)) {
			throw new RuntimeException(
					"Person name: " + attendee + " is not attending meeting name: " + meeting.getName());
		}

		if (meeting.getResponsiblePerson().equals(attendee)) {
			throw new RuntimeException(
					"Can't remove responsible persone name: " + attendee + " from meeting name: " + meeting.getName());
		}

		meetingService.removeAttendee(person, meeting);

		return "Person name: " + attendee + " removed from meeting name: " + meetingName;
	}

}

/*



































*/

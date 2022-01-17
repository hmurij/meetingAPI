package com.visma.internship.doa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.visma.internship.dao.MeetingDAO;
import com.visma.internship.entity.Attendee;
import com.visma.internship.entity.Meeting;
import com.visma.internship.entity.MeetingCategory;
import com.visma.internship.entity.MeetingType;

@SpringBootTest(properties = { "file.path=data/test.json" })
public class MeetingDAOImplFileTests {

	@Autowired
	private MeetingDAO meetingDAO;

	@Test
	void findAllTest() {
		assertEquals(meetingDAO.findAll().size(), 4);
	}

	@Test
	void findByNameTestFound() {
		assertEquals(meetingDAO.findByName("Java Internship 2022").getName(), "Java Internship 2022");
	}

	@Test
	void findByNameTestNotFound() {
		assertNull(meetingDAO.findByName("Java Internship"));
	}

	@Test
	void findByDescriptionTestFound() {
		assertEquals(meetingDAO.findByDescription("Visma .NET Internship").size(), 1);
	}

	@Test
	void findByDescriptionTestNotFound() {
		assertEquals(meetingDAO.findByDescription("C++").size(), 0);
	}

	@Test
	void findByResponsiblePersonTestFound() {
		assertEquals(meetingDAO.findByResponsiblePerson("Robert Farah").size(), 1);
	}

	@Test
	void findByResponsiblePersonTestNotFound() {
		assertEquals(meetingDAO.findByResponsiblePerson("John Doe").size(), 0);
	}

	@Test
	void findByCategoryTestFound() {
		assertEquals(meetingDAO.findByCategory(MeetingCategory.HUB).size(), 4);
	}

	@Test
	void findByCategoryTestNotFound() {
		assertEquals(meetingDAO.findByCategory(MeetingCategory.CODE_MONKEY).size(), 0);
	}

	@Test
	void findByTypeTestFound() {
		assertEquals(meetingDAO.findByType(MeetingType.IN_PERSON).size(), 4);
	}

	@Test
	void findByTypeTestNotFound() {
		assertEquals(meetingDAO.findByType(MeetingType.LIVE).size(), 0);
	}

	@Test
	void saveTest() {
		Meeting meeting = new Meeting();
		meeting.setName("NEW MEETING");
		meetingDAO.save(meeting);
//		meetingDAO.findAll().forEach(System.out::println);
		assertEquals(meetingDAO.findAll().size(), 5);
		assertEquals(meetingDAO.findByName("NEW MEETING").getName(), "NEW MEETING");
		meetingDAO.delete(meeting);
	}

	@Test
	void deleteTest() {
		Meeting meeting = new Meeting();
		meeting.setName("NEW MEETING");
		meetingDAO.save(meeting);
		assertEquals(meetingDAO.findAll().size(), 5);
		meetingDAO.delete(meeting);
		assertEquals(meetingDAO.findAll().size(), 4);
	}

	@Test
	void addAttendeeToMeeting() {
		var meeting = meetingDAO.findByName("Java Internship 2022");
		var numberOfAttendies = meeting.getAttendees().size();

		meetingDAO.addAttendeeToMeeting(meeting, new Attendee("John", "Doe"));
		assertEquals(meetingDAO.findByName("Java Internship 2022").getAttendees().size(), numberOfAttendies + 1);
		meetingDAO.removeAttendee(new Attendee("John", "Doe"), meeting);
	}

	@Test
	void removeAttendee() {
		var meeting = meetingDAO.findByName("Java Internship 2022");
		var numberOfAttendies = meeting.getAttendees().size();

		meetingDAO.addAttendeeToMeeting(meeting, new Attendee("John", "Doe"));
		assertEquals(meetingDAO.findByName("Java Internship 2022").getAttendees().size(), numberOfAttendies + 1);
		meetingDAO.removeAttendee(new Attendee("John", "Doe"), meeting);
		assertEquals(meetingDAO.findByName("Java Internship 2022").getAttendees().size(), numberOfAttendies);
	}

	@Test
	void findStartingFromDateFound() {
		assertEquals(meetingDAO.findStartingFromDate(LocalDate.of(2022, 1, 29)).size(), 1);
	}

	@Test
	void findStartingFromDateNotFound() {
		assertEquals(meetingDAO.findStartingFromDate(LocalDate.of(2022, 2, 27)).size(), 0);
	}

	@Test
	void findByDateRangeFound() {
		assertEquals(meetingDAO.findByDateRange(LocalDate.of(2022, 1, 28), LocalDate.of(2022, 1, 31)).size(), 2);
	}

	@Test
	void findByDateRangeNotFound() {
		assertEquals(meetingDAO.findByDateRange(LocalDate.of(2022, 3, 28), LocalDate.of(2022, 3, 31)).size(), 0);
	}

	@Test
	void findByMinAttendeesFound() {
		assertEquals(meetingDAO.findByMinAttendees(3).size(), 4);
	}

	@Test
	void findByMinAttendeesNotFound() {
		assertEquals(meetingDAO.findByMinAttendees(5).size(), 0);
	}

}

/*



































*/

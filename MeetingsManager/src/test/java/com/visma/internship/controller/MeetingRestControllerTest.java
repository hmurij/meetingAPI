package com.visma.internship.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.internship.dao.MeetingDAO;
import com.visma.internship.entity.Attendee;
import com.visma.internship.entity.Meeting;
import com.visma.internship.exception.MeetingError;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = { "file.path=data/test.json",
		"server.port=8182" })
public class MeetingRestControllerTest {

	@Autowired
	MeetingRestController controller;

	@Autowired
	MeetingDAO meetingDAO;

	@Value("${server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void getMeetingsTest() {

		@SuppressWarnings("rawtypes")
		ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/api/meetings",
				List.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 4);
	}

	@Test
	public void getMeetingByNameTestFound() {

		ResponseEntity<Meeting> responseEntity = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/meetings/search/findByName?name={name}", Meeting.class,
				"Java Internship 2022");

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().getName(), "Java Internship 2022");

	}

	@Test
	public void getMeetingByNameTestNotFound() {
		String name = "Ruby Internship 2022";

		ResponseEntity<MeetingError> responseEntity = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/meetings/search/findByName?name={name}", MeetingError.class, name);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(responseEntity.getBody().getMessage(), "Can't find meeting with name: " + name);

	}

	@Test
	public void getMeetingByDescriptionTestFound() {

		String description = "Java Internship";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByDescription?description={description}",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Meeting>>() {
				}, description);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 1);
	}

	@Test
	public void getMeetingByDescriptionTestNotFound() {

		String description = "C++";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByDescription?description={description}",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Meeting>>() {
				}, description);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 0);
	}

	@Test
	public void getMeetingByResponsiblePersonTestFound() {
		String person = "Yasuko Rice";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByResponsiblePerson?person={person}",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Meeting>>() {
				}, person);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 1);

	}

	@Test
	public void getMeetingByResponsiblePersonTestNotFound() {
		String person = "John Doe";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByResponsiblePerson?person={person}",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Meeting>>() {
				}, person);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 0);

	}

	@Test
	public void getMeetingByCategoryFound() {
		String category = "HUB";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByCategory?category={category}", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Meeting>>() {
				}, category);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 4);
	}

	@Test
	public void getMeetingByCategoryNotFound() {
		String category = "CODE_MONKEY";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByCategory?category={category}", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Meeting>>() {
				}, category);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 0);
	}

	@Test
	public void getMeetingByTypeFound() {
		String type = "IN_PERSON";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByType?type={type}", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Meeting>>() {
				}, type);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 4);
	}

	@Test
	public void getMeetingByTypeNotFound() {
		String type = "LIVE";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByType?type={type}", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Meeting>>() {
				}, type);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 0);
	}

	@Test
	public void getMeetingStartingFromDateTestFound() {
		String date = "2022-01-29";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findStartingFromDate?date={date}", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Meeting>>() {
				}, date);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 1);
	}

	@Test
	public void getMeetingStartingFromDateTestNotFound() {
		String date = "2022-03-29";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findStartingFromDate?date={date}", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Meeting>>() {
				}, date);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 0);
	}

	@Test
	public void getMeetingByDateRangeTestFound() {
		String start = "2022-01-28";
		String end = "2022-01-31";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByDateRange?start={start}&end={end}",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Meeting>>() {
				}, start, end);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 2);
	}

	@Test
	public void getMeetingByDateRangeTestNotFound() {
		String start = "2022-03-28";
		String end = "2022-03-31";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByDateRange?start={start}&end={end}",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Meeting>>() {
				}, start, end);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 0);
	}

	@Test
	public void getMeetingByMinAttendeesTestFound() {
		String number = "3";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByMinAttendees?number={number}", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Meeting>>() {
				}, number);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 4);
	}

	@Test
	public void getMeetingByMinAttendeesTestNotFound() {
		String number = "5";

		ResponseEntity<List<Meeting>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings/search/findByMinAttendees?number={number}", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Meeting>>() {
				}, number);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().size(), 0);
	}

	@Test
	public void saveTest() throws JsonProcessingException {
		Meeting meeting = new Meeting();
		meeting.setName("NEW MEETING");

		var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(objectMapper.writeValueAsString(meeting), headers);

		ResponseEntity<Meeting> responseEntity = restTemplate
				.postForEntity("http://localhost:" + port + "/api/meetings", request, Meeting.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody().getName(), "NEW MEETING");

		meetingDAO.delete(meeting);
	}

	@Test
	public void saveTestExistingMeeting() throws JsonProcessingException {
		Meeting meeting = new Meeting();
		meeting.setName("Java Internship 2022");

		var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(objectMapper.writeValueAsString(meeting), headers);

		ResponseEntity<MeetingError> responseEntity = restTemplate
				.postForEntity("http://localhost:" + port + "/api/meetings", request, MeetingError.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(responseEntity.getBody().getMessage(),
				"Meeting with name: " + meeting.getName() + " already exsists");

	}

	@Test
	public void addAttendeeTest() {
		String meetingName = "Java Internship 2022";
		String attendee = "John Doe";
		String time = "11:11";

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings?meetingName={meetingName}&attendee={attendee}&time={time}",
				HttpMethod.PUT, null, String.class, meetingName, attendee, time);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody(),
				"Attendee name: " + attendee + " added to meeting name: " + meetingName + " at time: " + time);

		meetingDAO.removeAttendee(new Attendee("John", "Doe"), meetingDAO.findByName("Java Internship 2022"));
	}

	@Test
	public void addAttendeeTestInvalidMeetingName() {
		String meetingName = "C++ Internship 2022";
		String attendee = "John Doe";
		String time = "11:11";

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings?meetingName={meetingName}&attendee={attendee}&time={time}",
				HttpMethod.PUT, null, MeetingError.class, meetingName, attendee, time);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(responseEntity.getBody().getMessage(), "Can't find meeting with name: " + meetingName);
	}

	@Test
	public void addAttendeeTestInvalidAttendee() {
		String meetingName = "Java Internship 2022";
		String attendee = "Phyllis Ellis";
		String time = "11:11";

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings?meetingName={meetingName}&attendee={attendee}&time={time}",
				HttpMethod.PUT, null, MeetingError.class, meetingName, attendee, time);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(responseEntity.getBody().getMessage(),
				"Person name: " + attendee + " already in the meeting name: " + meetingName);
	}

	@Test
	public void addAttendeeTestIntersectingMeetings() {
		String meetingName = ".NET Internship 2022";
		String attendee = "Donna Maier";
		String time = "11:11";

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/meetings?meetingName={meetingName}&attendee={attendee}&time={time}",
				HttpMethod.PUT, null, MeetingError.class, meetingName, attendee, time);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void deleteTest() {
		String responsiblePerson = "John Doe";
		String meetingName = "NEW MEETING";

		Meeting meeting = new Meeting();
		meeting.setName("NEW MEETING");
		meeting.setResponsiblePerson(responsiblePerson);
		meetingDAO.save(meeting);
		int size = meetingDAO.findAll().size();

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:" + port
						+ "/api/meetings?responsiblePerson={responsiblePerson}&meetingName={meetingName}",
				HttpMethod.DELETE, null, String.class, responsiblePerson, meetingName);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody(),
				"Deleted meeting name: " + meetingName + " by responsible persone: " + responsiblePerson);
		assertEquals(meetingDAO.findAll().size(), size - 1);

	}

	@Test
	public void deleteTestInvalidMeetingName() {
		String responsiblePerson = "John Doe";
		String meetingName = "NEW MEETING";

		int size = meetingDAO.findAll().size();

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port
						+ "/api/meetings?responsiblePerson={responsiblePerson}&meetingName={meetingName}",
				HttpMethod.DELETE, null, MeetingError.class, responsiblePerson, meetingName);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(responseEntity.getBody().getMessage(), "Can't find meeting with name: " + meetingName);
		assertEquals(meetingDAO.findAll().size(), size);

	}

	@Test
	public void deleteTestInvalidResponsiblePerson() {
		String responsiblePerson = "John Doe";
		String meetingName = "Java Internship 2022";

		var meeting = meetingDAO.findByName(meetingName);

		int size = meetingDAO.findAll().size();

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port
						+ "/api/meetings?responsiblePerson={responsiblePerson}&meetingName={meetingName}",
				HttpMethod.DELETE, null, MeetingError.class, responsiblePerson, meetingName);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(responseEntity.getBody().getMessage(), "Only responsible persone: "
				+ meeting.getResponsiblePerson() + " can delete meeting name: " + meeting.getName());
		assertEquals(meetingDAO.findAll().size(), size);

	}

	@Test
	public void removeAttendeeTest() {
		String meetingName = "Java Internship 2022";
		String attendee = "John Doe";

		meetingDAO.addAttendeeToMeeting(meetingDAO.findByName("Java Internship 2022"), new Attendee("John", "Doe"));

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/attendees?meetingName={meetingName}&attendee={attendee}",
				HttpMethod.DELETE, null, String.class, meetingName, attendee);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getBody(),
				"Person name: " + attendee + " removed from meeting name: " + meetingName);

	}

	@Test
	public void removeAttendeeTestInvalidMeetingName() {
		String meetingName = "C++ Internship 2022";
		String attendee = "John Doe";

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/attendees?meetingName={meetingName}&attendee={attendee}",
				HttpMethod.DELETE, null, MeetingError.class, meetingName, attendee);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(responseEntity.getBody().getMessage(), "Can't find meeting with name: " + meetingName);

	}

	@Test
	public void removeAttendeeTestInvalidAttendee() {
		String meetingName = "Java Internship 2022";
		String attendee = "John Doe";

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/attendees?meetingName={meetingName}&attendee={attendee}",
				HttpMethod.DELETE, null, MeetingError.class, meetingName, attendee);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(responseEntity.getBody().getMessage(),
				"Person name: " + attendee + " is not attending meeting name: " + meetingName);

	}

	@Test
	public void removeAttendeeTestRemoveResponsiblePerson() {
		String meetingName = "Java Internship 2022";
		String attendee = "Sherry Baggett";

		ResponseEntity<MeetingError> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/attendees?meetingName={meetingName}&attendee={attendee}",
				HttpMethod.DELETE, null, MeetingError.class, meetingName, attendee);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(responseEntity.getBody().getMessage(),
				"Can't remove responsible persone name: " + attendee + " from meeting name: " + meetingName);

	}

}

/*






































*/

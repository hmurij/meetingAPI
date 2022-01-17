package com.visma.internship.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.internship.entity.Attendee;
import com.visma.internship.entity.Meeting;
import com.visma.internship.entity.MeetingCategory;
import com.visma.internship.entity.MeetingType;

@Repository
public class MeetingDAOImplFile implements MeetingDAO {

	@Value("${file.path}")
	private String filePath;

	private ObjectMapper objectMapper;

	public MeetingDAOImplFile() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	}

	@Override
	public List<Meeting> findAll() {
		List<Meeting> meetings = null;

		try {

			meetings = Files.readAllLines(Path.of(filePath)).stream().map(s -> {
				Meeting meeting = null;
				try {
					meeting = objectMapper.readValue(s, Meeting.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				return meeting;
			}).collect(Collectors.toList());
		} catch (IOException e) {
			meetings = List.of();
		}

		return meetings;
	}

	@Override
	public Meeting findByName(String name) {
		return findAll().stream().filter(m -> m.getName().equals(name)).findAny().orElse(null);
	}

	@Override
	public List<Meeting> findByDescription(String description) {
		return findAll().stream().filter(m -> m.getDescription().toLowerCase().contains(description.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Meeting> findByResponsiblePerson(String person) {
		return findAll().stream().filter(m -> m.getResponsiblePerson().toLowerCase().equals(person.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Meeting> findByCategory(MeetingCategory category) {
		return findAll().stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
	}

	@Override
	public List<Meeting> findByType(MeetingType type) {
		return findAll().stream().filter(m -> m.getType() == type).collect(Collectors.toList());
	}

	@Override
	public Meeting save(Meeting meeting) {

		try {
			Files.writeString(Path.of(filePath), objectMapper.writeValueAsString(meeting) + "\n",
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return meeting;
	}

	@Override
	public void delete(Meeting meeting) {

		try {

			Files.writeString(Path.of(filePath),
					findAll().stream().filter(v -> !v.getName().equals(meeting.getName())).map(t -> {
						String m = null;
						try {
							m = objectMapper.writeValueAsString(t);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						return m;
					}).collect(Collectors.joining("\n")) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addAttendeeToMeeting(Meeting meeting, Attendee person) {
		delete(meeting);
		meeting.getAttendees().add(person);
		save(meeting);
	}

	@Override
	public void removeAttendee(Attendee person, Meeting meeting) {
		delete(meeting);
		meeting.getAttendees().remove(person);
		save(meeting);
	}

	@Override
	public List<Meeting> findStartingFromDate(LocalDate date) {
		return findAll().stream().filter(m -> m.getStartDate().compareTo(date) >= 0).collect(Collectors.toList());
	}

	@Override
	public List<Meeting> findByDateRange(LocalDate start, LocalDate end) {
		return findAll().stream()
				.filter(m -> (m.getStartDate().compareTo(start) >= 0) && (m.getEndDate().compareTo(end) <= 0))
				.collect(Collectors.toList());
	}

	@Override
	public List<Meeting> findByMinAttendees(int number) {
		return findAll().stream().filter(m -> m.getAttendees().size() >= number).collect(Collectors.toList());
	}

}

/*




























*/

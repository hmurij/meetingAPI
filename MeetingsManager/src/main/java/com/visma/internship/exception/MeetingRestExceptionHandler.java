package com.visma.internship.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MeetingRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<MeetingError> handleMeetingNotFoundExcepton(MeetingNotFoudException e) {
		return new ResponseEntity<MeetingError>(
				new MeetingError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<MeetingError> handleMeetingBadRequestExcepton(Exception e) {
		return new ResponseEntity<MeetingError>(
				new MeetingError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now()),
				HttpStatus.BAD_REQUEST);
	}
}

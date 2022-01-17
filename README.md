# meetingAPI
### Spring Boot REST API for meetings manager.

The API caller is able to use these operations on API:

- Get all meetings           - GET   - /api/meetings
- Get meeting by name        - GET   - /meetings/search/findByName?name=value   
- Get meetings by description - GET   - /meetings/search/findByDescription?description=value
- Get meetings by responsible person - GET - /meetings/search/findByResponsiblePerson?person=value
- Get meetings by category - GET - /meetings/search/findByCategory?category=value
- Get meetings by type - GET - /meetings/search/findByType?type=value
- Get meetings starting from date - GET - /meetings/search/findStartingFromDate?date=yyyy-mm-dd
- Get meetings by date range - GET - /meetings/search/findByDateRange?start=yyyy-mm-dd&end=yyyy-mm-dd
- Get meetings by attendees number - GET - /meetings/search/findByMinAttendees?number=value
- Save new meeting - POST - /meetings
- Add new attendee - PUT - /meetings?meetingName=value&attendee=value&time=hh:mm
- Delete meeting - DELETE - /meetings?responsiblePerson=value&meetingName=value
- Remove attendee - DELETE - /attendees?meetingName=value&attendee=value

Link to [Postman test data samples](https://www.postman.com/avionics-physicist-21440496/workspace/rest-api/collection/18662089-a1f790cc-fe78-4f74-ba8c-60959fbee1ed)

## Installation

Download MeetingsManager folder or ```git pull https://github.com/hmurij/meetingAPI.git``` 
Import existing Maven project and run com.visma.internship.MeetingsManagerApplication.java or run jar file with ```java -jar meetingAPI.jar```, please note "data" folder should be in the same directory as jar file.

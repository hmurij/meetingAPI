# meetingAPI
### Spring Boot REST API for meetings manager.

The API caller is able to use these operations on API:

- Get all meetings           - GET   - /api/meetings
- Get meeting by name        - GET   - /meetings/search/findByName?name=value   
- Get meeting by description - GET   - /meetings/search/findByDescription?description=value
- Get meeting by resposnbile person - GET - /meetings/search/findByResponsiblePerson?person=value
- 
- Get task by id             - GET    - /api/tasks/{id}
- Save task                  - POST   - /api/meetings
- Update task                - PUT    - /api/tasks
- Delete task by id          - DELETE - /api/tasks/{id}

Link to [Postman test data samples](https://www.postman.com/avionics-physicist-21440496/workspace/rest-api/collection/18662089-a1f790cc-fe78-4f74-ba8c-60959fbee1ed)

## Installation

Download MeetingsManager folder or ```git pull https://github.com/hmurij/meetingAPI.git``` 
Import existing Maven project and run com.visma.internship.MeetingsManagerApplication.java or run jar file with java -jar meetingAPI.jar, please note "data" folder should be in the same directory as jar file.

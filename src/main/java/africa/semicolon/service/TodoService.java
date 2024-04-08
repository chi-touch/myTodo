package africa.semicolon.service;

import africa.semicolon.data.model.Todo;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.response.CreateTaskResponse;

public interface TodoService {

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    long getNumberOfTask();
    void delete(String title);
     Todo findByAuthor(String author);
     void deleteAll();

     void update(String title, String description);




}

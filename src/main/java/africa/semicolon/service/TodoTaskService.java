package africa.semicolon.service;

import africa.semicolon.data.model.TodoTask;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;
import africa.semicolon.dto.response.CompleteTaskResponse;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.dto.response.IncompleteTaskResponse;
import africa.semicolon.dto.response.UpdateTaskResponse;

import java.util.List;

public interface TodoTaskService {

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    long getNumberOfTask();
    void delete(String title);
     TodoTask findByAuthor(String author);
     void deleteAll();

     UpdateTaskResponse update(UpdateTaskRequest updateTaskRequest);

     List<TodoTask> findCompletedTasks();
     List<TodoTask> findInCompletedTasks();
     CompleteTaskResponse completeTask(CreateTaskRequest createTaskRequest);
     IncompleteTaskResponse incompleteTask(CreateTaskRequest incompleteTaskRequest);




}

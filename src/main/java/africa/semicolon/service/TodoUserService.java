package africa.semicolon.service;

import africa.semicolon.data.model.TodoTask;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;

import java.util.List;

public interface TodoUserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

   TodoUser findByUserName(String username);

    long count();

    void deleteAll();
    LoginResponse login(LoginRequest loginRequest);


    long getNumberOfUser();

    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);

    CompleteTaskResponse completeTask(CreateTaskRequest createTaskRequest);
    IncompleteTaskResponse incompleteTask(CreateTaskRequest incompleteTaskRequest);
    UpdateTaskResponse update(UpdateTaskRequest updateTaskRequest);
    List<TodoTask> findCompletedTasks();
    List<TodoTask> findInCompletedTasks();

    void deleteTask(String title);


    long getNumberOfTasks();

}

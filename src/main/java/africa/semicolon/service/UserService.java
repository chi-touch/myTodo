package africa.semicolon.service;

import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.UserRequest;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.dto.response.LoginResponse;
import africa.semicolon.dto.response.UserResponse;

public interface UserService {
    UserResponse register(UserRequest userRequest);

    TodoUser findByUserName(String username);

    long count();

    void deleteAll();
    LoginResponse login(LoginRequest loginRequest);
    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);
}

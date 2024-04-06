package africa.semicolon.service;

import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.response.LoginResponse;
import africa.semicolon.dto.response.RegisterUserResponse;

public interface TodoUserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    TodoUser findByUserName(String username);

    long count();

    void deleteAll();
    LoginResponse login(LoginRequest loginRequest);


    long getNumberOfUser();
}

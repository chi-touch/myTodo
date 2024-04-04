package africa.semicolon.service;

import africa.semicolon.data.model.TodoUser;
import africa.semicolon.data.repository.UserRepository;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.UserRequest;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.dto.response.LoginResponse;
import africa.semicolon.dto.response.UserResponse;
import africa.semicolon.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public UserResponse register(UserRequest userRequest) {
            TodoUser user = new TodoUser();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setUserName(userRequest.getUserName());
            user.setPassword(userRequest.getPassword());
            user.setLocked(false);
            userRepository.save(user);

            UserResponse userResponse = new UserResponse();
            userResponse.setMessage("Registration successful");
            return userResponse;

    }


    @Override
    public TodoUser findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        TodoUser foundUser = userRepository.findByUserName(loginRequest.getUsername());
        foundUser.setLocked(false);
        userRepository.save(foundUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Login successful");

        return loginResponse;

    }

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        return null;
    }

    private boolean validateUsernameAndPassword(String username, String password){
        return false;
    }




}

package africa.semicolon.service;

import africa.semicolon.data.model.TodoUser;
import africa.semicolon.data.repository.TodoUserRepository;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.response.LoginResponse;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.exceptions.InvalidInputEnteredException;
import africa.semicolon.exceptions.UserNameAlreadyExistException;
import africa.semicolon.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semicolon.util.Mapper.map;
import static africa.semicolon.util.Mapper.registerResponseMap;

@Service
public class TodoUserServiceImpl implements TodoUserService {
    @Autowired
    private TodoUserRepository todoUserRepository;

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        validate(registerUserRequest.getUserName());
        TodoUser todoUser = map(registerUserRequest);
        TodoUser savedUser = todoUserRepository.save(todoUser);
        RegisterUserResponse userResponse = new RegisterUserResponse();
        return registerResponseMap(savedUser);

    }



    private void validate(String username){
        boolean userExist = todoUserRepository.existsByUserName(username);
        if(userExist)throw new UserNameAlreadyExistException(String.format("%s already exists",username));


    }


    @Override
    public TodoUser findByUserName(String userName) {
       return todoUserRepository.findByUserName(userName);
//        return todoUserRepository.searchByUserName(userName);
    }

    @Override
    public long count() {
        return todoUserRepository.count();
    }

    @Override
    public void deleteAll() {
        todoUserRepository.deleteAll();

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        TodoUser foundUser = todoUserRepository.findByUserName(loginRequest.getUsername());

        if (isValidUsernameAndPassword(username, password)) {
            foundUser.setLocked(false);
            todoUserRepository.save(foundUser);
            LoginResponse response = new LoginResponse();
            response.setMessage("Login successful");
            return response;
        } else {
            throw new InvalidInputEnteredException("Invalid username or password");
        }
    }

    @Override
    public long getNumberOfUser() {
        return todoUserRepository.count();
    }

    private boolean isValidUsernameAndPassword(String username, String password) {
        List<TodoUser> userList = todoUserRepository.findAll();
        for (TodoUser user : userList) {
            if (user.getUserName() !=null && user.getPassword() !=null) {
                return true;
            }
        }
        return false;
    }


    private boolean validateUsernameAndPassword(String username, String password){
        return false;
    }




}

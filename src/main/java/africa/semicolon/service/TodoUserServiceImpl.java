package africa.semicolon.service;

import africa.semicolon.data.model.TodoTask;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.data.repository.TodoRepository;
import africa.semicolon.data.repository.TodoUserRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import africa.semicolon.exceptions.InvalidInputEnteredException;
import africa.semicolon.exceptions.InvalidUserNameException;
import africa.semicolon.exceptions.TitleAlreadyExistException;
import africa.semicolon.exceptions.UserNameAlreadyExistException;
import africa.semicolon.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static africa.semicolon.data.model.Status.COMPLETE;
import static africa.semicolon.data.model.Status.INCOMPLETE;
import static africa.semicolon.util.Mapper.map;
import static africa.semicolon.util.Mapper.registerResponseMap;

@Service
public class TodoUserServiceImpl implements TodoUserService {
    @Autowired
    private TodoUserRepository todoUserRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        validate(registerUserRequest.getUsername());

        TodoUser todoUser = map(registerUserRequest);
        TodoUser savedUser = todoUserRepository.save(todoUser);
        RegisterUserResponse userResponse = new RegisterUserResponse();
        return registerResponseMap(savedUser);

    }



    private void validate(String username){
       boolean userExist = todoUserRepository.existsByUsername(username);
        if(userExist)throw new UserNameAlreadyExistException(String.format("%s already exists",username));


    }


    @Override
    public TodoUser findByUserName(String username) {
        TodoUser user = todoUserRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new InvalidUserNameException("this user name does not exist");
    }


    private boolean ifUserExists(String username) {
        return todoUserRepository.existsByUsername(username);

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
        TodoUser foundUser = todoUserRepository.findByUsername(loginRequest.getUsername());
        if (foundUser == null) throw new IllegalStateException("User " + username + "not found");

//        isValidUsernameAndPassword(username, password)
        if (foundUser.getPassword().equals(password)) {
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

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        if (ifTitleExistAlready(createTaskRequest.getTitle())){
            throw new TitleAlreadyExistException("this title already exist");
        }
        TodoTask todo = Mapper.mapper(createTaskRequest);
        TodoTask savedTodo = todoRepository.save(todo);

        CreateTaskResponse createTaskResponse = new CreateTaskResponse();
        createTaskResponse.setAuthor(savedTodo.getAuthor());
        createTaskResponse.setMessage("created successful");
        return createTaskResponse;

    }

//    @Override
//    public CompleteTaskResponse completeTask(CreateTaskRequest createTaskRequest) {
//        TodoTask todo = Mapper.mapper(createTaskRequest);
//        TodoTask savedTodo = todoRepository.save(todo);
//
//        CompleteTaskResponse response = new CompleteTaskResponse();
//        response.setAuthor(savedTodo.getAuthor());
//        response.setMessage("Task completed successful");
//        return response;
//    }



//    @Override
//    public IncompleteTaskResponse incompleteTask(CreateTaskRequest createTaskRequest) {
//        TodoTask todo = Mapper.mapper(createTaskRequest);
//        TodoTask savedTodo = todoRepository.save(todo);
//
//        IncompleteTaskResponse response = new IncompleteTaskResponse();
//        response.setAuthor(savedTodo.getAuthor());
//        response.setMessage("incomplete task created");
//        return response;
//    }

    @Override
    public UpdateTaskResponse update(UpdateTaskRequest updateTaskRequest) {
        TodoTask todoTask = Mapper.updateMapper(updateTaskRequest);
        TodoTask savedTodo = todoRepository.save(todoTask);

        UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
        updateTaskResponse.setAuthor(savedTodo.getAuthor());
        updateTaskResponse.setMessage("updated successful");
        return updateTaskResponse;
    }

//    @Override
//    public List<TodoTask> findCompletedTasks() {
//        return todoUserRepository.findAll().stream().filter(todoTask -> todoTask.getStatus() == COMPLETED).collect(Collectors.toList());
//    }

    @Override
    public List<TodoTask> findCompletedTasks() {
        return todoRepository.findAll().stream().filter(todoTask -> todoTask.getStatus() == COMPLETE).collect(Collectors.toList());
    }

    @Override
    public List<TodoTask> findInCompletedTasks() {
        return todoRepository.findAll().stream().filter(todoTask -> todoTask.getStatus() == INCOMPLETE).collect(Collectors.toList());
    }

    @Override
    public void deleteTask(String title) {
        todoRepository.deleteByTitle(title);
    }

    @Override
    public long getNumberOfTasks() {
        return todoUserRepository.count();
    }


    private boolean ifTitleExistAlready(String author){return todoRepository.findByAuthor(author) != null; }


    private boolean isValidUsernameAndPassword(String username, String password) {
        List<TodoUser> userList = todoUserRepository.findAll();
        for (TodoUser user : userList) {
            if (user.getUsername() !=null && user.getPassword() !=null) {
                return true;
            }
        }
        return false;
    }


    private boolean validateUsernameAndPassword(String username, String password){
        return false;
    }




}

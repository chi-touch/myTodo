package africa.semicolon.service;

import africa.semicolon.data.model.TodoTaskList;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.data.repository.TodoTaskListRepository;
import africa.semicolon.data.repository.TodoUserRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import africa.semicolon.exceptions.*;
import africa.semicolon.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


import static africa.semicolon.util.Mapper.*;

@Service

public class TodoUserServiceImpl implements TodoUserService {
    @Autowired
    private  TodoTaskListService todoListService;
    @Autowired
   private  TodoTaskListRepository todoTaskListRepository;
    @Autowired
    private TodoUserRepository todoUserRepository;
    private TodoUser authenticatedUser ;


    @Override
    public RegisterUserResponse register(RegisterUserRequest registerRequest) {

        validate(registerRequest.getUsername());
        TodoUser todoUser = map(registerRequest);
        TodoUser savedUser = todoUserRepository.save(todoUser);
        return registerResponseMap(savedUser);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        TodoUser foundUser = todoUserRepository.findByUsername(loginRequest.getUsername());
        if (!isPasswordIncorrect(foundUser, loginRequest.getPassword())) {
            authenticatedUser = foundUser;
            return mapLoginResponse(foundUser);
        } else {
            throw new InvalidInputEnteredException("Invalid Username or password");
        }
    }
    @Override
    public LogoutUserResponse logout(LogoutRequest logoutRequest) {
        authenticatedUser = null;
        TodoUser foundUser = todoUserRepository.findByUsername(logoutRequest.getUsername());
        TodoUser savedUser = todoUserRepository.save(foundUser);
        return mapLogoutResponse(savedUser);

    }

    @Override
    public TodoUser startTask(StartTaskRequest startTaskRequest) {
        validateAuthentication();
        String author = startTaskRequest.getAuthor();
        String title = startTaskRequest.getTitle();
        TodoUser foundUser = todoUserRepository.findByUsername(author);
        TodoTaskList newTask = todoListService.startTaskWith(startTaskRequest);
        TodoTaskList taskToStart = findTaskByTitle(foundUser, title);
        return todoUserRepository.save(foundUser);

    }

    private TodoTaskList findTaskByTitle(TodoUser foundUser, String title) {
        TodoTaskList taskToStart = null;
        for (TodoTaskList todoList : foundUser.getTasks()) {
            if (todoList.getTitle().equals(title)) {
                taskToStart = todoList;
                break;
            }
        }
        return taskToStart;
    }

    @Override
    public List<TodoTaskList> viewAllPendingTasks(ViewAllPendingTaskRequest viewAllPendingTaskRequest) {
        validateAuthentication();
        TodoUser foundUser = todoUserRepository.findByUsername(viewAllPendingTaskRequest.getUsername());
        List<TodoTaskList> todoLists = new ArrayList<>();
        for (TodoTaskList todoList : foundUser.getTasks()) {
            if (todoList.getPriority().equals(viewAllPendingTaskRequest.getTaskPriority())) {
                todoLists.add(todoList);
            }
        }
        return todoLists;

    }

    @Override
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest) {
        validateAuthentication();
        String assignerUsername = assignTaskRequest.getAuthor();
        String assigneeUsername = assignTaskRequest.getUsername();
        TodoUser assigner = todoUserRepository.findByUsername(assignerUsername);
        TodoUser assignee = todoUserRepository.findByUsername(assigneeUsername);

        TodoTaskList assignedTask = todoListService.assignTask(assignTaskRequest);
        assignee.getTasks().add(assignedTask);
        todoUserRepository.save(assignee);

        return mapAssignTaskResponseWith(assignedTask);


    }
    private void validateAuthentication() {
        if (authenticatedUser == null)throw new InvalidInputEnteredException("no action can be performed until you log in");
    }


//    @Override
//    public TodoListResponse createTodolist(TodoTaskListRequest todolistRequest) {
//        validateAuthentication();
//        TodoUser foundUser = todoUserRepository.findByUsername(todolistRequest.getUsername());
//        if (taskExistsForUser(foundUser, todolistRequest.getTitle()))
//            throw new TaskExistsException("Task already exists");
//        TodoTaskList newTodoList = todoListService.createList(todolistRequest);
//        foundUser.getTasks().add(newTodoList);
//        todoUserRepository.save(foundUser);
//        return mapCreateTodoListResponseWith(newTodoList);
//    }

    private boolean taskExistsForUser(TodoUser foundUser, String title) {
        for (TodoTaskList todoList : foundUser.getTasks()) {
            if (todoList.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EditTodolistUserResponse editTodoListWith(EditTodolistRequest editTodolistRequest) {
        validateAuthentication();
        TodoUser author = todoUserRepository.findByUsername(editTodolistRequest.getAuthor());
        TodoTaskList userList = findUserTaskBy(editTodolistRequest.getListId(), author);
        userList.setTitle(editTodolistRequest.getTitle());
        userList.setPriority(editTodolistRequest.getTaskPriority());
        todoUserRepository.save(author);
        return todoListService.editList(editTodolistRequest, userList);
    }

    @Override
    public DeleteTodoListResponse deleteTodoTaskWith(DeleteListRequest deleteTodolistRequest) {
        validateAuthentication();
        TodoUser author = findUserBy(deleteTodolistRequest.getAuthor());
        TodoTaskList task = findUserTaskBy(deleteTodolistRequest.getListId(), author);
        return todoListService.deleteList(deleteTodolistRequest, task);

    }

    @Override
    public List<TodoListResponse> viewAllTodoList(TodoTaskListRequest todolistRequest) {
        validateAuthentication();
        TodoUser foundUser = findUserBy(todolistRequest.getUsername());
        List<TodoListResponse> todoListResponses = new ArrayList<>();
        for (TodoTaskList todoList : foundUser.getTasks()) {
            TodoListResponse todoListResponse = mapTodoListResponse(todoList);
            todoListResponses.add(todoListResponse);
        }
        return todoListResponses;
    }

    @Override
    public MarkTaskResponse markTaskStatus(MarkTaskRequest markTaskRequest) {
        validateAuthentication();
        TodoUser foundUser = findUserBy(markTaskRequest.getUsername());
        TodoTaskList newTodoList = todoListService.markTaskStatus(markTaskRequest);
        TodoTaskList todoList = findTodoListByTitle(markTaskRequest.getTitle(), foundUser);
        todoList.setPriority(markTaskRequest.getTaskPriority());
        TodoTaskList updatedTodoList = todoTaskListRepository.save(todoList);

        MarkTaskResponse markTaskResponse = mapMarkTaskResponse(updatedTodoList);
        todoUserRepository.save(foundUser);
        return markTaskResponse;
    }



    private TodoTaskList findTodoListByTitle(String title, TodoUser foundUser) {
        for (TodoTaskList todoList : foundUser.getTasks()) if (todoList.getTitle().equals(title)) return todoList;
        throw new TitleDoesNotExistException("Task title not found");
    }

    private TodoTaskList findUserTaskBy(String listId, TodoUser author) {
        for (TodoTaskList todoList : author.getTasks()) if (todoList.getId().equals(listId)) return todoList;
        throw new TaskNotFoundException("Task not found");
    }

    private boolean isPasswordIncorrect(TodoUser foundUser, String password) {
        return !foundUser.getPassword().equals(password);
    }

    private TodoUser findUserBy(String username) {
        if (username == null)
            throw new InvalidUserNameException("User cannot be null");

        TodoUser foundUser = todoUserRepository.findByUsername(username);
        if (foundUser == null)
            throw new InvalidUserNameException(String.format("%s not found", username));

        return foundUser;

    }

    private void validate(String username) {
        if (username == null || username.trim().isEmpty())
            throw new InvalidUserNameException("you can not register an empty input");

        boolean userExists = todoUserRepository.existsByUsername(username);
        if (userExists) throw new UserNameAlreadyExistException(String.format("%s username already exists", username));

    }

//    @Autowired
//    private TodoUserRepository todoUserRepository;
//
//    @Autowired
//    private TodoTaskListService todoTaskService;
//
//    @Override
//    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
//        validate(registerUserRequest.getUsername());
//
//        TodoUser todoUser = map(registerUserRequest);
//        TodoUser savedUser = todoUserRepository.save(todoUser);
//        RegisterUserResponse userResponse = new RegisterUserResponse();
//        return registerResponseMap(savedUser);
//
//    }
//
//    private void validate(String username){
//       boolean userExist = todoUserRepository.existsByUsername(username);
//        if(userExist)throw new UserNameAlreadyExistException(String.format("%s already exists",username));
//
//
//    }
//
//
    @Override
    public TodoUser findByUserName(String username) {
        TodoUser user = todoUserRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new InvalidUserNameException("this user name does not exist");
    }
//
//
//    private boolean ifUserExists(String username) {
//        return todoUserRepository.existsByUsername(username);
//
//    }
//
    @Override
    public long count() {
        return todoUserRepository.count();
    }
//
    @Override
    public void deleteAll() {
        todoTaskListRepository.deleteAll();
    }
//
//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//        TodoUser foundUser = todoUserRepository.findByUsername(loginRequest.getUsername());
//        if (foundUser == null) throw new IllegalStateException("User " + username + "not found");
//
//        if (foundUser.getPassword().equals(password)) {
//            foundUser.setLocked(false);
//            todoUserRepository.save(foundUser);
//            LoginResponse response = new LoginResponse();
//            response.setMessage("Login successful");
//            return response;
//        } else {
//            throw new InvalidInputEnteredException("Invalid username or password");
//        }
//    }

    @Override
    public long getNumberOfUser() {
        return todoUserRepository.count();
    }

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        TodoUser user = todoUserRepository.findByUsername(createTaskRequest.getUsername());
        CreateTaskResponse response = todoListService.createList(createTaskRequest);
        TodoTaskList todoTask = todoListService.findById(response.getTaskId());
        List<TodoTaskList> userTasks = user.getTasks();
        userTasks.add(todoTask);
        user.setTasks(userTasks);
        todoUserRepository.save(user);
//        if (ifTitleExistAlready(createTaskRequest.getTitle())){
//            throw new TitleAlreadyExistException("this title already exist");
//        }

        CreateTaskResponse createTaskResponse = new CreateTaskResponse();
        createTaskResponse.setAuthor(user.getId());
        createTaskResponse.setMessage("created successful");
        return createTaskResponse;

    }
//
//
//
////    @Override
////    public CompleteTaskResponse completeTask(CreateTaskRequest createTaskRequest) {
////        TodoTask todo = Mapper.mapper(createTaskRequest);
////        TodoTask savedTodo = todoRepository.save(todo);
////
////        CompleteTaskResponse response = new CompleteTaskResponse();
////        response.setAuthor(savedTodo.getAuthor());
////        response.setMessage("Task completed successful");
////        return response;
////    }
//
//
//
////    @Override
////    public IncompleteTaskResponse incompleteTask(CreateTaskRequest createTaskRequest) {
////        TodoTask todo = Mapper.mapper(createTaskRequest);
////        TodoTask savedTodo = todoRepository.save(todo);
////
////        IncompleteTaskResponse response = new IncompleteTaskResponse();
////        response.setAuthor(savedTodo.getAuthor());
////        response.setMessage("incomplete task created");
////        return response;
////    }

    @Override
    public UpdateTaskResponse update(UpdateTaskRequest updateTaskRequest) {
        TodoTaskList todoTask = Mapper.updateMapper(updateTaskRequest);
        TodoTaskList savedTodo = todoTaskListRepository.save(todoTask);

        UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
        updateTaskResponse.setAuthor(savedTodo.getAuthor());
        updateTaskResponse.setMessage("updated successful");
        return updateTaskResponse;
    }

    @Override
    public void deleteTask(String title) {
     todoTaskListRepository.deleteByTitle(title);
    }

    @Override
    public long getNumberOfTasks() {
        return todoUserRepository.count();
    }


  //  private boolean ifTitleExistAlready(String title){return todoUserRepository.findByTitle(title) != null;}
//
//
//    private boolean isValidUsernameAndPassword(String username, String password) {
//        List<TodoUser> userList = todoUserRepository.findAll();
//        for (TodoUser user : userList) {
//            if (user.getUsername() !=null && user.getPassword() !=null) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//



}

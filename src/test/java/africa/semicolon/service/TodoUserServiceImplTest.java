package africa.semicolon.service;

import africa.semicolon.data.model.TaskPriority;
import africa.semicolon.data.model.Tasks;
import africa.semicolon.data.repository.TodoRepository;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.dto.response.LoginResponse;
import africa.semicolon.dto.response.UpdateTaskResponse;
import africa.semicolon.exceptions.InvalidInputEnteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TodoUserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private Users users;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private TodolistRequest todolistRequest;
    private EditTodolistRequest editTodolistRequest;
    private DeleteTodolistRequest deleteTodolistRequest;
    private MarkTaskRequest markTaskRequest;
    private  LogoutRequest logoutRequest;
    private StartTaskRequest startTaskRequest;
    private ViewAllPendingTaskRequest viewAllPendingTaskRequest;
    private AssignTaskRequest assignTaskRequest;

    @BeforeEach
    public void setUp(){
        users.deleteAll();

        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title");


        editTodolistRequest = new EditTodolistRequest();
        editTodolistRequest.setAuthor("username");
        editTodolistRequest.setStatus(TaskStatus.PENDING);
        editTodolistRequest.setTitle("title");

        deleteTodolistRequest = new DeleteTodolistRequest();
        deleteTodolistRequest.setAuthor("username");

        markTaskRequest = new MarkTaskRequest();
        markTaskRequest.setUsername("username");
        markTaskRequest.setTitle("title");


        viewAllPendingTaskRequest = new ViewAllPendingTaskRequest();
        viewAllPendingTaskRequest.setUsername("username");


        logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");

        startTaskRequest = new StartTaskRequest();
        startTaskRequest.setUsername("username");
        startTaskRequest.setTitle("title");

        assignTaskRequest = new AssignTaskRequest();
        assignTaskRequest.setUsername("username2");
        assignTaskRequest.setTitle("titles");
        assignTaskRequest.setAuthor("username1");




    }

    @Test
    public void testUserCanRegister(){
        users.deleteAll();
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));


    }


    @Test
    public void testUserCannotRegisterTwice(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        try {
            userService.register(registerRequest);
        } catch (UserExistsException e) {
            assertThat(e.getMessage(), containsString("username already exists"));
        }
        assertThat(users.count(), is(1L));

    }

    @Test
    public void testUserCanLoginWithCorrect_Password(){
        users.deleteAll();
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        var loginResponse = userService.login(loginRequest);
        assertThat(loginResponse.getId(), notNullValue());


    }
    @Test
    public void loginWrongUser_throwsExceptionTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existing_username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Non existent username");
        try {
            userService.login(loginRequest);
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage(), containsString("username not found"));
        }

    }

    @Test
    public void loginWithIncorrectPassword_throwsExceptionTest(){
        userService.register(registerRequest);
        loginRequest.setPassword("incorrectPassword");
        try {
            userService.login(loginRequest);
        } catch (InvalidUsernameOrPassword e) {
            assertThat(e.getMessage(), containsString("Invalid Username or password"));
        }
    }

    @Test
    public void testUserCanAdd_firstTodoList(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(0));
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
        // todolistRequest.setStatus(TaskStatus.PENDING);
        var todolistResponse = userService.createTodolist(todolistRequest);
        checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(1));
        assertThat(todolistResponse.getListId(), notNullValue());

    }

    @Test
    public void testUserCanUpdateTodoList(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title");
        // todolistRequest.setStatus(TaskStatus.PENDING);
        userService.createTodolist(todolistRequest);

        var foundUser = users.findByUsername(registerRequest.getUsername().toLowerCase());
        var savedTodolist = foundUser.getTodoList().getFirst();
        assertThat(foundUser.getTodoList().size(), is(1));
        editTodolistRequest.setStatus(TaskStatus.SUCCESS);
        editTodolistRequest.setListId(savedTodolist.getId());
        var editTodolistResponse = userService.editTodoListWith(editTodolistRequest);
        foundUser = users.findByUsername(registerRequest.getUsername().toLowerCase());
        savedTodolist = foundUser.getTodoList().getFirst();
        assertThat(foundUser.getTodoList().size(), is(1));
        assertThat(editTodolistResponse.getListId(), notNullValue());

    }

    @Test
    public void testUserCanDeleteTodoList_ListIsZero(){
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        userService.createTodolist(todolistRequest);
        var foundUser = users.findByUsername(registerRequest.getUsername().toLowerCase());
        var savedPost = foundUser.getTodoList().getFirst();
        assertThat(foundUser.getTodoList().size(), is(1));
        deleteTodolistRequest.setListId(savedPost.getId());

        var deleteTodoListResponse = userService.deleteTodoTaskWith(deleteTodolistRequest);
        assertThat(deleteTodoListResponse.getListId(), notNullValue());


    }


    @Test
    public void testToViewAllPendingTask(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);

        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title");
        userService.createTodolist(todolistRequest);
        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username");
        todolistRequest.setTitle("title1");
        userService.createTodolist(todolistRequest);
        viewAllPendingTaskRequest = new ViewAllPendingTaskRequest();
        viewAllPendingTaskRequest.setUsername(registerRequest.getUsername());
        List<TodoList> pendingTasks = userService.viewAllPendingTasks(viewAllPendingTaskRequest);
        assertEquals(2, pendingTasks.size());
        for (TodoList task : pendingTasks) {
            assertEquals(TaskStatus.PENDING, task.getStatus());
            assertTrue(task.getTitle().startsWith("title"));
        }
    }

    @Test
    public void testUserCan_ViewAllTodoList(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(0));

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
        userService.createTodolist(todolistRequest);
        todolistRequest.setTitle("title1");
        userService.createTodolist(todolistRequest);

        var checkName = users.findByUsername(registerRequest.getUsername());
        assertThat(checkName.getTodoList().size(), is(2));
        var todolistResponse = userService.viewAllTodoList(todolistRequest);
        assertThat(todolistResponse.size(), is(2));


    }

    @Test
    public void testUserCan_StartTask(){
        userService.register(registerRequest);
        userService.login(loginRequest);

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
        userService.createTodolist(todolistRequest);

        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(1));

        var savedTodoList = checkUser.getTodoList().getFirst();

        startTaskRequest = new StartTaskRequest();
        startTaskRequest.setUsername(registerRequest.getUsername());
        startTaskRequest.setTitle("title");
        userService.startTask(startTaskRequest);
        var updateUser = users.findByUsername(registerRequest.getUsername());
        assertThat(updateUser.getTodoList().size(), is(1));

        var updatedTask = updateUser.getTodoList().getFirst();
        assertThat(updatedTask.getStatus(), is(TaskStatus.IN_PROGRESS));

    }

    @Test
    public void testMarkTaskStatus(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        var checkUser = users.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getTodoList().size(), is(0));

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername(registerRequest.getUsername());
        todolistRequest.setTitle("title");
        //todolistRequest.setStatus(TaskStatus.PENDING);
        userService.createTodolist(todolistRequest);
        var updateUserBeforeMarking = users.findByUsername(registerRequest.getUsername());
        assertThat(updateUserBeforeMarking.getTodoList().size(), is(1));

        markTaskRequest = new MarkTaskRequest();
        markTaskRequest.setUsername(registerRequest.getUsername());
        markTaskRequest.setTitle("title");
        userService.markTaskStatus(markTaskRequest);
        var updateUserAfterMarking = users.findByUsername(registerRequest.getUsername());
        assertThat(updateUserAfterMarking.getTodoList().size(), is(1));

        TodoList updatedTodoList = updateUserAfterMarking.getTodoList().getFirst();
        assertThat(updatedTodoList.getTitle(), is("title"));
        assertThat(updatedTodoList.getStatus(), is(TaskStatus.SUCCESS));

    }

    @Test
    public void testUserCan_AssignTask_to_A_User() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username1");
        registerRequest.setPassword("password");
        userService.register(registerRequest);

        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Banjo");
        registerRequest.setUsername("username2");
        registerRequest.setPassword("pass");
        userService.register(registerRequest);

        assertThat(users.count(), is(2L));

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        todolistRequest = new TodolistRequest();
        todolistRequest.setUsername("username1");
        todolistRequest.setTitle("title");
        userService.createTodolist(todolistRequest);

        assignTaskRequest = new AssignTaskRequest();
        assignTaskRequest.setUsername("username2");
        assignTaskRequest.setTitle("title");
        assignTaskRequest.setAuthor("username1");
        userService.assignTask(assignTaskRequest);

        User user2 = users.findByUsername("username2");
        assertThat(user2, notNullValue());
        List<TodoList> todoListsUser2 = user2.getTodoList();
        assertThat(todoListsUser2.size(), is(1));

        TodoList assignedTask = todoListsUser2.getFirst();
        assertThat(assignedTask.getTitle(), is("title"));
        assertThat(assignedTask.getStatus(), is(TaskStatus.PENDING));
        assertThat(assignedTask.getAuthor(), is("username1"));

    }

    @Test
    public void testUserCanLogout() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(users.count(), is(1L));
        logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        var loginResponse = userService.logout(logoutRequest);
        assertThat(loginResponse.getUsername(), notNullValue());
    }

//    RegisterUserRequest registerUserRequest;
//
//
//    @Autowired
//    TodoUserService userService;
//
//    @Autowired
//    TodoTaskListService todoService;
//    @Autowired
//    TodoRepository todoRepository;
//
//    @BeforeEach
//    public void setUp(){
//        todoRepository.deleteAll();
//        userService.deleteAll();
////       registerUserRequest = new RegisterUserRequest();
////       registerUserRequest.setFirstName("chi");
////       registerUserRequest.setLastName("dav");
////       registerUserRequest.setUsername("chichi");
////       registerUserRequest.setPassword("1234");
//    }
//
//    @Test
//    public void testToRegisterUser(){
//
//        registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setUsername("amen");
//        registerUserRequest.setFirstName("chil");
//        registerUserRequest.setLastName("dave");
//        registerUserRequest.setPassword("1237");
//        userService.register(registerUserRequest);
//        assertEquals(1,userService.getNumberOfUser());
//
//    }
//
//    @Test
//
//    public void testToLogin(){
//        registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setUsername("chichiu");
//        registerUserRequest.setFirstName("chio");
//        registerUserRequest.setLastName("davi");
//        registerUserRequest.setPassword("1239");
//        userService.register(registerUserRequest);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest = new LoginRequest();
//        loginRequest.setUsername("chichiu");
//        loginRequest.setPassword("1239");
//        userService.login(loginRequest);
//        loginRequest.setUsername("chichiu");
//        loginRequest.setPassword("1239");
//
//        LoginResponse loginResponse = userService.login(loginRequest);
//        assertThat(loginResponse).isNotNull();
//        assertThat(loginResponse.getMessage()).isNotNull();
//
//
//    }
//
//    @Test
//    public void testToFindUser(){
//        registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setUsername("chih");
//        registerUserRequest.setFirstName("chiom");
//        registerUserRequest.setLastName("daviv");
//        registerUserRequest.setPassword("1232");
//        userService.register(registerUserRequest);
//        assertEquals("chih",userService.findByUserName("chih").getUsername());
//
//    }
//
//    @Test
//    public void testToUpdate(){
//        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
//        createTaskRequest.setBody("today's job");
//        createTaskRequest.setTitle("how my demo");
//        createTaskRequest.setAuthor("neddy");
//        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
////        List<Tasks> taskList = new ArrayList<>();
////        Tasks task = new Tasks();
//
//        //taskList.add(task);
//        // taskList.add(task1);
//        //createTaskRequest.setTaskList(taskList);
//        userService.createTask(createTaskRequest);
//
//
//        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
//        updateTaskRequest.setBody("journey so far");
//        updateTaskRequest.setTitle("how my demo");
//        updateTaskRequest.setAuthor("neddy");
//        //List<Tasks> taskList = new ArrayList<>();
//        //Tasks task = new Tasks();
//        //Tasks task1 = new Tasks();
//        //taskList.add(task);
//        //taskList.add(task1);
////        updateTaskRequest.getBody();
////        updateTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
//
//        UpdateTaskResponse updateTaskResponse =todoService.update(updateTaskRequest);
//        assertThat(updateTaskResponse.getMessage()).isNotNull();
//    }
//
//    @Test
//    public void testToDeleteAll(){
//
//        registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setUsername("chichi");
//        registerUserRequest.setFirstName("chi");
//        registerUserRequest.setLastName("dav");
//        registerUserRequest.setPassword("1234");
//        userService.register(registerUserRequest);
//
//        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
//        registerUserRequest1.setPassword("2222");
//        registerUserRequest1.setUsername("amil");
//        registerUserRequest1.setFirstName("ama");
//        registerUserRequest1.setLastName("john");
//        userService.register(registerUserRequest1);
//        userService.deleteAll();
//        assertThat(userService.getNumberOfUser());
//
//    }
//
//    @Test
//    public void testToLoginWithWrongPassword(){
//
//        registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setUsername("chichi");
//        registerUserRequest.setFirstName("chio");
//        registerUserRequest.setLastName("davi");
//        registerUserRequest.setPassword("1239");
//        userService.register(registerUserRequest);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest = new LoginRequest();
//        loginRequest.setUsername("chichi");
//        loginRequest.setPassword("1236");
//
//        LoginRequest finalLoginRequest = loginRequest;
//        assertThrows(InvalidInputEnteredException.class,() -> userService.login(finalLoginRequest));
//    }
//
//    @Test
//    public void testCreatTodoTask(){
//        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
//        createTaskRequest.setBody("today's job");
//        createTaskRequest.setTitle("how my demo");
//        createTaskRequest.setAuthor("neddy");
//       // createTaskRequest.setStatus(COMPLETE);
//        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
//        List<Tasks> taskList = new ArrayList<>();
//        Tasks task = new Tasks();
//        //Tasks task1 = new Tasks();
//        taskList.add(task);
//       // taskList.add(task1);
//        createTaskRequest.setTaskList(taskList);
//
//
//
//        CreateTaskResponse createTaskResponse =  todoService.createTask(createTaskRequest);
//
//        assertThat(createTaskResponse.getMessage()).isNotNull();
//        assertEquals(1,todoService.getNumberOfTask());
//    }

}
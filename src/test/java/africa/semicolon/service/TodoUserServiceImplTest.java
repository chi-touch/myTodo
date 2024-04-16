package africa.semicolon.service;

import africa.semicolon.data.model.TaskPriority;
import africa.semicolon.data.model.Tasks;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.dto.response.LoginResponse;
import africa.semicolon.dto.response.UpdateTaskResponse;
import africa.semicolon.exceptions.InvalidInputEnteredException;
import lombok.var;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.data.model.Status.COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TodoUserServiceImplTest {
    RegisterUserRequest registerUserRequest;


    @Autowired
    TodoUserService userService;

    @Autowired
    TodoTaskService todoService;

    @BeforeEach
    public void setUp(){
        userService.deleteAll();
//       registerUserRequest = new RegisterUserRequest();
//       registerUserRequest.setFirstName("chi");
//       registerUserRequest.setLastName("dav");
//       registerUserRequest.setUsername("chichi");
//       registerUserRequest.setPassword("1234");
    }

    @Test
    public void testToRegisterUser(){

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("amen");
        registerUserRequest.setFirstName("chil");
        registerUserRequest.setLastName("dave");
        registerUserRequest.setPassword("1237");
        userService.register(registerUserRequest);
        assertEquals(1,userService.getNumberOfUser());

    }

    @Test

    public void testToLogin(){
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chichiu");
        registerUserRequest.setFirstName("chio");
        registerUserRequest.setLastName("davi");
        registerUserRequest.setPassword("1239");
        userService.register(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest = new LoginRequest();
        loginRequest.setUsername("chichiu");
        loginRequest.setPassword("1239");
        userService.login(loginRequest);
        loginRequest.setUsername("chichiu");
        loginRequest.setPassword("1239");

        LoginResponse loginResponse = userService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isNotNull();


    }

    @Test
    public void testToFindUser(){
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chih");
        registerUserRequest.setFirstName("chiom");
        registerUserRequest.setLastName("daviv");
        registerUserRequest.setPassword("1232");
        userService.register(registerUserRequest);
        assertEquals("chih",userService.findByUserName("chih").getUsername());

    }

    @Test
    public void testToUpdate(){
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setBody("journey so far");
        updateTaskRequest.setTitle("Journey");
        updateTaskRequest.setAuthor("neddy");
        List<Tasks> taskList = new ArrayList<>();
        Tasks task = new Tasks();
        Tasks task1 = new Tasks();
        taskList.add(task);
        taskList.add(task1);
        updateTaskRequest.setTaskList(taskList);
        updateTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));

        UpdateTaskResponse updateTaskResponse =todoService.update(updateTaskRequest);
        assertThat(updateTaskResponse.getMessage()).isNotNull();
    }

    @Test
    public void testToDeleteAll(){

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chichi");
        registerUserRequest.setFirstName("chi");
        registerUserRequest.setLastName("dav");
        registerUserRequest.setPassword("1234");
        userService.register(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setPassword("2222");
        registerUserRequest1.setUsername("amil");
        registerUserRequest1.setFirstName("ama");
        registerUserRequest1.setLastName("john");
        userService.register(registerUserRequest1);
        userService.deleteAll();
        assertThat(userService.getNumberOfUser());

    }

    @Test
    public void testToLoginWithWrongPassword(){

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chichi");
        registerUserRequest.setFirstName("chio");
        registerUserRequest.setLastName("davi");
        registerUserRequest.setPassword("1239");
        userService.register(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest = new LoginRequest();
        loginRequest.setUsername("chichi");
        loginRequest.setPassword("1236");

        LoginRequest finalLoginRequest = loginRequest;
        assertThrows(InvalidInputEnteredException.class,() -> userService.login(finalLoginRequest));
    }

    @Test
    public void testCreatTodoTask(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my demo went");
        createTaskRequest.setAuthor("neddy");
       // createTaskRequest.setStatus(COMPLETE);
        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
        List<Tasks> taskList = new ArrayList<>();
        Tasks task = new Tasks();
        //Tasks task1 = new Tasks();
        taskList.add(task);
       // taskList.add(task1);
        createTaskRequest.setTaskList(taskList);



        CreateTaskResponse createTaskResponse =  todoService.createTask(createTaskRequest);

        assertThat(createTaskResponse.getMessage()).isNotNull();
        assertEquals(1,todoService.getNumberOfTask());
    }

}
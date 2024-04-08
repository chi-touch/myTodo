package africa.semicolon.service;

import africa.semicolon.data.model.Tasks;
import africa.semicolon.data.model.Todo;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.exceptions.InvalidTitleException;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

public class TodoServiceImplTest {

    CreateTaskRequest createTaskRequest;

    @Autowired
    TodoService todoService;

    @BeforeEach
    public void setUp(){
        createTaskRequest = new CreateTaskRequest();
    }


    @Test
    public void testToCreateTask(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        List<Tasks> taskList = new ArrayList<>();
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));

        CreateTaskResponse createTaskResponse =  todoService.createTask(createTaskRequest);

        assertThat(todoService.getNumberOfTask(),is(1L));
        assertThat(createTaskResponse.getMessage()).isNotNull();

    }
    @Test
    public void testToDeleteByTitle(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("chichi");
        List<Tasks> taskList = new ArrayList<>();
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));

        todoService.delete("how my day went");
        assertThat(todoService.getNumberOfTask(), is(0L));
    }

    @Test
    public void testToFindByAuthor(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("neddy");
        List<Tasks> taskList = new ArrayList<>();
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        var response = todoService.createTask(createTaskRequest);

//        Todo expected = new Todo();
//        expected.setAuthor("chichi");
        Todo actual =todoService.findByAuthor(createTaskRequest.getAuthor());
        assertEquals(response.getAuthor(),actual.getAuthor());
    }
    @Test
    public void testToDeleteAll(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("neddy");
        List<Tasks> taskList = new ArrayList<>();
        Tasks task = new Tasks();
        Tasks task1 = new Tasks();
        taskList.add(task);
        taskList.add(task1);
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        var response = todoService.createTask(createTaskRequest);

        todoService.deleteAll();
        assertThat(todoService.getNumberOfTask(), is(0L));
    }

//    @Test
//    public void testToThrowException(){
//        assertThrows(InvalidTitleException.class,()-> todoService.)
//    }





//    LoginResponse loginResponse = userService.login(loginRequest);
//    assertThat(loginResponse).isNotNull();
//    assertThat(loginResponse.getMessage()).isNotNull();

}
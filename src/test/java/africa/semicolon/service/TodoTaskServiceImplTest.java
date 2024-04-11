package africa.semicolon.service;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.TaskPriority;
import africa.semicolon.data.model.Tasks;
import africa.semicolon.data.model.TodoTask;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;
import africa.semicolon.dto.response.CompleteTaskResponse;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.dto.response.UpdateTaskResponse;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.data.model.Status.COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

public class TodoTaskServiceImplTest {

//    CreateTaskRequest createTaskRequest;

    @Autowired
    TodoTaskService todoService;

    @BeforeEach
    public void setUp(){
        todoService.deleteAll();
    }

//    @BeforeEach
//    public void setUp(){
//        createTaskRequest = new CreateTaskRequest();
//    }


    @Test
    public void testToCreateTask(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        List<Tasks> taskList = new ArrayList<>();
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
        createTaskRequest.setStatus(COMPLETE);

        CreateTaskResponse createTaskResponse =  todoService.createTask(createTaskRequest);


        assertThat(createTaskResponse.getMessage()).isNotNull();
        assertThat(todoService.getNumberOfTask(),is(1L));

    }
    @Test
    public void testToDeleteByTitle(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("chichi");
        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
        createTaskRequest.setStatus(COMPLETE);
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
        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
        createTaskRequest.setStatus(COMPLETE);
        List<Tasks> taskList = new ArrayList<>();
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        var response = todoService.createTask(createTaskRequest);

//        Todo expected = new Todo();
//        expected.setAuthor("chichi");
        TodoTask actual =todoService.findByAuthor(createTaskRequest.getAuthor());
        assertEquals(response.getAuthor(),actual.getAuthor());
    }
    @Test
    public void testToDeleteAll(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("neddy");
        createTaskRequest.setStatus(COMPLETE);
        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
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

    @Test
    public void testToUpdateTask(){
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setBody("journey so far");
        updateTaskRequest.setAuthor("neddy");
        List<Tasks> taskList = new ArrayList<>();
        Tasks task = new Tasks();
        Tasks task1 = new Tasks();
        taskList.add(task);
        taskList.add(task1);
        updateTaskRequest.setTaskList(taskList);
        updateTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));

        UpdateTaskResponse updateTaskResponse =todoService.update(updateTaskRequest);
        assertThat(todoService.getNumberOfUpdatedTasks(),is(1L));
        assertThat(updateTaskResponse.getMessage()).isNotNull();

    }

    @Test
    public void testToFindListOfTasks(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("neddy");
        createTaskRequest.setPriority(TaskPriority.IMPORTANT);
        createTaskRequest.setStatus(COMPLETE);
        List<Tasks> taskList = new ArrayList<>();
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        todoService.createTask(createTaskRequest);

        List<TodoTask> actual = todoService.findCompletedTasks();

        CompleteTaskResponse completeTaskResponse =
        assertThat(actual.size(),is(1));
    }





//    LoginResponse loginResponse = userService.login(loginRequest);
//    assertThat(loginResponse).isNotNull();
//    assertThat(loginResponse.getMessage()).isNotNull();

}
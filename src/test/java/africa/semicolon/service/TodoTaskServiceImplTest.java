package africa.semicolon.service;


import africa.semicolon.data.model.MyStatus;
import africa.semicolon.data.model.Tasks;
import africa.semicolon.data.model.TodoTaskList;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.TodoTaskListRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;

import africa.semicolon.dto.response.CreateTaskResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import africa.semicolon.exceptions.InvalidTitleException;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TodoTaskServiceImplTest {


    @Autowired
    TodoTaskListService todoService;

    @BeforeEach
    public void setUp(){
        todoService.deleteAll();
    }

    @Test
    public void testToCreateTask(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();

        TodoTaskListRequest todoTaskListRequest = new TodoTaskListRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        List<TodoTaskList> myTaskList = new ArrayList<>();
        createTaskRequest.setTaskList(myTaskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        createTaskRequest.setPriority(MyStatus.PENDING);
       // createTaskRequest.setStatus(COMPLETE);

        CreateTaskResponse createTaskResponse = todoService.createList(createTaskRequest);




        assertThat(createTaskResponse.getMessage()).isNotNull();
        assertThat(todoService.getNumberOfTask(),is(1L));
    }
    @Test
    public void testToDeleteByTitle(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("chichi");
        createTaskRequest.setPriority(MyStatus.IMPORTANT);

        List<TodoTaskList> taskList = new ArrayList<>();
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
        createTaskRequest.setPriority(MyStatus.IMPORTANT);

        List<TodoTaskList> taskList = new ArrayList<>();
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        var response = todoService.createList(createTaskRequest);

        TodoTaskList actual = todoService.findByAuthor(createTaskRequest.getAuthor());
        assertEquals(response.getAuthor(),actual.getAuthor());
    }
    @Test
    public void testToDeleteAll(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("neddy");

        //createTaskRequest.setPriority(MyStatus.IMPORTANT);
        List<TodoTaskList> taskList = new ArrayList<>();
        TodoTaskList task = new TodoTaskList();
        TodoTaskList task1 = new TodoTaskList();
        taskList.add(task);
        taskList.add(task1);
        createTaskRequest.setTaskList(taskList);
        createTaskRequest.setLocalDate(String.valueOf(LocalDate.now()));
        var response = todoService.createList(createTaskRequest);

        todoService.deleteAll();
        assertThat(todoService.getNumberOfTask(), is(0L));
    }
//
//    @Test
//    public void testToThrowException(){
//        assertThrows(InvalidTitleException.class,()-> todoService);
//    }

    @Test
    public void testToUpdateTask(){
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setBody("journey so far");
        updateTaskRequest.setAuthor("neddy");
        List<TodoTaskList> taskList = new ArrayList<>();
        TodoTaskList task = new TodoTaskList();
        TodoTaskList task1 = new TodoTaskList();
        taskList.add(task);
        taskList.add(task1);
        updateTaskRequest.setTodoTaskListList(taskList);


        //UpdateTaskResponse updateTaskResponse =todoService.update(updateTaskRequest);
        //assertThat(todoService.getNumberOfUpdatedTasks(),is(1L));
       // assertThat(updateTaskResponse.getMessage()).isNotNull();

    }

    @Test
    public void testToFindListOfTasks() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("neddy");
       // createTaskRequest.setPriority(MyStatus.IMPORTANT);
        //todoService.createTask(createTaskRequest);

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setBody("journey so far");
        updateTaskRequest.setAuthor("neddy");
//        List<Tasks> taskList = new ArrayList<>();
//        Tasks task = new Tasks();
//        Tasks task1 = new Tasks();
//        taskList.add(task);
//        taskList.add(task1);
       // updateTaskRequest.setTaskList(taskList);
        //updateTaskRequest.setStatus(COMPLETE);
        //UpdateTaskResponse updateTaskResponse =todoService.update(updateTaskRequest);

       // assertThat(todoService.findCompletedTasks().size(),is(1));
    }



    @Test
    public void testForCompletedTask(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
        createTaskRequest.setAuthor("neddy");
//        createTaskRequest.setPriority(MyStatus.IMPORTANT);
//
//
//        todoService.completeTask(createTaskRequest);
//
//        assertThat(todoService.getNumberOfCompletedTasks(),is(1L));

    }

    @Test
    public void testToFindIncompleteTasks(){
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setBody("today's job");
        createTaskRequest.setTitle("how my day went");
//        createTaskRequest.setPriority(MyStatus.LESS_IMPORTANT);
//        todoService.createTask(createTaskRequest);
//        List<TodoTaskList> tasks = todoService.findInCompletedTasks();
        //assertEquals(1, tasks.size());
    }





}
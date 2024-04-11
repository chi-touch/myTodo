package africa.semicolon.util;

import africa.semicolon.data.model.TodoTask;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;
import africa.semicolon.dto.response.RegisterUserResponse;

import java.time.format.DateTimeFormatter;

public class Mapper {
    public static TodoTask mapper(CreateTaskRequest createTaskRequest){
        TodoTask todo = new TodoTask();
        todo.setBody(createTaskRequest.getBody());
        todo.setTitle(createTaskRequest.getTitle());
        todo.setTaskList(createTaskRequest.getTaskList());
        todo.setAuthor(createTaskRequest.getAuthor());
        todo.setLocalDate(createTaskRequest.getLocalDate());
        todo.setPriority(createTaskRequest.getPriority());
        todo.setStatus(createTaskRequest.getStatus());
        return todo;
    }

    public static TodoUser map(RegisterUserRequest registerUserRequest){
        TodoUser user = new TodoUser();
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setLocked(false);
        return user;
    }
    public static RegisterUserResponse registerResponseMap(TodoUser user){
        RegisterUserResponse response = new RegisterUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setDateRegistered(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a").format(user.getDateRegistered()));
        return response;
    }

    public static TodoTask updateMapper(UpdateTaskRequest updateTaskRequest){
        TodoTask todoTask = new TodoTask();
        todoTask.setBody(updateTaskRequest.getBody());
        todoTask.setTaskList(updateTaskRequest.getTaskList());
        todoTask.setAuthor(updateTaskRequest.getAuthor());
        todoTask.setLocalDate(updateTaskRequest.getLocalDate());
        todoTask.setPriority(updateTaskRequest.getPriority());
        todoTask.setStatus(updateTaskRequest.getStatus());
        return todoTask;
    }
//    public static TodoTask completeMapper(CreateTaskRequest createTaskRequest){
//        TodoTask todo = new TodoTask();
//        todo.setBody(createTaskRequest.getBody());
//        todo.setTitle(createTaskRequest.getTitle());
//        todo.setTaskList(createTaskRequest.getTaskList());
//        todo.setAuthor(createTaskRequest.getAuthor());
//        todo.setLocalDate(createTaskRequest.getLocalDate());
//        todo.setPriority(createTaskRequest.getPriority());
//        todo.setStatus(createTaskRequest.getStatus());
//        todo.setCompleted(true);
//        return todo;
//    }


}

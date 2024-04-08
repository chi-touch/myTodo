package africa.semicolon.util;

import africa.semicolon.data.model.Todo;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.response.RegisterUserResponse;

import java.time.format.DateTimeFormatter;

public class Mapper {
    public static Todo mapper(CreateTaskRequest createTaskRequest){
        Todo todo = new Todo();
        todo.setBody(createTaskRequest.getBody());
        todo.setTitle(createTaskRequest.getTitle());
        todo.setTaskList(createTaskRequest.getTaskList());
        todo.setAuthor(createTaskRequest.getAuthor());
        todo.setLocalDate(createTaskRequest.getLocalDate());
        return todo;
    }

    public static TodoUser map(RegisterUserRequest registerUserRequest){
        TodoUser user = new TodoUser();
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        user.setUserName(registerUserRequest.getUserName());
        user.setPassword(registerUserRequest.getPassword());
        user.setLocked(false);
        return user;
    }
    public static RegisterUserResponse registerResponseMap(TodoUser user){
        RegisterUserResponse response = new RegisterUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUserName());
        response.setDateRegistered(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a").format(user.getDateRegistered()));
        return response;
    }

}

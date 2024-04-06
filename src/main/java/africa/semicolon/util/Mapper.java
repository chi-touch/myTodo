package africa.semicolon.util;

import africa.semicolon.data.model.Todo;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.RegisterUserRequest;

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
}

package africa.semicolon.util;

import africa.semicolon.data.model.TodoTaskList;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;

import java.time.format.DateTimeFormatter;

public class Mapper {

    public static TodoUser map(RegisterUserRequest registerRequest) {
        TodoUser user = new TodoUser();
        user.setFirstName(registerRequest.getFirstName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        return user;
    }

    public static RegisterUserResponse registerResponseMap(TodoUser user) {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setId(user.getId());
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setDateRegistered(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(user.getDateRegistered()));
        return registerUserResponse;

    }

    public static LoginResponse mapLoginResponse(TodoUser user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(user.getUsername());
        loginResponse.setMessage("Login was successful");
        return loginResponse;

    }
    public static LogoutUserResponse mapLogoutResponse(TodoUser user) {
        LogoutUserResponse loginUserResponse = new LogoutUserResponse();
        loginUserResponse.setId(user.getId());
        loginUserResponse.setMessage("Logout successful");
        return loginUserResponse;

    }

    public static TodoListResponse mapCreateTodoListResponseWith(TodoTaskList todoList) {
        TodoListResponse todoListResponse = new TodoListResponse();
        todoListResponse.setListId(todoList.getId());
        todoListResponse.setTitle((todoList.getTitle()));
        todoListResponse.setTaskPriority(todoList.getPriority());
        todoListResponse.setDateCreated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateCreated()));
        return todoListResponse;
    }

    public static TodoTaskList checkMap(CreateTaskRequest createTaskRequest) {
        TodoTaskList todoList1 = new TodoTaskList();
        todoList1.setTitle(createTaskRequest.getTitle());
        todoList1.setPriority(createTaskRequest.getPriority());
        return todoList1;
    }

    public static TodoTaskList checkMapTask(MarkTaskRequest markTaskRequest) {
        TodoTaskList todoList1 = new TodoTaskList();
        todoList1.setTitle(markTaskRequest.getTitle());
        return todoList1;
    }

    public static TodoTaskList map(EditTodolistRequest editPostRequest, TodoTaskList todoList) {
        todoList.setTitle(editPostRequest.getTitle());
        todoList.setPriority(editPostRequest.getTaskPriority());
        return todoList;
    }

    public static EditTodolistUserResponse mapEditTodoResponseWith(TodoTaskList todoList) {
        EditTodolistUserResponse todolistUserResponse = new EditTodolistUserResponse();
        todolistUserResponse.setListId(todoList.getId());
        todolistUserResponse.setTitle(todoList.getTitle());
        todolistUserResponse.setTaskPriority(todoList.getPriority());
        todolistUserResponse.setDateUpdated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateUpdated()));
        return todolistUserResponse;
    }

    public static AssignTaskResponse mapAssignTaskResponseWith(TodoTaskList todoList) {
        AssignTaskResponse assignTaskResponse = new AssignTaskResponse();
        assignTaskResponse.setListId(todoList.getId());
        assignTaskResponse.setTitle(todoList.getTitle());
        assignTaskResponse.setTaskPriority(todoList.getPriority());
        assignTaskResponse.setDateUpdated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateUpdated()));
        return assignTaskResponse;
    }

    public static DeleteTodoListResponse mapDeleteTodolistResponseWith(TodoTaskList todoList) {
        DeleteTodoListResponse deletePostResponse = new DeleteTodoListResponse();
        deletePostResponse.setTaskId(todoList.getId());
        return deletePostResponse;
    }

    public static TodoListResponse mapTodoListResponse(TodoTaskList todoList) {
        TodoListResponse todoListResponse = new TodoListResponse();
        todoListResponse.setListId(todoList.getId());
        todoListResponse.setTitle(todoList.getTitle());
        todoListResponse.setTaskPriority(todoList.getPriority());
        todoListResponse.setDateCreated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateCreated()));
        return todoListResponse;
    }

//    public static ViewAllPendingTaskResponse mapPendingTasks(TodoList todoList) {
//        ViewAllPendingTaskResponse pendingTaskResponse = new ViewAllPendingTaskResponse();
//        pendingTaskResponse.setListId(todoList.getId());
//        pendingTaskResponse.setTitle(todoList.getTitle());
//        pendingTaskResponse.setStatus(todoList.getStatus());
//        pendingTaskResponse.setDateCreated(DateTimeFormatter
//                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateCreated()));
//        return pendingTaskResponse;
//
//
//   }

    public static MarkTaskResponse mapMarkTaskResponse(TodoTaskList todoList) {
        MarkTaskResponse markTaskResponse = new MarkTaskResponse();
        markTaskResponse.setListId(todoList.getId());
        markTaskResponse.setTitle(todoList.getTitle());
        markTaskResponse.setTaskPriority(todoList.getPriority());
        markTaskResponse.setDateUpdated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(todoList.getDateUpdated()));
        return markTaskResponse;
    }

    public static StartTaskResponse startTaskResponseMap(TodoTaskList task){
        StartTaskResponse startTaskResponse = new StartTaskResponse();
        startTaskResponse.setListId(task.getId());
        startTaskResponse.setTitle(task.getTitle());
        startTaskResponse.setTaskPriority(task.getPriority());
        startTaskResponse.setStartTime(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(task.getStartTime()));
        return startTaskResponse;
    }


//    public static TodoTaskList mapper(CreateTaskRequest createTaskRequest){
//        TodoTaskList todo = new TodoTaskList();
//        todo.setBody(createTaskRequest.getBody());
//        todo.setTitle(createTaskRequest.getTitle());
//        todo.setTaskList(createTaskRequest.getTaskList());
//        todo.setAuthor(createTaskRequest.getAuthor());
//        todo.setLocalDate(createTaskRequest.getLocalDate());
//        todo.setPriority(createTaskRequest.getPriority());
//       // todo.setStatus(createTaskRequest.getStatus());
////        todo.setCompleted(false);
//        return todo;
//    }
//
//    public static TodoUser map(RegisterUserRequest registerUserRequest){
//        TodoUser user = new TodoUser();
//        user.setFirstName(registerUserRequest.getFirstName());
//        user.setLastName(registerUserRequest.getLastName());
//        user.setUsername(registerUserRequest.getUsername());
//        user.setPassword(registerUserRequest.getPassword());
//        user.setLocked(false);
//        return user;
//    }
//    public static RegisterUserResponse registerResponseMap(TodoUser user){
//        RegisterUserResponse response = new RegisterUserResponse();
//        response.setId(user.getId());
//        response.setUsername(user.getUsername());
//        response.setDateRegistered(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a").format(user.getDateRegistered()));
//        return response;
//    }
//
    public static TodoTaskList updateMapper(UpdateTaskRequest updateTaskRequest){
        TodoTaskList todoTask = new TodoTaskList();
        todoTask.setBody(updateTaskRequest.getBody());
        todoTask.setTitle(updateTaskRequest.getTitle());
        todoTask.setAuthor(updateTaskRequest.getAuthor());
        todoTask.setLocalDate(updateTaskRequest.getLocalDate());
       // todoTask.setPriority(updateTaskRequest.getPriority());
       // todoTask.setStatus(updateTaskRequest.getStatus());
        return todoTask;
    }
////    public static TodoTask completeMapper(CreateTaskRequest createTaskRequest){
////        TodoTask todo = new TodoTask();
////        todo.setBody(createTaskRequest.getBody());
////        todo.setTitle(createTaskRequest.getTitle());
////        todo.setTaskList(createTaskRequest.getTaskList());
////        todo.setAuthor(createTaskRequest.getAuthor());
////        todo.setLocalDate(createTaskRequest.getLocalDate());
////        todo.setPriority(createTaskRequest.getPriority());
////        todo.setStatus(createTaskRequest.getStatus());
////        todo.setCompleted(true);
////        return todo;
////    }
//

}

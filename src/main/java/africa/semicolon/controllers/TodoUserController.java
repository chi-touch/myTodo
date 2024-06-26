package africa.semicolon.controllers;

import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.exceptions.*;
import africa.semicolon.service.TodoUserService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1")
public class TodoUserController {
    @Autowired
   private  TodoUserService userService;


    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterUserRequest registerUserRequest){
        try {
            var result = userService.register(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch(UserNameAlreadyExistException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest){
        try {
            var result = userService.login(loginRequest);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch(UserNameAlreadyExistException e){
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        }
    }
    @DeleteMapping("/todoUser")
    public ResponseEntity<?> deleteAll(){
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findByUserName/{username}")
    public ResponseEntity<?> findByUserName(@PathVariable String username){
        try{
            var result = userService.findByUserName(username);
            return ResponseEntity.ok(new ApiResponse(true, result));
        }catch (InvalidUserNameException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest createTaskRequest){
        try {
            var result = userService.createTask(createTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        }catch (TitleAlreadyExistException | InvalidTitleException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateTaskRequest updateTaskRequest){
        try {
            var updateResult = userService.update(updateTaskRequest);
            return new ResponseEntity<>(new ApiResponse(true,updateResult),CREATED);
        }catch (AuthorDoesNotExist e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }











}

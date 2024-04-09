package africa.semicolon.controllers;

import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.exceptions.InvalidUserNameException;
import africa.semicolon.exceptions.UserNameAlreadyExistException;
import africa.semicolon.service.TodoUserService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1")
public class TodoUserController {

    @Autowired
    TodoUserService userService;


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




//    @PostMapping("/register")
//    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest registerUserRequest){
//        RegisterUserResponse response = userService.register(registerUserRequest);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    @GetMapping("/{userName}")
//    public ResponseEntity <TodoUser> findByUserName(@PathVariable String userName){
//        TodoUser userList = userService.findByUserName(userName);
//        return ResponseEntity.status(HttpStatus.OK).body(userList);
//    }
//
//    @DeleteMapping("/todoUser")
//    public ResponseEntity <Void> deleteAll(){
//        userService.deleteAll();
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
//        LoginResponse loginResponse = userService.login(loginRequest);
//        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
//    }
//








}

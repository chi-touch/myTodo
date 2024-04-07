package africa.semicolon.controllers;

import africa.semicolon.data.model.Todo;
import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.service.TodoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoUserController {

    @Autowired
    TodoUserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse response = userService.register(registerUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userName}")
    public ResponseEntity <TodoUser> findByUserName(@PathVariable String userName){
        TodoUser userList = userService.findByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @DeleteMapping("/api/v1/users")
    public ResponseEntity <Void> deleteAll(){
        userService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


//        @DeleteMapping("/api/v1/users") // Specify the URL path for the delete endpoint
//        public ResponseEntity<Void> deleteAll() {
//            userService.deleteAll();
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 No Content
//        }



}

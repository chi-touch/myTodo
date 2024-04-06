package africa.semicolon.controllers;

import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.service.TodoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TodoUserController {

    @Autowired
    TodoUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        return new ResponseEntity<>(userService.register(registerUserRequest), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> findByUserName(@PathVariable String name){
        return new ResponseEntity<>(userService.findByUserName(name), HttpStatus.OK);
    }
}

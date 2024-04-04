package africa.semicolon.service;

import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceImplTest {

    UserRequest userRequest;
    LoginRequest loginRequest;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp(){

        userRequest = new UserRequest();
        loginRequest = new LoginRequest();
    }

    @Test
    public void testToRegisterUser(){
        userRequest.setUserName("chichi");
        userRequest.setFirstName("chi");
        userRequest.setLastName("dav");
        userRequest.setPassword("1234");
        userService.register(userRequest);
        assertEquals(1,userService.count());
    }

    @Test
    public void testToLogin(){

        loginRequest.setUsername("chichi");
        loginRequest.setPassword("1234");
        userService.login(loginRequest);
        TodoUser myUser = userService.findByUserName("chichi");
        assertFalse(myUser.isLocked());

    }


}
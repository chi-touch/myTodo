package africa.semicolon.service;

import africa.semicolon.data.model.TodoUser;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TodoUserServiceImplTest {

   RegisterUserRequest registerUserRequest;
//    LoginRequest loginRequest;

    @Autowired
    TodoUserService userService;

    @BeforeEach
    public void setUp(){

        registerUserRequest = new RegisterUserRequest();
        userService.deleteAll();

        //loginRequest = new LoginRequest();

    }

    @Test
    @Order(0)
    public void testToRegisterUser(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("chichi");
        registerUserRequest.setFirstName("chi");
        registerUserRequest.setLastName("dav");
        registerUserRequest.setPassword("1234");
        userService.register(registerUserRequest);

        assertEquals(1,userService.getNumberOfUser());
    }

    @Test
    @Order(1)
    public void testToLogin(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("chichiu");
        registerUserRequest.setFirstName("chio");
        registerUserRequest.setLastName("davi");
        registerUserRequest.setPassword("1239");
        userService.register(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest = new LoginRequest();
        loginRequest.setUsername("chichiu");
        loginRequest.setPassword("1239");
        userService.login(loginRequest);
        TodoUser myUser = userService.findByUserName("chichiu");
        assertFalse(myUser.isLocked());
    }

    @Test
    @Order(2)
    public void testToDeleteAll(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("chichi");
        registerUserRequest.setFirstName("chi");
        registerUserRequest.setLastName("dav");
        registerUserRequest.setPassword("1234");
        userService.register(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setPassword("2222");
        registerUserRequest1.setUserName("amil");
        registerUserRequest1.setFirstName("ama");
        registerUserRequest1.setLastName("john");
        userService.register(registerUserRequest1);
        userService.deleteAll();
        assertThat(userService.count(), is(0L));





    }

}
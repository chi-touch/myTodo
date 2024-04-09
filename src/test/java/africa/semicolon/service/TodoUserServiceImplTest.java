package africa.semicolon.service;

import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TodoUserServiceImplTest {


private  RegisterUserRequest registerUserRequest;
//    LoginRequest loginRequest;

    @Autowired
    TodoUserService userService;

    @BeforeEach
    public void setUp(){
        userService.deleteAll();
       registerUserRequest = new RegisterUserRequest();
       registerUserRequest.setFirstName("chi");
       registerUserRequest.setLastName("dav");
       registerUserRequest.setUsername("chichi");
       registerUserRequest.setPassword("1234");



        //loginRequest = new LoginRequest();
    }

    @Test
    public void testToRegisterUser(){

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chichi");
        registerUserRequest.setFirstName("chi");
        registerUserRequest.setLastName("dav");
        registerUserRequest.setPassword("1234");
        userService.register(registerUserRequest);

//        RegisterUserResponse  registerUserResponse = new RegisterUserResponse();
//        assertThat(registerUserResponse.getMessage()).isNotNull();
        //assertThat(registerUserResponse.getMessage());

        assertEquals(1,userService.getNumberOfUser());

    }

    @Test
    //@Order(1)
    public void testToLogin(){
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chichiu");
        registerUserRequest.setFirstName("chio");
        registerUserRequest.setLastName("davi");
        registerUserRequest.setPassword("1239");
        userService.register(registerUserRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest = new LoginRequest();
        loginRequest.setUsername("chichiu");
        loginRequest.setPassword("1239");
        userService.login(loginRequest);
        loginRequest.setUsername("chichiu");
        loginRequest.setPassword("1239");

        LoginResponse loginResponse = userService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isNotNull();

//        TodoUser myUser = userService.findByUserName("chichiu");
    }

    @Test
    public void testToFindUser(){
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chih");
        registerUserRequest.setFirstName("chiom");
        registerUserRequest.setLastName("daviv");
        registerUserRequest.setPassword("1232");
        userService.register(registerUserRequest);
        assertEquals("chih",userService.findByUserName("chih").getUsername());

    }

    @Test
    public void testToDeleteAll(){

        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("chichi");
        registerUserRequest.setFirstName("chi");
        registerUserRequest.setLastName("dav");
        registerUserRequest.setPassword("1234");
        userService.register(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setPassword("2222");
        registerUserRequest1.setUsername("amil");
        registerUserRequest1.setFirstName("ama");
        registerUserRequest1.setLastName("john");
        userService.register(registerUserRequest1);
        userService.deleteAll();
        assertThat(userService.getNumberOfUser());
        System.out.println(userService.getNumberOfUser());
    }

    @Test
    public void testToLoginWithWrongPassword(){

    }

}
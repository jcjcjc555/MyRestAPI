package com.example.demo.controller;

import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTest {

    @Mock
    UserService userService;

    @Before
    public void configMock() {
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.standaloneSetup(new UserController(userService));
    }

    @Test
    public void testGetUserById(){
        Mockito.when(userService.findUserById(anyInt())).thenReturn(new User(1, "111", "222", "chun"));
        given().accept("application/json").get("/search/1").peek().
                then().assertThat()
                .statusCode(200)
                .body(Matchers.equalTo("{\"id\":1,\"lat\":\"111\",\"lng\":\"222\",\"name\":\"chun\"}"));
    }


    @Test
    public void getNoneExistingUserByID(){
        Mockito.when(userService.findUserById(anyInt())).thenReturn(null);
        given().accept("application/json").get("/search/100").peek().
                then().assertThat()
                .statusCode(404)
                .body("errorCode", Matchers.equalTo(404));
    }

    @Test
    public void testGetUser(){
        User user = new User(5, "5", "55", "huang");
        User user1 = new User(6, "6", "66", "yang");
        List<User> li = new ArrayList<>();
        li.add(user);
        li.add(user1);
        Mockito.when(userService.findUser()).thenReturn(li);
        given().accept("application/json").get("/search").peek().
                then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size",Matchers.is(2));
    }

    @Test
    public void getNoneExistingUser(){
        Mockito.when(userService.findUser()).thenReturn(null);
        given().accept("application/json").get("/search").peek().
                then().assertThat()
                .statusCode(404)
                .body("errorCode", Matchers.equalTo(404));
    }

}
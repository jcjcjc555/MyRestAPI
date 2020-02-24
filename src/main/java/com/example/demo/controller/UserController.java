package com.example.demo.controller;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(value="Users", description="Operations pertaining to users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List<User>> getUser() throws Exception{
        List<User> user = userService.findUser();
        if (user == null || user.isEmpty()) {
            throw new UserNotFoundException();
        }
        log.info("Success");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/search/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable int id) throws Exception {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        log.error("Controller Error",ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerUserNotFound(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        log.error("Not Found Error", ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

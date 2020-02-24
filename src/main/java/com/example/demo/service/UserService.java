package com.example.demo.service;

import com.example.demo.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> findUser();

    public User findUserById(int id);
}

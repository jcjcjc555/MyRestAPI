package com.example.demo.service.impl;

import com.example.demo.dto.User;
import com.example.demo.pojo.Resource;
import com.example.demo.service.UserService;
import com.example.demo.util.RestTemplateSingleton;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private String url = "https://jsonplaceholder.typicode.com/users";

    @Override
    public List<User> findUser() {

        RestTemplate rt = RestTemplateSingleton.getter();

        Resource[] res = rt.getForObject(url, Resource[].class);

        List<User> users = new ArrayList<>();

        for(Resource r : res) {
           User user = new User(r.getId(), r.getAddress().getGeo().getLat(), r.getAddress().getGeo().getLng(), r.getName());
           users.add(user);
        }
        return users;
    }

    @Override
    public User findUserById(int id) {

        RestTemplate rt = RestTemplateSingleton.getter();

        Resource[] resArr = rt.getForObject(url, Resource[].class);

        List<Resource> li = Arrays.asList(resArr);

        Resource res = li.stream().
                filter(entity -> entity.getId() == id).
                findFirst().get();

        return new User(res.getId(), res.getAddress().getGeo().getLat(), res.getAddress().getGeo().getLng(), res.getName());
    }
}

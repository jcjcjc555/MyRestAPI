package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Resource {

    private int id;

    private String name;

    private String username;

    private String email;

    private Address address;

    private String phone;

    private String website;

    private Company company;


}

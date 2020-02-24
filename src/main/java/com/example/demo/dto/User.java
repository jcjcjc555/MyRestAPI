package com.example.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String lat;
    private String lng;
    private String name;

}

package com.example.demo.dto.user;

import lombok.Data;

@Data
public class UserOzetDto {
    private Integer id;
    private String name;
    private String surname;
    private String password;
    private String tcNo;
    private String email;
    private String userType;

}

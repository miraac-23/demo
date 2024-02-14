package com.example.demo.dto.user;

import lombok.Data;

@Data
public class UserSummaryDto {
    private Integer id;
    private String name;
    private String surname;
    private String tcNo;
    private String email;
    private String userType;
}

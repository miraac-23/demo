package com.example.demo.service;

import com.example.demo.dto.user.UserAddDto;
import com.example.demo.dto.user.UserResultDto;

public interface UserService {

    UserResultDto getUserById(Integer id);

    UserResultDto userAdd(UserAddDto userAddDto);

    UserResultDto getUserByEmail(String email);


}

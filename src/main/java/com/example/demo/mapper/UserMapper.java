package com.example.demo.mapper;

import com.example.demo.dto.user.UserAddDto;
import com.example.demo.dto.user.UserResultDto;
import com.example.demo.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResultDto entityToDto(UserEntity userEntity);

    UserEntity dtoToEntity(UserResultDto userResultDto);

    UserEntity addDtoToEntity(UserAddDto userAddDto);

}

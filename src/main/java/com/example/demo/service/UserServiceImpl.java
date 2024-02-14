package com.example.demo.service;

import com.example.demo.dto.user.UserAddDto;
import com.example.demo.dto.user.UserResultDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppNotFoundException;
import com.example.demo.exception.AppValidationException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    @Override
    public UserResultDto getUserById(Integer id) {
        if (id == null) {
            throw new AppValidationException("Id bilgisi boş olamaz");
        }
        try {
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow(() -> new AppNotFoundException(id + " id'sine sahip bir kullanıcı sistemde mevcut değil"));

            return userMapper.entityToDto(userEntity);

        } catch (AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public UserResultDto userAdd(UserAddDto userAddDto) {
        if (userAddDto == null) {
            throw new AppValidationException("eksik bilgi");
        }

        Optional<UserEntity> user = userRepository.findByEmail(userAddDto.getEmail());
        if (user.isPresent()) {

            throw new AppValidationException("Aynı mail adresine kayıtlı bir kullanıcı sistemde bulunmaktadır, " +
                    "farklı bir kullanıcı adı ile tekrar deneyiniz");
        }

        try {
            UserEntity userEntity = userMapper.addDtoToEntity(userAddDto);

            userRepository.save(userEntity);

            return userMapper.entityToDto(userEntity);

        } catch (AppException | AppValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public UserResultDto getUserByEmail(String email) {
        if (email == null) {
            throw new AppValidationException("Id bilgisi boş olamaz");
        }
        try {
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new AppNotFoundException(email + " e sahip bir kullanıcı sistemde mevcut değil"));
            return userMapper.entityToDto(user);
        } catch (AppValidationException | AppNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}

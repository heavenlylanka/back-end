package com.heavenlylanka.heavenlylanka.service;

import com.heavenlylanka.heavenlylanka.dto.UserDto;
import com.heavenlylanka.heavenlylanka.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
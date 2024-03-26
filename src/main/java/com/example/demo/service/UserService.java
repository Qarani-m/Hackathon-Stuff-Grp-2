package com.example.demo.service;

import com.example.demo.dto.users.UserDto;

public interface UserService {

    Object login(UserDto userDto);

    Object register(UserDto userDto);
}

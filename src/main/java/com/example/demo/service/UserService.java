package com.example.demo.service;

import com.example.demo.dto.users.ChangeUserRole;
import com.example.demo.dto.users.LoginDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.LogEntity;

import java.util.List;

public interface UserService {

    Object login(LoginDto userDto);

    Object register(UserDto userDto);

    Object changeUserRole(ChangeUserRole changeUserRole);

    List<String> readLogsFromFile();

    LogEntity parseLog(String log);
}

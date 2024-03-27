package com.example.demo.service;

import com.example.demo.dto.users.ChangeUserRole;
import com.example.demo.dto.users.LoginDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.LogEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Object login(LoginDto userDto) {
        logger.debug("Attempting to log in user with code: {}", userDto.getCode());
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        String code = userDto.getCode();
        UserEntity userEntity = userRepository.findByCode(code);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                logger.info("User with code {} logged in successfully", code);
                return userEntity;
            } else {
                logger.warn("Invalid username or password for user with code {}", code);
                return "Invalid username or password";
            }
        } else {
            logger.warn("User with code {} not found", code);
            return "User not found";
        }
    }

    @Override
    public Object register(UserDto userDto) {
        logger.debug("Attempting to register a new user");
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty() ||
                userDto.getPassword() == null || userDto.getPassword().isEmpty() ||
                userDto.getCode() == null || userDto.getCode().isEmpty() ||
                userDto.getRole() == null) {
            logger.warn("One or more required fields are missing for user registration");
            return "All fields are required for registration";
        }
        UserEntity userEntity = UserEntity.builder()
                .code(userDto.getCode())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();
        UserEntity savedUser = userRepository.save(userEntity);
        logger.info("User with code {} registered successfully", savedUser.getCode());
        return savedUser;
    }

    @Override
    public Object changeUserRole(ChangeUserRole changeUserRole) {
        UserEntity userEntity=userRepository.findByCode(changeUserRole.getTPersonChangingRoleCode());

        if(userEntity.getRole() != changeUserRole.personChangingRole){
            return "Return something appropriate";
        }
        if (!checkAuthority(changeUserRole.getPersonChangingRole())) {
            return "Insufficient authority to change user role";
        }
        return "User role changed successfully";
    }
    private boolean checkAuthority(UserEntity.Role personChangingRole) {
        return personChangingRole == UserEntity.Role.SUPERADMIN;
    }

    @Override
    public List<String> readLogsFromFile() {
        List<String> logs = new ArrayList<>();
        String fileName = System.getProperty("user.dir") + "/app_logs.log";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            logger.error("Log file not found");

            e.printStackTrace(); // Handle file reading errors gracefully in your application
        }
        return logs;
    }
    @Override
    public  LogEntity parseLog(String log) {
        Pattern pattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2})\\s(\\d{2}:\\d{2}:\\d{2}\\.\\d{3})\\s\\[(.*?)\\]\\s(\\w+)\\s+(.*?)\\s+-\\s(.*)$");
        Matcher matcher = pattern.matcher(log);

        if (matcher.matches()) {
            String date = matcher.group(1);
            String time = matcher.group(2);
            String type = matcher.group(3);
            String level = matcher.group(4);
            String _class = matcher.group(5);
            String message = matcher.group(6);

            return new LogEntity(date, time, type, level, _class, message);
        } else {
            throw new IllegalArgumentException("Invalid log format: " + log);
        }
    }
}
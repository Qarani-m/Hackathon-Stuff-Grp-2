package com.example.demo.controller.users;

import com.example.demo.dto.users.ChangeUserRole;
import com.example.demo.dto.users.LoginDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.LogEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto userDto) {
        try {
            logger.debug("Received a request for user login");
            if (userDto == null || userDto.getUsername() == null || userDto.getPassword() == null) {
                logger.warn("Invalid user credentials provided");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user credentials");
            }
            Object response = userService.login(userDto);
            if (response instanceof String) {
                logger.info("Username or password is invalid");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username or password invalid");
            } else if (response instanceof UserDto) {
                logger.info("User login successful");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                logger.error("Unexpected response from login service");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected response from login service");
            }
        } catch (Exception e) {
            logger.error("Exception occurred during user login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            logger.debug("Received a request for user registration");
            // Check if userDto is null
            if (userDto == null) {
                logger.warn("User data is required for registration");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User data is required");
            }
            Object response = userService.register(userDto);
            if (response instanceof String) {
                logger.warn("Error occurred during user registration: {}", response);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                logger.info("User registration successful");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (Exception e) {
            logger.error("Exception occurred during user registration: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }


    @GetMapping("/logs")
    public ResponseEntity<List<LogEntity>> getLogsB() {
        List<String> logs = userService.readLogsFromFile();
        List<LogEntity> filteredLogs = new ArrayList<>();
        for(String log: logs) {
            LogEntity logEntity = userService.parseLog(log);
            filteredLogs.add(logEntity);
        }
        return ResponseEntity.ok().body(filteredLogs);

    }


    @GetMapping("/logs/level/{logLevel}")
    public ResponseEntity<?> getLogsByLevel(@PathVariable String logLevel) {
        List<String> logs = userService.readLogsFromFile();
        List<LogEntity> filteredLogs = new ArrayList<>();
        try {
            for(String log: logs) {
                LogEntity logEntity = userService.parseLog(log);
                filteredLogs.add(logEntity);
            }
            logger.info("Logs filtered successfully for level: {}", logLevel);
            return ResponseEntity.ok(filteredLogs);
        } catch (Exception e) {
            logger.error("Error occurred while filtering logs for level: {}", logLevel, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while filtering logs.");
        }
    }

    @GetMapping("/logs/type/{logType}")
    public ResponseEntity<?> getLogsByType(@PathVariable String logType) {
        List<String> logs = userService.readLogsFromFile();
        List<LogEntity> filteredLogs = new ArrayList<>();
        try {

            for(String log: logs) {
                LogEntity logEntity = userService.parseLog(log);
                filteredLogs.add(logEntity);
            }
            logger.info("Logs filtered successfully for level: {}", logType);
            return ResponseEntity.ok(filteredLogs);
        } catch (Exception e) {
            logger.error("Error occurred while filtering logs for level: {}", logType, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while filtering logs.");
        }
    }
//    List<String> filteredLogsByDate = filterLogsByDate(logs, "2024-03-27");
//    List<String> filteredLogsByDateTime = filterLogsByDateTime(logs, "2024-03-27T08:20:00");

    @GetMapping("/logs/date/{date}")
    private ResponseEntity<List<LogEntity>> filterLogsByDate(@PathVariable String dateString) {
        List<String> logs = userService.readLogsFromFile();
        List<LogEntity> filteredLogs = new ArrayList<>();
        try {
            LocalDate targetDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
            for (String log : logs) {
                String logDateStr = log.substring(0, 10); // Extract the date part of the log
                LocalDate logDate = LocalDate.parse(logDateStr, DateTimeFormatter.ISO_DATE);
                if (logDate.equals(targetDate)) {
                    LogEntity logEntity = userService.parseLog(log);
                    filteredLogs.add(logEntity);
                }
            }
            logger.info("Logs filtered by date successfully for date: {}", dateString);
        } catch (Exception e) {
            logger.error("Error occurred while filtering logs by date: {}", dateString, e);
        }
        return ResponseEntity.ok(filteredLogs);
    }

    @GetMapping("/logs/datetime/{logType}")
    private ResponseEntity<List<LogEntity>> filterLogsByDateTime(@PathVariable String dateTimeString) {
        List<String> logs = userService.readLogsFromFile();
        List<LogEntity> filteredLogs = new ArrayList<>();
        try {
            LocalDateTime targetDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
            for (String log : logs) {
                String logDateTimeStr = log.substring(0, 23); // Extract the date-time part of the log
                LocalDateTime logDateTime = LocalDateTime.parse(logDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                if (logDateTime.equals(targetDateTime)) {
                    LogEntity logEntity = userService.parseLog(log);
                    filteredLogs.add(logEntity);
                }
            }
            logger.info("Logs filtered by date-time successfully for date-time: {}", dateTimeString);
        } catch (Exception e) {
            logger.error("Error occurred while filtering logs by date-time: {}", dateTimeString, e);
        }
        return ResponseEntity.ok(filteredLogs);
    }

    @PostMapping("/changeUserRole")
    public ResponseEntity<?> changeUserRole(@RequestBody ChangeUserRole changeUserRole) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            logger.debug("Received request to change user role: {}", changeUserRole);
            Object response = userService.changeUserRole(changeUserRole);
            if (response instanceof String) {
                logger.warn("Error occurred while changing user role: {}", response);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                logger.info("User role changed successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            logger.error("Exception occurred during user role change: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }
}
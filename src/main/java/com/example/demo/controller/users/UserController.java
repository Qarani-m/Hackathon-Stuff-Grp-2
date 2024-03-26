package com.example.demo.controller.users;


import com.example.demo.dto.users.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto){
        try {
            if (userDto == null || userDto.getUsername() == null || userDto.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user credentials");
            }
            return ResponseEntity.status(HttpStatus.OK).body(userService.login(userDto));
        } catch (Exception e) {
            System.err.println("Exception occurred during user login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }


    }
}

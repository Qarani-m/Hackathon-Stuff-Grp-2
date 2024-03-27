package com.example.demo.dto.users;

import com.example.demo.entity.UserEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginDto {
    private String username;
    private String password;
    private String code;
}

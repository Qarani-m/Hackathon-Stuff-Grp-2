package com.example.demo.service;

import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Object login(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        UserEntity.Role role = userDto.getRole();
        String code = userDto.getCode();
       UserEntity userEntity= userRepository.findByCode(code);
       if(userEntity !=null){
          if(passwordEncoder.matches(password, userEntity.getPassword())){
              return userEntity;
          }
       }





        return null;
    }

    public Object register(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()
                .code(userDto.getCode())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();
        return userRepository.save(userEntity);
    }
}

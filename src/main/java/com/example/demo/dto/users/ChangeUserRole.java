package com.example.demo.dto.users;

import com.example.demo.entity.UserEntity;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChangeUserRole {
    public UserEntity.Role personChangingRole;
    public UserEntity.Role newRole;
    public UserEntity.Role oldRole;
    public String personChangingRoleUsername;
    public String tPersonChangingRoleCode;
    public String newRoleeUsername;
}

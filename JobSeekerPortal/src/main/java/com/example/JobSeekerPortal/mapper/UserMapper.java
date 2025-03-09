package com.example.JobSeekerPortal.mapper;

import com.example.JobSeekerPortal.dto.UserDTO;
import com.example.JobSeekerPortal.entity.User;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(userDTO.isEnabled());
        return user;
    }
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}

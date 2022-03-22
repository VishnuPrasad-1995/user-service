package com.mavericsystems.userservice.service;

import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    UserDto createUser(User user);
    String deleteUser(String userId);
    List<UserDto> getUsers();
    UserDto updateUser(User user, String userId);
    UserDto getUserById(String userId);
}

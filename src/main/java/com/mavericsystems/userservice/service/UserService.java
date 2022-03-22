package com.mavericsystems.userservice.service;

import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.dto.UserRequest;

import java.util.List;


public interface UserService {

    UserDto createUser(UserRequest userRequest);
    String deleteUser(String userId);
    List<UserDto> getUsers(int page, int pageSize);
    UserDto updateUser(UserRequest userRequest, String userId);
    UserDto getUserById(String userId);
}

package com.mavericsystems.userservice.service;

import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.dto.UserRequest;
import com.mavericsystems.userservice.model.User;


import java.util.List;


public interface UserService {

    UserDto createUser(UserRequest userRequest);
    String deleteUser(String userId);
    List<UserDto> getUsers(Integer page, Integer pageSize);
    UserDto updateUser(UserRequest userRequest, String userId);
    UserDto getUserById(String userId);
    UserDto getUserDetailsByEmail(String emailId);
}

package com.mavericsystems.userservice.service;

import com.mavericsystems.userservice.dto.UserWithOutPassword;
import com.mavericsystems.userservice.dto.UserRequest;

import java.util.List;


public interface UserService {
    UserWithOutPassword createUser(UserRequest userRequest);

    String deleteUser(String userId);

    List<UserWithOutPassword> getUsers(Integer page, Integer pageSize);

    UserWithOutPassword updateUser(UserRequest userRequest, String userId);

    UserWithOutPassword getUserById(String userId);

    UserWithOutPassword getUserDetailsByEmail(String emailId);
}

package com.mavericsystems.userservice.service;


import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.model.User;
import com.mavericsystems.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mavericsystems.userservice.constant.UserConstant.deleteCustomer;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDto createUser(User user) {
        User user1 =  userRepo.save(user);
        return new UserDto(user1.getId(), user1.getFirstName(),user1.getLastName(),user1.getMiddleName(),user1.getPhoneNumber(),user1.getEmail(),user1.getDateOfBirth(),user1.getEmployeeNumber(),user1.getBloodGroup(),user1.getGender());

    }

    @Override
    public List<UserDto> getUsers() {
       List<User> users= userRepo.findAll();
       List<UserDto> userDtos = new ArrayList<>();
        for(User user1 : users){
            userDtos.add(new UserDto(user1.getId(), user1.getFirstName(),user1.getLastName(),user1.getMiddleName(),user1.getPhoneNumber(),user1.getEmail(),user1.getDateOfBirth(),user1.getEmployeeNumber(),user1.getBloodGroup(),user1.getGender()));
        }
        return userDtos;
    }



}

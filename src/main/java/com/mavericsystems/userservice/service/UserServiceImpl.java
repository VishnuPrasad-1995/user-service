package com.mavericsystems.userservice.service;


import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.model.User;
import com.mavericsystems.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    

    @Override
    public UserDto updateUser(User user, String userId) {

        User user1 = userRepo.findById(userId).get();
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setMiddleName(user.getMiddleName());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setEmail(user.getEmail());
        user1.setDateOfBirth(user.getDateOfBirth());
        user1.setGender(user.getGender());
        user1.setMaritalStatus(user.getMaritalStatus());
        user1.setBloodGroup(user.getBloodGroup());
        user1.setPassword(user.getPassword());
        userRepo.save(user1);
        return new UserDto(user1.getId(), user1.getFirstName(),user1.getLastName(),user1.getMiddleName(),user1.getPhoneNumber(),user1.getEmail(),user1.getDateOfBirth(),user1.getEmployeeNumber(),user1.getBloodGroup(),user1.getGender());

    }


}

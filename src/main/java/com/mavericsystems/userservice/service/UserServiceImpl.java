package com.mavericsystems.userservice.service;

import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.dto.UserRequest;
import com.mavericsystems.userservice.exception.UserNotFoundException;
import com.mavericsystems.userservice.model.User;
import com.mavericsystems.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mavericsystems.userservice.constant.UserConstant.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDto createUser(UserRequest userRequest) {
        User user1 = new User();
        user1.setFirstName(userRequest.getFirstName());
        user1.setLastName(userRequest.getLastName());
        user1.setMiddleName(userRequest.getMiddleName());
        user1.setPhoneNumber(userRequest.getPhoneNumber());
        user1.setEmail(userRequest.getEmail());
        user1.setDateOfBirth(userRequest.getDateOfBirth());
        user1.setGender(userRequest.getGender());
        user1.setEmployeeNumber(userRequest.getEmployeeNumber());
        user1.setBloodGroup(userRequest.getBloodGroup());
        user1.setPassword(userRequest.getPassword());
        user1 = userRepo.save(user1);
        return new UserDto(user1.getId(), user1.getFirstName(),user1.getLastName(),user1.getMiddleName(),user1.getPhoneNumber(),user1.getEmail(),user1.getDateOfBirth(),user1.getEmployeeNumber(),user1.getBloodGroup(),user1.getGender());

    }

    @Override
    public List<UserDto> getUsers(Integer page, Integer pageSize) {
            if(page==null){
                page=1;
            }
            if(pageSize==null){
                pageSize=10;
            }
            Page<User> users = userRepo.findAll(PageRequest.of(page-1, pageSize));
            List<UserDto> userDtoList = new ArrayList<>();
            for (User user1 : users) {
                userDtoList.add(new UserDto(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getMiddleName(), user1.getPhoneNumber(), user1.getEmail(), user1.getDateOfBirth(), user1.getEmployeeNumber(), user1.getBloodGroup(), user1.getGender()));
            }
            if(userDtoList.isEmpty()){
                throw new UserNotFoundException(NOUSERFOUND);
            }
            return userDtoList;

    }

    @Override
    public String deleteUser(String userId) {
        try {
            userRepo.deleteById(userId);
            return DELETEUSER;
        }
        catch (Exception e){
            throw new UserNotFoundException(USERNOTFOUND + userId);
        }

    }

    @Override
    public UserDto updateUser(UserRequest userRequest, String userId) {

        Optional<User> user2 = userRepo.findById(userId);
        if(user2.isPresent()) {
            User user1 = user2.get();
            user1.setFirstName(userRequest.getFirstName());
            user1.setLastName(userRequest.getLastName());
            user1.setMiddleName(userRequest.getMiddleName());
            user1.setPhoneNumber(userRequest.getPhoneNumber());
            user1.setEmail(userRequest.getEmail());
            user1.setDateOfBirth(userRequest.getDateOfBirth());
            user1.setGender(userRequest.getGender());
            user1.setBloodGroup(userRequest.getBloodGroup());
            user1.setPassword(userRequest.getPassword());
            userRepo.save(user1);
            return new UserDto(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getMiddleName(), user1.getPhoneNumber(), user1.getEmail(), user1.getDateOfBirth(), user1.getEmployeeNumber(), user1.getBloodGroup(), user1.getGender());
        }
        else{
            throw new UserNotFoundException(USERNOTFOUND + userId);
        }
    }

    @Override
    public UserDto getUserById(String userId) {

        Optional<User> user = userRepo.findById(userId);

        if (user.isPresent()) {
            User user1 = user.get();
            return new UserDto(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getMiddleName(), user1.getPhoneNumber(), user1.getEmail(), user1.getDateOfBirth(), user1.getEmployeeNumber(), user1.getBloodGroup(), user1.getGender());
        }
        else{
            throw new UserNotFoundException(USERNOTFOUND + userId);
        }
    }

    @Override
    public UserDto getUserDetailsByEmail(String emailId) {
        Optional<User> user = userRepo.findByEmail(emailId);

        if (user.isPresent()) {
            User user1 = user.get();
            return new UserDto(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getMiddleName(), user1.getPhoneNumber(), user1.getEmail(), user1.getDateOfBirth(), user1.getEmployeeNumber(), user1.getBloodGroup(), user1.getGender());
        }
        else{
            throw new UserNotFoundException(USERNOTFOUND + emailId);
        }
    }

}

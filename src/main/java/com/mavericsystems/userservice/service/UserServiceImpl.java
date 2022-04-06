package com.mavericsystems.userservice.service;

import com.mavericsystems.userservice.dto.UserWithOutPassword;
import com.mavericsystems.userservice.dto.UserRequest;
import com.mavericsystems.userservice.exception.EmailAlreadyExistException;
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
    public UserWithOutPassword createUser(UserRequest userRequest) {
        if (userRepo.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException(EMAIL_ALREADY_EXIST + userRequest.getEmail());
        }
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setGender(userRequest.getGender());
        user.setEmployeeNumber(userRequest.getEmployeeNumber());
        user.setBloodGroup(userRequest.getBloodGroup());
        user.setPassword(userRequest.getPassword());
        user = userRepo.save(user);
        return new UserWithOutPassword(user.getId(), user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getPhoneNumber(), user.getEmail(), user.getDateOfBirth(), user.getEmployeeNumber(), user.getBloodGroup(), user.getGender());
    }

    @Override
    public List<UserWithOutPassword> getUsers(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<User> users = userRepo.findAll(PageRequest.of(page - 1, pageSize));
        List<UserWithOutPassword> userWithOutPasswordList = new ArrayList<>();
        for (User user : users) {
            userWithOutPasswordList.add(new UserWithOutPassword(user.getId(), user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getPhoneNumber(), user.getEmail(), user.getDateOfBirth(), user.getEmployeeNumber(), user.getBloodGroup(), user.getGender()));
        }
        if (userWithOutPasswordList.isEmpty()) {
            throw new UserNotFoundException(NO_USER_FOUND);
        }
        return userWithOutPasswordList;
    }

    @Override
    public String deleteUser(String userId) {
        try {
            userRepo.deleteById(userId);
            return DELETE_USER;
        } catch (Exception e) {
            throw new UserNotFoundException(USER_NOT_FOUND + userId);
        }
    }

    @Override
    public UserWithOutPassword updateUser(UserRequest userRequest, String userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setMiddleName(userRequest.getMiddleName());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setEmail(userRequest.getEmail());
            user.setDateOfBirth(userRequest.getDateOfBirth());
            user.setGender(userRequest.getGender());
            user.setBloodGroup(userRequest.getBloodGroup());
            user.setPassword(userRequest.getPassword());
            userRepo.save(user);
            return new UserWithOutPassword(user.getId(), user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getPhoneNumber(), user.getEmail(), user.getDateOfBirth(), user.getEmployeeNumber(), user.getBloodGroup(), user.getGender());
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND + userId);
        }
    }

    @Override
    public UserWithOutPassword getUserById(String userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserWithOutPassword(user.getId(), user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getPhoneNumber(), user.getEmail(), user.getDateOfBirth(), user.getEmployeeNumber(), user.getBloodGroup(), user.getGender());
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND + userId);
        }
    }

    @Override
    public UserWithOutPassword getUserDetailsByEmail(String emailId) {
        Optional<User> userOptional = userRepo.findByEmail(emailId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserWithOutPassword(user.getId(), user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getPhoneNumber(), user.getEmail(), user.getDateOfBirth(), user.getEmployeeNumber(), user.getBloodGroup(), user.getGender());
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND + emailId);
        }
    }
}

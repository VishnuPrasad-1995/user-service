package com.mavericsystems.userservice.controller;

import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.dto.UserRequest;
import com.mavericsystems.userservice.exception.UserIdMismatchException;
import com.mavericsystems.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

import static com.mavericsystems.userservice.constant.UserConstant.USERIDMISMATCH;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize){
        return new ResponseEntity<>(userService.getUsers(page, pageSize),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequest userRequest){
            return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable("userId") String userId) {
        if(!(userRequest.getId().equals(userId))){
            throw new UserIdMismatchException(USERIDMISMATCH);
        }
        return new ResponseEntity<>(userService.updateUser(userRequest, userId), HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return new ResponseEntity<> (userService.deleteUser(userId), HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/getUserByEmail/{emailId}")
    public UserDto getUserDetailsByEmail(@PathVariable("emailId") String emailId){
        return userService.getUserDetailsByEmail(emailId);
    }

    }

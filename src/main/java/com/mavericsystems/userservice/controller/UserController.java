package com.mavericsystems.userservice.controller;

import com.mavericsystems.userservice.dto.UserDto;
import com.mavericsystems.userservice.exception.CustomCreateUserException;
import com.mavericsystems.userservice.model.User;
import com.mavericsystems.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize){
       return new ResponseEntity<>(userService.getUsers(page, pageSize),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user){
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new CustomCreateUserException("Syntax Error");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody User user, @PathVariable("userId") String userId) {
        return new ResponseEntity<>(userService.updateUser(user, userId), HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return new ResponseEntity<> (userService.deleteUser(userId), HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.FOUND);
    }


    }

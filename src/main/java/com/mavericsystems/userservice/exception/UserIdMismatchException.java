package com.mavericsystems.userservice.exception;

public class UserIdMismatchException extends RuntimeException{
    public UserIdMismatchException(String s){
        super(s);
    }
}

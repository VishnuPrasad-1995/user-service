package com.mavericsystems.userservice.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String s) {
        super(s);
    }
}

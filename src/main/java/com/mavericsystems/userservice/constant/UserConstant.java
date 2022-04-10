package com.mavericsystems.userservice.constant;

public final class UserConstant {

    private UserConstant() {
        // restrict instantiation
    }

    public static final String DELETE_USER = "User successfully deleted";
    public static final String USER_NOT_FOUND = "User not found with id : ";
    public static final String NO_USER_FOUND = "No user data available : ";
    public static final String EMAIL_ALREADY_EXIST = "Email Already exist : ";
    public static final String USERID_MISMATCH = "Id passed in url and request body does not match";
}
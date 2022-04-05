package com.mavericsystems.userservice.constant;

public final class UserConstant {

    private UserConstant() {
        // restrict instantiation
    }

    public static final String DELETEUSER = "User successfully deleted"  ;
    public static final String USERNOTFOUND = "User not found with id : ";
    public static final String NOUSERFOUND = "No user data available : ";
    public static final String EMAILALREADYEXIST = "Email Already exist : ";
    public static final String USERIDMISMATCH= "Id passes in url and request body does not match";
}
package com.nhnacademy.shoppingmall.user.exception;

public class UserNotEnoughPointException extends RuntimeException {
    public UserNotEnoughPointException(String userId) {
        super("User " + userId + " does not have enough point");
    }
}

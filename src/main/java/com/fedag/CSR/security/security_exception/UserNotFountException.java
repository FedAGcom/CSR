package com.fedag.CSR.security.security_exception;

public class UserNotFountException extends Throwable {
    public UserNotFountException(String s) {
        super("User isn't found.");
    }
}

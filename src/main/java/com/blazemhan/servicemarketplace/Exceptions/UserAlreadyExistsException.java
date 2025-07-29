package com.blazemhan.servicemarketplace.Exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

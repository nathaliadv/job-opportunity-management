package com.nadantas.jobopportunitymanagement.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("User not found.");
    }
}

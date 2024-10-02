package com.nadantas.jobopportunitymanagement.exceptions;

public class UserFoundExceptions extends RuntimeException {

    public UserFoundExceptions() {
        super("Username and/or email already registered.");
    }
}

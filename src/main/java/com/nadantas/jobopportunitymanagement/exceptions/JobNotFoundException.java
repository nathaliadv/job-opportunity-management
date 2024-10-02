package com.nadantas.jobopportunitymanagement.exceptions;

public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException() {
        super("Job not found.");
    }
}

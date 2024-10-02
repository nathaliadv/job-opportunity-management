package com.nadantas.jobopportunitymanagement.exceptions;

public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException() {
        super("Company not found.");
    }
}

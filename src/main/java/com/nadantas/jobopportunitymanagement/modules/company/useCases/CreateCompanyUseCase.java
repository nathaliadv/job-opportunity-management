package com.nadantas.jobopportunitymanagement.modules.company.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.UserFoundExceptions;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Company;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Company execute(Company company) {
        this.companyRepository
                .findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent(comp -> {
                    throw new UserFoundExceptions();
                });
        String password = passwordEncoder.encode(company.getPassword());
        company.setPassword(password);
        return this.companyRepository.save(company);
    }
}

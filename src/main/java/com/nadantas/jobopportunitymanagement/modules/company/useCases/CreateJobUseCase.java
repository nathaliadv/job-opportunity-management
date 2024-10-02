package com.nadantas.jobopportunitymanagement.modules.company.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.CompanyNotFoundException;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Job;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.CompanyRepository;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    CompanyRepository companyRepository;

    public Job execute(Job job) {
        companyRepository.findById(job.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundException();
        });
        return jobRepository.save(job);
    }
}

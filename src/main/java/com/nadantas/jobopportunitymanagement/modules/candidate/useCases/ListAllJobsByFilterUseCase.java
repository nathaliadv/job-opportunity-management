package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.modules.candidate.dto.JobResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Job;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobResponseDTO> execute(String value) {
        List<Job> jobList =  this.jobRepository.findByDescriptionContainingIgnoreCase(value);
        return jobList.stream().map( job -> createJobDTO(job)).toList();
    }

    private JobResponseDTO createJobDTO(Job jobEntity) {
        return JobResponseDTO.builder()
                .description(jobEntity.getDescription())
                .benefits(jobEntity.getBenefits())
                .level(jobEntity.getLevel())
                .company_name(jobEntity.getCompany().getName())
                .company_description(jobEntity.getCompany().getDescription())
                .company_website(jobEntity.getCompany().getWebsite())
                .build();
    }
}

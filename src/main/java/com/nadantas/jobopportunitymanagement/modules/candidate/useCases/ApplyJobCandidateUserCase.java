package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.JobNotFoundException;
import com.nadantas.jobopportunitymanagement.exceptions.UserNotFoundException;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.JobApplication;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.CandidateRepository;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.JobApplicationRepository;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUserCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplication execute(UUID candidateId, UUID jobId) {
        validateCandidate(candidateId);
        validateJob(jobId);
        JobApplication jobApplication = JobApplication.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .build();
        return this.jobApplicationRepository.save(jobApplication);
    }

    private void validateCandidate(UUID candidateId) {
        this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> {
                   throw new UserNotFoundException();
                });
    }

    private void validateJob(UUID jobId) {
        this.jobRepository.findById(jobId)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });
    }
}

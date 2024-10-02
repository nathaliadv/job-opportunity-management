package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.JobNotFoundException;
import com.nadantas.jobopportunitymanagement.exceptions.UserNotFoundException;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.JobApplication;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.CandidateRepository;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.JobApplicationRepository;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Job;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUserCaseTest {

    @InjectMocks
    private ApplyJobCandidateUserCase applyJobCandidateUseCase;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Test
    @DisplayName("Should throw exception when candidate not exists")
    void shouldThrowExceptionWhenCandidateNotExists() {
        UUID candidateUUID = UUID.randomUUID();
        UUID jobUUID = UUID.randomUUID();

        try {
            applyJobCandidateUseCase.execute(candidateUUID, jobUUID);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should throw exception when job not exists")
    void shouldThrowExceptionWhenJobNotExists() {
        UUID candidateUUID = UUID.randomUUID();
        UUID jobUUID = UUID.randomUUID();

        Candidate candidate = Candidate.builder().id(candidateUUID).build();
        when(candidateRepository.findById(candidateUUID)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(candidateUUID, jobUUID);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a job application")
    void shouldBeAbleToCreateAJobApplication() {
        UUID candidateUUID = UUID.randomUUID();
        UUID jobUUID = UUID.randomUUID();

        Candidate candidate = Candidate.builder().id(candidateUUID).build();
        when(candidateRepository.findById(candidateUUID)).thenReturn(Optional.of(candidate));

        Job job = Job.builder().id(jobUUID).build();
        when(jobRepository.findById(jobUUID)).thenReturn(Optional.of(job));

        JobApplication jobApplication = JobApplication.builder()
                .candidateId(candidateUUID)
                .jobId(jobUUID)
                .build();


        JobApplication jobApplicationSaved = JobApplication.builder()
                .id(UUID.randomUUID())
                .candidateId(candidateUUID)
                .jobId(jobUUID)
                .build();

        when(jobApplicationRepository.save(jobApplication)).thenReturn(jobApplicationSaved );

        JobApplication result = applyJobCandidateUseCase.execute(candidateUUID, jobUUID);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}

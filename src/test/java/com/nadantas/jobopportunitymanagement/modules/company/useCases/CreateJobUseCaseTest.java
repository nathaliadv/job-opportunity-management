package com.nadantas.jobopportunitymanagement.modules.company.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.CompanyNotFoundException;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Company;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Job;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.CompanyRepository;
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
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CreateJobUseCaseTest {

    @InjectMocks
    CreateJobUseCase createJobUseCase;
    @Mock
    JobRepository jobRepository;
    @Mock
    CompanyRepository companyRepository;

    @Test
    @DisplayName("Should create a new job")
    void shouldCreateANewJob() {
        UUID jobId = UUID.randomUUID();
        doReturn(Optional.of(createCompany(jobId))).when(companyRepository).findById(jobId);
        Job job = createJob(jobId);
        doReturn(job).when(jobRepository).save(job);

        Job result = createJobUseCase.execute(job);

        assert result != null;
        assert result.getCompanyId() == jobId;
    }

    @Test
    @DisplayName("Should return an exception when company doesn't exist")
    void shouldReturnAnExceptionWhenCompanyDoesNotExist() {
        UUID jobId = UUID.randomUUID();
        doReturn(Optional.empty()).when(companyRepository).findById(jobId);

        try {
            createJobUseCase.execute(createJob(jobId));
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CompanyNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("Company not found.");
        }
    }

    private Job createJob(UUID id) {
        return Job.builder()
                .description("Job Description")
                .level("Job level")
                .benefits("benefit1; benefit2;")
                .companyId(id)
                .build();
    }

    private Company createCompany(UUID id) {
        return Company.builder()
                .id(id)
                .name("Company Test")
                .username("company_test")
                .email("company-test@email.com")
                .password("1234@abc1234")
                .website("companytest.com")
                .description("Tech Company Solutions")
                .build();
    }
}

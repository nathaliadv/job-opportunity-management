package com.nadantas.jobopportunitymanagement.modules.company.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.UserFoundExceptions;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Company;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CreateCompanyUseCaseTest {

    @InjectMocks
    CreateCompanyUseCase createCompanyUseCase;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should create a new company if it doesn't exist")
    void shouldCreateANewCompanyIfItDoesNotExist() {
        doReturn("encoded_password").when(passwordEncoder).encode("1234@abc1234");
        Company company = createCompany();
        doReturn(company).when(companyRepository).save(company);

        Company response = createCompanyUseCase.execute(company);

        assert response != null;
        assert response.getName() == "Company Test";
    }

    @Test
    @DisplayName("Should return an exception if company already exists")
    void shouldReturnAnExceptionIfCompanyAlreadyExists() {
        Company existingCompany = createCompany();
        doReturn(Optional.of(existingCompany)).when(companyRepository)
                .findByUsernameOrEmail(existingCompany.getUsername(), existingCompany.getEmail());

        try {
            createCompanyUseCase.execute(createCompany());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserFoundExceptions.class);
            assertThat(e.getMessage()).isEqualTo("Username and/or email already registered.");
        }
    }

    private Company createCompany() {
        return Company.builder()
                .name("Company Test")
                .username("company_test")
                .email("company-test@email.com")
                .password("1234@abc1234")
                .website("companytest.com")
                .description("Tech Company Solutions")
                .build();
    }
}

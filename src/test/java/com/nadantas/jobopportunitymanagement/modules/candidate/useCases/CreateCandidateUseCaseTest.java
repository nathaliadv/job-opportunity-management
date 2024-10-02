package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.UserFoundExceptions;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.CandidateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CreateCandidateUseCaseTest {

    @InjectMocks
    CreateCandidateUseCase createCandidateUseCase;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should create a new candidate")
    void shouldCreateANewCandidate() {
        doReturn("encoded_password").when(passwordEncoder).encode("12345678abc");
        Candidate candidate = createCandidate();
        doReturn(candidate).when(candidateRepository).save(candidate);

        Candidate response = createCandidateUseCase.execute(candidate);

        assertThat(response).hasFieldOrProperty("id");
        assertEquals("Candidate Name", response.getName());
        assertEquals("encoded_password", response.getPassword());
    }

    @Test
    @DisplayName("Should return an exception when try to create a candidate that already exists")
    void shouldReturnAnExceptionWhenTryToCreateACandidateThatAlreadyExists() {
        Candidate existingCandidate = createCandidate();
        doReturn(Optional.of(existingCandidate))
                .when(candidateRepository)
                .findByUsernameOrEmail(existingCandidate.getUsername(), existingCandidate.getEmail());

        try {
            createCandidateUseCase.execute(createCandidate());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserFoundExceptions.class);
        }
    }

    private Candidate createCandidate() {
        return Candidate.builder()
                .id(UUID.randomUUID())
                .name("Candidate Name")
                .username("candidate_username")
                .email("candidate@email.com")
                .password("12345678abc")
                .description("Candidate Description Test")
                .build();
    }
}

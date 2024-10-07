package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.UserNotFoundException;
import com.nadantas.jobopportunitymanagement.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.CandidateRepository;
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
public class ProfileCandidateUseCaseTest {

    @InjectMocks
    ProfileCandidateUseCase profileCandidateUseCase;
    @Mock
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Should return a profile candidate if the candidate exists")
    void shouldReturnAProfileCandidateIfTheCandidateExists() {
        UUID id = UUID.randomUUID();
        Candidate existingCandidate = createCandidate(id);
        doReturn(Optional.of(existingCandidate)).when(candidateRepository).findById(id);

        ProfileCandidateResponseDTO response = profileCandidateUseCase.execute(id);

        assert response != null;
        assert response.id() == id;
        assert response.name() == "Candidate Name";
    }

    @Test
    @DisplayName("Should return an exception if the candidate doesn't exist")
    void shouldReturnAnExceptionIfDoesNotExist() {
        UUID id = UUID.randomUUID();
        Candidate existingCandidate = createCandidate(id);
        doReturn(Optional.empty()).when(candidateRepository).findById(id);

        try {
            profileCandidateUseCase.execute(id);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("User not found.");
        }
    }

    private Candidate createCandidate(UUID id) {
        return Candidate.builder()
                .id(id)
                .name("Candidate Name")
                .username("candidate_username")
                .email("candidate@email.com")
                .password("12345678abc")
                .description("Candidate Description Test")
                .build();
    }
}

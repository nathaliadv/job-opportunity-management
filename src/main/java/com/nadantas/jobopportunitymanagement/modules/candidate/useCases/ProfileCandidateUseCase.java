package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.UserNotFoundException;
import com.nadantas.jobopportunitymanagement.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        Candidate candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        return ProfileCandidateResponseDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .description(candidate.getDescription())
                .build();
    }
}

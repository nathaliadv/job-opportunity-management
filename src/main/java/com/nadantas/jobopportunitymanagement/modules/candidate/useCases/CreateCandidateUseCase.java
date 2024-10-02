package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.exceptions.UserFoundExceptions;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.CandidateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Candidate execute(Candidate candidate) {
        this.candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundExceptions();
                });
        String password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);
        return this.candidateRepository.save(candidate);
    }
}

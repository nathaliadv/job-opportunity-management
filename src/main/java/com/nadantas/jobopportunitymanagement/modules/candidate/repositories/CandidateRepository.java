package com.nadantas.jobopportunitymanagement.modules.candidate.repositories;

import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    Optional<Candidate> findByUsernameOrEmail(String username, String email);

    Optional<Candidate> findByUsername(String username);
}

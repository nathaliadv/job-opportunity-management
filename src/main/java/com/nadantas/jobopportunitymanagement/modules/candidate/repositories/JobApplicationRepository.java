package com.nadantas.jobopportunitymanagement.modules.candidate.repositories;

import com.nadantas.jobopportunitymanagement.modules.candidate.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
}

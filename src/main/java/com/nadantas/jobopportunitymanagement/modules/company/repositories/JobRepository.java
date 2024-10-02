package com.nadantas.jobopportunitymanagement.modules.company.repositories;

import com.nadantas.jobopportunitymanagement.modules.company.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    // Containing - "LIKE" --> ... like %value%
    List<Job> findByDescriptionContainingIgnoreCase(String value);
}

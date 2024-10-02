package com.nadantas.jobopportunitymanagement.modules.company.controllers;

import com.nadantas.jobopportunitymanagement.modules.candidate.dto.JobResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.company.dto.JobDTO;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Job;
import com.nadantas.jobopportunitymanagement.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
@Tag(name = "Vacancies", description = "Information about vacancies")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary = "Job registration", description = "Register open vacancies in a authenticated company")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration created successfully!")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody JobDTO jobdto, HttpServletRequest request) {
        try {
            UUID companyId = UUID.fromString(request.getAttribute("id").toString());
            Job result = createJobUseCase.execute(createJobEntity(jobdto, companyId));
            return ResponseEntity.ok("Registration created successfully! Id: " + result.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Job createJobEntity(JobDTO jobdto, UUID companyId) {
        return Job.builder()
                .description(jobdto.description())
                .level(jobdto.level())
                .benefits(jobdto.benefits())
                .companyId(companyId)
                .build();
    }
}

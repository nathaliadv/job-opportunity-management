package com.nadantas.jobopportunitymanagement.modules.candidate.controllers;

import com.nadantas.jobopportunitymanagement.modules.candidate.dto.CandidateDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.dto.JobResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.JobApplication;
import com.nadantas.jobopportunitymanagement.modules.candidate.useCases.ApplyJobCandidateUserCase;
import com.nadantas.jobopportunitymanagement.modules.candidate.useCases.CreateCandidateUseCase;
import com.nadantas.jobopportunitymanagement.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import com.nadantas.jobopportunitymanagement.modules.candidate.useCases.ProfileCandidateUseCase;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Related to the candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;
    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;
    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;
    @Autowired
    private ApplyJobCandidateUserCase applyJobCandidateUserCase;

    @PostMapping("/")
    @Operation(summary = "Candidate registration", description = "Register candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration created successfully!"),
            @ApiResponse(responseCode = "400", description = "Username and/or email already registered.")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateDTO candidateDTO) {
        try {
            Candidate response = createCandidateUseCase.execute(createCandidateEntity(candidateDTO));
            return ResponseEntity.ok("Registration created successfully! id: " + response.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Information about the authenticated candidate", description = "List some information about the authenticated candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> find(HttpServletRequest request) {
        try {
            UUID candidateId = UUID.fromString(request.getAttribute("id").toString());
            ProfileCandidateResponseDTO profile = profileCandidateUseCase.execute(candidateId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "List of vacancies available for candidate", description = "List all vacancies based on the parameter passed in the request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobResponseDTO.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobResponseDTO> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Apply for a job", description = "Create an application for a vacancy")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The application 'id' was made successfully! Company 'name' will contact you"),
            @ApiResponse(responseCode = "400", description = "Candidate not found."),
            @ApiResponse(responseCode = "400", description = "Job not found.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
        try {
            UUID candidateId = UUID.fromString(request.getAttribute("id").toString());
            JobApplication response = applyJobCandidateUserCase.execute(candidateId, jobId);
            return ResponseEntity.ok(String.format("The application %s was made successfully!", response.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Candidate createCandidateEntity(CandidateDTO candidateDTO) {
        return Candidate.builder()
                .name(candidateDTO.name())
                .username(candidateDTO.username())
                .password(candidateDTO.password())
                .email(candidateDTO.email())
                .description(candidateDTO.description())
                .curriculum(candidateDTO.curriculum())
                .build();
    }
}

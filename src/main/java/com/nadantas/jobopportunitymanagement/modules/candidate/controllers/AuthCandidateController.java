package com.nadantas.jobopportunitymanagement.modules.candidate.controllers;

import com.nadantas.jobopportunitymanagement.modules.candidate.dto.AuthCandidateRequestDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.dto.AuthCandidateResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Related to the candidate")
public class AuthCandidateController {

    @Autowired
    AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    @Operation(summary = "Authenticate candidate", description = "Generate authentication token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Username/password incorrect"),
    })
    public ResponseEntity<Object> create(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            AuthCandidateResponseDTO auth = authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok(auth);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

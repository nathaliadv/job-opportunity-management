package com.nadantas.jobopportunitymanagement.modules.company.controllers;

import com.nadantas.jobopportunitymanagement.modules.company.dto.CompanyDTO;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Company;
import com.nadantas.jobopportunitymanagement.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Related to the company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    @Operation(summary = "Company registration", description = "Register company")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration created successfully!"),
            @ApiResponse(responseCode = "400", description = "Username and/or email already registered.")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyDTO companyDTO) {
        try {
            Company response = createCompanyUseCase.execute(createCompanyEntity(companyDTO));
            return ResponseEntity.ok("Registration created successfully! Id: " + response.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Company createCompanyEntity(CompanyDTO companyDTO) {
        return Company.builder()
                .name(companyDTO.name())
                .username(companyDTO.username())
                .password(companyDTO.password())
                .email(companyDTO.email())
                .description(companyDTO.description())
                .website(companyDTO.website())
                .build();
    }
}

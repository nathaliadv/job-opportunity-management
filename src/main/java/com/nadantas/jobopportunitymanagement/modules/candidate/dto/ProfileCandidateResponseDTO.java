package com.nadantas.jobopportunitymanagement.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProfileCandidateResponseDTO(
        UUID id,
        @Schema(example = "John Smith")
        String name,
        @Schema(example = "john_smith")
        String username,
        @Schema(example = "john_smith@gmail.com")
        String email,
        @Schema(example = "Junior Java Developer with 5 years of experience, proficient in building and maintaining Java-based applications.")
        String description
) {
}

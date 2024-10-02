package com.nadantas.jobopportunitymanagement.modules.candidate.dto;

import lombok.Builder;

@Builder
public record AuthCandidateRequestDTO(
        String password,
        String username
) {
}

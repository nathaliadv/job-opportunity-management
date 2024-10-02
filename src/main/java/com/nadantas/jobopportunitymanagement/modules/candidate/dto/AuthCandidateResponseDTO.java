package com.nadantas.jobopportunitymanagement.modules.candidate.dto;

import lombok.Builder;

@Builder
public record AuthCandidateResponseDTO(
        String access_token,
        Long expires_in
) {
}

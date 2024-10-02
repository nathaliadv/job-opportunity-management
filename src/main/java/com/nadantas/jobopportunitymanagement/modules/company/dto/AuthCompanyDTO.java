package com.nadantas.jobopportunitymanagement.modules.company.dto;

import lombok.Builder;

@Builder
public record AuthCompanyDTO(
        String password,
        String username
) {
}

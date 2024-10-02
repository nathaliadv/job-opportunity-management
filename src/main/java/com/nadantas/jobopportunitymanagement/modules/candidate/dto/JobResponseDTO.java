package com.nadantas.jobopportunitymanagement.modules.candidate.dto;

import lombok.Builder;

@Builder
public record JobResponseDTO(
        String description,
        String level,
        String benefits,
        String company_name,
        String company_website,
        String company_description
) {
}

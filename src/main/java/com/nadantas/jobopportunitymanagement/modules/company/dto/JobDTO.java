package com.nadantas.jobopportunitymanagement.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record JobDTO(
        @Schema(example = "Java Software Developer", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "The description can't be null.")
        String description,

        @Schema(example = "Junior", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "The level can't be null.")
        String level,

        @Schema(example = "Health insurance, gym memberships, paid time off, mentorship", requiredMode = Schema.RequiredMode.REQUIRED)
        String benefits
) {
}

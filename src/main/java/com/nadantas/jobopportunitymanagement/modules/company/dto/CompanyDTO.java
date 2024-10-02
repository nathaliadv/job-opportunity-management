package com.nadantas.jobopportunitymanagement.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CompanyDTO(
        @Schema(example = "NexaCore Tech Solutions")
        String name,

        @Schema(description = "The username must not contain whitespace.", example = "nexa-rh-admin")
        @Pattern(regexp = "\\S+", message = "The value can't contain whitespace.")
        String username,

        @Schema(description = "The field must contain a valid email address.", example = "rh-nexa@nexacore.com")
        @Email(message = "The field must contain a valid email address.")
        String email,

        @Schema(description = "The password must contain between 8.", example = "291jdi@#")
        @Length(min = 8, message = "The password must contain between 8.")
        String password,

        @Schema(example = "NexaCore Solutions is a company specializing in innovative software solutions for businesses of all sizes.")
        String description,

        @Schema(example = "nexacoretech.com")
        String website
) {
}

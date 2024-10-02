package com.nadantas.jobopportunitymanagement.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CandidateDTO(
        @Schema(example = "John Smith")
        String name,

        @Schema(description = "The username must not contain whitespace.", example = "john_smith")
        @Pattern(regexp = "\\S+", message = "The value can't contain whitespace.")
        String username,

        @Schema(description = "The field must contain a valid email address.", example = "john_smith@gmail.com")
        @Email(message = "The field must contain a valid email address.")
        String email,

        @Schema(description = "The password must contain between 8.", example = "291jdi@#")
        @Length(min = 8, message = "The password must contain between 8.")
        String password,

        @Schema(example = "Junior Java Developer with 5 years of experience, proficient in building and maintaining Java-based applications.")
        String description,

        String curriculum
) {
}

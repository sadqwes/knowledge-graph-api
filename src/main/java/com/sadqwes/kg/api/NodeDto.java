package com.sadqwes.kg.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Input validation DTO: prevents invalid data from reaching the domain
// layer and blocks common injection payloads (oversized strings, empty names).
public record NodeDto(
        @NotBlank(message = "name is required")
        @Size(max = 200, message = "name too long")
        String name,

        @Size(max = 2000, message = "description too long")
        String description,

        @Size(max = 500, message = "tags too long")
        String tags
) {}

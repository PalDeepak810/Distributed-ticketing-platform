package com.dtp.theatre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateScreenRequest {
    @NotBlank(message = "Screen name is required")
    private String name;

    @NotNull(message = "Theatre id is required")
    private Long theatreId;
}

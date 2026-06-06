package com.dtp.theatre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateScreenRequest {
    private String name;

    private Long theatreId;
}

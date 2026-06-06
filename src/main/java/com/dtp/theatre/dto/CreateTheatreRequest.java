package com.dtp.theatre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTheatreRequest {
    private String name;

    private String city;

    private String address;
}

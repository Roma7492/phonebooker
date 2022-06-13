package com.romanshilov.phoneBooker.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingRequest {

    @JsonProperty("phoneName")
    private String phoneName;

    @JsonProperty("userName")
    private String userName;
}

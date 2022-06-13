package com.romanshilov.phoneBooker.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.romanshilov.phoneBooker.domain.dto.fonoapi.DeviceEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("phoneName")
    private String phoneName;

    @JsonProperty("isBooked")
    private Boolean isBooked;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("bookingDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime bookingDate;

    @JsonProperty("technology")
    private String technology;

    @JsonProperty("_2g_bands")
    private String _2gBands;

    @JsonProperty("_3g_bands")
    private String _3gBands;

    @JsonProperty("_4g_bands")
    private String _4gBands;


}

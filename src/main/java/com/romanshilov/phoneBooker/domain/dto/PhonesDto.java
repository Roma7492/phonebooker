package com.romanshilov.phoneBooker.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PhonesDto {

    @JsonProperty("phones")
    private List<PhoneDto> phones;
}

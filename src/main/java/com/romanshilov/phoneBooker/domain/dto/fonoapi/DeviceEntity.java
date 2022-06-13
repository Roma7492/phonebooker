package com.romanshilov.phoneBooker.domain.dto.fonoapi;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeviceEntity {

    @JsonProperty("DeviceName")
    private String deviceName;
    @JsonProperty("technology")
    private String technology;
    @JsonProperty("_2g_bands")
    private String _2gBands;
    @JsonProperty("_3g_bands")
    private String _3gBands;
    @JsonProperty("_4g_bands")
    private String _4gBands;
}

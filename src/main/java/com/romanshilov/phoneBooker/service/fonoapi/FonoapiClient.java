package com.romanshilov.phoneBooker.service.fonoapi;

import com.romanshilov.phoneBooker.domain.dto.fonoapi.DeviceEntity;
import com.romanshilov.phoneBooker.exception.FonoapiClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FonoapiClient {

    private static final String DEVICE = "device";
    private static final String TOKEN = "token";

    @Value("${fonoapi.url}")
    private String fonoapiUrl;

    @Value("${fonoapi.token}")
    private String fonoapiTokenUri;

    @Value("${fonoapi.device}")
    private String fonoapiDevice;

    public DeviceEntity getDevice(final String deviceName) {
        final String token = getFonoapiToken();
        try {
            final List<DeviceEntity> deviceEntities = getWebClient()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .pathSegment(fonoapiDevice)
                            .queryParam(DEVICE, deviceName)
                            .queryParam(TOKEN, token)
                            .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<DeviceEntity>>() {})
                    .block();

            if (deviceEntities == null) {
                log.error("Device response is null");
                throw new FonoapiClientException("Device response is null");
            }

            Optional<DeviceEntity> deviceEntityOptional = deviceEntities.stream()
                    .filter(de -> de.getDeviceName().equalsIgnoreCase(deviceName))
                    .findFirst();

            if (deviceEntityOptional.isEmpty()) {
                log.error("Device entity with required name is absent in response from fonoapi");
                throw new FonoapiClientException("Device entity with required name is absent in response from fonoapi");
            }

            return deviceEntityOptional.get();

        } catch (Exception e) {
            log.error("Device request to fonoapi failed");
            throw new FonoapiClientException("Device request to fonoapi failed");
        }
    }

    private String getFonoapiToken() {
        try {
            return getWebClient()
                    .get()
                    .uri(fonoapiTokenUri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            log.error("Request for fonoapi token failed");
            throw new FonoapiClientException("Request for fonoapi token failed");
        }
    }


    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(fonoapiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


}

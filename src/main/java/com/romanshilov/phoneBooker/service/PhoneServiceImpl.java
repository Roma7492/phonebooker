package com.romanshilov.phoneBooker.service;

import com.romanshilov.phoneBooker.domain.dto.BookingRequest;
import com.romanshilov.phoneBooker.domain.dto.PhoneDto;
import com.romanshilov.phoneBooker.domain.dto.PhonesDto;
import com.romanshilov.phoneBooker.domain.dto.fonoapi.DeviceEntity;
import com.romanshilov.phoneBooker.domain.entity.FonoapiBackupEntity;
import com.romanshilov.phoneBooker.domain.entity.PhoneEntity;
import com.romanshilov.phoneBooker.exception.BookingException;
import com.romanshilov.phoneBooker.exception.FonoapiClientException;
import com.romanshilov.phoneBooker.repository.FonoapiBackupRepository;
import com.romanshilov.phoneBooker.repository.PhoneRepository;
import com.romanshilov.phoneBooker.service.fonoapi.FonoapiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final FonoapiBackupRepository fonoapiBackupRepository;
    private final FonoapiClient fonoapiClient;

    @Override
    public PhonesDto getAllPhones() {
        final List<PhoneDto> phoneDtos = phoneRepository.findAll().stream()
                .map(this::mapPhoneToDto)
                .collect(Collectors.toList());
        enrichPhoneDtosWithFonoapiInfo(phoneDtos);
        return PhonesDto.builder().phones(phoneDtos).build();
    }

    private void enrichPhoneDtosWithFonoapiInfo(List<PhoneDto> phoneDtos) {
        phoneDtos.forEach(this::enrichPhoneDtoWithFonoapiInfo);
    }

    private void enrichPhoneDtoWithFonoapiInfo(final PhoneDto phoneDto) {
        try {
            final DeviceEntity deviceEntity = fonoapiClient.getDevice(phoneDto.getPhoneName());
            phoneDto.set_2gBands(deviceEntity.get_2gBands());
            phoneDto.set_3gBands(deviceEntity.get_3gBands());
            phoneDto.set_4gBands(deviceEntity.get_4gBands());
            phoneDto.setTechnology(deviceEntity.getTechnology());
        } catch (FonoapiClientException e) {
            final FonoapiBackupEntity fonoapiBackupEntity = fonoapiBackupRepository
                    .findByPhoneName(phoneDto.getPhoneName());
            phoneDto.set_2gBands(fonoapiBackupEntity.get_2gBands());
            phoneDto.set_3gBands(fonoapiBackupEntity.get_3gBands());
            phoneDto.set_4gBands(fonoapiBackupEntity.get_4gBands());
            phoneDto.setTechnology(fonoapiBackupEntity.getTechnology());
        }
    }

    @Override
    @Transactional
    public PhoneDto bookPhone(BookingRequest request) {
        Optional<List<PhoneEntity>> vacantPhones = phoneRepository
                .findPhoneEntitiesByPhoneNameAndIsBooked(request.getPhoneName().toLowerCase(Locale.ROOT), false);

        if (vacantPhones.isEmpty()) {
            log.error("No such vacant phone");
            throw new NotFoundException("No such vacant phone");
        }

        Optional<PhoneEntity> firstVacantPhone = vacantPhones.get().stream().findFirst();

        if (firstVacantPhone.isPresent()) {
            final PhoneDto phoneDto = mapPhoneToDto(bookPhone(firstVacantPhone.get(), request.getUserName()));
            enrichPhoneDtoWithFonoapiInfo(phoneDto);
            return phoneDto;
        } else {
            log.error("No such vacant phone");
            throw new NotFoundException("No such vacant phone");
        }
    }

    private PhoneEntity bookPhone(final PhoneEntity entity, final String userName) {
        entity.setIsBooked(true);
        entity.setUserName(userName);
        entity.setBookingDate(LocalDateTime.now());
        return phoneRepository.save(entity);
    }

    @Override
    public void returnPhone(BookingRequest request) {
        Optional<List<PhoneEntity>> phoneEntityListOptional = phoneRepository.findPhoneEntitiesByPhoneNameAndIsBookedAndUserName(
                request.getPhoneName(), true, request.getUserName());
        if (phoneEntityListOptional.isEmpty() || phoneEntityListOptional.get().isEmpty()) {
            log.error("No {} was booked by {}", request.getPhoneName(), request.getUserName());
            throw new BookingException(
                    String.format("No %s was booked by %s", request.getPhoneName(), request.getPhoneName())
            );
        }

        final PhoneEntity phoneEntity = phoneEntityListOptional.get().stream().findFirst().get();
        phoneEntity.setBookingDate(null);
        phoneEntity.setUserName(null);
        phoneEntity.setIsBooked(false);
        phoneRepository.save(phoneEntity);

    }

    private PhoneDto mapPhoneToDto(PhoneEntity entity) {
        return PhoneDto.builder()
                .id(entity.getId())
                .phoneName(entity.getPhoneName())
                .isBooked(entity.getIsBooked())
                .userName(entity.getUserName())
                .bookingDate(entity.getBookingDate())
                .build();
    }
}

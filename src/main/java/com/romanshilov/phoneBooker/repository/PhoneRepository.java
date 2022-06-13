package com.romanshilov.phoneBooker.repository;

import com.romanshilov.phoneBooker.domain.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {

    Optional<List<PhoneEntity>> findPhoneEntitiesByPhoneNameAndIsBooked(final String name, final Boolean isBooked);
    Optional<List<PhoneEntity>> findPhoneEntitiesByPhoneNameAndIsBookedAndUserName(
            final String name, final Boolean isBooked, final String userName);
}

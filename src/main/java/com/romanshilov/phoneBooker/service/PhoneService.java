package com.romanshilov.phoneBooker.service;

import com.romanshilov.phoneBooker.domain.dto.BookingRequest;
import com.romanshilov.phoneBooker.domain.dto.PhoneDto;
import com.romanshilov.phoneBooker.domain.dto.PhonesDto;

public interface PhoneService {

    PhonesDto getAllPhones();

    PhoneDto bookPhone(final BookingRequest request);

    void returnPhone(final BookingRequest request);
}

package com.romanshilov.phoneBooker.controller;

import com.romanshilov.phoneBooker.domain.dto.BookingRequest;
import com.romanshilov.phoneBooker.domain.dto.PhoneDto;
import com.romanshilov.phoneBooker.domain.dto.PhonesDto;
import com.romanshilov.phoneBooker.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/phone")
@Slf4j
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping("/all")
    public ResponseEntity<PhonesDto> getPhones() {
        return new ResponseEntity<>(phoneService.getAllPhones(), HttpStatus.OK);
    }

    @PatchMapping("/book")
    public ResponseEntity<PhoneDto> bookPhone(@RequestBody BookingRequest request) {
        try {
            final PhoneDto phoneDto = phoneService.bookPhone(request);
            return new ResponseEntity<>(phoneDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/return")
    public ResponseEntity<HttpStatus> returnPhone(@RequestBody BookingRequest request) {
        try {
            phoneService.returnPhone(request);
        } catch (Exception e) {
            log.error("Return request failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}

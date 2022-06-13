package com.romanshilov.phoneBooker.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "phones")
@Data
@EqualsAndHashCode
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_name")
    private String phoneName;

    @Column(name = "is_booked")
    private Boolean isBooked;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;
}

package com.heavenlylanka.heavenlylanka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "bookings")
public class Booking {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String packageId;

    @Column(nullable = false)
    private Date travelDates;

    @Column(nullable = false)
    private int adults;

    @Column(nullable = false)
    private int children;

    @Column
    private String specialRequests;

}

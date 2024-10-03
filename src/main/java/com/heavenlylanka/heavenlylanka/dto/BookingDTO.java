package com.heavenlylanka.heavenlylanka.dto;

import lombok.Data;

@Data
public class BookingDTO {
    private String fullName;
    private String email;
    private String phoneNumber;

    private String apartment;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    private String packageId;
    private String travelDates;

    private int adults;
    private int children;

    private String specialRequests;

    private boolean termsAccepted;
}

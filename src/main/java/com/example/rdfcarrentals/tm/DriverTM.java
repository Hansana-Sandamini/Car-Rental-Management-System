package com.example.rdfcarrentals.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverTM {
    private String nic;
    private String name;
    private String email;
    private String availabilityStatus;
    private String contactNumber;
    private Double pricePerKm;
}

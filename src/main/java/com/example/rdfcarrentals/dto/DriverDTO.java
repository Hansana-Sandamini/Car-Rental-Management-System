package com.example.rdfcarrentals.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DriverDTO {
    private String nic;
    private String name;
    private String email;
    private String availabilityStatus;
    private String contactNumber;
}

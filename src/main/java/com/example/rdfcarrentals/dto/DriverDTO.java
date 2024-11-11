package com.example.rdfcarrentals.dto;

import lombok.*;

import java.util.ArrayList;

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
    private Double pricePerKm;

    private ArrayList<DriverAssignmentDTO> driverAssignmentDTOS;

    public DriverDTO(String nic, String name, String email, String availabilityStatus, String contactNumber, Double pricePerKm) {
        this.nic = nic;
        this.name = name;
        this.email = email;
        this.availabilityStatus = availabilityStatus;
        this.contactNumber = contactNumber;
        this.pricePerKm = pricePerKm;
    }
}

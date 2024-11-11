package com.example.rdfcarrentals.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverAssignmentDTO {
    private String licensePlateNo;
    private String driverNic;
    private Double pricePerKm;

}

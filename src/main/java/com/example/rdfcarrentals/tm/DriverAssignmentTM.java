package com.example.rdfcarrentals.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverAssignmentTM {
    private String licensePlateNo;
    private String driverNic;
    private Double pricePerKm;
}

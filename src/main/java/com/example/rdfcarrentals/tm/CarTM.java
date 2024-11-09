package com.example.rdfcarrentals.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarTM {
    private String licensePlateNo;
    private String typeId;
    private String model;
    private String colour;
    private double dailyRate;
    private double monthlyRate;
    private String availabilityStatus;
}

package com.example.rdfcarrentals.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private String licensePlateNo;
    private String model;
    private String colour;
    private double dailyRate;
    private double monthlyRate;
    private String availabilityStatus;
    private String typeId;

    public CarDTO(String licensePlateNo, String model) {
        this.licensePlateNo = licensePlateNo;
        this.model = model;
    }
}

package com.example.rdfcarrentals.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverAssignmentDTO {
    private String licensePlateNo;
    private String driverNic;
    private Double pricePerKm;
    private Date date;
    private String time;
}

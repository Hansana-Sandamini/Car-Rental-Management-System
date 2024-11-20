package com.example.rdfcarrentals.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverAssignmentTM {
    private String licensePlateNo;
    private String driverNic;
    private Double pricePerKm;
    private Date date;
    private String time;
}

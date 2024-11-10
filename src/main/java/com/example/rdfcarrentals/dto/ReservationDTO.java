package com.example.rdfcarrentals.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String reservationId;
    private String customerNic;
    private String cashierUsername;
    private String creditId;
    private Date pickUpDate;
    private String pickUpTime;
    private Date returnDate;
    private String returnTime;
    private String isDriverWant;
}

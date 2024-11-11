package com.example.rdfcarrentals.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetailTM {
    private String reservationId;
    private String licensePlateNo;
    private Double driverCost;
    private Double totalAmount;
}

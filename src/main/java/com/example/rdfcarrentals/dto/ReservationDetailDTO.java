package com.example.rdfcarrentals.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetailDTO {
    private String reservationId;
    private String licensePlateNo;
    private Double driverCost;
    private Double totalAmount;
}

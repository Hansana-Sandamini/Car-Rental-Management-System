package com.example.rdfcarrentals.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String paymentId;
    private String reservationId;
    private String billId;
    private String paymentMethod;
    private Double amount;
    private Date date;
    private String time;
}

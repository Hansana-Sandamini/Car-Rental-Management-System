package com.example.rdfcarrentals.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTM {
    private String paymentId;
    private String reservationId;
    private String billId;
    private String paymentMethod;
    private Double amount;
    private Date date;
    private String time;
}

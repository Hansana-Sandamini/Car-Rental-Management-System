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

    public PaymentTM(String paymentId, String reservationId, String paymentMethod, Double amount, Date date, String time) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }
}

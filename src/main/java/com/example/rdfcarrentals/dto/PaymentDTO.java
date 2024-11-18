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

    public PaymentDTO(String paymentId, String reservationId, String paymentMethod, double amount, Date date, String time) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }
}

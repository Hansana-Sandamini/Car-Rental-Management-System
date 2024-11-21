package com.example.rdfcarrentals.tm;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditTM {
    private String creditId;
    private String reservationId;
    private Double totalAmount;
    private Double amountPaid;
    private Double amountToPay;
    private Date dueDate;
}

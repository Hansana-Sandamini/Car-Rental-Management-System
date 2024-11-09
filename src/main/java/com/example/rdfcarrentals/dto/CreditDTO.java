package com.example.rdfcarrentals.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditDTO {
    private String creditId;
    private Double totalAmount;
    private Double amountPaid;
    private Double amountToPay;
    private Date dueDate;
}

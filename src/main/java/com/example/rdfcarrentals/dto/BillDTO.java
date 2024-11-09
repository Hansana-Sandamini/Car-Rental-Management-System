package com.example.rdfcarrentals.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private String billId;
    private String description;
    private Date issueDate;
}

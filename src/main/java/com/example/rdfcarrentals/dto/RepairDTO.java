package com.example.rdfcarrentals.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairDTO {
    private String repairId;
    private String description;
    private Date date;
    private Double cost;
    private String licensePlateNo;
}

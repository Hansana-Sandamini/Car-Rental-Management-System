package com.example.rdfcarrentals.tm;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairTM {
    private String repairId;
    private String description;
    private Date date;
    private Double cost;
    private String licensePlateNo;
}

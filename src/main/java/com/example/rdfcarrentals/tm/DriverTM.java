package com.example.rdfcarrentals.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DriverTM {
    private String nic;
    private String name;
    private String email;
    private String availabilityStatus;
    private String contactNumber;
}

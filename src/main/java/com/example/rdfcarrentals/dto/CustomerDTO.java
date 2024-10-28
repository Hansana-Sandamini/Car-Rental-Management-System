package com.example.rdfcarrentals.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {
    private String nic;
    private String name;
    private String address;
    private String email;
    private String contactNumber;
}

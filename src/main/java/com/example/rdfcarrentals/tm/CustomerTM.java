package com.example.rdfcarrentals.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerTM {
    private String nic;
    private String name;
    private String address;
    private String email;
    private String contactNumber;

}

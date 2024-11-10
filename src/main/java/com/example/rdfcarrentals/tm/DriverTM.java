package com.example.rdfcarrentals.tm;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverTM {
    private String nic;
    private String name;
    private String email;
    private String availabilityStatus;
    private String contactNumber;
}

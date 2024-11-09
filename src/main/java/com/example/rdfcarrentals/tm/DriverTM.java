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
    private Button btnRemove;

    public DriverTM(String nic, String name, String email, String availabilityStatus, String contactNumber) {
        this.nic = nic;
        this.name = name;
        this.email = email;
        this.availabilityStatus = availabilityStatus;
        this.contactNumber = contactNumber;
    }
}

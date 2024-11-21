package com.example.rdfcarrentals.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String reservationId;
    private String customerNic;
    private String cashierUsername;
    private Date pickUpDate;
    private String pickUpTime;
    private Date returnDate;
    private String returnTime;
    private String isDriverWant;
    private String driverNic;

    private ArrayList<ReservationDetailDTO> reservationDetailDTOS;

    public ReservationDTO(String reservationId, String customerNic, String  cashierUsername, Date pickUpDate, String pickUpTime, Date returnDate, String returnTime, String isDriverWant) {
        this.reservationId = reservationId;
        this.customerNic = customerNic;
        this.cashierUsername = cashierUsername;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.isDriverWant = isDriverWant;
    }

    public ReservationDTO(String reservationId, String customerNic, String cashierUsername, Date pickUpDate, String pickUpTime, Date returnDate, String returnTime, String isDriverWant, ArrayList<ReservationDetailDTO> reservationDetailDTOS) {
        this.reservationId = reservationId;
        this.customerNic = customerNic;
        this.cashierUsername = cashierUsername;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.isDriverWant = isDriverWant;
        this.reservationDetailDTOS = reservationDetailDTOS;
    }
}

package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.ReservationDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.SQLException;

public class ReservationModel {

    public boolean addReservation(ReservationDTO reservationDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO reservation VALUES (?,?,?,?,?,?,?,?,?)",
                reservationDTO.getReservationId(),
                reservationDTO.getCustomerNic(),
                reservationDTO.getCashierUsername(),
                reservationDTO.getCreditId(),
                reservationDTO.getPickUpDate(),
                reservationDTO.getPickUpTime(),
                reservationDTO.getReturnDate(),
                reservationDTO.getReturnTime(),
                reservationDTO.getIsDriverWant()
        );
    }
}

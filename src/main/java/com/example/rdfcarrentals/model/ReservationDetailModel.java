package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.ReservationDetailDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDetailModel {

    public boolean saveReservationDetailList(ArrayList<ReservationDetailDTO> reservationDetailDTOS) throws SQLException {
        for (ReservationDetailDTO reservationDetailDTO : reservationDetailDTOS) {
            boolean isReservationDetailSaved = saveReservationDetail(reservationDetailDTO);
            if (!isReservationDetailSaved) {
                return false;
            }
        }
        return true;
    }

    private boolean saveReservationDetail(ReservationDetailDTO reservationDetailDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO reservation_detail VALUES (?,?,?,?)",
                reservationDetailDTO.getReservationId(),
                reservationDetailDTO.getLicensePlateNo(),
                reservationDetailDTO.getDriverCost(),
                reservationDetailDTO.getTotalAmount()
        );
    }

    public ArrayList<ReservationDetailDTO> getReservationDetails() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM reservation_detail"
        );

        ArrayList<ReservationDetailDTO> reservationDetailDTOS = new ArrayList<>();

        while (resultSet.next()) {
            ReservationDetailDTO reservationDetailDTO = new ReservationDetailDTO(
                    resultSet.getString("reservation_id"),
                    resultSet.getString("license_plate_no"),
                    resultSet.getDouble("driver_cost"),
                    resultSet.getDouble("total_amount")
            );
            reservationDetailDTOS.add(reservationDetailDTO);
        }
        return reservationDetailDTOS;
    }


}

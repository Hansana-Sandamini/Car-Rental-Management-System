package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.db.DBConnection;
import com.example.rdfcarrentals.dto.ReservationDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationModel {

    private final ReservationDetailModel reservationDetailModel = new ReservationDetailModel();

    public String getNextReservationId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT reservation_id FROM reservation ORDER BY reservation_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("R%03d", newIdIndex);
        }
        return "R001";
    }

    public boolean addReservation(ReservationDTO reservationDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            boolean isReservationSaved = CrudUtil.execute(
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

            if (isReservationSaved) {
                boolean isReservationDetailListSaved = reservationDetailModel.saveReservationDetailList(reservationDTO.getReservationDetailDTOS());
                if (isReservationDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public ArrayList<ReservationDTO> getAllReservations() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reservation");

        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();

        while (rst.next()) {
            ReservationDTO reservationDTO = new ReservationDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6),
                    rst.getDate(7),
                    rst.getString(8),
                    rst.getString(9)
            );
            reservationDTOS.add(reservationDTO);
        }
        return reservationDTOS;
    }

    public ArrayList<String> getAllReservationIDS() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT reservation_id FROM reservation");

        ArrayList<String> reservationIDS = new ArrayList<>();

        while (rst.next()) {
            reservationIDS.add(rst.getString(1));
        }
        return reservationIDS;
    }

    public ReservationDTO findByReservationID(String selectedReservationID) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reservation WHERE reservation_id = ?", selectedReservationID);

        if (rst.next()) {
            return new ReservationDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6),
                    rst.getDate(7),
                    rst.getString(8),
                    rst.getString(9)
            );
        }
        return null;
    }
}

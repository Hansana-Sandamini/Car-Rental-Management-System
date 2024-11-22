package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.db.DBConnection;
import com.example.rdfcarrentals.dto.ReservationDTO;
import com.example.rdfcarrentals.dto.ReservationDetailDTO;
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
                    "INSERT INTO reservation VALUES (?,?,?,?,?,?,?,?)",
                    reservationDTO.getReservationId(),
                    reservationDTO.getCustomerNic(),
                    reservationDTO.getCashierUsername(),
                    reservationDTO.getPickUpDate(),
                    reservationDTO.getPickUpTime(),
                    reservationDTO.getReturnDate(),
                    reservationDTO.getReturnTime(),
                    reservationDTO.getIsDriverWant()
            );

            if (!isReservationSaved) {
                connection.rollback();
                return false;
            }

            boolean isReservationDetailListSaved = reservationDetailModel.saveReservationDetailList(reservationDTO.getReservationDetailDTOS());
            if (!isReservationDetailListSaved) {
                connection.rollback();
                return false;
            }

            for (ReservationDetailDTO reservationDetailDto : reservationDTO.getReservationDetailDTOS()) {
                boolean isCarUpdated = CrudUtil.execute(
                        "UPDATE car SET availability_status = 'No' WHERE license_plate_no = ?",
                        reservationDetailDto.getLicensePlateNo()
                );

                if (!isCarUpdated) {
                    connection.rollback();
                    return false;
                }

                if (reservationDTO.getIsDriverWant().equalsIgnoreCase("Yes")) {
                    ResultSet rst = CrudUtil.execute(
                                "SELECT driver_nic FROM driver_assignment WHERE license_plate_no = ?",
                                reservationDetailDto.getLicensePlateNo()
                        );

                    if(rst.next()) {
                        boolean isDriverUpdated = CrudUtil.execute(
                                    "UPDATE driver SET availability_status = 'No' WHERE nic = ?",
                                    rst.getString(1)
                        );

                        if (!isDriverUpdated) {
                            connection.rollback();
                            return false;
                        }
                    } else {
                        connection.rollback();
                        throw new SQLException("Driver not linked to the car being reserved.");
                    }
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7),
                    rst.getString(8)
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
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getDate(6),
                    rst.getString(7),
                    rst.getString(8)
            );
        }
        return null;
    }

    public int getMonthlySales() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(reservation_id) AS sales_count FROM reservation WHERE MONTH(pick_up_date) = MONTH(CURRENT_DATE()) AND YEAR(pick_up_date) = YEAR(CURRENT_DATE())"
        );

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

}

package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.DriverDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {

    private final DriverAssignmentModel driverAssignmentModel = new DriverAssignmentModel();

    public boolean saveDriver(DriverDTO driverDTO) throws SQLException {
        boolean isDriverSaved = CrudUtil.execute(
                "INSERT INTO driver VALUES (?,?,?,?,?,?)",
                driverDTO.getNic(),
                driverDTO.getName(),
                driverDTO.getEmail(),
                driverDTO.getAvailabilityStatus(),
                driverDTO.getContactNumber(),
                driverDTO.getPricePerKm()
        );
        if (isDriverSaved) {
            return driverAssignmentModel.saveDriverAssignmentList(driverDTO.getDriverAssignmentDTOS());
        }
        return false;
    }

    public ArrayList<DriverDTO> getAllDrivers() throws SQLException {
        ArrayList<DriverDTO> driverDTOS;
        try {

            ResultSet rst = CrudUtil.execute("SELECT * FROM `driver`");

            driverDTOS = new ArrayList<>();

            while (rst.next()) {
                DriverDTO driverDTO = new DriverDTO(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getDouble(6)
                );
                driverDTOS.add(driverDTO);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return driverDTOS;
    }

    public boolean updateDriver(DriverDTO driverDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE driver SET name = ?, email = ?, availability_status = ?, contact_number = ?, price_per_km = ? WHERE nic = ?",
                driverDTO.getName(),
                driverDTO.getEmail(),
                driverDTO.getAvailabilityStatus(),
                driverDTO.getContactNumber(),
                driverDTO.getPricePerKm(),
                driverDTO.getNic()
        );
    }
}

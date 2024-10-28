package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.DriverDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {

    public boolean saveDriver(DriverDTO driverDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO driver VALUES (?,?,?,?,?)",
                driverDTO.getNic(),
                driverDTO.getName(),
                driverDTO.getEmail(),
                driverDTO.getAvailabilityStatus(),
                driverDTO.getContactNumber()
        );
    }

    public ArrayList<DriverDTO> getAllDrivers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM driver");

        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();

        while (rst.next()) {
            DriverDTO driverDTO = new DriverDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            driverDTOS.add(driverDTO);
        }
        return driverDTOS;
    }
}

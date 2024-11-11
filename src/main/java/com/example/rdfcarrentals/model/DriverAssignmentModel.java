package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.DriverAssignmentDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverAssignmentModel {

    public boolean saveDriverAssignmentList(ArrayList<DriverAssignmentDTO> driverAssignmentDTOS) throws SQLException {
        for (DriverAssignmentDTO driverAssignmentDTO : driverAssignmentDTOS) {
            boolean isDriverAssignmentSaved = saveDriverAssignment(driverAssignmentDTO);
            if (!isDriverAssignmentSaved) {
                return false;
            }
        }
        return true;
    }

    public boolean saveDriverAssignment(DriverAssignmentDTO driverAssignmentDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO driver_assignment VALUES (?,?,?)",
                driverAssignmentDTO.getLicensePlateNo(),
                driverAssignmentDTO.getDriverNic(),
                driverAssignmentDTO.getPricePerKm()
        );
    }

    public ArrayList<DriverAssignmentDTO> getDriverAssignments() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM driver_assignment"
        );

        ArrayList<DriverAssignmentDTO> driverAssignmentDTOS = new ArrayList<>();

        while (resultSet.next()) {
            DriverAssignmentDTO driverAssignmentDTO = new DriverAssignmentDTO(
                    resultSet.getString("license_plate_no"),
                    resultSet.getString("driver_nic"),
                    resultSet.getDouble("price_per_km")
            );
            driverAssignmentDTOS.add(driverAssignmentDTO);
        }
        return driverAssignmentDTOS;
    }

}

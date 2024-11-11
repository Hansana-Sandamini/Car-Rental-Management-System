package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.DriverAssignmentDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<DriverAssignmentDTO> getDriverAssignments() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT da.license_plate_no, d.nic AS driver_nic, d.price_per_km " +
                        "FROM driver_assignment da " +
                        "JOIN car c ON da.license_plate_no = c.license_plate_no " +
                        "JOIN driver d ON da.driver_nic = d.nic;"
        );

        List<DriverAssignmentDTO> driverAssignmentDTOS = new ArrayList<>();

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

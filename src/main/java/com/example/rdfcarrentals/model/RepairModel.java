package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.RepairDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairModel {

    public ArrayList<RepairDTO> getAllRepairs() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM repair");

        ArrayList<RepairDTO> repairDTOS = new ArrayList<>();

        while (rst.next()) {
            RepairDTO repairDTO = new RepairDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            repairDTOS.add(repairDTO);
        }
        return repairDTOS;
    }
}

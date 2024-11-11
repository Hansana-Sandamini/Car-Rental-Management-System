package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.RepairDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairModel {

    public String getNextRepairId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT repair_id FROM repair ORDER BY repair_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);

            if (lastId.startsWith("Rep-")) {
                String substring = lastId.substring(4);
                try {
                    int i = Integer.parseInt(substring);
                    int newIdIndex = i + 1;
                    return String.format("Rep-%03d", newIdIndex);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new SQLException("Invalid repair_id format in database: " + lastId);
                }
            } else {
                throw new SQLException("repair_id does not start with the expected prefix 'Rep-': " + lastId);
            }
        }
        return "Rep-001";
    }

    public boolean addRepair(RepairDTO repairDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO repair VALUES (?,?,?,?,?)",
                repairDTO.getRepairId(),
                repairDTO.getDescription(),
                repairDTO.getDate(),
                repairDTO.getCost(),
                repairDTO.getLicensePlateNo()
        );
    }

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

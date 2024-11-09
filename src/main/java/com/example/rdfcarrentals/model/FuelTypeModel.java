package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.FuelTypeDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuelTypeModel {

    public ArrayList<String> getAllFuelTypeIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT type_id FROM fuel_type");

        ArrayList<String> fuelTypeIds = new ArrayList<>();

        while (rst.next()) {
            fuelTypeIds.add(rst.getString(1));
        }
        return fuelTypeIds;
    }

    public FuelTypeDTO findById(String selectedTypeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM fuel_type WHERE type_id = ?", selectedTypeId);

        if (rst.next()) {
            return new FuelTypeDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
        }
        return null;
    }
}

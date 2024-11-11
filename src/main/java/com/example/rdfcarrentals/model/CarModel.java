package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.CarDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarModel {

    public boolean saveCar(CarDTO carDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO car VALUES (?,?,?,?,?,?,?)",
                carDTO.getLicensePlateNo(),
                carDTO.getModel(),
                carDTO.getColour(),
                carDTO.getDailyRate(),
                carDTO.getMonthlyRate(),
                carDTO.getAvailabilityStatus(),
                carDTO.getTypeId()
        );
    }

    public ArrayList<CarDTO> getAllCars() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM car");

        ArrayList<CarDTO> carDTOS = new ArrayList<>();

        while (rst.next()) {
            CarDTO carDTO = new CarDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7)
            );
            carDTOS.add(carDTO);
        }
        return carDTOS;
    }

    public boolean updateCar(CarDTO carDTO) throws SQLException {
        return CrudUtil.execute("UPDATE car SET type_id = ?, model = ?, colour = ?, daily_rate = ?, monthly_rate = ?, availability_status = ? WHERE license_plate_no = ?",
              carDTO.getTypeId(),
              carDTO.getModel(),
              carDTO.getColour(),
              carDTO.getDailyRate(),
              carDTO.getMonthlyRate(),
              carDTO.getAvailabilityStatus(),
              carDTO.getLicensePlateNo()
        );
    }

    public boolean deleteCar(String LicensePlateNo) throws SQLException {
        return CrudUtil.execute("DELETE FROM car WHERE license_plate_no = ?", LicensePlateNo);
    }

    public CarDTO findByLicensePlateNo(String selectedLicensePlateNo) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM car WHERE license_plate_no = ?", selectedLicensePlateNo);

        if (rst.next()) {
            return new CarDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }
        return null;
    }

    public ArrayList<String> getAllLicensePlateNos() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT license_plate_no FROM car");

        ArrayList<String> licensePlateNos = new ArrayList<>();

        while (rst.next()) {
            licensePlateNos.add(rst.getString(1));
        }
        return licensePlateNos;
    }
}

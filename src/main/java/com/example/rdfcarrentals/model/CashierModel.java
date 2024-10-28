package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.CashierDTO;
import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CashierModel {

    public boolean saveCashier(CashierDTO cashierDTO) throws SQLException {
        return CrudUtil.execute(
              "INSERT INTO cashier VALUES (?,?,?,?,?)",
                cashierDTO.getUserName(),
                cashierDTO.getPassword(),
                cashierDTO.getName(),
                cashierDTO.getEmail(),
                cashierDTO.getContactNumber()
        );
    }

    public ArrayList<CashierDTO> getAllCashiers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM cashier");

        ArrayList<CashierDTO> cashierDTOS = new ArrayList<>();

        while (rst.next()) {
            CashierDTO cashierDTO = new CashierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            cashierDTOS.add(cashierDTO);
        }
        return cashierDTOS;
    }

    public boolean updateCashier(CashierDTO cashierDTO) throws SQLException {
        return CrudUtil.execute("UPDATE cashier SET password = ?, name = ?, email = ?, contact_number = ? WHERE username = ?",
                cashierDTO.getPassword(),
                cashierDTO.getName(),
                cashierDTO.getEmail(),
                cashierDTO.getContactNumber(),
                cashierDTO.getUserName()
        );
    }

    public boolean deleteCashier(String cashierUserName) throws SQLException {
        return CrudUtil.execute("DELETE FROM cashier WHERE username = ?", cashierUserName);
    }

}

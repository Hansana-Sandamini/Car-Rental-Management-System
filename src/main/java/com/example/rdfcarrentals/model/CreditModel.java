package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.CreditDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreditModel {

    public String getNextCreditId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select credit_id from credit order by credit_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    public boolean saveCredit(CreditDTO creditDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO credit VALUES (?,?,?,?,?)",
                creditDTO.getCreditId(),
                creditDTO.getTotalAmount(),
                creditDTO.getAmountPaid(),
                creditDTO.getAmountToPay(),
                creditDTO.getDueDate()
        );
    }

    public ArrayList<CreditDTO> getAllCredits() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM credit");

        ArrayList<CreditDTO> creditDTOS = new ArrayList<>();

        while (rst.next()) {
            CreditDTO creditDTO = new CreditDTO(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDate(5)
            );
            creditDTOS.add(creditDTO);
        }
        return creditDTOS;
    }

    public boolean updateCredit(CreditDTO creditDTO) throws SQLException {
        return CrudUtil.execute("UPDATE credit SET total_amount = ?, amount_paid = ?, amount_to_pay = ?, due_date = ? WHERE credit_id = ?",
                creditDTO.getTotalAmount(),
                creditDTO.getAmountPaid(),
                creditDTO.getAmountToPay(),
                creditDTO.getDueDate(),
                creditDTO.getCreditId()
        );
    }

    public boolean deleteCredit(String creditId) throws SQLException {
        return CrudUtil.execute("DELETE FROM credit WHERE credit_id = ?", creditId);
    }
}

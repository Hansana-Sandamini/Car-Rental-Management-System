package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.CreditDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreditModel {

    public String getNextCreditId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT credit_id FROM credit ORDER BY credit_id DESC LIMIT 1");

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
                "INSERT INTO credit VALUES (?,?,?,?,?,?)",
                creditDTO.getCreditId(),
                creditDTO.getReservationId(),
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
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDate(6)
            );
            creditDTOS.add(creditDTO);
        }
        return creditDTOS;
    }

    public boolean updateCredit(CreditDTO creditDTO) throws SQLException {
        return CrudUtil.execute("UPDATE credit SET reservation_id = ?, total_amount = ?, amount_paid = ?, amount_to_pay = ?, due_date = ? WHERE credit_id = ?",
                creditDTO.getReservationId(),
                creditDTO.getTotalAmount(),
                creditDTO.getAmountPaid(),
                creditDTO.getAmountToPay(),
                creditDTO.getDueDate(),
                creditDTO.getCreditId()
        );
    }

    public int getCreditNotPaidCount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(credit_id) FROM credit WHERE due_date<now() AND amount_to_pay > 0");

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
}

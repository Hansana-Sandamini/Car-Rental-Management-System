package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.PaymentDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    public boolean addPayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO payment VALUES (?,?,?,?,?,?,?)",
                paymentDTO.getPaymentId(),
                paymentDTO.getReservationId(),
                paymentDTO.getBillId(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getAmount(),
                paymentDTO.getDate(),
                paymentDTO.getTime()
        );
    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment");

        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDate(6),
                    rst.getString(7)
            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }
}

package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.BillDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillModel {

    public String getNextBillId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT bill_id FROM bill ORDER BY bill_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("B%03d", newIdIndex);
        }
        return "B001";
    }

    public boolean saveBill(BillDTO billDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO bill VALUES (?,?,?,?)",
                billDTO.getBillId(),
                billDTO.getPaymentId(),
                billDTO.getDescription(),
                billDTO.getIssueDate()
        );
    }
}

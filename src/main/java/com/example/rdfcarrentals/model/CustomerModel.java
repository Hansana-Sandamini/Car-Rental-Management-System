package com.example.rdfcarrentals.model;

import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO customer VALUES (?,?,?,?,?)",
                customerDTO.getNic(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getContactNumber()
        );
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer");

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
            rst.getString(1),
            rst.getString(2),
            rst.getString(3),
            rst.getString(4),
            rst.getString(5)
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE customer SET name = ?, address = ?, email = ?, contact_number = ? WHERE nic = ?",
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getContactNumber(),
                customerDTO.getNic()
        );
    }

    public boolean deleteCustomer(String customerNIC) throws SQLException {
        return CrudUtil.execute("DELETE FROM customer WHERE nic = ?", customerNIC);
    }

    public ArrayList<String> getAllCustomerNICs() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT nic FROM customer");

        ArrayList<String> customerNICs = new ArrayList<>();

        while (rst.next()) {
            customerNICs.add(rst.getString(1));
        }
        return customerNICs;
    }

    public CustomerDTO findByNIC(String selectedCustomerNIC) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer WHERE nic = ?", selectedCustomerNIC);

        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}

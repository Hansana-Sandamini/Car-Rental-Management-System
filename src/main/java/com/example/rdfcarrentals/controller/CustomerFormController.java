package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.model.CustomerModel;
import com.example.rdfcarrentals.tm.CustomerTM;
import com.example.rdfcarrentals.util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colContactNumber;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private AnchorPane customerContent;

    @FXML
    private TableView<CustomerTM> tblCustomers;

    @FXML
    private TextField txtFldAddress;

    @FXML
    private TextField txtFldContactNumber;

    @FXML
    private TextField txtFldEmail;

    @FXML
    private TextField txtFldNIC;

    @FXML
    private TextField txtFldName;

    @FXML
    private TextField txtFldSearchHere;

    private final CustomerModel customerModel = new CustomerModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerNIC = txtFldNIC.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete this Customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = customerModel.deleteCustomer(customerNIC);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Delete Customer...!").show();
            }
        }
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            CustomerDTO customerDTO = getTextFieldsValues();

            boolean isSaved = customerModel.saveCustomer(customerDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Save Customer...!").show();
            }
        }
    }

    CustomerDTO getTextFieldsValues() {
        String nic = txtFldNIC.getText();
        String name = txtFldName.getText();
        String address = txtFldAddress.getText();
        String email = txtFldEmail.getText();
        String contactNumber = txtFldContactNumber.getText();

        return new CustomerDTO(nic, name, address, email, contactNumber);
    }

    boolean validateTextFields() {
        boolean isValidNic = ValidationUtil.isValidNic(txtFldNIC);
        boolean isValidName = ValidationUtil.isValidName(txtFldName);
        boolean isValidAddress = ValidationUtil.isValidAddress(txtFldAddress);
        boolean isValidEmail = ValidationUtil.isValidEmail(txtFldEmail);
        boolean isValidContactNumber = ValidationUtil.isValidContactNumber(txtFldContactNumber);

        return isValidNic && isValidName && isValidAddress && isValidEmail && isValidContactNumber;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            CustomerDTO customerDTO = getTextFieldsValues();

            boolean isUpdate = customerModel.updateCustomer(customerDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Update Customer...!").show();
            }
        }
    }

    @FXML
    void tblCustomersOnClicked(MouseEvent event) {
        CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtFldNIC.setText(selectedItem.getNic());
            txtFldName.setText(selectedItem.getName());
            txtFldAddress.setText(selectedItem.getAddress());
            txtFldEmail.setText(selectedItem.getEmail());
            txtFldContactNumber.setText(selectedItem.getContactNumber());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        txtFldNIC.setText("");
        txtFldName.setText("");
        txtFldAddress.setText("");
        txtFldEmail.setText("");
        txtFldContactNumber.setText("");

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getNic(),
                    customerDTO.getName(),
                    customerDTO.getAddress(),
                    customerDTO.getEmail(),
                    customerDTO.getContactNumber()
            );
            customerTMS.add(customerTM);
        }
        tblCustomers.setItems(customerTMS);
    }
}
